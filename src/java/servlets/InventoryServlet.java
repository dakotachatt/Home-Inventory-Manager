package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.*;
import services.*;

/**
 *
 * @author Dakota Chatt
 * @version June 21, 2022
 */
public class InventoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String action = request.getParameter("action");
        String itemIdString = request.getParameter("itemId");
        UserService us = new UserService();
        InventoryService is = new InventoryService();
        
        try{
            Users user = us.getUser(username);
            request.setAttribute("user", user);
            
            List<Items> items = user.getItemsList();
            request.setAttribute("items", items);
            
            List<Categories> categories = is.getAllCategories();
            request.setAttribute("categories", categories);
            
            if(action != null && itemIdString != null) {
                if(action.equals("delete")) {
                    int itemId = Integer.parseInt(itemIdString);
                    boolean wasDeleted = is.deleteItem(itemId, username);
                    
                    if(wasDeleted) {
                        String message = "Item has been deleted";
                        session.setAttribute("message", message);
                    }
                    
                    response.sendRedirect("inventory");
                    return;    
                }
            }
            
        } catch (Exception e) {
            System.out.println("Inventory Servlet Get error");
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
        session.setAttribute("message", null);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String action = request.getParameter("action");
        String categoryInput = request.getParameter("categories");
        String itemName = request.getParameter("itemName").toLowerCase();
        String priceInput = request.getParameter("price");
        InventoryService is = new InventoryService();
        
        try{
            if(categoryInput == null || categoryInput.equals("") || itemName == null || itemName.equals("") || priceInput == null || priceInput.equals("")) {
                String message = "Invalid. All fields must be filled.";
                session.setAttribute("message", message);
            } else if(action != null) {
                if(action.equals("add")) {
                    int categoryID = Integer.parseInt(categoryInput);
                    double price = Double.parseDouble(priceInput);
                    is.addItem(categoryID, itemName, price, username);
                    
                    String message = "Item has been added";
                    session.setAttribute("message", message);
                }
            }
        } catch (NumberFormatException e) {
            String message = "Price must be a number of the format ##.##, decimal points are optional";
            session.setAttribute("message", message);
            
            response.sendRedirect("admin");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in Inventory doPost");
        }
        
        response.sendRedirect("inventory");
        return;
    }
}
