
package cr.ac.una.sigeceunacomunicationws.model;

import cr.ac.una.sigeceunacomunicationws.service.UserService;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;


@Entity
@Table(name = "COM_CHAT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Chat.findChatByUsers",query = "SELECT c FROM Chat c WHERE (c.usrIdUser1 = :usrIdUser1 AND c.usrIdUser2 = :usrIdUser2) OR (c.usrIdUser1 = :usrIdUser2 AND c.usrIdUser2 = :usrIdUser1)"),
    @NamedQuery(name = "Chat.findAll", query = "SELECT c FROM Chat c"),
    @NamedQuery(name = "Chat.findByChtId", query = "SELECT c FROM Chat c WHERE c.chtId = :chtId"),
    @NamedQuery(name = "Chat.findByChtVersion", query = "SELECT c FROM Chat c WHERE c.chtVersion = :chtVersion")})
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "CHT_ID_GENERATOR", sequenceName = "CHT_ID_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHT_ID_GENERATOR")
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHT_ID")
    private Long chtId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHT_VERSION")
    private Long chtVersion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chtId")
    private Collection<Message> messageCollection;
    @JoinColumn(name = "USR_ID_USER2", referencedColumnName = "USR_ID")
    @ManyToOne(optional = false)
    private User usrIdUser2;
    @JoinColumn(name = "USR_ID_USER1", referencedColumnName = "USR_ID")
    @ManyToOne(optional = false)
    private User usrIdUser1;
    
    public Chat() {
    }

    public Chat(Long chtId) {
        this.chtId = chtId;
    }
    
    public Chat(ChatDto chatDto){
        this.chtId=chatDto.getChtId();
        this.chtVersion=chatDto.getChtVersion();           
    }

    public Chat(Long chtId, Long chtVersion) {
        this.chtId = chtId;
        this.chtVersion = chtVersion;
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
    public Collection<Message> getMessageCollection() {
        return messageCollection;
    }

    public void setMessageCollection(Collection<Message> messageCollection) {
        this.messageCollection = messageCollection;
    }

    public User getUsrIdUser2() {
        return usrIdUser2;
    }

    public void setUsrIdUser2(User usrIdUser2) {
        this.usrIdUser2 = usrIdUser2;
    }

    public User getUsrIdUser1() {
        return usrIdUser1;
    }

    public void setUsrIdUser1(User usrIdUser1) {
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
        if (!(object instanceof Chat)) {
            return false;
        }
        Chat other = (Chat) object;
        if ((this.chtId == null && other.chtId != null) || (this.chtId != null && !this.chtId.equals(other.chtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.sigeceunacomunicationws.model.Chat[ chtId=" + chtId + " ]";
    }
    
}
