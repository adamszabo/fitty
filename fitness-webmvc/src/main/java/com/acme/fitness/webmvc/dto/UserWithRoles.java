package com.acme.fitness.webmvc.dto;

import java.util.List;

import com.acme.fitness.domain.users.User;

public class UserWithRoles {
	
	private User user;
	private List<String> roleNames;
	
	public UserWithRoles() {
		super();
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public List<String> getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(List<String> roleNames) {
		this.roleNames = roleNames;
	}
	
}
