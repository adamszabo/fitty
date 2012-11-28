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
	private String type;

	@Column
	private int numberOfEntries;

	@Column
	private int maxNumberOfEntries;

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

	public Membership(String type, int numberOfEntries, int maxNumberOfEntries,
			Date expireDate, double price, Basket order) {
		super();
		this.type = type;
		this.numberOfEntries = numberOfEntries;
		this.maxNumberOfEntries = maxNumberOfEntries;
		this.expireDate = expireDate;
		this.price = price;
		this.basket = order;
	}

	@Override
	public String toString() {
		return "Membership [id=" + id + ", type=" + type + ", numberOfEntries="
				+ numberOfEntries + ", maxNumberOfEntries="
				+ maxNumberOfEntries + ", expireDate=" + expireDate
				+ ", price=" + price + ", basketId=" + basket.getId() + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
