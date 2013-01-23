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
	private String detail;
	
	@Column 
	private boolean isIntervally;
	
	@Column
	private int maxNumberOfEntries;
	
	@Column
	private long expireDateInDays;

	@Column
	private double price;
	
	public MembershipType() {
	}

	public MembershipType(String detail, boolean isIntervally,
			int maxNumberOfEntries, long expireDateInDays, double price) {
		this.detail = detail;
		this.isIntervally = isIntervally;
		this.maxNumberOfEntries = maxNumberOfEntries;
		this.expireDateInDays = expireDateInDays;
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((detail == null) ? 0 : detail.hashCode());
		result = prime * result + (isIntervally ? 1231 : 1237);
		result = prime * result
				+ (int) (expireDateInDays ^ (expireDateInDays >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + maxNumberOfEntries;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEquals = false;
		if (this == obj) {
			isEquals = true;
		} else if(obj instanceof MembershipType) {
			MembershipType other = (MembershipType) obj;
			isEquals = id == other.id 
					&& (this.isIntervally == other.isIntervally)
					&& (this.detail == null ? other.detail == null : this.detail.equals(other.detail))
					&& (expireDateInDays == other.expireDateInDays)
					&& (maxNumberOfEntries == other.maxNumberOfEntries)
					&& (Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price));
		}
		return isEquals;
	}

	@Override
	public String toString() {
		return "MembershipType [id=" + id + ", detail=" + detail + ", isIntervally=" + isIntervally
				+ ", maxNumberOfEntries=" + maxNumberOfEntries
				+ ", expireDateInDays=" + expireDateInDays + ", price=" + price
				+ "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public boolean getIsIntervally() {
		return isIntervally;
	}

	public void setIsIntervally(boolean isIntervally) {
		this.isIntervally = isIntervally;
	}

	public int getMaxNumberOfEntries() {
		return maxNumberOfEntries;
	}

	public void setMaxNumberOfEntries(int maxNumberOfEntries) {
		this.maxNumberOfEntries = maxNumberOfEntries;
	}

	public long getExpireDateInDays() {
		return expireDateInDays;
	}

	public void setExpireDateInDays(long expireDateInDays) {
		this.expireDateInDays = expireDateInDays;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
