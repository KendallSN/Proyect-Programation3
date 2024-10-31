package cr.ac.una.wssigeceuna.model;

import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class ActivityDto implements Serializable {

    private Long actId;
    private Long actIndexpertype;
    private String actName;
    private Collection<SubactivityDto> subactivityCollection;
    private Collection<ManagementDto> managementCollection;
    private Long areId;
    private Long actVersion;

    public ActivityDto() {
    }

    public ActivityDto(Long actId) {
        this.actId = actId;
    }

    public ActivityDto(Long actId, Long actIndexpertype, String actName) {
        this.actId = actId;
        this.actIndexpertype = actIndexpertype;
        this.actName = actName;
    }
    
    public ActivityDto(Activity activity) {
        this.actId = activity.getActId();
        this.actIndexpertype = activity.getActIndexpertype();
        this.actName = activity.getActName();
        this.subactivityCollection = new ArrayList<>();
        for(Subactivity subactivity : activity.getSubactivityCollection()){
            this.subactivityCollection.add(new SubactivityDto(subactivity));
        }
        this.managementCollection = new ArrayList<>();
        for(Management management : activity.getManagementCollection()){
            this.managementCollection.add(new ManagementDto(management));
        }
        this.areId = activity.getAreId().getAreId();
        this.actVersion = activity.getActVersion();
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
    
    public Long getActVersion() {
        return actVersion;
    }

    public void setActVersion(Long actVersion) {
        this.actVersion = actVersion;
    }
    
    @XmlTransient
    public Collection<SubactivityDto> getSubactivityCollection() {
        return subactivityCollection;
    }

    public void setSubactivityCollection(Collection<SubactivityDto> subactivityCollection) {
        this.subactivityCollection = subactivityCollection;
    }

    @XmlTransient
    public Collection<ManagementDto> getManagementCollection() {
        return managementCollection;
    }

    public void setManagementCollection(Collection<ManagementDto> managementCollection) {
        this.managementCollection = managementCollection;
    }

    public Long getAreId() {
        return areId;
    }

    public void setAreId(Long areId) {
        this.areId = areId;
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
        if (!(object instanceof ActivityDto)) {
            return false;
        }
        ActivityDto other = (ActivityDto) object;
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
