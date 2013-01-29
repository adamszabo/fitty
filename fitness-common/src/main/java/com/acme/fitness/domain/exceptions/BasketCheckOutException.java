package com.acme.fitness.domain.exceptions;

public class BasketCheckOutException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public BasketCheckOutException() {
	}

	public BasketCheckOutException(String message) {
		super(message);
	}
}
