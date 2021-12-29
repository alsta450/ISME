package com.entities;

import java.io.Serializable;
import java.util.Objects;

public class VisitID implements Serializable {
	private long memberSvnr;
	private String street;
	private String city;
	private String zip;

	public VisitID(long memberSvnr, String street, String city, String zip) {
		super();
		this.memberSvnr = memberSvnr;
		this.street = street;
		this.city = city;
		this.zip = zip;
	}

	public VisitID() {
		super();
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, street, memberSvnr, zip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VisitID other = (VisitID) obj;
		return Objects.equals(city, other.city) && Objects.equals(street, other.street) && memberSvnr == other.memberSvnr
				&& Objects.equals(zip, other.zip);
	}

}
