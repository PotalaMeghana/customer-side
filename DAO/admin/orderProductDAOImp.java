package eStoreProduct.DAO.admin;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

import eStoreProduct.model.admin.entities.orderModel;
import eStoreProduct.model.admin.entities.orderProductModel;

import eStoreProduct.model.admin.input.orderProductInput;
import eStoreProduct.model.admin.output.orderProductsModel;
import eStoreProduct.model.admin.output.stockSummaryModel;

@Component
public class orderProductDAOImp implements orderProductDAO {
	
	@PersistenceContext
	private EntityManager entityManager;

	//method to get all the products of an order
	@Override
	@Transactional
	public List<orderProductsModel> getOrderWiseOrderProducts(int orderid) {
		
		String query = "SELECT NEW eStoreProduct.model.admin.output.orderProductsModel("
				+ "op.orderId,op.productId,op.quantity,op.gst,op.price,op.shipmentStatus) from orderProductModel op where "
				+ "op.orderId=:orderid";
		List<orderProductsModel> l=null;
		try {
			//crate JPA query and execute
		l = entityManager.createQuery(
				query,
				orderProductsModel.class).setParameter("orderid", orderid).getResultList();
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList(); 
		}
		
	
	}

	//method to update the status of the product
	@Override
	@Transactional
	public int updateOrderProductStatus(orderProductInput op) {
		String status=op.getShipment_status();
		int oid=op.getOrdr_id();
		int pid=op.getProd_id();
		int x = entityManager.createQuery("UPDATE orderProductModel op SET op.shipmentStatus = :status WHERE op.orderId = :oid AND op.productId = :pid")
                .setParameter("status", status)
                .setParameter("oid", oid)
                .setParameter("pid", pid)
                .executeUpdate();
       
        return x;
	}

}
