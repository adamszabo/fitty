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
	private Date trainingStartDate;

	@Column
	private boolean isAnalyzed;

	@Column
	private int burnedCalories;

	@Column
	private String review;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Basket basket;

	public Training() {
		super();
	}

	public Training(User trainer, User client, Date trainingStartDate,
			boolean isAnalyzed, int burnedCalories, String review, Basket basket) {
		super();
		this.trainer = trainer;
		this.client = client;
		this.trainingStartDate = trainingStartDate;
		this.isAnalyzed = isAnalyzed;
		this.burnedCalories = burnedCalories;
		this.review = review;
		this.basket = basket;
	}

	@Override
	public String toString() {
		return "Training [id=" + id + ", trainerId=" + trainer.getId()
				+ ", clientId=" + client.getId() + ", trainingStartDate="
				+ trainingStartDate + ", burnedCalories=" + burnedCalories
				+ ", isAnalyzed=" + isAnalyzed + ", basketId=" + basket.getId()
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + burnedCalories;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (isAnalyzed ? 1231 : 1237);
		result = prime * result + ((trainer == null) ? 0 : trainer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof Training)) {
			return false;
		}

		Training other = (Training) obj;
		boolean result = false;
		if (client != null && trainer != null) {
			result = id == other.getId() 
					&& client.equals(other.getClient())
					&& trainer.equals(other.getTrainer())
					&& burnedCalories == other.getBurnedCalories()
					&& isAnalyzed == other.isAnalyzed();
		}
		return result;
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

	public Date getTrainingStartDate() {
		return trainingStartDate;
	}

	public void setTrainingStartDate(Date trainingStartDate) {
		this.trainingStartDate = trainingStartDate;
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

}
