package cr.ac.una.wssigeceuna.model;

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
@Table(name = "COM_CONSTRAIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Constrain.findAll", query = "SELECT c FROM Constrain c"),
    @NamedQuery(name = "Constrain.findByCnstId", query = "SELECT c FROM Constrain c WHERE c.cnstId = :cnstId"),
    @NamedQuery(name = "Constrain.findByCnstSymbol", query = "SELECT c FROM Constrain c WHERE c.cnstSymbol = :cnstSymbol"),
    @NamedQuery(name = "Constrain.findByCnstResult", query = "SELECT c FROM Constrain c WHERE c.cnstResult = :cnstResult"),
    @NamedQuery(name = "Constrain.findByCnstVersion", query = "SELECT c FROM Constrain c WHERE c.cnstVersion = :cnstVersion")})
public class Constrain implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "CNST_ID_GENERATOR", sequenceName = "CNST_ID_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CNST_ID_GENERATOR")
    @Basic(optional = false)
    @NotNull
    @Column(name = "CNST_ID")
    private Long cnstId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "CNST_SYMBOL")
    private String cnstSymbol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CNST_RESULT")
    private String cnstResult;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CNST_VERSION")
    private Long cnstVersion;
    @JoinColumn(name = "VAR_ID", referencedColumnName = "VAR_ID")
    @ManyToOne(optional = false)
    private Variable varId;

    public Constrain() {
    }

    public Constrain(Long cnstId) {
        this.cnstId = cnstId;
    }

    public Constrain(Long cnstId, String cnstSymbol, String cnstResult, Long cnstVersion) {
        this.cnstId = cnstId;
        this.cnstSymbol = cnstSymbol;
        this.cnstResult = cnstResult;
        this.cnstVersion = cnstVersion;
    }
    
    public Constrain(ConstrainDto constrain) {
        this.cnstId = constrain.getCnstId();
        this.cnstSymbol = constrain.getCnstSymbol();
        this.cnstResult = constrain.getCnstResult();
        this.cnstVersion = constrain.getCnstVersion();
        this.varId = new Variable(constrain.getVarId());
    }

    public Long getCnstId() {
        return cnstId;
    }

    public void setCnstId(Long cnstId) {
        this.cnstId = cnstId;
    }

    public String getCnstSymbol() {
        return cnstSymbol;
    }

    public void setCnstSymbol(String cnstSymbol) {
        this.cnstSymbol = cnstSymbol;
    }

    public String getCnstResult() {
        return cnstResult;
    }

    public void setCnstResult(String cnstResult) {
        this.cnstResult = cnstResult;
    }

    public Long getCnstVersion() {
        return cnstVersion;
    }

    public void setCnstVersion(Long cnstVersion) {
        this.cnstVersion = cnstVersion;
    }

    public Variable getVarId() {
        return varId;
    }

    public void setVarId(Variable varId) {
        this.varId = varId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cnstId != null ? cnstId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Constrain)) {
            return false;
        }
        Constrain other = (Constrain) object;
        if ((this.cnstId == null && other.cnstId != null) || (this.cnstId != null && !this.cnstId.equals(other.cnstId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.wssigeceuna.model.Constrain[ cnstId=" + cnstId + " ]";
    }
    
}
