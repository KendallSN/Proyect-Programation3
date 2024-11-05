package cr.ac.una.sigeceunacomunicationws.model;

import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class RoleDto implements Serializable {

    private Long rolId;
    private String rolName;
    private Long rolVersion;
    private Long systId;

    public RoleDto() {
    }

    public RoleDto(Long rolId) {
        this.rolId = rolId;
    }

    public RoleDto(Long rolId, String rolName, Long rolVersion) {
        this.rolId = rolId;
        this.rolName = rolName;
        this.rolVersion = rolVersion;
    }
    
    public RoleDto(Role role) {
        this.rolId = role.getRolId();
        this.rolName = role.getRolName();
        this.rolVersion = role.getRolVersion();
//        this.systemsCollection = new ArrayList<>();
//        for(Systems systems : role.getSystemsCollection()){
//            this.systemsCollection.add(new SystemsDto(systems));
//        }
//        
//        this.userCollection = new ArrayList<>();
//        for(User user : role.getUserCollection()){
//            this.userCollection.add(new UserDto(user));
//        }
        this.systId = role.getSystId().getSystId();
    }

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }

    public Long getRolVersion() {
        return rolVersion;
    }

    public void setRolVersion(Long rolVersion) {
        this.rolVersion = rolVersion;
    }

//    @XmlTransient
//    public Collection<SystemsDto> getSystemsCollection() {
//        return systemsCollection;
//    }
//
//    public void setSystemsCollection(Collection<SystemsDto> systemsCollection) {
//        this.systemsCollection = systemsCollection;
//    }
//
//    @XmlTransient
//    public Collection<UserDto> getUserCollection() {
//        return userCollection;
//    }
//
//    public void setUserCollection(Collection<UserDto> userCollection) {
//        this.userCollection = userCollection;
//    }

    public Long getSystId() {
        return systId;
    }

    public void setSystId(Long systId) {
        this.systId = systId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rolId != null ? rolId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoleDto)) {
            return false;
        }
        RoleDto other = (RoleDto) object;
        if ((this.rolId == null && other.rolId != null) || (this.rolId != null && !this.rolId.equals(other.rolId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.wssigeceuna.model.Role[ rolId=" + rolId + " ]";
    }
    
}
