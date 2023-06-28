package eStoreProduct.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eStoreProduct.DAO.admin.EmailConfigDAO;
import eStoreProduct.DAO.admin.OrderValueWiseShippingChargeDAO;
import eStoreProduct.DAO.admin.RegionDAO;
import eStoreProduct.DAO.admin.adminDAO;
// import eStoreProduct.model.ProductShip;
import eStoreProduct.model.admin.entities.EmailConfigModel;
import eStoreProduct.model.admin.input.OrderValueWiseShippingChargesInput;
import eStoreProduct.model.admin.input.Regions;
import eStoreProduct.model.admin.output.OrderValueWiseShippingCharge;
import eStoreProduct.model.admin.output.RegionsOutput;

@Controller
public class adminSettingsController {
	adminDAO adao;
	EmailConfigDAO edao;
	RegionDAO redao;
	OrderValueWiseShippingChargeDAO owsc;

	@Autowired
	public adminSettingsController(adminDAO admindao, EmailConfigDAO edao, RegionDAO redao,
			OrderValueWiseShippingChargeDAO owsc) {
		adao = admindao;
		this.edao = edao;
		this.redao = redao;
		this.owsc = owsc;

	}

	@RequestMapping(value = "/addRegion", method = RequestMethod.POST)
	@ResponseBody
	public String addRegion(@Validated Regions reg, Model model) {
		System.out.println("Admin Page");
		//add region
		if(redao.addRegion(reg))
			return "error occured.try again";
		else
			return "DONE";
		
	}

	@RequestMapping(value = "/remRegion", method = RequestMethod.POST)
	@ResponseBody
	public String removeRegion(@RequestParam("regionId") String id, Model model) {
		//parse regionId in String format to Integer
		int Id = Integer.parseInt(id);
		if(redao.removeRegion(Id))
			return "error occured.try again";
		else
			return "DONE";
		
	}

	@RequestMapping(value = "/ShippingRedirect", method = RequestMethod.GET)
	public String shippingRedirect(Model model) {
		//get list of all regions 
		List<RegionsOutput> regionList = redao.getRegions();
		model.addAttribute("regionList", regionList);
		System.out.println("shippingRedirect2");

		// call the view
		return "regions";
	}

	@RequestMapping(value = "/EmailConfiguration", method = RequestMethod.POST)
	@ResponseBody
	public String emailConfiguration(@Validated EmailConfigModel ecm, Model model) {
		System.out.println("emailConfiguration");

		edao.changeEmail(ecm);
		return "done";
	}

	@RequestMapping(value = "/EmailConfigurationPage", method = RequestMethod.GET)
	public String returnpage(Model model) {
		System.out.println("emailConfigurationpage");
		// EmailConfigDAO edao=new EmailConfigDAOImpl();
		// call the view
		return "emailConfig";
	}

	@RequestMapping(value = "/OrderValueWisePage", method = RequestMethod.GET)
	public String OrderValueWisePage(Model model) {
		System.out.println("OrderValueWisePage");
		List<OrderValueWiseShippingCharge> ocl = owsc.getAll();
		System.out.println("OrderValueWisePage");
		model.addAttribute("chargeList", ocl);
		System.out.println("OrderValueWisePage");

		// call the view
		return "OrderValueWisePage";
	}

	@RequestMapping(value = "/updateCharge", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateCharge(@Validated OrderValueWiseShippingChargesInput osw, Model model) {

		// call the view
		return owsc.updateCharge(osw);
	}

	@RequestMapping(value = "/addCharge", method = RequestMethod.POST)
	@ResponseBody
	public boolean addCharge(@Validated OrderValueWiseShippingChargesInput osw, Model model) {

		// call the view
		return owsc.addCharge(osw);
	}

}
