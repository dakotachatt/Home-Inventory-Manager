package services;

import dataaccess.CategoryDB;
import java.util.List;
import models.Category;

/**
 *
 * @author Dakota
 */
public class CategoryService {
        public List<Category> getAllCategories() throws Exception {
        CategoryDB catDB = new CategoryDB();
        List<Category> categories = catDB.getAll();
        return categories;
    }
    
    public Category getCategory(int categoryId) throws Exception {
        CategoryDB catDB = new CategoryDB();
        Category category = catDB.getCategory(categoryId);
        return category;
    }
}
