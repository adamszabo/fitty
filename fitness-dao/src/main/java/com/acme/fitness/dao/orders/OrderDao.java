package com.acme.fitness.dao.orders;

import java.util.List;

import com.acme.fitness.dao.GenericDao;
import com.acme.fitness.domain.Order;
import com.acme.fitness.domain.User;

public interface OrderDao extends GenericDao<Order> {
	
	public List<Order> getAllOrder();
	
	public Order getOrderById(long id);
	
	public List<Order> getOrderByUser(User user);
}
