package cr.ac.una.sigeceunacomunicationws.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
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
@Table(name = "COM_ROLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r"),
    @NamedQuery(name = "Role.findByRolId", query = "SELECT r FROM Role r WHERE r.rolId = :rolId"),
    @NamedQuery(name = "Role.findByRolName", query = "SELECT r FROM Role r WHERE r.rolName = :rolName"),
    @NamedQuery(name = "Role.findByRolVersion", query = "SELECT r FROM Role r WHERE r.rolVersion = :rolVersion")})
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "ROL_ID_GENERATOR", sequenceName = "ROL_ID_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROL_ID_GENERATOR")
    @Basic(optional = false)
    @NotNull
    @Column(name = "ROL_ID")
    private Long rolId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ROL_NAME")
    private String rolName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ROL_VERSION")
    private Long rolVersion;
    @JoinTable(name = "COM_ROLESYSTEM", joinColumns = {
        @JoinColumn(name = "ROL_ID", referencedColumnName = "ROL_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "SIST_ID", referencedColumnName = "SYST_ID")})
    @ManyToMany
    private Collection<Systems> systemsCollection;
    @ManyToMany(mappedBy = "roleCollection")
    private Collection<User> userCollection;
    @JoinColumn(name = "SYST_ID", referencedColumnName = "SYST_ID")
    @ManyToOne(optional = false)
    private Systems systId;

    public Role() {
    }

    public Role(Long rolId) {
        this.rolId = rolId;
    }

    public Role(Long rolId, String rolName, Long rolVersion) {
        this.rolId = rolId;
        this.rolName = rolName;
        this.rolVersion = rolVersion;
    }
    
    public Role(RoleDto role) {
        this.rolId = role.getRolId();
        this.rolName = role.getRolName();
        this.rolVersion = role.getRolVersion();
//        this.systemsCollection = new ArrayList<>();
//        for(SystemsDto systems : role.getSystemsCollection()){
//            this.systemsCollection.add(new Systems(systems));
//        }
//        
//        this.userCollection = new ArrayList<>();
//        for(UserDto user : role.getUserCollection()){
//            this.userCollection.add(new User(user));
//        }
        this.systId = new Systems(role.getSystId());
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

    @XmlTransient
    public Collection<Systems> getSystemsCollection() {
        return systemsCollection;
    }

    public void setSystemsCollection(Collection<Systems> systemsCollection) {
        this.systemsCollection = systemsCollection;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    public Systems getSystId() {
        return systId;
    }

    public void setSystId(Systems systId) {
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
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
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
