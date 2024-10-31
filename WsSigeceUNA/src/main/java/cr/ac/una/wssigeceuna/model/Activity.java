package cr.ac.una.wssigeceuna.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "COM_ACTIVITY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Activity.findAll", query = "SELECT a FROM Activity a"),
    @NamedQuery(name = "Activity.findByActId", query = "SELECT a FROM Activity a WHERE a.actId = :actId"),
    @NamedQuery(name = "Activity.findByActIndexpertype", query = "SELECT a FROM Activity a WHERE a.actIndexpertype = :actIndexpertype"),
    @NamedQuery(name = "Activity.findByActName", query = "SELECT a FROM Activity a WHERE a.actName = :actName")})
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "ACT_ID_GENERATOR", sequenceName = "ACT_ID_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACT_ID_GENERATOR")
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACT_ID")
    private Long actId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACT_INDEXPERTYPE")
    private Long actIndexpertype;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ACT_NAME")
    private String actName;
    @OneToMany(mappedBy = "actId")
    private Collection<Subactivity> subactivityCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actId")
    private Collection<Management> managementCollection;
    @JoinColumn(name = "ARE_ID", referencedColumnName = "ARE_ID")
    @ManyToOne
    private Area areId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACT_VERSION")
    private Long actVersion;

    public Activity() {
    }

    public Activity(Long actId) {
        this.actId = actId;
    }

    public Activity(Long actId, String actType, Long actIndexpertype, String actName) {
        this.actId = actId;
        this.actIndexpertype = actIndexpertype;
        this.actName = actName;
    }

    public Activity(ActivityDto activityDto) {
        this.actId = activityDto.getActId();
        this.actIndexpertype = activityDto.getActIndexpertype();
        this.actName = activityDto.getActName();
        this.subactivityCollection = new ArrayList<>();
        for(SubactivityDto subactivityDto : activityDto.getSubactivityCollection()){
            this.subactivityCollection.add(new Subactivity(subactivityDto));
        }
        this.managementCollection = new ArrayList<>();
        for(ManagementDto managementDto : activityDto.getManagementCollection()){
            this.managementCollection.add(new Management(managementDto));
        }
        this.areId = new Area(activityDto.getAreId());
        this.actVersion = activityDto.getActVersion();
    }
    
    public void update(ActivityDto activityDto){
        this.actIndexpertype = activityDto.getActIndexpertype();
        this.actName = activityDto.getActName();
        this.subactivityCollection = new ArrayList<>();
        for(SubactivityDto subactivityDto : activityDto.getSubactivityCollection()){
            this.subactivityCollection.add(new Subactivity(subactivityDto));
        }
        this.managementCollection = new ArrayList<>();
        for(ManagementDto managementDto : activityDto.getManagementCollection()){
            this.managementCollection.add(new Management(managementDto));
        }
        this.areId = new Area(activityDto.getAreId());
        this.actVersion = activityDto.getActVersion();
    }

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public Long getActIndexpertype() {
        return actIndexpertype;
    }

    public void setActIndexpertype(Long actIndexpertype) {
        this.actIndexpertype = actIndexpertype;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    @XmlTransient
    public Collection<Subactivity> getSubactivityCollection() {
        return subactivityCollection;
    }

    public void setSubactivityCollection(Collection<Subactivity> subactivityCollection) {
        this.subactivityCollection = subactivityCollection;
    }

    @XmlTransient
    public Collection<Management> getManagementCollection() {
        return managementCollection;
    }

    public void setManagementCollection(Collection<Management> managementCollection) {
        this.managementCollection = managementCollection;
    }

    public Area getAreId() {
        return areId;
    }

    public void setAreId(Area areId) {
        this.areId = areId;
    }
    
    public Long getActVersion() {
        return actVersion;
    }

    public void setActVersion(Long actVersion) {
        this.actVersion = actVersion;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actId != null ? actId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Activity)) {
            return false;
        }
        Activity other = (Activity) object;
        if ((this.actId == null && other.actId != null) || (this.actId != null && !this.actId.equals(other.actId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.wssigeceuna.model.Activity[ actId=" + actId + " ]";
    }
    
}
