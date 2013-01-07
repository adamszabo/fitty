package com.acme.fitness.users;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.users.Role;
import com.acme.fitness.domain.users.User;

public interface GeneralUsersService {
	User addUser(String fullName, String username, String password, String email, String mobile, Date registration);
	void deleteUser(User user);
	void updateUser(User user);
	List<User> getAllUsers();
	User getUserById(long id) throws FitnessDaoException;
	User getUserByUsername(String username) throws FitnessDaoException;
	User getUserByEmail(String email) throws FitnessDaoException;
	boolean isLoginValidByUser(String userName, String ipAddress) throws FitnessDaoException;
	void addUserRole(String roleName, User user);
	void removeUserRole(String roleName, User user);
	void addLastLoginInfo(User user, String lastIp, Date lastLoginDate);
	List<Role> getRolesbyUser(User user);
	List<User> getAllTrainers();
}
