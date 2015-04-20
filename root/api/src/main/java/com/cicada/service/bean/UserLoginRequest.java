package com.cicada.service.bean;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class UserLoginRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5995136478283288788L;

	private String username;
	private String password;
	private String validateCode;

	@NotBlank(message = "{user.name.not.empty}")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@NotBlank(message = "{password.not.empty}")
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	
}
