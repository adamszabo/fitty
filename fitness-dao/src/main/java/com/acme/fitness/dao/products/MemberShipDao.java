package com.acme.fitness.dao.products;

import java.util.List;

import com.acme.fitness.dao.GenericDao;
import com.acme.fitness.domain.MemberShip;
import com.acme.fitness.domain.Order;
import com.acme.fitness.domain.User;

public interface MemberShipDao extends GenericDao<MemberShip> {
	
	public List<MemberShip> getAllMemberShip();
	
	public MemberShip getMemberShipById(long id);
	
	public List<MemberShip> getMemberShipByOrder(Order order);
	
	public List<MemberShip> getMemberShipByUser(User user);
}
