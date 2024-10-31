package cr.ac.una.wssigeceuna.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "COM_AREA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Area.findAll", query = "SELECT a FROM Area a"),
    @NamedQuery(name = "Area.findByAreId", query = "SELECT a FROM Area a WHERE a.areId = :areId"),
    @NamedQuery(name = "Area.findByAreName", query = "SELECT a FROM Area a WHERE a.areName = :areName"),
    @NamedQuery(name = "Area.findByAreState", query = "SELECT a FROM Area a WHERE a.areState = :areState")})
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "ARE_ID_GENERATOR", sequenceName = "ARE_ID_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ARE_ID_GENERATOR")
    @Basic(optional = false)
    @NotNull
    @Column(name = "ARE_ID")
    private Long areId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ARE_NAME")
    private String areName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "ARE_STATE")
    private String areState;
    @OneToMany(mappedBy = "areId")
    private Collection<Activity> activityCollection;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ARE_VERSION")
    private Long areVersion;
    
    public Area() {
    }

    public Area(Long areId) {
        this.areId = areId;
    }

    public Area(Long areId, String areName, String areState) {
        this.areId = areId;
        this.areName = areName;
        this.areState = areState;
    }
    
    public Area(AreaDto areaDto) {
        this.areId = areaDto.getAreId();
        this.areName = areaDto.getAreName();
        this.areState = areaDto.getAreState();
        this.activityCollection = new ArrayList<>();
        for(ActivityDto activityDto : areaDto.getActivityCollection()){
            this.activityCollection.add(new Activity(activityDto));
        }
        this.areVersion = areaDto.getAreVersion();
    }

    public Long getAreId() {
        return areId;
    }

    public void setAreId(Long areId) {
        this.areId = areId;
    }

    public String getAreName() {
        return areName;
    }

    public void setAreName(String areName) {
        this.areName = areName;
    }

    public String getAreState() {
        return areState;
    }

    public void setAreState(String areState) {
        this.areState = areState;
    }

    public Long getAreVersion() {
        return areVersion;
    }

    public void setAreVersion(Long areVersion) {
        this.areVersion = areVersion;
    }
    
    @XmlTransient
    public Collection<Activity> getActivityCollection() {
        return activityCollection;
    }

    public void setActivityCollection(Collection<Activity> activityCollection) {
        this.activityCollection = activityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (areId != null ? areId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Area)) {
            return false;
        }
        Area other = (Area) object;
        if ((this.areId == null && other.areId != null) || (this.areId != null && !this.areId.equals(other.areId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.wssigeceuna.model.Area[ areId=" + areId + " ]";
    }

    public void update(AreaDto areaDto) {
        this.areName = areaDto.getAreName();
        this.areState = areaDto.getAreState();
        this.activityCollection = new ArrayList<>();
        for(ActivityDto activityDto : areaDto.getActivityCollection()){
            this.activityCollection.add(new Activity(activityDto));
        }
        this.areVersion = areaDto.getAreVersion();
    }
    
}
