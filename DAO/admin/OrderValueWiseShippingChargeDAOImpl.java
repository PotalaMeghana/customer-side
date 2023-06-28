package eStoreProduct.DAO.admin;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import eStoreProduct.model.admin.entities.OrderValueWiseShippingChargesModel;
import eStoreProduct.model.admin.input.OrderValueWiseShippingChargesInput;
import eStoreProduct.model.admin.output.OrderValueWiseShippingCharge;

@Component
public class OrderValueWiseShippingChargeDAOImpl implements OrderValueWiseShippingChargeDAO {

	@PersistenceContext
	private EntityManager entityManager;

	//method to get all the ordervalue wise shipment charges
	@Override
	@Transactional
	public List<OrderValueWiseShippingCharge> getAll() {
		try {
			//create JPA query
			TypedQuery<OrderValueWiseShippingCharge> query = entityManager.createQuery(
					"SELECT NEW eStoreProduct.model.admin.output.OrderValueWiseShippingCharge("
							+ "ow.id,ow.from,ow.to,ow.shippingAmount)"
							+ " FROM eStoreProduct.model.admin.entities.OrderValueWiseShippingChargesModel ow",
					OrderValueWiseShippingCharge.class);
			//execute the query
			List<OrderValueWiseShippingCharge> ordervaluecharges = query.getResultList();
			
			return ordervaluecharges;
		} catch (Exception e) {
			// Handle the exception appropriately (e.g., logging, throwing custom exception, etc.)
			e.printStackTrace();
			return Collections.emptyList(); // or throw an exception if required
		}
	}

	//method to add new charges 
	@Override
	@Transactional
	public boolean addCharge(OrderValueWiseShippingChargesInput ord) {
		//create object and set the corresponding values
		OrderValueWiseShippingChargesModel ordervaluecharges = new OrderValueWiseShippingChargesModel();
		ordervaluecharges.setId(ord.getId());
		ordervaluecharges.setFrom(ord.getFrom());
		ordervaluecharges.setTo(ord.getTo());
		ordervaluecharges.setShippingAmount(ord.getShippingAmount());
		try {
			entityManager.merge(ordervaluecharges);
			return true;
		}

		catch (Exception e) {
			// Handle the exception appropriately (e.g., logging, throwing custom exception, etc.)
			e.printStackTrace();
			return false; // or throw an exception if required
		}
	}

	//method to delete existing charges
	@Override
	@Transactional
	public boolean deleteCharge(OrderValueWiseShippingChargesInput ord) {
		int id = ord.getId();
		//get the ordervaluewise corresponding object
		OrderValueWiseShippingChargesModel ordervaluecharges = entityManager
				.find(OrderValueWiseShippingChargesModel.class, id);
		if (ordervaluecharges != null) {
			entityManager.remove(ordervaluecharges);
			return true;
		}
		return false;
	}

	//method to update the existing charges
	@Override
	@Transactional
	public boolean updateCharge(OrderValueWiseShippingChargesInput ord) {
		try {
			int id = ord.getId();
			//create JPA query
			OrderValueWiseShippingChargesModel ordervaluecharges = entityManager
					.find(OrderValueWiseShippingChargesModel.class, id);
			if (ordervaluecharges != null) {
				//set the attributes to the object
				ordervaluecharges.setFrom(ord.getFrom());
				ordervaluecharges.setTo(ord.getTo());
				ordervaluecharges.setShippingAmount(ord.getShippingAmount());
				//execute query
				entityManager.merge(ordervaluecharges);
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
