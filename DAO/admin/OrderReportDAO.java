package eStoreProduct.DAO.admin;
import java.util.List;

import eStoreProduct.model.admin.entities.SlamOrderModel;

public interface OrderReportDAO {
    List<SlamOrderModel> getAllOrders();
}
