package cr.ac.una.wssigeceuna.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "COM_MANAGEMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Management.findAll", query = "SELECT m FROM Management m"),
    @NamedQuery(name = "Management.findByMgtId", query = "SELECT m FROM Management m WHERE m.mgtId = :mgtId"),
    @NamedQuery(name = "Management.findByMgtSubject", query = "SELECT m FROM Management m WHERE m.mgtSubject = :mgtSubject"),
    @NamedQuery(name = "Management.findByMgtDescription", query = "SELECT m FROM Management m WHERE m.mgtDescription = :mgtDescription"),
    @NamedQuery(name = "Management.findByMgtCreationdate", query = "SELECT m FROM Management m WHERE m.mgtCreationdate = :mgtCreationdate"),
    @NamedQuery(name = "Management.findByMgtMaxdatetosolve", query = "SELECT m FROM Management m WHERE m.mgtMaxdatetosolve = :mgtMaxdatetosolve"),
    @NamedQuery(name = "Management.findByMgtState", query = "SELECT m FROM Management m WHERE m.mgtState = :mgtState"),
    @NamedQuery(name = "Management.findByMgtSolvedate", query = "SELECT m FROM Management m WHERE m.mgtSolvedate = :mgtSolvedate")})
public class Management implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "MGT_ID_GENERATOR", sequenceName = "MGT_ID_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MGT_ID_GENERATOR")
    @Basic(optional = false)
    @NotNull
    @Column(name = "MGT_ID")
    private Long mgtId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "MGT_SUBJECT")
    private String mgtSubject;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "MGT_DESCRIPTION")
    private String mgtDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MGT_CREATIONDATE")
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime mgtCreationdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MGT_MAXDATETOSOLVE")
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime mgtMaxdatetosolve;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "MGT_STATE")
    private String mgtState;
    @Column(name = "MGT_SOLVEDATE")
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime mgtSolvedate;
    @JoinTable(name = "COM_FILEMANAGEMENT", joinColumns = {
        @JoinColumn(name = "MGT_ID", referencedColumnName = "MGT_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "FLE_ID", referencedColumnName = "FLE_ID")})
    @ManyToMany
    private Collection<File> fileCollection;
    @JoinColumn(name = "ACT_ID", referencedColumnName = "ACT_ID")
    @ManyToOne(optional = false)
    private Activity actId;
    @JoinColumn(name = "SACT_ID", referencedColumnName = "SACT_ID")
    @ManyToOne(optional = false)
    private Subactivity sactId;
    @JoinColumn(name = "USR_REQUESTING_ID", referencedColumnName = "USR_ID")
    @ManyToOne(optional = false)
    private User usrRequestingId;
    @JoinColumn(name = "USR_ASSIGNED_ID", referencedColumnName = "USR_ID")
    @ManyToOne(optional = false)
    private User usrAssignedId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mgtId")
    private Collection<Managementaprobation> managementaprobationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mgtId")
    private Collection<Tracing> tracingCollection;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MGT_VERSION")
    private Long mgtVersion;
    
    public Management() {
    }

    public Management(Long mgtId) {
        this.mgtId = mgtId;
    }

    public Management(Long mgtId, String mgtSubject, String mgtDescription, LocalDateTime mgtCreationdate, LocalDateTime mgtMaxdatetosolve, String mgtState) {
        this.mgtId = mgtId;
        this.mgtSubject = mgtSubject;
        this.mgtDescription = mgtDescription;
        this.mgtCreationdate = mgtCreationdate;
        this.mgtMaxdatetosolve = mgtMaxdatetosolve;
        this.mgtState = mgtState;
    }
    
    public Management(ManagementDto managementDto) {
        this.mgtId = managementDto.getMgtId();
        this.mgtSubject = managementDto.getMgtSubject();
        this.mgtDescription = managementDto.getMgtDescription();
        this.mgtCreationdate = managementDto.getMgtCreationdate();
        this.mgtMaxdatetosolve = managementDto.getMgtMaxdatetosolve();
        this.mgtSolvedate = managementDto.getMgtSolvedate();
        this.mgtState = managementDto.getMgtState();
        this.fileCollection = new ArrayList<>();
        for(FileDto fileDto : managementDto.getFileCollection()){
            this.fileCollection.add(new File(fileDto));
        }
        this.usrAssignedId = new User(managementDto.getUsrAssignedId());
        this.usrRequestingId = new User(managementDto.getUsrRequestingId());
        
//        this.actId = new Activity(managementDto.getActId());
//        this.sactId = new Subactivity(managementDto.getSactId());
        this.mgtVersion = managementDto.getMgtVersion();
    }
    
    public void update(ManagementDto managementDto) {
        this.mgtSubject = managementDto.getMgtSubject();
        this.mgtDescription = managementDto.getMgtDescription();
        this.mgtCreationdate = managementDto.getMgtCreationdate();
        this.mgtMaxdatetosolve = managementDto.getMgtMaxdatetosolve();
        this.mgtSolvedate = managementDto.getMgtSolvedate();
        this.mgtState = managementDto.getMgtState();
        this.fileCollection = new ArrayList<>();
        for(FileDto fileDto : managementDto.getFileCollection()){
            this.fileCollection.add(new File(fileDto));
        }
        this.usrAssignedId = new User(managementDto.getUsrAssignedId());
        this.usrRequestingId = new User(managementDto.getUsrRequestingId());

//        this.actId = new Activity(managementDto.getActId());
        this.mgtVersion = managementDto.getMgtVersion();
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

    public Long getMgtVersion() {
        return mgtVersion;
    }

    public void setMgtVersion(Long mgtVersion) {
        this.mgtVersion = mgtVersion;
    }

    @XmlTransient
    public Collection<File> getFileCollection() {
        return fileCollection;
    }

    public void setFileCollection(Collection<File> fileCollection) {
        this.fileCollection = fileCollection;
    }

    public Activity getActId() {
        return actId;
    }

    public void setActId(Activity actId) {
        this.actId = actId;
    }

    public Subactivity getSactId() {
        return sactId;
    }

    public void setSactId(Subactivity sactId) {
        this.sactId = sactId;
    }
    
    public User getUsrRequestingId() {
        return usrRequestingId;
    }

    public void setUsrRequestingId(User usrRequestingId) {
        this.usrRequestingId = usrRequestingId;
    }

    public User getUsrAssignedId() {
        return usrAssignedId;
    }

    public void setUsrAssignedId(User usrAssignedId) {
        this.usrAssignedId = usrAssignedId;
    }

    @XmlTransient
    public Collection<Managementaprobation> getManagementaprobationCollection() {
        return managementaprobationCollection;
    }

    public void setManagementaprobationCollection(Collection<Managementaprobation> managementaprobationCollection) {
        this.managementaprobationCollection = managementaprobationCollection;
    }

    @XmlTransient
    public Collection<Tracing> getTracingCollection() {
        return tracingCollection;
    }

    public void setTracingCollection(Collection<Tracing> tracingCollection) {
        this.tracingCollection = tracingCollection;
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
        if (!(object instanceof Management)) {
            return false;
        }
        Management other = (Management) object;
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
