package com.acme.fitness.products;

import java.util.List;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.TrainingType;
import com.acme.fitness.domain.users.User;

public interface TrainingTypeService {
	
	public TrainingType newTrainingType(User trainer, String detail, double price);
	public void saveTrainingType(TrainingType trainingType);
	public List<TrainingType> getAllTrainingTypes();
	public TrainingType getTrainingTypeById(long id) throws FitnessDaoException;
	public TrainingType getTrainingTypeByTrainer(User trainer) throws FitnessDaoException;
	public void updateTrainingType(TrainingType trainingType);
	public void deleteTrainingType(TrainingType trainingType);
}
