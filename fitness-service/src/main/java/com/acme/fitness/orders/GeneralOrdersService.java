package com.acme.fitness.orders;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.users.User;

public interface GeneralOrdersService {
	void addProductToOrderItem(User user, Product product, int quantity);
	void addMembership(User user, String membershipType);
	void addTraining(User client, User trainer, Date date);
	void basketCheckOut(Basket basket);
	void deleteBasket(Basket basket);
	List<Basket> getBasketsByClient(User client);
	void basketDelivering(Basket basket);
	void addOrUpdateProductToStore(Product product, int quantity);
}
