package com.entities;

import java.util.Objects;
import java.io.Serializable;


public class BranchID implements Serializable {
	private String street;
	private String city;
	private String zip;

	public BranchID(String street, String city, String zip)  {
		this.street = street;
		this.city = city;
		this.zip = zip;
	}

	public BranchID() {
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, street, zip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BranchID other = (BranchID) obj;
		return Objects.equals(city, other.city) && Objects.equals(street, other.street)
				&& Objects.equals(zip, other.zip);
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
