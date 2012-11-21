package com.acme.fitness.dao.orders;

import java.util.List;

import com.acme.fitness.dao.GenericDao;
import com.acme.fitness.domain.OrderItem;

public interface OrderItemDao extends GenericDao<OrderItem> {

	public List<OrderItem> getAllOrderItem();
	
	public OrderItem getOrderItemById(long id);
	
}
