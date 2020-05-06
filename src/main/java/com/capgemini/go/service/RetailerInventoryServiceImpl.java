package com.capgemini.go.service;

import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;

import com.capgemini.go.bean.RetailerInventoryBean;
import com.capgemini.go.dao.RetailerInventoryDao;
import com.capgemini.go.dto.RetailerInventoryDTO;
import com.capgemini.go.dto.UserDTO;
import com.capgemini.go.dao.UserDao;
import com.capgemini.go.exception.RetailerInventoryException;
import com.capgemini.go.utility.GoUtility;

public class RetailerInventoryServiceImpl implements RetailerInventoryService {

	@Autowired
	private RetailerInventoryDao retailerInventoryDao;
	
	@Autowired
	private UserDao userDao;
	
	public UserDao getUserDao () {
		return userDao;
	}

	public void setUserDao (UserDao userDao) {
		this.userDao = userDao;
	}
	@Override
	public List<RetailerInventoryBean> getItemWiseDeliveryTimeReport(String retailerId) throws RetailerInventoryException {
		// TODO Auto-generated method stub
		//logger.info("getItemWiseDeliveryTimeReport - " + "Request for item wise delivery time report received");
		List<RetailerInventoryBean> result = new ArrayList<RetailerInventoryBean> ();
		
		RetailerInventoryDTO queryArguments = new RetailerInventoryDTO (retailerId, (byte)0, null, null, null, null, null);
		List<RetailerInventoryDTO> listOfDeliveredItems = this.retailerInventoryDao.getDeliveredItemsDetails(queryArguments);
				
		try {
			List<UserDTO> userList = this.userDao.getUserIdList();
			
			for (RetailerInventoryDTO deliveredItem : listOfDeliveredItems) {
				RetailerInventoryBean object = new RetailerInventoryBean ();
				object.setRetailerId(retailerId);
				for (UserDTO user : userList) {
					if (user.getUserId().equals(retailerId)) {
						object.setRetailerName(user.getUserName());
						break;
					}
				}
				object.setProductCategoryNumber(deliveredItem.getProductCategory());
				object.setProductCategoryName(GoUtility.getCategoryName(deliveredItem.getProductCategory()));
				object.setProductUniqueId(deliveredItem.getProductUniqueId());
				object.setDeliveryTimePeriod(GoUtility.calculatePeriod(deliveredItem.getProductDispatchTimestamp(), deliveredItem.getProductReceiveTimestamp()));
				object.setShelfTimePeriod(null);
				result.add(object);
			}
			
		} catch (UserException error) {
			//logger.error("getItemWiseDeliveryTimeReport - " + error.getMessage());
			throw new RetailerInventoryException ("getItemWiseDeliveryTimeReport - " + ExceptionConstants.FAILED_TO_RETRIEVE_USERNAME);
		} catch (RuntimeException error) {
			//logger.error("getItemWiseDeliveryTimeReport - " + error.getMessage());
			throw new RetailerInventoryException ("getItemWiseDeliveryTimeReport - " + ExceptionConstants.INTERNAL_RUNTIME_ERROR);
		}
		//logger.info("getItemWiseDeliveryTimeReport - " + "Sent requested data");
		return result;
	}

	@Override
	public List<RetailerInventoryBean> getCategoryWiseDeliveryTimeReport(String retailerId) throws RetailerInventoryException{
		// TODO Auto-generated method stub
List<RetailerInventoryBean> result = new ArrayList<RetailerInventoryBean> ();
		
		RetailerInventoryDTO queryArguments = new RetailerInventoryDTO (retailerId, (byte)0, null, null, null, null, null);
		List<RetailerInventoryDTO> listOfDeliveredItems = this.retailerInventoryDao.getDeliveredItemsDetails(queryArguments);
		
		Map<Integer, List<RetailerInventoryBean>> map = new HashMap<Integer, List<RetailerInventoryBean>>();
		for (int category = 1; category <= 5; category++)
			map.put(category, new ArrayList<RetailerInventoryBean>());
		
		try {
			List<UserDTO> userList = this.userDao.getUserIdList();
			for (RetailerInventoryDTO deliveredItem : listOfDeliveredItems) {
				RetailerInventoryBean object = new RetailerInventoryBean ();
				object.setRetailerId(retailerId);
				for (UserDTO user : userList) {
					if (user.getUserId().equals(retailerId)) {
						object.setRetailerName(user.getUserName());
						break;
					}
				}
				object.setProductCategoryNumber(deliveredItem.getProductCategory());
				object.setProductCategoryName(GoUtility.getCategoryName(deliveredItem.getProductCategory()));
				object.setProductUniqueId(deliveredItem.getProductUniqueId());
				object.setDeliveryTimePeriod(GoUtility.calculatePeriod(deliveredItem.getProductDispatchTimestamp(), deliveredItem.getProductReceiveTimestamp()));
				object.setShelfTimePeriod(null);
				map.get(Integer.valueOf(object.getProductCategoryNumber())).add(object);
			}
			
			for (int category = 1; category <= 5; category++) {
				if (map.get(category).size() != 0) {
					int years = 0, months = 0, days = 0, count = 0;
					for (RetailerInventoryBean item : map.get(category)) {
						years += item.getDeliveryTimePeriod().getYears(); 
						months += item.getDeliveryTimePeriod().getMonths(); 
						days += item.getDeliveryTimePeriod().getDays();
						count ++;
					}
					years /= count;
					months /= count;
					days /= count;
					RetailerInventoryBean object = new RetailerInventoryBean ();
					object.setProductCategoryNumber((byte)category);
					object.setProductCategoryName(GoUtility.getCategoryName(category));
					object.setProductUniqueId("----");
					object.setDeliveryTimePeriod(Period.of(years, months, days));
					result.add(object);
				}
			}
			
		} catch (UserException error) {
			//logger.error("getCategoryWiseDeliveryTimeReport - " + error.getMessage());
			error.printStackTrace();
			throw new RetailerInventoryException ("getCategoryWiseDeliveryTimeReport - " + ExceptionConstants.FAILED_TO_RETRIEVE_USERNAME);
		} catch (RuntimeException error) {
			//logger.error("getCategoryWiseDeliveryTimeReport - " + error.getMessage());
			error.printStackTrace();
			throw new RetailerInventoryException ("getCategoryWiseDeliveryTimeReport - " + ExceptionConstants.INTERNAL_RUNTIME_ERROR);
		}
		//logger.info("getCategoryWiseDeliveryTimeReport - " + "Sent requested data");
		return result;
	}

	@Override
	public List<RetailerInventoryBean> getOutlierCategoryItemWiseDeliveryTimeReport(String retailerId) throws RetailerInventoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RetailerInventoryBean> getListOfRetailers() {
		// TODO Auto-generated method stub
		return null;
	}

}
