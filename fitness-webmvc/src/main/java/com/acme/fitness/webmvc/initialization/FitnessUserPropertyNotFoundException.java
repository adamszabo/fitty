package com.acme.fitness.webmvc.initialization;

public class FitnessUserPropertyNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FitnessUserPropertyNotFoundException(String msg){
		super("Property not found with key: "+msg);
	}
}
