/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.unaplanillaws.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ElementKind;
import jakarta.validation.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

/**
 *
 * @author
 */
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger logger = Logger.getLogger(ConstraintViolationExceptionMapper.class.getName());

    @Override
    public Response toResponse(ConstraintViolationException constraintViolation) {
        try {
            ConstraintViolation violation = (ConstraintViolation) constraintViolation.getConstraintViolations().toArray()[0];

            String atributos = "";
            try {
                List<Path.Node> nodos = new ArrayList();
                violation.getPropertyPath().iterator().forEachRemaining(nodos::add);
                atributos = nodos.stream().filter((t) -> t.getKind() == ElementKind.PROPERTY).map((t) -> t.getName()).collect(Collectors.joining(", "));
            } catch (Exception ex) {
                this.logger.log(Level.SEVERE, String.format("Ocurrió al obtener el nombre de los datos que se enviaron de forma incorrecta en los parámetros. ",
                        ((ConstraintViolation) constraintViolation.getConstraintViolations().toArray()[0]).getLeafBean().getClass().toString()), ex);
            }

            return Response.status(CodigoRespuesta.ERROR_CLIENTE.getValue())
                    .entity(violation.getMessage().concat(". Los siguientes datos son invalidos [").concat(atributos).concat("]."))
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build();

        } catch (Exception ex) {
            this.logger.log(Level.SEVERE, String.format("Ocurrió un error al validar los datos de la entidad recibida [%s]. ",
                    ((ConstraintViolation) constraintViolation.getConstraintViolations().toArray()[0]).getLeafBean().getClass().toString()), ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue())
                    .entity("Ocurrió un error al validar los datos recibidos.")
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build();
        }
    }
    
     
}
