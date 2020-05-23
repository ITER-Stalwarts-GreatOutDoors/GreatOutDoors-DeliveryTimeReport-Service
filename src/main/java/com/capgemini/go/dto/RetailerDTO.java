package com.capgemini.go.dto;

import java.util.ArrayList;
import java.util.List;

public class RetailerDTO {

	private List<UserDTO> users = new ArrayList<UserDTO>();

	//Getters and Setters
	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}
	
}
