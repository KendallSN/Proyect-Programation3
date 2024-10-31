package cr.ac.una.sigeceunacomunicationws.service;

import cr.ac.una.sigeceunacomunicationws.utility.LocalResponse;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.logging.Logger;
import cr.ac.una.sigeceunacomunicationws.model.EmailmanagerDto;
import cr.ac.una.sigeceunacomunicationws.model.Emailmanager;
import cr.ac.una.sigeceunacomunicationws.utility.ResponseCode;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import java.util.logging.Level;

@Stateless
@LocalBean
public class EmailManagerService {
    
    private static final Logger LOG = Logger.getLogger(EmailsService.class.getName()); 
    @PersistenceContext(unitName="SigeceUNAComunicationWsPU")
    private EntityManager em;
    
    public LocalResponse saveEmailManager(EmailmanagerDto emailmanagerDto) {
        try {
            Emailmanager emailmanager;
            if(emailmanagerDto.getEmmId()!=null&&emailmanagerDto.getEmmId()>0){
                emailmanager = em.find(Emailmanager.class, emailmanagerDto.getEmmId());
                if(emailmanager == null){
                    return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "EmailManager to update not found.", "saveEmailManager NoResultException");
                }
                emailmanager.update(emailmanagerDto);
                emailmanager = em.merge(emailmanager);
            }else{
                emailmanager = new Emailmanager(emailmanagerDto);
                emailmanager.setEmmId(Long.valueOf(1));
                em.persist(emailmanager);
            }
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "EmailManager", new EmailmanagerDto(emailmanager));
        } catch (Exception ex) {
           LOG.log(Level.SEVERE, "An error ocurred while saving the EmailManager.", ex);
           return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while saving the EmailManager.", "saveEmailManager" + ex.getMessage());
       }
    }
    
    public LocalResponse getEmailManager(Long ID) {
        try {
            Query qryChat = em.createNamedQuery("Emailmanager.findByEmmId", Emailmanager.class);
            qryChat.setParameter("emmId", ID);
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "EmailManager", new EmailmanagerDto((Emailmanager) qryChat.getSingleResult()));
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "EmailManager with the ID does not exist.", "getEmailManager NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the EmailManager.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the EmailManager.", "getEmailManager NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the chat.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the EmailManager.", "getEmailManager " + ex.getMessage());
        }            
    }
}
