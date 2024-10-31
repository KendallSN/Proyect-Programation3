package cr.ac.una.sigeceunacomunicationws.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.QueryHint;
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
@Table(name = "COM_USER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUsrId", query = "SELECT u FROM User u WHERE u.usrId = :usrId"),
    @NamedQuery(name = "User.findByUsrPhoto", query = "SELECT u FROM User u WHERE u.usrPhoto = :usrPhoto"),
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
    @NamedQuery(name = "User.findByUsrVersion", query = "SELECT u FROM User u WHERE u.usrVersion = :usrVersion"),
    @NamedQuery(name = "User.findByIdentificationPassword",query = "SELECT u FROM User u WHERE u.usrIdentification = :usrIdentification and u.usrPassword = :usrPassword", hints = @QueryHint(name = "eclipselink.refresh", value = "true")),
    @NamedQuery(name = "User.findByIdentificationTempPassword",query = "SELECT u FROM User u WHERE u.usrIdentification = :usrIdentification and u.usrTemppassword = :usrTemppassword", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))})

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
//    @Size(min = 1, max = 300)
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usrIdSender")
    private Collection<Message> messageCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usrIdUser2")
    private Collection<Chat> chatCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usrIdUser1")
    private Collection<Chat> chatCollection1;
    
    public User() {
    }

    public User(Long usrId) {
        this.usrId = usrId;
    }
    
    public User(UserDto userDto) {
        this.usrId=userDto.getUsrId();
        
        update(userDto);
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
    
    public void update(UserDto userDto) {
       this.usrCelphonenumber=userDto.getUsrCelphonenumber();
        this.usrEmail=userDto.getUsrEmail();
        this.usrIdentification=userDto.getUsrIdentification();
        this.usrLanguage=userDto.getUsrLanguage();
        this.usrState=userDto.getUsrState();
        this.usrLastname=userDto.getUsrLastname();
        this.usrName=userDto.getUsrName();
        this.usrPassword=userDto.getUsrPassword();
        this.usrPhoto=userDto.getUsrPhoto();
        this.usrSurname=userDto.getUsrSurname();
        this.usrVersion=userDto.getUsrVersion();
        this.usrTemppassword=userDto.getUsrTemppassword();
        this.usrTelephone=userDto.getUsrTelephone();
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
        return "cr.ac.una.sigeceunasecurityws.model.User[ usrId=" + usrId + " ]";
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
}