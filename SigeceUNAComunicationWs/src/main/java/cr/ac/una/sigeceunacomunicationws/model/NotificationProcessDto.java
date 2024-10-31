
package cr.ac.una.sigeceunacomunicationws.model;

import java.util.Objects;

public class NotificationProcessDto {
    private Long ntpId;
    private String ntpHtml;
    private String ntpDescription;
    private Long ntpVersion;

    public NotificationProcessDto(NotificationProcess notificationprocess){
        this();
        this.ntpId=notificationprocess.getNtpId();
        this.ntpDescription=notificationprocess.getNtpDescription();
        this.ntpHtml=notificationprocess.getNtpHtml();
        this.ntpVersion=notificationprocess.getNtpVersion();
        
    }
    
    public NotificationProcessDto(Long ntpId, String ntpHtml, String ntpDescription, Long ntpVersion) {
        this.ntpId = ntpId;
        this.ntpHtml = ntpHtml;
        this.ntpDescription = ntpDescription;
        this.ntpVersion = ntpVersion;
    }

    public NotificationProcessDto() {
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.ntpId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
       if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NotificationProcessDto other = (NotificationProcessDto) obj;
        return Objects.equals(this.ntpId, other.ntpId);
    }

    @Override
    public String toString() {
        return "NotificationProcessDto{" + "Id=" + ntpId + "Description" + ntpDescription + "Html" + ntpHtml +'}';
    }
}
