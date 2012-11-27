package com.acme.fitness.domain.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Role {

	@Id
	@Column
	@GeneratedValue
	private long id;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne
	private User user;

	@Column
	private String name;

	public Role() {
		super();
	}

	public Role(User user, String name) {
		super();
		this.user = user;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", user=" + user + ", name=" + name + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
