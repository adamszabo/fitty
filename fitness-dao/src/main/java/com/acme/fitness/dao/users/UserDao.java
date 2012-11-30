package com.acme.fitness.dao.users;

import java.util.List;

import com.acme.fitness.dao.GenericDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.users.User;

public interface UserDao extends GenericDao<User> {
	
	public List<User> getAllUsers();
	
	public User getUserById(long id) throws FitnessDaoException;
	
	public List<User> getUsersByFullName(String fullName);
	
}
