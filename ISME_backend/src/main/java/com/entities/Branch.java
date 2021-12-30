package com.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Branch")
@IdClass(BranchID.class)
public class Branch implements Serializable{
	
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

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getZip() {
		return zip;
	}

	public String getName() {
		return name;
	}

	public String getArea() {
		return area;
	}

	@Override
	public String toString() {
		return "Branch [street=" + street + ", city=" + city + ", zip=" + zip + ", name=" + name + ", area=" + area
				+ "]";
	}

	public Branch(String street, String city, String zip, String name, String area) {
		super();
		this.street = street;
		this.city = city;
		this.zip = zip;
		this.name = name;
		this.area = area;
	}

	public Branch() {
		super();
	}
	
	
}
