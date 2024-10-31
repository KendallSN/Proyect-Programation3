
package cr.ac.una.sigeceunacomunicationws.controller;

import cr.ac.una.sigeceunacomunicationws.model.MessageDto;
import cr.ac.una.sigeceunacomunicationws.service.MessageService;
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

@Path("/MessageController")
public class MessageController {
    @EJB
    MessageService messageService;
    @POST
    @Path("/saveMessage")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveMessage(MessageDto messageDto) {
    try {
            LocalResponse res=messageService.saveMessage(messageDto);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok((MessageDto)res.getResult("Message")).build();
        } catch (Exception ex) {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while saving the message.").build();
        }
    }
    @GET
    @Path("/getMessagesByChat/{idChat}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response getMessagesByChat(@PathParam("idChat")Long idChat) {
        try {
            LocalResponse res=messageService.getMessagesByChat(idChat);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            return Response.ok(new GenericEntity<List<MessageDto>>((List<MessageDto>) res.getResult("Messages")) {
            }).build();

        } catch (Exception ex) {
        Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
        return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the messages.").build();
        }
    }
    @DELETE
    @Path("/deleteMessage/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response deleteMessage(@PathParam("id")Long id) {
        try {
            LocalResponse res=messageService.deleteMessage(id);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok().build();
        } catch (Exception ex) {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error ocurred while deleting the message").build();
        }
    }
}
