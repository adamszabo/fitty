package com.acme.fitness.orders;

import java.util.Set;

import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;

public interface BasketService {
	Basket newBasket(User client);
	void deleteBasket(Basket basket);
	void updateBasket(Basket basket);
	void addMembershipToBasket(Basket basket, Membership membership);
	void addTrainingToBasket(Basket basket, Training training);
	void addOrderItemToBasket(Basket basket, OrderItem orderItem);
	Set<Membership> getMemberships(Basket basket);
	Set<Training> getTrainings(Basket basket);
	Set<OrderItem> getOrderItems(Basket basket);
	boolean isDelivered(Basket basket);
	void checkOutBasket(Basket basket);
	void deliver(Basket basket);
}
