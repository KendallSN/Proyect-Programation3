
package cr.ac.una.sigeceunacomunicationws.service;

import cr.ac.una.sigeceunacomunicationws.model.ConstrainDto;
import cr.ac.una.sigeceunacomunicationws.model.NotificationProcess;
import cr.ac.una.sigeceunacomunicationws.model.NotificationProcessDto;
import cr.ac.una.sigeceunacomunicationws.model.Variable;
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
public class VariableService {
    private static final Logger LOG = Logger.getLogger(VariableService.class.getName());
    @EJB
    private NotificationProcessService notificationProcessService;
    @EJB
    private ConstrainService constrainService;
    @PersistenceContext(unitName="SigeceUNAComunicationWsPU")
    private EntityManager em;
    public LocalResponse saveVariable(VariableDto variableDto) {
        try {
            Variable variable;                      
            variable = new Variable(variableDto);
            NotificationProcessDto notificationProcessDto=(NotificationProcessDto)notificationProcessService.getNotificationProcess(variableDto.getNtpId()).getResult("NotificationProcess");
            NotificationProcess notificationProcess=new NotificationProcess(notificationProcessDto);
            variable.setNtpId(notificationProcess);
            em.persist(variable);         
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Variable", new VariableDto(variable));
       } catch (Exception ex) {
           LOG.log(Level.SEVERE, "An error ocurred while saving the variable.", ex);
           return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while saving the variable.", "saveVariable " + ex.getMessage());
       }
    }
    public LocalResponse getVariablesByNTP(Long idNotificationProcess) {
        try {
            NotificationProcessDto notificationProcessDto = (NotificationProcessDto) notificationProcessService.getNotificationProcess(idNotificationProcess).getResult("NotificationProcess");   
            NotificationProcess notificationProcess=new NotificationProcess(notificationProcessDto);
            Query qryVariables = em.createNamedQuery("Variable.findByNotificationProcess", Variable.class);
            qryVariables.setParameter("ntpId", notificationProcess);
            List<Variable> variables = qryVariables.getResultList();
            List<VariableDto> variablesDto = new ArrayList<>();
            for (Variable variable : variables) {
                variablesDto.add(new VariableDto(variable));
            }
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Variables", variablesDto);
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "There are no variables.", "getVariablesByNTP NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the variables.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the variables.", "getVariablesByNTP " + ex.getMessage());
        }
    }
    public LocalResponse getVariable(Long ID) {
        try {
            Query qryVariable = em.createNamedQuery("Variable.findByVarId", Variable.class);
            qryVariable.setParameter("varId", ID);
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Variable", new VariableDto((Variable) qryVariable.getSingleResult()));
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "Variable with the ID does not exist.", "getVariable NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the variable.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the variable.", "getVariable NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the variable.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the variable.", "getVariable " + ex.getMessage());
        }
    }
    public LocalResponse deleteVariable(Long id) {
        try {
            Variable variable;
            if (id != null && id > 0) {
                List<ConstrainDto> constrains=(List<ConstrainDto>)constrainService.getConstrainsByVariable(id).getResult("Constrains");
                for(ConstrainDto constrainDto:constrains){
                    constrainService.deleteConstrain(constrainDto.getCnstId());
                }
                variable = em.find(Variable.class, id);
                if (variable == null) {
                    return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "The variable to delete was not found.", "deleteVariable NoResultException");
                }
                em.remove(variable);
            } else {
                return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "You have to load the message to delete.", "deleteVariable NoResultException");
            }
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "");
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "Can not delete the variable because it has relations with other registers.", "deleteVariable " + ex.getMessage());
            }
            LOG.log(Level.SEVERE, "An error ocurred while deleting the variable.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error ocurred while deleting the variable.", "deleteVariable " + ex.getMessage());
        }
    }
}
