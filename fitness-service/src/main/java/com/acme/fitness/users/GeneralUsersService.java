package com.acme.fitness.users;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;

public interface GeneralUsersService {
	void addUser(String fullName, String password, String email, String mobile, Date registration);
	boolean isLoginValidByUser(String userName, String ipAddress);
	List<User> getAllUser();
	void addUserRole(String roleName, User user);
	void removeUserRole(String roleName, User user);
}
