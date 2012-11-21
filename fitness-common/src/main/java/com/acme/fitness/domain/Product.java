package com.acme.fitness.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {
	
	@Id
	@Column
	@GeneratedValue
	private long id;
	
	@Column
	private String name;
	
	@Column
	private String details;
	
	@Column
	private double price;

	@Column
	private String manufacturer;
	
	@Column
	private Date creation;

	public Product() {
		super();
	}

	public Product(String name, String details, double price,
			String manufacturer, Date creation) {
		super();
		this.name = name;
		this.details = details;
		this.price = price;
		this.manufacturer = manufacturer;
		this.creation = creation;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price
				+ ", manufacturer=" + manufacturer + ", creation=" + creation + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date create) {
		this.creation = create;
	}
	
}
