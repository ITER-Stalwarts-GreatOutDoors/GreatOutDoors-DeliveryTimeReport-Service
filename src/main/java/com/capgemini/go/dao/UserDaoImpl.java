package com.capgemini.go.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.capgemini.go.dto.UserDTO;
import com.capgemini.go.exception.ExceptionConstants;
import com.capgemini.go.exception.UserException;


public class UserDaoImpl implements UserDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public List<UserDTO> getUserIdList () throws UserException {
		

	
		
		List<UserDTO> result = null;
		Transaction transaction = null;
		Session session = getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<UserDTO> criteriaQuery = builder.createQuery(UserDTO.class);
			Root<UserDTO> userTable = criteriaQuery.from(UserDTO.class);
			criteriaQuery.select(userTable);
			result = session.createQuery(criteriaQuery).getResultList();
			transaction.commit();
		} catch (Exception exp) {
			transaction.rollback();
			//logger.error("getUserIdList - " + ExceptionConstants.INTERNAL_RUNTIME_ERROR);
			throw new UserException ("getUserIdList - " + ExceptionConstants.INTERNAL_RUNTIME_ERROR);
		} finally {
			session.close();
		}
		return result;
	}
}
