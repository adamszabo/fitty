package com.acme.fitness.dao.orders.hibernate;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.acme.fitness.dao.hibernate.AbstractHibernateGenericDao;
import com.acme.fitness.dao.orders.BasketDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.users.User;

@Repository
public class HibernateBasketDao extends AbstractHibernateGenericDao<Basket> implements BasketDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Basket> getAllBaskets() {
		//List<Order> result=getSession().createCriteria(Order.class).setFetchMode("orderItems", FetchMode.JOIN).list();
		List<Basket> result=getSession().createCriteria(Basket.class).list();
		return result;
	}

	@Override
	public Basket getBasketById(long id) throws FitnessDaoException {
		Basket result = (Basket) getSession().createCriteria(Basket.class).add(Restrictions.eq("id", id)).uniqueResult();
		if(result!=null)
			return result;
		else
			throw new FitnessDaoException("Doesn't have Basket with id: "+id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Basket> getBasketsByUser(User user) {
		return getSession().createCriteria(Basket.class).add(Restrictions.eq("user", user)).list();
	}

}
