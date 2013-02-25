package com.acme.fitness.dao.products.hibernate;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.acme.fitness.dao.hibernate.AbstractHibernateGenericDao;
import com.acme.fitness.dao.products.TrainingTypeDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.TrainingType;
import com.acme.fitness.domain.users.User;

@Repository
public class HibernateTrainingTypeDao extends AbstractHibernateGenericDao<TrainingType> implements TrainingTypeDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<TrainingType> getAllTrainingTypes() {
		return getSession().createCriteria(TrainingType.class).list();
	}

	@Override
	public TrainingType getTrainingTypeById(long id) throws FitnessDaoException {
		TrainingType result = (TrainingType) getSession().createCriteria(TrainingType.class).add(Restrictions.eq("id", id)).uniqueResult();
		if(result == null) {
			throw new FitnessDaoException("TrainingType doesn't found with id: " +id);
		}
		return result;
	}

	@Override
	public TrainingType getTrainingTypeByTrainer(User trainer) throws FitnessDaoException {
		TrainingType result = (TrainingType) getSession().createCriteria(TrainingType.class).add(Restrictions.eq("trainer", trainer)).uniqueResult();
		if(result == null) {
			throw new FitnessDaoException("TrainingType doesn't found by trainer id : " +trainer.getId());
		}
		return result;
	}

}
