package com.acme.fitness.domain.orders;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.acme.fitness.domain.products.Product;

@Entity
public class OrderItem {

	@Id
	@Column
	@GeneratedValue
	private long id;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Product product;

	@Column
	private int quantity;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Basket basket;

	public OrderItem() {
		super();
	}

	public OrderItem(Product product, int quantity, Basket order) {
		super();
		this.product = product;
		this.quantity = quantity;
		this.basket = order;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", product=" + product + ", quantity="
				+ quantity + ", basketId=" + basket.getId() + "]";
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

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket order) {
		this.basket = order;
	}

}
