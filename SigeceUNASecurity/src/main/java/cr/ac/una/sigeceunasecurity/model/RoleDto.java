package cr.ac.una.sigeceunasecurity.model;

import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;

public class RoleDto {

    private SimpleStringProperty rolId;
    private SimpleStringProperty rolName;
    private Long rolVersion;
    private Long rolSystId;

    public RoleDto() {
        this.rolId = new SimpleStringProperty();
        this.rolName = new SimpleStringProperty();
    }

    public RoleDto(cr.ac.una.sigeceunasecurityws.controller.RoleDto role) {
        this.rolId = new SimpleStringProperty();
        this.rolId.set(String.valueOf(role.getRolId()));
        this.rolName = new SimpleStringProperty();
        this.rolName.set(role.getRolName());
        this.rolVersion = role.getRolVersion();
        this.rolSystId = role.getSystId();
        this.rolSystId = role.getSystId();

    }

    public Long getSystId() {
        return rolSystId;
    }

    public void setSystId(Long rolSystId) {
        this.rolSystId = rolSystId;
    }

    public Long getRolId() {
        if (this.rolId.get() != null && !this.rolId.get().isEmpty()) {
            return Long.valueOf(this.rolId.get());
        } else {
            return null;
        }
    }

    public void setRolId(Long rolId) {
        this.rolId.set(rolId.toString());
    }

    public String getRolName() {
        return rolName.get();
    }

    public void setRolName(String rolName) {
        this.rolName.set(rolName);
    }

    public Long getRolVersion() {
        return rolVersion;
    }

    public void setRolVersion(Long rolVersion) {
        this.rolVersion = rolVersion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.rolId);
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
        return "RoleDto{" + "Id=" + rolId + ", nombre=" + rolName + '}';
    }

    public cr.ac.una.sigeceunasecurityws.controller.RoleDto getRole() {
        cr.ac.una.sigeceunasecurityws.controller.RoleDto role = new cr.ac.una.sigeceunasecurityws.controller.RoleDto();
        role.setRolId(this.getRolId());
        role.setRolName(this.getRolName());
        role.setRolVersion(this.rolVersion);
        role.setSystId(this.getSystId());
        return role;
    }

}
