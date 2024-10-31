package cr.ac.una.wssigeceuna.service;

import cr.ac.una.wssigeceuna.model.File;
import cr.ac.una.wssigeceuna.model.FileDto;
import cr.ac.una.wssigeceuna.utility.LocalResponse;
import cr.ac.una.wssigeceuna.utility.ResponseCode;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class FileService {
    private static final Logger LOG = Logger.getLogger(FileService.class.getName());
    @PersistenceContext(unitName="WsSigeceUNAPU")
    private EntityManager em;
        
    public LocalResponse getFile(Long ID) {
        try {
            Query qryFile = em.createNamedQuery("File.findByFleId", File.class);
            qryFile.setParameter("fleId", ID);
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "File", (File)qryFile.getSingleResult());
        } catch (NoResultException ex) {
            return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "File with the ID does not exist.", "getFile NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the File.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the File.", "getFile NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the File.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the File.", "getFile " + ex.getMessage());
        }
    }
    
    public LocalResponse saveFile(FileDto fileDto) {
        try {
            File file;
            if (fileDto.getFleId() != null && fileDto.getFleId() > 0) {
                file = em.find(File.class, fileDto.getFleId());
                if (file == null) {
                    return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "File to update not found.", "saveFile NoResultException");
                }
                file.update(fileDto);
                file = em.merge(file);
            } else {
                file = new File(fileDto);
                em.persist(file);
            }
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "", "File", new FileDto(file));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error ocurred while saving the File.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error occurred while saving the File.", "saveFile " + ex.getMessage());
        }
    }
    
    public LocalResponse deleteFile(Long id) {
        try {
            File file;
            if (id != null && id > 0) {
                file = em.find(File.class, id);
                if (file == null) {
                    return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "The file to delete was not found.", "deleteFile NoResultException");
                }
                em.remove(file);
            } else {
                return new LocalResponse(false, ResponseCode.NOTFOUND_ERROR, "You have to load the file to delete.", "deleteFile NoResultException");
            }
            em.flush();
            return new LocalResponse(true, ResponseCode.CORRECT, "", "");
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "Can not delete the file because it has relations with other registers.", "deleteFile " + ex.getMessage());
            }
            LOG.log(Level.SEVERE, "An error ocurred while deleting the file.", ex);
            return new LocalResponse(false, ResponseCode.INTERNAL_ERROR, "An error ocurred while deleting the file.", "deleteFile " + ex.getMessage());
        }
    }
}
