
package cr.ac.una.sigeceuna.service;

import cr.ac.una.sigeceuna.model.ActivityDto;
import cr.ac.una.sigeceuna.util.Request;
import cr.ac.una.sigeceuna.util.Response;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author IK
 */
public class ActivityService {
    public Response getActivity(Long id){
        try{
           Map<String,Object> parametros=new HashMap<>();
           parametros.put("id", id);
           Request request=new Request("ActivityController/getActivity", "/{id}", parametros,"sigeceUrl");
           request.get();
           if(request.isError()){
               return new Response(false, request.getError(), "");
           }
           ActivityDto activityDto=(ActivityDto)request.readEntity(ActivityDto.class);
            return new Response(true,"","", "Activity",activityDto);
        } catch (Exception ex) {
            Logger.getLogger(ActivityService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the activity [" + id +"]", ex);
            return new Response(false, "An error ocurred while querying the activity", "getActivity " + ex.getMessage());
        }
    }
    public Response saveActivity(ActivityDto activityDto){
        try {
            Request request = new Request("ActivityController/saveActivity","sigeceUrl");
            request.post(activityDto);
            if(request.isError()){
             return new Response(false,request.getError(),"");
            }
            ActivityDto activity=(ActivityDto)request.readEntity(ActivityDto.class);
            return new Response(true, "", "", "Activity",activity);
        } catch (Exception ex) {
            Logger.getLogger(ActivityService.class.getName()).log(Level.SEVERE, "An error ocurred while saving the activity.", ex);
            return new Response(false, "An error ocurred while saving the activtity", "saveActivity " + ex.getMessage());
        }
    }
    public Response getActivitiesByArea(Long ID_Area){
        try{
           Map<String,Object> parametros=new HashMap<>();
           parametros.put("are_ID", ID_Area);
           Request request=new Request("ActivityController/getActivitiesByArea", "/{are_ID}", parametros,"sigeceUrl");
           request.get();
           if(request.isError()){
               return new Response(false, request.getError(), "");
           }
           List<ActivityDto> activitiesDto = (List<ActivityDto>) request.readEntity(new GenericType<List<ActivityDto>>() {});
           return new Response(true,"","","Activities",activitiesDto);

        } catch (Exception ex) {
            Logger.getLogger(ActivityService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the activities.", ex);
            return new Response(false, "An error ocurred while querying the activities.", "getActivitiesByArea " + ex.getMessage());
        }
    }
    public Response deleteActivity(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("ActivityController/deleteActivity", "/{id}", parametros,"sigeceUrl");
            request.delete();
            if(request.isError()){
             return new Response(false,request.getError(), "");
            }
            return new Response(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(ActivityService.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the Activity", ex);
            return new Response(false, "An error ocurred while deleting the Activity.", "deleteActivity " + ex.getMessage());
        }
    }
    public Response getActivities() {
         try {
        Request request=new Request("ActivityController/getActivities","sigeceUrl");
        request.get();

        if(request.isError()){
        return new Response(false,request.getError(),"");
        }
        List<ActivityDto> activityDto = (List<ActivityDto>) request.readEntity(new GenericType<List<ActivityDto>>() {});
        return new Response(true,"","","Activities",activityDto);

        } catch (Exception ex) {
            Logger.getLogger(ActivityService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the activities.", ex);
            return new Response(false, "An error ocurred while querying the activities.", "getActivities " + ex.getMessage());
        }
    }

}
