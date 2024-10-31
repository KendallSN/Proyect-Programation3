
package cr.ac.una.sigeceunasecurity.service;

import cr.ac.una.sigeceunasecurity.util.Response;
import cr.ac.una.sigeceunasecurity.util.SigeceUNAWsConsumer;
import cr.ac.una.sigeceunasecurityws.controller.RoleController;
import cr.ac.una.sigeceunasecurityws.controller.RoleDto;
import cr.ac.una.sigeceunasecurityws.controller.SystemsDto;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoleService implements Serializable{
    
    RoleController roleController = SigeceUNAWsConsumer.getRoleController();

        public Response saveRole(RoleDto roleDto) {
        try {
            return new Response(true, "", "", "Role", roleController.saveRole(roleDto));
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while saving the role [" + roleDto + "]", ex);
            return new Response(false, "An error ocurred while saving the system.", "saveRole " + ex.getMessage());
        }
    }

    public Response getRoles() {
        try {
            return new Response(true, "", "", "Roles", roleController.getRoles());
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while queryng the systems", ex);
            return new Response(false, "An error ocurred while queryng the systems.", "getSystems " + ex.getMessage());
        }
    }

    public Response getRolesOfSystem(SystemsDto systemsDto) {
        try {
            return new Response(true, "", "", "RolesOfSystem",roleController.getRolesOfSystem(systemsDto));
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while queryng the systems", ex);
            return new Response(false, "An error ocurred while queryng the systems.", "getRolesOfSystem " + ex.getMessage());
        }
    }
    public Response deleteRole(Long ID) {
        try {
            return new Response(true, "", "", "Role", roleController.deleteRoleByID(ID));
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while queryng the role", ex);
            return new Response(false, "An error ocurred while queryng the role.", "deleteRole " + ex.getMessage());
        }
    }
}
