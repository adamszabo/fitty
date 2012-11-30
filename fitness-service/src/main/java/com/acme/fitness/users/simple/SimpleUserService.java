package com.acme.fitness.users.simple;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acme.fitness.dao.users.UserDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.users.UserService;

@Component
public class SimpleUserService implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public User addUser(String fullName, String userName, String password, String email, String mobile, Date registration) {
		User user=new User(fullName, userName, password, email, mobile, registration, null, null);
		userDao.save(user);
		return user;
	}

	@Override
	public void deleteUser(User user) {
		userDao.delete(user);
	}

	@Override
	public void updateUser(User user) {
		userDao.update(user);
	}

	@Override
	public User getUserById(long id) throws FitnessDaoException {
		return userDao.getUserById(id);
	}

	@Override
	public User getUserByUserName(String username) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsersByFullName(String fullName) {
		return userDao.getUsersByFullName(fullName);
	}

	@Override
	public void addLastLoginDate(User user, Date lastLoginDate) {
		user.setLastLogin(lastLoginDate);
		userDao.update(user);
	}

	@Override
	public void addLastLoginIp(User user, String lastLoginIp) {
		user.setLastLoginIp(lastLoginIp);
		userDao.update(user);
	}

	@Override
	public boolean isLoggedIn(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setEnabled(User user, boolean toEnabled) {
		user.setEnabled(toEnabled);
		userDao.update(user);
	}

	@Override
	public void setNumberOfEntries(User user, int numberOfEntries) {
		user.setNumberOfRetries(numberOfEntries);
		userDao.update(user);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	

}
