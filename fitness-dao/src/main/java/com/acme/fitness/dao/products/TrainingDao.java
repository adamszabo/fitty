package com.acme.fitness.dao.products;

import java.util.Date;
import java.util.List;

import com.acme.fitness.dao.GenericDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;

public interface TrainingDao extends GenericDao<Training>{
	
	public List<Training> getAllTraining();
	
	public Training getTrainingById(long id) throws FitnessDaoException;
	
	public List<Training> getTrainingsByTrainer(User trainer);
	
	public List<Training> getTrainingsByClient(User client);
	
	public List<Training> getTrainingsByOrder(Basket order);
	
	public List<Training> getTrainingsAfterDate(Date date);

	boolean isDateReserved(User trainer, Date date);

	List<Training> getTrainingsOnWeekByTrainer(User trainer, Date monday);
}
