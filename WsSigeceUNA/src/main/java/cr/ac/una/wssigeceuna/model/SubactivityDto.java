package cr.ac.una.wssigeceuna.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class SubactivityDto implements Serializable {

    private Long sactId;
    private Long sactIndexpertype;
    private String sactName;
    private Long actId;
    private Long sactVersion;
    
    public SubactivityDto() {
    }

    public SubactivityDto(Long sactId) {
        this.sactId = sactId;
    }

    public SubactivityDto(Long sactId, Long sactIndexpertype, String sactName) {
        this.sactId = sactId;
        this.sactIndexpertype = sactIndexpertype;
        this.sactName = sactName;
    }
    public SubactivityDto(Subactivity subactivity) {
        this.sactId = subactivity.getSactId();
        this.sactIndexpertype = subactivity.getSactIndexpertype();
        this.sactName = subactivity.getSactName();
        this.actId = subactivity.getActId().getActId();
        this.sactVersion = subactivity.getSactVersion();
    }

    public Long getSactId() {
        return sactId;
    }

    public void setSactId(Long sactId) {
        this.sactId = sactId;
    }

    public Long getSactIndexpertype() {
        return sactIndexpertype;
    }

    public void setSactIndexpertype(Long sactIndexpertype) {
        this.sactIndexpertype = sactIndexpertype;
    }

    public String getSactName() {
        return sactName;
    }

    public void setSactName(String sactName) {
        this.sactName = sactName;
    }

    public Long getSactVersion() {
        return sactVersion;
    }

    public void setSactVersion(Long sactVersion) {
        this.sactVersion = sactVersion;
    }
    
    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sactId != null ? sactId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubactivityDto)) {
            return false;
        }
        SubactivityDto other = (SubactivityDto) object;
        if ((this.sactId == null && other.sactId != null) || (this.sactId != null && !this.sactId.equals(other.sactId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.wssigeceuna.model.Subactivity[ sactId=" + sactId + " ]";
    }
    
}
