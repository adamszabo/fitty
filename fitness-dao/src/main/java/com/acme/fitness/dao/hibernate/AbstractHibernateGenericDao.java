package com.acme.fitness.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.acme.fitness.dao.GenericDao;

public abstract class AbstractHibernateGenericDao<T> implements GenericDao<T>{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public void save(T t){
		getSession().persist(t);
		logger.debug("Entity SAVED in the database: " + t);
	}
	
	public void update(T t){
		getSession().update(t);
		logger.debug("Entity UPDATED in the database: " + t);
	}
	
	public void delete(T t){
		getSession().delete(t);
		logger.debug("Entity DELETED in the database: " + t);
	}
}
