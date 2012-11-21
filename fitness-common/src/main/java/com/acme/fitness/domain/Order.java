package com.acme.fitness.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "fitness_order")
public class Order {

	@Id
	@Column
	@GeneratedValue
	private long id;

	@Column
	private boolean delivered;

	@ManyToOne
	@OnDelete(action=OnDeleteAction.CASCADE)
	private User user;
	
	@OneToMany(mappedBy="order",cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<OrderItem> orderItems;
	
	@Transient
	private List<MemberShip> memberShips;
	
	@Transient
	private List<Training> trainings;

	public Order() {
		super();
		orderItems=new ArrayList<OrderItem>();
	}


	public Order(boolean delivered, User user) {
		super();
		this.delivered = delivered;
		this.user = user;
		orderItems=new ArrayList<OrderItem>();
	}


	@Override
	public String toString() {
		return "Order [id=" + id + ", delivered=" + delivered + ", user="
				+ user  + "]"; //+ ", orderItems=" + orderItems.toString()
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}


	public List<MemberShip> getMemberShips() {
		return memberShips;
	}


	public void setMemberShips(List<MemberShip> memberShips) {
		this.memberShips = memberShips;
	}


	public List<Training> getTrainings() {
		return trainings;
	}


	public void setTrainings(List<Training> trainings) {
		this.trainings = trainings;
	}
	
}
