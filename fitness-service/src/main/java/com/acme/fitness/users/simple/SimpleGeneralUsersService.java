package com.acme.fitness.users.simple;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.users.User;
import com.acme.fitness.users.GeneralUsersService;

public class SimpleGeneralUsersService implements GeneralUsersService {

	@Override
	public User addUser(String fullName, String password, String email,
			String mobile, Date registration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isLoginValidByUser(String userName, String ipAddress) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUserRole(String roleName, User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUserRole(String roleName, User user) {
		// TODO Auto-generated method stub
		
	}

}
