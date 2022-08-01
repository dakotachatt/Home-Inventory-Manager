package services;

import dataaccess.CategoriesDB;
import dataaccess.ItemsDB;
import dataaccess.UserDB;
import java.util.List;
import models.Category;
import models.Item;
import models.User;
import exceptions.*;

/**
 *
 * @author Dakota Chatt
 * @version July 20, 2022
 */
public class InventoryService {
    
    public List<Category> getAllCategories() throws Exception {
        CategoriesDB catDB = new CategoriesDB();
        List<Category> categories = catDB.getAll();
        return categories;
    }
    
    public Category getCategory(int categoryId) throws Exception {
        CategoriesDB catDB = new CategoriesDB();
        Category category = catDB.getCategory(categoryId);
        return category;
    }
    
    public List<Item> getAllItems() throws Exception {
        ItemsDB itemsDB = new ItemsDB();
        List<Item> items = itemsDB.getAll();
        return items;
    }
    
    public List<Item> getAllUserItems(String username) throws Exception {
        ItemsDB itemsDB = new ItemsDB();
        UserService us = new UserService();
        User user = us.getUser(username);
        List<Item> items = itemsDB.getAllUserItems(user);
        return items;
    }
    
    public Item getItem(int itemId) throws Exception {
        ItemsDB itemsDB = new ItemsDB();
        Item item = itemsDB.getItem(itemId);
        return item;
    }
    
    public void addItem(int categoryID, String itemName, double price, String username) throws Exception {
            CategoriesDB catDB = new CategoriesDB();
            ItemsDB itemsDB = new ItemsDB();
            UserDB userDB = new UserDB();
            User user = userDB.getUser(username);
            Category category = catDB.getCategory(categoryID);

            Item item = new Item(); //ItemID is auto generated after being inserted into Items table
            item.setCategory(category);
            item.setItemName(itemName);
            item.setPrice(price);
            item.setOwner(user);

            itemsDB.addItem(item);
    }
    
    public boolean deleteItem(int itemId, String loggedInUsername) throws Exception {
        ItemsDB itemsDB = new ItemsDB();
        Item item = itemsDB.getItem(itemId);
        
        if(item.getOwner().getEmail().equals(loggedInUsername)) {
            itemsDB.deleteItem(item);            
            return true;
        } else {
            System.out.println("Cannot delete another user's items");
            return false;
        }
    }
}
