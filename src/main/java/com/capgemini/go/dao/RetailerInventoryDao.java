package com.capgemini.go.dao;

import java.util.List;

import com.capgemini.go.dto.RetailerInventoryDTO;

public interface RetailerInventoryDao {

	public List<RetailerInventoryDTO> getSoldItemsDetails(RetailerInventoryDTO retailerinventorydto);
	public List<RetailerInventoryDTO> getDeliveredItemsDetails(RetailerInventoryDTO retailerinventorydto);
	public List<RetailerInventoryDTO> getItemListByRetailor(RetailerInventoryDTO retailerinventorydto);
	public List<RetailerInventoryDTO> getListOfRetailers();
	public boolean updateProductRecieveTimeStamp(RetailerInventoryDTO retailerinventorydto);
	public boolean updateProductSaleTimeStamp(RetailerInventoryDTO retailerinventorydto);
	public boolean insertItemInRetailerInventory(RetailerInventoryDTO retailerinventorydto);
	public boolean deleteIteminRetailerInventory(RetailerInventoryDTO retailerinventorydto);
}
