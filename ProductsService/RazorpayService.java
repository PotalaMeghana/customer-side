package eStoreProduct.ProductsService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Component
public class RazorpayService {

	@Value("${razorpay.api.key}")
	private String razorpayApiKey;

	@Value("${razorpay.api.secret}")
	private String razorpayApiSecret;

	public String getRazorpayApiKey() {
		return razorpayApiKey;
	}

	public void setRazorpayApiKey(String razorpayApiKey) {
		this.razorpayApiKey = razorpayApiKey;
	}

	public String getRazorpayApiSecret() {
		return razorpayApiSecret;
	}

	public void setRazorpayApiSecret(String razorpayApiSecret) {
		this.razorpayApiSecret = razorpayApiSecret;
	}

	public String createOrder(int amountInPaise, String currency) throws RazorpayException {
		RazorpayClient razorpayClient = new RazorpayClient(razorpayApiKey, razorpayApiSecret);
		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", amountInPaise);
		orderRequest.put("currency", currency);
		// Add additional order details if required
		System.out.println("Razonpay-service");
		Order order = razorpayClient.orders.create(orderRequest);
		return order.get("id").toString();
	}
}
