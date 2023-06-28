package eStoreProduct.DAO;

import java.util.List;

import eStoreProduct.model.Category;
import eStoreProduct.model.Product;
import eStoreProduct.utility.ProductStockPrice;

public interface ProductDAO {

	public boolean createProduct(Product p);

	public List<String> getAllProductCategories();

	public List<ProductStockPrice> getProductsByCategory(Integer category);

	public List<ProductStockPrice> getAllProducts();

	public List<Category> getAllCategories();

	public ProductStockPrice getProductById(Integer productId);

	// -----------------------
	public boolean isPincodeValid(int pincode);

	public List<ProductStockPrice> filterProductsByPriceRange(List<ProductStockPrice> productList, double minPrice,
			double maxPrice);

	public List<ProductStockPrice> sortProductsByPrice(List<ProductStockPrice> productList, String sortOrder);

	public int getproductgstcid(int pid);

	public List<ProductStockPrice> searchproducts(String search);

}