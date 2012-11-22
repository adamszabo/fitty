package com.acme.fitness.dao.products;

import java.util.List;

import com.acme.fitness.dao.GenericDao;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.MemberShip;
import com.acme.fitness.domain.users.User;

public interface MemberShipDao extends GenericDao<MemberShip> {
	
	public List<MemberShip> getAllMemberShips();
	
	public MemberShip getMemberShipById(long id);
	
	public List<MemberShip> getMemberShipsByOrder(Basket order);
	
	public List<MemberShip> getMemberShipsByUser(User user);
}
