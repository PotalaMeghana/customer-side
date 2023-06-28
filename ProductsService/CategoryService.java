package eStoreProduct.ProductsService;

import java.util.List;

import eStoreProduct.DAO.CategoryDAO;
import eStoreProduct.model.Category;

public class CategoryService {
	private CategoryDAO categoryDAO;

	public CategoryService(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	public CategoryService() {
		// TODO Auto-generated constructor stub
	}

	public List<Category> getAllCategories() {
		return categoryDAO.getAllCategories();
	}
}