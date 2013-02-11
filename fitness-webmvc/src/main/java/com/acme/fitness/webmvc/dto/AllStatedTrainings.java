package com.acme.fitness.webmvc.dto;

import java.util.ArrayList;
import java.util.List;

import com.acme.fitness.domain.products.Training;

public class AllStatedTrainings {
	private List<Training> orderedTrainings;
	private List<Training> trainingsInBasket;
	
	public AllStatedTrainings(){
		super();
		orderedTrainings=new ArrayList<Training>();
		trainingsInBasket=new ArrayList<Training>();
	}

	public List<Training> getOrderedTrainings() {
		return orderedTrainings;
	}

	public void setOrderedTrainings(List<Training> orderedTrainings) {
		this.orderedTrainings = orderedTrainings;
	}

	public List<Training> getTrainingsInBasket() {
		return trainingsInBasket;
	}

	public void setTrainingsInBasket(List<Training> trainingsInBasket) {
		this.trainingsInBasket = trainingsInBasket;
	}
}
