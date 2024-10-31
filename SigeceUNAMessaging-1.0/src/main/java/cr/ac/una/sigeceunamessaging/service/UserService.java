package cr.ac.una.sigeceunamessaging.service;

import cr.ac.una.sigeceunamessaging.model.UserDto;
import cr.ac.una.sigeceunamessaging.util.AppContext;
import cr.ac.una.sigeceunamessaging.util.Request;
import cr.ac.una.sigeceunamessaging.util.Response;
import jakarta.ws.rs.core.GenericType;
//import cr.ac.una.sigeceunasecurityws.controller.UserController;
//import cr.ac.una.sigeceunasecurityws.controller.UserDto;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService implements Serializable{
    public Response validateUserRs(String identification,String passWord){
        try{
           Map<String,Object> parameters=new HashMap<>();
           parameters.put("identification", identification);
           parameters.put("passWord", passWord);
           Request request=new Request("UserControllerRs/validateUserRs", "/{identification}/{passWord}", parameters,"securityUrl");
           request.get();
           if(request.isError()){
           return new Response(false, request.getError(), "");
           }
           UserDto userDto=(UserDto)request.readEntity(UserDto.class);
           return new Response(true,"","", "User",userDto);
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the user. [" + identification + "]", ex);
            return new Response(false, "An error ocurred while querying the user.", "validateUserRs" + ex.getMessage());
        } 
    }
    public Response validateUserWithTemppasswordRs(String identification,String Temppassword){
        try{
           Map<String,Object> parameters=new HashMap<>();
           parameters.put("identification", identification);
           parameters.put("Temppassword", Temppassword);
           Request request=new Request("UserControllerRs/validateUserWithTemppasswordRs", "/{identification}/{Temppassword}", parameters,"securityUrl");
           request.get();
           if(request.isError()){
           return new Response(false, request.getError(), "");
           }
           UserDto userDto=(UserDto)request.readEntity(UserDto.class);
           return new Response(true,"","", "User",userDto);
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the user. [" + identification + "]", ex);
            return new Response(false, "An error ocurred while querying the user.", "validateUserRs" + ex.getMessage());
        } 
    }
    public Response isUserWithEmail(String email){
        try{
           System.out.println("inicio");
           Map<String,Object> parameters=new HashMap<>();
           parameters.put("email", email);
           Request request=new Request("UserController/isUserWithEmail", "/{email}", parameters,"comunicationUrl");          
           request.get();
           if(request.isError()){
               System.out.println("error");
           return new Response(false, request.getError(), "");
           }
           UserDto userDto=(UserDto)request.readEntity(UserDto.class);          
           return new Response(true,"","", "User",userDto);
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the user. [" + email + "]", ex);
            return new Response(false, "An error ocurred while querying the user.", "validateUser" + ex.getMessage());
        } 
    }
    public Response sendEmailTempPassword(String email, String tempPassword){
        try{
           Map<String,Object> parameters=new HashMap<>();
           parameters.put("email", email);
           parameters.put("tempPassword", tempPassword);
           Request request=new Request("EmailController/sendEmailTempPassword", "/{email}/{tempPassword}", parameters,"comunicationUrl");
           request.get();
           if(request.isError()){
           return new Response(false, request.getError(), "");
           }
           return new Response(true,"","");
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while sending the email with the tempPassword. [" + email + "]", ex);
            return new Response(false, "An error ocurred while sending the email with the tempPassword.", "sendEmailTempPassword" + ex.getMessage());
        } 
    }
    public Response saveUser(UserDto userDto){
        try {
            Request request = new Request("UserControllerRs/saveUserRs","securityUrl");
            request.post(userDto);
            if(request.isError()){
             return new Response(false,request.getError(),"");
            }
            UserDto user=(UserDto)request.readEntity(UserDto.class);
            return new Response(true, "", "", "User",user);
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while saving the user.", ex);
            return new Response(false, "An error ocurred while saving the user", "saveUserRs " + ex.getMessage());
        }
    }
    public Response getUsers() {
         try {
        Request request=new Request("UserController/getUsers","comunicationUrl");
        request.get();

        if(request.isError()){
        return new Response(false,request.getError(),"");
        }
        List<UserDto> userDto = (List<UserDto>) request.readEntity(new GenericType<List<UserDto>>() {});
        return new Response(true,"","","Users",userDto);

        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the users.", ex);
            return new Response(false, "An error ocurred while querying the users.", "getUsers " + ex.getMessage());
        }
    }
    
}
