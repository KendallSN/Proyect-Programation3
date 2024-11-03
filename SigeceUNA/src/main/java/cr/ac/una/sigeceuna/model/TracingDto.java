package cr.ac.una.sigeceuna.model;

import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;

public class TracingDto implements Serializable {

    private Long tcgId;
    private LocalDateTime tcgCreationdate;
    private String tcgType;
    private String tcgSolutiontype;
    public SimpleStringProperty tcgSolutiondetail;
    private Collection<FileDto> fileCollection;
    private Long mgtId;
    private UserDto usrId;
    private Long tcgVersion;
    
    public TracingDto() {
        this.fileCollection=new ArrayList<>();
        this.tcgSolutiondetail=new SimpleStringProperty();
        this.tcgVersion=1L;
        this.tcgType="text";
    }

    public TracingDto(Long tcgId) {
        this.tcgId = tcgId;
    }

    public TracingDto(Long tcgId, LocalDateTime tcgCreationdate, String tcgType, String tcgSolutiontype) {
        this.tcgId = tcgId;
        this.tcgCreationdate = tcgCreationdate;
        this.tcgSolutiondetail = new SimpleStringProperty();
        this.tcgType = tcgType;
        this.tcgSolutiontype = tcgSolutiontype;
    }

    public Long getTcgId() {
        return tcgId;
    }

    public void setTcgId(Long tcgId) {
        this.tcgId = tcgId;
    }

    public LocalDateTime getTcgCreationdate() {
        return tcgCreationdate;
    }

    public void setTcgCreationdate(LocalDateTime tcgCreationdate) {
        this.tcgCreationdate = tcgCreationdate;
    }

    public String getTcgType() {
        return tcgType;
    }

    public void setTcgType(String tcgType) {
        this.tcgType = tcgType;
    }

    public String getTcgSolutiontype() {
        return tcgSolutiontype;
    }

    public void setTcgSolutiontype(String tcgSolutiontype) {
        this.tcgSolutiontype = tcgSolutiontype;
    }

    public String getTcgSolutiondetail() {
        return tcgSolutiondetail.get();
    }

    public void setTcgSolutiondetail(String tcgSolutiondetail) {
        this.tcgSolutiondetail.set(tcgSolutiondetail);
    }

    public Long getTcgVersion() {
        return tcgVersion;
    }

    public void setTcgVersion(Long tcgVersion) {
        this.tcgVersion = tcgVersion;
    }
    
    @XmlTransient
    public Collection<FileDto> getFileCollection() {
        return fileCollection;
    }

    public void setFileCollection(Collection<FileDto> fileCollection) {
        this.fileCollection = fileCollection;
    }

    public Long getMgtId() {
        return mgtId;
    }

    public void setMgtId(Long mgtId) {
        this.mgtId = mgtId;
    }

    public UserDto getUsrId() {
        return usrId;
    }

    public void setUsrId(UserDto usrId) {
        this.usrId = usrId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tcgId != null ? tcgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TracingDto)) {
            return false;
        }
        TracingDto other = (TracingDto) object;
        if ((this.tcgId == null && other.tcgId != null) || (this.tcgId != null && !this.tcgId.equals(other.tcgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.wssigeceuna.model.Tracing[ tcgId=" + tcgId + " ]";
    }
    
}
