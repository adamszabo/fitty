package com.acme.fitness.products;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.ProductImage;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;

public interface GeneralProductsService {
	Product addProduct(String name, String details, double price, String manufacturer, Date creation, ProductImage productImage);
	void deleteProduct(Product product);
	void updateProduct(Product product);
	List<Product> getAllProduct();
	List<Product> getProductsByName(String name);
	List<Product> getProductsByManufacturer(String manufacturer);
	List<Product> getProductsByPriceInterval(double fromPrice, double toPrice);
	
	Training newTraining(User trainer, User client, Date date);
	void deleteTraining(Training training);
	void updateTraining(Training training);
	void recordTrainingResults(Training training, int burnedCalories, String review);
	List<Training> getTrainingsByTrainer(User trainer);
	List<Training> getTrainingsByClient(User client);
	List<Training> getTrainingsByBasket(Basket basket);
	
	Membership newMemberShip(String type, int maxEntries, Date expireDate, double price);
	void deleteMembership(Membership membership);
	void updateMembership(Membership membership);
	boolean isValid(Membership membership);
	Membership getMembershipById(long id) throws FitnessDaoException;
	List<Membership> getMembershipByBasket(Basket basket);
	List<Membership> getMembershipByUser(User user);
	void increaseClientEntries(Membership membership);
	Product getProductById(long id) throws FitnessDaoException;
}
