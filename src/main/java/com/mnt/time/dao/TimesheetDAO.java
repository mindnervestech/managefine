package com.mnt.time.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import models.User;

import org.codehaus.jackson.JsonNode;

import viewmodel.GanttVM;
import viewmodel.MonthVM;
import viewmodel.StaffLeaveVM;

public interface TimesheetDAO {
	
	JsonNode getScheduleByDate(Long userId, Integer weekOfYear, Integer year, Date date);
	
	Map getScheduleByWeek(Long userId, Integer weekOfYear, Integer year, Date date);
	
	MonthVM getScheduleByMonth(Long userId, Integer weekOfYear, Integer year, Date date);
	
	List getUserMonthLeaves(User user);
	
	List getUserWeek(User user);
	
	void markWeeklyLeave(Integer leaveIndex,User user);
	
	void markLeave(StaffLeaveVM leaveVM, User user);
	
	Map getTodayAllTimesheet(Integer weekOfYear, Integer year, User user, Date date);
	
	List getWeekReport(Integer weekOfYear, Integer year, User user, Date date);
	
	GanttVM getProjectData(Long id);
}
