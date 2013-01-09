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
	
	private OrderItemDao orderItemDao;
	
	@Autowired 
	public SimpleOrderItemService(OrderItemDao orderItemDao){
		this.orderItemDao=orderItemDao;
	}

	@Override
	public OrderItem newOrderItem(Product product, int quantity) {
		OrderItem orderItem=new OrderItem(product, quantity, null);
		return orderItem;
	}
	
	@Override
	public OrderItem saveOrderItem(Basket basket, OrderItem orderItem) {
		orderItem.setBasket(basket);
		orderItemDao.save(orderItem);
		return orderItem;
	}
	
	@Override
	public OrderItem saveNewOrderItem(Product product, int quantity, Basket basket) {
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
}
