
package cr.ac.una.sigeceunasecurityws.controller;
import cr.ac.una.sigeceunasecurityws.model.RoleDto;
import cr.ac.una.sigeceunasecurityws.model.SystemsDto;
import cr.ac.una.sigeceunasecurityws.service.RoleService;
import jakarta.ejb.EJB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;

@WebService(serviceName="RoleController")
public class RoleController {
    @EJB
    RoleService roleService;
    
    @WebMethod(operationName="getRoleByID")
    public RoleDto getRoleByID(@WebParam(name="ID")Long ID) {
        return (RoleDto) roleService.getRoleByID(ID).getResult("Role");
    }
    
    @WebMethod(operationName="getRoles")
    public List<RoleDto> getRoles() {
       return (List<RoleDto>) roleService.getRoles().getResult("Roles");
    }
    
    @WebMethod(operationName="getRolesOfSystem")
    public List<RoleDto> getRolesOfSystem(@WebParam(name="systemDto")SystemsDto systemsDto) {
        return (List<RoleDto>) roleService.getRolesOfSystem(systemsDto).getResult("RolesOfSystem");
    }
    
    @WebMethod(operationName="saveRole")
    public RoleDto saveRole(@WebParam(name="RoleToSave")RoleDto roleDto) {
        return (RoleDto) roleService.saveRole(roleDto).getResult("Role");
    }
    
    @WebMethod(operationName="deleteRoleByID")
    public boolean deleteRoleByID(@WebParam(name="RoleIDToDelete")Long ID) {
       return roleService.deleteRoleByID(ID).getState();
    } 
    
}
