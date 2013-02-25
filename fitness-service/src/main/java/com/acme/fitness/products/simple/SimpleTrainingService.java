package com.acme.fitness.products.simple;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.fitness.dao.products.TrainingDao;
import com.acme.fitness.dao.products.TrainingTypeDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.TrainingService;

@Service
public class SimpleTrainingService implements TrainingService {

	private TrainingDao trainingDao;
	
	private TrainingTypeDao trainingTypeDao;
	
	@Autowired
	public SimpleTrainingService(TrainingDao trainingDao, TrainingTypeDao trainingTypeDao){
		this.trainingDao=trainingDao;
		this.trainingTypeDao = trainingTypeDao;
	}
	
	@Override
	public void goOnHoliday(User trainer, Date date) {
		trainingDao.save(new Training(trainer, trainer, date, false, 0, "", null, 0.0));
	}
	
	@Override
	public Training newTraining(User trainer, User client, Date date) throws FitnessDaoException {
		double trainerPrice = trainingTypeDao.getTrainingTypeByTrainer(trainer).getPrice();
		Training training = new Training(trainer, client, date, false, 0, null, null, trainerPrice);
		return training;
	}
	
	@Override
	public Training saveTraining(Basket basket, Training training) {
		training.setBasket(basket);
		trainingDao.save(training);
		return training;
	}
	
	@Override
	public Training saveNewTraining(User trainer, User client, Date date, Basket basket) throws FitnessDaoException {
		Training training = newTraining(trainer, client, date);
		training.setBasket(basket);
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

	@Override
	public void goOnHolidayToAllDay(User trainer, Date date) {
		Calendar trainingTime=Calendar.getInstance();
		trainingTime.setTime(date);
		
		for(int i=0;i<24;i++){
			trainingTime.set(Calendar.HOUR_OF_DAY, i);
			goOnHolidayIfNotReserved(trainer, trainingTime.getTime());
		}
	}

	private void goOnHolidayIfNotReserved(User trainer, Date trainingDate) {
		if(!trainingDao.isDateReserved(trainer, trainingDate)){
			goOnHoliday(trainer, trainingDate);
		}
	}
}