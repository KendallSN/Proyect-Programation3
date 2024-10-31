
package cr.ac.una.sigeceunaemail.model;

import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;

public class NotificationProcessDto {
    private Long ntpId;
    public SimpleStringProperty ntpHtml;
    public SimpleStringProperty ntpDescription;
    private Long ntpVersion;

    public NotificationProcessDto() {
        this.ntpDescription=new SimpleStringProperty("");
        this.ntpHtml=new SimpleStringProperty("");
        this.ntpVersion=Long.valueOf("1");
    }

    public Long getNtpId() {
        return ntpId;
    }

    public void setNtpId(Long ntpId) {
        this.ntpId = ntpId;
    }

    public String getNtpHtml() {
        return ntpHtml.get();
    }

    public void setNtpHtml(String ntpHtml) {
        this.ntpHtml.set(ntpHtml);
    }

    public String getNtpDescription() {
        return ntpDescription.get();
    }

    public void setNtpDescription(String ntpDescription) {
        this.ntpDescription.set(ntpDescription);
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
