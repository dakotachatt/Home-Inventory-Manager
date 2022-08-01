package services;

import dataaccess.RoleDB;
import dataaccess.UserDB;
import exceptions.*;
import java.util.List;
import models.Role;
import models.User;

/**
 *
 * @author Dakota
 */
public class UserService {
    
    public List<User> getAll() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        return users;
    }
    
    public User getUser(String email) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.getUser(email);
        return user;
    }
    
    public void addUser(String email, String firstName, String lastName, String password) throws Exception {
        if(password != null && !password.equals("") && email != null && !email.equals("") && firstName != null && !firstName.equals("") && lastName != null && !lastName.equals("")) {
            UserDB userDB = new UserDB();
            RoleDB roleDB = new RoleDB();
            User user = new User(email, true, firstName, lastName, password);
            Role role = roleDB.getRole(2);
            user.setRole(role);
            userDB.addUser(user);            
        } else {
            throw new MissingInputsException();
        }
    }
    
    public void deleteUser(String loggedInEmail, String emailToDelete) throws Exception {
        if(!loggedInEmail.equals(emailToDelete)) { // Ensures the admin cannot delete their own account
            UserDB userDB = new UserDB();
            User user = userDB.getUser(emailToDelete);
            userDB.deleteUser(user);
        } else {
            throw new DeleteOwnAccountException();
        }  
    }
    
    public void updateUser(String email, String password, String firstName, String lastName, boolean active, int roleID) throws Exception {
        if(password != null && !password.equals("") && firstName != null && !firstName.equals("") && lastName != null && !lastName.equals("")) {
            UserDB userDB = new UserDB();
            RoleDB roleDB = new RoleDB();
            User user = userDB.getUser(email);
            Role role = roleDB.getRole(roleID);
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setActive(active);
            user.setRole(role);
            userDB.updateUser(user);   
        } else {
            throw new MissingInputsException();
        }
    }
}
