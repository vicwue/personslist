package com.sap.hana.cloud.sample;

import javax.persistence.*;

@Entity
@Table(name = "T_PERSON")
@NamedQuery(name = "AllPersons", query = "select p from Person p")
public class Person {

	@Id
	@GeneratedValue
	private long id;
	@Basic
	private String FirstName;
	@Basic
	private String LastName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setFirstName(String param) {
		this.FirstName = param;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setLastName(String param) {
		this.LastName = param;
	}

	public String getLastName() {
		return LastName;
	}

}