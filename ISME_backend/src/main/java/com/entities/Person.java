package com.entities;




import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Person")
public class Person {

	@Id
	@Column(name = "svnr")
	@JsonProperty("svnr")
	private long svnr;

	

	@Column(name = "firstname")
	@JsonProperty("firstname")
	private String firstName;
	

	@Column(name = "lastname")
	@JsonProperty("lastname")
	private String lastName;
	

	@Column(name = "iban")
	@JsonProperty("iban")
	private String iban;
	

	@Column(name = "birthday")
	@JsonProperty("birthday")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date birthday;
	
	public Person() {};
	
	public Person(long svnr, String firstname, String lastname, String iban, Date birthday) {
		this.svnr=svnr;
		this.firstName = firstname;
		this.lastName = lastname;
		this.iban = iban;
		this.birthday=birthday;
	}
}