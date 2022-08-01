package servlets;

import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Users;
import services.AccountService;
import services.UserService;

/**
 *
 * @author Dakota Chatt
 * @version June 21, 2022
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        UserService us = new UserService();
        Users user = null;
        
        //Logging user out
        if(request.getParameter("logout") != null) {
            session.invalidate();
            String message = "You have successfully logged out";
            request.setAttribute("message", message);
            
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        
        try {
            if (username != null) {
            user = us.getUser(username);
            }
        
        } catch (Exception e) {
            System.out.println("Error");
        }

        
        //If logged in session exists, redirect to applicable page
        if(user != null) { //If navigate manually to /login, and user session still active, redirect to /inventory
            if(user.getIsAdmin()) {
                response.sendRedirect("admin");
                return;
            } else {
                response.sendRedirect("inventory");
                return;
            }
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        AccountService account = new AccountService();
        String usernameParam = request.getParameter("username").toLowerCase(); //Ensures case insensitive usernames
        Users user = account.login(usernameParam, request.getParameter("password"));
        
        //Checks if username or password field is empty
        if(usernameParam == null || usernameParam.equals("") || request.getParameter("password") == null || request.getParameter("password").equals("")) {
            String message = "Please enter a username and password";
            request.setAttribute("message", message);
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
            
        } else if(user != null) { //If login successful, redirect to inventory or admin page
            session.setAttribute("username", user.getUsername());

            if(user.getIsAdmin()) {
                response.sendRedirect("admin");
                return;
            } else {
                response.sendRedirect("inventory");
                return;              
            }
        } else { //Username and password combo not found, display error message
            String message = "Username or password is incorrect, or account is inactive";
            request.setAttribute("message", message);
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
    }
}
