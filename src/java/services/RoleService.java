package services;

import dataaccess.RoleDB;
import java.util.List;
import models.Role;

/**
 *
 * @author Dakota
 */
public class RoleService {
    
    public List<Role> getAll() {
        RoleDB roleDB = new RoleDB();
        List<Role> roles = roleDB.getAll();
        return roles;
    }
    
    public List<Role> getAllCompany() {
        RoleDB roleDB = new RoleDB();
        List<Role> roles = roleDB.getAllCompany();
        return roles;
    }
    
    public Role getRole(int roleID) {
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.getRole(roleID);
        return role;
    }
}
