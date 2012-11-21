package com.acme.fitness.dao.orders.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.acme.fitness.dao.hibernate.AbstractHibernateGenericDao;
import com.acme.fitness.dao.orders.StoreDao;
import com.acme.fitness.domain.Product;
import com.acme.fitness.domain.Store;

@Repository
public class HibernateStoreDao extends AbstractHibernateGenericDao<Store> implements StoreDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Store> getAllStore() {
		return getSession().createCriteria(Store.class).list();
	}

	
	@Override
	public Store getStoreById(long id) {
		return (Store) getSession().createCriteria(Store.class).add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public Store getStoreByProdctId(long id) {
		return (Store) getSession().createCriteria(Store.class).add(Restrictions.eq("product.id", id)).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Store> getStoreByProductName(String name) {
		
		Criteria crit=getSession().createCriteria(Store.class).createCriteria("product", "product").add(Restrictions.eq("name", name));
		
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Store> getStoreByProductManufacturer(String manufacturer) {
		
		DetachedCriteria detCrit=DetachedCriteria.forClass(Product.class);
		detCrit.setProjection(Property.forName("id")).add(Restrictions.eq("manufacturer", manufacturer));
		
		Criteria crit=getSession().createCriteria(Store.class).add(Property.forName("product").in(detCrit));
		
		return crit.list();
	}

}
