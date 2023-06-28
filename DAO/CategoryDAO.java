package eStoreProduct.DAO;

import java.util.List;

import eStoreProduct.model.Category;

public interface CategoryDAO {

	public List<Category> getAllCategories();
	boolean addNewCategory(Category catg);
}
