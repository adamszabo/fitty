package com.acme.fitness.users;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.users.User;

public interface UserService {
	User addUser(String fullName, String userName, String password, String email, String mobile, Date registration);
	void deleteUser(User user);
	List<User> getAllUsers();
	void updateUser(User user);
	User getUserById(long id) throws FitnessDaoException;
	User getUserByUserName(String username) throws FitnessDaoException;
	List<User> getUsersByFullName(String fullName);
	User getUserByEmail(String email) throws FitnessDaoException;
	void addLastLoginDate(User user, Date lastLoginDate);
	void addLastLoginIp(User user, String lastLoginIp);
	void setEnabled(User user, boolean toEnabled);
	void setNumberOfEntries(User user, int numberOfEntries);
	List<User> getAllTrainers();
	List<User> getUsersByStringParamter(String paramName, String value);
}
