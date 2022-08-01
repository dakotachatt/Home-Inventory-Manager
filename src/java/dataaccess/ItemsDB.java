package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Categories;
import models.Items;
import models.Users;
import services.InventoryService;
import services.UserService;

/**
 *
 * @author Dakota Chatt
 * @version July 20, 2022
 */
public class ItemsDB {
    
    public List<Items> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Items> items = em.createNamedQuery("Items.findAll", Items.class).getResultList();
            return items;
        } finally {
            em.close();
        }
    }
    
    public List<Items> getAllUserItems(Users user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Items> items = user.getItemsList();
            return items;
        } finally {
            em.close();
        }
    }
    
    public Items getItem(int itemId) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Items item = em.find(Items.class, itemId);
            return item;
        } finally {
            em.close();
        }
    }
    
    public void addItem(Items item) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            Categories category = item.getCategory();
            category.getItemsList().add(item);
            Users user = item.getOwner();
            user.getItemsList().add(item);
            
            trans.begin();
            em.persist(item);
            em.merge(category);
            em.merge(user);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            System.out.println("Error adding item"); 
        } finally {
            em.close();
        }
    }
    
    public void deleteItem(Items item) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            Categories category = item.getCategory();
            category.getItemsList().remove(item);
            Users user = item.getOwner();
            user.getItemsList().remove(item);
            
            trans.begin();
            em.remove(em.merge(item));
            em.merge(category);
            em.merge(user);
            trans.commit();
        } catch (Exception e) {
            System.out.println("Rolling back");
            trans.rollback();
        } finally {
            em.close();
        }
    }
}
