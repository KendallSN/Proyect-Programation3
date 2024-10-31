package cr.ac.una.sigeceunacomunicationws.controller;

import cr.ac.una.sigeceunacomunicationws.model.File;
import cr.ac.una.sigeceunacomunicationws.model.FileDto;
import cr.ac.una.sigeceunacomunicationws.service.FileService;
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/FileController")
public class FileController {
    @EJB
    FileService fileService;
    
    @GET
    @Path("/getFile/{ID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response getFile(@PathParam("ID")Long id) {
        try {
            LocalResponse res = fileService.getFile(id);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            FileDto fileDto=new FileDto((File)res.getResult("File"));
           return Response.ok(fileDto).build();
        } catch (Exception ex) {
            Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the File.").build();
        }
    }
    
    @POST
    @Path("/saveFile")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveTracing(FileDto fileDto) {
    try {
            LocalResponse res = fileService.saveFile(fileDto);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok((FileDto)res.getResult("File")).build();
        } catch (Exception ex) {
            Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while saving the File.").build();
        }
    }
    
    @DELETE
    @Path("/deleteFile/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response deleteFile(@PathParam("id")Long id) {
        try {
            LocalResponse res=fileService.deleteFile(id);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok().build();
        } catch (Exception ex) {
            Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error ocurred while deleting the file").build();
        }
    }
}
