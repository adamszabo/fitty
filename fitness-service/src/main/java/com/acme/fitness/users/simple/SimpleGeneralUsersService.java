package com.acme.fitness.users.simple;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.users.Role;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.users.GeneralUsersService;
import com.acme.fitness.users.RoleService;
import com.acme.fitness.users.UserService;

@Service
public class SimpleGeneralUsersService implements GeneralUsersService {
	
	private UserService userService;
	private RoleService roleService;
	
	@Autowired
	public SimpleGeneralUsersService(RoleService roleService, UserService userService){
		this.roleService=roleService;
		this.userService=userService;
	}
	
	@Override
	public User addUser(String fullName, String username, String password, String email, String mobile, Date registration) {
		return userService.addUser(fullName, username, password, email, mobile, registration);
	}

	@Override
	public boolean isLoginValidByUser(String userName, String ipAddress, boolean isLoggedIn) throws FitnessDaoException {
		User user=userService.getUserByUserName(userName);
		if(isLoggedIn){
			if(user.getLastLoginIp().equals(ipAddress))
				return true;
			else
				return false;
		}
		else
			return true;
	}

	@Override
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@Override
	public void addUserRole(String roleName, User user) {
		roleService.addRoleToUser(roleName, user);
	}

	@Override
	public void removeUserRole(String roleName, User user) {
		roleService.removeRoleFromUser(roleName, user);
	}

	@Override
	public void addLastLoginInfo(User user, String lastIp, Date lastLoginDate) {
		userService.addLastLoginDate(user, lastLoginDate);
		userService.addLastLoginIp(user, lastIp);
	}
	
	@Override
	public User getUserByUsername(String username) throws FitnessDaoException {
		return userService.getUserByUserName(username);
	}
	
	@Override
	public User getUserByEmail(String email) throws FitnessDaoException {
		return userService.getUserByEmail(email);
	}

	@Override
	public void deleteUser(User user) {
		userService.deleteUser(user);
	}
	
	@Override
	public void updateUser(User user) {
		userService.updateUser(user);
	}

	@Override
	public List<Role> getRolesbyUser(User user) {
		return roleService.getRolesByUser(user);
	}

	@Override
	public User getUserById(long id) throws FitnessDaoException {
		return userService.getUserById(id);
	}

	@Override
	public List<User> getAllTrainers() {
		return userService.getAllTrainers();
	}

	@Override
	public List<User> getUsersByStringParamter(String paramName, String value) {
		return userService.getUsersByStringParamter(paramName, value);
	}
	
}
