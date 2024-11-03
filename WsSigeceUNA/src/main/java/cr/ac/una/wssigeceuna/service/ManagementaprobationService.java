package cr.ac.una.wssigeceuna.service;

import cr.ac.una.wssigeceuna.model.Management;
import cr.ac.una.wssigeceuna.model.ManagementDto;
import cr.ac.una.wssigeceuna.model.Managementaprobation;
import cr.ac.una.wssigeceuna.model.ManagementaprobationDto;
import cr.ac.una.wssigeceuna.model.User;
import cr.ac.una.wssigeceuna.model.UserDto;
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
public class ManagementaprobationService {
    private static final Logger LOG = Logger.getLogger(ManagementaprobationService.class.getName());
    @PersistenceContext(unitName="WsSigeceUNAPU")
    private EntityManager em;
    @EJB
    UserService userService;
    @EJB
    ManagementService managementService;
    
    public LocalResponse getManagementaprobations(){
           try {
            Query qryManagementaprovation = em.createNamedQuery("Managementaprobation.findAll", Managementaprobation.class);
            List<Managementaprobation> managementaprobations = qryManagementaprovation.getResultList();
            List<ManagementaprobationDto> managementaprobationDtos = new ArrayList<>();
            for (Managementaprobation managementaprobation : managementaprobations) {
                ManagementaprobationDto managementaprobationDto = new ManagementaprobationDto(managementaprobation);
                managementaprobationDtos.add(managementaprobationDto);
            }
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Managementaprobations", managementaprobationDtos);
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "There are no Managementaprobations.", "getManagementaprobations NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while consulting the Managementaprobations.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while consulting the Managementaprobations.", "getManagementaprobations " + ex.getMessage());
        }
    }
    
    public LocalResponse getManagementaprobationsByManagement(Long id){
           try {
            List<ManagementaprobationDto> managementaprobationDtos = (List<ManagementaprobationDto>) this.getManagementaprobations().getResult("Managementaprobations");
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Managementaprobations", managementaprobationDtos.stream().filter(s->s.getMgtId()==id).toList());
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "There are no Managementaprobations.", "getManagementaprobationsByManagement NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while consulting the Managementaprobations.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while consulting the Managementaprobations.", "getManagementaprobationsByManagement " + ex.getMessage());
        }
    }
    
    public LocalResponse saveManagementaprobation(ManagementaprobationDto managementaprobationDto) {
        try {
            Managementaprobation managementaprobation;
            if (managementaprobationDto.getMgtaId() != null && managementaprobationDto.getMgtaId() > 0) {
                managementaprobation = em.find(Managementaprobation.class, managementaprobationDto.getMgtaId());
                if (managementaprobation == null) {
                    return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "Managementaprobation to update not found.", "saveManagementaprobation NoResultException");
                }
                managementaprobation.update(managementaprobationDto);
                managementaprobation.setMgtId(new Management((ManagementDto) managementService.getManagement(managementaprobationDto.getMgtId()).getResult("Management")));
                managementaprobation.setUsrToaproveId(new User((UserDto) userService.getUserByID(managementaprobationDto.getUsrToaproveId().getUsrId()).getResult("User")));
                managementaprobation = em.merge(managementaprobation);
            } else {
                managementaprobation = new Managementaprobation(managementaprobationDto);
                managementaprobation.setMgtId(new Management((ManagementDto) managementService.getManagement(managementaprobationDto.getMgtId()).getResult("Management")));
                managementaprobation.setUsrToaproveId(new User((UserDto) userService.getUserByID(managementaprobationDto.getUsrToaproveId().getUsrId()).getResult("User")));
                em.persist(managementaprobation);
            }
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Managementaprobation", new ManagementaprobationDto(managementaprobation));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error ocurred while saving the Managementaprobation.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while saving the Managementaprobation.", "saveManagementaprobation" + ex.getMessage());
        }
    }
    
    public LocalResponse getManagementaprobation(Long ID) {
        try {
            Query qryManagementaprobation = em.createNamedQuery("Managementaprobation.findByMgtaId", Managementaprobation.class);
            qryManagementaprobation.setParameter("mgtaId", ID);
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Managementaprobation", new ManagementaprobationDto((Managementaprobation) qryManagementaprobation.getSingleResult()));
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "Managementaprobation with the ID does not exist.", "getManagementaprobation NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the Managementaprobation.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the Managementaprobation.", "getManagementaprobation NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the Managementaprobation.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the Managementaprobation.", "getManagementaprobation " + ex.getMessage());
        }
    }
    
    public LocalResponse deleteManagementAprobation(Long id) {
        try {
            Managementaprobation managementaprobation;
            if (id != null && id > 0) {
                managementaprobation = em.find(Managementaprobation.class, id);
                if (managementaprobation == null) {
                    return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "The managementaprobation to delete was not found.", "deleteManagement NoResultException");
                }
                em.remove(managementaprobation);
            } else {
                return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "You have to load the Managementaprobation to delete.", "deleteManagementaprobation NoResultException");
            }
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error ocurred while deleting the Managementaprobation.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error ocurred while deleting the Managementaprobation.", "deleteManagementaprobation " + ex.getMessage());
        }
    }
}
