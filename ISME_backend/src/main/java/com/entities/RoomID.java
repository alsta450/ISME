package com.entities;

import java.io.Serializable;
import java.util.Objects;

public class RoomID implements Serializable {
	private int roomNumber;
	private String street;
	private String city;
	private String zip;

	public RoomID(int roomNumber, String street, String city, String zip)  {
		this.roomNumber=roomNumber;
		this.street = street;
		this.city = city;
		this.zip = zip;
	}

	public RoomID() {
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, roomNumber, street, zip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomID other = (RoomID) obj;
		return Objects.equals(city, other.city) && roomNumber == other.roomNumber
				&& Objects.equals(street, other.street) && Objects.equals(zip, other.zip);
	}
	
	
}
