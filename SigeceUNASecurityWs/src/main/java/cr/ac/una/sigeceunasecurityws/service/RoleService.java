
package cr.ac.una.sigeceunasecurityws.service;
import cr.ac.una.sigeceunasecurityws.model.Role;
import cr.ac.una.sigeceunasecurityws.model.RoleDto;
import cr.ac.una.sigeceunasecurityws.model.Systems;
import cr.ac.una.sigeceunasecurityws.model.SystemsDto;
import cr.ac.una.sigeceunasecurityws.model.User;
import cr.ac.una.sigeceunasecurityws.model.UserDto;
import cr.ac.una.sigeceunasecurityws.utility.ResponseCode;
import cr.ac.una.sigeceunasecurityws.utility.Response;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

@Stateless
@LocalBean
public class RoleService {
    private static final Logger LOG = Logger.getLogger(RoleService.class.getName());
    @PersistenceContext(unitName = "SigeceUNASecurityWsPU")
    private EntityManager em;
    @EJB
    SystemsService systemsService;
    
    public Response getRoleByID(Long ID) {
        try {
            Query qryRole = em.createNamedQuery("Role.findByRolId", Role.class);
            qryRole.setParameter("rolId", ID);
            return new Response(true, ResponseCode.CORRECT, "", "", "Role", new RoleDto((Role) qryRole.getSingleResult()));
        } catch (NoResultException ex) {
            return new Response(false, ResponseCode.NOTFOUND_ERROR, "Role with the ID does not exist.", "getRoleByID NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the Role.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while consulting the role.", "getRoleByID NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the Role.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the Role.", "getRoleByID " + ex.getMessage());
        }
    }
    public Response getRolesBySystID(Long ID) {
        try {
            Systems systems=(Systems)systemsService.getSystemByID(ID).getResult("Systems");
            Query qryRole = em.createNamedQuery("Role.findByRolSystID", Role.class);
            qryRole.setParameter("systId", systems);
            List<Role>roles=qryRole.getResultList();
            List<RoleDto>rolesDto=new ArrayList<>();
            for(Role role:roles){
                rolesDto.add(new RoleDto(role));
            }
            return new Response(true, ResponseCode.CORRECT, "", "", "Roles", rolesDto);
        } catch (NoResultException ex) {
            return new Response(false, ResponseCode.NOTFOUND_ERROR, "Role with the ID does not exist.", "getRoleByID NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the Role.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while consulting the role.", "getRoleByID NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the Role.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the Role.", "getRoleByID " + ex.getMessage());
        }
    }
     public Response getRoles(){
        try{
            Query qryRole=em.createNamedQuery("Role.findAll",Role.class);
            List<Role> roles=qryRole.getResultList();
            List<RoleDto> rolesDto=new ArrayList<>();
            for(Role role:roles){
                rolesDto.add(new RoleDto(role));
            }
          return new Response(true,ResponseCode.CORRECT,"","","Roles",rolesDto);
            
        } catch (NoResultException ex) {
            return new Response(false, ResponseCode.NOTFOUND_ERROR, "There are no roles.", "getRoles NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while consulting the roles.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while consulting the roles.", "getRoles " + ex.getMessage());
        }
    }

    public Response saveRole(RoleDto roleDto) {
        Role role;
        System.out.println("System id del rol a guardar: "+ roleDto.getSystId());
        try{
            if(roleDto.getRolId()!=null&&roleDto.getRolId()>0){
                role=em.find(Role.class, roleDto.getRolId());
                if(role==null){
                    return new Response(false, ResponseCode.NOTFOUND_ERROR, "Role to update not found.", "saveRole NoResultException");   
                }
                role.update(roleDto);
                role.setSystId((Systems) systemsService.getSystemByID(roleDto.getSystId()).getResult("Systems"));
                role=em.merge(role);
            }else{
                role=new Role(roleDto);
                role.setSystId((Systems) systemsService.getSystemByID(roleDto.getSystId()).getResult("Systems"));
                em.persist(role);
            }
            em.flush();
            return new Response(true, ResponseCode.CORRECT, "", "", "Role", new RoleDto(role));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error ocurred while saving the role.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while saving the role.", "saveRole " + ex.getMessage());
        }
    }
    
    public Response deleteRoleByID(Long ID){
        try {
            Role role;
            if(ID != null && ID > 0){
                role = em.find(Role.class, ID);
                if (role == null) {
                    return new Response(false, ResponseCode.NOTFOUND_ERROR, "Role to delete not found.", "deleteRoleByID NoResultException");
                }
                em.remove(role);
            } else {
                return new Response(false, ResponseCode.NOTFOUND_ERROR, "You should give the ID of the role to delete.", "deleteRoleByID NoResultException");
            }
            em.flush();
            return new Response(true, ResponseCode.CORRECT, "", "");
        }catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new Response(false, ResponseCode.INTERNAL_ERROR, "You can't delete the role because it have relation with other elements.", "deleteRoleByID " + ex.getMessage());
            }
            LOG.log(Level.SEVERE, "An error ocurred while deleting the role.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while deleting the role.", "deleteRoleByID " + ex.getMessage());
        }   
    }

    public Response getRolesOfSystem(SystemsDto systemsDto) {
        try {
            Query qryRole = em.createNamedQuery("Role.findByRolSystID", Role.class);
            qryRole.setParameter("systId", systemsDto.getSystem());
            List<Role> roles=qryRole.getResultList();
            List<RoleDto> rolesDto=new ArrayList<>();
            for(Role role:roles){
                rolesDto.add(new RoleDto(role));
            }
            return new Response(true, ResponseCode.CORRECT, "", "", "RolesOfSystem",  rolesDto);
        } catch (NoResultException ex) {
            return new Response(false, ResponseCode.NOTFOUND_ERROR, "Role with the ID does not exist.", "getRoleByID NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the Role.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while consulting the role.", "getRoleByID NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "An error occurred while querying the Role.", ex);
            return new Response(false, ResponseCode.INTERNAL_ERROR, "An error occurred while querying the Role.", "getRoleByID " + ex.getMessage());
        }
    }
     
}
