package net.bqc.uetchat.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FbUser {

	@JsonProperty("first_name")
	private String firstName;
	
	@JsonProperty("last_name")
	private String lastName;
	
	@JsonProperty("gender")
	private String gender = "M";
	
	public FbUser() {
		// TODO Auto-generated constructor stub
	}
	
	public FbUser(String firstName, String lastName, String gender) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "FbUser [firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender + "]";
	};
	
}
