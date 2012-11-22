package com.acme.fitness.dao.users;

import java.util.List;

import com.acme.fitness.dao.GenericDao;
import com.acme.fitness.domain.users.User;

public interface UserDao extends GenericDao<User> {
	
	public List<User> getAllUsers();
	
	public User getUserById(long id);
	
}
