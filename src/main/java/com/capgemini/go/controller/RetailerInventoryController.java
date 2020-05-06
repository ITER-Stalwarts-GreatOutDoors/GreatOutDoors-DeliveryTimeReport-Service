package com.capgemini.go.controller;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.capgemini.go.service.RetailerInventoryService;
import com.capgemini.go.bean.RetailerInventoryBean;
import com.capgemini.go.exception.RetailerInventoryException;

@RestController
@RequestMapping("/getRetailerInventory")
public class RetailerInventoryController {
	
	//private Logger logger = Logger.getRootLogger();
	@Autowired
	private RetailerInventoryService retailerInventoryService;
	
	public RetailerInventoryService getRetailerInventoryService() {
		return retailerInventoryService;
	}

	public void setRetailerInventoryService(RetailerInventoryService retailerInventoryService) {
		this.retailerInventoryService = retailerInventoryService;
	}
	
	@ResponseBody
	@PostMapping("/getDeliveryTimeReport")
	public String getDeliveryTimeReport(@RequestBody Map<String,Object> requestData)
	{
		String status = "Request for Delivery Time Report Recieved!";
		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		String retailerId = requestData.get("retailerId").toString();
		int reportType = Integer.valueOf(requestData.get("reportType").toString());
		List<RetailerInventoryBean> result = null;
		switch (reportType) {
		case 1: {
			try {
				result = this.retailerInventoryService.getItemWiseDeliveryTimeReport(retailerId);
			} catch (RetailerInventoryException error) {
				//logger.error("getDeliveryTimeReport - " + error.getMessage());
				((ObjectNode) dataResponse).put("Error", error.getMessage());
				return dataResponse.toString();
			}
			break;
		}
		case 2: {
			try {
				result = this.retailerInventoryService.getCategoryWiseDeliveryTimeReport(retailerId);
			} catch (RetailerInventoryException error) {
				//logger.error("getDeliveryTimeReport - " + error.getMessage());
				((ObjectNode) dataResponse).put("Error", error.getMessage());
				return dataResponse.toString();
			}
			break;
		}
		case 3: {
			try {
				result = this.retailerInventoryService.getOutlierCategoryItemWiseDeliveryTimeReport(retailerId);
			} catch (RetailerInventoryException error) {
				//logger.error("getDeliveryTimeReport - " + error.getMessage());
				((ObjectNode) dataResponse).put("Error", error.getMessage());
				return dataResponse.toString();
			}
			break;
		}
		
		default: {
			//logger.error("getDeliveryTimeReport - " + "Invalid Argument Received");
			((ObjectNode) dataResponse).put("Error", "Invalid Argument Received");
			return dataResponse.toString();
		}
	}
		if (result == null) {
			//logger.error("getDeliveryTimeReport - " + "Data could not be obtained from database");
			((ObjectNode) dataResponse).put("Error", "Data could not be obtained from database");
			return dataResponse.toString();
		}
		JsonArray itemList=new JsonArray();
		for (RetailerInventoryBean item : result) {
			JsonObject itemObj = new JsonObject();
			itemObj.addProperty ("retailerId", item.getRetailerId());
			itemObj.addProperty("retailerName", item.getRetailerName());
			itemObj.addProperty("productCategoryNumber", item.getProductCategoryNumber());
			itemObj.addProperty("productCategoryName", item.getProductCategoryName());
			itemObj.addProperty("productUniqueId", item.getProductUniqueId());
			itemObj.addProperty("deliveryTimePeriod", RetailerInventoryBean.periodToString(item.getDeliveryTimePeriod()));
			itemList.add(itemObj);
		}
		//logger.info("getDeliveryTimeReport - " + "Sent requested data");
		return status+" "+itemList.toString();
		
		}
	@ResponseBody
	@PostMapping("/RetailerList")
	public String getRetailerList () {
		//logger.info("getRetailerList - " + "Request for Retailer List Received");
		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataResponse = mapper.createObjectNode();
		JsonArray retailerList = new JsonArray();
		try {
			List<RetailerInventoryBean> result = this.retailerInventoryService.getListOfRetailers();
			for (RetailerInventoryBean item : result) {
				JsonObject retailerObj = new JsonObject();
				retailerObj.addProperty ("retailerId", item.getRetailerId());
				retailerObj.addProperty("retailerName", item.getRetailerName());
				retailerList.add(retailerObj);
			}
		} catch (Exception error) {
			//logger.error("getRetailerList - " + error.getMessage());
			((ObjectNode) dataResponse).put("Error", error.getMessage());
			return dataResponse.toString();
		}
		//logger.info("getRetailerList - " + "Sent requested data");
		return retailerList.toString();
	}

}
	
	
	
	


