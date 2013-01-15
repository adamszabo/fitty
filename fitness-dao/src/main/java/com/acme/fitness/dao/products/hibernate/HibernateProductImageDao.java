package com.acme.fitness.dao.products.hibernate;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.acme.fitness.dao.hibernate.AbstractHibernateGenericDao;
import com.acme.fitness.dao.products.ProductImageDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.ProductImage;

@Repository
public class HibernateProductImageDao extends AbstractHibernateGenericDao<ProductImage>
		implements ProductImageDao {


	@SuppressWarnings("unchecked")
	@Override
	public List<ProductImage> getAllProductImages() {
		return getSession().createCriteria(ProductImage.class).list();
	}

	@Override
	public ProductImage getProductImageById(long id) throws FitnessDaoException {
		ProductImage result = (ProductImage) getSession().createCriteria(ProductImage.class).add(Restrictions.eq("id", id)).uniqueResult();
		if (result != null)
			return result;
		else
			throw new FitnessDaoException("ProductImage doesn't found with id:" + id);
	}

}
