package com.acme.fitness.products.simple;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.TrainingService;

public class SimpleTrainingService implements TrainingService {

	@Override
	public void addTraining(User trainer, User client, Date date, Basket basket) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteTraining(Training training) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTraining(Training training) {
		// TODO Auto-generated method stub

	}

	@Override
	public void recordTrainingResults(Training training, int burnedCalories,
			String review) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Training> getTrainingsByTrainer(User trainer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Training> getTrainingsByClient(User client) {
		// TODO Auto-generated method stub
		return null;
	}

}
