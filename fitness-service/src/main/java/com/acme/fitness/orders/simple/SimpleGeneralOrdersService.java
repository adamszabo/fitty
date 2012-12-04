package com.acme.fitness.orders.simple;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.orders.BasketService;
import com.acme.fitness.orders.GeneralOrdersService;
import com.acme.fitness.orders.OrderItemService;
import com.acme.fitness.orders.StoreService;

@Service
public class SimpleGeneralOrdersService implements GeneralOrdersService {

	@Autowired
	private BasketService basketService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private StoreService storeService;
	
	@Override
	public void addProductToOrderItem(User user, Product product, int quantity) {
		
	}

	@Override
	public void addMembership(User user, String membershipType) {

	}

	@Override
	public void addTraining(User client, User trainer, Date date) {
		// TODO Auto-generated method stub

	}

	@Override
	public void basketCheckOut(Basket basket) {
		
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
