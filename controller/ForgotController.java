package eStoreProduct.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eStoreProduct.DAO.customerDAO;
import eStoreProduct.model.custCredModel;
import eStoreProduct.model.emailSend;

@Controller
public class ForgotController {

	String generateotp;
	String finalemail;
	customerDAO cdao;
	custCredModel cust;
	java.time.LocalDateTime t1;
	//dependency injection
	@Autowired
	public ForgotController(customerDAO customerdao) {
		cdao = customerdao;
	}
	//url mapping to open forgot password page
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
	public String forgotPassword(Model model) {
		// call the view
		return "forgotPage";
	}
	// url mapping to check the email is present in database or not
	@PostMapping("/emailValid")
	@ResponseBody
	public String verifyEmail(@RequestParam("email") String email) {
		 finalemail = email;
		//call method to get customer with given email
		 cust = cdao.getCustomerByEmail(email);
		//return response to the ajax call
		if(cust==null)
			return "no";
		return "yes"; 
	}
	//url mapping to send otp to the user mail
	@PostMapping("/otpAction")
	@ResponseBody
	public String sendOTP(@RequestParam("email") String email) {
		 finalemail = email;
		//send mail by generating the otp
		 generateotp = (new emailSend()).sendEmail(email);
		//saving the time of otp generated
		 t1 = java.time.LocalDateTime.now();
		// Return the generated OTP as the response
		return generateotp; 
	}
	//url mapping to validate the otp given by the customer
	@PostMapping("/validateOTP")
	@ResponseBody
	public String validateOTP(@RequestParam("otp12") String otp) {
	  if (otp.equals(generateotp)) {
		  //getting the time of otp submitted
	        java.time.LocalDateTime t2 = java.time.LocalDateTime.now();
		  //find the difference and validate the otp
	        java.time.Duration duration = java.time.Duration.between(t1, t2);
	       long seconds = duration.getSeconds();
	        if (seconds <= 30) {
			//OTP is valid and submitted within time
	            return "valid";
	        } else {
			//OTP is valid but not submitted in time
	            return "no";
	        }
	    } else {
	        // OTP is invalid
	        return "invalid";
	    }
	}
        //url mapping for saving new user password
	@RequestMapping(value = "/updatepwd")
	public String updateUserNewPassword(@RequestParam("psd2") String p2, Model model) {
		//updating user password in database
		cdao.updatePassword(p2, finalemail);

		return "signIn";
	}
}
