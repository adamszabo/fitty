package com.acme.fitness.dao;

public interface GenericDao<T> {
	
	public void save(T t);

	public void update(T t);

	public void delete(T t);
}
