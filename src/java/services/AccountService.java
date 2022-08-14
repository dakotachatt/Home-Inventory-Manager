package services;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public void newAccountConfirm(String email, String path, String url) {
        UserService us = new UserService();
        
        try {
            User user = us.getUser(email);
            
            if(user != null) {
                String to = user.getEmail();
                String subject = "Home nVentory - Confirm Account";
                String template = path + "/templates/welcomeconfirmation.html";
                String uuid = user.getUuid();
                String link = url + "?uuid=" + uuid;
                
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", user.getFirstName());
                tags.put("lastname", user.getLastName());
                tags.put("link", link);
                
                GmailService.sendMail(to, subject, template, tags);
            }
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void forgotPassword(String email, String path, String url) {
        UserService us = new UserService();
        
        try {
            User user = us.getUser(email);
            
            if(user != null) {
                String to = user.getEmail();
                String subject = "Home nVentory - Password Reset";
                String template = path + "/templates/resetpassword.html";
                String uuid = UUID.randomUUID().toString();
                us.updateUserUUID(email, uuid);
                String link = url + "?uuid=" + uuid;
                
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", user.getFirstName());
                tags.put("lastname", user.getLastName());
                tags.put("link", link);
                
                GmailService.sendMail(to, subject, template, tags);
            }
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void resetPassword(String uuid, String password) {
        UserService us = new UserService();
        
        try {
            User user = us.getUserByUUID(uuid);
            us.updateUserPassword(user, password);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
