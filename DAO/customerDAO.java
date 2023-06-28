package eStoreProduct.DAO;

import eStoreProduct.model.custCredModel;

public interface customerDAO {
	boolean createCustomer(custCredModel ccm);

	// custCredModel checkCustomer(String usr, String pswd);

	public void updateLastLogin(int cid);

	public void updatecustomer(custCredModel ccm);

	custCredModel getCustomerById(int custId);

	custCredModel getCustomer(String email, String psd);

	void updatePassword(String p2, String finalemail);
	
	custCredModel getCustomerByEmail(String email);
	
	public String updateShpimentDetails(custCredModel ccm);

}