package com.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Branch")
@IdClass(BranchID.class)
public class Branch {
	
	@Id
	@Column(name="street")
	@JsonProperty("street")
	private String street;
	
	@Id
	@Column(name="city")
	@JsonProperty("city")
	private String city;
	
	@Id
	@Column(name="zip")
	@JsonProperty("zip")
	private String zip;
	
	@Column(name="name")
	@JsonProperty("name")
	private String name;
	
	@Column(name="area")
	@JsonProperty("area")
	private String area;
	
}
