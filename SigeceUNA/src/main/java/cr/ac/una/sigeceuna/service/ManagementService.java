package cr.ac.una.sigeceuna.service;

import cr.ac.una.sigeceuna.model.ManagementDto;
import cr.ac.una.sigeceuna.util.Request;
import cr.ac.una.sigeceuna.util.Response;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManagementService {
    
    public Response getManagements() {
        try {
        Request request=new Request("ManagementController/getManagements","sigeceUrl");
        request.get();

        if(request.isError()){
        return new Response(false,request.getError(),"");
        }
        List<ManagementDto> managementsDtos = (List<ManagementDto>) request.readEntity(new GenericType<List<ManagementDto>>() {});
        return new Response(true,"","","Managements",managementsDtos);

        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the managements.", ex);
            return new Response(false, "An error ocurred while querying the managements.", "getManagements " + ex.getMessage());
        }
    }
    public Response getManagement(Long ID){
        try{
           Map<String,Object> parametros=new HashMap<>();
           parametros.put("id", ID);
           Request request=new Request("ManagementController/getManagement", "/{id}", parametros,"sigeceUrl");
           request.get();
           if(request.isError()){
               return new Response(false, request.getError(), "");
           }
           ManagementDto management=(ManagementDto)request.readEntity(ManagementDto.class);
            return new Response(true,"","", "Management",management);
        } catch (Exception ex) {
            Logger.getLogger(ManagementService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the management. ", ex);
            return new Response(false, "An error ocurred while querying the management", "getManagement " + ex.getMessage());
        }
    }
    
    public Response getManagementsToAprovedBy(Long id) {
        try {
        Map<String,Object> parametros=new HashMap<>();
        parametros.put("id", id);
        Request request=new Request("ManagementController/getManagementsToAprovedBy", "/{id}", parametros,"sigeceUrl");
        request.get();

        if(request.isError()){
        return new Response(false,request.getError(),"");
        }
        List<ManagementDto> managementsDtos = (List<ManagementDto>) request.readEntity(new GenericType<List<ManagementDto>>() {});
        return new Response(true,"","","Managements",managementsDtos);

        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the managements.", ex);
            return new Response(false, "An error ocurred while querying the managements.", "getManagements " + ex.getMessage());
        }
    }
    
    public Response saveManagement(ManagementDto managementDto){
        try {
            Request request = new Request("ManagementController/saveManagement","sigeceUrl");
            request.post(managementDto);
            if(request.isError()){
             return new Response(false,request.getError(),"");
            }
            ManagementDto management=(ManagementDto)request.readEntity(ManagementDto.class);
            return new Response(true, "", "", "Management",management);
        } catch (Exception ex) {
            Logger.getLogger(ManagementService.class.getName()).log(Level.SEVERE, "An error ocurred while saving the management.", ex);
            return new Response(false, "An error ocurred while saving the management", "saveManagement " + ex.getMessage());
        }
    }
    
    public Response deleteManagement(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("ManagementController/deleteManagement", "/{id}", parametros,"sigeceUrl");
            request.delete();
            if(request.isError()){
             return new Response(false,request.getError(), "");
            }
            return new Response(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(AreaService.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the management", ex);
            return new Response(false, "An error ocurred while deleting the management.", "deleteManagement " + ex.getMessage());
        }
    }
}
