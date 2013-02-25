package com.acme.fitness.products.simple;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.fitness.dao.products.TrainingTypeDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.TrainingType;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.TrainingTypeService;

@Service
public class SimpleTrainingTypeService implements TrainingTypeService {

	private TrainingTypeDao trainingTypeDao;
	
	private Logger logger = LoggerFactory.getLogger(SimpleTrainingTypeService.class);
	
	public SimpleTrainingTypeService() {
		
	}
	
	@Autowired
	public SimpleTrainingTypeService(TrainingTypeDao trainingTypeDao) {
		this.trainingTypeDao = trainingTypeDao;
	}
	
	@Override
	public TrainingType newTrainingType(User trainer, String detail, double price) {
		return new TrainingType(trainer, detail, price);
	}

	@Override
	public void saveTrainingType(TrainingType trainingType) {
		trainingTypeDao.save(trainingType);
		logger.info("Training type is saved " + trainingType);
	}

	@Override
	public List<TrainingType> getAllTrainingTypes() {
		return trainingTypeDao.getAllTrainingTypes();
	}

	@Override
	public TrainingType getTrainingTypeById(long id) throws FitnessDaoException {
		return trainingTypeDao.getTrainingTypeById(id);
	}

	@Override
	public TrainingType getTrainingTypeByTrainer(User trainer) throws FitnessDaoException {
		return trainingTypeDao.getTrainingTypeByTrainer(trainer);
	}

	@Override
	public void updateTrainingType(TrainingType trainingType) {
		trainingTypeDao.update(trainingType);
		logger.info("Training type is updated " + trainingType);
	}

	@Override
	public void deleteTrainingType(TrainingType trainingType) {
		logger.info("Training type is deleted " + trainingType);
		trainingTypeDao.delete(trainingType);
	}
}
