package com.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Fitness_equipment")
public class FitnessEquipment {

	@Id
	@Column(name = "description")
	@JsonProperty("description")
	private String description;

	@Column(name = "muscle_group")
	@JsonProperty("muscleGroup")
	private String muscleGroup;

	@Column(name = "fitness_type ")
	@JsonProperty("fitnessType")
	private String fitnessType;

	@Column(name = "street")
	@JsonProperty("street")
	private String street;

	@Column(name = "city")
	@JsonProperty("city")
	private String city;

	@Column(name = "zip")
	@JsonProperty("zip")
	private String zip;

}
