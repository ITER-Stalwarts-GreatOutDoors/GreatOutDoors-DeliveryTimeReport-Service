package com.capgemini.go.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.capgemini.go.dto.RetailerInventoryDTO;

public class RetailerInventoryDaoImpl implements RetailerInventoryDao {

	private SessionFactory sessionFactory;
	
	@Override
	public List<RetailerInventoryDTO> getSoldItemsDetails(RetailerInventoryDTO retailerinventorydto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RetailerInventoryDTO> getDeliveredItemsDetails(RetailerInventoryDTO retailerinventorydto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RetailerInventoryDTO> getItemListByRetailor(RetailerInventoryDTO retailerinventorydto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RetailerInventoryDTO> getListOfRetailers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateProductRecieveTimeStamp(RetailerInventoryDTO retailerinventorydto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateProductSaleTimeStamp(RetailerInventoryDTO retailerinventorydto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertItemInRetailerInventory(RetailerInventoryDTO retailerinventorydto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteIteminRetailerInventory(RetailerInventoryDTO retailerinventorydto) {
		// TODO Auto-generated method stub
		return false;
	}

}
