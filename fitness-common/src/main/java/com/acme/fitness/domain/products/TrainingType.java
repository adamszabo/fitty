package com.acme.fitness.domain.products;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.acme.fitness.domain.users.User;

@Entity
public class TrainingType {
	
	@Id
	@Column
	@GeneratedValue
	private long id;
	
	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User trainer;

	@Column
	private String detail;
	
	@Column
	private double price;

	public TrainingType() {
		
	}

	public TrainingType(User trainer, String detail, double price) {
		super();
		this.trainer = trainer;
		this.detail = detail;
		this.price = price;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((detail == null) ? 0 : detail.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((trainer == null) ? 0 : trainer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEquals = false;
		if (this == obj) {
			isEquals = true;
		} else if(obj instanceof TrainingType) {
			TrainingType other = (TrainingType) obj;
			isEquals = id == other.id
					&& (this.detail == null ? other.detail == null : this.detail.equals(other.detail))
					&& (this.trainer == null ? other.trainer == null : this.trainer.equals(other.trainer))
					&& (Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price));
		}
		return isEquals;
	}

	@Override
	public String toString() {
		return "TrainingType [id=" + id + ", trainer=" + trainer + ", detail=" + detail + ", price=" + price + "]";
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
