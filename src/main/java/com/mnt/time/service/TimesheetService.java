package com.mnt.time.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import models.User;

import org.codehaus.jackson.JsonNode;

import viewmodel.MonthVM;
import viewmodel.StaffLeaveVM;

public interface TimesheetService {

	
	JsonNode getScheduleByDate(Long userId, Integer weekOfYear, Integer year, Date date);
	
	Map getScheduleByWeek(Long userId, Integer weekOfYear, Integer year, Date date);
	
	MonthVM getScheduleByMonth(Long userId, Integer weekOfYear, Integer year, Date date);
	
	List getUserMonthLeaves(User user);
	
	List getUserWeek(User user);
	
	void markWeeklyLeave(Integer leaveIndex,User user);
	
	void markLeave(StaffLeaveVM leaveVM, User user);
}
