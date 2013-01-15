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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((basket == null) ? 0 : basket.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + quantity;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof OrderItem))
			return false;
		OrderItem other = (OrderItem) obj;
		if (basket == null) {
			if (other.basket != null)
				return false;
		} else if (!basket.equals(other.basket))
			return false;
		if (id != other.id)
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
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
