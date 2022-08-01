package dataaccess;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Dakota
 */
public class DBUtil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("HomeInventoryPU");

    public static EntityManagerFactory getEmFactory() {
        return emf;
    }
}
