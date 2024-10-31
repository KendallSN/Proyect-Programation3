package cr.ac.una.wssigeceuna.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class MessageDto implements Serializable {

    private Long msgId;
    private Date msgDateandhour;
    private String msgMessage;
    private Long msgVersion;
    private Collection<FileDto> fileCollection;
    private ChatDto chtId;
    private UserDto usrIdSender;

    public MessageDto() {
    }

    public MessageDto(Long msgId) {
        this.msgId = msgId;
    }

    public MessageDto(Long msgId, Date msgDateandhour, String msgMessage, Long msgVersion) {
        this.msgId = msgId;
        this.msgDateandhour = msgDateandhour;
        this.msgMessage = msgMessage;
        this.msgVersion = msgVersion;
    }
    
    public MessageDto(Message message) {
        this.msgId = message.getMsgId();
        this.msgDateandhour = message.getMsgDateandhour();
        this.msgMessage = message.getMsgMessage();
        this.msgVersion = message.getMsgVersion();
        this.fileCollection = new ArrayList<>();
        for(File file : message.getFileCollection()){
            this.fileCollection.add(new FileDto(file));
        }
        this.chtId = new ChatDto(message.getChtId());
        this.usrIdSender = new UserDto(message.getUsrIdSender());
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public Date getMsgDateandhour() {
        return msgDateandhour;
    }

    public void setMsgDateandhour(Date msgDateandhour) {
        this.msgDateandhour = msgDateandhour;
    }

    public String getMsgMessage() {
        return msgMessage;
    }

    public void setMsgMessage(String msgMessage) {
        this.msgMessage = msgMessage;
    }

    public Long getMsgVersion() {
        return msgVersion;
    }

    public void setMsgVersion(Long msgVersion) {
        this.msgVersion = msgVersion;
    }

    @XmlTransient
    public Collection<FileDto> getFileCollection() {
        return fileCollection;
    }

    public void setFileCollection(Collection<FileDto> fileCollection) {
        this.fileCollection = fileCollection;
    }

    public ChatDto getChtId() {
        return chtId;
    }

    public void setChtId(ChatDto chtId) {
        this.chtId = chtId;
    }

    public UserDto getUsrIdSender() {
        return usrIdSender;
    }

    public void setUsrIdSender(UserDto usrIdSender) {
        this.usrIdSender = usrIdSender;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (msgId != null ? msgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MessageDto)) {
            return false;
        }
        MessageDto other = (MessageDto) object;
        if ((this.msgId == null && other.msgId != null) || (this.msgId != null && !this.msgId.equals(other.msgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.wssigeceuna.model.Message[ msgId=" + msgId + " ]";
    }
    
}
