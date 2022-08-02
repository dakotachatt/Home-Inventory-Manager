package services;

import dataaccess.CategoryDB;
import dataaccess.ItemDB;
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
public class ItemService {
    
    public List<Item> getAllItems() throws Exception {
        ItemDB itemsDB = new ItemDB();
        List<Item> items = itemsDB.getAll();
        return items;
    }
    
    public List<Item> getAllUserItems(String username) throws Exception {
        ItemDB itemsDB = new ItemDB();
        UserService us = new UserService();
        User user = us.getUser(username);
        List<Item> items = itemsDB.getAllUserItems(user);
        return items;
    }
    
    public Item getItem(int itemId) throws Exception {
        ItemDB itemsDB = new ItemDB();
        Item item = itemsDB.getItem(itemId);
        return item;
    }
    
    public void addItem(int categoryID, String itemName, double price, String email) throws Exception {
            CategoryDB catDB = new CategoryDB();
            ItemDB itemsDB = new ItemDB();
            UserDB userDB = new UserDB();
            User user = userDB.getUser(email);
            Category category = catDB.getCategory(categoryID);

            Item item = new Item(); //ItemID is auto generated after being inserted into Items table
            item.setCategory(category);
            item.setItemName(itemName);
            item.setPrice(price);
            item.setOwner(user);

            itemsDB.addItem(item);
    }
    
    public boolean deleteItem(int itemId, String loggedInEmail) throws Exception {
        ItemDB itemsDB = new ItemDB();
        Item item = itemsDB.getItem(itemId);
        
        if(item.getOwner().getEmail().equals(loggedInEmail)) {
            itemsDB.deleteItem(item);            
            return true;
        } else {
            System.out.println("Cannot delete another user's items");
            return false;
        }
    }
    
    public boolean updateItem(int itemId, int categoryId, String itemName, double itemPrice, String loggedInEmail) throws Exception {
        ItemDB itemDB = new ItemDB();
        CategoryDB categoryDB = new CategoryDB();

        Item item = itemDB.getItem(itemId);
        Category prevCategory = item.getCategory();
        Category category = categoryDB.getCategory(categoryId);
        item.setCategory(category);
        item.setItemName(itemName);
        item.setPrice(itemPrice);
        
        if(item.getOwner().getEmail().equals(loggedInEmail)) {
            itemDB.updateItem(item, prevCategory);
            return true;
        } else {
            System.out.println("Cannot edit another user's items");
            return false;
        }
    }
}
