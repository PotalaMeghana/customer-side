/*
 * package eStoreProduct.DAO;
 * 
 * import java.sql.Connection; import java.sql.DriverManager; import
 * java.sql.PreparedStatement; import java.sql.ResultSet; import
 * java.util.ArrayList; import java.util.List;
 * 
 * import javax.persistence.EntityManager; import
 * javax.persistence.PersistenceContext; import javax.sql.DataSource;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.jdbc.core.JdbcTemplate; import
 * org.springframework.stereotype.Component;
 * 
 * import eStoreProduct.model.Category; import
 * eStoreProduct.model.CategoryRowMapper;
 * 
 * @Component public class CategoryDAOImp implements CategoryDAO{
 * 
 * JdbcTemplate jdbcTemplate; private String SQL_INSERT_CATEGORY =
 * "insert into slam_productCAtegories(prct_id,prct_title,prct_desc) values(?,?,?)"
 * ; private String SQL_GET_TOP_CATGID =
 * "select prct_id from slam_productCAtegories order by prct_id desc limit 1";
 * private String
 * SQL_GET_CATEGORIES="SELECT prct_title FROM slam_productCategories";
 * 
 * @Autowired public CategoryDAOImp(DataSource dataSource) { jdbcTemplate = new
 * JdbcTemplate(dataSource); }
 * 
 * @Override public boolean addNewCategory(Category catg) { int c_id =
 * jdbcTemplate.queryForObject(SQL_GET_TOP_CATGID, int.class); c_id = c_id + 1;
 * System.out.println(c_id + "Category_id\n");
 * 
 * return jdbcTemplate.update(SQL_INSERT_CATEGORY, c_id, catg.getPrct_title(),
 * catg.getPrct_desc()) > 0; }
 * 
 * public List<Category> getAllCategories() { List<Category> categories = new
 * ArrayList<>();
 * 
 * categories=jdbcTemplate.query(SQL_GET_CATEGORIES,new CategoryRowMapper());
 * 
 * return categories; } }
 */