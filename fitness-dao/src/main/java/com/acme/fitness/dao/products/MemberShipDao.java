package com.acme.fitness.dao.products;

import java.util.List;

import com.acme.fitness.dao.GenericDao;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.users.User;

public interface MembershipDao extends GenericDao<Membership> {
	
	public List<Membership> getAllMemberships();
	
	public Membership getMembershipById(long id);
	
	public List<Membership> getMembershipsByOrder(Basket order);
	
	public List<Membership> getMembershipsByUser(User user);
}
