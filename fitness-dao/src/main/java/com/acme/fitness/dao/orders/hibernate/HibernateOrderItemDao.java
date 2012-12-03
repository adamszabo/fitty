package com.acme.fitness.dao.orders.hibernate;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.acme.fitness.dao.hibernate.AbstractHibernateGenericDao;
import com.acme.fitness.dao.orders.OrderItemDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.OrderItem;

@Repository
public class HibernateOrderItemDao extends AbstractHibernateGenericDao<OrderItem> implements OrderItemDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItem> getAllOrderItems() {
		return getSession().createCriteria(OrderItem.class).list();
	}

	@Override
	public OrderItem getOrderItemById(long id) throws FitnessDaoException {
		OrderItem result=(OrderItem) getSession().createCriteria(OrderItem.class).add(Restrictions.eq("id", id)).uniqueResult();
		if(result!=null)
			return result;
		else
			throw new FitnessDaoException("Doesn't have OrderItem with id: "+id);
	}

}
