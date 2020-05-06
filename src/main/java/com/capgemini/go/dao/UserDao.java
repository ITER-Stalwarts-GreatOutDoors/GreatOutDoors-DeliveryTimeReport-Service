package com.capgemini.go.dao;

import java.util.List;

import com.capgemini.go.dto.UserDTO;
import com.capgemini.go.exception.UserException;

public interface UserDao {
	List<UserDTO> getUserIdList () throws UserException;

}
