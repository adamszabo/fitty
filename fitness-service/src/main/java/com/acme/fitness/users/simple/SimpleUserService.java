package com.acme.fitness.users.simple;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acme.fitness.dao.users.UserDao;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.Training;
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
	public User getUserById(long id) {
		return userDao.getUserById(id);
	}

	@Override
	public User getUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsersByFullName(String fullName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByMobile(String mobile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getLastLoginDate(Date loginDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLastLoginIp(String lastLoginIp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Membership> getMembershipsByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Training> getTrainingsByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addLastLOginDate(Date lastLoginDate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addLastLoginIp(String lastLoginIp) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLoggedIn(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setEnabled(User user, boolean toEnabled) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getNumberOfEntries(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setNumberOfEntries(User user, int numberOfEntries) {
		// TODO Auto-generated method stub

	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	

}
