/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Items;
import models.Users;

/**
 *
 * @author Dakota
 */
public class UserDB {
    
    public List<Users> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Users> users = em.createNamedQuery("Users.findAll", Users.class).getResultList();
            return users;
        } finally {
            em.close();
        }
    }
    
    public Users getUser(String username) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Users user = em.find(Users.class, username);
            return user;
        } finally {
            em.close();
        }
    }
    
    public void addUser(Users user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.persist(user);
            trans.commit();
        } catch(Exception e) {
            trans.rollback();
            System.out.println("Error adding user"); 
        } finally {
            em.close();           
        }
    }
    
    public void deleteUser(Users user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try { 
            trans.begin();
            em.remove(em.merge(user)); // Due to CascadeType.All relationship with Items, all items belonging to user will be deleted also
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            System.out.println("Error removing user");
        } finally {
            em.close();
        }
    }
    
    public void updateUser(Users user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            System.out.println("Error updating user");            
        } finally {
            em.close();
        }
    }
}
