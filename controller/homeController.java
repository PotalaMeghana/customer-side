package eStoreProduct.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eStoreProduct.DAO.cartDAO;
import eStoreProduct.DAO.customerDAO;
import eStoreProduct.model.custCredModel;
import eStoreProduct.utility.ProductStockPrice;

@Controller
public class homeController {
	static boolean flag = false;
	customerDAO cdao;
	cartDAO cd;

	//dependency injection
	@Autowired
	public homeController(customerDAO customerdao, cartDAO cd) {
		cdao = customerdao;
		this.cd = cd;

	}
        //url mapping for home page
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getHomePage(Model model) {
		// call the view
		return "home";
	}
        //url mapping for logged in user to get back to home
	@RequestMapping(value = "/loggedIn", method = RequestMethod.GET)
	public String getHomeFoeLoggedUser(Model model) {
		//setting flag variable to maintain status and add to model
		flag = true;
		model.addAttribute("fl", flag);
		// call the view
		return "home";
	}
        //url mapping to open signup page for new customer
	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public String getSignUpPage(Model model) {
		// call the view
		return "signUp";
	}
        //url mapping to open signin page  
	@RequestMapping(value = "/signIn", method = RequestMethod.GET)
	public String getSignInPage(Model model) {
		// call the view
		return "signIn";
	}
        //url mapping when customer completed the signup form
	@RequestMapping(value = "/signInCreateAccount", method = RequestMethod.POST)
	public String createAccount(@Validated custCredModel ccm, Model model) {
		//add customer to database
		boolean b = cdao.createCustomer(ccm);
		// set it to the model
		if (b) {
			model.addAttribute("customer", ccm);
		}
		// call the view
		return "createdMsg";
	}
        //url mapping to redirect to home page by changing login status
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String userlogout(Model model, HttpSession session) {
		//get the logged in session customer
		custCredModel cust = (custCredModel) session.getAttribute("customer");
		//change the login status and add to model
		flag = false;
		model.addAttribute("fl", flag);
		if (model.containsAttribute("customer"))
			model.addAttribute("customer", null);
		session.invalidate();
		//call view
		return "home";
	}

}
