
package cr.ac.una.sigeceunacomunicationws.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

@Entity
@Table(name = "COM_NOTIFICATIONPROCESS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotificationProcess.findAll", query = "SELECT n FROM NotificationProcess n"),
    @NamedQuery(name = "NotificationProcess.findByNtpId", query = "SELECT n FROM NotificationProcess n WHERE n.ntpId = :ntpId"),
    @NamedQuery(name = "NotificationProcess.findByNtpHtml", query = "SELECT n FROM NotificationProcess n WHERE n.ntpHtml = :ntpHtml"),
    @NamedQuery(name = "NotificationProcess.findByNtpDescription", query = "SELECT n FROM NotificationProcess n WHERE n.ntpDescription = :ntpDescription"),
    @NamedQuery(name = "NotificationProcess.findByNtpVersion", query = "SELECT n FROM NotificationProcess n WHERE n.ntpVersion = :ntpVersion")})
public class NotificationProcess implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "NTP_ID_GENERATOR", sequenceName = "NTP_ID_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NTP_ID_GENERATOR")
    @Basic(optional = false)
    @NotNull
    @Column(name = "NTP_ID")
    private Long ntpId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4000)
    @Column(name = "NTP_HTML")
    private String ntpHtml;
    @Size(max = 300)
    @Column(name = "NTP_DESCRIPTION")
    private String ntpDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NTP_VERSION")
    private Long ntpVersion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ntpId")
    private Collection<Emails> emailsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ntpId")
    private Collection<Variable> variableCollection;

    public NotificationProcess() {
    }

    public NotificationProcess(Long ntpId) {
        this.ntpId = ntpId;
    }

    public NotificationProcess(Long ntpId, String ntpHtml, Long ntpVersion) {
        this.ntpId = ntpId;
        this.ntpHtml = ntpHtml;
        this.ntpVersion = ntpVersion;
    }

    public NotificationProcess(NotificationProcessDto notificationProcessDto) {
        this.ntpId=notificationProcessDto.getNtpId();
        update(notificationProcessDto);
    }
    
    public void update(NotificationProcessDto notificationProcessDto) {
        this.ntpDescription=notificationProcessDto.getNtpDescription();
        this.ntpHtml=notificationProcessDto.getNtpHtml();
        this.ntpVersion=notificationProcessDto.getNtpVersion();
    }

    public Long getNtpId() {
        return ntpId;
    }

    public void setNtpId(Long ntpId) {
        this.ntpId = ntpId;
    }

    public String getNtpHtml() {
        return ntpHtml;
    }

    public void setNtpHtml(String ntpHtml) {
        this.ntpHtml = ntpHtml;
    }

    public String getNtpDescription() {
        return ntpDescription;
    }

    public void setNtpDescription(String ntpDescription) {
        this.ntpDescription = ntpDescription;
    }

    public Long getNtpVersion() {
        return ntpVersion;
    }

    public void setNtpVersion(Long ntpVersion) {
        this.ntpVersion = ntpVersion;
    }

    @XmlTransient
    public Collection<Variable> getVariableCollection() {
        return variableCollection;
    }

    public void setVariableCollection(Collection<Variable> variableCollection) {
        this.variableCollection = variableCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ntpId != null ? ntpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificationProcess)) {
            return false;
        }
        NotificationProcess other = (NotificationProcess) object;
        if ((this.ntpId == null && other.ntpId != null) || (this.ntpId != null && !this.ntpId.equals(other.ntpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.sigeceunacomunicationws.model.NotificationProcess[ ntpId=" + ntpId + " ]";
    }

    @XmlTransient
    public Collection<Emails> getEmailsCollection() {
        return emailsCollection;
    }

    public void setEmailsCollection(Collection<Emails> emailsCollection) {
        this.emailsCollection = emailsCollection;
    }
}
