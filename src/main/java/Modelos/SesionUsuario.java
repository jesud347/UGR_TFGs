package Modelos;

public class SesionUsuario {

    private static SesionUsuario instancia;
    private Usuario usuario;

    private SesionUsuario() {}

    public static SesionUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SesionUsuario();
        }
        return instancia;
    }

    public void iniciarSesion(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void cerrarSesion() {
        usuario = null;
        instancia = null;
    }
}
