package servlets;

import exceptions.MissingInputsException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        
        getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        session.setAttribute("message", null);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        UserService us = new UserService();
        
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
                    
                    if(us.getUser(newEmail) == null) {
                        us.addUser(newEmail, newFirstName, newLastName,  newPassword);
                        
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
