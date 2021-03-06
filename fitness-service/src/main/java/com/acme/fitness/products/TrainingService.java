package com.acme.fitness.products;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;

public interface TrainingService {
	
	Training newTraining(User trainer, User client, Date date) throws FitnessDaoException;

	Training saveTraining(Basket basket, Training training);

	Training saveNewTraining(User trainer, User client, Date date, Basket basket) throws FitnessDaoException;

	void deleteTraining(Training training);

	void updateTraining(Training training);

	void recordTrainingResults(Training training, int burnedCalories,String review);

	List<Training> getTrainingsByTrainer(User trainer);

	List<Training> getTrainingsByClient(User client);
	
	List<Training> getTrainingsByBasket(Basket basket);

	boolean isDateReserved(User trainer, Date date);

	List<Training> getTrainingsOnWeekByTrainer(User trainer, Date monday);

	void goOnHoliday(User trainer, Date date);
	
	void goOnHolidayToAllDay(User trainer, Date date);
}
