package com.acme.fitness.orders.simple;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.exceptions.StoreQuantityException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.orders.Store;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.Training;
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
	public Basket newBasket(User client) {
		return basketService.newBasket(client);
	}

	@Override
	public void deleteBasket(Basket basket) {
		basketService.deleteBasket(basket);
	}

	@Override
	public void updateBasket(Basket basket) {
		basketService.updateBasket(basket);
	}

	@Override
	public void addMembershipToBasket(Basket basket, Membership membership) {
		basketService.addMembershipToBasket(basket, membership);
	}

	@Override
	public void addTrainingToBasket(Basket basket, Training training) {
		basketService.addTrainingToBasket(basket, training);
	}

	@Override
	public void addOrderItemToBasket(Basket basket, OrderItem orderItem) {
		basketService.addOrderItemToBasket(basket, orderItem);
	}

	@Override
	public Set<Membership> getMemberships(Basket basket) {
		return basketService.getMemberships(basket);
	}

	@Override
	public Set<Training> getTrainings(Basket basket) {
		return basketService.getTrainings(basket);
	}

	@Override
	public Set<OrderItem> getOrderItems(Basket basket) {
		return basketService.getOrderItems(basket);
	}

	@Override
	public void checkOutBasket(Basket basket) throws StoreQuantityException {
		basketService.checkOutBasket(basket);
	}

	@Override
	public void deliver(Basket basket) {
		basketService.deliver(basket);
	}

	@Override
	public Set<Basket> getBasketsByUser(User user) {
		return basketService.getBasketsByUser(user);
	}

	@Override
	public Basket getBasketById(long id) throws FitnessDaoException {
		return basketService.getBasketById(id);
	}

	@Override
	public OrderItem newOrderItem(Product product, int quantity) {
		return orderItemService.newOrderItem(product, quantity);
	}

	@Override
	public void deleteOrderItem(OrderItem orderItem) {
		orderItemService.deleteOrderItem(orderItem);
	}

	@Override
	public void updateOrderItem(OrderItem orderItem) {
		orderItemService.updateOrderItem(orderItem);
	}

	@Override
	public OrderItem getOrderItemById(long id) throws FitnessDaoException {
		return orderItemService.getOrderItemById(id);
	}

	@Override
	public Store addProductToStore(Product product, int quantity) {
		return storeService.addProduct(product, quantity);
	}

	@Override
	public void deleteStore(Store store) {
		storeService.deleteStore(store);
	}

	@Override
	public void updateStore(Store store) {
		storeService.updateStore(store);
	}

	@Override
	public Store getStoreByProduct(Product product) throws FitnessDaoException {
		return storeService.getStoreByProduct(product);
	}

	@Override
	public boolean takeOutProduct(Product product, int quantity)
			throws FitnessDaoException {
		return storeService.takeOutProduct(product, quantity);
	}

	@Override
	public void putInProduct(Product product, int quantity) throws FitnessDaoException {
		storeService.putInProduct(product, quantity);
	}
	
	@Override
	public Set<Store> getAllStores() {
		return storeService.getAllStores();
	}

	public BasketService getBasketService() {
		return basketService;
	}

	public void setBasketService(BasketService basketService) {
		this.basketService = basketService;
	}

	public OrderItemService getOrderItemService() {
		return orderItemService;
	}

	public void setOrderItemService(OrderItemService orderItemService) {
		this.orderItemService = orderItemService;
	}

	public StoreService getStoreService() {
		return storeService;
	}

	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}
	
	
}
