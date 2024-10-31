
package cr.ac.una.sigeceunaemail.service;

import cr.ac.una.sigeceunaemail.model.NotificationProcessDto;
import cr.ac.una.sigeceunaemail.util.Request;
import cr.ac.una.sigeceunaemail.util.Response;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NotificationProcessService {
    public Response saveNotificationProcess(NotificationProcessDto notificationProcessDto){
        try {
            Request request = new Request("NotificationProcessController/saveNotificationProcess","comunicationUrl");
            request.post(notificationProcessDto);
            if(request.isError()){
             return new Response(false,request.getError(),"");
            }
            NotificationProcessDto notificationProcess=(NotificationProcessDto)request.readEntity(NotificationProcessDto.class);
            return new Response(true, "", "", "NotificationProcess",notificationProcess);
        } catch (Exception ex) {
            Logger.getLogger(NotificationProcessService.class.getName()).log(Level.SEVERE, "An error ocurred while saving the notificationProcess.", ex);
            return new Response(false, "An error ocurred while saving the notificationProcess", "saveNotificationProcess " + ex.getMessage());
        }
    }
    public Response getNotificationProcess(Long id){
        try{
           Map<String,Object> parametros=new HashMap<>();
           parametros.put("id", id);
           Request request=new Request("NotificationProcessController/getNotificationProcess", "/{id}", parametros,"comunicationUrl");
           request.get();
           if(request.isError()){
               return new Response(false, request.getError(), "");
           }
           NotificationProcessDto notificationProcessDto=(NotificationProcessDto)request.readEntity(NotificationProcessDto.class);
            return new Response(true,"","", "NotificationProcess",notificationProcessDto);
        } catch (Exception ex) {
            Logger.getLogger(NotificationProcessService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the notificationProcess [" + id +"]", ex);
            return new Response(false, "An error ocurred while querying the notificationProcess", "getNotificationProcess " + ex.getMessage());
        }
    }
    public Response deleteNotificationProcess(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("NotificationProcessController/deleteNotificationProcess", "/{id}", parametros,"comunicationUrl");
            request.delete();
            if(request.isError()){
             return new Response(false,request.getError(), "");
            }
            return new Response(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(NotificationProcessService.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the notificationProcess", ex);
            return new Response(false, "An error ocurred while deleting the notificationProcess.", "deleteNotificationProcess " + ex.getMessage());
        }
    }
    public Response getNotificationProcesses(){
        try{
           Map<String,Object> parametros=new HashMap<>();
           Request request=new Request("NotificationProcessController/getNotificationProcesses","comunicationUrl");
           request.get();
           if(request.isError()){
               return new Response(false, request.getError(), "");
           }
           List<NotificationProcessDto> notificationProcessDto = (List<NotificationProcessDto>) request.readEntity(new GenericType<List<NotificationProcessDto>>() {});
           return new Response(true,"","","NotificationProcesses",notificationProcessDto);

        } catch (Exception ex) {
            Logger.getLogger(NotificationProcessService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the notificationProcesses.", ex);
            return new Response(false, "An error ocurred while querying the notificationProcesses.", "getNotificationProcesses " + ex.getMessage());
        }
    }
}
