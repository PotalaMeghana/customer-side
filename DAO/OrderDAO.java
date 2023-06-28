package eStoreProduct.DAO;

import java.sql.Timestamp;
import java.util.List;

import javax.activation.DataSource;

import eStoreProduct.model.Order;
import eStoreProduct.model.admin.entities.orderModel;
import eStoreProduct.utility.ProductStockPrice;

public interface OrderDAO {
	//void insertOrder(orderModel order);

	List<orderModel> getAllOrders();

	List<orderModel> loadOrdersByDate(Timestamp startDate, Timestamp endDate);

	void updateOrderProcessedBy(Long orderId, Integer processedBy);

	void updateOrderShipmentStatus(int orderId, String status);

	void insertIntoOrders(orderModel or, List<ProductStockPrice> al);

}