package eStoreProduct.DAO.admin;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import eStoreProduct.model.admin.entities.SlamOrderModel;
import eStoreProduct.model.admin.entities.SlamOrderProduct;

@Component
public class OrderReportDAOImpl implements OrderReportDAO {
	@PersistenceContext
	private EntityManager entityManager;
       //method to get all orders placed
	@Override
	@Transactional
	public List<SlamOrderModel> getAllOrders() {
		List<SlamOrderModel> slamOrders;
		try {
			// Retrieve all SlamOrders
			TypedQuery<SlamOrderModel> query = entityManager.createQuery("SELECT o FROM SlamOrderModel o",
					SlamOrderModel.class);
			slamOrders = query.getResultList();

				// Access related SlamOrderProducts
				List<SlamOrderProduct> orderProducts = ((SlamOrderModel) slamOrders).getOrderProducts();
				
				return slamOrders;
			}
		 catch (Exception e) {
			// Handle the exception appropriately (e.g., logging, throwing custom exception, etc.)
			e.printStackTrace();
			return Collections.emptyList(); // or throw an exception if required
		}
	}

}
