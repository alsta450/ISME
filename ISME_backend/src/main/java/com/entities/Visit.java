package com.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Visit")
@IdClass(VisitID.class)
public class Visit implements Serializable{
	@Id
	@Column(name = "member_svnr")
	@JsonProperty("svnr")
	private long memberSvnr;

	@Id
	@Column(name = "street")
	@JsonProperty("street")
	private String street;

	@Id
	@Column(name = "city")
	@JsonProperty("city")
	private String city;

	@Id
	@Column(name = "zip")
	@JsonProperty("zip")
	private String zip;

	public Visit(long memberSvnr, String street, String city, String zip) {
		super();
		this.memberSvnr = memberSvnr;
		this.street = street;
		this.city = city;
		this.zip = zip;
	}

	public Visit() {
		super();
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getZip() {
		return zip;
	}
	
	
	
}
