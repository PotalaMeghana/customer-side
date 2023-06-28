package eStoreProduct.DAO;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import eStoreProduct.model.ProdStock;
import eStoreProduct.model.ProductStockRowMapper;

@Component
public class ProdStockDAOImp implements ProdStockDAO {
	private static final String PD_STK_QUERY = "SELECT * FROM slam_productstock";
	private static final String SELECT_PRD_STK_QUERY = "SELECT * FROM slam_productstock WHERE prod_id = ?";
	private static final String PD_PRICE_QUERY = "SELECT prod_price FROM slam_productstock WHERE prod_id = ?";
	private static final String PD_MRP_QUERY = "SELECT prod_mrp FROM slam_productstock WHERE prod_id = ?";
	private static final String Get_Prod_stck = "select prod_stock from slam_productStock where prod_id=?";
	

	private JdbcTemplate jdbcTemplate;

	public ProdStockDAOImp(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<ProdStock> getAllProdStocks() {
		return jdbcTemplate.query(PD_STK_QUERY, new ProductStockRowMapper());
	}

	@Override
	public ProdStock getProdStockById(int prodId) {
		return jdbcTemplate.queryForObject(SELECT_PRD_STK_QUERY, new ProductStockRowMapper(), prodId);
	}

	@Override
	public double getProdPriceById(int prodId) {
		return jdbcTemplate.queryForObject(PD_PRICE_QUERY, Double.class, prodId);
	}

	@Override
	public double getProdMrpById(int prodId) {
		return jdbcTemplate.queryForObject(PD_MRP_QUERY, Double.class, prodId);
	}

	@Override
	public int getProdStock(int prod_id) {
		
		return jdbcTemplate.queryForObject(Get_Prod_stck, Integer.class, prod_id);
	}
	@Override
	public int getProductQuantity(int productId,int orderId)
	{
		return jdbcTemplate.queryForObject("select orpr_qty from slam_orderproducts where ordr_id=? and prod_id=?", new Object[]{orderId, productId}, Integer.class);
	}
	@Override
	public void updateStock(int productId,int orderId)
	{
		int qty=getProductQuantity(productId,orderId);
		int quantity=qty+getProdStock(productId);
		String update_stock="update slam_productStock set prod_stock=? where prod_id=? ";
		jdbcTemplate.update(update_stock,quantity,productId);
	}
}