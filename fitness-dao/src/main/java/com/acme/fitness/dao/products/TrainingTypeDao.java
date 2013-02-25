package com.acme.fitness.dao.products;

import java.util.List;

import com.acme.fitness.dao.GenericDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.TrainingType;
import com.acme.fitness.domain.users.User;

public interface TrainingTypeDao extends GenericDao<TrainingType> {

	public List<TrainingType> getAllTrainingTypes();
	
	public TrainingType getTrainingTypeById(long id) throws FitnessDaoException;
	
	public TrainingType getTrainingTypeByTrainer(User trainer) throws FitnessDaoException;
}
