package cr.ac.una.sigeceunaemail.service;

import cr.ac.una.sigeceunaemail.model.EmailsDto;
import cr.ac.una.sigeceunaemail.util.Request;
import cr.ac.una.sigeceunaemail.util.Response;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailsService {
    
    public Response saveEmail(EmailsDto emailsDto){
        try {
            Request request = new Request("EmailsController/saveEmail","comunicationUrl");
            request.post(emailsDto);
            if(request.isError()){
             return new Response(false,request.getError(),"");
            }
            EmailsDto email = (EmailsDto) request.readEntity(EmailsDto.class);
            return new Response(true, "", "", "Email",email);
        } catch (Exception ex) {
            Logger.getLogger(NotificationProcessService.class.getName()).log(Level.SEVERE, "An error ocurred while saving the email.", ex);
            return new Response(false, "An error ocurred while saving the email", "saveEmail " + ex.getMessage());
        }
    }
    
    public Response getEmails(){
        try{
           Map<String,Object> parametros=new HashMap<>();
           Request request=new Request("EmailsController/getEmails","comunicationUrl");
           request.get();
           if(request.isError()){
               return new Response(false, request.getError(), "");
           }
           List<EmailsDto> emails = (List<EmailsDto>) request.readEntity(new GenericType<List<EmailsDto>>() {});
           return new Response(true,"","","Emails",emails);

        } catch (Exception ex) {
            Logger.getLogger(NotificationProcessService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the emails.", ex);
            return new Response(false, "An error ocurred while querying the emails.", "getEmails " + ex.getMessage());
        }
    }
    
    public Response sendEmail(String to,String subject, String body, String username, String password, String port){
        try{
           Map<String,Object> parametros=new HashMap<>();
           parametros.put("to", to);
           parametros.put("subject", subject);
           parametros.put("body", body);
           parametros.put("username", username);
           parametros.put("password", password);
           parametros.put("port", port);
           Request request=new Request("EmailController/sendEmail", "/{to}/{subject}/{body}/{username}/{password}/{port}", parametros,"comunicationUrl");
           request.get();
           if(request.isError()){
               return new Response(false, request.getError(), "");
           }
            return new Response(true,"","");
        } catch (Exception ex) {
            Logger.getLogger(NotificationProcessService.class.getName()).log(Level.SEVERE, "An error ocurred while sending the email ", ex);
            return new Response(false, "An error ocurred while sending the email", "sendEmail " + ex.getMessage());
        }
    }
}
