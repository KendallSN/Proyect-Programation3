
package cr.ac.una.sigeceunamessaging.service;

import cr.ac.una.sigeceunamessaging.model.MessageDto;
import cr.ac.una.sigeceunamessaging.util.Request;
import cr.ac.una.sigeceunamessaging.util.Response;
import jakarta.ws.rs.core.GenericType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MessageService implements Serializable{
    public Response saveMessage(MessageDto messageDto){
        try {
            Request request = new Request("MessageController/saveMessage","comunicationUrl");
            request.post(messageDto);
            if(request.isError()){
             return new Response(false,request.getError(),"");
            }
            MessageDto message=(MessageDto)request.readEntity(MessageDto.class);
            return new Response(true, "", "", "Message",message);
        } catch (Exception ex) {
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, "An error ocurred while saving the message.", ex);
            return new Response(false, "An error ocurred while saving the message", "saveMessage " + ex.getMessage());
        }
    }
    public Response getMessagesByChat(Long idChat){
        try{
           Map<String,Object> parametros=new HashMap<>();
           parametros.put("idChat", idChat);
           Request request=new Request("MessageController/getMessagesByChat", "/{idChat}", parametros,"comunicationUrl");
           request.get();
           if(request.isError()){
               return new Response(false, request.getError(), "");
           }
           List<MessageDto> messageDto = (List<MessageDto>) request.readEntity(new GenericType<List<MessageDto>>() {});
           return new Response(true,"","","Messages",messageDto);

        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the messages.", ex);
            return new Response(false, "An error ocurred while querying the messages.", "getMessagesByChat " + ex.getMessage());
        }
    }
    public Response deleteMessage(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("MessageController/deleteMessage", "/{id}", parametros,"comunicationUrl");
            request.delete();
            if(request.isError()){
             return new Response(false,request.getError(), "");
            }
            return new Response(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the message", ex);
            return new Response(false, "An error ocurred while deleting the message.", "deleteMessage " + ex.getMessage());
        }
    }
    
}
