package com.acme.fitness.orders;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.products.Product;

public interface OrderItemService {
	OrderItem newOrderItem(Product product, int quantity);
	OrderItem saveOrderItem(Basket basket, OrderItem orderItem);
	OrderItem saveNewOrderItem(Product product, int quantity, Basket basket);
	void deleteOrderItem(OrderItem orderItem);
	void updateOrderItem(OrderItem orderItem);
	OrderItem getOrderItemById(long id) throws FitnessDaoException;
}
