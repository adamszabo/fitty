package com.acme.fitness.domain.orders;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.acme.fitness.domain.products.Product;

@Entity
public class Store {

	@Id
	@Column
	@GeneratedValue
	private long id;

	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Product product;

	@Column
	private int quantity;

	public Store() {
		super();
	}

	public Store(Product product, int quantity) {
		super();
		this.product = product;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Store [id=" + id + ", product=" + product + ", quantity="
				+ quantity + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
