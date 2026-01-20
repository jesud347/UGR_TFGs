package Controladores;

import Modelos.*;
import java.util.List;
import javax.persistence.*;

public class Ctrl_ProfesorTribunal {

    private List<Object[]> lista;
    private EntityManagerFactory emf;
    private EntityManager em;
    private Query query;

    public Ctrl_ProfesorTribunal(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // SACAR ROL DE UN PROFESOR EN UN TRIBUNAL
    public String sacarRolProfesor(String codTribunal, String codProfesor) {
        em = emf.createEntityManager();
        RolTribunal rol = null;
        try {
            TypedQuery<RolTribunal> query = em.createQuery(
                    "SELECT pt.rol FROM ProfesorTribunal pt WHERE pt.tribunal.codTribunal = :codTribunal AND pt.profesor.codProfesor = :codProfesor",
                    RolTribunal.class
            );
            query.setParameter("codTribunal", codTribunal);
            query.setParameter("codProfesor", codProfesor);

            rol = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No se encontró rol para el profesor en el tribunal.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return rol.name(); // o .toString() si quieres el nombre legible
    }

    //ACTUALIZAR ROL DE UN PROFESOR
    public int actualizarRol(String codTribunal, String codProfesor, String nuevoRol) {
        em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Verificamos si se quiere establecer como Presidente
            if (nuevoRol.equalsIgnoreCase("Presidente")) {
                long presidentes = em.createQuery(
                        "SELECT COUNT(pt) FROM ProfesorTribunal pt WHERE pt.tribunal.codTribunal = :codTribunal AND pt.rol = :rol", Long.class)
                        .setParameter("codTribunal", codTribunal)
                        .setParameter("rol", RolTribunal.Presidente)
                        .getSingleResult();

                if (presidentes >= 1) {
                    tx.rollback();
                    return 2; // Ya hay un presidente
                }
            }

            ProfesorTribunalId id = new ProfesorTribunalId(codTribunal, codProfesor);
            ProfesorTribunal pt = em.find(ProfesorTribunal.class, id);

            if (pt != null) {
                pt.setRol(RolTribunal.valueOf(nuevoRol));
                em.merge(pt);
                tx.commit();
                return 1; // Éxito
            } else {
                tx.rollback();
                return 0; // No encontrado
            }

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return -1; // Error
        } finally {
            em.close();
        }
    }

    //SACAR TRIBUNALES POR CODIGO DE PROFESOR
    public List<Object[]> listarTribunalesPorCodProfesor(String codProfesor) {
        em = emf.createEntityManager();
        if (em != null) {
            query = em.createQuery("SELECT t.codTribunal, t.curso FROM ProfesorTribunal pt JOIN pt.tribunal t WHERE pt.profesor.codProfesor = :codProfesor ORDER BY t.curso").setParameter("codProfesor", codProfesor);
            lista = query.getResultList();
        }
        return lista;
    }

    //LISTAR TRIBUNALES CON SUS PROFESORES
    public List<Object[]> listarProfesoresTribunales() {
        em = emf.createEntityManager();
        if (em != null) {
            query = em.createQuery("SELECT t.codTribunal, t.curso, p.codProfesor, p.nombre, p.departamento FROM ProfesorTribunal pt JOIN pt.tribunal t JOIN pt.profesor p ORDER BY t.curso");
            lista = query.getResultList();
        }
        return lista;
    }

    //LISTAR TRIBUNALES CON SUS PROFESORES DE UN CURSO
    public List<Object[]> listarProfesoresTribunalesCurso(String curso) {
        em = emf.createEntityManager();
        if (em != null) {
            query = em.createQuery("SELECT t.codTribunal, t.curso, p.codProfesor, p.nombre, p.departamento FROM ProfesorTribunal pt JOIN pt.tribunal t JOIN pt.profesor p WHERE t.curso = :curso").setParameter("curso", curso);
            lista = query.getResultList();
        }
        return lista;
    }

    //LISTAR TRIBUNALES CON SUS PROFESORES DE UN CURSO Y DEPARTAMENTO
    public List<Object[]> listarProfesoresTribunalesCursoDepartamento(String curso, String departamento) {
        em = emf.createEntityManager();
        return em.createNativeQuery(
                "SELECT t.codTribunal, t.curso, "
                + " (SELECT CONCAT(p1.nombre, ' (', p1.codProfesor, ')') "
                + "  FROM profesores_tribunales pt1 "
                + "  JOIN profesores p1 ON pt1.codProfesor = p1.codProfesor "
                + "  WHERE pt1.codTribunal = t.codTribunal ORDER BY p1.codProfesor LIMIT 0,1) AS profesor1, "
                + " (SELECT CONCAT(p2.nombre, ' (', p2.codProfesor, ')') "
                + "  FROM profesores_tribunales pt2 "
                + "  JOIN profesores p2 ON pt2.codProfesor = p2.codProfesor "
                + "  WHERE pt2.codTribunal = t.codTribunal ORDER BY p2.codProfesor LIMIT 1,1) AS profesor2, "
                + " (SELECT CONCAT(p3.nombre, ' (', p3.codProfesor, ')') "
                + "  FROM profesores_tribunales pt3 "
                + "  JOIN profesores p3 ON pt3.codProfesor = p3.codProfesor "
                + "  WHERE pt3.codTribunal = t.codTribunal ORDER BY p3.codProfesor LIMIT 2,1) AS profesor3 "
                + "FROM tribunales t "
                + "WHERE t.curso = ?1 "
                + "  AND t.codTribunal NOT LIKE 'SUPL-%' "
                + "  AND t.codTribunal IN ( "
                + "    SELECT pt.codTribunal "
                + "    FROM profesores_tribunales pt "
                + "    JOIN profesores p ON pt.codProfesor = p.codProfesor "
                + "    WHERE p.departamento = ?2 "
                + "    GROUP BY pt.codTribunal "
                + "    HAVING COUNT(*) >= 2)"
        ).setParameter(1, curso)
                .setParameter(2, departamento)
                .getResultList();
    }

    //LISTAR TRIBUNALES CON SUS PROFESORES DE UN CURSO
    public List<Object[]> listarProfesores123TribunalesCurso(String curso) {
        em = emf.createEntityManager();
        return em.createNativeQuery(
                "SELECT t.codTribunal, t.curso, "
                + " (SELECT CONCAT(p1.nombre, ' (', p1.codProfesor, ')') "
                + "  FROM profesores_tribunales pt1 "
                + "  JOIN profesores p1 ON pt1.codProfesor = p1.codProfesor "
                + "  WHERE pt1.codTribunal = t.codTribunal ORDER BY p1.codProfesor LIMIT 0,1) AS profesor1, "
                + " (SELECT CONCAT(p2.nombre, ' (', p2.codProfesor, ')') "
                + "  FROM profesores_tribunales pt2 "
                + "  JOIN profesores p2 ON pt2.codProfesor = p2.codProfesor "
                + "  WHERE pt2.codTribunal = t.codTribunal ORDER BY p2.codProfesor LIMIT 1,1) AS profesor2, "
                + " (SELECT CONCAT(p3.nombre, ' (', p3.codProfesor, ')') "
                + "  FROM profesores_tribunales pt3 "
                + "  JOIN profesores p3 ON pt3.codProfesor = p3.codProfesor "
                + "  WHERE pt3.codTribunal = t.codTribunal ORDER BY p3.codProfesor LIMIT 2,1) AS profesor3 "
                + "FROM tribunales t "
                + "WHERE t.curso = ?1 "
                + "  AND t.codTribunal NOT LIKE 'SUPL-%' "
                + "  AND t.codTribunal IN ( "
                + "    SELECT pt.codTribunal "
                + "    FROM profesores_tribunales pt "
                + "    JOIN profesores p ON pt.codProfesor = p.codProfesor "
                + "    GROUP BY pt.codTribunal "
                + "    HAVING COUNT(*) >= 2)"
        ).setParameter(1, curso)
                .getResultList();
    }

    //LISTAR SUPLENTES DE UN CURSO
    public List<Object[]> listarProfesoresSuplentesCurso(String curso) {
        em = emf.createEntityManager();
        String jpql = "SELECT pt.tribunal.codTribunal, t.curso, p.codProfesor, p.nombre, p.departamento "
                + "FROM ProfesorTribunal pt "
                + "JOIN pt.profesor p "
                + "JOIN pt.tribunal t "
                + "WHERE pt.tribunal.codTribunal LIKE 'SUPL-%' AND t.curso = :curso";

        return em.createQuery(jpql, Object[].class)
                .setParameter("curso", curso)
                .getResultList();
    }

    //ELIMINAR TODOS LOS TRIBUNALES Y SUS PROFESORES
    public void eliminarPT() {
        em = emf.createEntityManager();
        if (em != null) {
            em.getTransaction().begin();

            // 1. Desvincular los tribunales de los TFGs
            em.createQuery("UPDATE Tfg t SET t.tribunal = NULL WHERE t.tribunal IS NOT NULL").executeUpdate();

            // 2. Eliminar las relaciones de profesores con tribunales
            em.createQuery("DELETE FROM ProfesorTribunal").executeUpdate();

            // 3. Eliminar los tribunales
            em.createQuery("DELETE FROM Tribunal").executeUpdate();

            em.getTransaction().commit();
            em.close();
        }
    }

    //ELIMINAR TODOS LOS TRIBUNALES Y SUS PROFESORES DE UN CURSO
    public void eliminarPTCurso(String curso) {
        em = emf.createEntityManager();
        if (em != null) {
            em.getTransaction().begin();

            // 1. Desvincular tribunales de TFGs del curso
            em.createQuery("UPDATE Tfg t SET t.tribunal = NULL WHERE t.tribunal.codTribunal IN "
                    + "(SELECT tr.codTribunal FROM Tribunal tr WHERE tr.curso = :curso)")
                    .setParameter("curso", curso)
                    .executeUpdate();

            // 3. Eliminar asociaciones en ProfesorTribunal
            em.createQuery("DELETE FROM ProfesorTribunal pt WHERE pt.tribunal.codTribunal IN "
                    + "(SELECT t.codTribunal FROM Tribunal t WHERE t.curso = :curso)")
                    .setParameter("curso", curso)
                    .executeUpdate();

            // 4. Eliminar los tribunales del curso
            em.createQuery("DELETE FROM Tribunal t WHERE t.curso = :curso")
                    .setParameter("curso", curso)
                    .executeUpdate();

            em.getTransaction().commit();
            em.close();
        }
    }

    //ELIMINAR TRIBUNAL
    public int eliminarTribunal(String codTribunal) {
        em = emf.createEntityManager();
        int resultado = 0;

        try {
            em.getTransaction().begin();

            // 1. Eliminar asociaciones en la tabla intermedia
            Query eliminarAsociaciones = em.createQuery(
                    "DELETE FROM ProfesorTribunal pt WHERE pt.tribunal.codTribunal = :cod"
            );
            eliminarAsociaciones.setParameter("cod", codTribunal);
            eliminarAsociaciones.executeUpdate();

            // 2. Buscar el tribunal
            Tribunal tribunal = em.find(Tribunal.class, codTribunal);
            if (tribunal != null) {
                em.remove(tribunal); //Y ELIMINARLO
                resultado = 1;
            }

            em.getTransaction().commit();
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

}
