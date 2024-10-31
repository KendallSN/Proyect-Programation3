package cr.ac.una.sigeceunamessaging.model;

import java.io.Serializable;
import java.util.Objects;

public class ChatDto implements Serializable {

    private Long chtId;
    private Long chtVersion;
    private Long usrIdUser2;
    private Long usrIdUser1;

    public Long getChtId() {
        return chtId;
    }

    public void setChtId(Long chtId) {
        this.chtId = chtId;
    }
    
    public ChatDto(Long chtId, Long chtVersion) {
        this.chtId = chtId;        
        this.chtVersion = chtVersion;
    }

    public ChatDto() {
        this.chtVersion=Long.valueOf("1");    
    }

    public Long getChtVersion() {
        return chtVersion;
    }

    public void setChtVersion(Long chtVersion) {
        this.chtVersion = chtVersion;
    }

    public Long getUsrIdUser2() {
        return usrIdUser2;
    }

    public void setUsrIdUser2(Long usrIdUser2) {
        this.usrIdUser2 = usrIdUser2;
    }

    public Long getUsrIdUser1() {
        return usrIdUser1;
    }

    public void setUsrIdUser1(Long usrIdUser1) {
        this.usrIdUser1 = usrIdUser1;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.chtId);
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
        final ChatDto other = (ChatDto) obj;
        return Objects.equals(this.chtId, other.chtId);
    }

    @Override
    public String toString() {
        return "ChatDto{" + "Id=" + chtId + '}';
    }
    
}
