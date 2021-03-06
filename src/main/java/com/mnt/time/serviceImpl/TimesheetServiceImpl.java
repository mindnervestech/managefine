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
import viewmodel.StaffWeekReportVM;

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

	public GanttVM getProjectData(Long id) {
		return timesheetDAO.getProjectData(id);
	}
	
	public List getStageReport(Integer weekOfYear, Integer year, User user) {
		return timesheetDAO.getStageReport(weekOfYear, year, user);
	}
	
	public StaffWeekReportVM getTimesheetTaskWeekReport(Integer weekOfYear, Integer year, User user, Date date) {
		return timesheetDAO.getTimesheetTaskWeekReport(weekOfYear, year, user, date);
	}
	
	public StaffWeekReportVM getCalendarTaskWeekReport(Integer weekOfYear, Integer year, User user, Date date) {
		return timesheetDAO.getCalendarTaskWeekReport(weekOfYear, year, user, date);
	}
	
	public StaffWeekReportVM getTimesheetStageWeekReport(Integer weekOfYear, Integer year, User user) {
		return timesheetDAO.getTimesheetStageWeekReport(weekOfYear, year, user);
	}
	
	public StaffWeekReportVM getCalendarStageWeekReport(Integer weekOfYear, Integer year, User user) {
		return timesheetDAO.getCalendarStageWeekReport(weekOfYear, year, user);
	}
	
	public StaffWeekReportVM getTaskWeekTotal(Integer weekOfYear, Integer year, User user) {
		return timesheetDAO.getTaskWeekTotal(weekOfYear, year, user);
	}
	
	public StaffWeekReportVM getStageWeekTotal(Integer weekOfYear, Integer year, User user) {
		return timesheetDAO.getStageWeekTotal(weekOfYear, year, user);
	}
}
