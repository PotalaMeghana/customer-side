package eStoreProduct.DAO.admin;

import eStoreProduct.model.admin.entities.adminModel;

public interface adminDAO {
  
  // Retrieve admin information based on email and password
  public adminModel getAdmin(String email, String psd);

  // Update admin information
  public void updateAdmin(adminModel am);
}
