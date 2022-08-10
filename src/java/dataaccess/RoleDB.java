/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import models.Role;

/**
 *
 * @author Dakota
 */
public class RoleDB {
    
    public List<Role> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Role> roles = em.createNamedQuery("Role.findAll", Role.class).getResultList();
            return roles;
        } finally {
            em.close();
        }
    }
    
    //Only gets non system admin roles for the company admin to have access to
    public List<Role> getAllCompany() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Role> roles = new ArrayList<>();
            roles.add(this.getRole(2));
            roles.add(this.getRole(3));
            
            return roles;
        } finally {
            em.close();
        }
    }
    
    public Role getRole(int roleID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Role role = em.find(Role.class, roleID);
            return role;
        } finally {
            em.close();
        }
    }
}
