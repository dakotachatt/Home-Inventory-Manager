package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Category;
import models.Item;
import models.User;

/**
 *
 * @author Dakota Chatt
 * @version July 20, 2022
 */
public class ItemDB {
    
    public List<Item> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Item> items = em.createNamedQuery("Items.findAll", Item.class).getResultList();
            return items;
        } finally {
            em.close();
        }
    }
    
    public List<Item> getAllUserItems(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Item> items = user.getItemList();
            return items;
        } finally {
            em.close();
        }
    }
    
    public Item getItem(int itemId) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Item item = em.find(Item.class, itemId);
            return item;
        } finally {
            em.close();
        }
    }
    
    public void addItem(Item item) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            Category category = item.getCategory();
            category.getItemList().add(item);
            User user = item.getOwner();
            user.getItemList().add(item);
            
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
    
    public void deleteItem(Item item) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            Category category = item.getCategory();
            category.getItemList().remove(item);
            User user = item.getOwner();
            user.getItemList().remove(item);
            
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
    
    public void updateItem(Item item, Category prevCategory) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            //If category changed, will remove item from old category, and add to new category
            Category category = item.getCategory();
            if(item.getCategory() != prevCategory) {
                prevCategory.getItemList().remove(item);
                category.getItemList().add(item);
            }
            
            trans.begin();
            em.merge(item);
            
            if(!prevCategory.equals(item.getCategory())) {
                em.merge(prevCategory);
                em.merge(category);   
            }
            
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
        } finally {
            em.close();
        } 
    }
}
