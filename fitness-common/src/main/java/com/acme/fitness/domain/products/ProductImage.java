package com.acme.fitness.domain.products;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class ProductImage {
	
	@Column
	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private String mime;
	
	@Column
	@Lob
	private byte[] image;
	
	public ProductImage() {
		super();
	}
	
	public ProductImage(String mime, byte[] image) {
		super();
		this.mime = mime;
		this.image = image;
	}

	@Override
	public String toString() {
		return "ProductImage [id=" + id + ", mime=" + mime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 431;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((mime == null) ? 0 : mime.hashCode());
		result = prime * result + ((image == null) ? 0 : Arrays.hashCode(image));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean isEquals = false;
		if (this == obj){
			isEquals = true;
		}
		else if (obj instanceof ProductImage){
		ProductImage other = (ProductImage) obj;
		isEquals = id == other.id
				&& (this.mime==null  ?other.getMime()==null : this.mime.equals(other.getMime()))
				&& (this.image==null ? other.getImage()==null : Arrays.equals(this.image, other.getImage()));
		}
		
		return isEquals;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMime() {
		return mime;
	}
	public void setMime(String mime) {
		this.mime = mime;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
}
