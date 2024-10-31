package cr.ac.una.sigeceunamessaging.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;

public class MessageDto implements Serializable {

    private Long msgId;
    private LocalDateTime msgDateAndHour;
    public SimpleStringProperty msgMessage;
    private Long msgVersion;
    private Long chtId;
    private Long usrIdSender;
    private Collection<FileDto> fileCollection;

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }
    
//    public MessageDto(Long msgId, Long msgVersion) {
//        this.msgId = msgId;        
//        this.msgVersion = msgVersion;    
//    }

    public MessageDto() { 
        this.msgVersion=Long.valueOf("1");
        this.msgDateAndHour=LocalDateTime.now();
        this.msgMessage=new SimpleStringProperty();
        this.fileCollection = new ArrayList<>();
    }

    public Long getMsgVersion() {
        return msgVersion;
    }

    public void setMsgVersion(Long msgVersion) {
        this.msgVersion = msgVersion;
    }

    public LocalDateTime getMsgDateAndHour() {
        return msgDateAndHour;
    }

    public void setMsgDateAndHour(LocalDateTime msgDateAndHour) {
        this.msgDateAndHour = msgDateAndHour;
    }

    public String getMsgMessage() {
        return msgMessage.get();
    }

    public void setMsgMessage(String msgMessage) {
        this.msgMessage.set(msgMessage);
    }

    public Long getChtId() {
        return chtId;
    }

    public void setChtId(Long chtId) {
        this.chtId = chtId;
    }

    public Long getUsrIdSender() {
        return usrIdSender;
    }

    public void setUsrIdSender(Long usrIdSender) {
        this.usrIdSender = usrIdSender;
    }
    
    public Collection<FileDto> getFileCollection() {
        return fileCollection;
    }

    public void setFileCollection(Collection<FileDto> fileCollection) {
        this.fileCollection = fileCollection;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.msgId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
       if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MessageDto other = (MessageDto) obj;
        return Objects.equals(this.msgId, other.msgId);
    }

    @Override
    public String toString() {
        return "MessageDto{" + "Id=" + msgId + '}';
    }
    
}
