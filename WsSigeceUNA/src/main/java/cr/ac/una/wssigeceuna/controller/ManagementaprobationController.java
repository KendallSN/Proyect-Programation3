package cr.ac.una.wssigeceuna.controller;

import cr.ac.una.wssigeceuna.model.ManagementaprobationDto;
import cr.ac.una.wssigeceuna.service.ManagementaprobationService;
import cr.ac.una.wssigeceuna.utility.LocalResponse;
import cr.ac.una.wssigeceuna.utility.ResponseCode;
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

@Path("/ManagementaprobationController")
public class ManagementaprobationController {

    @EJB
    ManagementaprobationService managementaprobationService;

    @GET
    @Path("/getManagementaprobations")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getManagementaprobations() {
        try {
            LocalResponse res = managementaprobationService.getManagementaprobations();
            if (!res.getState()) {
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            return Response.ok(new GenericEntity<List<ManagementaprobationDto>>((List<ManagementaprobationDto>) res.getResult("Managementaprobations")) {
            }).build();
        } catch (Exception ex) {
            Logger.getLogger(ManagementaprobationController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the managementaprobations.").build();
        }
    }

    @GET
    @Path("/getManagementaprobationsByManagement/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getManagementaprobationsByManagement(@PathParam("id") Long id) {
        try {
            LocalResponse res = managementaprobationService.getManagementaprobationsByManagement(id);
            if (!res.getState()) {
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            return Response.ok(new GenericEntity<List<ManagementaprobationDto>>((List<ManagementaprobationDto>) res.getResult("Managementaprobations")) {
            }).build();
        } catch (Exception ex) {
            Logger.getLogger(ManagementaprobationController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the managementaprobations.").build();
        }
    }

    @POST
    @Path("/saveManagementaprobation")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveManagementaprobation(ManagementaprobationDto managementaprobationDto) {
        try {
            LocalResponse res = managementaprobationService.saveManagementaprobation(managementaprobationDto);
            if (!res.getState()) {
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            return Response.ok((ManagementaprobationDto) res.getResult("Managementaprobation")).build();
        } catch (Exception ex) {
            Logger.getLogger(ManagementController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while saving the management.").build();
        }
    }

    @DELETE
    @Path("/deleteManagementaprobation/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteManagementaprobation(@PathParam("id") Long id) {
        try {
            ManagementaprobationDto managementaprobation = (ManagementaprobationDto) managementaprobationService.getManagementaprobation(id).getResult("Managementaprobation");
            if(((List<ManagementaprobationDto>)(managementaprobationService.getManagementaprobationsByManagement(managementaprobation.getMgtId()).getResult("Managementaprobations"))).size()==1){
                return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("Last managementaprobation of a management cant be deleted").build();
            }
            LocalResponse res = managementaprobationService.deleteManagementAprobation(id);
            
            if (!res.getState()) {
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            return Response.ok().build();
        } catch (Exception ex) {
            Logger.getLogger(ManagementaprobationController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error ocurred while deleting the Managementaprobation").build();
        }
    }
}
