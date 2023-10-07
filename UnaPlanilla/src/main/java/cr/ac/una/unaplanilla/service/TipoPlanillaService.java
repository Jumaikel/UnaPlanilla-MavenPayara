/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.unaplanilla.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import cr.ac.una.unaplanilla.model.TipoPlanillaDto;
import cr.ac.una.unaplanilla.util.Request;
import cr.ac.una.unaplanilla.util.Respuesta;
import jakarta.ws.rs.core.GenericType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class TipoPlanillaService {

    public Respuesta getTipoPlanilla(Long id) {
        try {
            // TODO
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("TipoPlanillaController/gettipoplanilla/", "{id}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            TipoPlanillaDto tipoPlanillaDto = (TipoPlanillaDto) request.readEntity(TipoPlanillaDto.class);
            return new Respuesta(true, "", "", "TipoPlanilla", tipoPlanillaDto);
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error obteniendo el tipo de Planilla [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el tipo de Planilla.", "getTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta getTiposPlanilla() {
        try {
            // TODO
            Request request = new Request("TipoPlanillaController/gettiposplanilla");
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<TipoPlanillaDto> tiposPlanillaDto = new ArrayList<>();
            tiposPlanillaDto = (List<TipoPlanillaDto>) request.readEntity( new GenericType<List<TipoPlanillaDto>>() {});
   
            System.out.println(tiposPlanillaDto.size());
            return new Respuesta(true, "", "", "TiposPlanilla", tiposPlanillaDto);
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error obteniendo los tipos de Planilla", ex);
            return new Respuesta(false, "Error obteniendo los tipos de Planilla.", "getTiposPlanilla " + ex.getMessage());
        }
    }

    public Respuesta guardarTipoPlanilla(TipoPlanillaDto tipoPlanilla) {
        try {
            // TODO
            Request request = new Request("TipoPlanillaController/svtipoplanilla/");
            request.post(tipoPlanilla);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            TipoPlanillaDto tipoPlanillaDto = (TipoPlanillaDto) request.readEntity(TipoPlanillaDto.class);
            return new Respuesta(true, "", "", "TipoPlanilla", tipoPlanillaDto);
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error guardando el empleado.", ex);
            return new Respuesta(false, "Error guardando el tipo de Planilla.", "guardarTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta eliminarTipoPlanilla(Long id) {
        try {
            // TODO
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("TipoPlanillaController/dltipoplanilla/", "{id}", parametros);
            request.delete();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error eliminando el tipo de Planilla.", ex);
            return new Respuesta(false, "Error eliminando el tipo de Planilla.", "eliminarTipoPlanilla " + ex.getMessage());
        }
    }
}
