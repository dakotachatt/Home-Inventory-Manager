package servlets;

import exceptions.MissingInputsException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.UserService;

/**
 *
 * @author Dakota
 */
public class AccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        String action = request.getParameter("action");
        UserService us = new UserService();
        
        try {
            User user = us.getUser(email);
            request.setAttribute("user", user);
            
            if(action != null) {
                if(action.toLowerCase().equals("back")) {
                    response.sendRedirect("inventory");
                    return;
                } else if (action.toLowerCase().equals("deactivate account") && request.getParameter("logout") != null) {
                    
                    us.deactivateUser(email);
                
                    String message = "Your account has been successfully deactivated";
                    request.setAttribute("message", message);

                    getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                    session.setAttribute("message", null);
                    return;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
        session.setAttribute("message", null);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String email = (String) session.getAttribute("email");
        UserService us = new UserService();
        
        try {
            if(action != null) {
                if (action.toLowerCase().equals("edit")) {
                    User user = us.getUser(email);

                    String password = request.getParameter("editPassword");
                    String firstName = request.getParameter("editFName");
                    String lastName = request.getParameter("editLName");
                    boolean isActive = user.getActive();
                    
                    //Note in this case, the logged in email and email of account to be updated is the same as this is the user's page
                    us.updateUser(email, email, password, firstName, lastName, isActive, user.getRole().getRoleId());
                    
                    String message = "Your account information has been updated";
                    session.setAttribute("message", message);   
                    
                    response.sendRedirect("account");
                    return;
                }
            }
        } catch (MissingInputsException e) {
            String message = "All fields must be filled in";
            session.setAttribute("message", message);
            
            response.sendRedirect("account");
            return;  
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Issue with the Account page doPost");
            
            String message = "An error has ocurred, please try again";
            session.setAttribute("message", message);
        }
        
    }
}
