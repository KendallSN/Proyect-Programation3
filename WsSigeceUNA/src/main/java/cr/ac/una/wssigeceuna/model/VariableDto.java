package cr.ac.una.wssigeceuna.model;

import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class VariableDto implements Serializable {

    private Long varId;
    private String varVariable;
    private String varDefault;
    private String varType;
    private Long varVersion;
    private Collection<ConstrainDto> constrainCollection;
    private NotificationprocessDto ntpId;

    public VariableDto() {
    }

    public VariableDto(Long varId) {
        this.varId = varId;
    }

    public VariableDto(Long varId, String varVariable, String varDefault, String varType, Long varVersion) {
        this.varId = varId;
        this.varVariable = varVariable;
        this.varDefault = varDefault;
        this.varType = varType;
        this.varVersion = varVersion;
    }

    public VariableDto(Variable variable) {
        this.varId = variable.getVarId();
        this.varVariable = variable.getVarVariable();
        this.varDefault = variable.getVarDefault();
        this.varType = variable.getVarType();
        this.varVersion = variable.getVarVersion();
        this.constrainCollection = new ArrayList<>();
        for(Constrain constrain : variable.getConstrainCollection()){
            this.constrainCollection.add(new ConstrainDto(constrain));
        }
        this.ntpId = new NotificationprocessDto(variable.getNtpId());
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
    public Collection<ConstrainDto> getConstrainCollection() {
        return constrainCollection;
    }

    public void setConstrainCollection(Collection<ConstrainDto> constrainCollection) {
        this.constrainCollection = constrainCollection;
    }

    public NotificationprocessDto getNtpId() {
        return ntpId;
    }

    public void setNtpId(NotificationprocessDto ntpId) {
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
        if (!(object instanceof VariableDto)) {
            return false;
        }
        VariableDto other = (VariableDto) object;
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
