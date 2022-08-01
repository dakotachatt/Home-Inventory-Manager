package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import models.Categories;

/**
 *
 * @author Dakota Chatt
 * @version July 20, 2022
 */
public class CategoriesDB {
    
    public List<Categories> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Categories> categories = em.createNamedQuery("Categories.findAll", Categories.class).getResultList();
            return categories;
        } finally {
            em.close();
        }
    }
    
    public Categories getCategory(int categoryId) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Categories category = em.find(Categories.class, categoryId);
            return category;
        } finally {
            em.close();
        }
    }
}
