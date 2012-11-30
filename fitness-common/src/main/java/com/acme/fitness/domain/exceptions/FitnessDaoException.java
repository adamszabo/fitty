package com.acme.fitness.domain.exceptions;

public class FitnessDaoException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public FitnessDaoException(){
		super();
	}
	
	public FitnessDaoException(String msg){
		super(msg);
	}
}
