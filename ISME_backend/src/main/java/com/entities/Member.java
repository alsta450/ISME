package com.entities;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Member")
@AttributeOverrides({ @AttributeOverride(name = "svnr", column = @Column(name = "svnr")) })
public class Member extends Person{
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

	@Column(name = "role")
	@JsonProperty("role")
	private String role;

	public long getSvnr() {
		return svnr;
	}

}
