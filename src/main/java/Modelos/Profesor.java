package Modelos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "profesores")
@NamedQueries({
    @NamedQuery(name = "Profesor.findAll", query = "SELECT p FROM Profesor p ORDER BY p.departamento"),
    @NamedQuery(name = "Profesor.findByCodProfesor", query = "SELECT p FROM Profesor p WHERE p.codProfesor = :codProfesor"),
    @NamedQuery(name = "Profesor.findByNombre", query = "SELECT p FROM Profesor p WHERE LOWER(p.nombre) LIKE LOWER(:nombre)"),
    @NamedQuery(name = "Profesor.findByDepartamento", query = "SELECT p FROM Profesor p WHERE p.departamento = :departamento"),
    @NamedQuery(name = "Profesor.findByDisponibilidad", query = "SELECT p FROM Profesor p WHERE p.disponibilidad = :disponibilidad")})
public class Profesor implements Serializable {

    @Id
    @Column(name = "codProfesor")
    private String codProfesor;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "departamento", nullable = false)
    private String departamento;

    @Column(name = "disponibilidad", nullable = false)
    private String disponibilidad;

    @OneToMany(mappedBy = "profesor") // <- asegúrate de que en Usuario uses "profesor"
    private List<Usuario> usuarios = new ArrayList<>();

    @OneToMany(mappedBy = "tutor") // En Tfg.java: private Profesor tutor;
    private List<Tfg> tfgs = new ArrayList<>();

    @OneToMany(mappedBy = "profesor") // En ProfesorTribunal.java
    private List<ProfesorTribunal> tribunales = new ArrayList<>();

    public Profesor() {
    }

    public Profesor(String codProfesor) {
        this.codProfesor = codProfesor;
    }

    public Profesor(String codProfesor, String nombre, String departamento) {
        this.codProfesor = codProfesor;
        this.nombre = nombre;
        this.departamento = departamento;
    }

    public String getCodProfesor() {
        return codProfesor;
    }

    public void setCodProfesor(String codProfesor) {
        this.codProfesor = codProfesor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public List<ProfesorTribunal> getTribunales() {
        return tribunales;
    }

    public void setTribunales(List<ProfesorTribunal> tribunales) {
        this.tribunales = tribunales;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codProfesor != null ? codProfesor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profesor)) {
            return false;
        }
        Profesor other = (Profesor) object;
        if ((this.codProfesor == null && other.codProfesor != null) || (this.codProfesor != null && !this.codProfesor.equals(other.codProfesor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre + " | " + codProfesor;
    }

    public Collection<Usuario> getUsuarioCollection() {
        return usuarios;
    }

}
