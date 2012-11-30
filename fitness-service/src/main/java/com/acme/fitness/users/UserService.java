package com.acme.fitness.users;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.users.User;

public interface UserService {
	User addUser(String fullName, String userName, String password, String email, String mobile, Date registration);
	void deleteUser(User user);
	void updateUser(User user);
	User getUserById(long id) throws FitnessDaoException;
	User getUserByUserName(String username);
	List<User> getUsersByFullName(String fullName);
	User getUserByEmail(String email);
	void addLastLoginDate(User user, Date lastLoginDate);
	void addLastLoginIp(User user, String lastLoginIp);
	boolean isLoggedIn(User user);
	void setEnabled(User user, boolean toEnabled);
	void setNumberOfEntries(User user, int numberOfEntries);
}
