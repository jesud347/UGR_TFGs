package Controladores;

import Modelos.Tfg;
import Modelos.Tribunal;
import java.util.List;
import javax.persistence.*;

public class Ctrl_Tfgs {

    private EntityManagerFactory emf;
    private EntityManager em;
    private List<Tfg> lista;
    private Query query;
    private Tfg tfg;

    public Ctrl_Tfgs(EntityManagerFactory emf) {
        this.emf = emf;
    }

    //LISTAR TODOS LOS TFGS
    public List<Tfg> listarTfgs() {
        em = emf.createEntityManager();
        if (em != null) {
            query = em.createNamedQuery("Tfg.findAll");
            lista = query.getResultList();
        }
        return lista;
    }

    //BUSCAR UN TFG POR CODIGO
    public Tfg buscarTfg(String codTFG) {
        em = emf.createEntityManager();
        if (em != null) {
            query = em.createNamedQuery("Tfg.findByCodTFG").setParameter("codTFG", codTFG);
            tfg = (Tfg) query.getSingleResult();
        }
        return tfg;
    }

    //ASIGNAR TRIBUNAL A UN TFG
    public int asignarTribunalTfg(String codTFG, String codTribunal) {
        em = emf.createEntityManager();
        EntityTransaction tx = null;
        int resultado = 0;

        try {
            tx = em.getTransaction();
            tx.begin();

            tfg = em.find(Tfg.class, codTFG);
            Tribunal tribunal = em.find(Tribunal.class, codTribunal);

            if (tfg != null && tribunal != null) {
                tfg.setTribunal(tribunal);
                em.merge(tfg);
                resultado = 1;
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            resultado = -1;
        } finally {
            em.close();
        }

        return resultado;
    }

    //QUITAR TRIBUNAL A TFG
    public int quitarTribunalTFG(String codTFG) {
        em = emf.createEntityManager();
        int resultado = 0;
        if (em != null) {
            try {
                
                em.getTransaction().begin();
                
                tfg = em.find(Tfg.class, codTFG);
                
                if(tfg != null){
                    tfg.setTribunal(null);
                    em.merge(tfg);
                    
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

}
