
package cr.ac.una.sigeceunaemail.model;

import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;

public class VariableDto {
    private Long varId; 
    public SimpleStringProperty varVariable;
    public SimpleStringProperty varDefault;
    public SimpleStringProperty varType;
    private Long varVersion;
    private Long ntpId;

    public VariableDto() {
        this.varDefault=new SimpleStringProperty("");
        this.varVariable=new SimpleStringProperty("");
        this.varType=new SimpleStringProperty("S");
        this.varVersion=Long.valueOf("1");
    }

    public Long getVarId() {
        return varId;
    }

    public void setVarId(Long varId) {
        this.varId = varId;
    }

    public String getVarVariable() {
        return varVariable.get();
    }

    public void setVarVariable(String varVariable) {
        this.varVariable.set(varVariable);
    }

    public String getVarDefault() {
        return varDefault.get();
    }

    public void setVarDefault(String varDefault) {
        this.varDefault.set(varDefault);
    }

    public String getVarType() {
        return varType.get();
    }

    public void setVarType(String varType) {
        this.varType.set(varType);
    }

    public Long getVarVersion() {
        return varVersion;
    }

    public void setVarVersion(Long varVersion) {
        this.varVersion = varVersion;
    }

    public Long getNtpId() {
        return ntpId;
    }

    public void setNtpId(Long ntpId) {
        this.ntpId = ntpId;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.varId);
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
        final VariableDto other = (VariableDto) obj;
        return Objects.equals(this.varId, other.varId);
    }

    @Override
    public String toString() {
        return "MessageDto{" + "Id=" + varId + "Variable="+ varVariable + "Default=" + varDefault +
                "Type"+ varType +'}';
    }
}
