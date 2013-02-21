package com.acme.fitness.orders.simple;

import java.util.List;

import com.acme.fitness.domain.products.Training;

public class TrainingDateReservedException extends RuntimeException {

	private static final long serialVersionUID = -5469933095990263931L;
	private List<Training> reservedTrainings;
	
	public TrainingDateReservedException(List<Training> reservedTrainings) {
		this.reservedTrainings = reservedTrainings;
	}

	public List<Training> getReservedTrainings() {
		return reservedTrainings;
	}
}
