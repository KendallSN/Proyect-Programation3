package cr.ac.una.wssigeceuna.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "COM_TRACING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tracing.findAll", query = "SELECT t FROM Tracing t"),
    @NamedQuery(name = "Tracing.findByTcgId", query = "SELECT t FROM Tracing t WHERE t.tcgId = :tcgId"),
    @NamedQuery(name = "Tracing.findByTcgCreationdate", query = "SELECT t FROM Tracing t WHERE t.tcgCreationdate = :tcgCreationdate"),
    @NamedQuery(name = "Tracing.findByTcgType", query = "SELECT t FROM Tracing t WHERE t.tcgType = :tcgType"),
    @NamedQuery(name = "Tracing.findByTcgSolutiontype", query = "SELECT t FROM Tracing t WHERE t.tcgSolutiontype = :tcgSolutiontype"),
    @NamedQuery(name = "Tracing.findByTcgSolutiondetail", query = "SELECT t FROM Tracing t WHERE t.tcgSolutiondetail = :tcgSolutiondetail")})
public class Tracing implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "TCG_ID_GENERATOR", sequenceName = "TCG_ID_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TCG_ID_GENERATOR")
    @Basic(optional = false)
    @NotNull
    @Column(name = "TCG_ID")
    private Long tcgId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TCG_CREATIONDATE")
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime tcgCreationdate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TCG_TYPE")
    private String tcgType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TCG_SOLUTIONTYPE")
    private String tcgSolutiontype;
    @Size(max = 200)
    @Column(name = "TCG_SOLUTIONDETAIL")
    private String tcgSolutiondetail;
    @JoinTable(name = "COM_FILETRACING", joinColumns = {
        @JoinColumn(name = "TCG_ID", referencedColumnName = "TCG_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "FLE_ID", referencedColumnName = "FLE_ID")})
    @ManyToMany
    private Collection<File> fileCollection;
    @JoinColumn(name = "MGT_ID", referencedColumnName = "MGT_ID")
    @ManyToOne(optional = false)
    private Management mgtId;
    @JoinColumn(name = "USR_ID", referencedColumnName = "USR_ID")
    @ManyToOne
    private User usrId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TCG_VERSION")
    private Long tcgVersion;
    
    public Tracing() {
    }

    public Tracing(Long tcgId) {
        this.tcgId = tcgId;
    }

    public Tracing(Long tcgId, LocalDateTime tcgCreationdate, String tcgType, String tcgSolutiontype) {
        this.tcgId = tcgId;
        this.tcgCreationdate = tcgCreationdate;
        this.tcgType = tcgType;
        this.tcgSolutiontype = tcgSolutiontype;
    }
    
    public Tracing(TracingDto tracing) {
        this.tcgId = tracing.getTcgId();
        this.tcgCreationdate = tracing.getTcgCreationdate();
        this.tcgType = tracing.getTcgType();
        this.tcgSolutiontype = tracing.getTcgSolutiontype();
        this.tcgSolutiondetail = tracing.getTcgSolutiondetail();
        this.fileCollection = new ArrayList<>();
        for(FileDto file : tracing.getFileCollection()){
            this.fileCollection.add(new File(file));
        }
        this.tcgVersion = tracing.getTcgVersion();
    }
    
    public void update(TracingDto tracing) {
        this.tcgId = tracing.getTcgId();
        this.tcgCreationdate = tracing.getTcgCreationdate();
        this.tcgType = tracing.getTcgType();
        this.tcgSolutiontype = tracing.getTcgSolutiontype();
        this.tcgSolutiondetail = tracing.getTcgSolutiondetail();
        this.fileCollection = new ArrayList<>();
        for(FileDto file : tracing.getFileCollection()){
            this.fileCollection.add(new File(file));
        }
        this.tcgVersion = tracing.getTcgVersion();
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
        return tcgSolutiondetail;
    }

    public void setTcgSolutiondetail(String tcgSolutiondetail) {
        this.tcgSolutiondetail = tcgSolutiondetail;
    }

    public Long getTcgVersion() {
        return tcgVersion;
    }

    public void setTcgVersion(Long tcgVersion) {
        this.tcgVersion = tcgVersion;
    }

    @XmlTransient
    public Collection<File> getFileCollection() {
        return fileCollection;
    }

    public void setFileCollection(Collection<File> fileCollection) {
        this.fileCollection = fileCollection;
    }

    public Management getMgtId() {
        return mgtId;
    }

    public void setMgtId(Management mgtId) {
        this.mgtId = mgtId;
    }

    public User getUsrId() {
        return usrId;
    }

    public void setUsrId(User usrId) {
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
        if (!(object instanceof Tracing)) {
            return false;
        }
        Tracing other = (Tracing) object;
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
