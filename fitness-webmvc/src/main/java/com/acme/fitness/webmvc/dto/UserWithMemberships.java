package com.acme.fitness.webmvc.dto;

import java.util.List;

import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.users.User;

public class UserWithMemberships {
	private User user;
	private List<Membership> memberships;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Membership> getMemberships() {
		return memberships;
	}
	public void setMemberships(List<Membership> memberships) {
		this.memberships = memberships;
	}
}
