package com.acme.fitness.products;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.users.User;

public interface MembershipService {
	
	Membership saveNewMemberShip(boolean isIntervally, Basket basket, String type, int maxEntries, Date startDate, Date expireDate, double price);

	Membership newMemberShip(boolean isIntervally, String type, int maxEntries, Date startDate, Date expireDate, double price);
	
	Membership saveMemberShip(Basket basket, Membership membership);

	void deleteMembership(Membership membership);

	void updateMembership(Membership membership);

	boolean isValid(Membership membership, Date date);

	Membership getMembershipById(long id) throws FitnessDaoException;

	List<Membership> getMembershipByBasket(Basket basket);

	List<Membership> getMembershipByUser(User user);

	void increaseClientEntries(Membership membership);

	List<Membership> getValidMembershipsByUser(User user, Date date);

}
