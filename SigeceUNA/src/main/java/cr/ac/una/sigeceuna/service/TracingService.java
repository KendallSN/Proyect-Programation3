
package cr.ac.una.sigeceuna.service;

import cr.ac.una.sigeceuna.model.TracingDto;
import cr.ac.una.sigeceuna.util.Request;
import cr.ac.una.sigeceuna.util.Response;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TracingService {
    public Response saveTracing(TracingDto tracingDto){
        try {
            Request request = new Request("TracingController/saveTracing","sigeceUrl");
            request.post(tracingDto);
            if(request.isError()){
             return new Response(false,request.getError(),"");
            }
            TracingDto tracing=(TracingDto)request.readEntity(TracingDto.class);
            return new Response(true, "", "", "Tracing",tracing);
        } catch (Exception ex) {
            Logger.getLogger(TracingService.class.getName()).log(Level.SEVERE, "An error ocurred while saving the tracing.", ex);
            return new Response(false, "An error ocurred while saving the tracing", "saveTracing " + ex.getMessage());
        }
    }
    public Response getTracings() {
        try {
            Request request=new Request("TracingController/getTracings","sigeceUrl");
            request.get();
            if(request.isError()){
                return new Response(false,request.getError(),"");
            }
            List<TracingDto> tracingsDto = (List<TracingDto>) request.readEntity(new GenericType<List<TracingDto>>() {});
        return new Response(true,"","","Tracings",tracingsDto);

        } catch (Exception ex) {
            Logger.getLogger(TracingService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the tracing.", ex);
            return new Response(false, "An error ocurred while querying the tracings.", "getTracings " + ex.getMessage());
        }
    }
    public Response deleteTracing(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("TracingController/deleteTracing", "/{id}", parametros,"sigeceUrl");
            request.delete();
            if(request.isError()){
             return new Response(false,request.getError(), "");
            }
            return new Response(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(TracingService.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the tracing", ex);
            return new Response(false, "An error ocurred while deleting the tracing.", "deleteTracing " + ex.getMessage());
        }
    }
}
