package com.entities;

public class PersonNameSvnr {
	private Long svnr;
	private String firstName;
	public PersonNameSvnr(Long svnr, String firstName) {
		super();
		this.svnr = svnr;
		this.firstName = firstName;
	}
	public Long getSvnr() {
		return svnr;
	}
	public String getFirstName() {
		return firstName;
	}
	
	
	
}
