package servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        session.setAttribute("message", null);
        String uuid = request.getParameter("uuid");
        UserService us = new UserService();
        User user = null;
        
        try {
            if (email != null) {
                user = us.getUser(email);
            }
        
        } catch (Exception e) {
            System.out.println("Error");
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
        } else if(uuid != null) { //If uuid parameter is present, redirect to reset password page
            request.setAttribute("uuid", uuid);
            getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
            return;
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountService as = new AccountService();
        String action = request.getParameter("action");
        String path = getServletContext().getRealPath("/WEB-INF");
        String url = request.getRequestURL().toString();
        String email = request.getParameter("email");
        String password = request.getParameter("resetPassword");
        String uuid = request.getParameter("uuid");

        if(action != null) {
            if(action.equals("emaillink")) {
                if(!email.toLowerCase().equals("")) {
                    as.forgotPassword(email, path, url);
                    String message = "If the email address is associated with an account you will receive an email shortly";
                    request.setAttribute("message", message);

                    getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
                    return;
                } else {
                    String message = "Please enter your email";
                    request.setAttribute("message", message);

                    getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
                    return;
                }
            } else if (action.equals("resetpassword")) {
                if(!password.equals("")) {
                    as.resetPassword(uuid, password);
                    String message = "Please try logging into your account with your new password";
                    request.setAttribute("message", message);

                    getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
                    return;
                } else {
                    String message = "Please enter a new password";
                    request.setAttribute("message", message);

                    getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
                    return;
                }
            }
        }
    }
}
