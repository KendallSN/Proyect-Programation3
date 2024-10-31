package cr.ac.una.sigeceuna.model;

import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class AreaDto implements Serializable {

    private Long areId;
    public SimpleStringProperty areName;
    public SimpleBooleanProperty areState;
    private Collection<ActivityDto> activityCollection;
    private Long areVersion;
    
    public AreaDto() {
        this.areName=new SimpleStringProperty("");
        this.areState=new SimpleBooleanProperty(true);
        this.areVersion=Long.valueOf(1);
        this.activityCollection= new ArrayList();
    }

    public AreaDto(Long areId) {
        this.areId = areId;
    }

    public AreaDto(Long areId, String areName,String state) {
        this.areId = areId;
        this.areName = new SimpleStringProperty(areName);
        this.areState =new SimpleBooleanProperty(state.equals("A"));
    }

    public Long getAreId() {
        return areId;
    }

    public void setAreId(Long areId) {
        this.areId = areId;
    }

    public String getAreName() {
        return areName.get();
    }

    public void setAreName(String areName) {
        this.areName.set(areName);
    }

    public String getAreState() {
        return areState.get() ? "A" : "I";
    }

    public void setAreState(String areState) {
        this.areState.set(areState.equals("A"));
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
