package com.acme.fitness.domain.orders;

import java.util.HashSet;
import java.util.Set;

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

import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;

@Entity
@Table
public class Basket {

	@Id
	@Column
	@GeneratedValue
	private long id;

	@Column
	private boolean delivered;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	@OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<OrderItem> orderItems;

	@Transient
	private Set<Membership> memberships;

	@Transient
	private Set<Training> trainings;

	public Basket() {
		super();
		trainings = new HashSet<Training>();
		memberships = new HashSet<Membership>();
		orderItems = new HashSet<OrderItem>();
	}

	public Basket(boolean delivered, User user) {
		super();
		this.delivered = delivered;
		this.user = user;
		trainings = new HashSet<Training>();
		memberships = new HashSet<Membership>();
		orderItems = new HashSet<OrderItem>();
	}
	
	public void addMembership(Membership m) {
		memberships.add(m);
	}
	
	public void addTraining(Training t) {
		trainings.add(t);
	}
	
	public void addOrderItem(OrderItem o) {
		orderItems.add(o);
	}

	@Override
	public String toString() {
		return "Basket [id=" + id + ", delivered=" + delivered + ", user="
				+ user + "]"; // + ", orderItems=" + orderItems.toString()
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (delivered ? 1231 : 1237);
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Basket))
			return false;
		Basket other = (Basket) obj;
		if (delivered != other.delivered)
			return false;
		if (id != other.id)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
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

	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Set<Membership> getMemberships() {
		return memberships;
	}

	public void setMemberships(Set<Membership> memberships) {
		this.memberships = memberships;
	}

	public Set<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(Set<Training> trainings) {
		this.trainings = trainings;
	}
}
