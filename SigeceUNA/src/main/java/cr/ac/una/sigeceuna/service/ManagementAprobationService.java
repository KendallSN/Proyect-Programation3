
package cr.ac.una.sigeceuna.service;

import cr.ac.una.sigeceuna.model.ManagementaprobationDto;
import cr.ac.una.sigeceuna.util.Request;
import cr.ac.una.sigeceuna.util.Response;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManagementAprobationService {
    public Response getManagementaprobations() {
        try {
            Request request=new Request("ManagementaprobationController/getManagementaprobations","sigeceUrl");
            request.get();
            if(request.isError()){
                return new Response(false,request.getError(),"");
            }
            List<ManagementaprobationDto> managementaprobationDtos = (List<ManagementaprobationDto>) request.readEntity(new GenericType<List<ManagementaprobationDto>>() {});
        return new Response(true,"","","Managementaprobations",managementaprobationDtos);

        } catch (Exception ex) {
            Logger.getLogger(ManagementAprobationService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the Managementaprobations.", ex);
            return new Response(false, "An error ocurred while querying the Managementaprobations.", "getManagementaprobations " + ex.getMessage());
        }
    }
    public Response getManagementaprobationsByManagement(Long id) {
        try {
            Map<String,Object> parametros=new HashMap<>();
           parametros.put("id", id);
            Request request=new Request("ManagementaprobationController/getManagementaprobationsByManagement", "/{id}", parametros,"sigeceUrl");
            request.get();
            if(request.isError()){
                return new Response(false,request.getError(),"");
            }
            List<ManagementaprobationDto> managementaprobationDtos = (List<ManagementaprobationDto>) request.readEntity(new GenericType<List<ManagementaprobationDto>>() {});
        return new Response(true,"","","Managementaprobations",managementaprobationDtos);

        } catch (Exception ex) {
            Logger.getLogger(ManagementAprobationService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the Managementaprobations.", ex);
            return new Response(false, "An error ocurred while querying the Managementaprobations.", "getManagementaprobations " + ex.getMessage());
        }
    }
    
    public Response saveManagementaprobations(ManagementaprobationDto managementaprobationDto){
        try {
            Request request = new Request("ManagementaprobationController/saveManagementaprobation","sigeceUrl");
            request.post(managementaprobationDto);
            if(request.isError()){
             return new Response(false,request.getError(),"");
            }
            ManagementaprobationDto managementaprobation=(ManagementaprobationDto)request.readEntity(ManagementaprobationDto.class);
            return new Response(true, "", "", "Managementaprobation",managementaprobation);
        } catch (Exception ex) {
            Logger.getLogger(ManagementAprobationService.class.getName()).log(Level.SEVERE, "An error ocurred while saving the managementaprobation.", ex);
            return new Response(false, "An error ocurred while saving the managementaprobation", "saveManagementaprobation " + ex.getMessage());
        }
    }
    
    public Response deleteManagementaprobation(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("ManagementaprobationController/deleteManagementaprobation", "/{id}", parametros,"sigeceUrl");
            request.delete();
            if(request.isError()){
             return new Response(false,request.getError(), "");
            }
            return new Response(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(ManagementAprobationService.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the managementaprobation", ex);
            return new Response(false, "An error ocurred while deleting the managementaprobation.", "deleteManagementaprobation " + ex.getMessage());
        }
    }
}
