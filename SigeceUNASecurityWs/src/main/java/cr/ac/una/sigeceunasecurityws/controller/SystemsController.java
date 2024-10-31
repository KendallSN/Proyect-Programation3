
package cr.ac.una.sigeceunasecurityws.controller;

import cr.ac.una.sigeceunasecurityws.model.Systems;
import cr.ac.una.sigeceunasecurityws.model.SystemsDto;
import cr.ac.una.sigeceunasecurityws.service.SystemsService;
import cr.ac.una.sigeceunasecurityws.utility.ResponseCode;
import jakarta.ejb.EJB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/SystemsControllerRs")
@WebService(serviceName="SystemsController")
public class SystemsController {
    @EJB
    SystemsService systemsService;
    
    @WebMethod(operationName="getSystemsByID")
    public SystemsDto getSystemByID(@WebParam(name="ID")Long ID) {   
        return new SystemsDto((Systems) systemsService.getSystemByID(ID).getResult("Systems"));
    }
    
    @WebMethod(operationName="getSystems")
    public List<SystemsDto> getSystems() {
       return (List<SystemsDto>) systemsService.getSystems().getResult("Systems");
    }
    
    @WebMethod(operationName="saveSystem")
    public SystemsDto saveSystem(@WebParam(name="SystemToRegister")SystemsDto systemsDto) {
        return (SystemsDto) systemsService.saveSystem(systemsDto).getResult("Systems");
    }
    
    @WebMethod(operationName="deleteSystemByID")
    public boolean deleteSystemByID(@WebParam(name="SystemIDToDelete")Long ID) {
       return systemsService.deleteSystemByID(ID).getState();
    } 
    
    @GET
    @Path("/getSystemById/{ID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response getSystemById(@PathParam("ID")Long id) {
        try {
            cr.ac.una.sigeceunasecurityws.utility.Response res=systemsService.getSystemByID(id);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            SystemsDto systemsDto=new SystemsDto((Systems)res.getResult("Systems"));
           return Response.ok(systemsDto).build();
        } catch (Exception ex) {
            Logger.getLogger(SystemsController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the systems.").build();
        }
    }
   
}
