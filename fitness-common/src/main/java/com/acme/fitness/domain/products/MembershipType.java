package com.acme.fitness.domain.products;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MembershipType {
	
	@Id
	@Column
	@GeneratedValue
	private long id;

	@Column
	private String type;
	
	@Column
	private int maxNumberOfEntries;
	
	@Column
	private int expireDateInDays;

	@Column
	private double price;
	
	public MembershipType() {
	}

	public MembershipType(String type, int maxNumberOfEntries,
			int expireDateInDays, double price) {
		this.type = type;
		this.maxNumberOfEntries = maxNumberOfEntries;
		this.expireDateInDays = expireDateInDays;
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + expireDateInDays;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + maxNumberOfEntries;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEquals = false;
		if(this == obj) {
			isEquals = true;
		} else if(obj instanceof MembershipType) {
			MembershipType other = (MembershipType) obj;
			isEquals = id == other.id 
					&& expireDateInDays == other.expireDateInDays
					&& maxNumberOfEntries == other.maxNumberOfEntries
					&& type == other.type
					&& Double.doubleToLongBits(price) == Double
							.doubleToLongBits(other.price);
		}
		return isEquals;
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

	public int getMaxNumberOfEntries() {
		return maxNumberOfEntries;
	}

	public void setMaxNumberOfEntries(int maxNumberOfEntries) {
		this.maxNumberOfEntries = maxNumberOfEntries;
	}

	public int getExpireDateInDays() {
		return expireDateInDays;
	}

	public void setExpireDateInDays(int expireDateInDays) {
		this.expireDateInDays = expireDateInDays;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
