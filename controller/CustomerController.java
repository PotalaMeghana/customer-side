package eStoreProduct.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eStoreProduct.BLL.FairandGStBLL;
import eStoreProduct.BLL.OrderIdCreationBLL;
import eStoreProduct.DAO.OrderDAO;
import eStoreProduct.DAO.ProductDAO;
import eStoreProduct.DAO.StockUpdaterDAO;
import eStoreProduct.DAO.cartDAO;
import eStoreProduct.DAO.customerDAO;
import eStoreProduct.DAO.walletDAO;
import eStoreProduct.externalServices.InvoiceMailSending;
import eStoreProduct.model.custCredModel;
import eStoreProduct.model.admin.entities.orderModel;
import eStoreProduct.model.wallet;
import eStoreProduct.utility.ProductStockPrice;

@Controller
public class CustomerController {
	customerDAO cdao;
	InvoiceMailSending invoiceMail;
	//cartDAO cartimp;
	FairandGStBLL BLL;
	OrderIdCreationBLL bl2;
	String buytype = null;
	ProductDAO pdaoimp;
	OrderDAO odao;
	walletDAO wdao;
	StockUpdaterDAO stckdao;
	List<ProductStockPrice> products = null;
	List<ProductStockPrice> product2 = new ArrayList<ProductStockPrice>();
	orderModel om;

	@Autowired
	public CustomerController(
			/* cartDAO cartdao, */customerDAO customerdao, StockUpdaterDAO stckdao,InvoiceMailSending invoiceMail,
			OrderIdCreationBLL bl2, FairandGStBLL bl1, ProductDAO productdao, OrderDAO odao, walletDAO w) {
		cdao = customerdao;
		this.invoiceMail=invoiceMail;
		this.bl2 = bl2;
		this.BLL = bl1;
		pdaoimp = productdao;
		this.odao = odao;
		wdao = w;
		this.stckdao = stckdao;
	}
        //called when user clicks on profile
	@RequestMapping(value = "/profilePage")
	public String sendProfilePage(Model model, HttpSession session) {
		// Retrieve customer information from the session
		custCredModel cust = (custCredModel) session.getAttribute("customer");
		
		// Add customer information to the model
		model.addAttribute("cust", cust);
		return "profile";
	}
        //called when user tries to update the profile data
	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	public String userupdate(@ModelAttribute("Customer") custCredModel cust, Model model, HttpSession session) {
		// Update the customer information in the database
		cdao.updatecustomer(cust);
		
		// Retrieve the updated customer information
		custCredModel custt = cdao.getCustomerById(cust.getCustId());
		if (custt != null) {
			// Add the updated customer information to the model
			model.addAttribute("cust", custt);
		}
		return "profile";
	}
        //for payment fair in the cart
	@GetMapping("/buycartitems")
	public String confirmbuycart(Model model, HttpSession session) {
		// Retrieve customer information from the session
		custCredModel cust1 = (custCredModel) session.getAttribute("customer");
		if (cust1 != null) {
			// Calculate the total fair for cart items
			BLL.calculateTotalfair(cust1);
			
			// Get the quantity and price information of the cart items
			products = BLL.GetQtyItems2();
			
			// Add the products and buy type information to the model
			model.addAttribute("products", products);
			buytype = "cartproducts";

			// Get the wallet amount of the customer
			wallet Wallet = wdao.getWalletAmount(cust1.getCustId());
			model.addAttribute("Wallet", Wallet);

			return "paymentpreview";
		} else {
			return "signIn";
		}
	}

	@GetMapping("/getOrderId")
	@ResponseBody
	public String getOrderId(@RequestParam(value = "amt") double amt) {
		
		double amountInPaisa = amt;
		
		// Create a Razorpay order and retrieve the order ID
		String orderId = bl2.createRazorpayOrder(amountInPaisa);
		return orderId;
	}

	
        //adding customer shipment address
	@PostMapping("/confirmShipmentAddress")
	@ResponseBody
	public String confirm(@RequestParam(value = "mobile") String mobile,
			@RequestParam(value = "custsaddress") String custsaddress,
			@RequestParam(value = "spincode") String spincode, Model model, HttpSession session) {
		custCredModel cust1 = (custCredModel) session.getAttribute("customer");
		custCredModel cust2 = new custCredModel();
		cust2.setCustMobile(mobile);
		cust2.setCustSAddress(custsaddress);
		cust2.setCustPincode(spincode);
		session.setAttribute("cust2", cust2);
		return "OK";
	}
       
	@PostMapping("/updateshipment")
	@ResponseBody
	public String handleFormSubmission(@RequestParam(value = "name") String name,
			@RequestParam(value = "custSAddress") String caddress, @RequestParam(value = "custSpincode") String pincode,
			HttpSession session) {
		custCredModel cust = (custCredModel) session.getAttribute("customer");
		//checks whether the pincode is valid or not
		boolean isValid = pdaoimp.isPincodeValid(Integer.parseInt(pincode));
                // if valid case
		if (isValid) {
			cust.setCustName(name);
			cust.setCustSAddress(caddress);
			cust.setCustSpincode(pincode);
			//shipment details get updated
			String update_status = cdao.updateShpimentDetails(cust);
			//if the shipment details updated
			if (update_status.equals("Updated"))
				return "Valid";
			else
				return "Not Valid";
		}
		return "Not Valid";
	}
        //method for showing invoice 
	@PostMapping(value = "/invoice")
	public String invoice(@RequestParam("paymentReference") String id, @RequestParam("total") String total, Model model,
			HttpSession session, @Validated orderModel om,HttpServletRequest request,HttpServletResponse response) throws Exception {
		custCredModel cust1 = (custCredModel) session.getAttribute("customer");
		//gets wallet amount of user
		wallet Wallet = wdao.getWalletAmount(cust1.getCustId());
		double payamount = Double.parseDouble(total);
		//total payable amount
		double totalamount = ProductStockPrice.getTotal();
		//amount used from wallet
		double walletusedamount = totalamount - payamount;
		if (walletusedamount > 0) {
			double x = Wallet.getAmount() - walletusedamount;
			//updating remaining wallet amount
			wdao.updatewallet(x, cust1.getCustId());
		}

		if (buytype.equals("cartproducts")) {
			products = BLL.GetQtyItems2();
		} else {
			products = product2;
		}
		//getting orders gst
		om.setGst(BLL.getOrderGST(products));
		model.addAttribute("payment_id", om.getPaymentReference());
		odao.insertIntoOrders(om, products);
		for (ProductStockPrice p : products) {
			
			stckdao.updateStocks(p.getProd_id(), p.getQuantity());
		}
		//adding requried attributes to the model
		
		System.out.println(cust1+"                       customer in invoice");
		invoiceMail.sendEmail(request, response, om, cust1.getCustEmail());
		model.addAttribute("invoicecustomer", cust1);
		model.addAttribute("payid", id);
		session.setAttribute("products", products);
		return "invoice";
	}
         //buying an individual product directly without cart
	@GetMapping("/buythisproduct")
	public String buythisproduct(@RequestParam(value = "productId", required = true) int productId,
			@RequestParam(value = "qty", required = true) int qty, Model model, HttpSession session)
			throws NumberFormatException, SQLException {
		product2.clear();
		custCredModel cust1 = (custCredModel) session.getAttribute("customer");
		//calculating the fare
		ProductStockPrice product = BLL.individualTotalfair(cust1, productId, qty);
		product2.add(product);

		buytype = "individual";
		//adding wallet amount
		wallet Wallet = wdao.getWalletAmount(cust1.getCustId());
		model.addAttribute("Wallet", Wallet);

		model.addAttribute("products", product2);

		return "paymentpreview";
	}
        //verify whether person logged in or not before proceding to buy
	@GetMapping("/checkloginornot")
	@ResponseBody
	public String buyproduct(Model model, HttpSession session) throws NumberFormatException, SQLException {
		custCredModel cust1 = (custCredModel) session.getAttribute("customer");
		if (cust1 != null) {
			return "true";
		} else {
			return "false";
		}
	}
}
