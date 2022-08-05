/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Role;
import models.User;
import services.RoleService;

/**
 *
 * @author Dakota
 */
public class UserDB {
    
    public List<User> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<User> users = em.createNamedQuery("User.findAll", User.class).getResultList();
            return users;
        } finally {
            em.close();
        }
    }
    
    public User getUser(String email) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            User user = em.find(User.class, email);
            return user;
        } finally {
            em.close();
        }
    }
    
    public void addUser(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            Role role = user.getRole();
            role.getUserList().add(user);
            
            trans.begin();
            em.persist(user);
            em.merge(role);
            trans.commit();
        } catch(Exception e) {
            trans.rollback();
            System.out.println("Error adding user"); 
        } finally {
            em.close();           
        }
    }
    
    public void deleteUser(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            Role role = user.getRole();
            role.getUserList().remove(user);
            
            trans.begin();
            em.merge(role);
            em.remove(em.merge(user)); // Due to CascadeType.All relationship with Items, all items belonging to user will be deleted also
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            System.out.println("Error removing user");
        } finally {
            em.close();
        }
    }
    
    public void updateUser(User user, Role prevRole) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            //If role changed, ensures user is removed from old role userList, and added to new role userList
            Role role = user.getRole();
            if(!prevRole.equals(user.getRole())) {
                prevRole.getUserList().remove(user);
                role.getUserList().add(user);
            }
            
            trans.begin();
            em.merge(user);
            em.merge(prevRole);
            em.merge(role);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            System.out.println("Error updating user");            
        } finally {
            em.close();
        }
    }
}
