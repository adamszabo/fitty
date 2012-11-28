package com.acme.fitness.orders.fitty;

import java.util.List;

import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.orders.BasketService;

public class SimpleBasketService implements BasketService {

	@Override
	public void newBasket(User client) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteBasket(Basket basket) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBasket(Basket basket) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addMembershipToBasket(Basket basket, Membership membership) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTrainingToBasket(Basket basket, Training training) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addOrderItemToBasket(Basket basket, OrderItem orderItem) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Membership> getMemberships(Basket basket) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Training> getTrainings(Basket basket) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProducts(Basket basket) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDelivered(Basket basket) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void checkOutBasket(Basket basket) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deliver(Basket basket) {
		// TODO Auto-generated method stub

	}

}
