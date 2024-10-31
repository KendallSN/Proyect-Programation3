package cr.ac.una.sigeceunacomunicationws.controller;

import cr.ac.una.sigeceunacomunicationws.model.EmailsDto;
import cr.ac.una.sigeceunacomunicationws.service.EmailsService;
import cr.ac.una.sigeceunacomunicationws.utility.LocalResponse;
import cr.ac.una.sigeceunacomunicationws.utility.ResponseCode;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/EmailsController")
public class EmailsController {
    @EJB
    EmailsService emailsService;
    
    @POST
    @Path("/saveEmail")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveEmail(EmailsDto emailsDto) {
    try {
            LocalResponse res=emailsService.saveEmail(emailsDto);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok((EmailsDto)res.getResult("Email")).build();
        } catch (Exception ex) {
            Logger.getLogger(EmailsController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while saving the email.").build();
        }
    }
    
    @GET
    @Path("/getEmails")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getEmails() {
        try {
            LocalResponse res=emailsService.getEmails();
            if(!res.getState()){
              return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
        }
             return Response.ok(new GenericEntity<List<EmailsDto>>((List<EmailsDto>) res.getResult("Emails")) {
            }).build();
        } catch (Exception ex) {
        Logger.getLogger(NotificationProcessController.class.getName()).log(Level.SEVERE, null, ex);
        return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the emails.").build();
        }
    }
}
