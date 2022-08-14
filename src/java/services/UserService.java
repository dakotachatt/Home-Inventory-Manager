package services;

import dataaccess.CompanyDB;
import dataaccess.RoleDB;
import dataaccess.UserDB;
import exceptions.*;
import java.util.List;
import java.util.UUID;
import models.Company;
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
    
    public List<User> getAllCompany(int companyId) throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAllCompany(companyId);
        return users;
    }
    
    public User getUser(String email) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.getUser(email);
        return user;
    }
    
    public User getUserByUUID(String uuid) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.getUserByUUID(uuid);
        return user;
    }
    
    public void updateUserPassword(User user, String password) throws Exception {
        UserDB userDB = new UserDB();
        user.setPassword(password);
        user.setUuid(null); //removes UUID from user data
        userDB.userUpdate(user);
    }
    
    public void updateUserUUID(String email, String uuid) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.getUser(email);
        user.setUuid(uuid);
        userDB.userUpdate(user);
    }
    
    public void adminAddUser(String loggedInEmail, String email, String firstName, String lastName, String password) throws Exception {
        if(password != null && !password.equals("") && email != null && !email.equals("") && firstName != null && !firstName.equals("") && lastName != null && !lastName.equals("")) {
            UserDB userDB = new UserDB();
            RoleDB roleDB = new RoleDB();
            CompanyDB companyDB = new CompanyDB();
            User requestingUser = userDB.getUser(loggedInEmail);
            User user = new User(email, true, firstName, lastName, password);
            Role role = roleDB.getRole(2);
            //If user creating the user account is a system admin, create the user without a company
            Company company = companyDB.getCompany(2);
            
            //If the user creating the user account is a company admin, add the user to their company
            if(requestingUser != null && requestingUser.getRole().getRoleId() == 3) {
                company = companyDB.getCompany(requestingUser.getCompany().getCompanyId());
            }
            
            user.setRole(role);
            user.setCompany(company);
            userDB.addUser(user);
        } else {
            throw new MissingInputsException();
        }
    }
    
    public void userAdd(String email, String firstName, String lastName, String password) throws Exception {
        if(password != null && !password.equals("") && email != null && !email.equals("") && firstName != null && !firstName.equals("") && lastName != null && !lastName.equals("")) {
            UserDB userDB = new UserDB();
            RoleDB roleDB = new RoleDB();
            CompanyDB companyDB = new CompanyDB();
            User user = new User(email, false, firstName, lastName, password); //New users not created by admin are deactivated by default
            
            //UUID logic for initiating user account activation process
            String uuid = UUID.randomUUID().toString();
            user.setUuid(uuid); //Sets new user with uuid to be verified by confirmation link
            
            Role role = roleDB.getRole(2);
            Company company = companyDB.getCompany(2);
            
            user.setRole(role);
            user.setCompany(company);
            userDB.addUser(user);
        } else {
            throw new MissingInputsException();
        }
    }
    
    public void deleteUser(String loggedInEmail, String emailToDelete) throws Exception {
            UserDB userDB = new UserDB();
            User requestingUser = userDB.getUser(loggedInEmail);
            User user = userDB.getUser(emailToDelete);
            
        if(!loggedInEmail.equals(emailToDelete)) { // Ensures the admin cannot delete their own account
            if(requestingUser.getRole().getRoleId() == 1) {
                userDB.deleteUser(user);
            } else if(requestingUser.getRole().getRoleId() == 3 && user.getCompany().equals(requestingUser.getCompany())) {
                userDB.deleteUser(user);
            }

        } else {
            throw new OwnAccountException();
        }  
    }
    
    public void adminUpdateUser(String loggedInEmail, String email, String password, String firstName, String lastName, boolean active, int roleID, int companyID) throws Exception {
        if(password != null && !password.equals("") && firstName != null && !firstName.equals("") && lastName != null && !lastName.equals("")) {
            UserDB userDB = new UserDB();
            RoleDB roleDB = new RoleDB();
            CompanyDB companyDB = new CompanyDB();
            
            User user = userDB.getUser(email);
            User requestingUser = userDB.getUser(loggedInEmail);
            Role prevRole = user.getRole();
            Role role = roleDB.getRole(roleID);
            Company prevCompany = user.getCompany();
            Company company = companyDB.getCompany(companyID);
            
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            
            /*If user is an admin and attempting to change their own role/company, throw exception, else if user is an admin changing another users role then change it*/
            if(loggedInEmail.equals(email) && (requestingUser.getRole().getRoleId() == 1 || requestingUser.getRole().getRoleId() == 3) && (!role.equals(prevRole) || !company.equals(prevCompany) || user.getActive() != active)) {
                throw new OwnAccountException();
            } else if(requestingUser.getRole().getRoleId() == 1) {
                user.setActive(active);
                user.setRole(role);
                user.setCompany(company);
              //Ensures company admin can't change a user's role to system admin with URL rewriting
            } else if (requestingUser.getRole().getRoleId() == 3 && role.getRoleId() != 1) {
                user.setActive(active);
                user.setRole(role);
            }
            
            userDB.adminUpdateUser(user, prevRole, prevCompany);   
        } else {
            throw new MissingInputsException();
        }
    }
    
    public void userUpdate(String loggedInEmail, String password, String firstName, String lastName) throws Exception {
        if(password != null && !password.equals("") && firstName != null && !firstName.equals("") && lastName != null && !lastName.equals("")) {
            UserDB userDB = new UserDB();
            User user = userDB.getUser(loggedInEmail);
            
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            
            userDB.userUpdate(user);
        } else {
            throw new MissingInputsException();
        }
    }
    
    public void deactivateUser(String email) throws Exception {
        UserDB userDB = new UserDB();

        User user = userDB.getUser(email);
        user.setActive(false);

        userDB.userUpdate(user);
    }
    
    public void activateNewUser(User user) throws Exception {
        UserDB userDB = new UserDB();
        user.setUuid(null);
        user.setActive(true);
        
        userDB.userUpdate(user);
    }
}
