package cr.ac.una.wssigeceuna.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
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
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "COM_USER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUsrId", query = "SELECT u FROM User u WHERE u.usrId = :usrId"),
    @NamedQuery(name = "User.findByUsrName", query = "SELECT u FROM User u WHERE u.usrName = :usrName"),
    @NamedQuery(name = "User.findByUsrLastname", query = "SELECT u FROM User u WHERE u.usrLastname = :usrLastname"),
    @NamedQuery(name = "User.findByUsrSurname", query = "SELECT u FROM User u WHERE u.usrSurname = :usrSurname"),
    @NamedQuery(name = "User.findByUsrIdentification", query = "SELECT u FROM User u WHERE u.usrIdentification = :usrIdentification"),
    @NamedQuery(name = "User.findByUsrEmail", query = "SELECT u FROM User u WHERE u.usrEmail = :usrEmail"),
    @NamedQuery(name = "User.findByUsrCelphonenumber", query = "SELECT u FROM User u WHERE u.usrCelphonenumber = :usrCelphonenumber"),
    @NamedQuery(name = "User.findByUsrPassword", query = "SELECT u FROM User u WHERE u.usrPassword = :usrPassword"),
    @NamedQuery(name = "User.findByUsrTemppassword", query = "SELECT u FROM User u WHERE u.usrTemppassword = :usrTemppassword"),
    @NamedQuery(name = "User.findByUsrLanguage", query = "SELECT u FROM User u WHERE u.usrLanguage = :usrLanguage"),
    @NamedQuery(name = "User.findByUsrState", query = "SELECT u FROM User u WHERE u.usrState = :usrState"),
    @NamedQuery(name = "User.findByUsrTelephone", query = "SELECT u FROM User u WHERE u.usrTelephone = :usrTelephone"),
    @NamedQuery(name = "User.findByUsrVersion", query = "SELECT u FROM User u WHERE u.usrVersion = :usrVersion")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "USR_ID_GENERATOR", sequenceName = "USR_ID_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USR_ID_GENERATOR")
    @Basic(optional = false)
    @NotNull
    @Column(name = "USR_ID")
    private Long usrId;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "USR_PHOTO")
    private String usrPhoto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "USR_NAME")
    private String usrName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "USR_LASTNAME")
    private String usrLastname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "USR_SURNAME")
    private String usrSurname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "USR_IDENTIFICATION")
    private String usrIdentification;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "USR_EMAIL")
    private String usrEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "USR_CELPHONENUMBER")
    private String usrCelphonenumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "USR_PASSWORD")
    private String usrPassword;
    @Size(max = 30)
    @Column(name = "USR_TEMPPASSWORD")
    private String usrTemppassword;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "USR_LANGUAGE")
    private String usrLanguage;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "USR_STATE")
    private String usrState;
    @Size(max = 30)
    @Column(name = "USR_TELEPHONE")
    private String usrTelephone;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USR_VERSION")
    private Long usrVersion;
    @JoinTable(name = "COM_USERROLE", joinColumns = {
        @JoinColumn(name = "USR_ID", referencedColumnName = "USR_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "ROL_ID", referencedColumnName = "ROL_ID")})
    @ManyToMany
    private Collection<Role> roleCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usrRequestingId")
    private Collection<Management> managementCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usrAssignedId")
    private Collection<Management> managementCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usrIdSender")
    private Collection<Message> messageCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usrIdUser2")
    private Collection<Chat> chatCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usrIdUser1")
    private Collection<Chat> chatCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usrToaproveId")
    private Collection<Managementaprobation> managementaprobationCollection;
    @OneToMany(mappedBy = "usrId")
    private Collection<Tracing> tracingCollection;

    public User() {
    }

    public User(Long usrId) {
        this.usrId = usrId;
    }

    public User(Long usrId, String usrPhoto, String usrName, String usrLastname, String usrSurname, String usrIdentification, String usrEmail, String usrCelphonenumber, String usrPassword, String usrLanguage, String usrState, Long usrVersion) {
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
    
    public User(UserDto user) {
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
        for(RoleDto role : user.getRoleCollection()){
            this.roleCollection.add(new Role(role));
        }
        
//        this.managementCollection = new ArrayList<>();
//        for(ManagementDto management : user.getManagementCollection()){
//            this.managementCollection.add(new Management(management));
//        }
//        
//        this.managementCollection1 = new ArrayList<>();
//        for(ManagementDto management : user.getManagementCollection1()){
//            this.managementCollection1.add(new Management(management));
//        }
//        
//        this.messageCollection = new ArrayList<>();
//        for(MessageDto message : user.getMessageCollection()){
//            this.messageCollection.add(new Message(message));
//        }
//        
//        this.chatCollection = new ArrayList<>();
//        for(ChatDto chat : user.getChatCollection()){
//            this.chatCollection.add(new Chat(chat));
//        }
//        
//        this.chatCollection1 = new ArrayList<>();
//        for(ChatDto chat : user.getChatCollection1()){
//            this.chatCollection1.add(new Chat(chat));
//        }
//        
//        this.managementaprobationCollection = new ArrayList<>();
//        for(ManagementaprobationDto managementaprobation : user.getManagementaprobationCollection()){
//            this.managementaprobationCollection.add(new Managementaprobation(managementaprobation));
//        }
//        
//        this.tracingCollection = new ArrayList<>();
//        for(TracingDto tracing : user.getTracingCollection()){
//            this.tracingCollection.add(new Tracing(tracing));
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
    public Collection<Role> getRoleCollection() {
        return roleCollection;
    }

    public void setRoleCollection(Collection<Role> roleCollection) {
        this.roleCollection = roleCollection;
    }

    @XmlTransient
    public Collection<Management> getManagementCollection() {
        return managementCollection;
    }

    public void setManagementCollection(Collection<Management> managementCollection) {
        this.managementCollection = managementCollection;
    }

    @XmlTransient
    public Collection<Management> getManagementCollection1() {
        return managementCollection1;
    }

    public void setManagementCollection1(Collection<Management> managementCollection1) {
        this.managementCollection1 = managementCollection1;
    }

    @XmlTransient
    public Collection<Message> getMessageCollection() {
        return messageCollection;
    }

    public void setMessageCollection(Collection<Message> messageCollection) {
        this.messageCollection = messageCollection;
    }

    @XmlTransient
    public Collection<Chat> getChatCollection() {
        return chatCollection;
    }

    public void setChatCollection(Collection<Chat> chatCollection) {
        this.chatCollection = chatCollection;
    }

    @XmlTransient
    public Collection<Chat> getChatCollection1() {
        return chatCollection1;
    }

    public void setChatCollection1(Collection<Chat> chatCollection1) {
        this.chatCollection1 = chatCollection1;
    }

    @XmlTransient
    public Collection<Managementaprobation> getManagementaprobationCollection() {
        return managementaprobationCollection;
    }

    public void setManagementaprobationCollection(Collection<Managementaprobation> managementaprobationCollection) {
        this.managementaprobationCollection = managementaprobationCollection;
    }

    @XmlTransient
    public Collection<Tracing> getTracingCollection() {
        return tracingCollection;
    }

    public void setTracingCollection(Collection<Tracing> tracingCollection) {
        this.tracingCollection = tracingCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usrId != null ? usrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
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
