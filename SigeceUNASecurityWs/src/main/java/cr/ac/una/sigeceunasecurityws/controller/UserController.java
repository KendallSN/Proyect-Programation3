package cr.ac.una.sigeceunasecurityws.controller;

import cr.ac.una.sigeceunasecurityws.model.UserDto;
import jakarta.ejb.EJB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import cr.ac.una.sigeceunasecurityws.service.UserService;
import cr.ac.una.sigeceunasecurityws.utility.ResponseCode;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/UserControllerRs")
@WebService(serviceName="UserController")
public class UserController {
    @EJB
    UserService userService;
    
    @WebMethod(operationName="validateUser")
    public UserDto validateUser(@WebParam(name="identification")String identification,@WebParam(name="password")String password){
        return (UserDto) userService.validateUser(identification, password).getResult("User");
    }
    
    @WebMethod(operationName="validateUserWithTempPassword")
    public UserDto validateUserWithTempPassword(@WebParam(name="identification")String identification,@WebParam(name="tempPassword")String tempPassword){
        return (UserDto) userService.validateUserWithTempPassword(identification, tempPassword).getResult("User");
    }
    
    @WebMethod(operationName="isUserWithEmail")
    public UserDto isUserWithEmail(@WebParam(name="email")String email){
        return (UserDto) userService.isUserWithEmail(email).getResult("User");
    }
    
    @WebMethod(operationName="getUserByID")
    public UserDto getUserByID(@WebParam(name="ID")Long ID) {
        return (UserDto) userService.getUserByID(ID).getResult("User");
    }
    
    @WebMethod(operationName="getUsers")
    public List<UserDto> getUsers() {
        return (List<UserDto>) userService.getUsers().getResult("Users");
    }
    
    @WebMethod(operationName="saveUser")
    public UserDto saveUser(@WebParam(name="userToRegister")UserDto userDto) {
        return (UserDto) userService.saveUser(userDto).getResult("User");
    }
    
    @WebMethod(operationName="deleteUserByID")
    public Boolean deleteUserByID(@WebParam(name="ID")Long ID) {
        return userService.deleteUserByID(ID).getState();
    }
    @GET
    @Path("/validateUserRs/{identification}/{passWord}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateUserRs(@PathParam ("identification")String identification,@PathParam ("passWord") String passWord) {
    try {
        cr.ac.una.sigeceunasecurityws.utility.Response res=userService.validateUserRs(identification, passWord);
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
    @Path("/validateUserWithTemppasswordRs/{identification}/{Temppassword}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateUserWithTempPasswordRs(@PathParam ("identification")String identification,@PathParam ("Temppassword") String Temppassword) {
    try {
        cr.ac.una.sigeceunasecurityws.utility.Response res=userService.validateUserWithTempPasswordRs(identification, Temppassword);
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
    @POST
    @Path("/saveUserRs")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveUserRs(UserDto userDto) {
    try {
            cr.ac.una.sigeceunasecurityws.utility.Response res=userService.saveUserRs(userDto);
            if(!res.getState()){
                return Response.status(res.getResponseCode().getValue()).entity(res.getMessage()).build();
            }
           return Response.ok((UserDto)res.getResult("User")).build();
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(ResponseCode.INTERNAL_ERROR.getValue()).entity("An error occurred while saving the user.").build();
        }
    }
}

