package com.acme.fitness.products;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.MemberShip;
import com.acme.fitness.domain.users.User;

public interface MembershipService {
	void addMemberShip(Basket basket, String type, int maxEntries, Date expireDate, double price);
	void deleteMemberShip(MemberShip memberShip);
	void updateMemberShip(MemberShip memberShip);
	boolean isValid(MemberShip memberShip);
	int getPrice(MemberShip memberShip);
	int getNumberOfEntries(MemberShip memberShip);
	MemberShip getMembershipById(long id);
	List<MemberShip> getMembershipByBasket(Basket basket);
	List<MemberShip> getMembershipByUser(User user);
	void increaseClientEntries(User user);
}
