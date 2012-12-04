package com.acme.fitness.products.simple;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.fitness.dao.products.TrainingDao;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.TrainingService;

@Service
public class SimpleTrainingService implements TrainingService {

	@Autowired
	private TrainingDao trainingDao;
	
	@Override
	public Training newTraining(User trainer, User client, Date date) {
		Training training = new Training(trainer, client, date, false, 0, null, null);
		return training;
	}
	
	@Override
	public Training saveTraining(Basket basket, Training training) {
		training.setBasket(basket);
		trainingDao.save(training);
		return training;
	}
	
	@Override
	public Training saveNewTraining(User trainer, User client, Date date, Basket basket) {
		Training training = new Training(trainer, client, date, false, 0, null, basket);
		trainingDao.save(training);
		return training;
	}

	@Override
	public void deleteTraining(Training training) {
		trainingDao.delete(training);
	}

	@Override
	public void updateTraining(Training training) {
		trainingDao.update(training);
	}

	@Override
	public void recordTrainingResults(Training training, int burnedCalories,
			String review) {
		training.setBurnedCalories(burnedCalories);
		training.setReview(review);
		training.setAnalyzed(true);
		updateTraining(training);
	}

	@Override
	public List<Training> getTrainingsByTrainer(User trainer) {
		return trainingDao.getTrainingsByTrainer(trainer);
	}

	@Override
	public List<Training> getTrainingsByClient(User client) {
		return trainingDao.getTrainingsByClient(client);
	}

	public TrainingDao getTrainingDao() {
		return trainingDao;
	}

	public void setTrainingDao(TrainingDao trainingDao) {
		this.trainingDao = trainingDao;
	}
}
