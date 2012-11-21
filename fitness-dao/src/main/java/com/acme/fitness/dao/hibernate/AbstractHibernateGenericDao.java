package com.acme.fitness.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.acme.fitness.dao.GenericDao;

public abstract class AbstractHibernateGenericDao<T> implements GenericDao<T>{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public void save(T t){
		getSession().persist(t);
	}
	
	public void update(T t){
		getSession().update(t);
	}
	
	public void delete(T t){
		getSession().delete(t);
	}
}
