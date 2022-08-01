package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.UserService;

/**
 *
 * @author Dakota
 */
public class AdminFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        UserService us = new UserService();
        String email = (String) session.getAttribute("email");
        
        try {
            User user = us.getUser(email);
            
            // If user session exists and user does not have admin role privileges, redirect to inventory page
            if(user != null && user.getRole().getRoleId() == 2) {
                httpResponse.sendRedirect("inventory");
                return;
            }
            
        } catch (Exception e) {
            System.out.println("Error in Admin Filter");
        }
        
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
    
        
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
