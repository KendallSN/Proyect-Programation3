package cr.ac.una.sigeceunamessaging.util;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;
@XmlAccessorType(XmlAccessType.FIELD)
public class Response implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Boolean state;
    private ResponseCode responseCode;   
    private String message;    
    private String internalMessage;
    private HashMap<String, Object> result; 

    public Response() {
        this.result = new HashMap<>();
    }

    public Response(Boolean state, String message, String internalMessage) {
        this.state = state;
        this.message = message;
        this.internalMessage = internalMessage;
        this.result = new HashMap<>();
    }
    
    public Response(Boolean state, String message, String internalMessage, String name, Object result) {
        this.state = state;
        this.message = message;
        this.internalMessage = internalMessage;
        this.result = new HashMap<>();
        this.result.put(name, result);
    }

    public Response(Boolean state, String message, String internalMessage, Object result) {
        this.state = state;
        this.message = message;
        this.internalMessage = internalMessage;
        this.result = new HashMap<>();
        this.result.put("[Object]", result);
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInternalMessage() {
        return internalMessage;
    }

    public void setInternalMessage(String internalMessage) {
        this.internalMessage = internalMessage;
    }
    
    public Object getResult(String name) {
        return result.get(name);
    }
    
    public HashMap getResult(){
        return this.result;
    }
    
    public void setResult(Object result) {
        this.result.put("[Object]", result);
    }

    
}
