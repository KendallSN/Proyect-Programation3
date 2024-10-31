
package cr.ac.una.sigeceunamessaging.model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;

public class RoleDto {
    private SimpleStringProperty rolId;
    private SimpleStringProperty rolName;
    private Long rolVersion;
    private Long rolSystId;

     public RoleDto() {
         this.rolId=new SimpleStringProperty();
         this.rolName=new SimpleStringProperty();
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
    
    public Long getSystId() {
        return rolSystId;
    }

    public void setSystId(Long rolSystId) {
        this.rolSystId = rolSystId;
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
    
}
