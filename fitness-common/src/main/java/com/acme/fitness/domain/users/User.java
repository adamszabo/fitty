package com.acme.fitness.domain.users;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fitness_user")
public class User {

	@Id
	@Column
	@GeneratedValue
	private long id;

	@Column
	private String fullName;

	@Column(unique=true)
	private String userName;

	@Column
	private String password;

	@Column(unique=true)
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

	public User() {
		super();
	}

	public User(String fullName, String userName, String password,
			String email, String mobile, Date registration, Date lastLogin,
			String lastLoginIp, boolean enabled) {
		super();
		this.fullName = fullName;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.mobile = mobile;
		this.registration = registration;
		this.lastLogin = lastLogin;
		this.lastLoginIp = lastLoginIp;
		this.enabled=enabled;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fullName=" + fullName + ", userName="
				+ userName + ", email=" + email + ", registration="
				+ registration + ", lastLogin=" + lastLogin + ", lastLoginIp="
				+ lastLoginIp + ", isEnabled="+ enabled + "]";
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
	

}
