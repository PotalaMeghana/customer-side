package eStoreProduct.DAO.admin;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import eStoreProduct.model.admin.entities.RegionModel;
import eStoreProduct.model.admin.input.Regions;
import eStoreProduct.model.admin.output.RegionsOutput;


@Component
public class RegionDAOImpl implements RegionDAO {
	
	@PersistenceContext
	private EntityManager entityManager;



	//method to get all the regions available
	@Override
	@Transactional
	public List<RegionsOutput> getRegions() {
	 List<RegionsOutput> regions=null;
		try {
			//create JPA query
            TypedQuery<RegionsOutput> query = entityManager.createQuery("SELECT NEW eStoreProduct.model.admin.output.RegionsOutput("
            		+ "r.regionId, r.regionName,r.regionPinFrom,r.regionPinTo,r.regionSurcharge,r.regionPriceWaiver)"
            		+ " FROM RegionModel r", RegionsOutput.class);
			//execute query
            regions = query.getResultList();
            
            return regions;
        } catch (Exception e) {
           
            e.printStackTrace();
            return Collections.emptyList(); 
        }
	}

	//method to add a new region
	@Override
	@Transactional
	public boolean addRegion(Regions reg) {
		RegionModel reg1=new RegionModel() ;
		//set all the object attributes
		reg1.setRegionId(reg.getRegionId());
		reg1.setRegionName(reg.getRegionName());
		reg1.setRegionPinFrom(reg.getRegionPinFrom());
		reg1.setRegionPinTo(reg.getRegionPinTo());
		reg1.setRegionPriceWaiver(reg.getRegionPriceWaiver());
		reg1.setRegionSurcharge(reg.getRegionSurcharge());
		try{
			//execute query
        		entityManager.merge(reg1);
			return true;
			
		}
		catch(Exception e){
			
			return false;
		}
			
    }
	
	//remove existing region
	@Override
	@Transactional
	public boolean removeRegion(int id) {
		try{
			//get the region if existing 
		RegionModel region = entityManager.find(RegionModel.class, id);
	        if (region != null) {
			//remove the region
	            entityManager.remove(region);
			return true;
       		 }
		else{
			return false;
		
			}
    		}
		catch(Exception e){
			return false;
		}
		
}
}
