
package cr.ac.una.sigeceunasecurityws.service;

import cr.ac.una.sigeceunasecurityws.model.RoleDto;
import cr.ac.una.sigeceunasecurityws.model.Systems;
import cr.ac.una.sigeceunasecurityws.model.SystemsDto;
import cr.ac.una.sigeceunasecurityws.utility.Response;
import cr.ac.una.sigeceunasecurityws.utility.ResponseCode;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class SystemsService {
     private static final Logger LOG = Logger.getLogger(SystemsService.class.getName());
    @PersistenceContext(unitName = "SigeceUNASecurityWsPU")
    private EntityManager em;
    @EJB
    RoleService roleService;
    
    public Response getSystemByID(Long ID) {
        try {
            Query qrySystems = em.createNamedQuery("Systems.findBySystId", Systems.class);
            qrySystems.setParameter("systId", ID);
            return new Response(true, ResponseCode.CORRECT, "", "", "Systems", (Systems) qrySystems.getSingleResult());
        } catch (NoResultException ex) {
            return new Response(false, ResponseCode.NOTFOUND_ERROR, "System with the ID does not exist.", "getSystemByID NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the system.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while consulting the system.", "getSystemByID NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the system.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the system.", "getSystemByID " + ex.getMessage());
        }
    }
    
    public Response getSystems(){
        try{
            Query qrySystems=em.createNamedQuery("Systems.findAll",Systems.class);
            List<Systems> systems=qrySystems.getResultList();
            List<SystemsDto> systemsDto=new ArrayList<>();
            for(Systems system:systems){
                systemsDto.add(new SystemsDto(system));
            }      
          return new Response(true,ResponseCode.CORRECT,"","","Systems",systemsDto);
            
         } catch (NoResultException ex) {
            return new Response(false, ResponseCode.NOTFOUND_ERROR, "There are no systems.", "getSystems NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while consulting the systems.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while consulting the systems.", "getSystems " + ex.getMessage());
        }
    }
    
    public Response saveSystem(SystemsDto systemsDto) {
        try {
            Systems systems;
            if (systemsDto.getSystId()!= null && systemsDto.getSystId()> 0) {
                systems = em.find(Systems.class, systemsDto.getSystId());
                if (systems == null) {
                    return new Response(false, ResponseCode.NOTFOUND_ERROR, "System to update not found.", "saveSystem NoResultException");
                }
                systems.update(systemsDto);
                systems = em.merge(systems);
            } else {
                systems = new Systems(systemsDto);
                em.persist(systems);
            }
            em.flush();
            return new Response(true, ResponseCode.CORRECT, "", "", "Systems", new SystemsDto(systems));
        }catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error ocurred while saving the system.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while saving the system.", "saveSystem " + ex.getMessage());
        }
    }
    
    public Response deleteSystemByID(Long ID){
        try {
            Systems systems;
            if (ID != null && ID > 0) {
                List<RoleDto> roles=(List<RoleDto>)roleService.getRolesBySystID(ID).getResult("Roles");
                    for(RoleDto roleDto:roles){
                    roleService.deleteRoleByID(roleDto.getRolId());
                    }
                systems = em.find(Systems.class, ID);
                if (systems == null) {
                    return new Response(false, ResponseCode.NOTFOUND_ERROR, "System to delete not found.", "deleteSystemByID NoResultException");
                }
                em.remove(systems);
            } else {
                return new Response(false, ResponseCode.NOTFOUND_ERROR, "You should give the ID of the system to delete.", "deleteSystemByID NoResultException");
            }
            em.flush();
            return new Response(true, ResponseCode.CORRECT, "", "");
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new Response(false, ResponseCode.INTERNAL_ERROR, "You can't delete the system because it have relation with other elements.", "deleteSystemByID " + ex.getMessage());
            }
            LOG.log(Level.SEVERE, "An error ocurred while deleting the system.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while deleting the system.", "deleteSystemByID " + ex.getMessage());
        } 
    }
    
}
