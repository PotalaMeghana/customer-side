package eStoreProduct.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import eStoreProduct.DAO.ProdStockDAO;
import eStoreProduct.utility.ProductStockPrice;

@Component
public class ProductRowMapper implements RowMapper<ProductStockPrice> {

	private ProdStockDAO prodStockDAO;

	public ProductRowMapper(ProdStockDAO prodStockDAO) {
		this.prodStockDAO = prodStockDAO;
	}

	@Override
	public ProductStockPrice mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProductStockPrice product = new ProductStockPrice(prodStockDAO);
		product.setProd_id(rs.getInt("prod_id"));
		product.setProd_title(rs.getString("prod_title"));
		product.setProd_brand(rs.getString("prod_brand"));
		product.setImage_url(rs.getString("image_url"));
		product.setProd_desc(rs.getString("prod_desc"));
		// product.setProd_gstc_id(rs.getInt("prod_gstc_id"));
		product.setMrp();
		product.setPrice();
		product.setProduct_stock();

		return product;
	}
}
