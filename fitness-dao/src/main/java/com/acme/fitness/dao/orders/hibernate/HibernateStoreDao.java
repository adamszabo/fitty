package com.acme.fitness.dao.orders.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.acme.fitness.dao.hibernate.AbstractHibernateGenericDao;
import com.acme.fitness.dao.orders.StoreDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Store;
import com.acme.fitness.domain.products.Product;

@Repository
public class HibernateStoreDao extends AbstractHibernateGenericDao<Store>
		implements StoreDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Store> getAllStores() {
		return getSession().createCriteria(Store.class).list();
	}

	@Override
	public Store getStoreById(long id) throws FitnessDaoException {
		Store result = (Store) getSession().createCriteria(Store.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
		if (result != null)
			return result;
		else
			throw new FitnessDaoException("Store doesn't found with id:" + id);
	}

	@Override
	public Store getStoreByProductId(long id) throws FitnessDaoException {
		Store result = (Store) getSession().createCriteria(Store.class)
				.add(Restrictions.eq("product.id", id)).uniqueResult();
		if (result != null)
			return result;
		else
			throw new FitnessDaoException("Product doesn't found with Product id:" + id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Store> getStoresByProductName(String name) {

		Criteria crit = getSession().createCriteria(Store.class)
				.createCriteria("product", "product")
				.add(Restrictions.eq("name", name));

		return crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Store> getStoresByProductManufacturer(String manufacturer) {

		DetachedCriteria detCrit = DetachedCriteria.forClass(Product.class);
		detCrit.setProjection(Property.forName("id")).add(
				Restrictions.eq("manufacturer", manufacturer));

		Criteria crit = getSession().createCriteria(Store.class).add(
				Property.forName("product").in(detCrit));

		return crit.list();
	}

}
