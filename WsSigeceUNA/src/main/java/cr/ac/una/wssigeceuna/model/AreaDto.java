package cr.ac.una.wssigeceuna.model;

import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class AreaDto implements Serializable {

    private Long areId;
    private String areName;
    private String areState;
    private Collection<ActivityDto> activityCollection;
    private Long areVersion;
    
    public AreaDto() {
    }

    public AreaDto(Long areId) {
        this.areId = areId;
    }

    public AreaDto(Long areId, String areName, String areState) {
        this.areId = areId;
        this.areName = areName;
        this.areState = areState;
    }
    public AreaDto(Area area) {
        this.areId = area.getAreId();
        this.areName = area.getAreName();
        this.areState = area.getAreState();
        this.activityCollection = new ArrayList<>();
        for(Activity activity : area.getActivityCollection()){
            this.activityCollection.add(new ActivityDto(activity));
        }
        this.areVersion = area.getAreVersion();
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
    public Collection<ActivityDto> getActivityCollection() {
        return activityCollection;
    }

    public void setActivityCollection(Collection<ActivityDto> activityCollection) {
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
        if (!(object instanceof AreaDto)) {
            return false;
        }
        AreaDto other = (AreaDto) object;
        if ((this.areId == null && other.areId != null) || (this.areId != null && !this.areId.equals(other.areId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.wssigeceuna.model.Area[ areId=" + areId + " ]";
    }
    
}
