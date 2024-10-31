
package cr.ac.una.sigeceunasecurity.service;

import cr.ac.una.sigeceunasecurity.util.Response;
import cr.ac.una.sigeceunasecurity.util.SigeceUNAWsConsumer;
import cr.ac.una.sigeceunasecurityws.controller.SystemsController;
import cr.ac.una.sigeceunasecurityws.controller.SystemsDto;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemsService implements Serializable{
    
    SystemsController systemsController = SigeceUNAWsConsumer.getSystemsController();

    public Response saveSystem(SystemsDto systemsDto) {
        try {
            return new Response(true, "", "", "System", systemsController.saveSystem(systemsDto));
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while saving the system [" + systemsDto + "]", ex);
            return new Response(false, "An error ocurred while saving the system.", "saveSystem " + ex.getMessage());
        }
    }
        

    public Response getSystems() {
        try {
            return new Response(true, "", "", "Systems", systemsController.getSystems());
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while queryng the systems", ex);
            return new Response(false, "An error ocurred while queryng the systems.", "getSystems " + ex.getMessage());
        }
    }
    
    public Response getSystemById(Long id) {
        try {
            return new Response(true, "", "", "Systems", systemsController.getSystemsByID(id));
        } catch (Exception ex) {
            Logger.getLogger(SystemsService.class.getName()).log(Level.SEVERE, "An error ocurred while queryng the systems", ex);
            return new Response(false, "An error ocurred while queryng the systems.", "getSystems " + ex.getMessage());
        }
    }
    
    public Response deleteSystems(Long ID) {
        try {
            return new Response(true, "", "", "System", systemsController.deleteSystemByID(ID));
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the system", ex);
            return new Response(false, "An error ocurred while deleting the system.", "deleteSystem " + ex.getMessage());
        }
    }
    
}
