package com.capgemini.go.service;

import java.util.List;

import com.capgemini.go.bean.RetailerInventoryBean;

public interface RetailerInventoryService {
	public List<RetailerInventoryBean> getItemWiseDeliveryTimeReport(String retailerId);
	public List<RetailerInventoryBean> getCategoryWiseDeliveryTimeReport(String retailerId);
	public List<RetailerInventoryBean> getOutlierCategoryItemWiseDeliveryTimeReport(String retailerId);

}
