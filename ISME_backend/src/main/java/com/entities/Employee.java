package com.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
@Entity
@Table(name = "Employee")

public class Employee{
	
	@Id
	@Column(name = "svnr")
	@JsonProperty("svnr")
	private long svnr;
	
	@Column(name="street")
	@JsonProperty("street")
	private String street;
	
	@Column(name="city")
	@JsonProperty("city")
	private String city;
	
	@Column(name="zip")
	@JsonProperty("zip")
	private String zip;
	
	@Column(name="hours")
	@JsonProperty("hours")
	private int hours;
	
	
	@Column(name="wage")
	@JsonProperty("wage")
	private int wage;
	
	@Column(name="qualification")
	@JsonProperty("qualification")
	private String qualification;
	
	@Column(name="role")
	@JsonProperty("role")
	private String role;
	

}
