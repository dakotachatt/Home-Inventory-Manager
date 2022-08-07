package services;

import dataaccess.CategoryDB;
import exceptions.MissingInputsException;
import java.util.List;
import models.Category;

/**
 *
 * @author Dakota
 */
public class CategoryService {
        public List<Category> getAll() throws Exception {
        CategoryDB catDB = new CategoryDB();
        List<Category> categories = catDB.getAll();
        return categories;
    }
    
    public Category getCategory(int categoryId) throws Exception {
        CategoryDB catDB = new CategoryDB();
        Category category = catDB.getCategory(categoryId);
        return category;
    }
    
    public void addCategory(String categoryName) {
        CategoryDB catDB = new CategoryDB();
        Category category = new Category();
        category.setCategoryName(categoryName);
        
        catDB.addCategory(category);
    }
    
    public void updateCategory(int categoryId, String categoryName) throws Exception {
        CategoryDB catDB = new CategoryDB();
        Category category = catDB.getCategory(categoryId);
        
        if(categoryName != null || !categoryName.equals("")) {
            category.setCategoryName(categoryName);
        } else {
            throw new MissingInputsException();
        }
        
        catDB.updateCategory(category);
    }
}
