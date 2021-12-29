package com.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Tutor")
public class Tutor {
	
	@Id
	@Column(name = "svnr")
	@JsonProperty("svnr")
	private long svnr;
	
	@Column(name = "employee_svnr")
	@JsonProperty("employee_svnr")
	private long employee_svnr;
}
