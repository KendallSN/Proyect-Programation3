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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "COM_SUBACTIVITY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subactivity.findAll", query = "SELECT s FROM Subactivity s"),
    @NamedQuery(name = "Subactivity.findBySactId", query = "SELECT s FROM Subactivity s WHERE s.sactId = :sactId"),
    @NamedQuery(name = "Subactivity.findBySactIndexpertype", query = "SELECT s FROM Subactivity s WHERE s.sactIndexpertype = :sactIndexpertype"),
    @NamedQuery(name = "Subactivity.findBySactName", query = "SELECT s FROM Subactivity s WHERE s.sactName = :sactName")})
public class Subactivity implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "SACT_ID_GENERATOR", sequenceName = "SACT_ID_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SACT_ID_GENERATOR")
    @Basic(optional = false)
    @NotNull
    @Column(name = "SACT_ID")
    private Long sactId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SACT_INDEXPERTYPE")
    private Long sactIndexpertype;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SACT_NAME")
    private String sactName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sactId")
    private Collection<Management> managementCollection;
    @JoinColumn(name = "ACT_ID", referencedColumnName = "ACT_ID")
    @ManyToOne
    private Activity actId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SACT_VERSION")
    private Long sactVersion;

    public Subactivity() {
    }

    public Subactivity(Long sactId) {
        this.sactId = sactId;
    }

    public Subactivity(Long sactId, Long sactIndexpertype, String sactName) {
        this.sactId = sactId;
        this.sactIndexpertype = sactIndexpertype;
        this.sactName = sactName;
    }
    
    public Subactivity(SubactivityDto subactivityDto) {
        this.sactId = subactivityDto.getSactId();
        this.sactIndexpertype = subactivityDto.getSactIndexpertype();
        this.sactName = subactivityDto.getSactName();
        this.actId = new Activity(subactivityDto.getActId());
        this.sactVersion = subactivityDto.getSactVersion();
        this.managementCollection = new ArrayList<>();
    }
    
    public void update(SubactivityDto subactivityDto) {
        this.sactIndexpertype = subactivityDto.getSactIndexpertype();
        this.sactName = subactivityDto.getSactName();
        this.actId = new Activity(subactivityDto.getActId());
        this.sactVersion = subactivityDto.getSactVersion();
        this.managementCollection = new ArrayList<>();
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
    
    public Activity getActId() {
        return actId;
    }

    public void setActId(Activity actId) {
        this.actId = actId;
    }

    public Collection<Management> getManagementCollection() {
        return managementCollection;
    }

    public void setManagementCollection(Collection<Management> managementCollection) {
        this.managementCollection = managementCollection;
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
        if (!(object instanceof Subactivity)) {
            return false;
        }
        Subactivity other = (Subactivity) object;
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
