package eStoreProduct.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eStoreProduct.model.Product;

import eStoreProduct.DAO.admin.CategoryDAO;
import eStoreProduct.DAO.ProductDAO;
import eStoreProduct.DAO.admin.stockSummaryDAO;
import eStoreProduct.model.admin.input.Category;
import eStoreProduct.model.admin.output.stockSummaryModel;

@Controller
public class adminMasterEntryController {

	private stockSummaryDAO ssd;

	private final ProductDAO pdaoimp;
	private final CategoryDAO cdaoimp;

	@Autowired
	adminMasterEntryController(stockSummaryDAO stockSummDao, ProductDAO productdao, CategoryDAO categorydao) {
		ssd = stockSummDao;
		pdaoimp = productdao;
		cdaoimp = categorydao;
	}

	// Display the Product Stocks to the Admin. So the admin can view and edit the stock of the products.
	@RequestMapping(value = "/showProductStocks", method = RequestMethod.GET)
	public String showProductStocks(Model model) {
		System.out.println("enter masterEntry controller");
		List<stockSummaryModel> stocks1 = (List<stockSummaryModel>) ssd.getStocks();
		model.addAttribute("stocks1", stocks1);
		// call the view
		return "editableStocks";
	}

	//Update the Stock of the Products 
	@GetMapping("/updateStocks")
	public String UpdateStocks(@Validated stockSummaryModel ssm, Model model) {
		System.out.println("enter updated masterEntry controller");
		ssd.updateStocks(ssm.getId(), ssm.getImageUrl(), ssm.getHsnCode(), ssm.getReorderLevel(), ssm.getStock(),
				ssm.getMrp());
		List<stockSummaryModel> stocks1 = (List<stockSummaryModel>) ssd.getStocks();
		System.out.println("enter updated masterEntry controller23");
		model.addAttribute("stocks1", stocks1);
		// call the view
		return "editableStocks";
	}

	//Redirect to the Add new Product page
	@GetMapping("/addNewProductInTheMasterEntry")
	public String addNewProductInMasterEntryPage(Model model) {
		System.out.println("enter addNewProductController ");
		// call the view
		return "addNewProduct";
	}

	//Add new Product in the Store 
	@RequestMapping(value = "/createNewProduct", method = RequestMethod.POST)
	public String createNewProduct(@Validated Product prod, Model model) {
		System.out.print("craeting newww product\n");
		pdaoimp.createProduct(prod);
		System.out.print("created\n");
		// call the AddedProduct view to display the notification of product Added
		return "AddedProduct";

	}

	//Redirect to Add new Category page
	@GetMapping("/addNewCategorytInTheMasterEntry")
	public String addNewCategorytInMasterEntryPage(Model model) {
		System.out.println("enter addNewCategoryController controller");
		// call the view
		return "addNewCategoryForm";
	}

	//Add new Product Category 
	@RequestMapping(value = "/createNewCategory", method = RequestMethod.POST)
	public String createNewCategory(@Validated Category catg, Model model) {
		cdaoimp.addNewCategory(catg);
		// call the AddedCategory view to display the notification of Category Added
		return "AddedCategory";

	}

}
