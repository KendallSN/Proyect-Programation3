package cr.ac.una.wssigeceuna.model;

import java.io.Serializable;

public class ManagementaprobationDto implements Serializable {

    private Long mgtaId;
    private String mgtaState;
    private String mgtaComment;
    private Long mgtId;
    private UserDto usrToaproveId;
    private Long mgtaVersion;
    private String mgtSubject;
    
    public ManagementaprobationDto() {
    }

    public ManagementaprobationDto(Long mgtaId) {
        this.mgtaId = mgtaId;
    }

    public ManagementaprobationDto(Long mgtaId, String mgtaState) {
        this.mgtaId = mgtaId;
        this.mgtaState = mgtaState;
    }
    
    public ManagementaprobationDto(Managementaprobation managementaprobation) {
        this.mgtaId = managementaprobation.getMgtaId();
        this.mgtaState = managementaprobation.getMgtaState();
        this.mgtaComment = managementaprobation.getMgtaComment();
        this.mgtId = managementaprobation.getMgtId().getMgtId();
        this.usrToaproveId = new UserDto(managementaprobation.getUsrToaproveId());
        this.mgtaVersion = managementaprobation.getMgtaVersion();
        this.mgtSubject = managementaprobation.getMgtId().getMgtSubject();
    }

    public String getMgtSubject() {
        return mgtSubject;
    }

    public void setMgtSubject(String mgtSubject) {
        this.mgtSubject = mgtSubject;
    }

    public Long getMgtaId() {
        return mgtaId;
    }

    public void setMgtaId(Long mgtaId) {
        this.mgtaId = mgtaId;
    }

    public String getMgtaState() {
        return mgtaState;
    }

    public void setMgtaState(String mgtaState) {
        this.mgtaState = mgtaState;
    }

    public String getMgtaComment() {
        return mgtaComment;
    }

    public void setMgtaComment(String mgtaComment) {
        this.mgtaComment = mgtaComment;
    }

    public Long getMgtaVersion() {
        return mgtaVersion;
    }

    public void setMgtaVersion(Long mgtaVersion) {
        this.mgtaVersion = mgtaVersion;
    }
    
    public Long getMgtId() {
        return mgtId;
    }

    public void setMgtId(Long mgtId) {
        this.mgtId = mgtId;
    }

    public UserDto getUsrToaproveId() {
        return usrToaproveId;
    }

    public void setUsrToaproveId(UserDto usrToaproveId) {
        this.usrToaproveId = usrToaproveId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mgtaId != null ? mgtaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ManagementaprobationDto)) {
            return false;
        }
        ManagementaprobationDto other = (ManagementaprobationDto) object;
        if ((this.mgtaId == null && other.mgtaId != null) || (this.mgtaId != null && !this.mgtaId.equals(other.mgtaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.wssigeceuna.model.Managementaprobation[ mgtaId=" + mgtaId + " ]";
    }
    
}
