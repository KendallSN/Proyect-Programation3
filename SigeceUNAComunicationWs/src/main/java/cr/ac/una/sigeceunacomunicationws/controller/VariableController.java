
package cr.ac.una.sigeceunacomunicationws.controller;

import cr.ac.una.sigeceunacomunicationws.model.VariableDto;
import cr.ac.una.sigeceunacomunicationws.service.VariableService;
import cr.ac.una.sigeceunacomunicationws.utility.LocalResponse;
import cr.ac.una.sigeceunacomunicationws.utility.ResponseCode;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/VariableController")
public class VariableController {
    @EJB
    VariableService variableService;
    @POST
    @Path("/saveVariable")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveVariable(VariableDto variableDto) {
    try {
            LocalResponse res=variableService.saveVariable(variableDto);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok((VariableDto)res.getResult("Variable")).build();
        } catch (Exception ex) {
            Logger.getLogger(VariableController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while saving the variable.").build();
        }
    }
    @GET
    @Path("/getVariablesByNTP/{idNotificationProcess}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response getVariablesByNTP(@PathParam("idNotificationProcess")Long idNotificationProcess) {
        try {
            LocalResponse res=variableService.getVariablesByNTP(idNotificationProcess);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            return Response.ok(new GenericEntity<List<VariableDto>>((List<VariableDto>) res.getResult("Variables")) {
            }).build();

        } catch (Exception ex) {
        Logger.getLogger(VariableController.class.getName()).log(Level.SEVERE, null, ex);
        return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the variables.").build();
        }
    }
    @DELETE
    @Path("/deleteVariable/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response deleteVariable(@PathParam("id")Long id) {
        try {
            LocalResponse res=variableService.deleteVariable(id);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok().build();
        } catch (Exception ex) {
            Logger.getLogger(VariableController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error ocurred while deleting the variable").build();
        }
    }
}
