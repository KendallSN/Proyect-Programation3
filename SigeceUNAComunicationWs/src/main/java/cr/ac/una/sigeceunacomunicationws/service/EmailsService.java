package cr.ac.una.sigeceunacomunicationws.service;

import cr.ac.una.sigeceunacomunicationws.model.Emails;
import cr.ac.una.sigeceunacomunicationws.model.EmailsDto;
import cr.ac.una.sigeceunacomunicationws.model.NotificationProcess;
import cr.ac.una.sigeceunacomunicationws.model.NotificationProcessDto;
import cr.ac.una.sigeceunacomunicationws.utility.LocalResponse;
import cr.ac.una.sigeceunacomunicationws.utility.ResponseCode;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class EmailsService {
    private static final Logger LOG = Logger.getLogger(EmailsService.class.getName());
    @EJB
    private NotificationProcessService notificationProcessService;  
    @PersistenceContext(unitName="SigeceUNAComunicationWsPU")
    private EntityManager em;
    
    public LocalResponse saveEmail(EmailsDto emailsDto) {
        try {
            Emails emails;
            if(emailsDto.getEmlId()!=null&&emailsDto.getEmlId()>0){
                emails = em.find(Emails.class, emailsDto.getEmlId());
                if(emails == null){
                    return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "Email to update not found.", "saveEmail NoResultException");
                }
                emails.update(emailsDto);
                emails.setNtpId(new NotificationProcess((NotificationProcessDto) notificationProcessService.getNotificationProcess(emailsDto.getNtpId()).getResult("NotificationProcess")));
                emails = em.merge(emails);
            }else{
                emails = new Emails(emailsDto);
                emails.setNtpId(new NotificationProcess((NotificationProcessDto) notificationProcessService.getNotificationProcess(emailsDto.getNtpId()).getResult("NotificationProcess")));
                em.persist(emails);
            }
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Email", new EmailsDto(emails));
       } catch (Exception ex) {
           LOG.log(Level.SEVERE, "An error ocurred while saving the email.", ex);
           return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while saving the email.", "saveEmail" + ex.getMessage());
       }
    }
    
    public LocalResponse getEmails(){
        try{
            Query qryEmails = em.createNamedQuery("Emails.findAll");
            List<Emails> emails = qryEmails.getResultList();
            List<EmailsDto> emailsDto = new ArrayList<>();
            for (Emails email:emails) {
                emailsDto.add(new EmailsDto(email));
            }
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Emails", emailsDto);
        }catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "There are no emails.", "getEmails NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while consulting the emails.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while consulting the emails.", "getEmails " + ex.getMessage());
        }
    }
}
