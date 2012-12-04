package com.acme.fitness.products;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;

public interface TrainingService {
	Training newTraining(User trainer, User client, Date date);
	Training saveTraining(Basket basket, Training training);
	Training saveNewTraining(User trainer, User client, Date date, Basket basket);
	void deleteTraining(Training training);

	void updateTraining(Training training);

	void recordTrainingResults(Training training, int burnedCalories,
			String review);

	List<Training> getTrainingsByTrainer(User trainer);

	List<Training> getTrainingsByClient(User client);
}
