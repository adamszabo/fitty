package com.acme.fitness.orders;

import java.util.List;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.exceptions.StoreQuantityException;
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
	List<Membership> getMemberships(Basket basket);
	List<Training> getTrainings(Basket basket);
	List<OrderItem> getOrderItems(Basket basket);
	void checkOutBasket(Basket basket) throws StoreQuantityException;
	void deliver(Basket basket);
	List<Basket> getBasketsByUser(User user);
	Basket getBasketById(long id) throws FitnessDaoException;
}
