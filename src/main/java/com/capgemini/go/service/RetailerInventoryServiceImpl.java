package com.capgemini.go.service;

import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.RollbackException;

import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.exception.UserException;
import com.capgemini.go.repository.RetailerInventoryRepository;
import com.capgemini.go.repository.UserRepository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	@Autowired
	private RetailerInventoryRepository retailerInventoryRepository;
	@Autowired
	private UserRepository userRepository;
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
	public List<RetailerInventoryBean> getItemWiseDeliveryTimeReport(int retailerId) throws RetailerInventoryException {
		// TODO Auto-generated method stub
		//logger.info("getItemWiseDeliveryTimeReport - " + "Request for item wise delivery time report received");
		List<RetailerInventoryBean> result = new ArrayList<RetailerInventoryBean> ();
		
		//RetailerInventoryDTO queryArguments = new RetailerInventoryDTO (retailerId, (byte)0, null, null, null, null, null);
		List<RetailerInventoryDTO> listOfDeliveredItems = retailerInventoryRepository.findAllByretailerId(retailerId);
				//this.retailerInventoryDao.getDeliveredItemsDetails(queryArguments);
				
		try {
			List<UserDTO> userList = (List<UserDTO>) userRepository.findAll();
					//this.userDao.getUserIdList();
			
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
				object.setDeliveryTimePeriod(GoUtility.calculatePeriod(deliveredItem.getProductDispatchTimestamp(), deliveredItem.getProductRecieveTimestamp()));
				object.setShelfTimePeriod(null);
				result.add(object);
			}
			
		} catch (RuntimeException error) {
			//logger.error("getItemWiseDeliveryTimeReport - " + error.getMessage());
			throw new RetailerInventoryException ("getItemWiseDeliveryTimeReport - " + ExceptionConstants.INTERNAL_RUNTIME_ERROR);
		}
		//logger.info("getItemWiseDeliveryTimeReport - " + "Sent requested data");
		return result;
	}

	@Override
	public List<RetailerInventoryBean> getCategoryWiseDeliveryTimeReport(int retailerId) throws RetailerInventoryException{
		// TODO Auto-generated method stub
List<RetailerInventoryBean> result = new ArrayList<RetailerInventoryBean> ();
		
		//RetailerInventoryDTO queryArguments = new RetailerInventoryDTO (retailerId, (byte)0, null, null, null, null, null);
		List<RetailerInventoryDTO> listOfDeliveredItems = retailerInventoryRepository.findAllByretailerId(retailerId); 
				//this.retailerInventoryDao.getDeliveredItemsDetails(queryArguments);
		
		Map<Integer, List<RetailerInventoryBean>> map = new HashMap<Integer, List<RetailerInventoryBean>>();
		for (int category = 1; category <= 5; category++)
			map.put(category, new ArrayList<RetailerInventoryBean>());
		
		try {
			List<UserDTO> userList = (List<UserDTO>) userRepository.findAll();
					//this.userDao.getUserIdList();
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
				object.setDeliveryTimePeriod(GoUtility.calculatePeriod(deliveredItem.getProductDispatchTimestamp(), deliveredItem.getProductRecieveTimestamp()));
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
			
		} catch (RuntimeException error) {
			//logger.error("getCategoryWiseDeliveryTimeReport - " + error.getMessage());
			error.printStackTrace();
			throw new RetailerInventoryException ("getCategoryWiseDeliveryTimeReport - " + ExceptionConstants.INTERNAL_RUNTIME_ERROR);
		}
		//logger.info("getCategoryWiseDeliveryTimeReport - " + "Sent requested data");
		return result;
	}



	@Override
	public List<RetailerInventoryBean> getOutlierCategoryItemWiseDeliveryTimeReport(int retailerId)
			throws RetailerInventoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateProductRecieveTimeStamp(RetailerInventoryDTO retailerinventorydto) throws RetailerInventoryException {
		//logger.info("updateProductReceiveTimeStamp - " + "function called");
		boolean receiveTimestampUpdated = false;
		
		Transaction transaction = null;
		Session session = getSessionFactory().openSession();
		try {
			transaction = session.getTransaction();
			transaction.begin();
			RetailerInventoryDTO existingItem = (RetailerInventoryDTO) retailerInventoryRepository.findAll();
					//session.find(RetailerInventoryDTO.class, queryArguments.getProductUniqueId());
			if (existingItem == null) {
				//logger.debug(ExceptionConstants.PRODUCT_NOT_IN_INVENTORY);
				throw new RetailerInventoryException(
						"updateProductReceiveTimeStamp - " + ExceptionConstants.PRODUCT_NOT_IN_INVENTORY);
			}
			existingItem.setProductRecieveTimestamp(retailerinventorydto.getProductRecieveTimestamp());
			transaction.commit();
		} catch (IllegalStateException error) {
			//logger.error(error.getMessage());
			throw new RetailerInventoryException(
					"updateProductReceiveTimeStamp - " + ExceptionConstants.INAPPROPRIATE_METHOD_INVOCATION);
		} catch (RollbackException error) {
			//logger.error(error.getMessage());
			throw new RetailerInventoryException(
					"updateProductReceiveTimeStamp - " + ExceptionConstants.FAILURE_COMMIT_CHANGES);
		} finally {
			session.close();
		}
		receiveTimestampUpdated = true;
		return receiveTimestampUpdated;
		
	}
	
	@Override
	public boolean updateProductSaleTimeStamp(RetailerInventoryDTO retailerinventorydto) throws RetailerInventoryException {
		// TODO Auto-generated method stub
		boolean saleTimestampUpdated = false;
		//logger.info("updateProductSaleTimeStamp - " + "function called");
		/*
		 * required arguments in `queryArguments` productUIN, productSaleTime
		 * 
		 * un-required productDispatchTime, productReceiveTime, productCategory,
		 * retailerUserId
		 */
		Transaction transaction = null;
		Session session = getSessionFactory().openSession();
		try {
			transaction = session.getTransaction();
			transaction.begin();
			RetailerInventoryDTO existingItem = (RetailerInventoryDTO) retailerInventoryRepository.findAll();
					//session.find(RetailerInventoryDTO.class, queryArguments.getProductUniqueId());
			if (existingItem == null) {
				//logger.debug(ExceptionConstants.PRODUCT_NOT_IN_INVENTORY);
				throw new RetailerInventoryException(
						"updateProductSaleTimeStamp - " + ExceptionConstants.PRODUCT_NOT_IN_INVENTORY);
			}
			existingItem.setProductSaleTimestamp(retailerinventorydto.getProductSaleTimestamp());
			transaction.commit();
		} catch (IllegalStateException error) {
			//logger.error("updateProductSaleTimeStamp - " + error.getMessage());
			throw new RetailerInventoryException(
					"updateProductSaleTimeStamp - " + ExceptionConstants.INAPPROPRIATE_METHOD_INVOCATION);
		} catch (RollbackException error) {
			//logger.error("updateProductSaleTimeStamp - " + error.getMessage());
			throw new RetailerInventoryException(
					"updateProductSaleTimeStamp - " + ExceptionConstants.FAILURE_COMMIT_CHANGES);
		} finally {
			session.close();
		}
		saleTimestampUpdated = true;
		return saleTimestampUpdated;
		
	}

}
