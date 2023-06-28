package eStoreProduct.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

import eStoreProduct.BLL.FairandGStBLL;
import eStoreProduct.DAO.ProductDAO;
import eStoreProduct.DAO.ServicableRegionDAO;
import eStoreProduct.DAO.cartDAO;
import eStoreProduct.DAO.customerDAO;
import eStoreProduct.model.cartModel;
import eStoreProduct.model.custCredModel;
import eStoreProduct.utility.ProductStockPrice;

@Controller
public class CartController {
	ServicableRegionDAO sdao;
	static boolean flag = false;
	cartDAO cartimp;
	private final ProductDAO pdaoimp;
	List<ProductStockPrice> alist = new ArrayList<>();
	customerDAO cdao;
	
	FairandGStBLL  BLL;
	@Autowired
	public CartController(cartDAO cartdao, ProductDAO productdao, customerDAO cdao, FairandGStBLL b, ServicableRegionDAO sdao) {
		cartimp = cartdao;
		pdaoimp = productdao;
		this.cdao = cdao;
		this.sdao = sdao;
		BLL = b;
	}
//method to add the product to cart and send response back
	@GetMapping("/addToCart")
	@ResponseBody
	public String addToCart(@RequestParam(value = "productId", required = true) int productId, Model model,
			HttpSession session) throws NumberFormatException, SQLException {
		custCredModel cust1 = (custCredModel) session.getAttribute("customer");
		if (cust1 != null) {
			return cartimp.addToCart(productId, cust1.getCustId());
		} else {
			ProductStockPrice product = pdaoimp.getProductById(productId);
			for (ProductStockPrice p : alist) {
				if (p.getProd_id() == product.getProd_id()) {
					return "Already added to cart";
				}
			}
			product.setQuantity(1);
			alist.add(product);
			model.addAttribute("alist", alist);
			return "Added to cart";

		}
	}

//display the cart items controller method 
	@RequestMapping(value = "/cartDisplay", method = RequestMethod.GET)
	public String getSignUpPage(Model model, HttpSession session) {
		double cartt = 0;
		// ProductDAO pdao = new ProductDAO();
		custCredModel cust = (custCredModel) session.getAttribute("customer");
		if (cust != null) {
			List<ProductStockPrice> products = cartimp.getCartProds(cust.getCustId());
			model.addAttribute("products", products);
			double cartcost = BLL.getCartCost(cust.getCustId());
			model.addAttribute("cartcost", cartcost);
			model.addAttribute("cust", cust);

			// Forward to the cart.jsp view
			return "cart";
		} else {
			// Set the products attribute in the model
			double cartcost = BLL.getCartCost(alist);
			model.addAttribute("cartcost", cartcost);
			model.addAttribute("alist", alist);
			return "cart";

		}
	}
 //when signok cart items stored into customer cart method
	@RequestMapping(value = "/signOk", method = RequestMethod.GET)
	public String getHomeFinal(@RequestParam("em") String email, @RequestParam("ps") String psd, Model model,
			HttpSession session) {
		// Retrieve the products ArrayList from the model
		custCredModel cust = cdao.getCustomer(email, psd);
		if (cust != null) {
			flag = true;

			try {
				cdao.updateLastLogin(cust.getCustId());
				session.setAttribute("customer", cust);
				model.addAttribute("fl", flag);

			} catch (Exception e) {
				System.out.println(e);
			}
			if (alist != null) {
				for (ProductStockPrice psp : alist) {
					cartimp.addToCart(psp.getProd_id(), cust.getCustId());
					// cartimp.updateinsert(alist, cust.getCustId());
				}
				List<ProductStockPrice> products = cartimp.getCartProds(cust.getCustId());
				model.addAttribute("products", products);
				return "home";
			}
		}

		return "home";
	}
//remove product from cart and send response back to jsp
	@GetMapping("/removeFromCart")
	@ResponseBody
	public String removeFromCart(@RequestParam(value = "productId", required = true) int productId, Model model,
			HttpSession session) throws NumberFormatException, SQLException {
		custCredModel cust1 = (custCredModel) session.getAttribute("customer");
		if (cust1 != null) {
			System.out.println("remove from cart login");
			cartimp.removeFromCart(productId, cust1.getCustId());
			return "Removed from cart";
		} else {

			for (ProductStockPrice p : alist) {
				if (p.getProd_id() == productId)

					alist.remove(p);
			}

			return "Removed from cart";
		}

	}
//update the quantity of product in cart  and return updated cost back via ajax
	@PostMapping("/updateQuantity")
	@ResponseBody
	public String updateQuantity(@RequestParam(value = "productId", required = true) int productId,
			@RequestParam(value = "quantity", required = true) int quantity, Model model, HttpSession session)
			throws NumberFormatException, SQLException {
		double cartcost = 0.0;
		custCredModel cust1 = (custCredModel) session.getAttribute("customer");
		if (cust1 != null) {
			cartModel cart = new cartModel(cust1.getCustId(), productId, quantity);
			cartimp.updateQty(cart);
			List<ProductStockPrice> products = cartimp.getCartProds(cust1.getCustId());
			session.setAttribute("products", products);
			cartcost = (BLL.getCartCost(cust1.getCustId()));
			String ccost = String.valueOf(cartcost);
			return ccost;
		} else {
			for (ProductStockPrice product : alist) {
				if (product.getProd_id() == productId) {
					product.setQuantity(quantity);
				}
			}
			cartcost = (BLL.getCartCost(alist));
			return String.valueOf(cartcost);
		}
	}
//update the cost of cart products 
	@PostMapping("/updateCostOnLoad")
	@ResponseBody
	public String updateCostOnLoad(Model model, HttpSession session) throws NumberFormatException, SQLException {
		double cartcost = 0.0;
		custCredModel cust1 = (custCredModel) session.getAttribute("customer");
		if (cust1 != null) {
			List<ProductStockPrice> products = cartimp.getCartProds(cust1.getCustId());
			session.setAttribute("products", products);
			cartcost = (BLL.getCartCost(cust1.getCustId()));
			String ccost = String.valueOf(cartcost);
			return ccost;
		} else {
			cartcost = (BLL.getCartCost(alist));
			return String.valueOf(cartcost);
		}
	}
//check the pincode availability of products
	@PostMapping("/checkPincodeValidity")
	@ResponseBody
	public String checkPincodeValidity(@RequestParam(value = "pincode", required = true) String pincode, Model model,
			HttpSession session) throws NumberFormatException, SQLException {
		return sdao.getValidityOfPincode(Integer.parseInt(pincode)) + "";

	}

}
