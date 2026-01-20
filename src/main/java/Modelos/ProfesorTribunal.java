package Modelos;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "profesores_tribunales")
public class ProfesorTribunal implements Serializable {

    @EmbeddedId
    private ProfesorTribunalId id;

    @ManyToOne
    @MapsId("codProfesor")
    @JoinColumn(name = "codProfesor")
    private Profesor profesor;

    @ManyToOne
    @MapsId("codTribunal")
    @JoinColumn(name = "codTribunal")
    private Tribunal tribunal;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", length = 12)
    private RolTribunal rol = RolTribunal.Vocal;

    public ProfesorTribunal() {
    }

    public ProfesorTribunal(Profesor profesor, Tribunal tribunal) {
        this.id = new ProfesorTribunalId(tribunal.getCodTribunal(), profesor.getCodProfesor());
        this.profesor = profesor;
        this.tribunal = tribunal;
        this.rol = RolTribunal.Vocal;
    }

    public ProfesorTribunalId getId() {
        return id;
    }

    public void setId(ProfesorTribunalId id) {
        this.id = id;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Tribunal getTribunal() {
        return tribunal;
    }

    public void setTribunal(Tribunal tribunal) {
        this.tribunal = tribunal;
    }

    public RolTribunal getRol() {
        return rol;
    }

    public void setRol(RolTribunal rol) {
        this.rol = rol;
    }
}