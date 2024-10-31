package cr.ac.una.wssigeceuna.controller;

import cr.ac.una.wssigeceuna.model.AreaDto;
import cr.ac.una.wssigeceuna.service.AreaService;
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

@Path("/AreaController")
public class AreaController {
    @EJB
    AreaService areaService;
    
    @GET
    @Path("/getArea/{ID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response getArea(@PathParam("ID")Long id) {
        try {
            LocalResponse res=areaService.getArea(id);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            AreaDto areaDto=(AreaDto)res.getResult("Area");
           return Response.ok(areaDto).build();
        } catch (Exception ex) {
            Logger.getLogger(AreaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the area.").build();
        }
    }
    
    @POST
    @Path("/saveArea")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveArea(AreaDto areaDto) {
    try {
            LocalResponse res=areaService.saveArea(areaDto);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok((AreaDto)res.getResult("Area")).build();
        } catch (Exception ex) {
            Logger.getLogger(AreaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while saving the area.").build();
        }
    }
    
    @GET
    @Path("/getAreas")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAreas() {
        try {
            LocalResponse res=areaService.getAreas();
            if(!res.getState()){
              return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            return Response.ok(new GenericEntity<List<AreaDto>>((List<AreaDto>) res.getResult("Areas")) {
            }).build();
        } catch (Exception ex) {
        Logger.getLogger(AreaController.class.getName()).log(Level.SEVERE, null, ex);
        return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the areas.").build();
        }
    }
    
    @DELETE
    @Path("/deleteArea/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response deleteArea(@PathParam("id")Long id) {
        try {
            LocalResponse res=areaService.deleteArea(id);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok().build();
        } catch (Exception ex) {
            Logger.getLogger(AreaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error ocurred while deleting the area").build();
        }
    }
}
