package services;

import models.Users;

/**
 *
 * @author Dakota Chatt
 * @version July 17, 2022
 */
public class AccountService {
    
    public Users login(String username, String password) {
        
        UserService us = new UserService();
        Users user = null;
        
        try {
            user = us.getUser(username);
        
        } catch (Exception e) {
            System.out.println("Error in AccountService");
        }
        
        if(user != null && password.equals(user.getPassword()) && user.getActive()) {
            return user;
        } else {
            return null;
        }
    }
}
