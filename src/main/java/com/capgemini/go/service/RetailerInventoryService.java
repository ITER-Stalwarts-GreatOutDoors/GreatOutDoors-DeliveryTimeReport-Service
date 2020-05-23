package com.capgemini.go.service;

import java.util.Calendar;
import java.util.List;

import com.capgemini.go.bean.RetailerInventoryBean;
import com.capgemini.go.dto.RetailerDTO;
import com.capgemini.go.dto.RetailerInventoryDTO;
import com.capgemini.go.exception.RetailerInventoryException;

public interface RetailerInventoryService {
	
	//Delivery Time Report
	/*******************************************************************************************************
	 * - Function Name : getItemWiseDeliveryTimeReport <br>
	 * - Description : to get Item wise Delivery Time Report  <br>
	 * 
	 * @param String retailerId
	 * @return List<RetailerInventoryBean>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryBean> getItemWiseDeliveryTimeReport(String retailerId) throws RetailerInventoryException;
	/*******************************************************************************************************
	 * - Function Name : getCategoryWiseDeliveryTimeReport <br>
	 * - Description : to get Category wise Delivery Time Report  <br>
	 * 
	 * @param String retailerId
	 * @return List<RetailerInventoryBean>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryBean> getCategoryWiseDeliveryTimeReport(String retailerId) throws RetailerInventoryException;
	public boolean updateItemRecieveTimeStamp(RetailerInventoryDTO retailerinventorydto) throws RetailerInventoryException;
	public boolean updateItemSaleTimeStamp(RetailerInventoryDTO retailerinventorydto) throws RetailerInventoryException;
	
	//Retailer Management System
	/*******************************************************************************************************
	 * - Function Name : getListOfRetailers <br>
	 * - Description : to get list of retailers in database  <br>
	 * - This function is called by client side application to load form data
	 * 
	 * @return List<RetailerInventoryBean>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryDTO> getListOfRetailers();
	
	/*******************************************************************************************************
	 * - Function Name : getInventoryById <br>
	 * - Description : to get List of Inventory by Id  <br>
	 * 
	 * @param String retailerId
	 *******************************************************************************************************/
	public List<RetailerInventoryDTO> getInventoryById(String retailerId);
	
	// Shelf Time Report 
		/*******************************************************************************************************
		 * - Function Name : getMonthlyShelfTimeReport <br>
		 * - Description : to get Monthly Shelf Time Report  <br>
		 * 
		 * @param String retailerId
		 * @param Calendar dateSelection
		 * @return List<RetailerInventoryBean>
		 * @throws RetailerInventoryException
		 *******************************************************************************************************/
	public List<RetailerInventoryBean> getMonthlyShelfTimeReport(String retailerId, Calendar dateSelection) throws RetailerInventoryException;;
	/*******************************************************************************************************
	 * - Function Name : getQuarterlyShelfTimeReport <br>
	 * - Description : to get Quarterly Shelf Time Report  <br>
	 * 
	 * @param String retailerId
	 * @param Calendar dateSelection
	 * @return List<RetailerInventoryBean>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryBean> getQuarterlyShelfTimeReport(String retailerId, Calendar dateSelection) throws RetailerInventoryException;;
	/*******************************************************************************************************
	 * - Function Name : getYearlyShelfTimeReport <br>
	 * - Description : to get Yearly Shelf Time Report  <br>
	 * 
	 * @param String retailerId
	 * @param Calendar dateSelection
	 * @return List<RetailerInventoryBean>
	 * @throws RetailerInventoryException
	 *******************************************************************************************************/
	public List<RetailerInventoryBean> getYearlyShelfTimeReport(String retailerId, Calendar dateSelection) throws RetailerInventoryException;
	List<RetailerDTO> getRetailers();;

}
