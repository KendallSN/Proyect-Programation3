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

@Entity
@Table(name = "COM_EMAILS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Emails.findAll", query = "SELECT e FROM Emails e"),
    @NamedQuery(name = "Emails.findByEmlId", query = "SELECT e FROM Emails e WHERE e.emlId = :emlId"),
    @NamedQuery(name = "Emails.findByEmlEmailaddress", query = "SELECT e FROM Emails e WHERE e.emlEmailaddress = :emlEmailaddress"),
    @NamedQuery(name = "Emails.findByEmlSent", query = "SELECT e FROM Emails e WHERE e.emlSent = :emlSent"),
    @NamedQuery(name = "Emails.findByEmlHtml", query = "SELECT e FROM Emails e WHERE e.emlHtml = :emlHtml"),
    @NamedQuery(name = "Emails.findByEmlVersion", query = "SELECT e FROM Emails e WHERE e.emlVersion = :emlVersion"),
    @NamedQuery(name = "Emails.findByEmlSentdate", query = "SELECT e FROM Emails e WHERE e.emlSentdate = :emlSentdate")})
public class Emails implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "EML_ID_GENERATOR", sequenceName = "EML_ID_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EML_ID_GENERATOR")
    @Basic(optional = false)
    @NotNull
    @Column(name = "EML_ID")
    private Long emlId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "EML_EMAILADDRESS")
    private String emlEmailaddress;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "EML_SENT")
    private String emlSent;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4000)
    @Column(name = "EML_HTML")
    private String emlHtml;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EML_VERSION")
    private Long emlVersion;
    @Column(name = "EML_SENTDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emlSentdate;
    @JoinTable(name = "COM_FILEEMAIL", joinColumns = {
        @JoinColumn(name = "EML_ID", referencedColumnName = "EML_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "FLE_ID", referencedColumnName = "FLE_ID")})
    @ManyToMany
    private Collection<File> fileCollection;
    @JoinColumn(name = "NTP_ID", referencedColumnName = "NTP_ID")
    @ManyToOne(optional = false)
    private Notificationprocess ntpId;

    public Emails() {
    }

    public Emails(Long emlId) {
        this.emlId = emlId;
    }

    public Emails(Long emlId, String emlEmailaddress, String emlSent, String emlHtml, Long emlVersion) {
        this.emlId = emlId;
        this.emlEmailaddress = emlEmailaddress;
        this.emlSent = emlSent;
        this.emlHtml = emlHtml;
        this.emlVersion = emlVersion;
    }
    
    public Emails(EmailsDto emails) {
        this.emlId = emails.getEmlId();
        this.emlEmailaddress = emails.getEmlEmailaddress();
        this.emlSent = emails.getEmlSent();
        this.emlHtml = emails.getEmlHtml();
        this.emlVersion = emails.getEmlVersion();
        this.emlSentdate = emails.getEmlSentdate();
        this.fileCollection = new ArrayList<>();
        for(FileDto file : emails.getFileCollection()){
            this.fileCollection.add(new File(file));
        }
        this.ntpId = new Notificationprocess(emails.getNtpId());
    }
    
    public Long getEmlId() {
        return emlId;
    }

    public void setEmlId(Long emlId) {
        this.emlId = emlId;
    }

    public String getEmlEmailaddress() {
        return emlEmailaddress;
    }

    public void setEmlEmailaddress(String emlEmailaddress) {
        this.emlEmailaddress = emlEmailaddress;
    }

    public String getEmlSent() {
        return emlSent;
    }

    public void setEmlSent(String emlSent) {
        this.emlSent = emlSent;
    }

    public String getEmlHtml() {
        return emlHtml;
    }

    public void setEmlHtml(String emlHtml) {
        this.emlHtml = emlHtml;
    }

    public Long getEmlVersion() {
        return emlVersion;
    }

    public void setEmlVersion(Long emlVersion) {
        this.emlVersion = emlVersion;
    }

    public Date getEmlSentdate() {
        return emlSentdate;
    }

    public void setEmlSentdate(Date emlSentdate) {
        this.emlSentdate = emlSentdate;
    }

    @XmlTransient
    public Collection<File> getFileCollection() {
        return fileCollection;
    }

    public void setFileCollection(Collection<File> fileCollection) {
        this.fileCollection = fileCollection;
    }

    public Notificationprocess getNtpId() {
        return ntpId;
    }

    public void setNtpId(Notificationprocess ntpId) {
        this.ntpId = ntpId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emlId != null ? emlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Emails)) {
            return false;
        }
        Emails other = (Emails) object;
        if ((this.emlId == null && other.emlId != null) || (this.emlId != null && !this.emlId.equals(other.emlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.wssigeceuna.model.Emails[ emlId=" + emlId + " ]";
    }
    
}
