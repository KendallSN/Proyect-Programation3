package cr.ac.una.sigeceunacomunicationws.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name = "COM_EMAILMANAGER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Emailmanager.findAll", query = "SELECT e FROM Emailmanager e"),
    @NamedQuery(name = "Emailmanager.findByEmmId", query = "SELECT e FROM Emailmanager e WHERE e.emmId = :emmId"),
    @NamedQuery(name = "Emailmanager.findByEmmEmail", query = "SELECT e FROM Emailmanager e WHERE e.emmEmail = :emmEmail"),
    @NamedQuery(name = "Emailmanager.findByEmmPassword", query = "SELECT e FROM Emailmanager e WHERE e.emmPassword = :emmPassword"),
    @NamedQuery(name = "Emailmanager.findByEmmLimitperhour", query = "SELECT e FROM Emailmanager e WHERE e.emmLimitperhour = :emmLimitperhour"),
    @NamedQuery(name = "Emailmanager.findByEmmTimeinminutes", query = "SELECT e FROM Emailmanager e WHERE e.emmTimeinminutes = :emmTimeinminutes"),
    @NamedQuery(name = "Emailmanager.findByEmmVersion", query = "SELECT e FROM Emailmanager e WHERE e.emmVersion = :emmVersion"),
    @NamedQuery(name = "Emailmanager.findByEmmPort", query = "SELECT e FROM Emailmanager e WHERE e.emmPort = :emmPort")})
public class Emailmanager implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMM_ID")
    private Long emmId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "EMM_EMAIL")
    private String emmEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "EMM_PASSWORD")
    private String emmPassword;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMM_LIMITPERHOUR")
    private Long emmLimitperhour;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMM_TIMEINMINUTES")
    private Long emmTimeinminutes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMM_VERSION")
    private Long emmVersion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "EMM_PORT")
    private String emmPort;

    public Emailmanager() {
    }

    public Emailmanager(Long emmId) {
        this.emmId = emmId;
    }

    public Emailmanager(Long emmId, String emmEmail, String emmPassword, Long emmLimitperhour, Long emmTimeinminutes, Long emmVersion, String emmPort) {
        this.emmId = emmId;
        this.emmEmail = emmEmail;
        this.emmPassword = emmPassword;
        this.emmLimitperhour = emmLimitperhour;
        this.emmTimeinminutes = emmTimeinminutes;
        this.emmVersion = emmVersion;
        this.emmPort = emmPort;
    }

    public Emailmanager(EmailmanagerDto emailmanagerDto) {
        this.emmEmail = emailmanagerDto.getEmmEmail();
        this.emmPassword = emailmanagerDto.getEmmPassword();
        this.emmLimitperhour = emailmanagerDto.getEmmLimitperhour();
        this.emmTimeinminutes = emailmanagerDto.getEmmTimeinminutes();
        this.emmVersion = emailmanagerDto.getEmmVersion();
        this.emmPort = emailmanagerDto.getEmmPort();
    }

    public Long getEmmId() {
        return emmId;
    }

    public void setEmmId(Long emmId) {
        this.emmId = emmId;
    }

    public String getEmmEmail() {
        return emmEmail;
    }

    public void setEmmEmail(String emmEmail) {
        this.emmEmail = emmEmail;
    }

    public String getEmmPassword() {
        return emmPassword;
    }

    public void setEmmPassword(String emmPassword) {
        this.emmPassword = emmPassword;
    }

    public Long getEmmLimitperhour() {
        return emmLimitperhour;
    }

    public void setEmmLimitperhour(Long emmLimitperhour) {
        this.emmLimitperhour = emmLimitperhour;
    }

    public Long getEmmTimeinminutes() {
        return emmTimeinminutes;
    }

    public void setEmmTimeinminutes(Long emmTimeinminutes) {
        this.emmTimeinminutes = emmTimeinminutes;
    }

    public Long getEmmVersion() {
        return emmVersion;
    }

    public void setEmmVersion(Long emmVersion) {
        this.emmVersion = emmVersion;
    }

    public String getEmmPort() {
        return emmPort;
    }

    public void setEmmPort(String emmPort) {
        this.emmPort = emmPort;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emmId != null ? emmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Emailmanager)) {
            return false;
        }
        Emailmanager other = (Emailmanager) object;
        if ((this.emmId == null && other.emmId != null) || (this.emmId != null && !this.emmId.equals(other.emmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.sigeceunacomunicationws.model.Emailmanager[ emmId=" + emmId + " ]";
    }

    public void update(EmailmanagerDto emailmanagerDto) {
        this.emmEmail=emailmanagerDto.getEmmEmail();
        this.emmPassword=emailmanagerDto.getEmmPassword();
        this.emmLimitperhour=emailmanagerDto.getEmmLimitperhour();
        this.emmPort=emailmanagerDto.getEmmPort();
        this.emmTimeinminutes=emailmanagerDto.getEmmTimeinminutes();
        this.emmVersion=emailmanagerDto.getEmmVersion();
    }
    
}
