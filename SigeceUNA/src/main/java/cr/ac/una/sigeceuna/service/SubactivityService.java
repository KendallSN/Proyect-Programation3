
package cr.ac.una.sigeceuna.service;

import cr.ac.una.sigeceuna.model.SubactivityDto;
import cr.ac.una.sigeceuna.util.Request;
import cr.ac.una.sigeceuna.util.Response;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SubactivityService {
    public Response getSubactivity(Long id){
        try{
           Map<String,Object> parametros=new HashMap<>();
           parametros.put("id", id);
           Request request=new Request("SubactivityController/getSubactivity", "/{id}", parametros,"sigeceUrl");
           request.get();
           if(request.isError()){
               return new Response(false, request.getError(), "");
           }
           SubactivityDto subactivityDto=(SubactivityDto)request.readEntity(SubactivityDto.class);
            return new Response(true,"","", "Subactivity",subactivityDto);
        } catch (Exception ex) {
            Logger.getLogger(SubactivityService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the Subactivity [" + id +"]", ex);
            return new Response(false, "An error ocurred while querying the Subactivity", "getSubactivity " + ex.getMessage());
        }
    }
    public Response saveSubactivity(SubactivityDto subactivityDto){
        try {
            Request request = new Request("SubactivityController/saveSubactivity","sigeceUrl");
            request.post(subactivityDto);
            if(request.isError()){
             return new Response(false,request.getError(),"");
            }
            SubactivityDto activity=(SubactivityDto)request.readEntity(SubactivityDto.class);
            return new Response(true, "", "", "Subactivity",activity);
        } catch (Exception ex) {
            Logger.getLogger(SubactivityService.class.getName()).log(Level.SEVERE, "An error ocurred while saving the subactivity.", ex);
            return new Response(false, "An error ocurred while saving the subactivtity", "saveSubactivity " + ex.getMessage());
        }
    }
    public Response getSubactivitiesByActivity(Long ID_Activity){
        try{
           Map<String,Object> parametros=new HashMap<>();
           parametros.put("act_ID", ID_Activity);
           Request request=new Request("SubactivityController/getSubactivitiesByActivity", "/{act_ID}", parametros,"sigeceUrl");
           request.get();
           if(request.isError()){
               return new Response(false, request.getError(), "");
           }
           List<SubactivityDto> subactivitiesDto = (List<SubactivityDto>) request.readEntity(new GenericType<List<SubactivityDto>>() {});
           return new Response(true,"","","Subactivities",subactivitiesDto);

        } catch (Exception ex) {
            Logger.getLogger(SubactivityService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the subactivities.", ex);
            return new Response(false, "An error ocurred while querying the subactivities.", "getSubactivitiesByActivity " + ex.getMessage());
        }
    }
    public Response deleteSubactivity(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("SubactivityController/deleteSubactivity", "/{id}", parametros,"sigeceUrl");
            request.delete();
            if(request.isError()){
             return new Response(false,request.getError(), "");
            }
            return new Response(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(SubactivityService.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the Subactivity", ex);
            return new Response(false, "An error ocurred while deleting the Subactivity.", "deleteSubactivity " + ex.getMessage());
        }
    }
    public Response getSubactivities() {
         try {
        Request request=new Request("SubactivityController/getSubactivities","sigeceUrl");
        request.get();

        if(request.isError()){
        return new Response(false,request.getError(),"");
        }
        List<SubactivityDto> subactivityDto = (List<SubactivityDto>) request.readEntity(new GenericType<List<SubactivityDto>>() {});
        return new Response(true,"","","Subactivities",subactivityDto);

        } catch (Exception ex) {
            Logger.getLogger(SubactivityService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the subactivities.", ex);
            return new Response(false, "An error ocurred while querying the subactivities.", "getSubactivities " + ex.getMessage());
        }
    }
}
