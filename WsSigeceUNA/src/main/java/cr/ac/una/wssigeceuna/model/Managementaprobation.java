package cr.ac.una.wssigeceuna.model;

import cr.ac.una.wssigeceuna.service.ManagementService;
import cr.ac.una.wssigeceuna.service.UserService;
import jakarta.ejb.EJB;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name = "COM_MANAGEMENTAPROBATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Managementaprobation.findAll", query = "SELECT m FROM Managementaprobation m"),
    @NamedQuery(name = "Managementaprobation.findByMgtaId", query = "SELECT m FROM Managementaprobation m WHERE m.mgtaId = :mgtaId"),
    @NamedQuery(name = "Managementaprobation.findByMgtaState", query = "SELECT m FROM Managementaprobation m WHERE m.mgtaState = :mgtaState"),
    @NamedQuery(name = "Managementaprobation.findByMgtaComment", query = "SELECT m FROM Managementaprobation m WHERE m.mgtaComment = :mgtaComment")})
public class Managementaprobation implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "MGTA_ID_GENERATOR", sequenceName = "MGTA_ID_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MGTA_ID_GENERATOR")
    @Basic(optional = false)
    @NotNull
    @Column(name = "MGTA_ID")
    private Long mgtaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "MGTA_STATE")
    private String mgtaState;
    @Size(max = 200)
    @Column(name = "MGTA_COMMENT")
    private String mgtaComment;
    @JoinColumn(name = "MGT_ID", referencedColumnName = "MGT_ID")
    @ManyToOne(optional = false)
    private Management mgtId;
    @JoinColumn(name = "USR_TOAPROVE_ID", referencedColumnName = "USR_ID")
    @ManyToOne(optional = false)
    private User usrToaproveId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MGTA_VERSION")
    private Long mgtaVersion;
    
    public Managementaprobation() {
    }

    public Managementaprobation(Long mgtaId) {
        this.mgtaId = mgtaId;
    }

    public Managementaprobation(Long mgtaId, String mgtaState) {
        this.mgtaId = mgtaId;
        this.mgtaState = mgtaState;
    }
    
    public Managementaprobation(ManagementaprobationDto managementaprobation) {
        this.mgtaId = managementaprobation.getMgtaId();
        this.mgtaState = managementaprobation.getMgtaState();
        this.mgtaComment = managementaprobation.getMgtaComment();
        this.mgtaVersion = managementaprobation.getMgtaVersion();
    }
    
    public void update(ManagementaprobationDto managementaprobation) {
        this.mgtaState = managementaprobation.getMgtaState();
        this.mgtaComment = managementaprobation.getMgtaComment();
        this.mgtaVersion = managementaprobation.getMgtaVersion();
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

    public Management getMgtId() {
        return mgtId;
    }

    public void setMgtId(Management mgtId) {
        this.mgtId = mgtId;
    }

    public Long getMgtaVersion() {
        return mgtaVersion;
    }

    public void setMgtaVersion(Long mgtaVersion) {
        this.mgtaVersion = mgtaVersion;
    }
    
    public User getUsrToaproveId() {
        return usrToaproveId;
    }

    public void setUsrToaproveId(User usrToaproveId) {
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
        if (!(object instanceof Managementaprobation)) {
            return false;
        }
        Managementaprobation other = (Managementaprobation) object;
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
