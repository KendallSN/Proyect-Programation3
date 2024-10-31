
package cr.ac.una.sigeceunacomunicationws.service;

import cr.ac.una.sigeceunacomunicationws.model.Constrain;
import cr.ac.una.sigeceunacomunicationws.model.ConstrainDto;
import cr.ac.una.sigeceunacomunicationws.model.Variable;
import cr.ac.una.sigeceunacomunicationws.model.VariableDto;
import cr.ac.una.sigeceunacomunicationws.utility.LocalResponse;
import cr.ac.una.sigeceunacomunicationws.utility.ResponseCode;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class ConstrainService {
    private static final Logger LOG = Logger.getLogger(ConstrainService.class.getName());
    @EJB
    private VariableService variableService;
    @PersistenceContext(unitName="SigeceUNAComunicationWsPU")
    private EntityManager em;
    public LocalResponse saveConstrain(ConstrainDto constrainDto) {
        try {
            Constrain constrain;                      
            constrain = new Constrain(constrainDto);
            VariableDto variableDto=(VariableDto)variableService.getVariable(constrainDto.getVarId()).getResult("Variable");
            Variable variable=new Variable(variableDto);
            constrain.setVarId(variable);
            em.persist(constrain);         
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Constrain", new ConstrainDto(constrain));
       } catch (Exception ex) {
           LOG.log(Level.SEVERE, "An error ocurred while saving the constrain.", ex);
           return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while saving the constrain.", "saveConstrain " + ex.getMessage());
       }
    }
    public LocalResponse getConstrainsByVariable(Long idVariable) {
        try {
            VariableDto variableDto = (VariableDto) variableService.getVariable(idVariable).getResult("Variable");   
            Variable variable=new Variable(variableDto);
            Query qryConstrains = em.createNamedQuery("Constrain.findByVariable", Constrain.class);
            qryConstrains.setParameter("varId", variable);
            List<Constrain> constrains = qryConstrains.getResultList();
            List<ConstrainDto> constrainsDto = new ArrayList<>();
            for (Constrain constrain : constrains) {
                constrainsDto.add(new ConstrainDto(constrain));
            }
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Constrains", constrainsDto);
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "There are no constrains.", "getConstrainsByVariable NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the constrains.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the constrains.", "getConstrainsByVariable " + ex.getMessage());
        }
    }
    public LocalResponse deleteConstrain(Long id) {
        try {
            Constrain constrain;
            if (id != null && id > 0) {
                constrain = em.find(Constrain.class, id);
                if (constrain == null) {
                    return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "The constrain to delete was not found.", "deleteConstrain NoResultException");
                }
                em.remove(constrain);
            } else {
                return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "You have to load the constrain to delete.", "deleteConstrain NoResultException");
            }
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "");
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "Can not delete the message because it has relations with other registers.", "deleteConstrain " + ex.getMessage());
            }
            LOG.log(Level.SEVERE, "An error ocurred while deleting the constrain.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error ocurred while deleting the constrain.", "deleteConstrain " + ex.getMessage());
        }
    }
}
