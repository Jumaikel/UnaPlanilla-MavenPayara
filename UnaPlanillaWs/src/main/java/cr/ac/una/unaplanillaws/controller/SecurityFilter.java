/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.unaplanillaws.controller;

import cr.ac.una.unaplanillaws.util.JwTokenHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import java.io.IOException;
import java.security.Principal;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import cr.ac.una.unaplanillaws.util.Secure;
import java.lang.reflect.Method;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;

/**
 *
 * @author cbcar
 */
@Provider //el servidor de aplicaciones va a controlar la clase como tal
@Secure
@Priority(Priorities.AUTHENTICATION)//puedo tener varios filtros por eso tengo que indicar cual tiene prioridad
public class SecurityFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_SERVICE_PATH = "getEmpleado";  // cual es el metodo 
    private static final String RENEWAL_SERVICE_PATH = "renovarToken"; //cual es el metodo que utilizo para renovar
    private final JwTokenHelper jwTokenHelper = JwTokenHelper.getInstance();
    private static final String AUTHENTICATION_SCHEME = "Bearer ";

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext request) throws IOException {
        Method method = resourceInfo.getResourceMethod();//obtiene a que metodo va y si es igual al de arriba lo deja pasar sino lo tiene que validar con el codigo de abajo
        if (method.getName().equals(AUTHORIZATION_SERVICE_PATH)) {
            return;
        }

        // Get the Authorization header from the request
        String authorizationHeader = request.getHeaderString(HttpHeaders.AUTHORIZATION); // se obtiene el header de autorizacion, que es lo que se genera en el suwager cuand uno hace una consulta

        // Validate the Authorization header
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {  // se reviza que sii halla obtenido el header
            abortWithUnauthorized(request, "Authorization is missing in header");
            return;

        } else if (!isTokenBasedAuthentication(authorizationHeader)) { // si si viene, revisa si el token es valido
            abortWithUnauthorized(request, "Invalid authorization");
            return;
        }

        // Extract the token from the Authorization header
        String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();  // se le quita el bearer del inicio

        try {

            // Validate the token
            try {
                Claims claims = jwTokenHelper.claimKey(token); // trata de extraer las propiedades del token
                if (method.getName().equals(RENEWAL_SERVICE_PATH)) {  //si el request va a el metodo de renovar token tiene que ser token de renovacion
                    if(!(boolean)claims.getOrDefault("rnw", false)){  // el rnw es una propiedad que se ve en el jwt para saber si es de renovacion o no
                        abortWithUnauthorized(request, "Invalid authorization");
                    }
                } else {
                    if((boolean)claims.getOrDefault("rnw", false)){  // si va para otro metodo tiene que validar que sea un token normal y no uno de renovacion
                        abortWithUnauthorized(request, "Invalid authorization");
                    }
                }
                final SecurityContext currentSecurityContext = request.getSecurityContext();  //se sobreescribe e contexto de seguridad
                request.setSecurityContext(new SecurityContext() {

                    @Override
                    public Principal getUserPrincipal() {  // de quien es la peticion
                        return () -> claims.getSubject();//saque el usuario de los claims y use la propiedad subject(tambien es un campo del jwt)
                    }

                    @Override
                    public boolean isUserInRole(String role) {  // si el usuario tiene un rol en especifico, como cuando un usuario tiene restricciones para hacer metodos 
                        return true;
                    }

                    @Override
                    public boolean isSecure() {
                        return currentSecurityContext.isSecure();
                    }

                    @Override
                    public String getAuthenticationScheme() { // obtiene la palabra bearer
                        return AUTHENTICATION_SCHEME;
                    }
                });
            } catch (ExpiredJwtException | MalformedJwtException e) {   // esto es por si esta expirado el token o si fue modificado, se hace en la primera linea de el try
                if (e instanceof ExpiredJwtException) {
                    abortWithUnauthorized(request, "Authorization is expired");
                } else if (e instanceof MalformedJwtException) {
                    abortWithUnauthorized(request, "Authorization is not correct");
                }
            }

        } catch (Exception e) {
            abortWithUnauthorized(request, "Invalid authorization");
        }
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) { 

        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(AUTHENTICATION_SCHEME.toLowerCase());
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext, String message) {

        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED.getStatusCode(), message)
                        .header(HttpHeaders.WWW_AUTHENTICATE,
                                message)
                        .build());
    }

}
