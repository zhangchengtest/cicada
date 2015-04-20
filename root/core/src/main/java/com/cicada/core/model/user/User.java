package com.cicada.core.model.user;

import com.cicada.core.model.TextualIDModel;

public class User extends TextualIDModel {

	private String name;
	private String firstName;
	private String lastName;
	private String encryptedPasswd;
	private String passwdSalt;
	private String securityQuestion;
	private String securityAnswer;
	private String email;
	private String regIP;
	private Boolean licenseAgreed;
	private UserStatusEnum status;
	private String comments;
	private String creatorId;
	private String ownerId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEncryptedPasswd() {
		return encryptedPasswd;
	}
	public void setEncryptedPasswd(String encryptedPasswd) {
		this.encryptedPasswd = encryptedPasswd;
	}
	public String getPasswdSalt() {
		return passwdSalt;
	}
	public void setPasswdSalt(String passwdSalt) {
		this.passwdSalt = passwdSalt;
	}
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	public String getSecurityAnswer() {
		return securityAnswer;
	}
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRegIP() {
		return regIP;
	}
	public void setRegIP(String regIP) {
		this.regIP = regIP;
	}
	public Boolean getLicenseAgreed() {
		return licenseAgreed;
	}
	public void setLicenseAgreed(Boolean licenseAgreed) {
		this.licenseAgreed = licenseAgreed;
	}
	public UserStatusEnum getStatus() {
		return status;
	}
	public void setStatus(UserStatusEnum status) {
		this.status = status;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [name=");
		builder.append(name);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", encryptedPasswd=");
		builder.append(encryptedPasswd);
		builder.append(", passwdSalt=");
		builder.append(passwdSalt);
		builder.append(", securityQuestion=");
		builder.append(securityQuestion);
		builder.append(", securityAnswer=");
		builder.append(securityAnswer);
		builder.append(", email=");
		builder.append(email);
		builder.append(", regIP=");
		builder.append(regIP);
		builder.append(", licenseAgreed=");
		builder.append(licenseAgreed);
		builder.append(", status=");
		builder.append(status);
		builder.append(", comments=");
		builder.append(comments);
		builder.append(", creatorId=");
		builder.append(creatorId);
		builder.append(", ownerId=");
		builder.append(ownerId);
		builder.append(", getID()=");
		builder.append(getID());
		builder.append(", getCreateDate()=");
		builder.append(getCreateDate());
		builder.append(", getUpdateDate()=");
		builder.append(getUpdateDate());
		builder.append("]");
		return builder.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((comments == null) ? 0 : comments.hashCode());
		result = prime * result
				+ ((creatorId == null) ? 0 : creatorId.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((encryptedPasswd == null) ? 0 : encryptedPasswd.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((licenseAgreed == null) ? 0 : licenseAgreed.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((ownerId == null) ? 0 : ownerId.hashCode());
		result = prime * result
				+ ((passwdSalt == null) ? 0 : passwdSalt.hashCode());
		result = prime * result + ((regIP == null) ? 0 : regIP.hashCode());
		result = prime * result
				+ ((securityAnswer == null) ? 0 : securityAnswer.hashCode());
		result = prime
				* result
				+ ((securityQuestion == null) ? 0 : securityQuestion.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (creatorId == null) {
			if (other.creatorId != null)
				return false;
		} else if (!creatorId.equals(other.creatorId))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (encryptedPasswd == null) {
			if (other.encryptedPasswd != null)
				return false;
		} else if (!encryptedPasswd.equals(other.encryptedPasswd))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (licenseAgreed == null) {
			if (other.licenseAgreed != null)
				return false;
		} else if (!licenseAgreed.equals(other.licenseAgreed))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ownerId == null) {
			if (other.ownerId != null)
				return false;
		} else if (!ownerId.equals(other.ownerId))
			return false;
		if (passwdSalt == null) {
			if (other.passwdSalt != null)
				return false;
		} else if (!passwdSalt.equals(other.passwdSalt))
			return false;
		if (regIP == null) {
			if (other.regIP != null)
				return false;
		} else if (!regIP.equals(other.regIP))
			return false;
		if (securityAnswer == null) {
			if (other.securityAnswer != null)
				return false;
		} else if (!securityAnswer.equals(other.securityAnswer))
			return false;
		if (securityQuestion == null) {
			if (other.securityQuestion != null)
				return false;
		} else if (!securityQuestion.equals(other.securityQuestion))
			return false;
		if (status != other.status)
			return false;
		return true;
	}
	
}
