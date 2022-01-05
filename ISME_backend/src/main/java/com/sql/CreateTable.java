package com.sql;

import java.sql.ResultSet;

public interface CreateTable {
	public void createRoomTable();
	public void createBranchTable();
	public void createPersonTable();
	public void createFitnessEquipmentTable();
	public void createTutorTable();
	public void createMemberTable();
	public void createEmployeeTable();
	public void createTrainingSessionTable();
	public void createVisitTable();
	public ResultSet getBestTrainers();
	public ResultSet getLoyalMember();
}
