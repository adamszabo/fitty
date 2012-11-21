package com.acme.fitness.dao.users;

import java.util.List;

import com.acme.fitness.dao.GenericDao;
import com.acme.fitness.domain.User;

public interface UserDao extends GenericDao<User> {
	
	public List<User> getAllUser();
	
	public User getUserById(long id);
	
}
