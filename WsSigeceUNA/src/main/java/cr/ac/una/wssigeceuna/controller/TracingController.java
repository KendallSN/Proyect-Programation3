package cr.ac.una.wssigeceuna.controller;

import cr.ac.una.wssigeceuna.model.TracingDto;
import cr.ac.una.wssigeceuna.service.TracingService;
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

@Path("/TracingController")
public class TracingController {
    @EJB
    TracingService tracingService;
    
    @GET
    @Path("/getTracings")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getTracings() {
        try {
            LocalResponse res = tracingService.getTracings();
            if(!res.getState()){
              return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            return Response.ok(new GenericEntity<List<TracingDto>>((List<TracingDto>) res.getResult("Tracings")) {
            }).build();
        } catch (Exception ex) {
        Logger.getLogger(TracingController.class.getName()).log(Level.SEVERE, null, ex);
        return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the Tracings.").build();
        }
    }
    
    @GET
    @Path("/getTracing/{ID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response getTracing(@PathParam("ID")Long id) {
        try {
            LocalResponse res = tracingService.getTracing(id);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            TracingDto tracingDto=(TracingDto)res.getResult("Tracing");
           return Response.ok(tracingDto).build();
        } catch (Exception ex) {
            Logger.getLogger(TracingController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the Tracing.").build();
        }
    }
    
    @POST
    @Path("/saveTracing")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveTracing(TracingDto tracingDto) {
    try {
            LocalResponse res = tracingService.saveTracing(tracingDto);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok((TracingDto)res.getResult("Tracing")).build();
        } catch (Exception ex) {
            Logger.getLogger(TracingController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while saving the tracing.").build();
        }
    }
    
    @DELETE
    @Path("/deleteTracing/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response deleteTracing(@PathParam("id")Long id) {
        try {
            LocalResponse res = tracingService.deleteTracing(id);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok().build();
        } catch (Exception ex) {
            Logger.getLogger(TracingController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error ocurred while deleting the Tracing").build();
        }
    }
}
