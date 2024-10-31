package cr.ac.una.sigeceunasecurity.service;

import cr.ac.una.sigeceunasecurity.util.Request;
import cr.ac.una.sigeceunasecurity.util.Response;
import cr.ac.una.sigeceunasecurity.util.SigeceUNAWsConsumer;
import cr.ac.una.sigeceunasecurityws.controller.UserController;
import cr.ac.una.sigeceunasecurityws.controller.UserDto;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService implements Serializable {
    UserController userController = SigeceUNAWsConsumer.getUserController();

    public Response saveUser(UserDto userDto) {
        try {
            return new Response(true, "", "", "User", userController.saveUser(userDto));
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while saving the user [" + userDto + "]", ex);
            return new Response(false, "An error ocurred while saving the user.", "saveUser " + ex.getMessage());
        }
    }

    public Response validateUser(String identification, String password) {
        try {
            Object user = userController.validateUser(identification, password);
            if (user != null) {
                return new Response(true, "User validated successfully", "", "User", user);
            } else {
                return new Response(false, "Invalid username or password", "");
            }
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error occurred while validating the user with [" + identification + "]" + "[" + password + "]", ex);
            return new Response(false, "An error occurred while validating the user.", "validateUser " + ex.getMessage());
        }
    }
    
    public Response validateUserWithTempPassword(String identification, String Temppassword) {
        try {
            Object user = userController.validateUserWithTempPassword(identification, Temppassword);
            if (user != null) {
                return new Response(true, "User validated successfully", "", "User", user);
            } else {
                return new Response(false, "Invalid username or password", "");
            }
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error occurred while validating the user with [" + identification + "]" + "[" + Temppassword + "]", ex);
            return new Response(false, "An error occurred while validating the user.", "validateUser " + ex.getMessage());
        }
    }
    
    public Response getUsers() {
        try {
            return new Response(true, "", "", "Users", userController.getUsers());
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while queryng the users", ex);
            return new Response(false, "An error ocurred while queryng the users.", "getUsers " + ex.getMessage());
        }
    }
    
    public Response isUserWithEmail(String email){
        try {
            UserDto user=userController.isUserWithEmail(email);
            if (user != null) {   
                return new Response(true, "User email search successfully", "", "User", user);
            } else {
                return new Response(false, "There is no user with this email", "");
            }
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error occurred while searching the user with [" + email + "]" , ex);
            return new Response(false, "An error occurred while searching the user.", "isUserWithEmail " + ex.getMessage());
        }
        }
    public Response sendEmailTempPassword(String email, String tempPassword){
        try{
           Map<String,Object> parameters=new HashMap<>();
           parameters.put("email", email);
           parameters.put("tempPassword", tempPassword);
           Request request=new Request("EmailController/sendEmailTempPassword", "/{email}/{tempPassword}", parameters);
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
    
    public Response sendActivationLink(String email){
        try{
           Map<String,Object> parameters=new HashMap<>();
           parameters.put("email", email);       
           Request request=new Request("EmailController/sendActivationLink", "/{email}", parameters);
           request.get();
           if(request.isError()){
           return new Response(false, request.getError(), "");
           }
           return new Response(true,"","");
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while sending the email with the activation Link. [" + email + "]", ex);
            return new Response(false, "An error ocurred while sending the email with the activation Link.", "sendActivationLink" + ex.getMessage());
        } 
    }
   
    public Response deleteChatsByUser(Long idUser){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", idUser);
            Request request = new Request("ChatController/deleteChatsByUser", "/{id}", parametros);
            request.delete();
            if(request.isError()){
             return new Response(false,request.getError(), "");
            }
            return new Response(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the chat", ex);
            return new Response(false, "An error ocurred while deleting the chat.", "deleteChat " + ex.getMessage());
        }
    }
    public Response deleteUserById(Long IDUser) {
        try {
            deleteChatsByUser(IDUser);
            return new Response(true, "", "", "User", userController.deleteUserByID(IDUser));
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the user", ex);
            return new Response(false, "An error ocurred while deleting the user.", "deleteUserById " + ex.getMessage());
        }
    }
    public Response getUserById(Long ID) {
        try {
            return new Response(true, "", "", "User", userController.getUserByID(ID));
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the user", ex);
            return new Response(false, "An error ocurred while querying the user.", "getUserById " + ex.getMessage());
        }
    }  
}