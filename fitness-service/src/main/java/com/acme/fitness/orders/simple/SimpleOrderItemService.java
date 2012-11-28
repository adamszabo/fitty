package com.acme.fitness.orders.simple;

import java.util.Date;

import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.orders.OrderItemService;

public class SimpleOrderItemService implements OrderItemService {

	@Override
	public void addOrderItem(Product product, String details, double price,
			String manufacturer, Date creation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteOrderItem(OrderItem orderItem) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateOrderItem(OrderItem orderItem) {
		// TODO Auto-generated method stub

	}

	@Override
	public OrderItem getOrderItemById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
