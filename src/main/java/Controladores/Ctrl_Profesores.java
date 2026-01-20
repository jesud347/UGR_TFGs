package Controladores;

import Modelos.Profesor;
import Modelos.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

public class Ctrl_Profesores {

    private List<Profesor> listadoProfesores;
    private List<String> departamentos;
    private EntityManagerFactory emf;
    private EntityManager em;
    private Query query;
    private int resultado;

    public Ctrl_Profesores(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // BUSCAR DOCENTE
    public Profesor buscarProfesor(String codProfesor) {
        em = emf.createEntityManager();
        Profesor p = null;
        if (em != null) {
            try {
                p = em.find(Profesor.class, codProfesor);
            } finally {
                em.close();
            }
        }
        return p;
    }

    //EDITAR DOCENTE Y USUARIO 
    public int editarDocenteYUsuario(Profesor p, Usuario uNuevo) {
        resultado = 0;
        em = emf.createEntityManager();

        if (em != null) {
            try {
                em.getTransaction().begin();

                Profesor profeOriginal = em.find(Profesor.class, p.getCodProfesor());

                // Buscamos usuario actual del profesor
                Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.profesor.codProfesor = :cod");
                query.setParameter("cod", p.getCodProfesor());
                Usuario usuarioOriginal = (Usuario) query.getSingleResult();

                if (profeOriginal == null || usuarioOriginal == null) {
                    em.getTransaction().rollback();
                    return -2;
                }

                // Actualizar profesor
                profeOriginal.setNombre(p.getNombre());
                profeOriginal.setDepartamento(p.getDepartamento());

                // ¿Ha cambiado el nombre de usuario?
                if (!usuarioOriginal.getUsuario().equals(uNuevo.getUsuario())) {
                    em.remove(usuarioOriginal);
                    em.persist(uNuevo);
                } else {
                    usuarioOriginal.setPasswd(uNuevo.getPasswd());
                    usuarioOriginal.setPermisos(uNuevo.getPermisos());
                }

                em.getTransaction().commit();
                resultado = 1;

            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                e.printStackTrace();
                return -1;
            } finally {
                em.close();
            }
        }
        return resultado;
    }

    // ALTA DOCENTE
    public int altaDocente(Profesor p, String correo) {
        resultado = 0;
        em = emf.createEntityManager();
        if (em != null) {
            try {
                em.getTransaction().begin();

                Profesor profe = em.find(Profesor.class, p.getCodProfesor());
                if (profe == null) {
                    em.persist(p); // Añadimos el profesor

                    // Creamos el usuario asociado
                    Usuario u = new Usuario();
                    u.setUsuario(correo); // Suponiendo que codProfesor es String
                    u.setPasswd("1234");
                    u.setPermisos(false);
                    u.setProfesor(p); // Asociamos el profesor

                    em.persist(u); // Añadimos el usuario

                    em.getTransaction().commit();
                    resultado = 1;
                } else {
                    return -2; // Profesor ya existente
                }
            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                e.printStackTrace();
                return -1; // Error general
            } finally {
                em.close();
            }
        }
        return resultado;
    }

// ELIMINAR DOCENTE
    public int eliminarDocente(Profesor p) {
        resultado = 0;
        em = emf.createEntityManager();
        if (em != null) {
            try {
                em.getTransaction().begin();

                Profesor profe = em.find(Profesor.class, p.getCodProfesor());
                if (profe != null) {

                    // Comprobamos si el profesor pertenece a algún tribunal
                    Query consulta = em.createNativeQuery("SELECT * FROM profesores_tribunales WHERE codProfesor = ?");
                    consulta.setParameter(1, p.getCodProfesor());
                    List<?> asociaciones = consulta.getResultList();

                    if (!asociaciones.isEmpty()) {
                        em.getTransaction().rollback();
                        return -3;
                    }

                    // Buscar usuario asociado por codProfesor (no por nombre de usuario)
                    Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.profesor.codProfesor = :cod");
                    query.setParameter("cod", p.getCodProfesor());
                    List<Usuario> usuarios = query.getResultList();

                    if (!usuarios.isEmpty()) {
                        em.remove(usuarios.get(0));
                    }

                    em.remove(profe);
                    em.getTransaction().commit();
                    resultado = 1;

                } else {
                    return -2;
                }

            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                e.printStackTrace();
                return -1;
            } finally {
                em.close();
            }
        }
        return resultado;
    }

    //LISTAR TODOS LOS PROFESORES
    public List<Profesor> listaProfesores() {
        em = emf.createEntityManager();
        if (em != null) {
            query = em.createNamedQuery("Profesor.findAll");
            listadoProfesores = query.getResultList();
        }
        return listadoProfesores;
    }

    //LISTAR TODOS LOS PROFESORES POR DEPARTAMENTO
    public List<Profesor> listaProfesoresDepartamento(String departamento) {
        em = emf.createEntityManager();
        if (em != null) {
            query = em.createNamedQuery("Profesor.findByDepartamento").setParameter("departamento", departamento);
            listadoProfesores = query.getResultList();
        }
        return listadoProfesores;
    }

    //LISTAR PROFESORES POR CODIGO
    public List<Profesor> listaProfesoresCodigo(String codProfesor) {
        em = emf.createEntityManager();
        if (em != null) {
            query = em.createNamedQuery("Profesor.findByCodProfesor").setParameter("codProfesor", codProfesor);
            listadoProfesores = query.getResultList();
        }
        return listadoProfesores;
    }

    //LISTAR PROFESORES POR NOMBRE
    public List<Profesor> listaProfesoresNombre(String nombre) {
        em = emf.createEntityManager();
        if (em != null) {
            query = em.createNamedQuery("Profesor.findByNombre")
                    .setParameter("nombre", "%" + nombre + "%"); // Añadir comodines
            listadoProfesores = query.getResultList();
        }
        return listadoProfesores;
    }

    //SACAR DEPARTAMENTOS PARA RELLENAR COMBOBOX
    public List<String> sacarDepartamentos() {
        em = emf.createEntityManager();
        departamentos = em.createQuery("SELECT DISTINCT p.departamento FROM Profesor p").getResultList();
        return departamentos;
    }

    //SACAR NOMBRE DE PROFESOR POR CODPROFESOR DE USUARIO
    public String nombreProfesor(String codProfesor) {
        em = emf.createEntityManager();
        try {
            return (String) em.createQuery("SELECT p.nombre FROM Profesor p WHERE p.codProfesor = :codProfesor")
                    .setParameter("codProfesor", codProfesor)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    //CHECKEAR CREDENCIALES
    public Usuario comprobarUsuario(String usuario, String password) {
        em = emf.createEntityManager();

        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :usuario AND u.passwd = :password", Usuario.class
            )
                    .setParameter("usuario", usuario)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    //CAMBIAR CONTRASEÑA CUANDO SEA 1234 (PRIMER LOGIN DEL USUARIO)
    public void changePassword(String usuario, String nuevaContraseña) {
        // Crear un EntityManager para interactuar con la base de datos
        em = emf.createEntityManager();

        // Buscar el usuario con el nombre de usuario
        Usuario user = em.find(Usuario.class,
                usuario);
        if (user != null) {
            // Iniciar una transacción
            em.getTransaction().begin();
            // Cambiar la contraseña
            user.setPasswd(nuevaContraseña);
            // Confirmar los cambios
            em.getTransaction().commit();
        }

    }

    //LISTAR PROFESORES SIN TRIBUNAL DE UN CURSO
    public List<Object[]> listarProfesoresSinTribunalPorCurso(String curso) {
        List<Object[]> resultados = new ArrayList<>();
        em = emf.createEntityManager();

        if (em != null) {
            try {
                query = em.createNativeQuery("SELECT p.* FROM profesores p WHERE p.codProfesor NOT IN (SELECT pt.codProfesor FROM profesores_tribunales pt JOIN tribunales t ON pt.codTribunal = t.codTribunal WHERE t.curso = ?)");
                query.setParameter(1, curso);
                resultados = query.getResultList();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                em.close();
            }
        }

        return resultados;
    }

}
