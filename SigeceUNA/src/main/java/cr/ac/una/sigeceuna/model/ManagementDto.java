package cr.ac.una.sigeceuna.model;

import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;

public class ManagementDto implements Serializable {

    private Long mgtId;
    public SimpleStringProperty mgtSubject;
    public SimpleStringProperty mgtDescription;
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
    private String mgtType;
    public ManagementDto() {
        this.mgtDescription=new SimpleStringProperty("");
        this.mgtSubject=new SimpleStringProperty("");
        this.mgtState="In approval";
        this.mgtVersion=1L;
        this.fileCollection=new ArrayList<>();      
    }

    public ManagementDto(Long mgtId) {
        this.mgtId = mgtId;
    }

    public ManagementDto(Long mgtId, String mgtSubject, String mgtDescription, LocalDateTime mgtCreationdate, LocalDateTime mgtMaxdatetosolve, String mgtState) {
        this.mgtId = mgtId;
        this.mgtSubject = new SimpleStringProperty(mgtSubject);
        this.mgtDescription =new SimpleStringProperty(mgtDescription);
        this.mgtCreationdate = mgtCreationdate;
        this.mgtMaxdatetosolve = mgtMaxdatetosolve;
        this.mgtState = mgtState;
    }
    
    public Long getMgtId() {
        return mgtId;
    }

    public void setMgtId(Long mgtId) {
        this.mgtId = mgtId;
    }

    public String getMgtSubject() {
        return mgtSubject.get();
    }

    public void setMgtSubject(String mgtSubject) {
        this.mgtSubject.set(mgtSubject);
    }

    public String getMgtDescription() {
        return mgtDescription.get();
    }

    public void setMgtDescription(String mgtDescription) {
        this.mgtDescription.set(mgtDescription);
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
    
    public String getMgtStateWord() {
        return switch (this.mgtState) {
            case "In approval" -> "En aprovación";
            case "Rejected" -> "Rechazado";
            case "In progress" -> "En progreso";
            case "On hold" -> "En Espera";
            case "Resolved" -> "Resuelta";
            default -> "Error";
        };
    }

    public String getMgtType() {
        return mgtType;
    }

    public void setMgtType(String mgtType) {
        this.mgtType = mgtType;
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
