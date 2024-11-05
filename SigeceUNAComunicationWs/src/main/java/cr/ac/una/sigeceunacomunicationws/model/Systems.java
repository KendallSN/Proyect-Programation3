package cr.ac.una.sigeceunacomunicationws.model;

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

@Entity
@Table(name = "COM_SYSTEMS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Systems.findAll", query = "SELECT s FROM Systems s"),
    @NamedQuery(name = "Systems.findBySystId", query = "SELECT s FROM Systems s WHERE s.systId = :systId"),
    @NamedQuery(name = "Systems.findBySystVersion", query = "SELECT s FROM Systems s WHERE s.systVersion = :systVersion"),
    @NamedQuery(name = "Systems.findBySystName", query = "SELECT s FROM Systems s WHERE s.systName = :systName"),
    @NamedQuery(name = "Systems.findBySystDescription", query = "SELECT s FROM Systems s WHERE s.systDescription = :systDescription")})
public class Systems implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "SYST_ID_GENERATOR", sequenceName = "SYST_ID_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SYST_ID_GENERATOR")
    @Basic(optional = false)
    @NotNull
    @Column(name = "SYST_ID")
    private Long systId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SYST_VERSION")
    private Long systVersion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SYST_NAME")
    private String systName;
    @Size(max = 300)
    @Column(name = "SYST_DESCRIPTION")
    private String systDescription;
    @ManyToMany(mappedBy = "systemsCollection")
    private Collection<Role> roleCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "systId")
    private Collection<Role> roleCollection1;

    public Systems() {
    }

    public Systems(Long systId) {
        this.systId = systId;
    }

    public Systems(Long systId, Long systVersion, String systName) {
        this.systId = systId;
        this.systVersion = systVersion;
        this.systName = systName;
    }
    
    public Systems(SystemsDto systems) {
        this.systId = systems.getSystId();
        this.systVersion = systems.getSystVersion();
        this.systName = systems.getSystName();
        this.systDescription = systems.getSystDescription();
        this.roleCollection = new ArrayList<>();
        for(RoleDto role : systems.getRoleCollection()){
            this.roleCollection.add(new Role(role));
        }
        this.roleCollection1 = new ArrayList<>();
        for(RoleDto role : systems.getRoleCollection1()){
            this.roleCollection1.add(new Role(role));
        }
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
    public Collection<Role> getRoleCollection() {
        return roleCollection;
    }

    public void setRoleCollection(Collection<Role> roleCollection) {
        this.roleCollection = roleCollection;
    }

    @XmlTransient
    public Collection<Role> getRoleCollection1() {
        return roleCollection1;
    }

    public void setRoleCollection1(Collection<Role> roleCollection1) {
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
        if (!(object instanceof Systems)) {
            return false;
        }
        Systems other = (Systems) object;
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
