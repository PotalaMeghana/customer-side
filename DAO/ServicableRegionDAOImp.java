package eStoreProduct.DAO;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import eStoreProduct.model.ServicableRegionMapper;
import eStoreProduct.model.ServiceableRegion;

@Component
public class ServicableRegionDAOImp implements ServicableRegionDAO {
	private String getPincodes = "select * from slam_regions";
	JdbcTemplate jdbcTemplate;

	@Autowired
	public ServicableRegionDAOImp(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);

	}

	@Override
	public boolean getValidityOfPincode(int pincode) {
		// TODO Auto-generated method stub
		List<ServiceableRegion> rg = jdbcTemplate.query(getPincodes, new ServicableRegionMapper());
		for (ServiceableRegion r : rg) {
			if (pincode >= r.getSrrgPinFrom() && pincode <= r.getSrrgPinTo()) {
				return true;
			}
		}
		return false;
	}
}
