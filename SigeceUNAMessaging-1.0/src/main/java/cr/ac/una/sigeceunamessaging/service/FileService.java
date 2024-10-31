package cr.ac.una.sigeceunamessaging.service;

import cr.ac.una.sigeceunamessaging.model.FileDto;
import cr.ac.una.sigeceunamessaging.util.Request;
import cr.ac.una.sigeceunamessaging.util.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileService {
    
    public Response saveFile(FileDto fileDto){
        try {
            Request request = new Request("FileController/saveFile","comunicationUrl");
            request.post(fileDto);
            if(request.isError()){
                return new Response(false,request.getError(),"");
            }
            FileDto file = (FileDto) request.readEntity(FileDto.class);
            return new Response(true, "", "", "File",file);
        } catch (Exception ex) {
            Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, "An error ocurred while saving the File.", ex);
            return new Response(false, "An error ocurred while saving the File", "saveFile " + ex.getMessage());
        }
    }
    
    public Response deleteFile(Long id){
        try{
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("FileController/deleteFile", "/{id}", parametros,"comunicationUrl");
            request.delete();
            if(request.isError()){
             return new Response(false,request.getError(), "");
            }
            return new Response(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the file", ex);
            return new Response(false, "An error ocurred while deleting the file.", "deleteFile " + ex.getMessage());
        }
    }
}
