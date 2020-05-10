package com.capgemini.go.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
				error.printStackTrace();
				status = error.getMessage();
			}
			break;
		}
		case 2: {
			try {
				result = this.retailerInventoryService.getCategoryWiseDeliveryTimeReport(retailerId);
			} catch (RetailerInventoryException error) {
				error.printStackTrace();
				status = error.getMessage();
			}
			break;
		}
		default: {		
			return  "Invalid Argument Received";
		}
	}
		if (result == null) {
			return "Data could not be obtained from database";
		}
		return status;
		
		}
	@ResponseBody
	@PostMapping("/ShelfTimeReport")
	public String getShelfTimeReport(@RequestParam int retailerId, @RequestParam int reportType) {
		// logger.info("getShelfTimeReport - " + "Request for Shelf Time Report Received");	
		// String retailerId = requestData.get("retailerId").toString();
//		int reportType = Integer.valueOf(requestData.get("reportType").toString());
		String status = "Request for Shelf Time Report Recieved!";
		Calendar dateSelection = Calendar.getInstance();
		List<RetailerInventoryBean> result = null;
		switch (reportType) {
		case 1: {
			try {
				result = this.retailerInventoryService.getMonthlyShelfTimeReport(retailerId, dateSelection);
			} catch (RetailerInventoryException error) {
				// logger.error("getShelfTimeReport - " + error.getMessage());
				
				error.printStackTrace();
				status = error.getMessage();
			}
			break;
		}

		case 2: {
			try {
				result = this.retailerInventoryService.getQuarterlyShelfTimeReport(retailerId, dateSelection);
			} catch (RetailerInventoryException error) {
				// logger.error("getShelfTimeReport - " + error.getMessage());
				
					error.printStackTrace();
					status = error.getMessage();
			}
			break;
		}

		case 3: {
			try {
				result = this.retailerInventoryService.getYearlyShelfTimeReport(retailerId, dateSelection);
			} catch (RetailerInventoryException error) {
				// logger.error("getShelfTimeReport - " + error.getMessage());
				
				error.printStackTrace();
				status = error.getMessage();
			}
			break;
		}
		default: {
			// logger.error("getShelfTimeReport - " + "Invalid Argument Received");
			
			return "Data could not be obtained from database";
		}
		}
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
	
	@ResponseBody
	@PostMapping("/RetailerList")
	public String getRetailerList () {
		String status="Get Retailer List..";
		List<RetailerInventoryDTO> result = null;
		try {
			result = this.retailerInventoryService.getListOfRetailers();

		} catch (Exception error) {
			error.printStackTrace();
			status = error.getMessage();
		}
		return status;
	}
	
	@ResponseBody
	@GetMapping("/RetailerInventoryById/{retailerId}")
	public String getRetailerInventoryById (@RequestParam int retailerId) {
		String status="Get Retailer by id.";
		List<RetailerInventoryDTO> result = null;
		try {
			result = this.retailerInventoryService.getInventoryById(retailerId);
		} catch (Exception error) {
			error.printStackTrace();
			status = error.getMessage();
		}
		return status;
	}

}
	
	
	
	


