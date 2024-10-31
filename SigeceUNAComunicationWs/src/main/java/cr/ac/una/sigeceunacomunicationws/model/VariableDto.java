
package cr.ac.una.sigeceunacomunicationws.model;

import java.util.Objects;

public class VariableDto {
    private Long varId; 
    private String varVariable;
    private String varDefault;
    private String varType;
    private Long varVersion;
    private Long ntpId;
    
    public VariableDto(Variable variable){
        this();
        this.varId=variable.getVarId();
        this.varVariable=variable.getVarVariable();
        this.varDefault=variable.getVarDefault();
        this.varType=variable.getVarType();
        this.varVersion=variable.getVarVersion();
        this.ntpId=variable.getNtpId().getNtpId();
    }

    public VariableDto() {
    }

    public VariableDto(Long varId, String varVariable, String varDefault, String varType, Long varVersion) {
        this.varId = varId;
        this.varVariable = varVariable;
        this.varDefault = varDefault;
        this.varType = varType;
        this.varVersion = varVersion;
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
