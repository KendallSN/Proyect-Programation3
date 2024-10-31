package cr.ac.una.sigeceuna.service;

import cr.ac.una.sigeceuna.model.AreaDto;
import cr.ac.una.sigeceuna.util.Request;
import cr.ac.una.sigeceuna.util.Response;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AreaService {
    public Response saveArea(AreaDto areaDto){
        try {
            Request request = new Request("AreaController/saveArea","sigeceUrl");
            request.post(areaDto);
            if(request.isError()){
             return new Response(false,request.getError(),"");
            }
            AreaDto area=(AreaDto)request.readEntity(AreaDto.class);
            return new Response(true, "", "", "Area",area);
        } catch (Exception ex) {
            Logger.getLogger(AreaService.class.getName()).log(Level.SEVERE, "An error ocurred while saving the area.", ex);
            return new Response(false, "An error ocurred while saving the area", "saveArea " + ex.getMessage());
        }
    }
    public Response getArea(Long ID){
        try{
           Map<String,Object> parametros=new HashMap<>();
           parametros.put("id", ID);
           Request request=new Request("AreaController/getArea", "/{id}", parametros,"sigeceUrl");
           request.get();
           if(request.isError()){
               return new Response(false, request.getError(), "");
           }
           AreaDto area=(AreaDto)request.readEntity(AreaDto.class);
            return new Response(true,"","", "Area",area);
        } catch (Exception ex) {
            Logger.getLogger(AreaService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the area. ", ex);
            return new Response(false, "An error ocurred while querying the area", "getArea " + ex.getMessage());
        }
    }
    public Response getAreas() {
        try {
            Request request=new Request("AreaController/getAreas","sigeceUrl");
            request.get();
            if(request.isError()){
                return new Response(false,request.getError(),"");
            }
            List<AreaDto> areasDtos = (List<AreaDto>) request.readEntity(new GenericType<List<AreaDto>>() {});
        return new Response(true,"","","Areas",areasDtos);

        } catch (Exception ex) {
            Logger.getLogger(AreaService.class.getName()).log(Level.SEVERE, "An error ocurred while querying the areas.", ex);
            return new Response(false, "An error ocurred while querying the areas.", "getAreas " + ex.getMessage());
        }
    }
    public Response deleteArea(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("AreaController/deleteArea", "/{id}", parametros,"sigeceUrl");
            request.delete();
            if(request.isError()){
             return new Response(false,request.getError(), "");
            }
            return new Response(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(AreaService.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the area", ex);
            return new Response(false, "An error ocurred while deleting the area.", "deleteArea " + ex.getMessage());
        }
    }
}
