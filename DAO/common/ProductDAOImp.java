//package eStoreProduct.DAO.common;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//import javax.transaction.Transactional;
//
//import org.springframework.stereotype.Component;
//
//import eStoreProduct.model.admin.entities.productStockModel;
//import eStoreProduct.model.admin.entities.productsModel;
//import eStoreProduct.model.admin.input.Category;
//import eStoreProduct.model.admin.input.Product;
//import eStoreProduct.utility.ProductStockPrice1;
//
//@Component
//public class ProductDAOImp implements ProductDAO {
//
//	@PersistenceContext
//	private EntityManager entityManager;
//
//	//getting the latest product id 
//	@Override
//	@Transactional
//	public Integer getMaxProductId() {
//		String query = "SELECT MAX(p.id) FROM productsModel p";
//		TypedQuery<Integer> maxIdQuery = entityManager.createQuery(query, Integer.class);
//		Integer maxId = maxIdQuery.getSingleResult();
//		return maxId != null ? maxId : 0;
//	}
//
//	//method to add a new product 
//	@Override
//	@Transactional
//	public boolean createProduct(Product p) {
//		//get the latest productid and add new product by incrementing
//		int p_id = getMaxProductId();
//		p_id = p_id + 1;
//		productsModel productEntity = new productsModel();
//		//set the product attributes
//		productEntity.setId(p_id);
//		productEntity.setTitle(p.getProd_title());
//		productEntity.setProductCategory(p.getProd_prct_id());
//		productEntity.setHsnCode(p.getProd_gstc_id());
//		productEntity.setBrand(p.getProd_brand());
//		productEntity.setImageUrl(p.getImage_url());
//		productEntity.setDescription(p.getProd_desc());
//		productEntity.setReorderLevel(p.getReorderLevel());
//		//execute query to save in database
//		entityManager.merge(productEntity);
//
//		return productEntity.getId() != null;
//
//	}
//
//	//get the product by its id
//	@Override
//	@Transactional
//	public productsModel getProductModelById(int prodid) {
//		//execute query
//		productsModel pm = entityManager.find(productsModel.class, prodid);
//		return pm;
//	}
//
//	//method to get all the available products
//	@Override
//	@Transactional
//	public List<ProductStockPrice1> getAllProducts() {
//		String query = "SELECT new eStoreProduct.utility.ProductStockPrice(p.id, p.title, p.brand, p.imageUrl, p.description, ps.price)"
//				+ " FROM eStoreProduct.model.admin.entities.productsModel p JOIN eStoreProduct.model.admin.entities.productStockModel ps ON p.id = ps.product";
//		//create JPA query and execute
//		TypedQuery<ProductStockPrice1> typedQuery = entityManager.createQuery(query, ProductStockPrice1.class);
//		return typedQuery.getResultList();
//	}
//
//	//method to get all the available categories along withthe details
//	@Override
//	@Transactional
//	public List<Category> getAllCategories() {
//		String query = "SELECT new eStoreProduct.model.admin.input.Category(c.id, c.prct_title, c.description)"
//				+ " FROM eStoreProduct.model.admin.entities.productCategoryModel c";
//
//		//create JPA query and execute
//		TypedQuery<Category> typedQuery = entityManager.createQuery(query, Category.class);
//		return typedQuery.getResultList();
//	}
//
//	//method to get products based on the categories selected
//	@Override
//	@Transactional
//	public List<ProductStockPrice1> getProductsByCategory(Integer category_id) {
//		String query = "SELECT new eStoreProduct.utility.ProductStockPrice(p.id, p.title, p.brand, p.imageUrl, p.description, ps.price)"
//				+ " FROM eStoreProduct.model.admin.entities.productsModel p JOIN eStoreProduct.model.admin.entities.productStockModel ps"
//				+ " on p.id=ps.product WHERE p.productCategory = :categoryId ";
//		TypedQuery<ProductStockPrice1> typedQuery = entityManager.createQuery(query, ProductStockPrice1.class);
//		typedQuery.setParameter("categoryId", category_id);
//		return typedQuery.getResultList();
//	}
//
//	//method to get the product by its id
//	@Override
//	@Transactional
//	public ProductStockPrice1 getProductById(Integer productId) {
//		productsModel pm = entityManager.find(productsModel.class, productId);
//		productStockModel psm = entityManager.find(productStockModel.class, productId);
//		ProductStockPrice1 psp = new ProductStockPrice1(pm.getId(), pm.getTitle(), pm.getBrand(), pm.getImageUrl(),
//				pm.getDescription(), psm.getPrice());
//		return psp;
//	}
//
//	//method to getall the products categories available
//	@Override
//	@Transactional
//	public List<String> getAllProductCategories() {
//		String query = "SELECT c.prct_title FROM eStoreProduct.model.model.entities.productCategoryModel c";
//		TypedQuery<String> typedQuery = entityManager.createQuery(query, String.class);
//		return typedQuery.getResultList();
//	}
//
//	//check the pincode availability for the region specified
//	@Override
//	@Transactional
//	public boolean isPincodeValid(int pincode) {
//		String query = "SELECT COUNT(*) FROM Region r WHERE :pincode BETWEEN r.pinFrom AND r.pinTo";
//		Integer count = entityManager.createQuery(query, Integer.class).setParameter("pincode", pincode)
//				.getSingleResult();
//		return count > 0;
//	}
//
//	//show products based on the price range selected
//	@Override
//	public List<ProductStockPrice1> filterProductsByPriceRange(double minPrice, double maxPrice) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	//method to sort the products on the filter applied 
//	@Override
//	public List<ProductStockPrice1> sortProductsByPrice(List<ProductStockPrice1> productList, String sortOrder) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//}
