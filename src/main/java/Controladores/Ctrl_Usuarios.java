package Controladores;

import Modelos.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

public class Ctrl_Usuarios {

    private EntityManagerFactory emf;
    private EntityManager em;
    private Query query;

    public Ctrl_Usuarios(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // BUSCAR USUARIO POR CODIGO DE PROFESOR
    public Usuario buscarUsuario(String codProfesor) {
        em = emf.createEntityManager();
        Usuario u = null;
        if (em != null) {
            try {
                query = em.createNamedQuery("Usuario.findBycodProfesor");
                query.setParameter("codProfesor", codProfesor);
                u = (Usuario) query.getSingleResult();
            } finally {
                em.close();
            }
        }
        return u;
    }

}
