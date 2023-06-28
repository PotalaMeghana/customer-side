package eStoreProduct.DAO;

import java.util.List;

import eStoreProduct.model.OrdersViewModel;

public interface OrderDAOView {
  
  // Retrieve ordered products for a given customer ID
  public List<OrdersViewModel> getorderProds(int c);

  // Retrieve the details of a specific ordered product by customer ID and product ID
  public OrdersViewModel OrdProductById(int c, Integer productId,int orderid);

  // Cancel an order in orderProducts table by product ID and order ID
  public void cancelorderbyId(Integer productId, int orderId);

  // Get the shipment status of an order by product ID and order ID
  public String getShipmentStatus(int productId, int orderId);

  // Sort the list of ordered products by price based on the given sort order
  public List<OrdersViewModel> sortProductsByPrice(List<OrdersViewModel> orderLists, String sortOrder);

  // Check if all products in an order are cancelled
  public boolean areAllProductsCancelled(int orderId);

  // Update the shipment status of an order in Orders table
  public void updateOrderShipmentStatus(int orderId, String shipmentStatus);

  // Filter the list of ordered products by price range
  public List<OrdersViewModel> filterProductsByPriceRange(List<OrdersViewModel> productList, double minPrice,
      double maxPrice);
}
