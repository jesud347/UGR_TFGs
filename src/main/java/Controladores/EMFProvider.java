package Controladores;

import javax.persistence.EntityManagerFactory;

public class EMFProvider {
    
    private static EntityManagerFactory emf;

    public static void setEmf(EntityManagerFactory emf) {
        EMFProvider.emf = emf;
    }

    public static EntityManagerFactory getEmf() {
        if (emf == null) {
            throw new IllegalStateException("EntityManagerFactory no ha sido inicializado.");
        }
        try {
            // Probar a crear un EM y hacer un SELECT 1 para verificar conexión
            emf.createEntityManager().createNativeQuery("SELECT 1").getSingleResult();
        } catch (Exception e) {
            throw new IllegalStateException("Conexión con la base de datos no disponible: " + e.getMessage());
        }
        return emf;
    }
}