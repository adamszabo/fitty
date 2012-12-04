package com.acme.fitness.orders;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;

public interface GeneralOrdersService {
	Basket newBasket(User client);
	void deleteBasket(Basket basket);
	void updateBasket(Basket basket);
	void addMembershipToBasket(Basket basket, Membership membership);
	void addTrainingToBasket(Basket basket, Training training);
	void addOrderItemToBasket(Basket basket, OrderItem orderItem);
	
	
	
	void addProductToOrderItem(Basket basket, Product product, int quantity);
	void addMembership(User user, String membershipType);
	void addTraining(User client, User trainer, Date date);
	void basketCheckOut(Basket basket);
	List<Basket> getBasketsByClient(User client);
	void basketDelivering(Basket basket);
	void addOrUpdateProductToStore(Product product, int quantity);
}
