package cr.ac.una.wssigeceuna.model;

import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class NotificationprocessDto implements Serializable {

    private Long ntpId;
    private String ntpHtml;
    private String ntpDescription;
    private Long ntpVersion;
    private Collection<VariableDto> variableCollection;
    private Collection<EmailsDto> emailsCollection;

    public NotificationprocessDto() {
    }

    public NotificationprocessDto(Long ntpId) {
        this.ntpId = ntpId;
    }

    public NotificationprocessDto(Long ntpId, String ntpHtml, Long ntpVersion) {
        this.ntpId = ntpId;
        this.ntpHtml = ntpHtml;
        this.ntpVersion = ntpVersion;
    }
    
    public NotificationprocessDto(Notificationprocess notificationprocess) {
        this.ntpId = notificationprocess.getNtpId();
        this.ntpHtml = notificationprocess.getNtpHtml();
        this.ntpDescription = notificationprocess.getNtpDescription();
        this.ntpVersion = notificationprocess.getNtpVersion();
        this.variableCollection = new ArrayList<>();
        for(Variable variable : notificationprocess.getVariableCollection()){
            this.variableCollection.add(new VariableDto(variable));
        }
        this.emailsCollection = new ArrayList<>();
        for(Emails emails : notificationprocess.getEmailsCollection()){
            this.emailsCollection.add(new EmailsDto(emails));
        }
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
    public Collection<VariableDto> getVariableCollection() {
        return variableCollection;
    }

    public void setVariableCollection(Collection<VariableDto> variableCollection) {
        this.variableCollection = variableCollection;
    }

    @XmlTransient
    public Collection<EmailsDto> getEmailsCollection() {
        return emailsCollection;
    }

    public void setEmailsCollection(Collection<EmailsDto> emailsCollection) {
        this.emailsCollection = emailsCollection;
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
        if (!(object instanceof NotificationprocessDto)) {
            return false;
        }
        NotificationprocessDto other = (NotificationprocessDto) object;
        if ((this.ntpId == null && other.ntpId != null) || (this.ntpId != null && !this.ntpId.equals(other.ntpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.wssigeceuna.model.Notificationprocess[ ntpId=" + ntpId + " ]";
    }
    
}
