package com.acme.fitness.products.fitty;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.MembershipService;

public class SimpleMembershipService implements MembershipService {

	@Override
	public void addMemberShip(Basket basket, String type, int maxEntries,
			Date expireDate, double price) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMembership(Membership membership) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateMembership(Membership membership) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid(Membership membership) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPrice(Membership membership) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfEntries(Membership membership) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Membership getMembershipById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Membership> getMembershipByBasket(Basket basket) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Membership> getMembershipByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void increaseClientEntries(User user) {
		// TODO Auto-generated method stub

	}

}
