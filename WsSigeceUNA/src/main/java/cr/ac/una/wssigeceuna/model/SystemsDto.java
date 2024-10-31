package cr.ac.una.wssigeceuna.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

public class SystemsDto implements Serializable {

    private Long systId;
    private Long systVersion;
    private String systName;
    private String systDescription;
    private Collection<RoleDto> roleCollection;
    private Collection<RoleDto> roleCollection1;

    public SystemsDto() {
    }

    public SystemsDto(Long systId) {
        this.systId = systId;
    }

    public SystemsDto(Long systId, Long systVersion, String systName) {
        this.systId = systId;
        this.systVersion = systVersion;
        this.systName = systName;
    }
    
    public SystemsDto(Systems systems) {
        this.systId = systems.getSystId();
        this.systVersion = systems.getSystVersion();
        this.systName = systems.getSystName();
        this.systDescription = systems.getSystDescription();
        this.roleCollection = new ArrayList<>();
//        for(Role role : systems.getRoleCollection()){
//            this.roleCollection.add(new RoleDto(role));
//        }
        this.roleCollection1 = new ArrayList<>();
//        for(Role role : systems.getRoleCollection1()){
//            this.roleCollection1.add(new RoleDto(role));
//        }
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

    @XmlTransient
    public Collection<RoleDto> getRoleCollection() {
        return roleCollection;
    }

    public void setRoleCollection(Collection<RoleDto> roleCollection) {
        this.roleCollection = roleCollection;
    }

    @XmlTransient
    public Collection<RoleDto> getRoleCollection1() {
        return roleCollection1;
    }

    public void setRoleCollection1(Collection<RoleDto> roleCollection1) {
        this.roleCollection1 = roleCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (systId != null ? systId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SystemsDto)) {
            return false;
        }
        SystemsDto other = (SystemsDto) object;
        if ((this.systId == null && other.systId != null) || (this.systId != null && !this.systId.equals(other.systId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.wssigeceuna.model.Systems[ systId=" + systId + " ]";
    }
    
}
