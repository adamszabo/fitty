package com.acme.fitness.products;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;

public interface GeneralProductsService {
	Product addProduct(String name, String details, double price, String manufacturer, Date creation);
	void addReviewToClient(Training training);
	List<Training> getAllTrainingByUser(User user);
	void clientEntry(User client);
	List<Product> getAllProduct();
}
