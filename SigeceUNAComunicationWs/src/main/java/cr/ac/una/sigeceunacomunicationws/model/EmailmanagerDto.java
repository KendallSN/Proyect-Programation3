package cr.ac.una.sigeceunacomunicationws.model;

public class EmailmanagerDto{

    private Long emmId;
    private String emmEmail;
    private String emmPassword;
    private Long emmLimitperhour;
    private Long emmTimeinminutes;
    private Long emmVersion;
    private String emmPort;

    public EmailmanagerDto() {
    }

    public EmailmanagerDto(Long emmId) {
        this.emmId = emmId;
    }

    public EmailmanagerDto(Long emmId, String emmEmail, String emmPassword, Long emmLimitperhour, Long emmTimeinminutes, Long emmVersion, String emmPort) {
        this.emmId = emmId;
        this.emmEmail = emmEmail;
        this.emmPassword = emmPassword;
        this.emmLimitperhour = emmLimitperhour;
        this.emmTimeinminutes = emmTimeinminutes;
        this.emmVersion = emmVersion;
        this.emmPort = emmPort;
    }

    public EmailmanagerDto(Emailmanager emailmanager) {
        this.emmId = emailmanager.getEmmId();
        this.emmEmail = emailmanager.getEmmEmail();
        this.emmPassword = emailmanager.getEmmPassword();
        this.emmLimitperhour = emailmanager.getEmmLimitperhour();
        this.emmTimeinminutes = emailmanager.getEmmTimeinminutes();
        this.emmVersion = emailmanager.getEmmVersion();
        this.emmPort = emailmanager.getEmmPort();
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
        if (!(object instanceof EmailmanagerDto)) {
            return false;
        }
        EmailmanagerDto other = (EmailmanagerDto) object;
        if ((this.emmId == null && other.emmId != null) || (this.emmId != null && !this.emmId.equals(other.emmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.sigeceunacomunicationws.model.Emailmanager[ emmId=" + emmId + " ]";
    }
    
}
