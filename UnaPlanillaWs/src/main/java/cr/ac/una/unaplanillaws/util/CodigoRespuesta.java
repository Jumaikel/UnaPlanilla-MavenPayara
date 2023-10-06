package cr.ac.una.unaplanillaws.util;

/**
 *
 * @author ccarranza
 */
public enum CodigoRespuesta {
    CORRECTO(200),
    ERROR_ACCESO(403),
    ERROR_PERMISOS(401),
    ERROR_NOENCONTRADO(404),
    ERROR_CLIENTE(400),
    ERROR_INTERNO(500);

    private Integer value;

    private CodigoRespuesta(Integer value) {
        this.setValue(value);
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
