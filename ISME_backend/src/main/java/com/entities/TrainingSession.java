package com.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
@Entity
@Table(name = "Training_session")
public class TrainingSession {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "trainings_id")
	private int trainingsID;
	
	@Column(name = "member_svnr")
	@JsonProperty("member_svnr")
	private long memberSvnr;
	
	@Column(name = "employee_svnr")
	@JsonProperty("employee_svnr")
	private long employeeSvnr;
	
	@Column(name = "price")
	@JsonProperty("price")
	private int price;
	
	@Column(name = "duration")
	@JsonProperty("duration")
	private int duration;

	@Override
	public String toString() {
		return "TrainingSession [trainingsID=" + trainingsID + ", memberSvnr=" + memberSvnr + ", employeeSvnr="
				+ employeeSvnr + ", price=" + price + ", duration=" + duration + "]";
	}
	
}
