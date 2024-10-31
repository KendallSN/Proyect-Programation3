
package cr.ac.una.sigeceunamessaging.model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;


public class SystemsDto{
    public SimpleStringProperty systId;
    public Long systVersion;
    public SimpleStringProperty systName;
    public SimpleStringProperty systDescription;
    public SimpleStringProperty asignedRole;
    private Collection<RoleDto> roleCollection;
    
    public SystemsDto() {
         this.systId=new SimpleStringProperty();
         this.systDescription=new SimpleStringProperty();
         this.systName=new SimpleStringProperty();
         this.asignedRole=new SimpleStringProperty("Sin asignar");
         this.roleCollection= new ArrayList();
    }

    public Long getSystId() {
        if (this.systId.get() != null && !this.systId.get().isEmpty()) {
            return Long.valueOf(this.systId.get());
        } else {
            return null;
        }
    }

    public void setSystId(Long systId) {
        this.systId.set(systId.toString());
    }

    public Long getSystVersion() {
        return systVersion;
    }

    public void setSystVersion(Long systVersion) {
        this.systVersion = systVersion;
    }

    public String getSystName() {
        return systName.get();
    }

    public void setSystName(String systName) {
        this.systName.set(systName);
    }

    public String getSystDescription() {
        return systDescription.get();
    }

    public void setSystDescription(String systDescription) {
        this.systDescription.set(systDescription);
    }

    public String getAsignedRole() {
        return asignedRole.get();
    }

    public void setAsignedRole(String asignedRole) {
        this.asignedRole.set(asignedRole);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.systId);
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
        final SystemsDto other = (SystemsDto) obj;
        return Objects.equals(this.systId, other.systId);
    }

    @Override
    public String toString() {
        return "SystemsDto{" + "Id=" + systId + ", nombre=" + systName + ", descripcion=" + systDescription + '}';
    }
    
}
