package com.capgemini.go.controller;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	

	private static final Logger logger = Logger.getLogger(RetailerInventoryController.class);
	@Autowired
	private RetailerInventoryService retailerInventoryService;
		
	@ResponseBody
	@GetMapping("/getDeliveryTimeReport")
	public List<RetailerInventoryBean> getDeliveryTimeReport(@RequestParam String retailerId, @RequestParam int reportType)
	{
		
		List<RetailerInventoryBean> result = null;
		switch (reportType) {
		case 1: {
				if(retailerId.isEmpty())
				{
					logger.error("Null request, cart details not provided at /getDeliveryTimeReport");
					throw new RetailerInventoryException("Null request, please provide retailerId!");
				}
				else
				{
					result = retailerInventoryService.getItemWiseDeliveryTimeReport(retailerId);
				}
				break;
		}
		case 2: {
				if(retailerId.isEmpty())
				{
					logger.error("Null request, cart details not provided at /getDeliveryTimeReport");
					throw new RetailerInventoryException("Null request, please provide retailer Id!");
				}
				else
				{
					result = retailerInventoryService.getCategoryWiseDeliveryTimeReport(retailerId);
				}
				break;
		}
		default: {		
			logger.error("getDeliveryTimeReport - " + "Invalid Argument Received");
		}
	}
	
		return result;
		
		}
	@ResponseBody
	@GetMapping("/ShelfTimeReport")
	public List<RetailerInventoryBean> getShelfTimeReport(@RequestParam String retailerId, @RequestParam int reportType) {
		// logger.info("getShelfTimeReport - " + "Request for Shelf Time Report Received");	
		Calendar dateSelection = Calendar.getInstance();
		List<RetailerInventoryBean> result = null;
		switch (reportType) {
		case 1: {
			if(retailerId.isEmpty())
			{
				logger.error("Null request, cart details not provided at /ShelfTimeReport");
				throw new RetailerInventoryException("Null request, please provide retailer Id!");
			}
			else
			{
				result = retailerInventoryService.getMonthlyShelfTimeReport(retailerId, dateSelection);
			}
			break;
		}

		case 2: {
			if(retailerId.isEmpty())
			{
				logger.error("Null request, cart details not provided at /ShelfTimeReport");
				throw new RetailerInventoryException("Null request, please provide retailer Id!");
			}
			else
			{
				result = retailerInventoryService.getQuarterlyShelfTimeReport(retailerId, dateSelection);
			}
			break;
		}

		case 3: {
			if(retailerId.isEmpty())
			{
				logger.error("Null request, cart details not provided at /ShelfTimeReport");
				throw new RetailerInventoryException("Null request, please provide retailer Id!");
			}
			else
			{
				result = retailerInventoryService.getYearlyShelfTimeReport(retailerId, dateSelection);
			}
			break;
		}
		default: {
			
			logger.error("getShelfTimeReport - " + "Invalid Argument Received");
			
		}
		}
		return result;

	}
	
	@ResponseBody
	@GetMapping("/getProductRecieveTime")
	public String getUpdateProductRecieveTimeStamp(@RequestBody RetailerInventoryDTO retailerInventoryDTO)
	{
		if(retailerInventoryDTO==null || retailerInventoryDTO.getProductRecieveTimestamp()==null) { 
			logger.error("Null request, cart details not provided at /getProductRecieveTime");
			throw new RetailerInventoryException("Null request, please provide recieve timestamp!");
		}
		String status="Product Recieve Timestamp updated";
		retailerInventoryService.updateProductRecieveTimeStamp(retailerInventoryDTO);
		
		return status;
		
	}
	
	@ResponseBody
	@GetMapping("/getProductSaleTime")
	public String getUpdateProductSaleTimeStamp(@RequestBody RetailerInventoryDTO retailerInventoryDTO)
	{
		if(retailerInventoryDTO==null || retailerInventoryDTO.getProductSaleTimestamp()==null) { 
			logger.error("Null request, cart details not provided at /getProductSaleTime");
			throw new RetailerInventoryException("Null request, please provide sale timestamp!");
		}
		String status="Product Sale Timestamp updated";
		retailerInventoryService.updateProductRecieveTimeStamp(retailerInventoryDTO);
		
		return status;
	}
	
	@ResponseBody
	@GetMapping("/RetailerList")
	public List<RetailerInventoryDTO> getRetailerList () {
		List<RetailerInventoryDTO> result = null;
		result = this.retailerInventoryService.getListOfRetailers();
		if(result==null)
		{
			logger.error("Null request, cart details not provided at /RetailerList");
			throw new RetailerInventoryException("Null request, please provide retailer List!");
		}
		return result;
	}
	

	@GetMapping("/RetailerInventoryById")
	public List<RetailerInventoryDTO> getRetailerInventoryById (@RequestParam String retailerId) {
		
		List<RetailerInventoryDTO> result = null;
		if(retailerId.isEmpty())
		{
			logger.error("Null request, cart details not provided at /RetailerInventoryById");
			throw new RetailerInventoryException("Null request, please provide retailer Id!");
		}
		else
		{
			result = retailerInventoryService.getInventoryById(retailerId);
		}
		return result;
	}

}
	
	
	
	


