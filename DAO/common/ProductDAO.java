package eStoreProduct.DAO.common;

import java.util.List;

import eStoreProduct.model.admin.entities.productsModel;
import eStoreProduct.model.admin.input.Category;
import eStoreProduct.model.admin.input.Product;
import eStoreProduct.utility.ProductStockPrice1;

public interface ProductDAO {

	public boolean createProduct(Product p);

	public List<String> getAllProductCategories();

	public List<ProductStockPrice1> getProductsByCategory(Integer category);

	public List<ProductStockPrice1> getAllProducts();

	public List<Category> getAllCategories();

	public ProductStockPrice1 getProductById(Integer productId);

	// -----------------------
	public boolean isPincodeValid(int pincode);

	public List<ProductStockPrice1> filterProductsByPriceRange(double minPrice, double maxPrice);

	public List<ProductStockPrice1> sortProductsByPrice(List<ProductStockPrice1> productList, String sortOrder);

	/* boolean createProduct(eStoreProduct.model.admin.input.Product p); */

	Integer getMaxProductId();

	productsModel getProductModelById(int prodid);

}