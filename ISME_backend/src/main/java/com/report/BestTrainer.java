package com.report;

import java.util.SortedSet;
import java.util.TreeSet;

public class BestTrainer {
	private String trainerName;
	private String zip;
	private String street;
	private String city;
	private String branchName;
	private int totalSessions;
	private SortedSet<String> memberName = new TreeSet<String>();

	public BestTrainer(String trainerName, String zip, String street, String city, String branchName,
			int totalSessions) {
		super();
		this.trainerName = trainerName;
		this.zip = zip;
		this.street = street;
		this.city = city;
		this.branchName = branchName;
		this.totalSessions = totalSessions;
	}

	public SortedSet<String> getMemberName() {
		return memberName;
	}

	public void addMemberName(String name) {
		memberName.add(name);
	}

	public String getTrainerName() {
		return trainerName;
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

	public int getTotalSessions() {
		return totalSessions;
	}

}
