
package cr.ac.una.sigeceunacomunicationws.model;

import java.io.Serializable;
import java.util.Objects;


public class ConstrainDto implements Serializable {
    private Long cnstId;
    private String cnstSymbol;
    private String cnstResult;
    private Long cnstVersion;
    private Long varId;
    
    public ConstrainDto(Constrain constrain) {
        this();
        this.cnstId=constrain.getCnstId();
        this.cnstResult=constrain.getCnstResult();
        this.cnstSymbol=constrain.getCnstSymbol();
        this.cnstVersion = constrain.getCnstVersion();
        this.varId=constrain.getVarId().getVarId();
    }

    public ConstrainDto() {
    }

    public ConstrainDto(Long cnstId, String cnstSymbol, String cnstResult, Long cnstVersion) {
        this.cnstId = cnstId;
        this.cnstSymbol = cnstSymbol;
        this.cnstResult = cnstResult;
        this.cnstVersion = cnstVersion;
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

    public Long getVarId() {
        return varId;
    }

    public void setVarId(Long varId) {
        this.varId = varId;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.cnstId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
       if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConstrainDto other = (ConstrainDto) obj;
        return Objects.equals(this.cnstId, other.cnstId);
    }

    @Override
    public String toString() {
        return "ConstrainDto{" + "Id=" + cnstId + "Result="+cnstResult+"Symbol="+cnstSymbol +'}';
    }
}
