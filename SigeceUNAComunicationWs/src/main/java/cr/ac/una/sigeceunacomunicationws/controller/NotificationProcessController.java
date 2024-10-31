
package cr.ac.una.sigeceunacomunicationws.controller;

import cr.ac.una.sigeceunacomunicationws.model.NotificationProcessDto;
import cr.ac.una.sigeceunacomunicationws.service.NotificationProcessService;
import cr.ac.una.sigeceunacomunicationws.utility.LocalResponse;
import cr.ac.una.sigeceunacomunicationws.utility.ResponseCode;
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

@Path("/NotificationProcessController")
public class NotificationProcessController {
    @EJB
    NotificationProcessService notificationProcessService;
    @GET
    @Path("/getNotificationProcess/{ID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response getNotificationProcess(@PathParam("ID")Long id) {
        try {
            LocalResponse res=notificationProcessService.getNotificationProcess(id);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            NotificationProcessDto notificationProcessDto=(NotificationProcessDto)res.getResult("NotificationProcess");
           return Response.ok(notificationProcessDto).build();
        } catch (Exception ex) {
            Logger.getLogger(NotificationProcessController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the notificationProcess.").build();
        }
    }
    @POST
    @Path("/saveNotificationProcess")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveNotificationProcess(NotificationProcessDto notificationProcessDto) {
    try {
            LocalResponse res=notificationProcessService.saveNotificationProcess(notificationProcessDto);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok((NotificationProcessDto)res.getResult("NotificationProcess")).build();
        } catch (Exception ex) {
            Logger.getLogger(NotificationProcessController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while saving the notificationProcess.").build();
        }
    }
    @DELETE
    @Path("/deleteNotificationProcess/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response deleteNotificationProcess(@PathParam("id")Long id) {
        try {
            LocalResponse res=notificationProcessService.deleteNotificationProcess(id);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok().build();
        } catch (Exception ex) {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error ocurred while deleting the notificationProcess").build();
        }
    }
    @GET
    @Path("/getNotificationProcesses")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getNotificationProcesses() {
        try {
            LocalResponse res=notificationProcessService.getNotificationProcesses();
            if(!res.getState()){
              return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
        }
             return Response.ok(new GenericEntity<List<NotificationProcessDto>>((List<NotificationProcessDto>) res.getResult("NotificationProcesses")) {
            }).build();

        } catch (Exception ex) {
        Logger.getLogger(NotificationProcessController.class.getName()).log(Level.SEVERE, null, ex);
        return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the notificationProcesses.").build();

        }
    }
}
