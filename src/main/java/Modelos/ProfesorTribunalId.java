package Modelos;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@Embeddable
public class ProfesorTribunalId implements Serializable{
    
    @Column(name = "codTribunal")
    private String codTribunal;
    
    @Column(name = "codProfesor")
    private String codProfesor;

    public ProfesorTribunalId() {
    }

    public ProfesorTribunalId(String codTribunal, String codProfesor) {
        this.codTribunal = codTribunal;
        this.codProfesor = codProfesor;
    }

    public String getCodTribunal() {
        return codTribunal;
    }

    public void setCodTribunal(String codTribunal) {
        this.codTribunal = codTribunal;
    }

    public String getCodProfesor() {
        return codProfesor;
    }

    public void setCodProfesor(String codProfesor) {
        this.codProfesor = codProfesor;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.codTribunal);
        hash = 61 * hash + Objects.hashCode(this.codProfesor);
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
        final ProfesorTribunalId other = (ProfesorTribunalId) obj;
        if (!Objects.equals(this.codTribunal, other.codTribunal)) {
            return false;
        }
        return Objects.equals(this.codProfesor, other.codProfesor);
    }
    
    
    
}
