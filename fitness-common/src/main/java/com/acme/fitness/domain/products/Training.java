package com.acme.fitness.domain.products;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.users.User;

@Entity
public class Training {
	@Id
	@Column
	@GeneratedValue
	private long id;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User trainer;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User client;

	@Column
	private Date date;

	@Column
	private boolean isAnalyzed;

	@Column
	private int burnedCalories;

	@Column
	private String review;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Basket basket;
	
	@Column
	private double price;

	public Training() {
		super();
	}

	public Training(User trainer, User client, Date date,
			boolean isAnalyzed, int burnedCalories, String review, Basket basket, double price) {
		super();
		this.trainer = trainer;
		this.client = client;
		this.date = date;
		this.isAnalyzed = isAnalyzed;
		this.burnedCalories = burnedCalories;
		this.review = review;
		this.basket = basket;
		this.price = price;
	}

	@Override
	public String toString() {
		return "Training [id=" + id + ", trainerId=" + trainer.getId()
				+ ", clientId=" + client.getId() + ", date="
				+ date + ", burnedCalories=" + burnedCalories
				+ ", isAnalyzed=" + isAnalyzed 	+ ", price= " + price + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((basket == null) ? 0 : basket.hashCode());
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((trainer == null) ? 0 : trainer.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((trainer == null) ? 0 : trainer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEquals = false;
		if(this == obj) {
			isEquals = true;
		} else if(obj instanceof Training) {
			Training other = (Training) obj;
			isEquals = id == other.id
					&& (this.client == null ? other.client == null : this.client.equals(other.client))
					&& (this.trainer == null ? other.trainer == null : this.trainer.equals(other.trainer)) 
					&& (this.date == null ? other.date == null : this.date.equals(other.date))
					&& (this.basket == null ? other.basket == null : this.basket.equals(other.basket))
					&& (Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price));
		}

		return isEquals;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getTrainer() {
		return trainer;
	}

	public void setTrainer(User trainer) {
		this.trainer = trainer;
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public boolean isAnalyzed() {
		return isAnalyzed;
	}

	public void setAnalyzed(boolean isAnalyzed) {
		this.isAnalyzed = isAnalyzed;
	}

	public int getBurnedCalories() {
		return burnedCalories;
	}

	public void setBurnedCalories(int burnedCalories) {
		this.burnedCalories = burnedCalories;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
