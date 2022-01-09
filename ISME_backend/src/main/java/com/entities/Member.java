package com.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Member")

public class Member{
	
	@Id
	@Column(name = "svnr")
	@JsonProperty("svnr")
	private long svnr;

	@Column(name = "abo")
	@JsonProperty("abo")
	private String abo;

	@Column(name = "fee")
	@JsonProperty("fee")
	private int fee;


	public long getSvnr() {
		return svnr;
	}

}
