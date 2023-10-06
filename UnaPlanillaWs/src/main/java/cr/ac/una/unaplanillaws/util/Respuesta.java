/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.unaplanillaws.util;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author ccarranza
 */
public class Respuesta implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Boolean estado;
    private CodigoRespuesta codigoRespuesta;   
    private String mensaje;    
    private String mensajeInterno;
    private HashMap<String, Object> resultado; 

    public Respuesta() {
        this.resultado = new HashMap<>();
    }

    public Respuesta(Boolean estado, CodigoRespuesta codigoRespuesta, String mensaje, String mensajeInterno) {
        this.estado = estado;
        this.codigoRespuesta = codigoRespuesta;
        this.mensaje = mensaje;
        this.mensajeInterno = mensajeInterno;
        this.resultado = new HashMap<>();
    }
    
    public Respuesta(Boolean estado, CodigoRespuesta codigoRespuesta, String mensaje, String mensajeInterno, String nombre, Object resultado) {
        this.estado = estado;
        this.codigoRespuesta = codigoRespuesta;
        this.mensaje = mensaje;
        this.mensajeInterno = mensajeInterno;
        this.resultado = new HashMap<>();
        this.resultado.put(nombre, resultado);
    }

    public Respuesta(Boolean estado, CodigoRespuesta codigoRespuesta, String mensaje, String mensajeInterno, Object resultado) {
        this.estado = estado;
        this.codigoRespuesta = codigoRespuesta;
        this.mensaje = mensaje;
        this.mensajeInterno = mensajeInterno;
        this.resultado = new HashMap<>();
        this.resultado.put("[Objeto]", resultado);
    }
    
    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public CodigoRespuesta getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(CodigoRespuesta codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensajeInterno() {
        return mensajeInterno;
    }

    public void setMensajeInterno(String mensajeInterno) {
        this.mensajeInterno = mensajeInterno;
    }
    
    public Object getResultado(String nombre) {
        return resultado.get(nombre);
    }

    public void setResultado(String nombre, Object resultado) {
        this.resultado.put(nombre, resultado);
    }
    
    public Object getResultado() {
        return resultado.get("[Objeto]");
    }

    public void setResultado(Object resultado) {
        this.resultado.put("[Objeto]", resultado);
    }
}
