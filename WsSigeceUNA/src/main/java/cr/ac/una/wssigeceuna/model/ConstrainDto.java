package cr.ac.una.wssigeceuna.model;

import java.io.Serializable;

public class ConstrainDto implements Serializable {

    private Long cnstId;
    private String cnstSymbol;
    private String cnstResult;
    private Long cnstVersion;
    private VariableDto varId;

    public ConstrainDto() {
    }

    public ConstrainDto(Long cnstId) {
        this.cnstId = cnstId;
    }

    public ConstrainDto(Long cnstId, String cnstSymbol, String cnstResult, Long cnstVersion) {
        this.cnstId = cnstId;
        this.cnstSymbol = cnstSymbol;
        this.cnstResult = cnstResult;
        this.cnstVersion = cnstVersion;
    }
    
    public ConstrainDto(Constrain constrain) {
        this.cnstId = constrain.getCnstId();
        this.cnstSymbol = constrain.getCnstSymbol();
        this.cnstResult = constrain.getCnstResult();
        this.cnstVersion = constrain.getCnstVersion();
        this.varId = new VariableDto(constrain.getVarId());
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

    public VariableDto getVarId() {
        return varId;
    }

    public void setVarId(VariableDto varId) {
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
        if (!(object instanceof ConstrainDto)) {
            return false;
        }
        ConstrainDto other = (ConstrainDto) object;
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
