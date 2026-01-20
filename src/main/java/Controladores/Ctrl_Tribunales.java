package Controladores;

import Modelos.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.*;

public class Ctrl_Tribunales {

    private EntityManagerFactory emf;
    private EntityManager em;
    private List<String> cursos;

    private static final Map<String, String> SIGLAS_DEPARTAMENTOS = new HashMap<>();

    static {
        SIGLAS_DEPARTAMENTOS.put("Psicobiología", "PB");
        SIGLAS_DEPARTAMENTOS.put("Psicología Experimental", "EX");
        SIGLAS_DEPARTAMENTOS.put("Psicología Evolutiva y de la Educación", "EV");
        SIGLAS_DEPARTAMENTOS.put("Metodología de las Ciencias del Comportamiento", "ME");
        SIGLAS_DEPARTAMENTOS.put("Personalidad, Evaluación y Tratamiento Psicológico", "PE");
        SIGLAS_DEPARTAMENTOS.put("Psicología Social", "SO");
    }

    public Ctrl_Tribunales(EntityManagerFactory emf) {
        this.emf = emf;
    }

    //GENERAR TRIBUNALES SIN RESTRICCION DE FORMA ALEATORIA
    public int generarTribunalesSinRestriccion(String curso, String disponibilidad) {
        em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Paso 1: Obtener todos los profesores
            List<Profesor> profesores = em.createNamedQuery("Profesor.findByDisponibilidad", Profesor.class)
                    .setParameter("disponibilidad", disponibilidad)
                    .getResultList();
            // Paso 2: Barajar la lista
            Collections.shuffle(profesores);

            int totalTribunales = 0;
            int contadorTribunales = 1;

            // Paso 3: Crear tribunales de 3 en 3
            for (int i = 0; i + 2 < profesores.size(); i += 3) {
                // Crear nuevo tribunal
                Tribunal tribunal = new Tribunal();
                String codTribunal = "T" + String.format("%03d", contadorTribunales);
                tribunal.setCodTribunal(codTribunal + "-" + curso);
                tribunal.setCurso(curso);
                if (disponibilidad.equalsIgnoreCase("Ordinaria")) {
                    tribunal.setExtraordinaria(false);
                } else {
                    tribunal.setExtraordinaria(true); // o como lo quieras manejar
                }

                em.persist(tribunal);

                // Asociar profesores con rol
                ProfesorTribunal pt1 = new ProfesorTribunal();
                pt1.setId(new ProfesorTribunalId(codTribunal, profesores.get(i).getCodProfesor()));
                pt1.setTribunal(tribunal);
                pt1.setProfesor(profesores.get(i));
                pt1.setRol(RolTribunal.Vocal);

                ProfesorTribunal pt2 = new ProfesorTribunal();
                pt2.setId(new ProfesorTribunalId(codTribunal, profesores.get(i + 1).getCodProfesor()));
                pt2.setTribunal(tribunal);
                pt2.setProfesor(profesores.get(i + 1));
                pt2.setRol(RolTribunal.Vocal);

                ProfesorTribunal pt3 = new ProfesorTribunal();
                pt3.setId(new ProfesorTribunalId(codTribunal, profesores.get(i + 2).getCodProfesor()));
                pt3.setTribunal(tribunal);
                pt3.setProfesor(profesores.get(i + 2));
                pt3.setRol(RolTribunal.Vocal);

                // Persistir las relaciones
                em.persist(pt1);
                em.persist(pt2);
                em.persist(pt3);

                totalTribunales++;
                contadorTribunales++;
            }

            tx.commit();
            return totalTribunales;

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    //MARCAR TRIBUNAL ORDINARIA
    public void marcarOrdinaria(Tribunal t) {
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            t = em.find(Tribunal.class, t.getCodTribunal());
            if (t != null) {
                t.setExtraordinaria(false);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    //MARCAR TRIBUNAL EXTRAORDINARIA
    public void marcarExtraordinaria(Tribunal t) {
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            t = em.find(Tribunal.class, t.getCodTribunal());
            if (t != null) {
                t.setExtraordinaria(true);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    //SACAR TRIBUNALES DE UN CURSO
    public List<Object[]> listarTribunalesPorCursoConEstado(String curso) {
        List<Object[]> resultados = new ArrayList<>();
        em = emf.createEntityManager();

        if (em != null) {
            try {
                Query query = em.createNativeQuery("""
                    SELECT t.curso, t.codTribunal, t.extraordinaria,
                        CASE 
                            WHEN t.codTribunal LIKE 'SUPL%' THEN 'SUPLENTES'
                            WHEN COUNT(pt.codProfesor) = 3 THEN 'Completo'
                            ELSE 'Incompleto'
                        END AS estado
                    FROM tribunales t
                    LEFT JOIN profesores_tribunales pt ON t.codTribunal = pt.codTribunal
                    WHERE t.curso = ?
                    GROUP BY t.codTribunal, t.curso
                """);
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

    //SACAR PROFESORES AL CLICKAR UN TRIBUNAL
    public List<Object[]> listarProfesoresDeTribunal(String codTribunal) {
        List<Object[]> resultados = new ArrayList<>();
        em = emf.createEntityManager();

        if (em != null) {
            try {
                Query query = em.createNativeQuery("""
                SELECT p.codProfesor, p.nombre, p.departamento, pt.rol
                FROM profesores p
                INNER JOIN profesores_tribunales pt ON p.codProfesor = pt.codProfesor
                WHERE pt.codTribunal = ?
            """);

                query.setParameter(1, codTribunal);
                resultados = query.getResultList();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                em.close();
            }
        }

        return resultados;
    }

    //AÑADIR DOCENTE A TRIBUNAL MANUALMENTE
    public int addProfesorTribunal(String codTribunal, String codProfesor) {
        int resultado = 0;
        em = emf.createEntityManager();

        try {
            // Iniciar transacción
            em.getTransaction().begin();

            // Buscar el Tribunal y el Profesor
            Tribunal tribunal = em.find(Tribunal.class, codTribunal);
            Profesor profesor = em.find(Profesor.class, codProfesor);

            // Verificar si se encontraron los registros
            if (tribunal != null && profesor != null) {
                // Crear el nuevo ProfesorTribunal
                ProfesorTribunalId id = new ProfesorTribunalId();
                id.setCodProfesor(codProfesor);
                id.setCodTribunal(codTribunal);

                ProfesorTribunal profesorTribunal = new ProfesorTribunal();
                profesorTribunal.setId(id);
                profesorTribunal.setProfesor(profesor);
                profesorTribunal.setTribunal(tribunal);

                // Persistir el nuevo registro en la tabla intermedia
                em.persist(profesorTribunal);

                // Confirmar transacción
                em.getTransaction().commit();
                resultado = 1;  // Operación exitosa
            } else {
                resultado = -1; // Error si no se encuentran el Profesor o Tribunal
            }
        } catch (Exception e) {
            // Si ocurre algún error, hacer rollback
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            resultado = 0; // Error en la operación
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return resultado;
    }

    //ELIMINAR UN PROFESOR DE UN TRIBUNAL
    public int eliminarProfesorDeTribunal(String codTribunal, String codProfesor) {
        int resultado = 0;
        em = emf.createEntityManager();

        if (em != null) {
            try {
                em.getTransaction().begin();

                // Ejecutamos la eliminación de la relación
                Query query = em.createNativeQuery("""
                DELETE FROM profesores_tribunales 
                WHERE codTribunal = ? AND codProfesor = ?
            """);
                query.setParameter(1, codTribunal);
                query.setParameter(2, codProfesor);

                int filasAfectadas = query.executeUpdate();

                if (filasAfectadas > 0) {
                    em.getTransaction().commit();
                    resultado = 1; // Eliminado con éxito
                } else {
                    em.getTransaction().rollback();
                    resultado = -2; // No se encontró la relación
                }

            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                e.printStackTrace();
                resultado = -1; // Error general
            } finally {
                em.close();
            }
        }

        return resultado;
    }

    //SACAR CURSOS
    public List<String> sacarCursos() {
        em = emf.createEntityManager();
        cursos = em.createQuery("SELECT DISTINCT t.curso FROM Tribunal t").getResultList();
        return cursos;
    }

    //SACAR NUMERO DE TRIBUNALES DE UN CURSO
    public long sacarNumeroTribunales(String curso) {
        em = emf.createEntityManager();
        long num = (long) em.createQuery("SELECT COUNT(t) FROM Tribunal t WHERE t.curso = :curso")
                .setParameter("curso", curso)
                .getSingleResult();
        return num;
    }

    //GENERAR TRIBUNALES PARA UN CURSO
    public void generarTribunalesParaCurso(String curso, String disponibilidad) throws Exception {
        em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            List<Profesor> profesores = obtenerTodosLosProfesores(disponibilidad);
            Map<String, List<Profesor>> profesoresPorDepartamento = agruparPorDepartamento(profesores);
            Set<String> profesoresAsignados = new HashSet<>();

            int numeroTribunal = 1;
            boolean sePuedeGenerar = true;

            while (sePuedeGenerar) {
                Tribunal tribunal = new Tribunal();
                tribunal.setProfesores(new ArrayList<>());

                boolean asignado = asignarProfesoresAlTribunal(tribunal, profesoresPorDepartamento, profesoresAsignados);

                if (!asignado) {
                    sePuedeGenerar = false;
                } else {
                    String siglas = obtenerSiglasMayoritaria(tribunal);
                    String codTribunal = String.format("%s%02d-%s", siglas, numeroTribunal, curso);
                    tribunal.setCodTribunal(codTribunal);
                    tribunal.setCurso(curso);

                    em.persist(tribunal);
                    numeroTribunal++;
                }
            }

            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new Exception("Error al generar tribunales: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    private boolean asignarProfesoresAlTribunal(Tribunal tribunal,
            Map<String, List<Profesor>> profesoresPorDepartamento,
            Set<String> profesoresAsignados) {

        List<String> departamentos = new ArrayList<>(profesoresPorDepartamento.keySet());
        Collections.shuffle(departamentos);

        for (String depto1 : departamentos) {
            List<Profesor> grupo1 = profesoresPorDepartamento.get(depto1).stream()
                    .filter(p -> !profesoresAsignados.contains(p.getCodProfesor()))
                    .collect(Collectors.toList());

            if (grupo1.size() < 2) {
                continue;
            }

            for (String depto2 : departamentos) {
                if (depto2.equals(depto1)) {
                    continue;
                }

                List<Profesor> grupo2 = profesoresPorDepartamento.get(depto2).stream()
                        .filter(p -> !profesoresAsignados.contains(p.getCodProfesor()))
                        .collect(Collectors.toList());

                if (grupo2.size() < 1) {
                    continue;
                }

                Profesor p1 = grupo1.get(0);
                Profesor p2 = grupo1.get(1);
                Profesor p3 = grupo2.get(0);

                asignarProfesorAlTribunal(p1, tribunal);
                asignarProfesorAlTribunal(p2, tribunal);
                asignarProfesorAlTribunal(p3, tribunal);

                profesoresAsignados.add(p1.getCodProfesor());
                profesoresAsignados.add(p2.getCodProfesor());
                profesoresAsignados.add(p3.getCodProfesor());

                profesoresPorDepartamento.get(depto1).remove(p1);
                profesoresPorDepartamento.get(depto1).remove(p2);
                profesoresPorDepartamento.get(depto2).remove(p3);

                return true;
            }
        }

        return false;
    }

    //COGER TODOS LOS PROFESORES
    private List<Profesor> obtenerTodosLosProfesores(String disponibilidad) {
        Query query = em.createNamedQuery("Profesor.findByDisponibilidad");
        query.setParameter("disponibilidad", disponibilidad);
        return query.getResultList();
    }

    //PROFESORES POR DEPARTAMENTO
    private Map<String, List<Profesor>> agruparPorDepartamento(List<Profesor> profesores) {
        Map<String, List<Profesor>> profesoresPorDepartamento = new HashMap<>();
        for (Profesor profesor : profesores) {
            profesoresPorDepartamento
                    .computeIfAbsent(profesor.getDepartamento(), k -> new ArrayList<>())
                    .add(profesor);
        }
        return profesoresPorDepartamento;
    }

    // Obtiene las siglas del departamento mayoritario en el tribunal
    private String obtenerSiglasMayoritaria(Tribunal tribunal) {
        Map<String, Long> cuenta = tribunal.getProfesores().stream()
                .map(ProfesorTribunal::getProfesor)
                .collect(Collectors.groupingBy(Profesor::getDepartamento, Collectors.counting()));

        String mayoritario = cuenta.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();

        return SIGLAS_DEPARTAMENTOS.getOrDefault(mayoritario, "XX");
    }

    //ASIGNAR PROFESOR A UN TRIBUNAL
    private void asignarProfesorAlTribunal(Profesor profesor, Tribunal tribunal) {
        ProfesorTribunal pt = new ProfesorTribunal();
        pt.setProfesor(profesor);
        pt.setTribunal(tribunal);
        em.persist(pt);
        tribunal.getProfesores().add(pt);
    }

    //CREAR TRIBUNAL SUPLENTE CON LOS PROFESORES QUE SE QUEDAN SIN TRIBUNAL
    public void generarTribunalSuplente(String curso, String disponibilidad) {
        // Iniciar el EntityManager
        em = emf.createEntityManager();

        // Abrir transacción
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            // Hacemos la consulta directamente aquí
            List<Profesor> profesoresSobrantes = em.createQuery(
                    "SELECT p FROM Profesor p WHERE p.disponibilidad = :disponibilidad AND p.codProfesor NOT IN ("
                    + "SELECT pt.profesor.codProfesor FROM ProfesorTribunal pt "
                    + "JOIN pt.tribunal t WHERE t.curso = :curso)",
                    Profesor.class
            )
                    .setParameter("curso", curso) // Filtramos por curso
                    .setParameter("disponibilidad", disponibilidad) // Filtramos por disponibilidad
                    .getResultList();

            if (profesoresSobrantes == null || profesoresSobrantes.isEmpty()) {
                System.out.println("No hay profesores sobrantes con disponibilidad " + disponibilidad + " para crear el tribunal suplente.");
                transaction.commit();
                return;
            }

            // Creamos el tribunal suplente
            Tribunal tribunalSuplente = new Tribunal();
            tribunalSuplente.setCodTribunal("SUPL-" + curso + "-" + disponibilidad);
            tribunalSuplente.setCurso(curso);

            // Persistir el tribunal suplente
            em.persist(tribunalSuplente);

            // Crear y asignar profesores al tribunal suplente con rol SUPLENTE
            for (Profesor profesor : profesoresSobrantes) {
                ProfesorTribunal profesorTribunal = new ProfesorTribunal(profesor, tribunalSuplente);
                profesorTribunal.setRol(RolTribunal.Suplente);
                em.persist(profesorTribunal);
            }

            // Confirmar la transacción
            transaction.commit();
            System.out.println("Tribunal suplente generado correctamente con los profesores disponibles en " + disponibilidad + ".");

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    //LISTAR TRIBUNAL AL QUE PERTENECE UN PROFESOR
    public Tribunal tribunalDeUnProfesorEnCurso(String codProfesor, String curso) {
        em = emf.createEntityManager();
        Tribunal tribunal = null; // Declarada correctamente
        try {
            TypedQuery<ProfesorTribunal> query = em.createQuery(
                    "SELECT pt FROM ProfesorTribunal pt WHERE pt.profesor.codProfesor = :cod AND pt.tribunal.curso = :curso",
                    ProfesorTribunal.class
            );
            query.setParameter("cod", codProfesor);
            query.setParameter("curso", curso);

            List<ProfesorTribunal> resultados = query.getResultList();
            if (!resultados.isEmpty()) {
                tribunal = resultados.get(0).getTribunal(); // Solo uno, como indicas
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return tribunal;
    }
}
