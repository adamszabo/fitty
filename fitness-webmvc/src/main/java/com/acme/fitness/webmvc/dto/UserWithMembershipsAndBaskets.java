package com.acme.fitness.webmvc.dto;

import java.util.ArrayList;
import java.util.List;

import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.users.User;

public class UserWithMembershipsAndBaskets {
	private User user;
	private List<Membership> memberships;
	private List<Basket> baskets;
	
	public UserWithMembershipsAndBaskets(){
		super();
		memberships=new ArrayList<Membership>();
		baskets=new ArrayList<Basket>();
	}
	
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
	public List<Basket> getBaskets() {
		return baskets;
	}

	public void setBaskets(List<Basket> baskets) {
		this.baskets = baskets;
	}
}
