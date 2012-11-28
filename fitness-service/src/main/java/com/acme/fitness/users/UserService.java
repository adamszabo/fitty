package com.acme.fitness.users;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;

public interface UserService {
	void addUser(String fullName, String userName, String password, String email, String mobile, Date registration);
	void deleteUser(User user);
	void updateUser(User user);
	User getUserById(long id);
	User getUserByUserName(String userName);
	List<User> getUsersByFullName(String fullName);
	User getUserByEmail(String email);
	User getUserByMobile(String mobile);
	Date getLastLoginDate(Date loginDate);
	String getLastLoginIp(String lastLoginIp);
	List<Membership> getMembershipsByUser(User user);
	List<Training> getTrainingsByUser(User user);
	void addLastLOginDate(Date lastLoginDate);
	void addLastLoginIp(String lastLoginIp);
	boolean isLoggedIn(User user);
	void setEnabled(User user, boolean toEnabled);
	int getNumberOfEntries(User user);
	void setNumberOfEntries(User user, int numberOfEntries);
}
