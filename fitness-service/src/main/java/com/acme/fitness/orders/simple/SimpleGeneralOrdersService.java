package com.acme.fitness.orders.simple;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.orders.GeneralOrdersService;

public class SimpleGeneralOrdersService implements GeneralOrdersService {

	@Override
	public void addProductToOrderItem(User user, Product product, int quantity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addMembership(User user, String membershipType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTraining(User client, User trainer, Date date) {
		// TODO Auto-generated method stub

	}

	@Override
	public void basketCheckOut(Basket basket) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteBasket(Basket basket) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Basket> getBasketsByClient(User client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void basketDelivering(Basket basket) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addOrUpdateProductToStore(Product product, int quantity) {
		// TODO Auto-generated method stub

	}

}
