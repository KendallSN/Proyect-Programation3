package cr.ac.una.wssigeceuna.service;

import cr.ac.una.wssigeceuna.model.File;
import cr.ac.una.wssigeceuna.model.FileDto;
import cr.ac.una.wssigeceuna.model.Management;
import cr.ac.una.wssigeceuna.model.ManagementDto;
import cr.ac.una.wssigeceuna.model.Tracing;
import cr.ac.una.wssigeceuna.model.TracingDto;
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
public class TracingService {
    private static final Logger LOG = Logger.getLogger(TracingService.class.getName());
    @PersistenceContext(unitName="WsSigeceUNAPU")
    private EntityManager em;
    
    @EJB
    ManagementService managementService;
    @EJB
    UserService userService;
    @EJB
    FileService fileService;
    List<FileDto>files;
    
    public LocalResponse getTracings(){
        try {
            Query qryTracing = em.createNamedQuery("Tracing.findAll", Tracing.class);
            List<Tracing> tracings = qryTracing.getResultList();
            List<TracingDto> tracingDtos = new ArrayList<>();
            tracings.stream().forEach(ac->tracingDtos.add(new TracingDto(ac)));
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Tracings", tracingDtos);
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "There are no Tracings.", "getTracings NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while consulting the Tracings.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while consulting the Tracings.", "getTracings" + ex.getMessage());
        }
    }
    
    public LocalResponse getTracing(Long ID) {
        try {
            Query qryTracing = em.createNamedQuery("Tracing.findByTcgId", Tracing.class);
            qryTracing.setParameter("tcgId", ID);
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Tracing", new TracingDto((Tracing) qryTracing.getSingleResult()));
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "Tracing with the ID does not exist.", "getTracing NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the Tracing.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the Tracing.", "getTracing NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the Tracing.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the Tracing.", "getTracing " + ex.getMessage());
        }
    }
    
    public LocalResponse saveTracing(TracingDto tracingDto) {
        try {
            Tracing tracing;
            if (tracingDto.getTcgId() != null && tracingDto.getTcgId() > 0) {
                tracing = em.find(Tracing.class, tracingDto.getTcgId());
                if (tracing == null) {
                    return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "Tracing to update not found.", "saveTracing NoResultException");
                }
                tracing.update(tracingDto);
                tracing.setMgtId(new Management((ManagementDto) managementService.getManagement(tracingDto.getMgtId()).getResult("Management")));
                tracing.setUsrId(new User((UserDto) userService.getUserByID(tracingDto.getUsrId().getUsrId()).getResult("User")));
//                List<File> files = new ArrayList<>();
//                if(!tracingDto.getFileCollection().isEmpty()){
//                    tracingDto.getFileCollection().stream().forEach(f->files.add((File) fileService.getFile(f.getFleId()).getResult("File")));
//                }
//                tracing.setFileCollection(files);
                tracing = em.merge(tracing);
            } else {
                tracing = new Tracing(tracingDto);
                tracing.setMgtId(new Management((ManagementDto) managementService.getManagement(tracingDto.getMgtId()).getResult("Management")));
                tracing.setUsrId(new User((UserDto) userService.getUserByID(tracingDto.getUsrId().getUsrId()).getResult("User")));
                em.persist(tracing);
            }
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "Tracing", new TracingDto(tracing));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error ocurred while saving the Tracing.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while saving the Tracing.", "saveTracing " + ex.getMessage());
        }
    }
    
    public LocalResponse deleteTracing(Long id) {
        try {
            files = new ArrayList<>();
            Tracing tracing;
            if (id != null && id > 0) {
                TracingDto tracingDto = (TracingDto) getTracing(id).getResult("Tracing");
                if(tracingDto!=null){
                    if(!tracingDto.getFileCollection().isEmpty() && tracingDto.getFileCollection()!=null){
                        files.addAll(tracingDto.getFileCollection());
                    }
                }
                tracing = em.find(Tracing.class, id);
                if (tracing == null) {
                    return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "The Tracing to delete was not found.", "deleteTracing NoResultException");
                }
                em.remove(tracing);
            } else {
                return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "You have to load the activity to Tracing.", "deleteTracing NoResultException");
            }
            em.flush();
            if(!files.isEmpty() || files!=null){
                for(FileDto fileDto : files){
                fileService.deleteFile(fileDto.getFleId());
                }
            }
            return new LocalResponse(true, ResponseCode.CORRECT, "", "");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error ocurred while deleting the Tracing.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error ocurred while deleting the Tracing.", "deleteTracing" + ex.getMessage());
        }
    }
}
