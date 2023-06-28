package eStoreProduct.controller.admin;



import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import eStoreProduct.DAO.admin.AdminViewPaymentDAO;
import eStoreProduct.DAO.admin.AdminViewPaymentDAOImp;
import eStoreProduct.model.admin.output.AdminViewPayments;

@Controller
public class adminViewPaymentsController {
    private final AdminViewPaymentDAO adminPayment;
     //dependency injection
    @Autowired
    public adminViewPaymentsController(AdminViewPaymentDAO adminPayment) {
        this.adminPayment = adminPayment;
    }

    // Getting all payments done
    @GetMapping("/viewPayments")
    public String showPayments(Model model) {
        //getting all payments from database
        List<AdminViewPayments> payments = adminPayment.getPayments();
        model.addAttribute("payments", payments);
	    //call view
        return "viewPayments";
    }

    // Getting payments between selected dates
    @GetMapping("/getBetweenDatesPayments")
    public String showBetweenPayments(@RequestParam("startDate") Date startDate,
                                      @RequestParam("endDate") Date endDate,
                                      Model model) {
	    //time conversions to match database types
        Timestamp start = convertToTimestampWithoutTime(startDate);
        Timestamp end = convertToTimestampWithoutTime(endDate);
	    //getting payments between dates selected
        List<AdminViewPayments> payments = adminPayment.getPaymentsBetweenDates(start, end);
        model.addAttribute("payments", payments);
	    //call view
        return "viewPayments";
    }
	//method that converts the Date type to Timestamp 
    private Timestamp convertToTimestampWithoutTime(Date date) {
        LocalDate localDate = date.toLocalDate();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.MIN);
        return Timestamp.valueOf(localDateTime);
    }
	//getting the payments between price ranges
    @PostMapping("/filterPayments")
    public String showPriceRangePayments(@RequestParam("priceRange") String priceRange,Model model) {
        System.out.println("Enter payment controller");
        List<AdminViewPayments> payments = adminPayment.getPayments();    
        double minPrice;
		double maxPrice = 0;
		// Parse the selected price range
		if (priceRange.equals("0-10000")) {
			minPrice = 0.0;
			maxPrice = 10000;
		} else if (priceRange.equals("10000-20000")) {
			minPrice = 10000;
			maxPrice = 20000;
		} else if (priceRange.equals("20000-30000")) {
			minPrice = 20000;
			maxPrice = 30000;
		} else {
			minPrice=30000;
			payments=adminPayment.getMaxPricePayment(minPrice);
			model.addAttribute("payments", payments);
			return "viewPayments";
		}
		// Call the filterProductsByPriceRange() method from the DAO
		payments=adminPayment.getPaymentsInThePriceRange(minPrice,maxPrice);
		model.addAttribute("payments", payments);
        //call view
        return "viewPayments";
    }
}
