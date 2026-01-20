package Modelos;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "tfgs")
@NamedQueries({
    @NamedQuery(name = "Tfg.findAll", query = "SELECT t FROM Tfg t ORDER BY t.codTFG"),
    @NamedQuery(name = "Tfg.findByCodTFG", query = "SELECT t FROM Tfg t WHERE t.codTFG = :codTFG"),
    @NamedQuery(name = "Tfg.findByTitulo", query = "SELECT t FROM Tfg t WHERE LOWER(t.titulo) LIKE LOWER(:titulo)"),
    @NamedQuery(name = "Tfg.findByTutor", query = "SELECT t FROM Tfg t WHERE t.tutor.codProfesor = :codProfesor"),
    @NamedQuery(name = "Tfg.findByTribunal", query = "SELECT t FROM Tfg t WHERE t.tribunal.codTribunal = :codTribunal"),
    @NamedQuery(name = "Tfg.findEspeciales", query = "SELECT t FROM Tfg t WHERE t.especial = true")})
public class Tfg implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "codTFG", length = 10)
    private String codTFG;

    @ManyToOne
    @JoinColumn(name = "codProfesor")
    private Profesor tutor;

    @Column(name = "titulo", length = 150)
    private String titulo;

    @Column(name = "profesor2", length = 150)
    private String profesor2;

    @ManyToOne
    @JoinColumn(name = "codTribunal")
    private Tribunal tribunal;

    @Column(name = "especial")
    private boolean especial;
    
    @Column(name = "oral")
    private boolean oral;

    // Getters y setters

    public String getCodTFG() {
        return codTFG;
    }

    public void setCodTFG(String codTFG) {
        this.codTFG = codTFG;
    }

    public Profesor getTutor() {
        return tutor;
    }

    public void setTutor(Profesor tutor) {
        this.tutor = tutor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getProfesor2() {
        return profesor2;
    }

    public void setProfesor2(String profesor2) {
        this.profesor2 = profesor2;
    }

    public Tribunal getTribunal() {
        return tribunal;
    }

    public void setTribunal(Tribunal tribunal) {
        this.tribunal = tribunal;
    }

    public boolean getEspecial() {
        return especial;
    }

    public void setEspecial(boolean especial) {
        this.especial = especial;
    }

    public boolean isOral() {
        return oral;
    }

    public void setOral(boolean oral) {
        this.oral = oral;
    }

    
    
    @Override
    public String toString() {
        return codTFG + " | " + tutor + ", titulo=" + titulo;
    }
    
    
    
}