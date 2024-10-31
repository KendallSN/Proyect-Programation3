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
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "COM_VARIABLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Variable.findAll", query = "SELECT v FROM Variable v"),
    @NamedQuery(name = "Variable.findByVarId", query = "SELECT v FROM Variable v WHERE v.varId = :varId"),
    @NamedQuery(name = "Variable.findByVarVariable", query = "SELECT v FROM Variable v WHERE v.varVariable = :varVariable"),
    @NamedQuery(name = "Variable.findByVarDefault", query = "SELECT v FROM Variable v WHERE v.varDefault = :varDefault"),
    @NamedQuery(name = "Variable.findByVarType", query = "SELECT v FROM Variable v WHERE v.varType = :varType"),
    @NamedQuery(name = "Variable.findByVarVersion", query = "SELECT v FROM Variable v WHERE v.varVersion = :varVersion")})
public class Variable implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "VAR_ID_GENERATOR", sequenceName = "VAR_ID_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VAR_ID_GENERATOR")
    @Basic(optional = false)
    @NotNull
    @Column(name = "VAR_ID")
    private Long varId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "VAR_VARIABLE")
    private String varVariable;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "VAR_DEFAULT")
    private String varDefault;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "VAR_TYPE")
    private String varType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VAR_VERSION")
    private Long varVersion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "varId")
    private Collection<Constrain> constrainCollection;
    @JoinColumn(name = "NTP_ID", referencedColumnName = "NTP_ID")
    @ManyToOne(optional = false)
    private Notificationprocess ntpId;

    public Variable() {
    }

    public Variable(Long varId) {
        this.varId = varId;
    }

    public Variable(Long varId, String varVariable, String varDefault, String varType, Long varVersion) {
        this.varId = varId;
        this.varVariable = varVariable;
        this.varDefault = varDefault;
        this.varType = varType;
        this.varVersion = varVersion;
    }
    
    public Variable(VariableDto variable) {
        this.varId = variable.getVarId();
        this.varVariable = variable.getVarVariable();
        this.varDefault = variable.getVarDefault();
        this.varType = variable.getVarType();
        this.varVersion = variable.getVarVersion();
        this.constrainCollection = new ArrayList<>();
        for(ConstrainDto constrain : variable.getConstrainCollection()){
            this.constrainCollection.add(new Constrain(constrain));
        }
        this.ntpId = new Notificationprocess(variable.getNtpId());
    }
    
    public Long getVarId() {
        return varId;
    }

    public void setVarId(Long varId) {
        this.varId = varId;
    }

    public String getVarVariable() {
        return varVariable;
    }

    public void setVarVariable(String varVariable) {
        this.varVariable = varVariable;
    }

    public String getVarDefault() {
        return varDefault;
    }

    public void setVarDefault(String varDefault) {
        this.varDefault = varDefault;
    }

    public String getVarType() {
        return varType;
    }

    public void setVarType(String varType) {
        this.varType = varType;
    }

    public Long getVarVersion() {
        return varVersion;
    }

    public void setVarVersion(Long varVersion) {
        this.varVersion = varVersion;
    }

    @XmlTransient
    public Collection<Constrain> getConstrainCollection() {
        return constrainCollection;
    }

    public void setConstrainCollection(Collection<Constrain> constrainCollection) {
        this.constrainCollection = constrainCollection;
    }

    public Notificationprocess getNtpId() {
        return ntpId;
    }

    public void setNtpId(Notificationprocess ntpId) {
        this.ntpId = ntpId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (varId != null ? varId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Variable)) {
            return false;
        }
        Variable other = (Variable) object;
        if ((this.varId == null && other.varId != null) || (this.varId != null && !this.varId.equals(other.varId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.wssigeceuna.model.Variable[ varId=" + varId + " ]";
    }
    
}
