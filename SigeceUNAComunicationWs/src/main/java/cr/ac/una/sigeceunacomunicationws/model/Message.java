
package cr.ac.una.sigeceunacomunicationws.model;

import cr.ac.una.sigeceunacomunicationws.service.ChatService;
import cr.ac.una.sigeceunacomunicationws.service.UserService;
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
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@Entity
@Table(name = "COM_MESSAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name="Message.findByChat",query="SELECT m FROM Message m WHERE m.chtId= :chtId"),
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"),
    @NamedQuery(name = "Message.findByMsgId", query = "SELECT m FROM Message m WHERE m.msgId = :msgId"),
    @NamedQuery(name = "Message.findByMsgDateandhour", query = "SELECT c FROM Message c WHERE c.msgDateAndHour = :msgDateAndHour"),
    @NamedQuery(name = "Message.findByMsgMessage", query = "SELECT m FROM Message m WHERE m.msgMessage = :msgMessage"),
    @NamedQuery(name = "Message.findByMsgVersion", query = "SELECT m FROM Message m WHERE m.msgVersion = :msgVersion")})
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "MSG_ID_GENERATOR", sequenceName = "MSG_ID_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MSG_ID_GENERATOR")
    @Basic(optional = false)
    @NotNull
    @Column(name = "MSG_ID")
    private Long msgId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MSG_DATEANDHOUR")
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime msgDateAndHour;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "MSG_MESSAGE")
    private String msgMessage;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MSG_VERSION")
    private Long msgVersion;
    @JoinTable(name = "COM_MESSAGEFILE", joinColumns = {
        @JoinColumn(name = "MSG_ID", referencedColumnName = "MSG_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "FLE_ID", referencedColumnName = "FLE_ID")})
    @ManyToMany
    private Collection<File> fileCollection;
    @JoinColumn(name = "CHT_ID", referencedColumnName = "CHT_ID")
    @ManyToOne(optional = false)
    private Chat chtId;
    @JoinColumn(name = "USR_ID_SENDER", referencedColumnName = "USR_ID")
    @ManyToOne(optional = false)
    private User usrIdSender;   
    public Message() {
    }

    public Message(Long msgId) {
        this.msgId = msgId;
    }
    
    public Message(MessageDto messageDto){
        this.msgId=messageDto.getMsgId();
        this.msgVersion=messageDto.getMsgVersion();
        this.msgMessage=messageDto.getMsgMessage();
        this.msgDateAndHour=messageDto.getMsgDateAndHour();
        this.fileCollection = new ArrayList<>();
        if(messageDto.getFileCollection() != null){
            messageDto.getFileCollection().stream().forEach(e->this.fileCollection.add(new File(e)));
        }
    }

    public Message(Long msgId, LocalDateTime msgDateAndHour, String msgMessage, Long msgVersion) {
        this.msgId = msgId;
        this.msgDateAndHour = msgDateAndHour;
        this.msgMessage = msgMessage;
        this.msgVersion = msgVersion;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public LocalDateTime getMsgDateAndHour() {
        return msgDateAndHour;
    }

    public void setMsgDateAndHour(LocalDateTime msgDateAndHour) {
        this.msgDateAndHour = msgDateAndHour;
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

    public Chat getChtId() {
        return chtId;
    }

    public void setChtId(Chat chtId) {
        this.chtId = chtId;
    }

    public User getUsrIdSender() {
        return usrIdSender;
    }

    public void setUsrIdSender(User usrIdSender) {
        this.usrIdSender = usrIdSender;
    }
    
    public Collection<File> getFileCollection() {
        return fileCollection;
    }

    public void setFileCollection(Collection<File> fileCollection) {
        this.fileCollection = fileCollection;
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
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.msgId == null && other.msgId != null) || (this.msgId != null && !this.msgId.equals(other.msgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.sigeceunacomunicationws.model.Message[ msgId=" + msgId + " ]";
    }
    
}
