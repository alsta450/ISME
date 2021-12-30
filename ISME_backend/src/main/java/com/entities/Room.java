package com.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Room")
@IdClass(RoomID.class)
public class Room implements Serializable{
	@Id
	@Column(name = "room_number")
	@JsonProperty("roomNumber")
	private int roomNumber;

	
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
	
	@Column(name="interior")
	@JsonProperty("interior")
	private String interior;

	
}
