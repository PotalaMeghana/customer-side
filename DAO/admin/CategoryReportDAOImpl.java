package eStoreProduct.DAO.admin;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import eStoreProduct.model.admin.output.CategoryReportViewModel;

@Component
public class CategoryReportDAOImpl implements CategoryReportDAO {
	@PersistenceContext
	private EntityManager entityManager;

	//method to get report of the categories
	@Transactional
	@Override
	public List<CategoryReportViewModel> getCategoryReport() {

		String hql = "select  new eStoreProduct.model.admin.output.CategoryReportViewModel(spc.id,spc.prct_title,count(*),sum(sop.price),sum(sop.gst),sum(sop.quantity)) \n"
				+ "	    from SlamOrderModel so,OrderProds sop,SlamProduct sp,productCategoryModel spc \n"
				+ "	    where sop.productId=sp.id and sp.productCategory=spc.id group by spc.id";

		List<CategoryReportViewModel> result = entityManager.createQuery(hql, CategoryReportViewModel.class)
				.getResultList();
		return result;

	}

}
