
package cr.ac.una.sigeceunaemail.service;

import cr.ac.una.sigeceunaemail.model.ConstrainDto;
import cr.ac.una.sigeceunaemail.util.Request;
import cr.ac.una.sigeceunaemail.util.Response;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConstrainService {
    public Response saveConstrain(ConstrainDto constrainDto){
        try {
            Request request = new Request("ConstrainController/saveConstrain","comunicationUrl");
            request.post(constrainDto);
            if(request.isError()){
             return new Response(false,request.getError(),"");
            }
            ConstrainDto constrain=(ConstrainDto)request.readEntity(ConstrainDto.class);
            return new Response(true, "", "", "Constrain",constrain);
        } catch (Exception ex) {
            Logger.getLogger(ConstrainService.class.getName()).log(Level.SEVERE, "An error ocurred while saving the constrain.", ex);
            return new Response(false, "An error ocurred while saving the constrain", "saveConstrain " + ex.getMessage());
        }
    }
    public Response getConstrainsByVariable(Long idVariable){
        try{
           Map<String,Object> parametros=new HashMap<>();
           parametros.put("idVariable", idVariable);
           Request request=new Request("ConstrainController/getConstrainsByVariable", "/{idVariable}", parametros,"comunicationUrl");
           request.get();
           if(request.isError()){
               return new Response(false, request.getError(), "");
           }
           List<ConstrainDto> constrainsDto = (List<ConstrainDto>) request.readEntity(new GenericType<List<ConstrainDto>>() {});
           return new Response(true,"","","Constrains",constrainsDto);

        } catch (Exception ex) {
            Logger.getLogger(ConstrainService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the constrains.", ex);
            return new Response(false, "An error ocurred while querying the constrains.", "getConstrainsByVariable " + ex.getMessage());
        }
    }
    public Response deleteConstrain(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("ConstrainController/deleteConstrain", "/{id}", parametros,"comunicationUrl");
            request.delete();
            if(request.isError()){
             return new Response(false,request.getError(), "");
            }
            return new Response(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(ConstrainService.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the constrain", ex);
            return new Response(false, "An error ocurred while deleting the constrain.", "deleteConstrain " + ex.getMessage());
        }
    }
}
