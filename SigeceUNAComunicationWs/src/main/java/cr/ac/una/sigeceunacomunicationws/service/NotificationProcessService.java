
package cr.ac.una.sigeceunacomunicationws.service;

import cr.ac.una.sigeceunacomunicationws.model.NotificationProcess;
import cr.ac.una.sigeceunacomunicationws.model.NotificationProcessDto;
import cr.ac.una.sigeceunacomunicationws.model.VariableDto;
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
public class NotificationProcessService {
   private static final Logger LOG = Logger.getLogger(NotificationProcessService.class.getName());
   @EJB
   VariableService variableService;
   @PersistenceContext(unitName="SigeceUNAComunicationWsPU")
   private EntityManager em;
   public LocalResponse getNotificationProcess(Long ID) {
        try {
            Query qryNotificationProcess = em.createNamedQuery("NotificationProcess.findByNtpId", NotificationProcess.class);
            qryNotificationProcess.setParameter("ntpId", ID);
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "NotificationProcess", new NotificationProcessDto((NotificationProcess) qryNotificationProcess.getSingleResult()));
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "NotificationProcess with the ID does not exist.", "getNotificationProcess NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the NotificationProcess.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the NotificationProcess.", "getNotificationProcess NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the NotificationProcess.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the NotificationProcess.", "getNotificationProcess " + ex.getMessage());
        }
    }
   public LocalResponse saveNotificationProcess(NotificationProcessDto notificationProcessDto) {
        try {
           NotificationProcess notificationProcess;
           if (notificationProcessDto.getNtpId()!= null && notificationProcessDto.getNtpId()> 0) {
               notificationProcess = em.find(NotificationProcess.class, notificationProcessDto.getNtpId());
               if (notificationProcess == null) {
                   return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "NotificationProcess to update not found.", "saveNotificationProcess NoResultException");
               }
               notificationProcess.update(notificationProcessDto);
               notificationProcess = em.merge(notificationProcess);
           } else {
               notificationProcess = new NotificationProcess(notificationProcessDto);
               em.persist(notificationProcess);
           }
           em.flush();
           return new LocalResponse(true, ResponseCode.CORRECT, "", "", "NotificationProcess", new NotificationProcessDto(notificationProcess));
       } catch (Exception ex) {
           LOG.log(Level.SEVERE, "An error ocurred while saving the notificationProcess.", ex);
           return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while saving the notificationProcess.", "saveNotificationProcess " + ex.getMessage());
       }
    }
   public LocalResponse getNotificationProcesses(){
           try {
            Query qryNotificationProcesses = em.createNamedQuery("NotificationProcess.findAll", NotificationProcess.class);
            List<NotificationProcess> notificationProcesses = qryNotificationProcesses.getResultList();
            List<NotificationProcessDto> notificationProcessesDto = new ArrayList<>();
            for (NotificationProcess notificationProcess : notificationProcesses) {
                notificationProcessesDto.add(new NotificationProcessDto(notificationProcess));
            }
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "NotificationProcesses", notificationProcessesDto);
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "There are no notificationProcesses.", "getNotificationProcesses NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while consulting the notificationProcesses.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while consulting the notificationProcesses.", "getNotificationProcesses " + ex.getMessage());
        }
    }
   public LocalResponse deleteNotificationProcess(Long id) {
        try {
            NotificationProcess notificationProcess;
            if (id != null && id > 0) {
                List<VariableDto> variables=(List<VariableDto>)variableService.getVariablesByNTP(id).getResult("Variables");
                for(VariableDto variableDto:variables){
                    variableService.deleteVariable(variableDto.getVarId());
                }
                notificationProcess = em.find(NotificationProcess.class, id);
                if (notificationProcess == null) {
                    return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "The notificationProcess to delete was not found.", "deleteNotificationProcess NoResultException");
                }
                em.remove(notificationProcess);
            } else {
                return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "You have to load the notificationProcess to delete.", "deleteNotificationProcess NoResultException");
            }
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "");
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "Can not delete the notificationProcess because it has relations with other registers.", "deleteNotificationProcess " + ex.getMessage());
            }
            LOG.log(Level.SEVERE, "An error ocurred while deleting the notificationProcess.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error ocurred while deleting the notificationProcess.", "deleteNotificationProcess " + ex.getMessage());
        }
    }
}
