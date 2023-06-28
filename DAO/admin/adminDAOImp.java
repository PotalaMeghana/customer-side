package eStoreProduct.DAO.admin;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import eStoreProduct.model.admin.entities.adminModel;

@Component
public class adminDAOImp implements adminDAO {

	@PersistenceContext
	private EntityManager entityManager;

	//method to verify the admin credentials
	@Override
	@Transactional
	public adminModel getAdmin(String email, String password) {
		try {
			// Query to retrieve admin based on email and password
			String query = "SELECT a FROM adminModel a WHERE a.email = :email AND a.password = :password";
			return entityManager.createQuery(query, adminModel.class)
					.setParameter("email", email)
					.setParameter("password", password)
					.getSingleResult();
		} catch (Exception e) {
                         e.printStackTrace();
			return null; 
		}
	}

	//update the admin details
	@Override
	@Transactional
	public void updateAdmin(adminModel admin) {
		// Update admin information
		entityManager.merge(admin);
	}
}
