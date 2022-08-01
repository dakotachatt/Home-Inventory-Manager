package services;

import models.User;

/**
 *
 * @author Dakota Chatt
 * @version July 17, 2022
 */
public class AccountService {
    
    public User login(String email, String password) {
        
        UserService us = new UserService();
        User user = null;
        
        try {
            user = us.getUser(email);
        
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in AccountService");
        }
        
        if(user != null && password.equals(user.getPassword()) && user.getActive()) {
            return user;
        } else {
            return null;
        }
    }
}
