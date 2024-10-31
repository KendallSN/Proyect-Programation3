package cr.ac.una.sigeceunasecurityws;

import cr.ac.una.sigeceunasecurityws.controller.RoleController;
import cr.ac.una.sigeceunasecurityws.controller.SystemsController;
import cr.ac.una.sigeceunasecurityws.controller.UserController;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import jakarta.xml.ws.Endpoint;
@ApplicationPath("ws")
public class SigeceUNASecurityWs extends Application{
//    public static void main(String[] args) {
//        Endpoint.publish("http://localhost:8080/SigeceUNASecurityWs/UserController", new UserController());
//        Endpoint.publish("http://localhost:8080/SigeceUNASecurityWs/RoleController", new RoleController());
//        Endpoint.publish("http://localhost:8080/SigeceUNASecurityWs/SystemsController", new SystemsController());
//    }
}
