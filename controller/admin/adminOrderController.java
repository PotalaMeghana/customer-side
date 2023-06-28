package eStoreProduct.controller.admin;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import eStoreProduct.DAO.OrderDAO;
import eStoreProduct.model.admin.entities.orderModel;

@Controller
public class adminOrderController {

	private OrderDAO odao;
	@Autowired
	public adminOrderController(OrderDAO odao) {
		this.odao = odao;
	}

	//Display the Orders of customers to view and process the orders
	@GetMapping("/listOrders")
	public String showOrders(Model model) {
		List<orderModel> orders = odao.getAllOrders();
		model.addAttribute("orders", orders);
		// call the view
		return "orderList";
	}

	//Process the orders 
	@GetMapping("/processOrders")
	public String processOrders(@RequestParam(value = "orderId") long orderId,
			@RequestParam(value = "adminId") int adminId, Model model) {
		System.out.println("procvessing");
		System.out.println(orderId + "" + adminId);
		odao.updateOrderProcessedBy(orderId, adminId);
		System.out.println("processed");
		List<orderModel> orders = odao.getAllOrders();
		model.addAttribute("orders", orders);
		return "filteredOrderList";
	}

	// Filter: Load Orders By Date
	@GetMapping("/loadOrdersByDate")
	public String loadOrdersByDateFilter(@RequestParam(value = "selectDateFilter") String selectDateFilter, Model model) {
		LocalDateTime currentDate = LocalDateTime.now();
		LocalDateTime startDate = null;
		LocalDateTime endDate = null;

		if (selectDateFilter.equals("daily")) {
			// Set the start and end date for daily filter
			startDate = currentDate.withHour(0).withMinute(0).withSecond(0);
			endDate = currentDate.withHour(23).withMinute(59).withSecond(59);
		} else if (selectDateFilter.equals("weekly")) {
			// Set the start and end date for weekly filter (assuming a week starts on Monday)
			startDate = currentDate.withHour(0).withMinute(0).withSecond(0)
					.minusDays(currentDate.getDayOfWeek().getValue() - 1);
			endDate = startDate.plusDays(6).withHour(23).withMinute(59).withSecond(59);
		} else if (selectDateFilter.equals("monthly")) {
			// Set the start and end date for monthly filter
			startDate = currentDate.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
			endDate = startDate.plusMonths(1).minusDays(1).withHour(23).withMinute(59).withSecond(59);
		} else {
			// No filter selected, load all orders
			List<orderModel> orders = odao.getAllOrders();
			model.addAttribute("orders", orders);
			return "orderList";
		}

		List<orderModel> orders = odao.loadOrdersByDate(Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));
		model.addAttribute("orders", orders);
		return "filteredOrderList";
	}

}
