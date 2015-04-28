package com.mnt.time.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import models.User;

import org.codehaus.jackson.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import viewmodel.GanttVM;
import viewmodel.MonthVM;
import viewmodel.StaffLeaveVM;

import com.mnt.time.dao.TimesheetDAO;
import com.mnt.time.service.TimesheetService;

@Service
public class TimesheetServiceImpl implements TimesheetService{

	@Autowired
	TimesheetDAO timesheetDAO;
	
	public JsonNode getScheduleByDate(Long userId, Integer weekOfYear, Integer year, Date date) {
		return timesheetDAO.getScheduleByDate(userId, weekOfYear, year, date);
	}
	
	public Map getScheduleByWeek(Long userId, Integer weekOfYear, Integer year, Date date) {
		return timesheetDAO.getScheduleByWeek(userId, weekOfYear, year, date);
	}
	
	public MonthVM getScheduleByMonth(Long userId, Integer weekOfYear, Integer year, Date date) {
		return timesheetDAO.getScheduleByMonth(userId, weekOfYear, year, date);
	}
	
	public List getUserMonthLeaves(User user) {
		return timesheetDAO.getUserMonthLeaves(user);
	}
	
	public List getUserWeek(User user) {
		return timesheetDAO.getUserWeek(user);
	}
	
	public void markWeeklyLeave(Integer leaveIndex,User user) {
		 timesheetDAO.markWeeklyLeave(leaveIndex, user);
	}
	
	public void markLeave(StaffLeaveVM leaveVM, User user) {
		 timesheetDAO.markLeave(leaveVM, user);
	}
	
	public Map getTodayAllTimesheet(Integer weekOfYear, Integer year, User user, Date date) {
		return timesheetDAO.getTodayAllTimesheet(weekOfYear, year, user, date);
	}
	
	public List getWeekReport(Integer weekOfYear, Integer year, User user, Date date) {
		return timesheetDAO.getWeekReport(weekOfYear, year, user, date);
	}

	public GanttVM getProjectData(Long id, Long typeId) {
		return timesheetDAO.getProjectData(id,typeId);
	}
	
}
