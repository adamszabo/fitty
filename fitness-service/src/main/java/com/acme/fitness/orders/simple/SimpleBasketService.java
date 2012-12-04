package com.acme.fitness.orders.simple;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.fitness.dao.orders.BasketDao;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.orders.BasketService;
import com.acme.fitness.orders.OrderItemService;
import com.acme.fitness.products.MembershipService;
import com.acme.fitness.products.TrainingService;

@Service
public class SimpleBasketService implements BasketService {

	@Autowired
	private BasketDao basketDao;
	
	@Autowired
	private MembershipService membershipService;
	
	@Autowired 
	private TrainingService trainingService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Override
	public Basket newBasket(User client) {
		Basket basket = new Basket(false, client);
		return basket;
	}

	@Override
	public void deleteBasket(Basket basket) {
		basketDao.delete(basket);
	}

	@Override
	public void updateBasket(Basket basket) {
		basketDao.update(basket);
	}

	@Override
	public void addMembershipToBasket(Basket basket, Membership membership) {
		basket.addMembership(membership);
	}

	@Override
	public void addTrainingToBasket(Basket basket, Training training) {
		basket.addTraining(training);
	}

	@Override
	public void addOrderItemToBasket(Basket basket, OrderItem orderItem) {
		basket.addOrderItem(orderItem);
	}

	@Override
	public Set<Membership> getMemberships(Basket basket) {
		return basket.getMemberships();
	}

	@Override
	public Set<Training> getTrainings(Basket basket) {
		return basket.getTrainings();
	}

	@Override
	public Set<OrderItem> getOrderItems(Basket basket) {
		return basket.getOrderItems();
	}

	@Override
	public boolean isDelivered(Basket basket) {
		return basket.isDelivered();
	}

	@Override
	public void checkOutBasket(Basket basket) {
		basketDao.save(basket);
		for(Membership m : basket.getMemberships()) {
			membershipService.saveMemberShip(basket, m);
		}
		for(Training t : basket.getTrainings()) {
			trainingService.saveTraining(basket, t);
		}
		for(OrderItem o : basket.getOrderItems()) {
			orderItemService.saveOrderItem(basket, o);
		}
	}

	@Override
	public void deliver(Basket basket) {
		basket.setDelivered(true);
		basketDao.update(basket);
	}

	public BasketDao getBasketDao() {
		return basketDao;
	}

	public void setBasketDao(BasketDao basketDao) {
		this.basketDao = basketDao;
	}
}
