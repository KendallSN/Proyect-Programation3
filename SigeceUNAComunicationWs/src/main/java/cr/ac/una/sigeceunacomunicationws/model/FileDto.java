package cr.ac.una.sigeceunacomunicationws.model;

import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class FileDto implements Serializable {

    private Long fleId;
    private String fleType;
    private String fleName;
    private Byte[] fleContent;
//    private Collection<MessageDto> messageCollection;
//    private Collection<TracingDto> tracingCollection;
//    private Collection<ManagementDto> managementCollection;
//    private Collection<EmailsDto> emailsCollection;
    private Long fleVersion;
    
    public FileDto() {
    }

    public FileDto(Long fleId) {
        this.fleId = fleId;
    }

    public FileDto(Long fleId, String fleType, String fleName, Byte[] fleContent) {
        this.fleId = fleId;
        this.fleType = fleType;
        this.fleName = fleName;
        this.fleContent = fleContent;
    }
    public FileDto(File file) {
        this.fleId = file.getFleId();
        this.fleType = file.getFleType();
        this.fleName = file.getFleName();
        this.fleContent = file.getFleContent();
//        this.messageCollection = new ArrayList<>();
//        for(Message message : file.getMessageCollection()){
//            this.messageCollection.add(new MessageDto(message));
//        }
//        this.tracingCollection = new ArrayList<>();
//        for(Tracing tracing : file.getTracingCollection()){
//            this.tracingCollection.add(new TracingDto(tracing));
//        }
//        this.emailsCollection = new ArrayList<>();
//        for(Emails emails : file.getEmailsCollection()){
//            this.emailsCollection.add(new EmailsDto(emails));
//        }
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
//    public Collection<MessageDto> getMessageCollection() {
//        return messageCollection;
//    }
//
//    public void setMessageCollection(Collection<MessageDto> messageCollection) {
//        this.messageCollection = messageCollection;
//    }
//
//    @XmlTransient
//    public Collection<TracingDto> getTracingCollection() {
//        return tracingCollection;
//    }
//
//    public void setTracingCollection(Collection<TracingDto> tracingCollection) {
//        this.tracingCollection = tracingCollection;
//    }

//    @XmlTransient
//    public Collection<ManagementDto> getManagementCollection() {
//        return managementCollection;
//    }
//
//    public void setManagementCollection(Collection<ManagementDto> managementCollection) {
//        this.managementCollection = managementCollection;
//    }

//    @XmlTransient
//    public Collection<EmailsDto> getEmailsCollection() {
//        return emailsCollection;
//    }
//
//    public void setEmailsCollection(Collection<EmailsDto> emailsCollection) {
//        this.emailsCollection = emailsCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fleId != null ? fleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FileDto)) {
            return false;
        }
        FileDto other = (FileDto) object;
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
