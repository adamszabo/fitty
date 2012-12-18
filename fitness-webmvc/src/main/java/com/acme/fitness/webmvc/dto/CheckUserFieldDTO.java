package com.acme.fitness.webmvc.dto;

public class CheckUserFieldDTO {
	private boolean existUsername;
	private boolean existEmail;

	public boolean isExistUsername() {
		return existUsername;
	}

	public void setExistUsername(boolean existUsername) {
		this.existUsername = existUsername;
	}

	public boolean isExistEmail() {
		return existEmail;
	}

	public void setExistEmail(boolean existEmail) {
		this.existEmail = existEmail;
	}

	@Override
	public String toString() {
		return "CheckUserFieldDTO [existUsername=" + existUsername
				+ ", existEmail=" + existEmail + "]";
	}
	
}
