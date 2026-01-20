package Modelos;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "estudiantes")
@NamedQueries({
    @NamedQuery(name = "Estudiante.findAll", query = "SELECT e FROM Estudiante e ORDER BY e.codEstudiante"),
    @NamedQuery(name = "Estudiante.findByCodEstudiante", query = "SELECT e FROM Estudiante e WHERE e.codEstudiante = :codEstudiante"),
    @NamedQuery(name = "Estudiante.findByNombre", query = "SELECT e FROM Estudiante e WHERE LOWER(e.nombre) LIKE LOWER(:nombre)"),
    @NamedQuery(name = "Estudiante.findByApellidos", query = "SELECT e FROM Estudiante e WHERE LOWER(e.apellidos) LIKE LOWER(:apellidos)"),
    @NamedQuery(name = "Estudiante.findByEmail", query = "SELECT e FROM Estudiante e WHERE e.email = :email"),
    @NamedQuery(name = "Estudiante.findByTFG", query = "SELECT e FROM Estudiante e WHERE e.tfg.codTFG = :codTFG"),
    @NamedQuery(name = "Estudiante.findSeminario", query = "SELECT e FROM Estudiante e WHERE e.seminario = true"),
    @NamedQuery(name = "Estudiante.findByCalificacionTutorGreaterThan", query = "SELECT e FROM Estudiante e WHERE e.calificacionTutor >= :notaMin"),
    @NamedQuery(name = "Estudiante.findByCalificacionTribunalGreaterThan", query = "SELECT e FROM Estudiante e WHERE e.calificacionTribunal >= :notaMin")})
public class Estudiante implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codEstudiante")
    private int codEstudiante;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "apellidos", length = 100)
    private String apellidos;

    @Column(name = "email", length = 100)
    private String email;

    @ManyToOne
    @JoinColumn(name = "codTFG")
    private Tfg tfg;

    @Column(name = "nuevoTituloTFG")
    private String nuevoTituloTFG;
    
    @Column(name = "seminario")
    private boolean seminario;

    @Column(name = "calificacion_tutor", precision = 4, scale = 2)
    private double calificacionTutor;

    @Column(name = "calificacion_tribunal", precision = 4, scale = 2)
    private double calificacionTribunal;


    // Getters y setters

    public int getCodEstudiante() {
        return codEstudiante;
    }

    public void setCodEstudiante(int codEstudiante) {
        this.codEstudiante = codEstudiante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Tfg getTfg() {
        return tfg;
    }

    public void setTfg(Tfg tfg) {
        this.tfg = tfg;
    }

    public boolean getSeminario() {
        return seminario;
    }

    public void setSeminario(boolean seminario) {
        this.seminario = seminario;
    }

    public double getCalificacionTutor() {
        return calificacionTutor;
    }

    public void setCalificacionTutor(double calificacionTutor) {
        this.calificacionTutor = calificacionTutor;
    }

    public double getCalificacionTribunal() {
        return calificacionTribunal;
    }

    public void setCalificacionTribunal(double calificacionTribunal) {
        this.calificacionTribunal = calificacionTribunal;
    }

    public String getNuevoTituloTFG() {
        return nuevoTituloTFG;
    }

    public void setNuevoTituloTFG(String nuevoTituloTFG) {
        this.nuevoTituloTFG = nuevoTituloTFG;
    }
    
    @Override
    public String toString() {
        return codEstudiante + " | " + nombre + " " +apellidos;
    }
    
    

}