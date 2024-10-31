
package cr.ac.una.sigeceunamessaging.service;

import cr.ac.una.sigeceunamessaging.model.SystemsDto;
import cr.ac.una.sigeceunamessaging.util.Request;
import cr.ac.una.sigeceunamessaging.util.Response;
//import cr.ac.una.sigeceunasecurityws.controller.SystemsController;
//import cr.ac.una.sigeceunasecurityws.controller.SystemsDto;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemsService implements Serializable{
    
public Response getSystemById(Long id){
        try{
           Map<String,Object> parametros=new HashMap<>();
           parametros.put("id", id);
           Request request=new Request("SystemsControllerRs/getSystemById", "/{id}", parametros,"securityUrl");
           request.get();
           if(request.isError()){
               return new Response(false, request.getError(), "");
           }
           SystemsDto systemsDto=(SystemsDto)request.readEntity(SystemsDto.class);
            return new Response(true,"","", "Systems",systemsDto);
        } catch (Exception ex) {
            Logger.getLogger(SystemsService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the systems [" + id +"]", ex);
            return new Response(false, "An error ocurred while querying the system", "getSystemById " + ex.getMessage());
        }
    }
    
}
