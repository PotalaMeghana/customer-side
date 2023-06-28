
package eStoreProduct.DAO.admin;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import eStoreProduct.model.admin.entities.EmailConfigModel;

@Component
public class EmailConfigDAOImpl implements EmailConfigDAO {
	@PersistenceContext
	private EntityManager entityManager;

	private final String SELECT_EMAIL = "SELECT e FROM EmailConfigModel e WHERE e.id = 1";
	private final String UPDATE_EMAIL = "UPDATE EmailConfigModel e SET e.email = :email, e.pwd = :pwd WHERE e.id = 1";

	//method to change the email and password of the admin
	@Override
	@Transactional
	public void changeEmail(EmailConfigModel ecm) {
		entityManager.createQuery(UPDATE_EMAIL).setParameter("email", ecm.getEmail()).setParameter("pwd", ecm.getPwd())
				.executeUpdate();
	}

	//method to get the email to send the mails to the user
	@Override
	@Transactional
	public EmailConfigModel getEmail() {
		return entityManager.createQuery(SELECT_EMAIL, EmailConfigModel.class).getSingleResult();
	}
}
