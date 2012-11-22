package com.acme.fitness.dao.orders;

import java.util.List;

import com.acme.fitness.dao.GenericDao;
import com.acme.fitness.domain.orders.OrderItem;

public interface OrderItemDao extends GenericDao<OrderItem> {

	public List<OrderItem> getAllOrderItems();
	
	public OrderItem getOrderItemById(long id);
	
}
