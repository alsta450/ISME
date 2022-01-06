package com.entities;

public class TrainingSessionsForMember {
	private String member_name;
	private String trainer_name;
	private int price;
	private int duration;
	
	
	public TrainingSessionsForMember(String member_name, String trainer_name, int price, int duration) {
		super();
		this.member_name = member_name;
		this.trainer_name = trainer_name;
		this.price = price;
		this.duration = duration;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getTrainer_name() {
		return trainer_name;
	}
	public void setTrainer_name(String trainer_name) {
		this.trainer_name = trainer_name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
	
	
}
