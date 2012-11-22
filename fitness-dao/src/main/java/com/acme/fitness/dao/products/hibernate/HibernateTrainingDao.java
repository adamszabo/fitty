package com.acme.fitness.dao.products.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.acme.fitness.dao.hibernate.AbstractHibernateGenericDao;
import com.acme.fitness.dao.products.TrainingDao;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;

@Repository
public class HibernateTrainingDao extends AbstractHibernateGenericDao<Training> implements TrainingDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Training> getAllTraining() {
		return getSession().createCriteria(Training.class).list();
	}

	@Override
	public Training getTrainingById(long id) {
		return (Training) getSession().createCriteria(Training.class).add(Restrictions.eq("id", id)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Training> getTrainingByTrainer(User trainer) {
		return getSession().createCriteria(Training.class).add(Restrictions.eq("trainer", trainer)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Training> getTrainingByClient(User client) {
		return getSession().createCriteria(Training.class).add(Restrictions.eq("client", client)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Training> getTrainingByOrder(Basket basket) {
		return getSession().createCriteria(Training.class).add(Restrictions.eq("basket", basket)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Training> getTrainingAfterDate(Date date) {
		return getSession().createCriteria(Training.class).add(Restrictions.gt("trainingStartDate", date)).list();
	}

}
