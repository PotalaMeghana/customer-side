package eStoreProduct.controller.admin;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eStoreProduct.DAO.admin.adminDAO;
import eStoreProduct.model.admin.entities.adminModel;
import eStoreProduct.model.admin.input.adminLogin;

@Controller
public class adminController {
  
  adminDAO adao;

  @Autowired
  public adminController(adminDAO adminDAO) {
    adao = adminDAO;
  }

  @RequestMapping(value = "/adminLogin", method = RequestMethod.GET)
  // Method to show admin sign in
  public String getAdminLogin(Model model) {
    // Call the view
    return "adminSignIn";
  }

  @RequestMapping(value = "/adminSignOk", method = RequestMethod.POST)
  // Validating whether the admin is valid or not
  public String getHomeFinal(@Validated adminLogin al, Model model, HttpSession session) {
    // Validating the entered details
    adminModel ad = adao.getAdmin(al.getEmail(), al.getPassword());

    if (ad != null) {
      session.setAttribute("admin", ad); // Store admin object
      model.addAttribute("admin", ad);
    } else {
     //if not valid admin again go the sign in page
      return "adminSignIn";
    }
    
    return "admin";
  }
}
