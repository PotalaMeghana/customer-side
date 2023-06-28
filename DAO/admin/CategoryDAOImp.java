package eStoreProduct.DAO.admin;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import eStoreProduct.model.admin.entities.productCategoryModel;
import eStoreProduct.model.admin.input.Category;

@Component
public class CategoryDAOImp implements CategoryDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	//method to get the last category id
	@Transactional
	public Integer getMaxCategoryId() {
		//query to get max category id
		String query = "SELECT MAX(c.id) FROM productCategoryModel c";
		//create a JPA query
		TypedQuery<Integer> maxIdQuery = entityManager.createQuery(query, Integer.class);
		//execute query
		Integer maxId = maxIdQuery.getSingleResult();
		return maxId != null ? maxId : 0;
	}

	//method to add a new category to the existing stock
	@Override
	@Transactional
	public boolean addNewCategory(Category catg) {
		//get the last category id
		int c_id = getMaxCategoryId();
		//add new category id by incrementing
		c_id = c_id + 1;
		productCategoryModel categoryEntity = new productCategoryModel();
		//set the parameters of the class
		categoryEntity.setId(c_id);
		categoryEntity.setPrct_title(catg.getPrct_title());
		categoryEntity.setDescription(catg.getPrct_desc());
		//execute the query to update
		entityManager.merge(categoryEntity);

		return categoryEntity.getId() != null;

	}

	//method to load all the categories 
	public List<String> getAllCategories() {
		List<String> categories = new ArrayList<>();

		try {
			// Create a new EntityManager from the factory
			EntityManager entityManager = entityManagerFactory.createEntityManager();

			// Prepare the JPA query
			String query = "SELECT c.prct_title FROM eStoreProduct.model.admin.entities.productCategoryModel c";
			TypedQuery<String> typedQuery = entityManager.createQuery(query, String.class);

			// Execute the query
			List<String> results = typedQuery.getResultList();

			// Add the results to the categories list
			categories.addAll(results);

			// Close the EntityManager
			entityManager.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return categories;
	}

}
