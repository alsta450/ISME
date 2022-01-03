package com.report;

import java.util.ArrayList;
import java.util.List;

public class LoyalMember {
	private String memberName;
	private String zip;
	private String street;
	private String city;
	private String branchName;
	private int totalPrice;
	private int totalSessions;
	private List<String> trainerName = new ArrayList<String>();

	public LoyalMember(String memberName, String zip, String street, String city, String branchName, int totalPrice,
			int totalSessions) {
		super();
		this.memberName = memberName;
		this.zip = zip;
		this.street = street;
		this.city = city;
		this.branchName = branchName;
		this.totalPrice = totalPrice;
		this.totalSessions = totalSessions;
	}

	public String getMemberName() {
		return memberName;
	}

	public String getZip() {
		return zip;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getBranchName() {
		return branchName;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public int getTotalSessions() {
		return totalSessions;
	}

	public List<String> getTrainerName() {
		return trainerName;
	}

	public void addTrainerName(String name) {
		trainerName.add(name);
	}

}
