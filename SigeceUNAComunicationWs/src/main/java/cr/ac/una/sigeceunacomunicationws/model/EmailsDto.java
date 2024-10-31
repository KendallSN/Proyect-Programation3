package cr.ac.una.sigeceunacomunicationws.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class EmailsDto{

    private Long emlId;
    private String emlEmailaddress;
    private String emlSent;
    private String emlHtml;
    private Long emlVersion;
    private Date emlSentdate;
    private Long ntpId;
    private Collection<FileDto> fileCollection;

    public EmailsDto() {
    }

    public EmailsDto(Long emlId) {
        this.emlId = emlId;
    }

    public EmailsDto(Long emlId, String emlEmailaddress, String emlSent, String emlHtml, Long emlVersion) {
        this.emlId = emlId;
        this.emlEmailaddress = emlEmailaddress;
        this.emlSent = emlSent;
        this.emlHtml = emlHtml;
        this.emlVersion = emlVersion;
    }

    public EmailsDto(Emails emails) {
        this.emlId = emails.getEmlId();
        this.emlEmailaddress = emails.getEmlEmailaddress();
        this.emlSent = emails.getEmlSent();
        this.emlHtml = emails.getEmlHtml();
        this.emlVersion = emails.getEmlVersion();
        this.emlSentdate = emails.getEmlSentdate();
        this.ntpId = emails.getNtpId().getNtpId();
        this.fileCollection = new ArrayList<>();
        if(emails.getFileCollection() != null){
            emails.getFileCollection().stream().forEach(e->this.fileCollection.add(new FileDto(e)));
        }
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

    public Long getNtpId() {
        return ntpId;
    }

    public void setNtpId(Long ntpId) {
        this.ntpId = ntpId;
    }

    public Collection<FileDto> getFileCollection() {
        return fileCollection;
    }

    public void setFileCollection(Collection<FileDto> fileCollection) {
        this.fileCollection = fileCollection;
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
        if (!(object instanceof EmailsDto)) {
            return false;
        }
        EmailsDto other = (EmailsDto) object;
        if ((this.emlId == null && other.emlId != null) || (this.emlId != null && !this.emlId.equals(other.emlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.sigeceunacomunicationws.model.Emails[ emlId=" + emlId + " ]";
    }
    
}
