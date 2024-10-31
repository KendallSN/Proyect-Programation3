package cr.ac.una.wssigeceuna.controller;

import cr.ac.una.wssigeceuna.model.ManagementDto;
import cr.ac.una.wssigeceuna.service.ManagementService;
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

@Path("/ManagementController")
public class ManagementController {
    @EJB
    ManagementService managementService;
    
    @GET
    @Path("/getManagements")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getManagements() {
        try {
            LocalResponse res = managementService.getManagements();
            if(!res.getState()){
              return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            return Response.ok(new GenericEntity<List<ManagementDto>>((List<ManagementDto>) res.getResult("Managements")) {
            }).build();
        } catch (Exception ex) {
        Logger.getLogger(ManagementController.class.getName()).log(Level.SEVERE, null, ex);
        return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the management.").build();
        }
    }
    
    @GET
    @Path("/getManagement/{ID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response getManagement(@PathParam("ID")Long id) {
        try {
            LocalResponse res = managementService.getManagement(id);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            ManagementDto managementDto=(ManagementDto)res.getResult("Management");
           return Response.ok(managementDto).build();
        } catch (Exception ex) {
            Logger.getLogger(ManagementController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the Management.").build();
        }
    }
    
    @GET
    @Path("/getManagementsToAprovedBy/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getManagementsToAprovedBy(@PathParam("id")Long id) {
        try {
            LocalResponse res = managementService.getManagementsToAprovedBy(id);
            if(!res.getState()){
              return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            return Response.ok(new GenericEntity<List<ManagementDto>>((List<ManagementDto>) res.getResult("Managements")) {
            }).build();
        } catch (Exception ex) {
        Logger.getLogger(ManagementController.class.getName()).log(Level.SEVERE, null, ex);
        return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the management.").build();
        }
    }
    
    @POST
    @Path("/saveManagement")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveManagement(ManagementDto managementDto) {
    try {
            LocalResponse res = managementService.saveManagement(managementDto);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok((ManagementDto)res.getResult("Management")).build();
        } catch (Exception ex) {
            Logger.getLogger(ManagementController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while saving the management.").build();
        }
    }
    
    @DELETE
    @Path("/deleteManagement/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response deleteManagement(@PathParam("id")Long id) {
        try {
            LocalResponse res=managementService.deleteManagement(id);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok().build();
        } catch (Exception ex) {
            Logger.getLogger(ManagementController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error ocurred while deleting the management").build();
        }
    }
}
