package Modelos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "tribunales")
@NamedQueries({
    @NamedQuery(name = "Tribunal.findAll", query = "SELECT t FROM Tribunal t"),
    @NamedQuery(name = "Tribunal.findByCodTribunal", query = "SELECT t FROM Tribunal t WHERE t.codTribunal = :codTribunal"),
    @NamedQuery(name = "Tribunal.findByCurso", query = "SELECT t FROM Tribunal t WHERE t.curso = :curso")})
public class Tribunal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "codTribunal")
    private String codTribunal;

    @Column(name = "curso", nullable = false)
    private String curso;

    @Column(name = "extraordinaria")
    private boolean extraordinaria;

    @OneToMany(mappedBy = "tribunal") // En ProfesorTribunal.java: private Tribunal tribunal;
    private List<ProfesorTribunal> profesores = new ArrayList<>();

    @OneToMany(mappedBy = "tribunal") // En Tfg.java: private Tribunal tribunal;
    private List<Tfg> tfgs = new ArrayList<>();

    
    public Tribunal() {
    }

    public Tribunal(String codTribunal) {
        this.codTribunal = codTribunal;
    }

    public Tribunal(String codTribunal, String curso) {
        this.codTribunal = codTribunal;
        this.curso = curso;
    }

    public String getCodTribunal() {
        return codTribunal;
    }

    public void setCodTribunal(String codTribunal) {
        this.codTribunal = codTribunal;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public List<ProfesorTribunal> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<ProfesorTribunal> profesores) {
        this.profesores = profesores;
    }

    public boolean isExtraordinaria() {
        return extraordinaria;
    }

    public void setExtraordinaria(boolean extraordinaria) {
        this.extraordinaria = extraordinaria;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codTribunal != null ? codTribunal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tribunal)) {
            return false;
        }
        Tribunal other = (Tribunal) object;
        if ((this.codTribunal == null && other.codTribunal != null) || (this.codTribunal != null && !this.codTribunal.equals(other.codTribunal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return codTribunal;
    }
    
}
