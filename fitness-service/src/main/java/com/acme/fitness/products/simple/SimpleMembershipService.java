package com.acme.fitness.products.simple;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.fitness.dao.products.MembershipDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.MembershipService;

@Service
public class SimpleMembershipService implements MembershipService {
	
	private MembershipDao membershipDao;
	
	@Autowired
	public SimpleMembershipService(MembershipDao membershipDao){
		this.membershipDao=membershipDao;
	}
	
	@Override
	public Membership newMemberShip(String type, int maxEntries, Date expireDate, double price) {
		Membership membership=new Membership(type, 0, maxEntries, expireDate, price, null);
		return membership;
	}
	
	@Override
	public Membership saveMemberShip(Basket basket, Membership membership) {
		membership.setBasket(basket);
		membershipDao.save(membership);
		return membership;
	}
	
	@Override
	public Membership saveNewMemberShip(Basket basket, String type, int maxEntries, Date expireDate, double price) {
		Membership membership=new Membership(type, 0, maxEntries, expireDate, price, basket);
		membershipDao.save(membership);
		return membership;
	}

	@Override
	public void deleteMembership(Membership membership) {
		membershipDao.delete(membership);
	}

	@Override
	public void updateMembership(Membership membership) {
		membershipDao.update(membership);
	}

	@Override
	public boolean isValid(Membership membership) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Membership getMembershipById(long id) throws FitnessDaoException {
		return membershipDao.getMembershipById(id);
	}

	@Override
	public List<Membership> getMembershipByBasket(Basket basket) {
		return membershipDao.getMembershipsByOrder(basket);
	}

	@Override
	public List<Membership> getMembershipByUser(User user) {
		return membershipDao.getMembershipsByUser(user);
	}

	@Override
	public void increaseClientEntries(Membership membership) {
		membership.setNumberOfEntries(membership.getNumberOfEntries()+1);
		membershipDao.update(membership);
	}
}
