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

@Entity
public class Membership {
	
	@Id
	@Column
	@GeneratedValue
	private long id;
	
	@Column
	private boolean isIntervally;

	@Column
	private String type;

	@Column
	private int numberOfEntries;

	@Column
	private int maxNumberOfEntries;

	@Column 
	private Date startDate;
	
	@Column
	private Date expireDate;

	@Column
	private double price;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Basket basket;

	public Membership() {
		super();
	}

	public Membership(boolean isIntervally, String type,
			int numberOfEntries, int maxNumberOfEntries, Date startDate,
			Date expireDate, double price, Basket basket) {
		this.isIntervally = isIntervally;
		this.type = type;
		this.numberOfEntries = numberOfEntries;
		this.maxNumberOfEntries = maxNumberOfEntries;
		this.startDate = startDate;
		this.expireDate = expireDate;
		this.price = price;
		this.basket = basket;
	}

	@Override
	public String toString() {
		return "Membership [id=" + id + ", isIntervally=" + isIntervally
				+ ", type=" + type + ", numberOfEntries=" + numberOfEntries
				+ ", maxNumberOfEntries=" + maxNumberOfEntries + ", startDate="
				+ startDate + ", expireDate=" + expireDate + ", price=" + price
				+ ", basketId=" + basket.getId() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((basket == null) ? 0 : basket.hashCode());
		result = prime * result + (isIntervally ? 1231 : 1237);
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result
				+ ((expireDate == null) ? 0 : expireDate.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + maxNumberOfEntries;
		result = prime * result + numberOfEntries;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEquals = false;
		if (this == obj) {
			isEquals = true;
		} else if(obj instanceof Membership) {
			Membership other = (Membership) obj;
			isEquals = id == other.id 
					&& (this.isIntervally == other.isIntervally)
					&& (this.type == null ? other.type == null : this.type.equals(other.type))
					&& (this.basket == null ? other.basket == null : this.basket.equals(other.basket))
					&& (this.expireDate == null ? other.expireDate == null : this.expireDate.equals(other.expireDate))
					&& (this.startDate == null ? other.startDate == null : this.startDate.equals(other.startDate))
					&& (this.maxNumberOfEntries == other.maxNumberOfEntries)
					&& (this.numberOfEntries == other.numberOfEntries)
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

	public boolean getIsIntervally() {
		return isIntervally;
	}

	public void setIsIntervally(boolean isIntervally) {
		this.isIntervally = isIntervally;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNumberOfEntries() {
		return numberOfEntries;
	}

	public void setNumberOfEntries(int numberOfEntries) {
		this.numberOfEntries = numberOfEntries;
	}

	public int getMaxNumberOfEntries() {
		return maxNumberOfEntries;
	}

	public void setMaxNumberOfEntries(int maxNumberOfEntries) {
		this.maxNumberOfEntries = maxNumberOfEntries;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket order) {
		this.basket = order;
	}
}
