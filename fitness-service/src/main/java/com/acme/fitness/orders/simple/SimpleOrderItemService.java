package com.acme.fitness.orders.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.fitness.dao.orders.OrderItemDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.orders.OrderItemService;

@Service
public class SimpleOrderItemService implements OrderItemService {
	
	@Autowired
	private OrderItemDao orderItemDao;

	@Override
	public OrderItem addOrderItem(Product product, int quantity, Basket basket) {
		OrderItem orderItem=new OrderItem(product, quantity, basket);
		orderItemDao.save(orderItem);
		return orderItem;
	}

	@Override
	public void deleteOrderItem(OrderItem orderItem) {
		orderItemDao.delete(orderItem);
	}

	@Override
	public void updateOrderItem(OrderItem orderItem) {
		orderItemDao.update(orderItem);
	}

	@Override
	public OrderItem getOrderItemById(long id) throws FitnessDaoException {
		return orderItemDao.getOrderItemById(id);
	}

	public OrderItemDao getOrderItemDao() {
		return orderItemDao;
	}

	public void setOrderItemDao(OrderItemDao orderItemDao) {
		this.orderItemDao = orderItemDao;
	}
	
	
}
