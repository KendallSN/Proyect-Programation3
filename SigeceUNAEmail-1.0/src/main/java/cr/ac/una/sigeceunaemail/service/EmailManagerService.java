package cr.ac.una.sigeceunaemail.service;

import cr.ac.una.sigeceunaemail.model.EmailmanagerDto;
import cr.ac.una.sigeceunaemail.util.Request;
import cr.ac.una.sigeceunaemail.util.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailManagerService {
    public Response saveEmailManager(EmailmanagerDto emailmanagerDto){
        try {
            Request request = new Request("EmailManagerController/saveEmailManager","comunicationUrl");
            request.post(emailmanagerDto);
            if(request.isError()){
             return new Response(false,request.getError(),"");
            }
            EmailmanagerDto emailManager = (EmailmanagerDto) request.readEntity(EmailmanagerDto.class);
            return new Response(true, "", "", "EmailManager",emailManager);
        } catch (Exception ex) {
            Logger.getLogger(NotificationProcessService.class.getName()).log(Level.SEVERE, "An error ocurred while saving the EmailManager.", ex);
            return new Response(false, "An error ocurred while saving the email", "saveEmailManager " + ex.getMessage());
        }
    }
    
    public Response getEmailManager(Long id){
        try{
           Map<String,Object> parametros=new HashMap<>();
           parametros.put("id", id);
           Request request=new Request("EmailManagerController/getEmailManager", "/{id}", parametros,"comunicationUrl");
           request.get();
           if(request.isError()){
               return new Response(false, request.getError(), "");
           }
           EmailmanagerDto emailmanagerDto=(EmailmanagerDto)request.readEntity(EmailmanagerDto.class);
            return new Response(true,"","", "EmailManager",emailmanagerDto);
        } catch (Exception ex) {
            Logger.getLogger(NotificationProcessService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the EmailManager [" + id +"]", ex);
            return new Response(false, "An error ocurred while querying the EmailManager", "getEmailManager " + ex.getMessage());
        }
    }
}
