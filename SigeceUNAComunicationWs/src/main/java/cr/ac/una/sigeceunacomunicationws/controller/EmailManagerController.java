package cr.ac.una.sigeceunacomunicationws.controller;

import cr.ac.una.sigeceunacomunicationws.model.EmailmanagerDto;
import cr.ac.una.sigeceunacomunicationws.service.EmailManagerService;
import cr.ac.una.sigeceunacomunicationws.utility.LocalResponse;
import cr.ac.una.sigeceunacomunicationws.utility.ResponseCode;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;
@Path("/EmailManagerController")
public class EmailManagerController {
    @EJB
    EmailManagerService emailManagerService;
    
    @POST
    @Path("/saveEmailManager")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveEmailManager(EmailmanagerDto emailManagerDto) {
        try {
            LocalResponse res=emailManagerService.saveEmailManager(emailManagerDto);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok((EmailmanagerDto)res.getResult("EmailManager")).build();
        } catch (Exception ex) {
            Logger.getLogger(EmailManagerController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while saving the EmailManager.").build();
        }
    }
    
        @GET
    @Path("/getEmailManager/{ID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response getUser(@PathParam("ID")Long id) {
        try {
            LocalResponse res=emailManagerService.getEmailManager(id);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            EmailmanagerDto emailManagerDto=(EmailmanagerDto)res.getResult("EmailManager");
           return Response.ok(emailManagerDto).build();
        } catch (Exception ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the user.").build();
        }
    }
}
