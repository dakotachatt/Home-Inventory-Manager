package servlets;

import exceptions.*;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Users;
import services.UserService;

/**
 *
 * @author Dakota Chatt
 * @version June 21, 2022
 */
public class AdminServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String managedUserName = request.getParameter("username");
        String action = request.getParameter("action");
        UserService us = new UserService();
        Users user = null;
        
        //Get all users to display in manage users table
        try {
            List<Users> users = us.getAll();
            request.setAttribute("users", users);
            
            if(managedUserName != null && action != null) { 
                request.setAttribute("userSelected", managedUserName); // Used as check to show or hide add/edit user forms
                
                if(action.toLowerCase().equals("delete")) { //Creates a user object to be referenced when deleting the user
                    us.deleteUser(username, managedUserName); //pass in logged in username as well as username of user to delete to ensure admin cannot delete their own account
                    
                    String message = "User: " + managedUserName + " has been deleted";
                    session.setAttribute("message", message);
                    
                    response.sendRedirect("admin");
                    return;                    
                } else if (action.toLowerCase().equals("edit")) { //Creates a user object when edit button is clicked in order to pre-fill edit user form
                    user = us.getUser(managedUserName);
                    request.setAttribute("editUser", user);                    
                }
            } else if (action != null) {
                 if (action.toLowerCase().equals("cancel")) {
                    response.sendRedirect("admin");
                    return;
                }
            }
            
        } catch (DeleteOwnAccountException e) {
            String message = "Cannot delete your own account";
            session.setAttribute("message", message);
            
            response.sendRedirect("admin");
            return;  
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Issue with the Admin page doGet");
            
            String message = "An error has ocurred, please try again";
            session.setAttribute("message", message);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
        session.setAttribute("message", null);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String managedUserName = request.getParameter("username");
        UserService us = new UserService();
        
        try {
            if(action != null) {
                if(action.equals("add")) {
                    String userName = request.getParameter("newUsername");
                    String password = request.getParameter("newPassword");
                    String email = request.getParameter("newEmail");
                    String firstName = request.getParameter("newFName");
                    String lastName = request.getParameter("newLName");

                    us.addUser(userName, password, email, firstName, lastName, true, false);

                    String message = "User: " + userName + " has been added";
                    session.setAttribute("message", message);

                    response.sendRedirect("admin");
                    return;
                } else if (action.toLowerCase().equals("edit")) {
                    String userName = managedUserName;
                    String password = request.getParameter("editPassword");
                    String email = request.getParameter("editEmail");
                    String firstName = request.getParameter("editFName");
                    String lastName = request.getParameter("editLName");
                    boolean isActive = false;
                    if(request.getParameter("editIsActive") != null) {
                        isActive = true;
                    }
                    boolean isAdmin = false;
                    if(request.getParameter("editIsAdmin") != null) {
                        isAdmin = true;
                    }
                    
                    us.updateUser(userName, password, email, firstName, lastName, isActive, isAdmin);
                    
                    String message = "User: " + managedUserName + " has been updated";
                    session.setAttribute("message", message);   
                    
                    response.sendRedirect("admin");
                    return;
                } 
            }
        } catch (MissingInputsException e) {
            String message = "All fields must be filled in";
            session.setAttribute("message", message);
            
            response.sendRedirect("admin");
            return;  
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Issue with the Admin page doPost");
            
            String message = "An error has ocurred, please try again";
            session.setAttribute("message", message);
        }
    }
}
