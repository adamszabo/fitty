package com.acme.fitness.dao.orders;

import java.util.List;

import com.acme.fitness.dao.GenericDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.users.User;

public interface BasketDao extends GenericDao<Basket> {
	
	public List<Basket> getAllBaskets();
	
	public Basket getBasketById(long id) throws FitnessDaoException;
	
	public List<Basket> getBasketsByUser(User user);
	
	public List<Basket> getBasketsByUserAndDeliveredStatus(User user, boolean isDelviered);
	
}
