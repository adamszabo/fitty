package com.acme.fitness.products;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.users.User;

public interface MembershipService {
	void addMemberShip(Basket basket, String type, int maxEntries, Date expireDate, double price);
	void deleteMembership(Membership membership);
	void updateMembership(Membership membership);
	boolean isValid(Membership membership);
	int getPrice(Membership membership);
	int getNumberOfEntries(Membership membership);
	Membership getMembershipById(long id);
	List<Membership> getMembershipByBasket(Basket basket);
	List<Membership> getMembershipByUser(User user);
	void increaseClientEntries(User user);
}
