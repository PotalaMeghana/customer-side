package eStoreProduct.DAO;

import java.util.List;

import eStoreProduct.model.orderProductsModel;

public interface orderProductDAO {
	public List<orderProductsModel> getOrderWiseOrderProducts(int orderid);
	public int updateOrderProductStatus(int oid,int pid,String status);
	
}