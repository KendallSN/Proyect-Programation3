package cr.ac.una.wssigeceuna.service;

import cr.ac.una.wssigeceuna.model.ActivityDto;
import cr.ac.una.wssigeceuna.model.Area;
import cr.ac.una.wssigeceuna.model.AreaDto;
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
public class AreaService {
    private static final Logger LOG = Logger.getLogger(AreaService.class.getName());
    @PersistenceContext(unitName="WsSigeceUNAPU")
    private EntityManager em;
    @EJB
    ActivityService activityService;
    
    public LocalResponse saveArea(AreaDto areaDto) {
        try {
            Area area;
            if (areaDto.getAreId()!= null && areaDto.getAreId()> 0) {
                area = em.find(Area.class, areaDto.getAreId());
                if (area == null) {
                    return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "Area to update not found.", "saveArea NoResultException");
                }
                area.update(areaDto);
                area = em.merge(area);
            } else {
                area = new Area(areaDto);
                em.persist(area);
            }
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Area", new AreaDto(area));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error ocurred while saving the area.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while saving the Area.", "saveArea " + ex.getMessage());
        }
    }
    
    public LocalResponse getArea(Long ID) {
        try {
            Query qryArea = em.createNamedQuery("Area.findByAreId", Area.class);
            qryArea.setParameter("areId", ID);
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Area", new AreaDto((Area) qryArea.getSingleResult()));
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "Area with the ID does not exist.", "getArea NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the area.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the area.", "getArea NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the area.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the area.", "getArea " + ex.getMessage());
        }
    }
    
    public LocalResponse getAreas(){
           try {
            Query qryAreas = em.createNamedQuery("Area.findAll", Area.class);
            List<Area> users = qryAreas.getResultList();
            List<AreaDto> usersDto = new ArrayList<>();
            for (Area area : users) {
                usersDto.add(new AreaDto(area));
            }
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Areas", usersDto);
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "There are no areas.", "getAreas NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while consulting the areas.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while consulting the areas.", "getAreas " + ex.getMessage());
        }
    }
    
    public LocalResponse deleteArea(Long id) {
        try {
            Area area;
            if (id != null && id > 0) {
                area = em.find(Area.class, id);
                if (area == null) {
                    return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "The chat to area was not found.", "deleteArea NoResultException");
                }
                List<ActivityDto> activityDtos = (List<ActivityDto>) activityService.getActivitiesByArea(id).getResult("Activities");
                activityDtos.stream().forEach(a->activityService.deleteActivity(a.getActId()));
                em.remove(area);
            } else {
                return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "You have to load the area to delete.", "deleteArea NoResultException");
            }
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error ocurred while deleting the area.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error ocurred while deleting the area.", "deleteArea " + ex.getMessage());
        }
    }
}
