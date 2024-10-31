package cr.ac.una.wssigeceuna.controller;

import cr.ac.una.wssigeceuna.model.SubactivityDto;
import cr.ac.una.wssigeceuna.service.SubactivityService;
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

@Path("/SubactivityController")
public class SubactivityController {
    @EJB
    SubactivityService subactivityService;
    
    @GET
    @Path("/getSubactivities")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getSubactivities() {
        try {
            LocalResponse res = subactivityService.getSubactivities();
            if(!res.getState()){
              return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            return Response.ok(new GenericEntity<List<SubactivityDto>>((List<SubactivityDto>) res.getResult("Subactivities")) {
            }).build();
        } catch (Exception ex) {
            Logger.getLogger(SubactivityController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the subactivities.").build();
        }
    }
    
    @GET
    @Path("/getSubactivity/{ID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response getSubactivity(@PathParam("ID")Long id) {
        try {
            LocalResponse res=subactivityService.getSubactivity(id);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            SubactivityDto subactivityDto=(SubactivityDto)res.getResult("Subactivity");
           return Response.ok(subactivityDto).build();
        } catch (Exception ex) {
            Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the subactivity.").build();
        }
    }
    
    @GET
    @Path("/getSubactivitiesByActivity/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getSubactivitiesByActivity(@PathParam("id")Long id) {
        try {
            LocalResponse res = subactivityService.getSubactivitiesByActivity(id);
            if(!res.getState()){
              return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            return Response.ok(new GenericEntity<List<SubactivityDto>>((List<SubactivityDto>) res.getResult("Subactivities")) {
            }).build();
        } catch (Exception ex) {
            Logger.getLogger(SubactivityController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the subactivities by area.").build();
        }
    }
    
    @POST
    @Path("/saveSubactivity")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveSubactivity(SubactivityDto subactivityDto) {
    try {
            LocalResponse res = subactivityService.saveSubactivity(subactivityDto);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok((SubactivityDto)res.getResult("Subactivity")).build();
        } catch (Exception ex) {
            Logger.getLogger(SubactivityController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while saving the subactivity.").build();
        }
    }
    
    @DELETE
    @Path("/deleteSubactivity/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response deleteSubactivity(@PathParam("id")Long id) {
        try {
            LocalResponse res=subactivityService.deleteSubactivity(id);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok().build();
        } catch (Exception ex) {
            Logger.getLogger(SubactivityController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error ocurred while deleting the Subactivity").build();
        }
    }
}
