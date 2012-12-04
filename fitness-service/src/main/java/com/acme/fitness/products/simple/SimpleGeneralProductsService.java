package com.acme.fitness.products.simple;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.GeneralProductsService;

public class SimpleGeneralProductsService implements GeneralProductsService {

	@Override
	public Product addProduct(String name, String details, double price,
			String manufacturer, Date creation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Product> getAllProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsByManufacturer(String manufacturer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsByPriceInterval(double fromPrice,
			double toPrice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Training newTraining(User trainer, User client, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteTraining(Training training) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTraining(Training training) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addReviewToClient(Training training) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recordTrainingResults(Training training, int burnedCalories,
			String review) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Training> getTrainingsByTrainer(User trainer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Training> getTrainingsByClient(User client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Training> getTrainingsByBasket(Basket basket) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Membership newMemberShip(String type, int maxEntries,
			Date expireDate, double price) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteMembership(Membership membership) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMembership(Membership membership) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(Membership membership) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Membership getMembershipById(long id) throws FitnessDaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Membership> getMembershipByBasket(Basket basket) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Membership> getMembershipByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void increaseClientEntries(Membership membership) {
		// TODO Auto-generated method stub
		
	}


}
