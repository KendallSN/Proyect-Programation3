package cr.ac.una.wssigeceuna.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class ChatDto implements Serializable {

    private Long chtId;
    private Long chtVersion;
    private Collection<MessageDto> messageCollection;
    private UserDto usrIdUser2;
    private UserDto usrIdUser1;

    public ChatDto() {
    }

    public ChatDto(Long chtId) {
        this.chtId = chtId;
    }

    public ChatDto(Long chtId, Long chtVersion) {
        this.chtId = chtId;
        this.chtVersion = chtVersion;
    }
    
    public ChatDto(Chat chat) {
        this.chtId = chat.getChtId();
        this.chtVersion = chat.getChtVersion();
        this.messageCollection = new ArrayList<>();
        for(Message message : chat.getMessageCollection()){
            this.messageCollection.add(new MessageDto(message));
        }
        this.usrIdUser1 = new UserDto(chat.getUsrIdUser1());
        this.usrIdUser2 = new UserDto(chat.getUsrIdUser2());
    }

    public Long getChtId() {
        return chtId;
    }

    public void setChtId(Long chtId) {
        this.chtId = chtId;
    }

    public Long getChtVersion() {
        return chtVersion;
    }

    public void setChtVersion(Long chtVersion) {
        this.chtVersion = chtVersion;
    }

    @XmlTransient
    public Collection<MessageDto> getMessageCollection() {
        return messageCollection;
    }

    public void setMessageCollection(Collection<MessageDto> messageCollection) {
        this.messageCollection = messageCollection;
    }

    public UserDto getUsrIdUser2() {
        return usrIdUser2;
    }

    public void setUsrIdUser2(UserDto usrIdUser2) {
        this.usrIdUser2 = usrIdUser2;
    }

    public UserDto getUsrIdUser1() {
        return usrIdUser1;
    }

    public void setUsrIdUser1(UserDto usrIdUser1) {
        this.usrIdUser1 = usrIdUser1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chtId != null ? chtId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChatDto)) {
            return false;
        }
        ChatDto other = (ChatDto) object;
        if ((this.chtId == null && other.chtId != null) || (this.chtId != null && !this.chtId.equals(other.chtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.wssigeceuna.model.Chat[ chtId=" + chtId + " ]";
    }
    
}
