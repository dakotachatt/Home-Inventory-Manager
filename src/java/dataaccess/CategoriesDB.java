package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import models.Category;

/**
 *
 * @author Dakota Chatt
 * @version July 20, 2022
 */
public class CategoriesDB {
    
    public List<Category> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Category> categories = em.createNamedQuery("Categories.findAll", Category.class).getResultList();
            return categories;
        } finally {
            em.close();
        }
    }
    
    public Category getCategory(int categoryId) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Category category = em.find(Category.class, categoryId);
            return category;
        } finally {
            em.close();
        }
    }
}
