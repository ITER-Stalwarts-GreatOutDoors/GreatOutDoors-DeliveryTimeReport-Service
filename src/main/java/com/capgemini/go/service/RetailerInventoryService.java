package com.capgemini.go.service;

import java.util.Calendar;
import java.util.List;

import com.capgemini.go.bean.RetailerInventoryBean;
import com.capgemini.go.dto.RetailerInventoryDTO;
import com.capgemini.go.exception.RetailerInventoryException;

public interface RetailerInventoryService {
	public List<RetailerInventoryBean> getItemWiseDeliveryTimeReport(int retailerId) throws RetailerInventoryException;
	public List<RetailerInventoryBean> getCategoryWiseDeliveryTimeReport(int retailerId) throws RetailerInventoryException;
	public boolean updateProductRecieveTimeStamp(RetailerInventoryDTO retailerinventorydto) throws RetailerInventoryException;
	public boolean updateProductSaleTimeStamp(RetailerInventoryDTO retailerinventorydto) throws RetailerInventoryException;
	public List<RetailerInventoryDTO> getListOfRetailers();
	public List<RetailerInventoryDTO> getInventoryById(int retailerId);
	public List<RetailerInventoryBean> getMonthlyShelfTimeReport(int retailerId, Calendar dateSelection) throws RetailerInventoryException;;
	public List<RetailerInventoryBean> getQuarterlyShelfTimeReport(int retailerId, Calendar dateSelection) throws RetailerInventoryException;;
	public List<RetailerInventoryBean> getYearlyShelfTimeReport(int retailerId, Calendar dateSelection) throws RetailerInventoryException;;

}
