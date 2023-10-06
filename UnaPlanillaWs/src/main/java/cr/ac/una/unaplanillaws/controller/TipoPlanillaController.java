package cr.ac.una.unaplanillaws.controller;

import cr.ac.una.unaplanillaws.model.TipoPlanillaDto;
import cr.ac.una.unaplanillaws.service.TipoPlanillaService;
import cr.ac.una.unaplanillaws.util.CodigoRespuesta;
import cr.ac.una.unaplanillaws.util.Respuesta;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jumaikel
 */
@Path("/TipoPlanillaController")
@Tag(name = "tiposplanillas", description = "operaciones con tipos de planillas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
//@Secure
public class TipoPlanillaController {

    @EJB
    TipoPlanillaService tipoPlanillaService;

    @GET
    @Path("/gettipoplanilla/{id}")
    public Response getTipoPlanilla(@PathParam("id") Long id) {
        try {
            Respuesta res = tipoPlanillaService.getTipoPlanilla(id);
            if (!res.getEstado()) {
                return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
            }
            return Response.ok(res.getResultado("TipoPlanilla")).build();
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el tipo de planilla").build();
        }
    }

    @GET
    @Path("/gettiposplanilla")
    public Response getTiposPlanilla() {
        try {
            Respuesta res = tipoPlanillaService.getTiposPlanilla();
            if (!res.getEstado()) {
                return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
            }
            return Response.ok(res.getResultado("TiposPlanilla")).build();
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo los tipos de planilla").build();
        }
    }

    @POST
    @Path("/svtipoplanilla")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardarTipoPlanilla(TipoPlanillaDto tipoPlanillaDto) {

        try {
            Respuesta res = tipoPlanillaService.guardarTipoPlanilla(tipoPlanillaDto);
            if (!res.getEstado()) {
                return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
            }
            return Response.ok(res.getResultado("TipoPlanilla")).build();
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error guardando el tipo de planilla").build();
        }

    }

    @DELETE
    @Path("/dltipoplanilla/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response eliminarTipoPlanilla(@PathParam("id") Long id) {
        try {
            Respuesta res = tipoPlanillaService.eliminarTipoPlanilla(id);
            if (!res.getEstado()) {
                return Response.status(res.getCodigoRespuesta().getValue()).entity(res.getMensaje()).build();
            }
            return Response.ok(res.getResultado("TipoPlanilla")).build();
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error eliminando el tipo de planilla").build();
        }
    }

}
