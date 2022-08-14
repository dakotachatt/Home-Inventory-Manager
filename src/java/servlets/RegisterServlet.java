package servlets;

import exceptions.MissingInputsException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;
import services.UserService;

/**
 *
 * @author Dakota
 */
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        String uuid = request.getParameter("uuid");
        UserService us = new UserService();
        User user = null;
        
        try {
            if (email != null) {
                user = us.getUser(email);
            }
            
            //If logged in session exists, redirect to applicable page
            if(user != null) { //If navigate manually to /login, and user session still active, redirect to /inventory
                if(user.getRole().getRoleId() == 1 || user.getRole().getRoleId() == 3) {
                    response.sendRedirect("manageUsers");
                    return;
                } else {
                    response.sendRedirect("inventory");
                    return;
                }
            } else if(uuid != null && us.getUserByUUID(uuid) != null) {//Ensures this page isn't viewable if UUID doesn't match to accoun
                User confirmedUser = us.getUserByUUID(uuid);
                us.activateNewUser(confirmedUser);
                getServletContext().getRequestDispatcher("/WEB-INF/accountconfirmed.jsp").forward(request, response);
                return;
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        session.setAttribute("message", null);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String path = getServletContext().getRealPath("/WEB-INF");
        String url = request.getRequestURL().toString();
        UserService us = new UserService();
        AccountService as = new AccountService();
        
        try {
            if(action != null) {
                if(action.equals("add")) {
                    
                    String newEmail = request.getParameter("newEmail");
                    String newFirstName = request.getParameter("newFName");
                    String newLastName = request.getParameter("newLName");
                    String newPassword = request.getParameter("newPassword");
                    
                    request.setAttribute("newEmail", newEmail);
                    request.setAttribute("newFirstName", newFirstName);
                    request.setAttribute("newLastName", newLastName);
                    
                    //Checks if email user is creating account with has already been used
                    if(us.getUser(newEmail) == null) {
                        //Adds user to database inactive
                        us.userAdd(newEmail, newFirstName, newLastName, newPassword);
                        
                        //Sends confirmation email with link to new user
                        as.newAccountConfirm(newEmail, path, url);   

                        
                        getServletContext().getRequestDispatcher("/WEB-INF/registerConfirmation.jsp").forward(request, response);
                        return;
                    } else {
                        String message = "Email is already in use, please choose another";
                        session.setAttribute("message", message);
                        
                        getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
                        return;
                    }
                }
            }
        } catch (MissingInputsException e) {
            String message = "All fields must be filled in";
            session.setAttribute("message", message);
            
            response.sendRedirect("register");
            return;  
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Issue with the Register page doPost");
            
            String message = "An error has ocurred, please try again";
            session.setAttribute("message", message);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        return;
    }
}
