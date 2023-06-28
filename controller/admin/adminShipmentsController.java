package eStoreProduct.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import eStoreProduct.DAO.OrderDAO;
import eStoreProduct.DAO.admin.orderProductDAO;
import eStoreProduct.model.admin.entities.orderModel;
import eStoreProduct.model.admin.input.orderProductInput;
import eStoreProduct.model.admin.output.orderProductsModel;

@Controller
public class adminShipmentsController {

	private OrderDAO odao;
	private orderProductDAO opd;

	@Autowired
	adminShipmentsController(OrderDAO odao, orderProductDAO orderproductdao) {
		this.odao = odao;
		opd = orderproductdao;
	}

	//Display the Proccessed Orders to process the shipment status 
	@GetMapping("/displayProcessedOrdersInShipments")
	public String showProcessedOrders(Model model) {
		List<orderModel> orders = odao.getAllOrders();
		model.addAttribute("orders", orders);
		return "shipmentProgressPage";
	}

	//Display the orders and order wise products to process the shipment status of each product
	@GetMapping("/displayProcessedOrderProductsToUpdateStatus")
	public String showProcessedOrderProductsToUpdateStatus(@RequestParam(value = "orderId") int o_id, Model model) {
		System.out.println("show OrderProducts");
		List<orderProductsModel> orderproducts = opd.getOrderWiseOrderProducts(o_id);
		model.addAttribute("orderproducts", orderproducts);
		//call the view
		return "orderProductsListPage";
	}

	//Update the Order Product Shipment status 
	@PostMapping("/updateOrderProductStatus")
	public String updateOrderProductStatus(@Validated orderProductInput opm1, Model model) {
		System.out.println(
				"show update OrderProducts orderId" + opm1.getOrdr_id() + " product id:" + opm1.getProd_id() + "\n");
		
		opd.updateOrderProductStatus(opm1);
		System.out.print("updated Op Status");
		List<orderProductsModel> orderproducts = opd.getOrderWiseOrderProducts(opm1.getOrdr_id());
		model.addAttribute("orderproducts", orderproducts);
		//call the view
		return "orderProductsListPage";
	}

	//Display the shipped orders
	@GetMapping("/displayShippedOrders")
	public String showShippedOrders(Model model) {
		//get the orders and send it to the view to apply the logic to get the shipped orders
		List<orderModel> orders = odao.getAllOrders();
		model.addAttribute("orders", orders);
		//call the view
		return "shipmentShippedPage";
	}
}
