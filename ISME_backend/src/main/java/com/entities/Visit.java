package com.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Visit")
@IdClass(VisitID.class)
public class Visit {
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
}
