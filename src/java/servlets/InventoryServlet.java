package servlets;

import exceptions.MissingInputsException;
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
        String email = (String) session.getAttribute("email");
        String action = request.getParameter("action");
        request.setAttribute("currentPage", "inventory"); //To determine which navbar option to highlight as active
        String itemIdString = request.getParameter("itemId");
        UserService us = new UserService();
        ItemService is = new ItemService();
        CategoryService cs = new CategoryService();
        
        try{
            User user = us.getUser(email);
            request.setAttribute("user", user);
            
            List<Item> items = user.getItemList();
            request.setAttribute("items", items);
            
            List<Category> categories = cs.getAll();
            request.setAttribute("categories", categories);
            
            if(action != null && itemIdString != null) {
                int itemId = Integer.parseInt(itemIdString);
                request.setAttribute("itemSelected", itemId);
                
                if(action.toLowerCase().equals("delete")) {
                    boolean wasDeleted = is.deleteItem(itemId, email);
                    
                    if(wasDeleted) {
                        String message = "Item has been deleted";
                        session.setAttribute("message", message);
                    }
                    
                    response.sendRedirect("inventory");
                    return;    
                } else if (action.toLowerCase().equals("edit")) {
                    
                    Item item = is.getItem(itemId);
                    
                    if(us.getUser(email).equals(item.getOwner())) {
                        request.setAttribute("editItem", item);
                    }
                }
            } else if (action != null) {
                 if (action.toLowerCase().equals("cancel")) {
                    response.sendRedirect("inventory");
                    return;
                }
            }
            
        } catch (Exception e) {
            System.out.println("Inventory Servlet Get error");
            response.sendRedirect("inventory");
            return;  
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
        session.setAttribute("message", null);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        String action = request.getParameter("action");
        String itemIdString = request.getParameter("itemId");
        System.out.println(itemIdString);
        UserService us = new UserService();
        ItemService is = new ItemService();
        
        try{
            if(action != null) {
                if(action.toLowerCase().equals("add")) {
                    String categoryString = request.getParameter("newCategory");
                    String itemName = request.getParameter("newItemName").toLowerCase();
                    String priceInput = request.getParameter("newPrice");
                    
                    if(categoryString != null && !categoryString.equals("") && itemName != null && !itemName.equals("") && priceInput != null && !priceInput.equals("")) {
                    int categoryId = Integer.parseInt(categoryString);
                    double price = Double.parseDouble(priceInput);
                    is.addItem(categoryId, itemName, price, email);
                    
                    String message = "Item has been added";
                    session.setAttribute("message", message);
                    } else { //displays message if user goes into developer tools and removes client side validation
                        String message = "All fields must be filled in";
                        session.setAttribute("message", message);
            
                        response.sendRedirect("inventory");
                        return;
                    } 
                } else if (action.toLowerCase().equals("edit")) {
                    String categoryString = request.getParameter("editCategory");
                    String itemName = request.getParameter("editItemName").toLowerCase();
                    String priceInput = request.getParameter("editPrice");
                    
                    if(categoryString != null && !categoryString.equals("") && itemName != null && !itemName.equals("") && priceInput != null && !priceInput.equals("")) {
                        int itemId = Integer.parseInt(itemIdString);
                        int categoryId = Integer.parseInt(categoryString);
                        double price = Double.parseDouble(priceInput);

                        is.updateItem(itemId, categoryId, itemName, price, email);
                        
                        if(us.getUser(email).equals(is.getItem(itemId).getOwner())) {
                            String message = "Item has been updated";
                            session.setAttribute("message", message); 
                        }
                    } else {
                        String message = "All fields must be filled in";
                        session.setAttribute("message", message);
            
                        response.sendRedirect("inventory");
                        return;
                    }  
                }
            }
        } catch (NumberFormatException e) {
            String message = "Price must be a number of the format ##.##, decimal points are optional";
            session.setAttribute("message", message);
            
            response.sendRedirect("manageUsers");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in Inventory doPost");
        }
        
        response.sendRedirect("inventory");
        return;
    }
}
