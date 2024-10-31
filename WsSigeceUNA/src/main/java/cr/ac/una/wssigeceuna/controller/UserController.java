package cr.ac.una.wssigeceuna.controller;

import cr.ac.una.wssigeceuna.model.UserDto;
import cr.ac.una.wssigeceuna.service.UserService;
import cr.ac.una.wssigeceuna.utility.LocalResponse;
import cr.ac.una.wssigeceuna.utility.ResponseCode;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/UserController")
public class UserController {
    @EJB
    UserService userService;
    
    @GET
    @Path("/getUser/{ID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response getUser(@PathParam("ID")Long id) {
        try {
            LocalResponse res=userService.getUserByID(id);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            UserDto userDto=(UserDto)res.getResult("User");
           return Response.ok(userDto).build();
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the user.").build();
        }
    }
    
    @GET
    @Path("/getUsers")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        try {
            LocalResponse res = userService.getUsers();
            if(!res.getState()){
              return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            return Response.ok(new GenericEntity<List<UserDto>>((List<UserDto>) res.getResult("Users")) {
            }).build();
        } catch (Exception ex) {
        Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the users.").build();
        }
    }
}
