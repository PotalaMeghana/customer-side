package eStoreProduct.ProductsService;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@Component
public class OrderCreation {

	private static final long serialVersionUID = 1L;
	@Autowired
	private RazorpayService razorpayService;

	private void paymentMethod(HttpServletRequest request, HttpServletResponse response) {
		{
			try {
				RazorpayClient razorpayClient = new RazorpayClient("rzp_test_Xe8wdVU6XUBoBd",
						"Y0kKhdQLoVJQALAJ4iHNN0Sq");
				JSONObject obj = new JSONObject();
				obj.put("amount", Integer.parseInt((String) request.getParameter("amount")) * 100); // Replace 1000 with
																									// the desired
																									// amount value
				obj.put("currency", "INR");
				obj.put("receipt", "hello123");
				obj.put("payment_capture", true);
				Order order = razorpayClient.orders.create(obj);
				String orderId = order.get("id");
				System.out.println(order.toString());
				request.setAttribute("orderId", orderId); // Pass the orderId to the JSP page
				request.setAttribute("username", request.getParameter("username"));
				request.setAttribute("email", request.getParameter("email"));
				request.setAttribute("phone", request.getParameter("phone"));
				;
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/payment-options.jsp");
				dispatcher.forward(request, response);
			} catch (Exception e) {
				System.out.println(e);
				try {
					response.getWriter().append("Error occurred while creating order");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

}