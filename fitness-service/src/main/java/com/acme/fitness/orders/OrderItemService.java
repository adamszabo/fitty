package com.acme.fitness.orders;

import java.util.Date;

import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.products.Product;

public interface OrderItemService {
	void addOrderItem(Product product, String details, double price, String manufacturer, Date creation);
	void deleteOrderItem(OrderItem orderItem);
	void updateOrderItem(OrderItem orderItem);
	OrderItem getOrderItemById(long id);
}
