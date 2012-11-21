package com.acme.fitness.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class OrderItem {

	@Id
	@Column
	@GeneratedValue
	private long id;

	@ManyToOne
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Product product;

	@Column
	private int quantity;

	@ManyToOne
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Order order;

	public OrderItem() {
		super();
	}

	public OrderItem(Product product, int quantity, Order order) {
		super();
		this.product = product;
		this.quantity = quantity;
		this.order = order;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", product=" + product + ", quantity="
				+ quantity + ", orderId=" + order.getId() + "]";
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
