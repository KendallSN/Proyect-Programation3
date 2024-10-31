package cr.ac.una.wssigeceuna.service;

import cr.ac.una.wssigeceuna.model.Activity;
import cr.ac.una.wssigeceuna.model.ActivityDto;
import cr.ac.una.wssigeceuna.model.Area;
import cr.ac.una.wssigeceuna.model.AreaDto;
import cr.ac.una.wssigeceuna.model.ManagementDto;
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
public class ActivityService {
    private static final Logger LOG = Logger.getLogger(ActivityService.class.getName());
    @PersistenceContext(unitName="WsSigeceUNAPU")
    private EntityManager em;
    @EJB
    AreaService areaService;
    @EJB
    ManagementService managementService;
    @EJB
    SubactivityService subactivityService;
    
    public LocalResponse getActivities(){
        try {
            Query qryActivity = em.createNamedQuery("Activity.findAll", Activity.class);
            List<Activity> activities = qryActivity.getResultList();
            List<ActivityDto> activityDtos = new ArrayList<>();
            activities.stream().forEach(ac->activityDtos.add(new ActivityDto(ac)));
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Activities", activityDtos);
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "There are no activities.", "getActivities NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while consulting the activities.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while consulting the activities.", "getActivities" + ex.getMessage());
        }
    }
    
    public LocalResponse getActivitiesByArea(Long areId){
        try {
            Query qryActivity = em.createNamedQuery("Activity.findAll", Activity.class);
            List<Activity> activities = qryActivity.getResultList();
            List<ActivityDto> activityDtos = new ArrayList<>();
            activities.stream().filter(a->a.getAreId().getAreId() == areId).forEach(a->activityDtos.add(new ActivityDto(a)));
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Activities", activityDtos);
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "There are no activities by area.", "getActivitiesByArea NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while consulting the activities by area.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while consulting the activities by area.", "getActivitiesByArea" + ex.getMessage());
        }
    }
    
    public LocalResponse saveActivity(ActivityDto activityDto) {
        try {
            Activity activity;
            if (activityDto.getActId()!= null && activityDto.getActId()> 0) {
                activity = em.find(Activity.class, activityDto.getActId());
                if (activity == null) {
                    return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "Activity to update not found.", "saveActivity NoResultException");
                }
                activity.update(activityDto);
                activity = em.merge(activity);
            } else {
                activity = new Activity(activityDto);
                activity.setAreId(new Area((AreaDto) areaService.getArea(activityDto.getAreId()).getResult("Area")));
                em.persist(activity);
            }
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Activity", new ActivityDto(activity));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error ocurred while saving the activity.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while saving the activity.", "saveActivity " + ex.getMessage());
        }
    }
    
    public LocalResponse getActivity(Long ID) {
        try {
            Query qryActivity = em.createNamedQuery("Activity.findByActId", Activity.class);
            qryActivity.setParameter("actId", ID);
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Activity", new ActivityDto((Activity) qryActivity.getSingleResult()));
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "Activity with the ID does not exist.", "getActivity NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the Activity.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the Activity.", "getActivity NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the Activity.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the Activity.", "getActivity " + ex.getMessage());
        }
    }
    
    public LocalResponse deleteActivity(Long id) {
        try {
            Activity activity;
            if (id != null && id > 0) {
                activity = em.find(Activity.class, id);
                if (activity == null) {
                    return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "The activity to delete was not found.", "deleteActivity NoResultException");
                }
                List<ManagementDto> managementDtos = (List<ManagementDto>) managementService.getManagements().getResult("Managements");
                managementDtos.stream().filter(s->s.getActId()==id).forEach(s->managementService.deleteManagement(s.getMgtId()));
                List<SubactivityDto> subactivities = (List<SubactivityDto>) subactivityService.getSubactivitiesByActivity(id).getResult("Subactivities");
                subactivities.stream().forEach(s->subactivityService.deleteSubactivity(s.getSactId()));
                em.remove(activity);
            } else {
                return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "You have to load the activity to delete.", "deleteActivity NoResultException");
            }
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error ocurred while deleting the activity.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error ocurred while deleting the activity.", "deleteActivity" + ex.getMessage());
        }
    }
}
