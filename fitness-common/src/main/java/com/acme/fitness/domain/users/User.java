package com.acme.fitness.domain.users;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "fitness_user")
public class User {

	@Id
	@Column
	@GeneratedValue
	private long id;

	@Column
	@NotNull
	private String fullName;

	@Column(unique = true)
	@NotNull
	private String username;

	@Column
	@NotNull
	private String password;

	@Column(unique = true)
	@NotNull
	private String email;

	@Column
	private String mobile;

	@Column
	private Date registration;

	@Column
	private Date lastLogin;

	@Column
	private String lastLoginIp;

	@Column
	private boolean enabled;

	@Column
	private int numberOfRetries;

	public User() {
		super();
		this.enabled = true;
		this.numberOfRetries = 0;
	}

	public User(String fullName, String userName, String password,
			String email, String mobile, Date registration, Date lastLogin,
			String lastLoginIp) {
		super();
		this.fullName = fullName;
		this.username = userName;
		this.password = password;
		this.email = email;
		this.mobile = mobile;
		this.registration = registration;
		this.lastLogin = lastLogin;
		this.lastLoginIp = lastLoginIp;
		this.enabled = true;
		this.numberOfRetries = 0;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fullName=" + fullName + ", userName="
				+ username + ", email=" + email + ", registration="
				+ registration + ", lastLogin=" + lastLogin + ", lastLoginIp="
				+ lastLoginIp + ", isEnabled=" + enabled + ", numberOfRetries="
				+ numberOfRetries + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result
				+ ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + numberOfRetries;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEquals = false;
		if (this == obj){
			isEquals = true;
		}
		else if (obj instanceof User){
			User other = (User) obj;
			if (fullName != null && password != null) {
				isEquals = numberOfRetries == other.numberOfRetries 
						&& id == other.id
						&& enabled == other.enabled
						&& username.equals(other.getUsername())
						&& (email.equals(other.getEmail()))
						&& password.equals(other.getPassword())
						&& (fullName.equals(other.getFullName()) || fullName==null && other.fullName==null);
			}
		}

		return isEquals;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getRegistration() {
		return registration;
	}

	public void setRegistration(Date registration) {
		this.registration = registration;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getNumberOfRetries() {
		return numberOfRetries;
	}

	public void setNumberOfRetries(int numberOfRetries) {
		this.numberOfRetries = numberOfRetries;
	}

}
