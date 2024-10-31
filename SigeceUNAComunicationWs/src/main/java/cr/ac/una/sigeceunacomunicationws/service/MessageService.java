
package cr.ac.una.sigeceunacomunicationws.service;

import cr.ac.una.sigeceunacomunicationws.model.Chat;
import cr.ac.una.sigeceunacomunicationws.model.ChatDto;
import cr.ac.una.sigeceunacomunicationws.model.FileDto;
import cr.ac.una.sigeceunacomunicationws.model.Message;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class MessageService {
    private static final Logger LOG = Logger.getLogger(ChatService.class.getName());
    @EJB
    private UserService userService;
    @EJB
    private ChatService chatService;
    @EJB
    private FileService fileService;
    List<FileDto>files;
    @PersistenceContext(unitName="SigeceUNAComunicationWsPU")
    private EntityManager em;
    public LocalResponse saveMessage(MessageDto messageDto) {
        try {
            Message message;                      
            message = new Message(messageDto);
            ChatDto chatDto=(ChatDto)chatService.getChatByID(messageDto.getChtId()).getResult("Chat");
            UserDto userDto=(UserDto)userService.getUserByID(messageDto.getUsrIdSender()).getResult("User");
            Chat chat=new Chat(chatDto);
            User user=new User(userDto);
            message.setChtId(chat);
            message.setUsrIdSender(user);
            em.persist(message);         
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Message", new MessageDto(message));
       } catch (Exception ex) {
           LOG.log(Level.SEVERE, "An error ocurred while saving the message.", ex);
           return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while saving the message.", "saveMessage " + ex.getMessage());
       }
    }
    public LocalResponse getMessagesByChat(Long idChat) {
        try {
            ChatDto chatDto = (ChatDto) chatService.getChatByID(idChat).getResult("Chat");   
            Chat chat=new Chat(chatDto);
            Query qryMessages = em.createNamedQuery("Message.findByChat", Message.class);
            qryMessages.setParameter("chtId", chat);
            List<Message> messages = qryMessages.getResultList();
            List<MessageDto> messagesDto = new ArrayList<>();
            for (Message message : messages) {
                messagesDto.add(new MessageDto(message));
            }
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Messages", messagesDto);
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "There are no messages.", "getMessagesByChat NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the messages.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the messages.", "getMessagesByChat " + ex.getMessage());
        }
    }
    
    public LocalResponse getMessageByID(Long ID) {
        try {
            Query qryMessage = em.createNamedQuery("Message.findByMsgId", Message.class);
            qryMessage.setParameter("msgId", ID);
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Message", new MessageDto((Message) qryMessage.getSingleResult()));
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "Message with the ID does not exist.", "getMessageByID NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the message.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the message.", "getMessageByID NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the message.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the message.", "getMessageByID " + ex.getMessage());
        }
    }
    
    public LocalResponse deleteMessage(Long id) {
        try {
            Message message;
            if (id != null && id > 0) {
                MessageDto messageDto = (MessageDto) getMessageByID(id).getResult("Message");
                if(messageDto!=null){
                    if(!messageDto.getFileCollection().isEmpty() && messageDto.getFileCollection()!=null){
                        files = new ArrayList<>();
                        files.addAll(messageDto.getFileCollection());
                    }
                }
                message = em.find(Message.class, id);
                if (message == null) {
                    return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "The message to delete was not found.", "deleteMessage NoResultException");
                }
                em.remove(message);
            } else {
                return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "You have to load the message to delete.", "deleteMessage NoResultException");
            }
            em.flush();
            if(!files.isEmpty() || files!=null){
                for(FileDto fileDto : files){
                fileService.deleteFile(fileDto.getFleId());
                }
            }
            return new LocalResponse(true, ResponseCode.CORRECT, "", "");
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "Can not delete the message because it has relations with other registers.", "deleteMessage " + ex.getMessage());
            }
            LOG.log(Level.SEVERE, "An error ocurred while deleting the message.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error ocurred while deleting the message.", "deleteMessage " + ex.getMessage());
        }
    }     
}
