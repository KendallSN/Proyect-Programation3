
package cr.ac.una.sigeceunaemail.service;

import cr.ac.una.sigeceunaemail.model.VariableDto;
import cr.ac.una.sigeceunaemail.util.Request;
import cr.ac.una.sigeceunaemail.util.Response;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class VariableService {
    public Response saveVariable(VariableDto variableDto){
        try {
            Request request = new Request("VariableController/saveVariable","comunicationUrl");
            request.post(variableDto);
            if(request.isError()){
             return new Response(false,request.getError(),"");
            }
            VariableDto variable=(VariableDto)request.readEntity(VariableDto.class);
            return new Response(true, "", "", "Variable",variable);
        } catch (Exception ex) {
            Logger.getLogger(VariableService.class.getName()).log(Level.SEVERE, "An error ocurred while saving the variable.", ex);
            return new Response(false, "An error ocurred while saving the variable", "saveVariable " + ex.getMessage());
        }
    }
    public Response getVariablesByNTP(Long idNotificationProcess){
        try{
           Map<String,Object> parametros=new HashMap<>();
           parametros.put("idNotificationProcess", idNotificationProcess);
           Request request=new Request("VariableController/getVariablesByNTP", "/{idNotificationProcess}", parametros,"comunicationUrl");
           request.get();
           if(request.isError()){
               return new Response(false, request.getError(), "");
           }
           List<VariableDto> variablesDto = (List<VariableDto>) request.readEntity(new GenericType<List<VariableDto>>() {});
           return new Response(true,"","","Variables",variablesDto);

        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the variables.", ex);
            return new Response(false, "An error ocurred while querying the variables.", "getVariablesByNTP " + ex.getMessage());
        }
    }
    public Response deleteVariable(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("VariableController/deleteVariable", "/{id}", parametros,"comunicationUrl");
            request.delete();
            if(request.isError()){
             return new Response(false,request.getError(), "");
            }
            return new Response(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(VariableService.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the variable", ex);
            return new Response(false, "An error ocurred while deleting the variable.", "deleteVariable " + ex.getMessage());
        }
    }
}
