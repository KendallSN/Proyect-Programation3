
package cr.ac.una.sigeceunasecurityws.model;

import java.io.Serializable;
import java.util.Objects;

public class RoleDto implements Serializable {
    private Long rolId;
    private String rolName;
    private Long rolVersion;
    private Long systId;
    
    public RoleDto() {
    }
    
     public RoleDto(Role role) {
         this();
         this.rolId=role.getRolId();
         this.rolName=role.getRolName();
         this.rolVersion=role.getRolVersion();
         this.systId=role.getSystId().getSystId();
    }

    public RoleDto(Long rolId) {
        this.rolId = rolId;
    }

    public RoleDto(Long rolId, String rolName, Long rolVersion, Long systId) {
        this.rolId = rolId;
        this.rolName = rolName;
        this.rolVersion = rolVersion;
        this.systId = systId;
    }
    
    public Long getSystId() {
        return systId;
    }

    public void setSystId(Long systId) {
        this.systId = systId;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.rolId);
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
        final RoleDto other = (RoleDto) obj;
        return Objects.equals(this.rolId, other.rolId);
    }

    @Override
    public String toString() {
        return "RoleDto{" + "id=" + rolId + ", nombre=" + rolName + '}';
    }
    
}
