package eStoreProduct.controller;

import java.util.*;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eStoreProduct.DAO.OrderDAOView;
import eStoreProduct.DAO.ProdStockDAO;
import eStoreProduct.model.OrdersViewModel;
import eStoreProduct.model.Product;
import eStoreProduct.model.custCredModel;
import eStoreProduct.utility.ProductStockPrice;

@Controller
public class customerOrderController {
  
  @Autowired
  private OrderDAOView orderdaov;
  private ProdStockDAO productstockdao;
  public customerOrderController(OrderDAOView odaov,ProdStockDAO productdao)
  {
	  orderdaov=odaov;
	  productstockdao=productdao;
  }
  
  @RequestMapping("/CustomerOrdersProfile")
  // Method to show ordered products of the user
  public String showOrders(Model model, HttpSession session) {
    custCredModel cust = (custCredModel) session.getAttribute("customer");
    // Getting ordered products from the DAO
    List<OrdersViewModel> orderProducts = orderdaov.getorderProds(cust.getCustId());
    
    model.addAttribute("orderProducts", orderProducts);
    return "orders";
  }
  
  // Getting the details of the specific product when clicked on it
  @GetMapping("/productDetails")
  public String getProductDetails(@RequestParam("id") int productId, @RequestParam("orderId") int orderid,Model model, HttpSession session) {
    custCredModel cust = (custCredModel) session.getAttribute("customer");
    OrdersViewModel product = orderdaov.OrdProductById(cust.getCustId(), productId,orderid);
    model.addAttribute("product", product);
    return "OrdProDetails";
  }

  // Cancelling the order
  @PostMapping("/cancelOrder")
  @ResponseBody
  public String cancelOrder(@RequestParam("orderproId") Integer productId, @RequestParam("orderId") int orderId) {
    // Cancelling order in the orderproducts table and updating the status
    orderdaov.cancelorderbyId(productId, orderId);
    
    // Checking whether all the products in an order are cancelled or not
    boolean allProductsCancelled = orderdaov.areAllProductsCancelled(orderId);
    
    if (allProductsCancelled) {
      // Update the shipment status of the order in slam_Orders table
      orderdaov.updateOrderShipmentStatus(orderId, "cancelled");
      
    }
    productstockdao.updateStock(productId, orderId);
    return "Order with ID " + productId + orderId + " has been cancelled.";
  }
  
  @RequestMapping(value = "/trackOrder", method = RequestMethod.GET)
  @ResponseBody
  // Method to track the order
  public String trackOrder(@RequestParam("orderproId") int productId, @RequestParam("orderId") int orderId) {
    // Retrieve the shipment status for the given order ID
    String shipmentStatus = orderdaov.getShipmentStatus(productId, orderId);
    return shipmentStatus;
  }
  
  @RequestMapping(value = "/sortorders", method = RequestMethod.POST)
  public String sortProducts(@RequestParam("sortOrder") String sortOrder, Model model, HttpSession session) {
    // Sort the products based on the selected sorting option
    custCredModel cust = (custCredModel) session.getAttribute("customer");
    List<OrdersViewModel> ordersList = orderdaov.getorderProds(cust.getCustId());
    
    if (sortOrder.equals("lowToHigh") || sortOrder.equals("highToLow")) {
      ordersList = orderdaov.sortProductsByPrice(ordersList, sortOrder);
      model.addAttribute("orderProducts", ordersList);
    }
    
    return "orders";}}
