package cr.ac.una.sigeceunasecurity.util;

import cr.ac.una.sigeceunasecurityws.controller.RoleController;
import cr.ac.una.sigeceunasecurityws.controller.RoleController_Service;
import cr.ac.una.sigeceunasecurityws.controller.UserController;
import cr.ac.una.sigeceunasecurityws.controller.SystemsController;
import cr.ac.una.sigeceunasecurityws.controller.SystemsController_Service;
import cr.ac.una.sigeceunasecurityws.controller.UserController_Service;
import java.net.MalformedURLException;
import java.net.URL;

public class SigeceUNAWsConsumer {
     private static UserController userController;
     private static SystemsController systemsController;
     private static RoleController roleController;


public static void InitController() throws MalformedURLException {
    URL urlUser=new URL("http://localhost:8080/SigeceUNASecurityWs/UserController?wsdl");
    UserController_Service WebUserService=new UserController_Service(urlUser);
    userController= WebUserService.getUserControllerPort();
    URL urlSystems=new URL("http://localhost:8080/SigeceUNASecurityWs/SystemsController?wsdl");
    SystemsController_Service WebSystemsService=new SystemsController_Service(urlSystems);
    systemsController= WebSystemsService.getSystemsControllerPort();
    URL urlRole=new URL("http://localhost:8080/SigeceUNASecurityWs/RoleController?wsdl");
    RoleController_Service WebRoleService=new RoleController_Service(urlRole);
    roleController= WebRoleService.getRoleControllerPort();
}
    public static UserController getUserController() {
        return userController;
    }
    public static SystemsController getSystemsController() {
        return systemsController;
    }
    public static RoleController getRoleController() {
        return roleController;
    }
}