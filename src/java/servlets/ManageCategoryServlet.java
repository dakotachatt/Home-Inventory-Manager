package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Category;
import services.CategoryService;

/**
 *
 * @author Dakota
 */
public class ManageCategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            HttpSession session = request.getSession();
            String managedCategory = request.getParameter("categoryId");
            String action = request.getParameter("action");
            request.setAttribute("currentPage", "manageCategories"); //To determine which navbar option to highlight as active
            CategoryService cs = new CategoryService();
     
        try {
            List<Category> categories = cs.getAll();
            request.setAttribute("categories", categories);
            
            if(managedCategory != null && action != null) {
                request.setAttribute("categorySelected", managedCategory); // Used as check to show or hide add/edit user forms
                
                if(action.toLowerCase().equals("edit")) {
                    int managedCategoryId = Integer.parseInt(managedCategory);
                    Category category = cs.getCategory(managedCategoryId);
                    request.setAttribute("editCategory", category);
                    
                }
            }
            
            getServletContext().getRequestDispatcher("/WEB-INF/manageCategories.jsp").forward(request, response);
            session.setAttribute("message", null);
            return;
        } catch (Exception ex) {
            Logger.getLogger(ManageCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
