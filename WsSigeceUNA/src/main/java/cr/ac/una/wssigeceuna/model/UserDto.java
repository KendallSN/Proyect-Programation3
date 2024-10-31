package cr.ac.una.wssigeceuna.model;

import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class UserDto implements Serializable {

    private Long usrId;
    private String usrPhoto;
    private String usrName;
    private String usrLastname;
    private String usrSurname;
    private String usrIdentification;
    private String usrEmail;
    private String usrCelphonenumber;
    private String usrPassword;
    private String usrTemppassword;
    private String usrLanguage;
    private String usrState;
    private String usrTelephone;
    private Long usrVersion;
    private Collection<RoleDto> roleCollection;
    private Collection<ManagementDto> managementCollection;
    private Collection<ManagementDto> managementCollection1;
    private Collection<MessageDto> messageCollection;
    private Collection<ChatDto> chatCollection;
    private Collection<ChatDto> chatCollection1;
    private Collection<ManagementaprobationDto> managementaprobationCollection;
//    private Collection<TracingDto> tracingCollection;

    public UserDto() {
    }

    public UserDto(Long usrId) {
        this.usrId = usrId;
    }

    public UserDto(Long usrId, String usrPhoto, String usrName, String usrLastname, String usrSurname, String usrIdentification, String usrEmail, String usrCelphonenumber, String usrPassword, String usrLanguage, String usrState, Long usrVersion) {
        this.usrId = usrId;
        this.usrPhoto = usrPhoto;
        this.usrName = usrName;
        this.usrLastname = usrLastname;
        this.usrSurname = usrSurname;
        this.usrIdentification = usrIdentification;
        this.usrEmail = usrEmail;
        this.usrCelphonenumber = usrCelphonenumber;
        this.usrPassword = usrPassword;
        this.usrLanguage = usrLanguage;
        this.usrState = usrState;
        this.usrVersion = usrVersion;
    }
    
    public UserDto(User user) {
        this.usrId = user.getUsrId();
        this.usrPhoto = user.getUsrPhoto();
        this.usrName = user.getUsrName();
        this.usrLastname = user.getUsrLastname();
        this.usrSurname = user.getUsrSurname();
        this.usrIdentification = user.getUsrIdentification();
        this.usrEmail = user.getUsrEmail();
        this.usrCelphonenumber = user.getUsrCelphonenumber();
        this.usrPassword = user.getUsrPassword();
        this.usrLanguage = user.getUsrLanguage();
        this.usrState = user.getUsrState();
        this.usrVersion = user.getUsrVersion();
        this.usrTemppassword = user.getUsrTemppassword();
        this.usrTelephone = user.getUsrTelephone();
        
        this.roleCollection = new ArrayList<>();
        for(Role role : user.getRoleCollection()){
            this.roleCollection.add(new RoleDto(role));
        }
//        
//        this.managementCollection = new ArrayList<>();
//        for(Management management : user.getManagementCollection()){
//            this.managementCollection.add(new ManagementDto(management));
//        }
//        
//        this.managementCollection1 = new ArrayList<>();
//        for(Management management : user.getManagementCollection1()){
//            this.managementCollection1.add(new ManagementDto(management));
//        }
//        
//        this.messageCollection = new ArrayList<>();
//        for(Message message : user.getMessageCollection()){
//            this.messageCollection.add(new MessageDto(message));
//        }
//        
//        this.chatCollection = new ArrayList<>();
//        for(Chat chat : user.getChatCollection()){
//            this.chatCollection.add(new ChatDto(chat));
//        }
//        
//        this.chatCollection1 = new ArrayList<>();
//        for(Chat chat : user.getChatCollection1()){
//            this.chatCollection1.add(new ChatDto(chat));
//        }
//        
//        this.managementaprobationCollection = new ArrayList<>();
//        for(Managementaprobation managementaprobation : user.getManagementaprobationCollection()){
//            this.managementaprobationCollection.add(new ManagementaprobationDto(managementaprobation));
//        }
        
//        this.tracingCollection = new ArrayList<>();
//        for(Tracing tracing : user.getTracingCollection()){
//            this.tracingCollection.add(new TracingDto(tracing));
//        }
    }

    public Long getUsrId() {
        return usrId;
    }

    public void setUsrId(Long usrId) {
        this.usrId = usrId;
    }

    public String getUsrPhoto() {
        return usrPhoto;
    }

    public void setUsrPhoto(String usrPhoto) {
        this.usrPhoto = usrPhoto;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public String getUsrLastname() {
        return usrLastname;
    }

    public void setUsrLastname(String usrLastname) {
        this.usrLastname = usrLastname;
    }

    public String getUsrSurname() {
        return usrSurname;
    }

    public void setUsrSurname(String usrSurname) {
        this.usrSurname = usrSurname;
    }

    public String getUsrIdentification() {
        return usrIdentification;
    }

    public void setUsrIdentification(String usrIdentification) {
        this.usrIdentification = usrIdentification;
    }

    public String getUsrEmail() {
        return usrEmail;
    }

    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail;
    }

    public String getUsrCelphonenumber() {
        return usrCelphonenumber;
    }

    public void setUsrCelphonenumber(String usrCelphonenumber) {
        this.usrCelphonenumber = usrCelphonenumber;
    }

    public String getUsrPassword() {
        return usrPassword;
    }

    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }

    public String getUsrTemppassword() {
        return usrTemppassword;
    }

    public void setUsrTemppassword(String usrTemppassword) {
        this.usrTemppassword = usrTemppassword;
    }

    public String getUsrLanguage() {
        return usrLanguage;
    }

    public void setUsrLanguage(String usrLanguage) {
        this.usrLanguage = usrLanguage;
    }

    public String getUsrState() {
        return usrState;
    }

    public void setUsrState(String usrState) {
        this.usrState = usrState;
    }

    public String getUsrTelephone() {
        return usrTelephone;
    }

    public void setUsrTelephone(String usrTelephone) {
        this.usrTelephone = usrTelephone;
    }

    public Long getUsrVersion() {
        return usrVersion;
    }

    public void setUsrVersion(Long usrVersion) {
        this.usrVersion = usrVersion;
    }

    @XmlTransient
    public Collection<RoleDto> getRoleCollection() {
        return this.roleCollection;
    }

    public void setRoleCollection(Collection<RoleDto> roleCollection) {
        this.roleCollection = roleCollection;
    }

//    @XmlTransient
//    public Collection<ManagementDto> getManagementCollection() {
//        return managementCollection;
//    }
//
//    public void setManagementCollection(Collection<ManagementDto> managementCollection) {
//        this.managementCollection = managementCollection;
//    }
//
//    @XmlTransient
//    public Collection<ManagementDto> getManagementCollection1() {
//        return managementCollection1;
//    }
//
//    public void setManagementCollection1(Collection<ManagementDto> managementCollection1) {
//        this.managementCollection1 = managementCollection1;
//    }
//
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
//    public Collection<ChatDto> getChatCollection() {
//        return chatCollection;
//    }
//
//    public void setChatCollection(Collection<ChatDto> chatCollection) {
//        this.chatCollection = chatCollection;
//    }
//
//    @XmlTransient
//    public Collection<ChatDto> getChatCollection1() {
//        return chatCollection1;
//    }
//
//    public void setChatCollection1(Collection<ChatDto> chatCollection1) {
//        this.chatCollection1 = chatCollection1;
//    }
//
//    @XmlTransient
//    public Collection<ManagementaprobationDto> getManagementaprobationCollection() {
//        return managementaprobationCollection;
//    }
//
//    public void setManagementaprobationCollection(Collection<ManagementaprobationDto> managementaprobationCollection) {
//        this.managementaprobationCollection = managementaprobationCollection;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usrId != null ? usrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserDto)) {
            return false;
        }
        UserDto other = (UserDto) object;
        if ((this.usrId == null && other.usrId != null) || (this.usrId != null && !this.usrId.equals(other.usrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.wssigeceuna.model.User[ usrId=" + usrId + " ]";
    }
    
}
