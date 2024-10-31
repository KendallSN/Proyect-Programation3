
package cr.ac.una.sigeceunacomunicationws.controller;

import cr.ac.una.sigeceunacomunicationws.model.ConstrainDto;
import cr.ac.una.sigeceunacomunicationws.service.ConstrainService;
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

@Path("/ConstrainController")
public class ConstrainController {
  @EJB
  ConstrainService constrainService;
  @POST
    @Path("/saveConstrain")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveConstrain(ConstrainDto constrainDto) {
    try {
            LocalResponse res=constrainService.saveConstrain(constrainDto);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok((ConstrainDto)res.getResult("Constrain")).build();
        } catch (Exception ex) {
            Logger.getLogger(ConstrainController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while saving the constrain.").build();
        }
    }
    @GET
    @Path("/getConstrainsByVariable/{idVariable}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response getConstrainsByVariable(@PathParam("idVariable")Long idVariable) {
        try {
            LocalResponse res=constrainService.getConstrainsByVariable(idVariable);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            return Response.ok(new GenericEntity<List<ConstrainDto>>((List<ConstrainDto>) res.getResult("Constrains")) {
            }).build();

        } catch (Exception ex) {
        Logger.getLogger(ConstrainController.class.getName()).log(Level.SEVERE, null, ex);
        return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the constrains.").build();
        }
    }
    @DELETE
    @Path("/deleteConstrain/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response deleteConstrain(@PathParam("id")Long id) {
        try {
            LocalResponse res=constrainService.deleteConstrain(id);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok().build();
        } catch (Exception ex) {
            Logger.getLogger(ConstrainController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error ocurred while deleting the constrain").build();
        }
    }
}
