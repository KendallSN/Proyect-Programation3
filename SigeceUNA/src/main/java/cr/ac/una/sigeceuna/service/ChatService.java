
package cr.ac.una.sigeceuna.service;

import cr.ac.una.sigeceuna.model.ChatDto;
import cr.ac.una.sigeceuna.util.Request;
import cr.ac.una.sigeceuna.util.Response;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ChatService implements Serializable {
   public Response saveChat(ChatDto chatDto){
        try {
            Request request = new Request("ChatController/saveChat","comunicationUrl");
            request.post(chatDto);
            if(request.isError()){
             return new Response(false,request.getError(),"");
            }
            ChatDto chat=(ChatDto)request.readEntity(ChatDto.class);
            return new Response(true, "", "", "Chat",chat);
        } catch (Exception ex) {
            Logger.getLogger(ChatService.class.getName()).log(Level.SEVERE, "An error ocurred while saving the chat.", ex);
            return new Response(false, "An error ocurred while saving the chat", "saveChat " + ex.getMessage());
        }
    } 
   public Response getChatByUsers(Long idUser1,Long idUser2){
        try{
           Map<String,Object> parametros=new HashMap<>();
           parametros.put("idUser1", idUser1);
           parametros.put("idUser2", idUser2);
           Request request=new Request("ChatController/getChatByUsers", "/{idUser1}/{idUser2}", parametros,"comunicationUrl");
           request.get();
           if(request.isError()){
               return new Response(false, request.getError(), "");
           }
           ChatDto chatDto=(ChatDto)request.readEntity(ChatDto.class);
            return new Response(true,"","", "Chat",chatDto);
        } catch (Exception ex) {
            Logger.getLogger(ChatService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the chat [" + idUser1 + " "+ idUser2+ "]", ex);
            return new Response(false, "An error ocurred while querying the chat", "getChatByUsers " + ex.getMessage());
        } 
    }
   public Response deleteChat(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("ChatController/deleteChat", "/{id}", parametros,"comunicationUrl");
            request.delete();
            if(request.isError()){
             return new Response(false,request.getError(), "");
            }
            return new Response(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(ChatService.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the chat", ex);
            return new Response(false, "An error ocurred while deleting the chat.", "deleteChat " + ex.getMessage());
        }
    }
}
