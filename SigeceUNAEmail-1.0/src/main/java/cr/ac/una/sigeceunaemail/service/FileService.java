package cr.ac.una.sigeceunaemail.service;

import cr.ac.una.sigeceunaemail.model.EmailmanagerDto;
import cr.ac.una.sigeceunaemail.model.FileDto;
import cr.ac.una.sigeceunaemail.util.Request;
import cr.ac.una.sigeceunaemail.util.Response;
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
            Logger.getLogger(NotificationProcessService.class.getName()).log(Level.SEVERE, "An error ocurred while saving the File.", ex);
            return new Response(false, "An error ocurred while saving the File", "saveFile " + ex.getMessage());
        }
    }
}
