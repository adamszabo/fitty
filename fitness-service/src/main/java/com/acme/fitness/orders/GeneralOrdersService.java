package com.acme.fitness.orders;

import java.util.Set;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.exceptions.StoreQuantityException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.orders.Store;
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
	Set<Membership> getMemberships(Basket basket);
	Set<Training> getTrainings(Basket basket);
	Set<OrderItem> getOrderItems(Basket basket);
	void checkOutBasket(Basket basket) throws StoreQuantityException;
	void deliver(Basket basket);
	Set<Basket> getBasketsByUser(User user);
	Basket getBasketById(long id) throws FitnessDaoException;
	
	OrderItem newOrderItem(Product product, int quantity);
	void deleteOrderItem(OrderItem orderItem);
	void updateOrderItem(OrderItem orderItem);
	OrderItem getOrderItemById(long id) throws FitnessDaoException;
	
	Store addProductToStore(Product product, int quantity);
	void deleteStore(Store store);
	void updateStore(Store store);
	Store getStoreByProduct(Product product) throws FitnessDaoException;
	boolean takeOutProduct(Product product, int quantity) throws FitnessDaoException;
	void putInProduct(Product product, int quantity) throws FitnessDaoException;
	
}