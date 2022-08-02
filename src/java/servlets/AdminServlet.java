package servlets;

import exceptions.*;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Category;
import models.Item;
import models.Role;
import models.User;
import services.RoleService;
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
        String email = (String) session.getAttribute("email");
        String managedEmail = request.getParameter("email");
        String action = request.getParameter("action");
        UserService us = new UserService();
        RoleService rs = new RoleService();
        User user = null;
        
        //Get all users to display in manage users table
        try {
            List<User> users = us.getAll();
            request.setAttribute("users", users);
            
            List<Role> roles = rs.getAll();
            request.setAttribute("roles", roles);
            
            //To be deleted after check
            Role regRole = rs.getRole(2);
            List<User> regUsers = regRole.getUserList();
            request.setAttribute("regUsers", regUsers);
            
            
            regRole = rs.getRole(1);
            List<User> sysAdUsers = regRole.getUserList();
            request.setAttribute("sysAdUsers", sysAdUsers);
            
            if(managedEmail != null && action != null) { 
                request.setAttribute("userSelected", managedEmail); // Used as check to show or hide add/edit user forms
                
                if(action.toLowerCase().equals("delete")) { //Creates a user object to be referenced when deleting the user
                    us.deleteUser(email, managedEmail); //pass in logged in email as well as email of user to delete to ensure admin cannot delete their own account
                    
                    String message = "User: " + managedEmail + " has been deleted";
                    session.setAttribute("message", message);
                    
                    response.sendRedirect("admin");
                    return;                    
                } else if (action.toLowerCase().equals("edit")) { //Creates a user object when edit button is clicked in order to pre-fill edit user form
                    user = us.getUser(managedEmail);
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
        String managedEmail = request.getParameter("email");
        UserService us = new UserService();
        
        try {
            if(action != null) {
                if(action.equals("add")) {
                    String email = request.getParameter("newEmail");
                    String firstName = request.getParameter("newFName");
                    String lastName = request.getParameter("newLName");
                    String password = request.getParameter("newPassword");

                    us.addUser(email, firstName, lastName,  password);

                    String message = "User: " + email + " has been added";
                    session.setAttribute("message", message);

                    response.sendRedirect("admin");
                    return;
                } else if (action.toLowerCase().equals("edit")) {
                    String email = managedEmail;
                    String password = request.getParameter("editPassword");
                    String firstName = request.getParameter("editFName");
                    String lastName = request.getParameter("editLName");
                    int roleId = Integer.parseInt(request.getParameter("role"));
                    boolean isActive = false;
                    if(request.getParameter("editIsActive") != null) {
                        isActive = true;
                    }
                    
                    us.updateUser(email, password, firstName, lastName, isActive, roleId);
                    
                    String message = "User: " + managedEmail + " has been updated";
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
