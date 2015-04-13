package com.mnt.time.daoImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Project;
import models.Task;
import models.Timesheet;
import models.TimesheetDays;
import models.TimesheetRow;
import models.User;
import models.UserLeave;

import org.codehaus.jackson.JsonNode;
import org.springframework.stereotype.Service;

import play.libs.Json;
import viewmodel.DayVM;
import viewmodel.LeaveDay;
import viewmodel.LeaveMonth;
import viewmodel.MonthVM;
import viewmodel.ReportEvent;
import viewmodel.SchedularTodayVM;
import viewmodel.StaffLeaveVM;
import viewmodel.StaffWeekReportVM;
import viewmodel.TodayAllVM;
import viewmodel.WeekReportVM;

import com.mnt.time.dao.TimesheetDAO;

@Service
public class TimesheetDAOImpl implements TimesheetDAO {

	String[] months ={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	
	@Override
	public JsonNode getScheduleByDate(Long userId, Integer weekOfYear, Integer year, Date date) {
		User user = User.findById(userId);
		Timesheet timesheet = Timesheet.getByUserWeekAndYear(user, weekOfYear, year);
		List<SchedularTodayVM> emptyList = new ArrayList<>();
		if(timesheet != null) {
		
		List<TimesheetRow> timesheetRows = TimesheetRow.getByTimesheet(timesheet);
		List<SchedularTodayVM> vmList = new ArrayList<>();
		if(timesheetRows != null) {
			for(TimesheetRow row : timesheetRows) {
				TimesheetDays timesheetDay = TimesheetDays.findByDateAndTimesheet(date, row);
				if(timesheetDay != null) {
					if(timesheetDay.getTimeFrom() != null && timesheetDay.getTimeTo() != null) {
						SchedularTodayVM schedularTodayVM = new SchedularTodayVM();
						schedularTodayVM.id = timesheetDay.getId();
						schedularTodayVM.startTime = timesheetDay.getTimeFrom();
						schedularTodayVM.endTime = timesheetDay.getTimeTo();
						schedularTodayVM.notes = timesheet.getStatus().getName();
						schedularTodayVM.type = "A";
						if(timesheet.getStatus().getName().equals("Approved")) {
							schedularTodayVM.color = "#009933";
						} else {
							schedularTodayVM.color = "#FF7519";
						}
						Task task = Task.findByTaskCode(row.getTaskCode());
						schedularTodayVM.visitType = task.getTaskName();
						schedularTodayVM.projectId = Project.findByProjectCode(row.getProjectCode()).getId();
						schedularTodayVM.taskId = task.getId();
						vmList.add(schedularTodayVM);
					}
				}
			}
		}
			return Json.toJson(vmList);
	
		} else {
			return Json.toJson(emptyList);
		}
		
	}
	
	
	@Override
	public Map getScheduleByWeek(Long userId, Integer weekOfYear, Integer year, Date date) {
		User user = User.findById(userId);
		Timesheet timesheet = Timesheet.getByUserWeekAndYear(user, weekOfYear, year);
		Calendar cal = Calendar.getInstance();
		Date dt = new Date();
		Map map = new HashMap();
		
		if(timesheet != null) {
		
		List<TimesheetRow> timesheetRows = TimesheetRow.getByTimesheet(timesheet);
		
		if(timesheetRows != null) {
			
			String day = "";
			for(int i = 0; i<= 6; i++) {
				List<SchedularTodayVM> vmList = new ArrayList<>();
				if(i == 0) {
					day = "monday";
				}
				if(i == 1) {
					day = "tuesday";
				}
				if(i == 2) {
					day = "wednesday";
				}
				if(i == 3) {
					day = "thursday";
				}
				if(i == 4) {
					day = "friday";
				}
				if(i == 5) {
					day = "saturday";
				}
				if(i == 6) {
					day = "sunday";
				}
				
				
				for(TimesheetRow row : timesheetRows) {
					
					TimesheetDays timesheetDayObj = TimesheetDays.findByDayAndTimesheet(day, row);
							if(timesheetDayObj.getTimeFrom() != null && timesheetDayObj.getTimeTo() != null) {
								SchedularTodayVM schedularTodayVM = new SchedularTodayVM();
								dt = timesheetDayObj.getTimesheetDate();
								schedularTodayVM.id = timesheetDayObj.getId();
								schedularTodayVM.startTime = timesheetDayObj.getTimeFrom();
								schedularTodayVM.endTime = timesheetDayObj.getTimeTo();
								schedularTodayVM.notes = timesheet.getStatus().getName();
								schedularTodayVM.type = "A";
								if(timesheet.getStatus().getName().equals("Approved")) {
									schedularTodayVM.color = "#009933";
								} else {
									schedularTodayVM.color = "#FF7519";
								}
								Task task = Task.findByTaskCode(row.getTaskCode());
								schedularTodayVM.visitType = task.getTaskName();
								schedularTodayVM.projectId = Project.findByProjectCode(row.getProjectCode()).getId();
								schedularTodayVM.taskId = task.getId();
								vmList.add(schedularTodayVM);
							}
							
						}
				
						cal.setTime(dt);
						int dateInt = cal.get(Calendar.DATE);
						int month = cal.get(Calendar.MONTH)+1;
						String d = dateInt<10?"0"+dateInt:dateInt+"";
						String m = month<10?"0"+month:month+"";
						if(vmList.size()>0) {
							map.put(d+"/"+m+"/"+cal.get(Calendar.YEAR),vmList);
						}
			
			}
		}
			map.put("0", new ArrayList());
			
	
		}
		
		return map;
	}
	
	
	@Override
	public MonthVM getScheduleByMonth(Long userId, Integer weekOfYear, Integer year, Date date) {
		
		MonthVM monthVM = new MonthVM();
		
		User user = User.findById(userId);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		monthVM.setMonthIndex(cal.get(Calendar.MONTH));
		monthVM.setMonthName(months[cal.get(Calendar.MONTH)] + " " + year);
		monthVM.setYear(year);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int weekStart = cal.get(Calendar.WEEK_OF_YEAR);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		int weekEnd = cal.get(Calendar.WEEK_OF_YEAR);
		List<DayVM> dayVMList = new ArrayList<DayVM>();
		
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int monthStart = (cal.get(Calendar.DAY_OF_WEEK)+(7-((cal.get(Calendar.DATE))%7)))%7;
		
		for(int i=0;i<monthStart;i++) {
			DayVM dayVM = new DayVM();
			dayVM.setAppoinmentCount(0);
			dayVM.setAssigned(false);
			dayVM.setDay(" ");
			dayVMList.add(dayVM);
		}	
		
		List<DayVM> monthDays = new ArrayList<DayVM>();
		for(int day = 1; day <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); day++) {
			DayVM dayVM = new DayVM();
			dayVM.setAppoinmentCount(0);
			dayVM.setAssigned(true);
			dayVM.setDay(""+day);
			monthDays.add(dayVM);
		}
		
		for(int week = weekStart; week<= weekEnd; week++) {
		
			Timesheet timesheet = Timesheet.getByUserWeekAndYear(user, week, year);
			
			if(timesheet != null) {
				
				List<TimesheetRow> timesheetRows = TimesheetRow.getByTimesheet(timesheet);
				
				if(timesheetRows != null) {
					for(int i = 1; i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
						cal.set(Calendar.DAY_OF_MONTH, i);
						
						for(TimesheetRow row : timesheetRows) {
							TimesheetDays timesheetDay = TimesheetDays.findByDateAndTimesheet(cal.getTime(), row);
							if(timesheetDay != null) {
								if(timesheetDay.getTimeFrom() != null && timesheetDay.getTimeTo() != null) {
									monthDays.get(i-1).appoinmentCount += 1;
									monthDays.get(i-1).assigned = true;
								}
							}
						}
						
					}
				}
			}
		
		}	
		
		for(DayVM dayVM: monthDays) {
			dayVMList.add(dayVM);
		}
		
		for(int i=monthStart+cal.getActualMaximum(Calendar.DAY_OF_MONTH);i<42;i++) {
			DayVM dayVM = new DayVM();
			dayVM.setAppoinmentCount(0);
			dayVM.setAssigned(false);
			dayVM.setDay(" ");
			dayVMList.add(dayVM);
		}
		
		monthVM.setDays(dayVMList);
		
		return monthVM;
	}	
	
	@Override
	public List getUserMonthLeaves(User user) {
		List<LeaveMonth> leaveMonthList = new ArrayList<LeaveMonth>();
		
		try {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			c.set(Calendar.DATE, 1);
			int year = c.get(Calendar.YEAR);
			boolean isLeap=false;
			if(year%100 != 0 && year%200 != 0 && year%300 != 0){
				if(year%4==0){
					isLeap = true;
				}
			}
			for(int i=0;i<12;i++) {
				int monthStart = (c.get(Calendar.DAY_OF_WEEK)+(7-((c.get(Calendar.DATE))%7)))%7;
				int monthIndex = c.get(Calendar.MONTH);
				year = c.get(Calendar.YEAR);
				LeaveMonth leaveMonth = new LeaveMonth();
				leaveMonth.setMonthIndex(monthIndex);
				leaveMonth.setMonthName(months[monthIndex]);
				switch(monthIndex){
					case 1: 
						if(isLeap){
							leaveMonth.setMonthDays(getDayList(user,monthStart,monthIndex,year,29));
						} else
						{
							leaveMonth.setMonthDays(getDayList(user,monthStart,monthIndex,year,28));
						}
						break;
					case 3:
					case 5:
					case 8:
					case 10: leaveMonth.setMonthDays(getDayList(user,monthStart,monthIndex,year,30));
						break;
					default :leaveMonth.setMonthDays(getDayList(user,monthStart,monthIndex,year,31));
						break;
				}
				leaveMonth.setYear(year);
				leaveMonthList.add(leaveMonth);
				c.add(Calendar.MONTH, 1);
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
		return leaveMonthList;
	}
	
	public List<LeaveDay> getDayList(User user,int monthStart,int monthIndex,int monthYear,int monthDays) throws Exception{
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, monthIndex);
		c.set(Calendar.YEAR, monthYear);
		c.set(Calendar.DATE, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Calendar current = Calendar.getInstance();
		current.setTime(c.getTime());
		
		Calendar nextMonth = Calendar.getInstance();
		nextMonth.setTime(c.getTime());
		nextMonth.add(Calendar.MONTH, 1);
		c.add(Calendar.DATE, -1);
		
		List<LeaveDay> leaveDayList = new ArrayList<LeaveDay>();
		
		List<UserLeave> userLeaveList = UserLeave.getUserLeaves(user,c.getTime(),nextMonth.getTime());
		List<UserLeave> userLeaveFixedPreviousList = UserLeave.getUserFixedYearlyLeaves(user,c.getTime());
		List<UserLeave> userLeavePreviousList = UserLeave.getUserPreviousLeaves(user,c.getTime());
		List<UserLeave> userWeeklyLeaveList = UserLeave.getUserWeeklyLeaveList(user);
		List<UserLeave> userFixedLeaveList = UserLeave.getUserFixedLeaveList(user,c.getTime(),nextMonth.getTime());
		
		for(int i=0;i<monthStart;i++){
			LeaveDay ld = new LeaveDay();
			ld.setDay("");
			ld.setIsLeave(false);
			ld.setReason("");
			ld.setLeaveType(9);
			leaveDayList.add(ld);
		}
		int day = 1;
		for(int i = monthStart; i < monthDays+monthStart; i++) {		
			boolean flag= true;
			for(UserLeave dl:userLeaveFixedPreviousList){
				c.setTime(dl.getToDate());
				c.add(Calendar.DATE, 1);
				Calendar c1 = Calendar.getInstance();
				c1.setTime(dl.getFromDate());
				Calendar c2 = Calendar.getInstance();
				c2.setTime(c.getTime());
				if(c.getTimeInMillis() > c2.getTimeInMillis() && c2.getTimeInMillis() >= c1.getTimeInMillis()){
					LeaveDay ld = new LeaveDay();
					ld.setDay(""+(day++));
					ld.setIsLeave(true);
					ld.setLeaveType(dl.getLeaveType());
					ld.setReason(dl.getReason());
					leaveDayList.add(ld);
					flag = false;
					break;
				}
			}
			if(flag) {
				for(UserLeave dl : userFixedLeaveList) {
					c.setTime(dl.getFromDate());
					if(day == c.get(Calendar.DATE)) {
						LeaveDay ld = new LeaveDay();
						ld.setDay(""+(day++));
						ld.setIsLeave(true);
						ld.setLeaveType(dl.getLeaveType());
						ld.setReason(dl.getReason());
						leaveDayList.add(ld);
						flag = false;
						break;
					} else if(dl.getToDate() != null ) {
						c.setTime(dl.getToDate());
						c.add(Calendar.DATE, 1);
						Calendar c1 = Calendar.getInstance();
						c1.setTime(dl.getFromDate());
						Calendar c2 = Calendar.getInstance();
						c2.setTime(c.getTime());
						if(c.getTimeInMillis()>c2.getTimeInMillis() && c2.getTimeInMillis()>=c1.getTimeInMillis()){
							LeaveDay ld = new LeaveDay();
							ld.setDay(""+(day++));
							ld.setIsLeave(true);
							ld.setLeaveType(dl.getLeaveType());
							ld.setReason(dl.getReason());
							leaveDayList.add(ld);
							flag = false;
							break;
						}
					}
				}
			}
			if(flag) {
				for(UserLeave dl: userLeavePreviousList) {
					c.setTime(dl.getToDate());
					c.add(Calendar.DATE, 1);
					Calendar c1 = Calendar.getInstance();
					c1.setTime(dl.getFromDate());
					Calendar c2 = Calendar.getInstance();
					c2.setTime(c.getTime());
					if(c.getTimeInMillis()>c2.getTimeInMillis() && c2.getTimeInMillis()>=c1.getTimeInMillis()){
						LeaveDay ld = new LeaveDay();
						ld.setDay(""+(day++));
						ld.setIsLeave(true);
						ld.setLeaveType(dl.getLeaveType());
						ld.setReason(dl.getReason());
						leaveDayList.add(ld);
						flag = false;
						break;
					}
				}
			}
			if(flag && day <= monthDays){
				for(UserLeave dl : userLeaveList) {
					c.setTime(dl.getFromDate());
					if(day == c.get(Calendar.DATE)) {
						LeaveDay ld = new LeaveDay();
						ld.setDay(""+(day++));
						ld.setIsLeave(true);
						ld.setLeaveType(dl.getLeaveType());
						ld.setReason(dl.getReason());
						leaveDayList.add(ld);
						flag= false;
						break;
					} else if(dl.getToDate() != null ) {
						c.setTime(dl.getToDate());
						c.add(Calendar.DATE, 1);
						Calendar c1 = Calendar.getInstance();
						c1.setTime(dl.getFromDate());
						Calendar c2 = Calendar.getInstance();
						c2.setTime(c.getTime());
						if(c.getTimeInMillis()>c2.getTimeInMillis() && c2.getTimeInMillis()>=c1.getTimeInMillis()){
							LeaveDay ld = new LeaveDay();
							ld.setDay(""+(day++));
							ld.setIsLeave(true);
							ld.setLeaveType(dl.getLeaveType());
							ld.setReason(dl.getReason());
							leaveDayList.add(ld);
							flag = false;
							break;
						}
					} 
				}
			}
			if(flag && day <= monthDays) {
				for(UserLeave dfl : userWeeklyLeaveList){
					if(dfl.getLeaveType()==(i%7)) {
						LeaveDay ld = new LeaveDay();
						ld.setDay(""+(day++));
						ld.setIsLeave(true);
						ld.setLeaveType(dfl.getLeaveType());
						ld.setReason(dfl.getReason());
						leaveDayList.add(ld);
						flag= false;
						break;
					}
				}
			}
			if(flag && day <= monthDays) {
				LeaveDay ld = new LeaveDay();
				ld.setDay(""+(day++));
				ld.setIsLeave(false);
				ld.setReason("");
				ld.setLeaveType(9);
				leaveDayList.add(ld);
			}
		}
		for(int i=monthDays+monthStart;i<42;i++){
			LeaveDay ld = new LeaveDay();
			ld.setDay("");
			ld.setIsLeave(false);
			ld.setReason("");
			ld.setLeaveType(9);
			leaveDayList.add(ld);
		}
		
		
		return leaveDayList;
	}
	
	@Override
	public List getUserWeek(User user) {
		
		List<Boolean> resultList = new ArrayList<Boolean>();
		try {
			List<UserLeave> userFixedLeaves = UserLeave.getUserWeeklyLeaveList(user);

			for(int i=0;i<7;i++) {
				boolean flag = true;
				for(UserLeave dl: userFixedLeaves) {
					if(dl.getLeaveType() == i){
						resultList.add(true);
						flag = false;
						break;
					}
				}
				if(flag) {
					resultList.add(false);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return resultList;
	}
	
	@Override
	public void markWeeklyLeave(Integer leaveType, User user) {
		UserLeave userLeave = UserLeave.getUserWeeklyLeave(leaveType, user);
		if(userLeave == null) {
			UserLeave leave = new UserLeave();
			leave.setUser(user);
			leave.setLeaveType(leaveType);
			leave.setReason("Weekly Leaves");
			leave.save();
		} else {
			userLeave.delete();
		}
	}
	
	@Override
	public void markLeave(StaffLeaveVM leaveVM,User user) {
		
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		
		UserLeave userLeave = new UserLeave();
		try {
			userLeave.setUser(user);	
			userLeave.setReason(leaveVM.getReason());
			userLeave.setLeaveType(leaveVM.getLeaveType());
			try {
				userLeave.setFromDate(format.parse(leaveVM.getFromDate()));
			} catch (ParseException e) {
				userLeave.setFromDate(null);
			}
			try {
				userLeave.setToDate(format.parse(leaveVM.getToDate()));
			} catch (ParseException e) {
				userLeave.setToDate(null);
			}
			userLeave.setStatus("un-assigned");
			userLeave.save();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public List getTodayAllTimesheet(Integer weekOfYear, Integer year, User user, Date date) {
		List<User> users = User.findByManager(user);
		List<TodayAllVM> todayAllVMList = new ArrayList<>();
		for(User userObj: users) {
			TodayAllVM todayAllVM = new TodayAllVM();
			todayAllVM.id = userObj.getId();
			todayAllVM.name = userObj.getFirstName()+" "+userObj.getLastName();
			Timesheet timesheet = Timesheet.getByUserWeekAndYear(userObj, weekOfYear, year);
			
			List<SchedularTodayVM> vmList = new ArrayList<>();
			
			if(timesheet != null) {
			
			List<TimesheetRow> timesheetRows = TimesheetRow.getByTimesheet(timesheet);
			
			if(timesheetRows != null) {
				for(TimesheetRow row : timesheetRows) {
					TimesheetDays timesheetDay = TimesheetDays.findByDateAndTimesheet(date, row);
					if(timesheetDay != null) {
						if(timesheetDay.getTimeFrom() != null && timesheetDay.getTimeTo() != null) {
							SchedularTodayVM schedularTodayVM = new SchedularTodayVM();
							schedularTodayVM.id = timesheetDay.getId();
							schedularTodayVM.startTime = timesheetDay.getTimeFrom();
							schedularTodayVM.endTime = timesheetDay.getTimeTo();
							schedularTodayVM.notes = timesheet.getStatus().getName();
							schedularTodayVM.type = "A";
							if(timesheet.getStatus().getName().equals("Approved")) {
								schedularTodayVM.color = "#009933";
							} else {
								schedularTodayVM.color = "#FF7519";
							}
							Task task = Task.findByTaskCode(row.getTaskCode());
							schedularTodayVM.visitType = task.getTaskName();
							schedularTodayVM.projectId = Project.findByProjectCode(row.getProjectCode()).getId();
							schedularTodayVM.taskId = task.getId();
							vmList.add(schedularTodayVM);
						}
					}
				}
			}
			
			todayAllVM.data = vmList;
		  } else {
			  SchedularTodayVM schedularTodayVM = new SchedularTodayVM();
			  schedularTodayVM.startTime = "00:00";
			  schedularTodayVM.endTime = "00:00";
			  vmList.add(schedularTodayVM);
			  todayAllVM.data = vmList;
		  }
			todayAllVMList.add(todayAllVM);
	   }		
		return todayAllVMList;
	}
	
	@Override
	public List getWeekReport(Integer weekOfYear, Integer year, User user, Date date) {
		List<User> users = User.findByManager(user);
		List<StaffWeekReportVM> staffWeekReportList = new ArrayList<>();
		
		for(User userObj: users) {
			
			StaffWeekReportVM reportVM = new StaffWeekReportVM();
			reportVM.staffId = userObj.getId();
			reportVM.name = userObj.getFirstName()+" "+userObj.getLastName();
			reportVM.week = weekOfYear;
			reportVM.year = year;
			
			Timesheet timesheet = Timesheet.getByUserWeekAndYear(userObj, weekOfYear, year);
			
			if(timesheet != null) {
				List<TimesheetRow> timesheetRows = TimesheetRow.getByTimesheet(timesheet);
				if(timesheetRows != null) {
						String day = "";
						List<WeekReportVM> weekReportList = new ArrayList<>();
						for(int i=0;i<=6;i++) {
							if(i == 0) {
								day = "monday";
							}
							if(i == 1) {
								day = "tuesday";
							}
							if(i == 2) {
								day = "wednesday";
							}
							if(i == 3) {
								day = "thursday";
							}
							if(i == 4) {
								day = "friday";
							}
							if(i == 5) {
								day = "saturday";
							}
							if(i == 6) {
								day = "sunday";
							}
							WeekReportVM weekReportVM = new WeekReportVM();
							int hours = 0,totalmins = 0;
							for(TimesheetRow row : timesheetRows) {
								TimesheetDays timesheetDay = TimesheetDays.findByDayAndTimesheet(day, row);
								DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
								weekReportVM.date = df.format(timesheetDay.getTimesheetDate());
								if(timesheetDay.getTimeFrom() != null && timesheetDay.getTimeTo() != null) {
									totalmins = totalmins + timesheetDay.getWorkMinutes();
								}
							}
							hours = totalmins/60;
							int percent = (hours*100)/9;
							List<ReportEvent> reportEventList = new ArrayList<>();
							if(percent == 0) {
								ReportEvent reportEvent = new ReportEvent();
								reportEvent.setTooltip("Free Time!");
								reportEvent.setX("");
								List<Integer> y = new ArrayList<Integer>();
								y.add(100);
								reportEvent.setY(y);
								reportEvent.setColor("#FF0000");
								reportEvent.setLabel("9Hrs");
								reportEventList.add(reportEvent);
							} 
							if(percent>0 && percent<=100) {
								ReportEvent reportEvent = new ReportEvent();
								reportEvent.setTooltip("Free Time!");
								reportEvent.setX("");
								List<Integer> y = new ArrayList<Integer>();
								y.add(100-percent);
								reportEvent.setY(y);
								reportEvent.setColor("#FF0000");
								reportEvent.setLabel(9-hours+"Hrs");
								reportEventList.add(reportEvent);
								
								ReportEvent reportEvent2 = new ReportEvent();
								reportEvent2.setTooltip("Alloted Time!");
								reportEvent2.setX("");
								List<Integer> y2 = new ArrayList<Integer>();
								y2.add(percent);
								reportEvent2.setY(y2);
								reportEvent2.setColor("#009933");
								reportEvent2.setLabel(hours+"Hrs");
								reportEventList.add(reportEvent2);
							}
							weekReportVM.data = reportEventList;
							weekReportList.add(weekReportVM);
							
					}
						reportVM.weekReport = weekReportList;
				}	
			}
			
			staffWeekReportList.add(reportVM);
		}
		
		
		return staffWeekReportList;
	}
	
	
}
