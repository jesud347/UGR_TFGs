package Controladores;

import Modelos.Estudiante;
import Modelos.Tfg;
import java.util.List;
import javax.persistence.*;

public class Ctrl_Estudiantes {

    private EntityManagerFactory emf;
    private EntityManager em;
    private Query query;
    private List<Estudiante> lista;
    private Estudiante estudiante;

    public Ctrl_Estudiantes(EntityManagerFactory emf) {
        this.emf = emf;
    }

    //LISTAR ESTUDIANTES DE UN PROFESOR
    public List<Estudiante> obtenerEstudiantesPorTutor(String codProfesor) {
        em = emf.createEntityManager();
        try {
            lista = em.createQuery(
                    "SELECT e FROM Estudiante e WHERE e.tfg.tutor.codProfesor = :codProfesor", Estudiante.class)
                    .setParameter("codProfesor", codProfesor)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return lista;
    }

    //LISTAR ESTUDIANTES DE UN TRIBUNAL
    public List<Estudiante> obtenerEstudiantesPorTribunal(String codTribunal) {
        em = emf.createEntityManager();
        try {
            TypedQuery<Estudiante> query = em.createQuery(
                    "SELECT e FROM Estudiante e WHERE e.tfg.tribunal.codTribunal = :codTribunal",
                    Estudiante.class
            );
            query.setParameter("codTribunal", codTribunal);
            lista = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return lista;
    }

    //LISTAR TODOS LOS ESTUDIANTES
    public List<Estudiante> listarEstudiantes() {
        em = emf.createEntityManager();
        if (em != null) {
            query = em.createNamedQuery("Estudiante.findAll");
            lista = query.getResultList();
        }
        return lista;
    }

    //SACAR 1 ESTUDIANTE POR CODIGO
    public Estudiante listarEstudianteCodigo(int idEstudiante) {
        em = emf.createEntityManager();
        if (em != null) {
            query = em.createNamedQuery("Estudiante.findByCodEstudiante").setParameter("codEstudiante", idEstudiante);
            estudiante = (Estudiante) query.getSingleResult();
        }
        return estudiante;
    }

    // ASIGNAR TFG A ESTUDIANTE
    public int asignarTFG(String codTFG, int idEstudiante) {
        em = emf.createEntityManager();
        int resultado = 0;

        try {
            em.getTransaction().begin();

            // Buscar TFG y Estudiante
            Tfg tfg = em.find(Tfg.class, codTFG);
            estudiante = em.find(Estudiante.class, idEstudiante);

            if (tfg != null && estudiante != null) {
                // Asignar TFG
                estudiante.setTfg(tfg);

                // Guardar cambios
                em.merge(estudiante);
                em.getTransaction().commit();
                resultado = 1;
            } else {
                em.getTransaction().rollback();
            }

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }

        return resultado;
    }

    // ELIMINAR TFG A USUARIO
    public int eliminarTFG(int idEstudiante) {
        em = emf.createEntityManager();
        int resultado = 0;

        if (em != null) {
            try {
                em.getTransaction().begin();

                // Buscar al estudiante
                estudiante = em.find(Estudiante.class, idEstudiante);

                if (estudiante != null) {
                    // Eliminar relaciones
                    estudiante.setTfg(null);

                    // Merge para aplicar los cambios
                    em.merge(estudiante);

                    em.getTransaction().commit();
                    resultado = 1;
                }

            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                e.printStackTrace();
                resultado = 0;
            } finally {
                em.close();
            }
        }

        return resultado;
    }

    //ASIGNAR NOTA TUTOR
    public int asignarNotaTutor(int codEstudiante, double notaFinal) {
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            estudiante = em.find(Estudiante.class, codEstudiante);
            if (estudiante != null) {
                estudiante.setCalificacionTutor(notaFinal);
                em.merge(estudiante);
                em.getTransaction().commit();
                return 1;
            } else {
                em.getTransaction().rollback();
                return 0; // estudiante no encontrado
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return -1; // error
        } finally {
            em.close();
        }
    }

    //ASIGNAR NOTA TRIBUNAL
    public int asignarNotaTribunal(int codEstudiante, double notaFinal) {
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            estudiante = em.find(Estudiante.class, codEstudiante);
            if (estudiante != null) {
                estudiante.setCalificacionTribunal(notaFinal);
                em.merge(estudiante);
                em.getTransaction().commit();
                return 1;
            } else {
                em.getTransaction().rollback();
                return 0;
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

}
