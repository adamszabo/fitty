package com.acme.fitness.domain.orders;

import java.util.ArrayList;
import java.util.Date;
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

import org.codehaus.jackson.annotate.JsonIgnore;
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
	
	@JsonIgnore
	@OneToMany(mappedBy = "basket", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<OrderItem> orderItems;
	
	@JsonIgnore
	@Transient
	private List<Membership> memberships;
	
	@JsonIgnore
	@Transient
	private List<Training> trainings;
	
	@Column
	private Date creationDate;
	
	public Basket() {
		this(false, null);
	}

	public Basket(boolean delivered, User user) {
		super();
		this.delivered = delivered;
		this.user = user;
		trainings = new ArrayList<Training>();
		memberships = new ArrayList<Membership>();
		orderItems = new ArrayList<OrderItem>();
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

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public List<Membership> getMemberships() {
		return memberships;
	}

	public void setMemberships(List<Membership> memberships) {
		this.memberships = memberships;
	}

	public List<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(List<Training> trainings) {
		this.trainings = trainings;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
}
