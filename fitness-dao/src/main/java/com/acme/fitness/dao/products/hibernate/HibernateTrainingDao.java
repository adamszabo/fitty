package com.acme.fitness.dao.products.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.acme.fitness.dao.hibernate.AbstractHibernateGenericDao;
import com.acme.fitness.dao.products.TrainingDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;

@Repository
public class HibernateTrainingDao extends AbstractHibernateGenericDao<Training>
		implements TrainingDao {

	private static long ONE_DAY = 1000*60*60*24;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Training> getAllTraining() {
		return getSession().createCriteria(Training.class).list();
	}

	@Override
	public Training getTrainingById(long id) throws FitnessDaoException {
		Training result = (Training) getSession()
				.createCriteria(Training.class).add(Restrictions.eq("id", id))
				.uniqueResult();
		if (result != null)
			return result;
		else
			throw new FitnessDaoException("Training doesn't found with id:" + id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Training> getTrainingsByTrainer(User trainer) {
		return getSession().createCriteria(Training.class)
				.add(Restrictions.eq("trainer", trainer)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Training> getTrainingsByClient(User client) {
		return getSession().createCriteria(Training.class)
				.add(Restrictions.eq("client", client)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Training> getTrainingsByOrder(Basket basket) {
		return getSession().createCriteria(Training.class)
				.add(Restrictions.eq("basket", basket)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Training> getTrainingsAfterDate(Date date) {
		return getSession().createCriteria(Training.class)
				.add(Restrictions.gt("trainingStartDate", date)).list();
	}
	
	@Override
	public boolean isDateReserved(User trainer, Date date) {
		Training training = (Training)getSession().createCriteria(Training.class).add(Restrictions.eq("trainer", trainer)).add(Restrictions.eq("trainingStartDate", date)).uniqueResult();
		return training == null ? false : true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Training> getTrainingsOnWeekByTrainer(User trainer, Date monday) {
		Date sunday = new Date(monday.getTime() + 7*ONE_DAY-1);
		return getSession().createCriteria(Training.class).add(Restrictions.eq("trainer", trainer)).add(Restrictions.ge("trainingStartDate", monday)).add(Restrictions.le("trainingStartDate", sunday)).list();
	}

}
