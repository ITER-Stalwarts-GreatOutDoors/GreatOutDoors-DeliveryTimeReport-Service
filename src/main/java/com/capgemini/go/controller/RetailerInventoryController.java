package com.capgemini.go.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.go.service.RetailerInventoryService;
import com.capgemini.go.bean.RetailerInventoryBean;
import com.capgemini.go.dto.RetailerInventoryDTO;
import com.capgemini.go.exception.RetailerInventoryException;

@RestController
@RequestMapping("/getRetailerInventory")
public class RetailerInventoryController {
	
	//private Logger logger = Logger.getRootLogger();
	@Autowired
	private RetailerInventoryService retailerInventoryService;
		
	@ResponseBody
	@PostMapping("/getDeliveryTimeReport")
	public String getDeliveryTimeReport(@RequestParam int retailerId, @RequestParam int reportType)
	{
		String status = "Request for Delivery Time Report Recieved!";
		List<RetailerInventoryBean> result = null;
		switch (reportType) {
		case 1: {
			try {
				result = this.retailerInventoryService.getItemWiseDeliveryTimeReport(retailerId);
			} catch (RetailerInventoryException error) {
				//logger.error("getDeliveryTimeReport - " + error.getMessage());
				error.printStackTrace();
				status = error.getMessage();
			}
			break;
		}
		case 2: {
			try {
				result = this.retailerInventoryService.getCategoryWiseDeliveryTimeReport(retailerId);
			} catch (RetailerInventoryException error) {
				//logger.error("getDeliveryTimeReport - " + error.getMessage());
				error.printStackTrace();
				status = error.getMessage();
			}
			break;
		}
		case 3: {
			try {
				result = this.retailerInventoryService.getOutlierCategoryItemWiseDeliveryTimeReport(retailerId);
			} catch (RetailerInventoryException error) {
				//logger.error("getDeliveryTimeReport - " + error.getMessage());
				error.printStackTrace();
				status = error.getMessage();
			}
			break;
		}
		
		default: {
			//logger.error("getDeliveryTimeReport - " + "Invalid Argument Received");		
			return  "Invalid Argument Received";
		}
	}
		if (result == null) {
			//logger.error("getDeliveryTimeReport - " + "Data could not be obtained from database");
			return "Data could not be obtained from database";
		}
		
		//logger.info("getDeliveryTimeReport - " + "Sent requested data");
		return status;
		
		}
	
	@ResponseBody
	@PostMapping("/getProductRecieveTime")
	public String getUpdateProductRecieveTimeStamp(@RequestBody RetailerInventoryDTO retailerInventoryDTO)
	{
		String status="Product Timestamp updated";
		try {
			retailerInventoryService.updateProductRecieveTimeStamp(retailerInventoryDTO);
		}catch (RetailerInventoryException error) {
			error.printStackTrace();
			status = error.getMessage();
		}
		return status;
		
	}
	
	@ResponseBody
	@PostMapping("/getProductSaleTime")
	public String getUpdateProductSaleTimeStamp(@RequestBody RetailerInventoryDTO retailerInventoryDTO)
	{
		String status="Product Timestamp updated";
		try {
			retailerInventoryService.updateProductSaleTimeStamp(retailerInventoryDTO);
		}catch (RetailerInventoryException error) {
			error.printStackTrace();
			status = error.getMessage();
		}
		return status;
		
	}

}
	
	
	
	


