package com.capgemini.go.service;

import java.util.List;

import com.capgemini.go.bean.RetailerInventoryBean;
import com.capgemini.go.exception.RetailerInventoryException;

public interface RetailerInventoryService {
	public List<RetailerInventoryBean> getItemWiseDeliveryTimeReport(String retailerId) throws RetailerInventoryException;
	public List<RetailerInventoryBean> getCategoryWiseDeliveryTimeReport(String retailerId) throws RetailerInventoryException;
	public List<RetailerInventoryBean> getOutlierCategoryItemWiseDeliveryTimeReport(String retailerId) throws RetailerInventoryException;
	public List<RetailerInventoryBean> getListOfRetailers();

}
