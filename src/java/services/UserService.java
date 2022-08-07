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
            UserDB userDB = new UserDB();
            User user = userDB.getUser(emailToDelete);
            
        if(!loggedInEmail.equals(emailToDelete)) { // Ensures the admin cannot delete their own account
            userDB.deleteUser(user);
        } else {
            throw new OwnAccountException();
        }  
    }
    
    public void updateUser(String loggedInEmail, String email, String password, String firstName, String lastName, boolean active, int roleID) throws Exception {
        if(password != null && !password.equals("") && firstName != null && !firstName.equals("") && lastName != null && !lastName.equals("")) {
            UserDB userDB = new UserDB();
            RoleDB roleDB = new RoleDB();
            User user = userDB.getUser(email);
            User requestingUser = userDB.getUser(loggedInEmail);
            Role prevRole = user.getRole();
            Role role = roleDB.getRole(roleID);
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setActive(active);
            
            /*If user is an admin and attempting to change their own role, throw exception, else if user is an admin changing another 
               users role then change it*/
            if(loggedInEmail.equals(email) && (requestingUser.getRole().getRoleId() == 1 || requestingUser.getRole().getRoleId() == 3) && !role.equals(prevRole)) {
                throw new OwnAccountException();
            } else if(requestingUser.getRole().getRoleId() == 1 || requestingUser.getRole().getRoleId() == 3) {
                user.setRole(role);
            }
            
            userDB.updateUser(user, prevRole);   
        } else {
            throw new MissingInputsException();
        }
    }
    
    public void deactivateUser(String email) throws Exception {
            UserDB userDB = new UserDB();

            User user = userDB.getUser(email);
            user.setActive(false);

            userDB.deactivateUser(user);
    }
}
