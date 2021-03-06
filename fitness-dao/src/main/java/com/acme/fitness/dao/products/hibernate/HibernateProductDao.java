package com.acme.fitness.dao.products.hibernate;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.acme.fitness.dao.hibernate.AbstractHibernateGenericDao;
import com.acme.fitness.dao.products.ProductDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.Product;

@Repository
public class HibernateProductDao extends AbstractHibernateGenericDao<Product>	implements ProductDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getAllProduct() {
		return getSession().createCriteria(Product.class).list();
	}

	@Override
	public Product getProductById(long id) throws FitnessDaoException {
		Product result = (Product) getSession().createCriteria(Product.class).add(Restrictions.eq("id", id)).uniqueResult();
		if(result!=null)
			return result;
		else
			throw new FitnessDaoException("Product doesn't found with id:"+id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProductsByName(String name) {
		return getSession().createCriteria(Product.class).add(Restrictions.eq("name", name)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProductsByManufacturer(String manufacturer) {
		return getSession().createCriteria(Product.class).add(Restrictions.eq("manufacturer", manufacturer)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProductsByPriceInterval(double fromPrice,
			double toPrice) {
		return getSession().createCriteria(Product.class).add(Restrictions.between("price", fromPrice, toPrice)).list();
		
	}

}
