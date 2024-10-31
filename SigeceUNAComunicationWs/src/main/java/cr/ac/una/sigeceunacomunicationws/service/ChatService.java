
package cr.ac.una.sigeceunacomunicationws.service;

import cr.ac.una.sigeceunacomunicationws.model.Chat;
import cr.ac.una.sigeceunacomunicationws.model.ChatDto;
import cr.ac.una.sigeceunacomunicationws.model.MessageDto;
import cr.ac.una.sigeceunacomunicationws.model.User;
import cr.ac.una.sigeceunacomunicationws.model.UserDto;
import cr.ac.una.sigeceunacomunicationws.utility.LocalResponse;
import cr.ac.una.sigeceunacomunicationws.utility.ResponseCode;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class ChatService {
    private static final Logger LOG = Logger.getLogger(ChatService.class.getName());
    @EJB
    private UserService userService;
    @EJB
    private MessageService messageService;
    @PersistenceContext(unitName="SigeceUNAComunicationWsPU")
    private EntityManager em; 
    public LocalResponse getChatByID(Long ID) {
        try {
            Query qryChat = em.createNamedQuery("Chat.findByChtId", Chat.class);
            qryChat.setParameter("chtId", ID);
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Chat", new ChatDto((Chat) qryChat.getSingleResult()));
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "Chat with the ID does not exist.", "getChatByID NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the chat.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the chat.", "getChatByID NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the chat.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the chat.", "getChatByID " + ex.getMessage());
        }
    }
    public LocalResponse saveChat(ChatDto chatDto) {
        try {
            Chat chat;                      
            chat = new Chat(chatDto);
            UserDto userDto1 = (UserDto) userService.getUserByID(chatDto.getUsrIdUser1()).getResult("User");
            UserDto userDto2 = (UserDto) userService.getUserByID(chatDto.getUsrIdUser2()).getResult("User");   
            User user1=new User(userDto1);
            User user2=new User(userDto2);
            chat.setUsrIdUser1(user1);
            chat.setUsrIdUser2(user2);
            em.persist(chat);         
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Chat", new ChatDto(chat));
       } catch (Exception ex) {
           LOG.log(Level.SEVERE, "An error ocurred while saving the chat.", ex);
           return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while saving the chat.", "saveChat " + ex.getMessage());
       }
    }
    public LocalResponse getChats(){
           try {
            Query qryChats = em.createNamedQuery("Chat.findAll", Chat.class);
            List<Chat> chats = qryChats.getResultList();
            List<ChatDto> chatsDto = new ArrayList<>();
            for (Chat chat : chats) {
                chatsDto.add(new ChatDto(chat));
            }
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Chats", chatsDto);
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "There are no chats.", "getChats NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the chats.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the chats.", "getChats " + ex.getMessage());
        }
    }
    public LocalResponse getChatByUsers(Long idUser1,Long idUser2) {
        try {
            UserDto userDto1 = (UserDto) userService.getUserByID(idUser1).getResult("User");
            UserDto userDto2 = (UserDto) userService.getUserByID(idUser2).getResult("User");   
            User user1=new User(userDto1);
            User user2=new User(userDto2);
            Query qryChat = em.createNamedQuery("Chat.findChatByUsers", Chat.class);
            qryChat.setParameter("usrIdUser1", user1);
            qryChat.setParameter("usrIdUser2", user2);
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Chat", new ChatDto((Chat) qryChat.getSingleResult()));
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "Chat with the user1 and user2 does not exist.", "getChatByUsers NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the chat.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the chat.", "getChatByUsers NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the chat.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the chat.", "getChatByUsers " + ex.getMessage());
        }
    }
    public LocalResponse deleteChat(Long id) {
        try {
            Chat chat;
            if (id != null && id > 0) {
                List<MessageDto> messages=(List<MessageDto>)messageService.getMessagesByChat(id).getResult("Messages");
                    for(MessageDto messageDto:messages){
                    messageService.deleteMessage(messageDto.getMsgId());
                    }          
                chat = em.find(Chat.class, id);
                if (chat == null) {
                    return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "The chat to delete was not found.", "deleteChat NoResultException");
                }
                em.remove(chat);
            } else {
                return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "You have to load the chat to delete.", "deleteChat NoResultException");
            }
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "");
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "Can not delete the chat because it has relations with other registers.", "deleteChat " + ex.getMessage());
            }
            LOG.log(Level.SEVERE, "An error ocurred while deleting the chat.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error ocurred while deleting the chat.", "deleteChat " + ex.getMessage());
        }
    }
    
    public LocalResponse deleteChatsByUser(Long id) {
        try {     
            UserDto userDto=(UserDto)userService.getUserByID(id).getResult("User");
            if(userDto.getUsrId()==null){
                return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "The chat to delete was not found.", "deleteChat NoResultException");
            }
            List<ChatDto>chats=(List<ChatDto>)getChats().getResult("Chats");
            if(chats.isEmpty()){
                return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "The chat to delete was not found.", "deleteChat NoResultException");
            }
            for(ChatDto chat:chats){
                if(chat.getUsrIdUser1()==userDto.getUsrId()||chat.getUsrIdUser2()==userDto.getUsrId()){
                    
                    deleteChat(chat.getChtId());
                }
            }
            return new LocalResponse(true, ResponseCode.CORRECT, "", "");
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "Can not delete the chat because it has relations with other registers.", "deleteChat " + ex.getMessage());
            }
            LOG.log(Level.SEVERE, "An error ocurred while deleting the chat.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error ocurred while deleting the chat.", "deleteChat " + ex.getMessage());
        }
    }
    
}
