package com.acme.fitness.dao.products;

import java.util.Date;
import java.util.List;

import com.acme.fitness.dao.GenericDao;
import com.acme.fitness.domain.Order;
import com.acme.fitness.domain.Training;
import com.acme.fitness.domain.User;

public interface TrainingDao extends GenericDao<Training>{
	
	public List<Training> getAllTraining();
	
	public Training getTrainingById(long id);
	
	public List<Training> getTrainingByTrainer(User trainer);
	
	public List<Training> getTrainingByClient(User client);
	
	public List<Training> getTrainingByOrder(Order order);
	
	public List<Training> getTrainingAfterDate(Date date);
}
