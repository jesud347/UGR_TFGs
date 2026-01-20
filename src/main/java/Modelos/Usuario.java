package Modelos;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "usuarios")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByUsuario", query = "SELECT u FROM Usuario u WHERE u.usuario = :usuario"),
    @NamedQuery(name = "Usuario.findByPasswd", query = "SELECT u FROM Usuario u WHERE u.passwd = :passwd"),
    @NamedQuery(name = "Usuario.findBycodProfesor", query = "SELECT u FROM Usuario u WHERE u.profesor.codProfesor = :codProfesor"),
    @NamedQuery(name = "Usuario.findByPermisos", query = "SELECT u FROM Usuario u WHERE u.permisos = :permisos")
})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;

    @Column(name = "passwd")
    private String passwd;

    @Column(name = "permisos")
    private boolean permisos;

    // Relación uno a uno con Profesor
    @OneToOne
    @JoinColumn(name = "codProfesor", referencedColumnName = "codProfesor")  // Asegúrate de que "codProfesor" sea la clave primaria en "Profesor"
    private Profesor profesor;

    public Usuario() {
    }

    public Usuario(String usuario, String passwd, boolean permisos, Profesor codProfesor) {
        this.usuario = usuario;
        this.passwd = passwd;
        this.permisos = permisos;
        this.profesor = codProfesor;
    }

    // Getters y setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public boolean getPermisos() {
        return permisos;
    }

    public void setPermisos(boolean permisos) {
        this.permisos = permisos;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor codProfesor) {
        this.profesor = codProfesor;
    }

    // Métodos toString, equals, y hashCode
    @Override
    public String toString() {
        return profesor.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Usuario usuario1 = (Usuario) obj;
        return usuario != null && usuario.equals(usuario1.usuario);
    }

    @Override
    public int hashCode() {
        return usuario != null ? usuario.hashCode() : 0;
    }
    
    
}
