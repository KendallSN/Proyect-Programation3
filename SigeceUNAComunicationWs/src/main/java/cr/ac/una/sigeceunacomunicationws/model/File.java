package cr.ac.una.sigeceunacomunicationws.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "COM_FILE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "File.findAll", query = "SELECT f FROM File f"),
    @NamedQuery(name = "File.findByFleId", query = "SELECT f FROM File f WHERE f.fleId = :fleId"),
    @NamedQuery(name = "File.findByFleType", query = "SELECT f FROM File f WHERE f.fleType = :fleType"),
    @NamedQuery(name = "File.findByFleName", query = "SELECT f FROM File f WHERE f.fleName = :fleName")})
public class File implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "FLE_ID_GENERATOR", sequenceName = "FLE_ID_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FLE_ID_GENERATOR")
    @Basic(optional = false)
    @NotNull
    @Column(name = "FLE_ID")
    private Long fleId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "FLE_TYPE")
    private String fleType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "FLE_NAME")
    private String fleName;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "FLE_CONTENT")
    private Byte[] fleContent;
//    @ManyToMany(mappedBy = "fileCollection")
//    private Collection<Message> messageCollection;
//    @JoinTable(name = "COM_FILETRACING", joinColumns = {
//        @JoinColumn(name = "FLE_ID", referencedColumnName = "FLE_ID")}, inverseJoinColumns = {
//        @JoinColumn(name = "TCG_ID", referencedColumnName = "TCG_ID")})
//    @ManyToMany
//    private Collection<Tracing> tracingCollection;
//    @ManyToMany(mappedBy = "fileCollection")
//    private Collection<Management> managementCollection;
    @ManyToMany(mappedBy = "fileCollection")
    private Collection<Emails> emailsCollection;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FLE_VERSION")
    private Long fleVersion;
    
    public File() {
    }

    public File(Long fleId) {
        this.fleId = fleId;
    }

    public File(Long fleId, String fleType, String fleName, Byte[] fleContent) {
        this.fleId = fleId;
        this.fleType = fleType;
        this.fleName = fleName;
        this.fleContent = fleContent;
    }
    
    public File(FileDto file) {
        this.fleId = file.getFleId();
        this.fleType = file.getFleType();
        this.fleName = file.getFleName();
        this.fleContent = file.getFleContent();
//        this.messageCollection = new ArrayList<>();
//        for(MessageDto message : file.getMessageCollection()){
//            this.messageCollection.add(new Message(message));
//        }
//        this.tracingCollection = new ArrayList<>();
//        for(TracingDto tracing : file.getTracingCollection()){
//            this.tracingCollection.add(new Tracing(tracing));
//        }
//        this.emailsCollection = new ArrayList<>();
//        for(EmailsDto emails : file.getEmailsCollection()){
//            this.emailsCollection.add(new Emails(emails));
//        }
        this.fleVersion = file.getFleVersion();
    }
    
    public void update(FileDto file) {
        this.fleType = file.getFleType();
        this.fleName = file.getFleName();
        this.fleContent = file.getFleContent();
        this.fleVersion = file.getFleVersion();
    }

    public Long getFleId() {
        return fleId;
    }

    public void setFleId(Long fleId) {
        this.fleId = fleId;
    }

    public String getFleType() {
        return fleType;
    }

    public void setFleType(String fleType) {
        this.fleType = fleType;
    }

    public String getFleName() {
        return fleName;
    }

    public void setFleName(String fleName) {
        this.fleName = fleName;
    }

    public Byte[] getFleContent() {
        return fleContent;
    }

    public void setFleContent(Byte[] fleContent) {
        this.fleContent = fleContent;
    }

    public Long getFleVersion() {
        return fleVersion;
    }

    public void setFleVersion(Long fleVersion) {
        this.fleVersion = fleVersion;
    }
    
//    @XmlTransient
//    public Collection<Message> getMessageCollection() {
//        return messageCollection;
//    }
//
//    public void setMessageCollection(Collection<Message> messageCollection) {
//        this.messageCollection = messageCollection;
//    }
//
//    @XmlTransient
//    public Collection<Tracing> getTracingCollection() {
//        return tracingCollection;
//    }
//
//    public void setTracingCollection(Collection<Tracing> tracingCollection) {
//        this.tracingCollection = tracingCollection;
//    }
//
//    @XmlTransient
//    public Collection<Management> getManagementCollection() {
//        return managementCollection;
//    }
//
//    public void setManagementCollection(Collection<Management> managementCollection) {
//        this.managementCollection = managementCollection;
//    }

    @XmlTransient
    public Collection<Emails> getEmailsCollection() {
        return emailsCollection;
    }

    public void setEmailsCollection(Collection<Emails> emailsCollection) {
        this.emailsCollection = emailsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fleId != null ? fleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof File)) {
            return false;
        }
        File other = (File) object;
        if ((this.fleId == null && other.fleId != null) || (this.fleId != null && !this.fleId.equals(other.fleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.wssigeceuna.model.File[ fleId=" + fleId + " ]";
    }
    
}
