
package cr.ac.una.sigeceunasecurityws.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;


public class SystemsDto implements Serializable {
    private Long systId;
    private Long systVersion;
    private String systName;
    private String systDescription;
    private Collection<RoleDto> roleCollection;
    public SystemsDto() {
    }

    public SystemsDto(Long systId) {
        this.systId = systId;
    }
    
    public SystemsDto(Systems systems) {
         this();
         this.systId=systems.getSystId();
         this.systDescription=systems.getSystDescription();
         this.systVersion=systems.getSystVersion();
         this.systName=systems.getSystName();
         this.roleCollection=new ArrayList<>();
         if(systems.getRoleCollection()!=null){
            for (Role role: systems.getRoleCollection()) {
                this.roleCollection.add(new RoleDto(role));
            }
         }
    }
    
    public SystemsDto(Long systId, Long systVersion, String systName) {
        this.systId = systId;
        this.systVersion = systVersion;
        this.systName = systName;
    }

    public Long getSystId() {
        return systId;
    }

    public void setSystId(Long systId) {
        this.systId = systId;
    }

    public Long getSystVersion() {
        return systVersion;
    }

    public void setSystVersion(Long systVersion) {
        this.systVersion = systVersion;
    }

    public String getSystName() {
        return systName;
    }

    public void setSystName(String systName) {
        this.systName = systName;
    }

    public String getSystDescription() {
        return systDescription;
    }

    public void setSystDescription(String systDescription) {
        this.systDescription = systDescription;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.systId);
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
        return "SystemsDto{" + "id=" + systId + ", nombre=" + systName + ", descripcion=" + systDescription + '}';
    }

    public cr.ac.una.sigeceunasecurityws.model.Systems getSystem() {
        cr.ac.una.sigeceunasecurityws.model.Systems system=new cr.ac.una.sigeceunasecurityws.model.Systems();
        system.setSystId(this.systId);
        system.setSystDescription(this.systDescription);
        system.setSystName(this.systName);
        system.setSystVersion(this.systVersion);
        return system;
    }
    
}
