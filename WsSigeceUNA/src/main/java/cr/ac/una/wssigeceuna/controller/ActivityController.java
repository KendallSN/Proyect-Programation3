package cr.ac.una.wssigeceuna.controller;

import cr.ac.una.wssigeceuna.model.ActivityDto;
import cr.ac.una.wssigeceuna.service.ActivityService;
import cr.ac.una.wssigeceuna.utility.LocalResponse;
import cr.ac.una.wssigeceuna.utility.ResponseCode;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/ActivityController")
public class ActivityController {
    @EJB
    ActivityService activityService;
    
    @GET
    @Path("/getActivities")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getActivities() {
        try {
            LocalResponse res = activityService.getActivities();
            if(!res.getState()){
              return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            return Response.ok(new GenericEntity<List<ActivityDto>>((List<ActivityDto>) res.getResult("Activities")) {
            }).build();
        } catch (Exception ex) {
            Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the subactivities.").build();
        }
    }
    
    @GET
    @Path("/getActivitiesByArea/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getActivitiesByArea(@PathParam("id")Long id) {
        try {
            LocalResponse res = activityService.getActivitiesByArea(id);
            if(!res.getState()){
              return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            return Response.ok(new GenericEntity<List<ActivityDto>>((List<ActivityDto>) res.getResult("Activities")) {
            }).build();
        } catch (Exception ex) {
            Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the subactivities by area.").build();
        }
    }
    
    @GET
    @Path("/getActivity/{ID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response getActivity(@PathParam("ID")Long id) {
        try {
            LocalResponse res=activityService.getActivity(id);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            ActivityDto activityDto=(ActivityDto)res.getResult("Activity");
           return Response.ok(activityDto).build();
        } catch (Exception ex) {
            Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the activity.").build();
        }
    }
    
    @POST
    @Path("/saveActivity")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveActivity(ActivityDto activityDto) {
    try {
            LocalResponse res = activityService.saveActivity(activityDto);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok((ActivityDto)res.getResult("Activity")).build();
        } catch (Exception ex) {
            Logger.getLogger(AreaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while saving the activity.").build();
        }
    }
    
    @DELETE
    @Path("/deleteActivity/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response deleteActivity(@PathParam("id")Long id) {
        try {
            LocalResponse res=activityService.deleteActivity(id);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok().build();
        } catch (Exception ex) {
            Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error ocurred while deleting the Activity").build();
        }
    }
}
