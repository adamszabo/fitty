package com.acme.fitness.dao.products;

import java.util.Date;
import java.util.List;

import com.acme.fitness.dao.GenericDao;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;

public interface TrainingDao extends GenericDao<Training>{
	
	public List<Training> getAllTraining();
	
	public Training getTrainingById(long id);
	
	public List<Training> getTrainingByTrainer(User trainer);
	
	public List<Training> getTrainingByClient(User client);
	
	public List<Training> getTrainingByOrder(Basket order);
	
	public List<Training> getTrainingAfterDate(Date date);
}
