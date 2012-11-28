package com.acme.fitness.products.fitty;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.GeneralProductsService;

public class SimpleGeneralProductsService implements GeneralProductsService {

	@Override
	public void addProduct(String name, String details, double price,
			String manufacturer, Date creation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addReviewToClient(Training training) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Training> getAllTrainingByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clientEntry(User client) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Product> getAllProduct() {
		// TODO Auto-generated method stub
		return null;
	}

}
