package cr.ac.una.sigeceunasecurity.model;

import cr.ac.una.sigeceunasecurity.service.SystemsService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserDto {

    public SimpleStringProperty usrId;
    public SimpleStringProperty usrPhoto;
    public SimpleStringProperty usrName;
    public SimpleStringProperty usrLastname;
    public SimpleStringProperty usrSurname;
    public SimpleStringProperty usrIdentification;
    public SimpleStringProperty usrEmail;
    public SimpleStringProperty usrCelphonenumber;
    public SimpleStringProperty usrPassword;
    public SimpleStringProperty usrTemppassword;
    public ObjectProperty<String> usrLanguage;
    public SimpleBooleanProperty usrState;
    public SimpleStringProperty usrTelephone;
    public Long usrVersion;
    private Collection<RoleDto> roleCollection;

    public UserDto() {
        this.usrId = new SimpleStringProperty("");
        this.usrPhoto = new SimpleStringProperty("");
        this.usrName = new SimpleStringProperty("");
        this.usrLastname = new SimpleStringProperty("");
        this.usrSurname = new SimpleStringProperty("");
        this.usrIdentification = new SimpleStringProperty("");
        this.usrEmail = new SimpleStringProperty("");
        this.usrCelphonenumber = new SimpleStringProperty("");
        this.usrPassword = new SimpleStringProperty("");
        this.usrTemppassword = new SimpleStringProperty();
        this.usrLanguage = new SimpleObjectProperty("ES");
        this.usrState = new SimpleBooleanProperty(false);
        this.usrTelephone = new SimpleStringProperty();
    }

    public UserDto(cr.ac.una.sigeceunasecurityws.controller.UserDto userDto) {
        this.usrId = new SimpleStringProperty();
        this.usrId.set(String.valueOf(userDto.getUsrId()));
        this.usrPhoto = new SimpleStringProperty();
        this.usrPhoto.set(userDto.getUsrPhoto());
        this.usrName = new SimpleStringProperty();
        this.usrName.set(userDto.getUsrName());
        this.usrLastname = new SimpleStringProperty();
        this.usrLastname.set(userDto.getUsrLastname());
        this.usrSurname = new SimpleStringProperty();
        this.usrSurname.set(userDto.getUsrSurname());
        this.usrIdentification = new SimpleStringProperty();
        this.usrIdentification.set(userDto.getUsrIdentification());
        this.usrEmail = new SimpleStringProperty();
        this.usrEmail.set(userDto.getUsrEmail());
        this.usrCelphonenumber = new SimpleStringProperty();
        this.usrCelphonenumber.set(userDto.getUsrCelphonenumber());
        this.usrPassword = new SimpleStringProperty();
        this.usrPassword.set(userDto.getUsrPassword());
        this.usrTemppassword = new SimpleStringProperty();
        this.usrTemppassword.set(userDto.getUsrTemppassword());
        this.usrLanguage = new SimpleObjectProperty("ES");
        this.usrLanguage.set(userDto.getUsrLanguage());
        this.usrState = new SimpleBooleanProperty();
        this.usrState.set(userDto.getUsrState().equals("A"));
        this.usrTelephone = new SimpleStringProperty();
        this.usrTelephone.set(userDto.getUsrTelephone());
        this.usrVersion = userDto.getUsrVersion();
        this.roleCollection = new ArrayList<>();
        for (cr.ac.una.sigeceunasecurityws.controller.RoleDto roleDto : userDto.getRoleCollection()) {
            this.roleCollection.add(new cr.ac.una.sigeceunasecurity.model.RoleDto(roleDto));
        }
    }

    public Collection<RoleDto> getRoleCollection() {
        return roleCollection;
    }

    public void setRoleCollection(Collection<RoleDto> roleCollection) {
        this.roleCollection = roleCollection;
    }

    public cr.ac.una.sigeceunasecurityws.controller.UserDto getUser() {
        cr.ac.una.sigeceunasecurityws.controller.UserDto user = new cr.ac.una.sigeceunasecurityws.controller.UserDto();
        user.setUsrId(this.getUsrId());
        user.setUsrPhoto(this.getUsrPhoto());
        user.setUsrName(this.getUsrName());
        user.setUsrLastname(this.getUsrLastname());
        user.setUsrSurname(this.getUsrSurname());
        user.setUsrIdentification(this.getUsrIdentification());
        user.setUsrEmail(this.getUsrEmail());
        user.setUsrCelphonenumber(this.getUsrCelphonenumber());
        user.setUsrPassword(this.getUsrPassword());
        user.setUsrTemppassword(this.getUsrTemppassword());
        user.setUsrLanguage(this.getUsrLanguage());
        user.setUsrTelephone(this.getUsrTelephone());
        if (this.roleCollection == null) {
            this.roleCollection = new ArrayList<>();
        }
        for (RoleDto roleDto : this.roleCollection) {
            user.getRoleCollection().add(roleDto.getRole());
        }
        user.setUsrState(this.getUsrState());
        user.setUsrVersion(Long.valueOf("1"));

        return user;
    }

    public Long getUsrId() {
        if (this.usrId.get() != null && !this.usrId.get().isEmpty()) {
            return Long.valueOf(this.usrId.get());
        } else {
            return null;
        }
    }

    public void setUsrId(Long usrId) {
        this.usrId.set(usrId.toString());
    }

    public String getUsrPhoto() {
        return usrPhoto.get();
    }

    public void setUsrPhoto(String usrPhoto) {
        this.usrPhoto.set(usrPhoto);
    }

    public String getUsrName() {
        return usrName.get();
    }

    public void setUsrName(String usrName) {
        this.usrName.set(usrName);
    }

    public String getUsrLastname() {
        return usrLastname.get();
    }

    public void setUsrLastname(String usrLastname) {
        this.usrLastname.set(usrLastname);
    }

    public String getUsrSurname() {
        return usrSurname.get();
    }

    public void setUsrSurname(String usrSurname) {
        this.usrSurname.set(usrSurname);
    }

    public String getUsrIdentification() {
        return usrIdentification.get();
    }

    public void setUsrIdentification(String usrIdentification) {
        this.usrIdentification.set(usrIdentification);
    }

    public String getUsrEmail() {
        return usrEmail.get();
    }

    public void setUsrEmail(String usrEmail) {
        this.usrEmail.set(usrEmail);
    }

    public String getUsrCelphonenumber() {
        return usrCelphonenumber.get();
    }

    public void setUsrCelphonenumber(String usrCelphonenumber) {
        this.usrCelphonenumber.set(usrCelphonenumber);
    }

    public String getUsrPassword() {
        return usrPassword.get();
    }

    public void setUsrPassword(String usrPassword) {
        this.usrPassword.set(usrPassword);
    }

    public String getUsrTemppassword() {
        return usrTemppassword.get();
    }

    public void setUsrTemppassword(String usrTemppassword) {
        this.usrTemppassword.set(usrTemppassword);
    }

    public String getUsrLanguage() {
        return usrLanguage.get();
    }

    public void setUsrLanguage(String usrLanguage) {
        this.usrLanguage.set(usrLanguage);
    }

    public String getUsrState() {
        return usrState.get() ? "A" : "I";
    }

    public void setUsrState(String usrState) {
        this.usrState.set(usrState.equals("A"));
    }

    public String getUsrTelephone() {
        return usrTelephone.get();
    }

    public void setUsrTelephone(String usrTelephone) {
        this.usrTelephone.set(usrTelephone);
    }

    public Long getUsrVersion() {
        return usrVersion;
    }

    public void setUsrVersion(Long usrVersion) {
        this.usrVersion = usrVersion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.usrId);
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
        return "UsuarioDto{" + "Id=" + usrId + ", nombre=" + usrName + ", primerApellido=" + usrSurname + ", segundoApellido=" + usrLastname + ", cedula=" + usrIdentification + ", idioma=" + usrLanguage + " ,estado=" + usrState + '}';
    }
    
//Verificar si se guarda como ADMIN
    public boolean isAdmin() {
        SystemsService systemsService=new SystemsService();
        if (roleCollection != null) {
            for (RoleDto role : roleCollection) {
                if ("Admin".equals(role.getRolName())) {                
                    SystemsDto systemsDto=new SystemsDto((cr.ac.una.sigeceunasecurityws.controller.SystemsDto)systemsService.getSystemById(role.getSystId()).getResult("Systems"));
                    if("Security".equals(systemsDto.getSystName())){
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
