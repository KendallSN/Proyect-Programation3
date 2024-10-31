package cr.ac.una.wssigeceuna.model;

import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ManagementDto implements Serializable {

    private Long mgtId;
    private String mgtSubject;
    private String mgtDescription;
    private LocalDateTime mgtCreationdate;
    private LocalDateTime mgtMaxdatetosolve;
    private String mgtState;
    private LocalDateTime mgtSolvedate;
    private Collection<FileDto> fileCollection;
    private Long actId;
    private Long sactId;
    private UserDto usrRequestingId;
    private UserDto usrAssignedId;
    private Long mgtVersion;
    private Long areId;
    private String areName;
    
    public ManagementDto() {
    }

    public ManagementDto(Long mgtId) {
        this.mgtId = mgtId;
    }

    public ManagementDto(Long mgtId, String mgtSubject, String mgtDescription, LocalDateTime mgtCreationdate, LocalDateTime mgtMaxdatetosolve, String mgtState) {
        this.mgtId = mgtId;
        this.mgtSubject = mgtSubject;
        this.mgtDescription = mgtDescription;
        this.mgtCreationdate = mgtCreationdate;
        this.mgtMaxdatetosolve = mgtMaxdatetosolve;
        this.mgtState = mgtState;
    }
    
    public ManagementDto(Management management) {
        this.mgtId = management.getMgtId();
        this.mgtSubject = management.getMgtSubject();
        this.mgtDescription = management.getMgtDescription();
        this.mgtCreationdate = management.getMgtCreationdate();
        this.mgtMaxdatetosolve = management.getMgtMaxdatetosolve();
        this.mgtSolvedate = management.getMgtSolvedate();
        this.mgtState = management.getMgtState();
        this.fileCollection = new ArrayList<>();
        for(File file : management.getFileCollection()){
            this.fileCollection.add(new FileDto(file));
        }
        this.usrAssignedId = new UserDto(management.getUsrAssignedId());
        this.usrRequestingId = new UserDto(management.getUsrRequestingId());
        this.mgtVersion = management.getMgtVersion();
        if(management.getActId()!=null){
            this.actId = management.getActId().getActId();
        }
        if(management.getSactId()!=null){
            this.sactId = management.getSactId().getSactId();
        }
    }
    
    public Long getMgtId() {
        return mgtId;
    }

    public void setMgtId(Long mgtId) {
        this.mgtId = mgtId;
    }

    public String getMgtSubject() {
        return mgtSubject;
    }

    public void setMgtSubject(String mgtSubject) {
        this.mgtSubject = mgtSubject;
    }

    public String getMgtDescription() {
        return mgtDescription;
    }

    public void setMgtDescription(String mgtDescription) {
        this.mgtDescription = mgtDescription;
    }

    public LocalDateTime getMgtCreationdate() {
        return mgtCreationdate;
    }

    public void setMgtCreationdate(LocalDateTime mgtCreationdate) {
        this.mgtCreationdate = mgtCreationdate;
    }

    public LocalDateTime getMgtMaxdatetosolve() {
        return mgtMaxdatetosolve;
    }

    public void setMgtMaxdatetosolve(LocalDateTime mgtMaxdatetosolve) {
        this.mgtMaxdatetosolve = mgtMaxdatetosolve;
    }

    public String getMgtState() {
        return mgtState;
    }

    public void setMgtState(String mgtState) {
        this.mgtState = mgtState;
    }

    public LocalDateTime getMgtSolvedate() {
        return mgtSolvedate;
    }

    public void setMgtSolvedate(LocalDateTime mgtSolvedate) {
        this.mgtSolvedate = mgtSolvedate;
    }

    @XmlTransient
    public Collection<FileDto> getFileCollection() {
        return fileCollection;
    }

    public void setFileCollection(Collection<FileDto> fileCollection) {
        this.fileCollection = fileCollection;
    }

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public Long getSactId() {
        return sactId;
    }

    public void setSactId(Long sactId) {
        this.sactId = sactId;
    }
    
    @XmlTransient
    public UserDto getUsrRequestingId() {
        return usrRequestingId;
    }
    
    public void setUsrRequestingId(UserDto usrRequestingId) {
        this.usrRequestingId = usrRequestingId;
    }
    @XmlTransient
    public UserDto getUsrAssignedId() {
        return usrAssignedId;
    }

    public void setUsrAssignedId(UserDto usrAssignedId) {
        this.usrAssignedId = usrAssignedId;
    }

    public Long getMgtVersion() {
        return mgtVersion;
    }

    public void setMgtVersion(Long mgtVersion) {
        this.mgtVersion = mgtVersion;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mgtId != null ? mgtId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ManagementDto)) {
            return false;
        }
        ManagementDto other = (ManagementDto) object;
        if ((this.mgtId == null && other.mgtId != null) || (this.mgtId != null && !this.mgtId.equals(other.mgtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.wssigeceuna.model.Management[ mgtId=" + mgtId + " ]";
    }
    
}
