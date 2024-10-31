package cr.ac.una.sigeceunasecurityws.model;

import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

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

    public UserDto(User user) {
        this();
        this.usrId=user.getUsrId();
        this.usrPhoto = user.getUsrPhoto();
        this.usrName = user.getUsrName();
        this.usrLastname = user.getUsrLastname();
        this.usrSurname = user.getUsrSurname();
        this.usrIdentification = user.getUsrIdentification();
        this.usrEmail = user.getUsrEmail();
        this.usrCelphonenumber = user.getUsrCelphonenumber();
        this.usrPassword = user.getUsrPassword();
        this.usrTemppassword = user.getUsrTemppassword();
        this.usrLanguage = user.getUsrLanguage();
        this.usrState = user.getUsrState();
        this.usrTelephone = user.getUsrTelephone();
        this.usrVersion = user.getUsrVersion();
        this.roleCollection=new ArrayList<>();
        user.getRoleCollection().stream().forEach(s->this.roleCollection.add(s.getRoleDto()));
    }

    public Long getUsrId() {
        return usrId;
    }

    public void setUsrId(Long usrId) {
        this.usrId = usrId;
    }
    
    public UserDto(Long usrId, String usrPhoto, String usrName, String usrLastname, String usrSurname, String usrIdentification, String usrEmail, String usrCelphonenumber, String usrPassword, String usrTemppassword, String usrLanguage, String usrState, String usrTelephone, Long usrVersion) {
        this.usrId = usrId;
        this.usrPhoto = usrPhoto;
        this.usrName = usrName;
        this.usrLastname = usrLastname;
        this.usrSurname = usrSurname;
        this.usrIdentification = usrIdentification;
        this.usrEmail = usrEmail;
        this.usrCelphonenumber = usrCelphonenumber;
        this.usrPassword = usrPassword;
        this.usrTemppassword = usrTemppassword;
        this.usrLanguage = usrLanguage;
        this.usrState = usrState;
        this.usrTelephone = usrTelephone;
        this.usrVersion = usrVersion;
    }

    public UserDto() {
    }
    
    public Collection<RoleDto> getRoleCollection() {
        return roleCollection;
    }

    public void setRoleCollection(Collection<RoleDto> roleCollection) {
        this.roleCollection = roleCollection;
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
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.usrId);
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
        final UserDto other = (UserDto) obj;
        return Objects.equals(this.usrId, other.usrId);
    }

    @Override
    public String toString() {
        return "UsuarioDto{" + "Id=" + usrId + ", nombre=" + usrName + ", primerApellido=" + usrSurname + ", segundoApellido=" + usrLastname + ", cedula=" + usrIdentification + '}';
    }
    
}
