package services;

import dataaccess.UserDB;
import exceptions.*;
import java.util.List;
import models.Users;

/**
 *
 * @author Dakota
 */
public class UserService {
    
    public List<Users> getAll() throws Exception {
        UserDB userDB = new UserDB();
        List<Users> users = userDB.getAll();
        return users;
    }
    
    public Users getUser(String username) throws Exception {
        UserDB userDB = new UserDB();
        Users user = userDB.getUser(username);
        return user;
    }
    
    public void addUser(String username, String password, String email, String firstName, String lastName, boolean active, boolean isAdmin) throws Exception {
        if(username != null && !username.equals("") && password != null && !password.equals("") && email != null && !email.equals("") && firstName != null && !firstName.equals("") && lastName != null && !lastName.equals("")) {
            UserDB userDB = new UserDB();
            Users user = new Users(username, password, email, firstName, lastName, active, isAdmin);
            userDB.addUser(user);            
        } else {
            throw new MissingInputsException();
        }
    }
    
    public void deleteUser(String loggedInUsername, String usernameToDelete) throws Exception {
        if(!loggedInUsername.equals(usernameToDelete)) { // Ensures the admin cannot delete their own account
            UserDB userDB = new UserDB();
            Users user = userDB.getUser(usernameToDelete);
            userDB.deleteUser(user);
        } else {
            throw new DeleteOwnAccountException();
        }  
    }
    
    public void updateUser(String username, String password, String email, String firstName, String lastName, boolean active, boolean isAdmin) throws Exception {
        if(username != null && !username.equals("") && password != null && !password.equals("") && email != null && !email.equals("") && firstName != null && !firstName.equals("") && lastName != null && !lastName.equals("")) {
            UserDB userDB = new UserDB();
            Users user = userDB.getUser(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setActive(active);
            user.setIsAdmin(isAdmin);
            userDB.updateUser(user);   
        } else {
            throw new MissingInputsException();
        }
    }
}
