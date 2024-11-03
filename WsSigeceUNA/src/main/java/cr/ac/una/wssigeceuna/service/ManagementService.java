package cr.ac.una.wssigeceuna.service;

import cr.ac.una.wssigeceuna.model.Activity;
import cr.ac.una.wssigeceuna.model.ActivityDto;
import cr.ac.una.wssigeceuna.model.AreaDto;
import cr.ac.una.wssigeceuna.model.FileDto;
import cr.ac.una.wssigeceuna.model.Management;
import cr.ac.una.wssigeceuna.model.ManagementDto;
import cr.ac.una.wssigeceuna.model.ManagementaprobationDto;
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
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class ManagementService {
    private static final Logger LOG = Logger.getLogger(ManagementService.class.getName());
    @PersistenceContext(unitName="WsSigeceUNAPU")
    private EntityManager em;
    @EJB
    ActivityService activityService;
    @EJB
    SubactivityService subactivityService;
    @EJB
    AreaService areaService;
    @EJB
    ManagementaprobationService managementaprobationService;
    @EJB
    private FileService fileService;
    List<FileDto>files;
    
    Predicate<ManagementDto> toAproveBy(Long id){
        return m->((List<ManagementaprobationDto>) managementaprobationService.getManagementaprobationsByManagement
        (m.getMgtId()).getResult("Managementaprobations")).stream().filter(ma->"Pending".equals(ma.getMgtaState()))
        .anyMatch(ma->ma.getUsrToaproveId().getUsrId()==id);
    }
    
    ActivityDto activity;
    AreaDto area;
    public LocalResponse getManagements(){
           try {
            Query qryAreas = em.createNamedQuery("Management.findAll", Management.class);
            List<Management> managements = qryAreas.getResultList();
            List<ManagementDto> managementDtos = new ArrayList<>();
            for (Management management : managements) {
                ManagementDto managementDto = new ManagementDto(management);
                
                if(management.getActId()!=null){
                    activity = (ActivityDto) activityService.getActivity(management.getActId().getActId()).getResult("Activity");
                    area = (AreaDto) areaService.getArea(activity.getAreId()).getResult("Area");
                    managementDto.setAreId(area.getAreId());
                    managementDto.setAreName(area.getAreName());
                }
                if(management.getSactId()!=null){
                    activity = (ActivityDto) activityService.getActivity(management.getSactId().getActId().getActId()).getResult("Activity");
                    area = (AreaDto) areaService.getArea(activity.getAreId()).getResult("Area");
                    
                    managementDto.setAreId(area.getAreId());
                    managementDto.setAreName(area.getAreName());
                }
                managementDtos.add(managementDto);
            }
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Managements", managementDtos);
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "There are no areas.", "getAreas NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while consulting the areas.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while consulting the areas.", "getAreas " + ex.getMessage());
        }
    }
    
    public LocalResponse getManagement(Long ID) {
        try {
            Query qryManagement = em.createNamedQuery("Management.findByMgtId", Management.class);
            qryManagement.setParameter("mgtId", ID);
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Management", new ManagementDto((Management) qryManagement.getSingleResult()));
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "Management with the ID does not exist.", "getManagement NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the Management.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the Management.", "getManagement NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the Management.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the Management.", "getManagement " + ex.getMessage());
        }
    }
    
    public LocalResponse getManagementsToAprovedBy(Long ID){
           try {
            List<ManagementDto> managementDtos = (List<ManagementDto>) this.getManagements().getResult("Managements");
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Managements", managementDtos.stream().filter(toAproveBy(ID)).toList());
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "There are no areas.", "getAreas NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while consulting the areas.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while consulting the areas.", "getAreas " + ex.getMessage());
        }
    }
    
    public LocalResponse saveManagement(ManagementDto managementDto) {
        try {
            Management management;
            if (managementDto.getMgtId()!= null && managementDto.getMgtId()> 0) {
                management = em.find(Management.class, managementDto.getMgtId());
                if (management == null) {
                    return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "Management to update not found.", "saveManagement NoResultException");
                }
                management.update(managementDto);
                if(managementDto.getActId()!=null && managementDto.getActId()>0){
                    management.setActId(new Activity((ActivityDto) activityService.getActivity(managementDto.getActId()).getResult("Activity")));
                }
                if(managementDto.getSactId()!=null && managementDto.getSactId()>0){
                    management.setSactId(new Subactivity((SubactivityDto) subactivityService.getSubactivity(managementDto.getSactId()).getResult("Subactivity")));
                }
                management = em.merge(management);
            } else {
                management = new Management(managementDto);
                if(managementDto.getActId()!=null && managementDto.getActId()>0){
                    management.setActId(new Activity((ActivityDto) activityService.getActivity(managementDto.getActId()).getResult("Activity")));
                }
                if(managementDto.getSactId()!=null && managementDto.getSactId()>0){
                    management.setSactId(new Subactivity((SubactivityDto) subactivityService.getSubactivity(managementDto.getSactId()).getResult("Subactivity")));
                }
                em.persist(management);
            }
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Management", new ManagementDto(management));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error ocurred while saving the management.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while saving the management.", "saveManagement" + ex.getMessage());
        }
    }
    
    public LocalResponse deleteManagement(Long id) {
        try {
            files = new ArrayList<>();
            Management management;
            if (id != null && id > 0) {
                ManagementDto managementDto = (ManagementDto) getManagement(id).getResult("Management");
                if(managementDto!=null){
                    if(!managementDto.getFileCollection().isEmpty() && managementDto.getFileCollection()!=null){
                        files.addAll(managementDto.getFileCollection());
                    }
                }
                management = em.find(Management.class, id);
                if (management == null) {
                    return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "The management to delete was not found.", "deleteManagement NoResultException");
                }
                ((List<ManagementaprobationDto>) managementaprobationService.getManagementaprobationsByManagement(id).getResult("Managementaprobations")).stream().forEach(ma->managementaprobationService.deleteManagementAprobation(ma.getMgtaId()));
                em.remove(management);
            } else {
                return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "You have to load the management to delete.", "deleteManagement NoResultException");
            }
            em.flush();
            if(!files.isEmpty() || files!=null){
                for(FileDto fileDto : files){
                fileService.deleteFile(fileDto.getFleId());
                }
            }
            
            return new LocalResponse(true, ResponseCode.CORRECT, "", "");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error ocurred while deleting the management.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error ocurred while deleting the management.", "deleteManagement " + ex.getMessage());
        }
    }
}
