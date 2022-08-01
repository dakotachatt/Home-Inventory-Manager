package services;

import dataaccess.CategoriesDB;
import dataaccess.ItemsDB;
import dataaccess.UserDB;
import java.util.List;
import models.Categories;
import models.Items;
import models.Users;
import exceptions.*;

/**
 *
 * @author Dakota Chatt
 * @version July 20, 2022
 */
public class InventoryService {
    
    public List<Categories> getAllCategories() throws Exception {
        CategoriesDB catDB = new CategoriesDB();
        List<Categories> categories = catDB.getAll();
        return categories;
    }
    
    public Categories getCategory(int categoryId) throws Exception {
        CategoriesDB catDB = new CategoriesDB();
        Categories category = catDB.getCategory(categoryId);
        return category;
    }
    
    public List<Items> getAllItems() throws Exception {
        ItemsDB itemsDB = new ItemsDB();
        List<Items> items = itemsDB.getAll();
        return items;
    }
    
    public List<Items> getAllUserItems(String username) throws Exception {
        ItemsDB itemsDB = new ItemsDB();
        UserService us = new UserService();
        Users user = us.getUser(username);
        List<Items> items = itemsDB.getAllUserItems(user);
        return items;
    }
    
    public Items getItem(int itemId) throws Exception {
        ItemsDB itemsDB = new ItemsDB();
        Items item = itemsDB.getItem(itemId);
        return item;
    }
    
    public void addItem(int categoryID, String itemName, double price, String username) throws Exception {
            CategoriesDB catDB = new CategoriesDB();
            ItemsDB itemsDB = new ItemsDB();
            UserDB userDB = new UserDB();
            Users user = userDB.getUser(username);
            Categories category = catDB.getCategory(categoryID);

            Items item = new Items(); //ItemID is auto generated after being inserted into Items table
            item.setCategory(category);
            item.setItemName(itemName);
            item.setPrice(price);
            item.setOwner(user);

            itemsDB.addItem(item);
    }
    
    public boolean deleteItem(int itemId, String loggedInUsername) throws Exception {
        ItemsDB itemsDB = new ItemsDB();
        Items item = itemsDB.getItem(itemId);
        
        if(item.getOwner().getUsername().equals(loggedInUsername)) {
            itemsDB.deleteItem(item);            
            return true;
        } else {
            System.out.println("Cannot delete another user's items");
            return false;
        }
    }
}
