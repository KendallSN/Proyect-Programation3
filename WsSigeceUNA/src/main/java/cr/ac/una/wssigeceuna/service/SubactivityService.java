package cr.ac.una.wssigeceuna.service;

import cr.ac.una.wssigeceuna.model.Activity;
import cr.ac.una.wssigeceuna.model.ActivityDto;
import cr.ac.una.wssigeceuna.model.Subactivity;
import cr.ac.una.wssigeceuna.model.SubactivityDto;
import cr.ac.una.wssigeceuna.utility.LocalResponse;
import cr.ac.una.wssigeceuna.utility.ResponseCode;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class SubactivityService {
    private static final Logger LOG = Logger.getLogger(SubactivityService.class.getName());
    @PersistenceContext(unitName="WsSigeceUNAPU")
    private EntityManager em;
    @EJB
    ActivityService activityService;
    
    public LocalResponse getSubactivities(){
        try {
            Query qrySubactivity = em.createNamedQuery("Subactivity.findAll", Subactivity.class);
            List<Subactivity> subactivities = qrySubactivity.getResultList();
            List<SubactivityDto> subactivityDtos = new ArrayList<>();
            subactivities.stream().forEach(sac->subactivityDtos.add(new SubactivityDto(sac)));
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Subactivities", subactivityDtos);
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "There are no Subactivities.", "getSubactivities NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while consulting the Subactivities.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while consulting the Subactivities.", "getSubactivities" + ex.getMessage());
        }
    }
    
    public LocalResponse getSubactivity(Long ID) {
        try {
            Query qrySubactivity = em.createNamedQuery("Subactivity.findBySactId", Subactivity.class);
            qrySubactivity.setParameter("sactId", ID);
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Subactivity", new SubactivityDto((Subactivity) qrySubactivity.getSingleResult()));
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "Subactivity with the ID does not exist.", "getSubactivity NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the Subactivity.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the Subactivity.", "getSubactivity NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the Subactivity.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the Subactivity.", "getSubactivity " + ex.getMessage());
        }
    }
    public LocalResponse getSubactivitiesByActivity(Long actId){
        try {
            Query qrySubactivity = em.createNamedQuery("Subactivity.findAll", Subactivity.class);
            List<Subactivity> subactivities = qrySubactivity.getResultList();
            List<SubactivityDto> subactivityDtos = new ArrayList<>();
            subactivities.stream().filter(s->s.getActId().getActId()==actId).forEach(s->subactivityDtos.add(new SubactivityDto(s)));
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Subactivities", subactivityDtos);
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "There are no subactivities by activity.", "getSubactivityByActivity NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while consulting the subactivities by activity.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while consulting the subactivities by activity.", "getSubactivityByActivity" + ex.getMessage());
        }
    }
    
    public LocalResponse saveSubactivity(SubactivityDto subactivityDto) {
        try {
            Subactivity subactivity;
            if (subactivityDto.getSactId()!= null && subactivityDto.getSactId()> 0) {
                subactivity = em.find(Subactivity.class, subactivityDto.getSactId());
                if (subactivity == null) {
                    return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "Subactivity to update not found.", "saveSubactivity NoResultException");
                }
                subactivity.update(subactivityDto);
                subactivity = em.merge(subactivity);
            } else {
                subactivity = new Subactivity(subactivityDto);
                subactivity.setActId(new Activity((ActivityDto) activityService.getActivity(subactivityDto.getActId()).getResult("Activity")));
                em.persist(subactivity);
            }
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Subactivity", new SubactivityDto(subactivity));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error ocurred while saving the subactivity.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while saving the subactivity.", "saveSubactivity " + ex.getMessage());
        }
    }
    
    public LocalResponse deleteSubactivity(Long id) {
        try {
            Subactivity subactivity;
            if (id != null && id > 0) {
                subactivity = em.find(Subactivity.class, id);
                if (subactivity == null) {
                    return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "The subactivity to delete was not found.", "deleteSubactivity NoResultException");
                }
                em.remove(subactivity);
            } else {
                return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "You have to load the subactivity to delete.", "deleteSubactivity NoResultException");
            }
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error ocurred while deleting the subactivity.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error ocurred while deleting the subactivity.", "deleteSubactivity " + ex.getMessage());
        }
    }
}
