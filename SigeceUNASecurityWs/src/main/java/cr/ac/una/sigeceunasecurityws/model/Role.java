package cr.ac.una.sigeceunasecurityws.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
import java.util.Collection;

/**
 *
 * @author IK
 */
@Entity
@Table(name = "COM_ROLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r"),
    @NamedQuery(name = "Role.findByRolId", query = "SELECT r FROM Role r WHERE r.rolId = :rolId"),
    @NamedQuery(name = "Role.findByRolName", query = "SELECT r FROM Role r WHERE r.rolName = :rolName"),
    @NamedQuery(name = "Role.findByRolSystID", query = "SELECT r FROM Role r WHERE r.systId = :systId"),
    @NamedQuery(name = "Role.findByRolVersion", query = "SELECT r FROM Role r WHERE r.rolVersion = :rolVersion")})
public class Role implements Serializable {
    
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "COM_ROLE_ID_GENERATOR", sequenceName = "ROL_ID_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COM_ROLE_ID_GENERATOR")
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

    public Role(Long rolId, String rolName, Long rolVersion, Systems systId) {
        this.rolId = rolId;
        this.rolName = rolName;
        this.rolVersion = rolVersion;
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

    public Role(RoleDto roleDto) {
        if(roleDto.getRolId()!=null){
            this.rolId = roleDto.getRolId();
        }
        update(roleDto);
    }

    public void update(RoleDto roleDto){
        this.rolName=roleDto.getRolName();
        this.rolVersion=roleDto.getRolVersion();
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
        return "cr.ac.una.sigeceunasecurityws.model.Role[ rolId=" + rolId + " ]";
    }
    
    RoleDto getRoleDto() {
        cr.ac.una.sigeceunasecurityws.model.RoleDto roleDto= new cr.ac.una.sigeceunasecurityws.model.RoleDto();
        roleDto.setRolId(rolId);
        roleDto.setRolName(rolName);
        roleDto.setRolVersion(rolVersion);
        roleDto.setSystId(systId.getSystId());
        return roleDto;
    }
    
}
