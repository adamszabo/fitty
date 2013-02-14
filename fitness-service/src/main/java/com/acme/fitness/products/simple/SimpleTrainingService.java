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

	private TrainingDao trainingDao;
	
	@Autowired
	public SimpleTrainingService(TrainingDao trainingDao){
		this.trainingDao=trainingDao;
	}
	
	@Override
	public void goOnHoliday(User trainer, Date date) {
		trainingDao.save(new Training(trainer, trainer, date, false, 0, "", null));
	}
	
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

	@Override
	public List<Training> getTrainingsByBasket(Basket basket) {
		return trainingDao.getTrainingsByOrder(basket);
	}

	@Override
	public boolean isDateReserved(User trainer, Date date) {
		return trainingDao.isDateReserved(trainer, date);
	}
	
	@Override
	public List<Training> getTrainingsOnWeekByTrainer(User trainer, Date monday) {
		return trainingDao.getTrainingsOnWeekByTrainer(trainer, monday);
	}
}