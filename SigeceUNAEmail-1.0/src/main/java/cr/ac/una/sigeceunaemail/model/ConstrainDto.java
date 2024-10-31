
package cr.ac.una.sigeceunaemail.model;

import java.io.Serializable;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;


public class ConstrainDto implements Serializable {
    private Long cnstId;
    public SimpleStringProperty cnstSymbol;
    public SimpleStringProperty cnstResult;
    private Long cnstVersion;
    private Long varId;

    public ConstrainDto() {
        this.cnstResult=new SimpleStringProperty("");
        this.cnstSymbol=new SimpleStringProperty("");
        this.cnstVersion=Long.valueOf("1");
    }   

    public Long getCnstId() {
        return cnstId;
    }

    public void setCnstId(Long cnstId) {
        this.cnstId = cnstId;
    }

    public String getCnstSymbol() {
        return cnstSymbol.get();
    }

    public void setCnstSymbol(String cnstSymbol) {
        this.cnstSymbol.set(cnstSymbol);
    }

    public String getCnstResult() {
        return cnstResult.get();
    }

    public void setCnstResult(String cnstResult) {
        this.cnstResult.set(cnstResult);
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
