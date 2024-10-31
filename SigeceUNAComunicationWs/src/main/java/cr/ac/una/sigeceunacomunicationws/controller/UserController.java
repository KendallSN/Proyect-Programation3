package cr.ac.una.sigeceunacomunicationws.controller;

import cr.ac.una.sigeceunacomunicationws.model.UserDto;
import cr.ac.una.sigeceunacomunicationws.service.UserService;
import cr.ac.una.sigeceunacomunicationws.utility.LocalResponse;
import cr.ac.una.sigeceunacomunicationws.utility.ResponseCode;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
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

@Path("/UserController")
public class UserController {
    @EJB
    UserService userService;
    @GET
    @Path("/getUserByEmail/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByEmail(@PathParam ("email")String email) {
        try {
            LocalResponse res=userService.getUserByEmail(email);
            if(!res.getState()){
            return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            UserDto userDto=(UserDto)res.getResult("User");
            return Response.ok(userDto).build();
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("Error obteniendo el usuario").build();
        }
    }
    
    @GET
    @Path("/activateUser/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response activateUser(@PathParam ("email") String email) {
        try {
            LocalResponse res1=userService.getUserByEmail(email);
            UserDto userDto1=(UserDto) res1.getResult("User");
            userDto1.setUsrState("A");
            LocalResponse res=userService.saveUser(userDto1);
            if(!res.getState()){
            return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
            UserDto userDto=(UserDto)res.getResult("User");
            return Response.ok("Usuario activado").build();
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("Error activando el usuario").build();
        }
    }
    @GET
    @Path("/isUserWithEmail/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response isUserWithEmail(@PathParam ("email")String email) {
    try {
           LocalResponse res=userService.isUserWithEmail(email);
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
            LocalResponse res=userService.getUsers();
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
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while querying the user.").build();
        }
    }
}
