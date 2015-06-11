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

import models.Client;
import models.Supplier;
import models.Timesheet;
import models.TimesheetActual;
import models.TimesheetDays;
import models.TimesheetDaysActual;
import models.TimesheetRow;
import models.TimesheetRowActual;
import models.User;
import models.UserLeave;

import org.codehaus.jackson.JsonNode;
import org.springframework.stereotype.Service;

import play.libs.Json;
import viewmodel.DayVM;
import viewmodel.GanttTask;
import viewmodel.GanttVM;
import viewmodel.LeaveDay;
import viewmodel.LeaveMonth;
import viewmodel.MonthVM;
import viewmodel.ReportEvent;
import viewmodel.SchedularTodayVM;
import viewmodel.StaffLeaveVM;
import viewmodel.StaffWeekReportVM;
import viewmodel.TodayAllVM;
import viewmodel.WeekReportVM;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import com.mnt.createProject.model.Projectinstance;
import com.mnt.createProject.model.Projectinstancenode;
import com.mnt.orghierarchy.model.Organization;
import com.mnt.projectHierarchy.model.Projectclassnode;
import com.mnt.time.dao.TimesheetDAO;

@Service
public class TimesheetDAOImpl implements TimesheetDAO {

	String[] months ={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	
	@Override
	public JsonNode getScheduleByDate(Long userId, Integer weekOfYear, Integer year, Date date) {
		User user = User.findById(userId);
		Boolean flag = false;
		List<UserLeave> userLeaveList = UserLeave.getUserWeeklyLeaveList();
		UserLeave leave = UserLeave.getLeave(date);
		if(leave != null) {
			List<Organization> orgList = leave.getOrganizations();
			for(Organization org : orgList) {
				if(user.getOrganization().getId() == org.getId()) {
					flag = true;
				}
			}	
			
		} else {
			flag = false;
			if(userLeaveList.size() != 0) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				for(UserLeave userLeave: userLeaveList) {
					if(userLeave.getLeaveType() == cal.get(Calendar.DAY_OF_WEEK)-1) {
						flag = true;
					} 
				}
			}	
		}
		Map map = new HashMap<>();
		Boolean isHoliday = false;
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
						schedularTodayVM.notes = timesheet.getStatus().getName();
						
						if(leave != null) {
							List<Organization> orgList = leave.getOrganizations();
							for(Organization org : orgList) {
								if(user.getOrganization().getId() == org.getId()) {
									isHoliday = true;
									schedularTodayVM.startTime = "00:00";
									schedularTodayVM.endTime = "24:00";
									schedularTodayVM.type = "L";
									schedularTodayVM.color = "#d3d3d3";
									schedularTodayVM.visitType = "Holiday!"+leave.getReason();
								}
							}
							
						} else {
							isHoliday = false;
							if(userLeaveList.size() != 0) {
								Calendar cal = Calendar.getInstance();
								cal.setTime(date);
								for(UserLeave userLeave: userLeaveList) {
									if(userLeave.getLeaveType() == cal.get(Calendar.DAY_OF_WEEK)-1) {
										isHoliday = true;
										schedularTodayVM.startTime = "00:00";
										schedularTodayVM.endTime = "24:00";
										schedularTodayVM.type = "L";
										schedularTodayVM.color = "#d3d3d3";
										schedularTodayVM.visitType = "Weekly Leave!";
									} 
								}
							}	
						}
						
						Projectclassnode task = Projectclassnode.getProjectById(Long.parseLong(row.getTaskCode()));
						
						if(isHoliday == false) {
							schedularTodayVM.startTime = timesheetDay.getTimeFrom();
							schedularTodayVM.endTime = timesheetDay.getTimeTo();
							schedularTodayVM.type = "A";
							schedularTodayVM.visitType = Projectinstance.getById(Long.parseLong(row.getProjectCode())).getProjectName();
							if(task.getProjectColor() != null) {
								schedularTodayVM.color = task.getProjectColor();
							} else {
								schedularTodayVM.color = "#FF7519";
							}
							Projectinstancenode instance = Projectinstancenode.getByClassNodeAndInstance(task, Long.parseLong(row.getProjectCode()));
							schedularTodayVM.status = instance.getStatus();
							schedularTodayVM.taskCode = task.getProjectTypes();
							schedularTodayVM.projectId = Long.parseLong(row.getProjectCode());
							schedularTodayVM.taskId = task.getId();
							if(timesheetDay.getSupplierId() != null) {
								schedularTodayVM.supplier = Supplier.findById(Long.parseLong(timesheetDay.getSupplierId())).getSupplierName();
							}
							if(timesheetDay.getCustomerId() != null) {
								schedularTodayVM.customer = Client.findById(Long.parseLong(timesheetDay.getCustomerId())).getClientName();
							}
							if(timesheetDay.getNotes() != null) {
								schedularTodayVM.todayNotes = timesheetDay.getNotes();
							}
						}
						
						vmList.add(schedularTodayVM);
					} else {
						SchedularTodayVM schedularTodayVM = new SchedularTodayVM();
						schedularTodayVM.id = timesheetDay.getId();
						schedularTodayVM.notes = timesheet.getStatus().getName();
						
						if(leave != null) {
							if(vmList.size() == 0) {
								List<Organization> orgList = leave.getOrganizations();
								for(Organization org : orgList) {
									if(user.getOrganization().getId() == org.getId()) {
										schedularTodayVM.startTime = "00:00";
										schedularTodayVM.endTime = "24:00";
										schedularTodayVM.type = "L";
										schedularTodayVM.color = "#d3d3d3";
										schedularTodayVM.visitType = "Holiday!"+leave.getReason();
									}
								}
								vmList.add(schedularTodayVM);
							}
							
						} else {
							if(userLeaveList.size() != 0) {
								Calendar cal = Calendar.getInstance();
								cal.setTime(date);
								if(vmList.size() == 0) {
									for(UserLeave userLeave: userLeaveList) {
										if(userLeave.getLeaveType() == cal.get(Calendar.DAY_OF_WEEK)-1) {
											schedularTodayVM.startTime = "00:00";
											schedularTodayVM.endTime = "24:00";
											schedularTodayVM.type = "L";
											schedularTodayVM.color = "#d3d3d3";
											schedularTodayVM.visitType = "Weekly Leave!";
											vmList.add(schedularTodayVM);
										} 
									}
								}
							}	
						}
						
					}
				}
			}
		}
		

		SchedularTodayVM schedularTodayVM = new SchedularTodayVM();
		
		if(leave != null) {
			List<Organization> orgList = leave.getOrganizations();
			for(Organization org : orgList) {
				if(user.getOrganization().getId() == org.getId()) {
					schedularTodayVM.startTime = "00:00";
					schedularTodayVM.endTime = "24:00";
					schedularTodayVM.type = "L";
					schedularTodayVM.color = "#d3d3d3";
					schedularTodayVM.visitType = "Holiday!"+leave.getReason();
				}
			}
			vmList.add(schedularTodayVM);
		} else {
			if(userLeaveList.size() != 0) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				for(UserLeave userLeave: userLeaveList) {
					if(userLeave.getLeaveType() == cal.get(Calendar.DAY_OF_WEEK)-1) {
						schedularTodayVM.startTime = "00:00";
						schedularTodayVM.endTime = "24:00";
						schedularTodayVM.type = "L";
						schedularTodayVM.color = "#d3d3d3";
						schedularTodayVM.visitType = "Weekly Leave!";
						vmList.add(schedularTodayVM);
					} 
				}
			}	
		}
		
			map.put("isHoliday", flag);
			map.put("todayData", vmList);
			return Json.toJson(map);
	
		} else {
			
			SchedularTodayVM schedularTodayVM = new SchedularTodayVM();
			
			if(leave != null) {
				List<Organization> orgList = leave.getOrganizations();
				for(Organization org : orgList) {
					if(user.getOrganization().getId() == org.getId()) {
						schedularTodayVM.startTime = "00:00";
						schedularTodayVM.endTime = "24:00";
						schedularTodayVM.type = "L";
						schedularTodayVM.color = "#d3d3d3";
						schedularTodayVM.visitType = "Holiday!"+leave.getReason();
					}
				}
				emptyList.add(schedularTodayVM);
			} else {
				if(userLeaveList.size() != 0) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					for(UserLeave userLeave: userLeaveList) {
						if(userLeave.getLeaveType() == cal.get(Calendar.DAY_OF_WEEK)-1) {
							schedularTodayVM.startTime = "00:00";
							schedularTodayVM.endTime = "24:00";
							schedularTodayVM.type = "L";
							schedularTodayVM.color = "#d3d3d3";
							schedularTodayVM.visitType = "Weekly Leave!";
							emptyList.add(schedularTodayVM);
						} 
					}
				}	
			}
			
			map.put("isHoliday", flag);
			map.put("todayData", emptyList);
			return Json.toJson(map);
		}
		
	}
	
	
	@Override
	public Map getScheduleByWeek(Long userId, Integer weekOfYear, Integer year, Date date) {
		User user = User.findById(userId);
		Boolean isHoliday = false;
		List<UserLeave> userLeaveList = UserLeave.getUserWeeklyLeaveList();
		
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
					UserLeave leave = UserLeave.getLeave(timesheetDayObj.getTimesheetDate());
							if(timesheetDayObj.getTimeFrom() != null && timesheetDayObj.getTimeTo() != null) {
								SchedularTodayVM schedularTodayVM = new SchedularTodayVM();
								dt = timesheetDayObj.getTimesheetDate();
								schedularTodayVM.id = timesheetDayObj.getId();
								schedularTodayVM.notes = timesheet.getStatus().getName();
								
								if(leave != null) {
									if(vmList.size() == 0) {
										List<Organization> orgList = leave.getOrganizations();
										for(Organization org : orgList) {
											if(user.getOrganization().getId() == org.getId()) {
												isHoliday = true;
												schedularTodayVM.startTime = "00:00";
												schedularTodayVM.endTime = "24:00";
												schedularTodayVM.type = "L";
												schedularTodayVM.color = "#d3d3d3";
												schedularTodayVM.visitType = "Holiday!"+leave.getReason();
											}
										}
										vmList.add(schedularTodayVM);
									}
								} else {
									isHoliday = false;
									if(userLeaveList.size() != 0) {
										Calendar cal2 = Calendar.getInstance();
										cal2.setTime(timesheetDayObj.getTimesheetDate());
										for(UserLeave userLeave: userLeaveList) {
											if(userLeave.getLeaveType() == cal2.get(Calendar.DAY_OF_WEEK)-1) {
												isHoliday = true;
												if(vmList.size() == 0) {
													schedularTodayVM.startTime = "00:00";
													schedularTodayVM.endTime = "24:00";
													schedularTodayVM.type = "L";
													schedularTodayVM.color = "#d3d3d3";
													schedularTodayVM.visitType = "Weekly Leave!";
													vmList.add(schedularTodayVM);
												}
											} 
										}
									}	
								}
								
								Projectclassnode task = Projectclassnode.getProjectById(Long.parseLong(row.getTaskCode()));
								
								if(isHoliday == false) {
									schedularTodayVM.startTime = timesheetDayObj.getTimeFrom();
									schedularTodayVM.endTime = timesheetDayObj.getTimeTo();
									schedularTodayVM.type = "A";
									schedularTodayVM.visitType = Projectinstance.getById(Long.parseLong(row.getProjectCode())).getProjectName();
									if(task.getProjectColor() != null) {
										schedularTodayVM.color = task.getProjectColor();
									} else {
										schedularTodayVM.color = "#FF7519";
									}
									Projectinstancenode instance = Projectinstancenode.getByClassNodeAndInstance(task, Long.parseLong(row.getProjectCode()));
									schedularTodayVM.status = instance.getStatus();
									schedularTodayVM.taskCode = task.getProjectTypes();
									schedularTodayVM.projectId = Long.parseLong(row.getProjectCode());
									schedularTodayVM.taskId = task.getId();
									if(timesheetDayObj.getSupplierId() != null) {
										schedularTodayVM.supplier = Supplier.findById(Long.parseLong(timesheetDayObj.getSupplierId())).getSupplierName();
									}
									if(timesheetDayObj.getCustomerId() != null) {
										schedularTodayVM.customer = Client.findById(Long.parseLong(timesheetDayObj.getCustomerId())).getClientName();
									}
									if(timesheetDayObj.getNotes() != null) {
										schedularTodayVM.todayNotes = timesheetDayObj.getNotes();
									}
									vmList.add(schedularTodayVM);
								}
								
								
							} else {

								SchedularTodayVM schedularTodayVM = new SchedularTodayVM();
								schedularTodayVM.id = timesheetDayObj.getId();
								schedularTodayVM.notes = timesheet.getStatus().getName();
								dt = timesheetDayObj.getTimesheetDate();
								
								if(leave != null) {
									if(vmList.size() == 0) {
										List<Organization> orgList = leave.getOrganizations();
										for(Organization org : orgList) {
											if(user.getOrganization().getId() == org.getId()) {
												schedularTodayVM.startTime = "00:00";
												schedularTodayVM.endTime = "24:00";
												schedularTodayVM.type = "L";
												schedularTodayVM.color = "#d3d3d3";
												schedularTodayVM.visitType = "Holiday!"+leave.getReason();
											}
										}
										vmList.add(schedularTodayVM);
									}
									
								} else {
									if(userLeaveList.size() != 0) {
										Calendar cal3 = Calendar.getInstance();
										cal3.setTime(timesheetDayObj.getTimesheetDate());
										if(vmList.size() == 0) {
											for(UserLeave userLeave: userLeaveList) {
												if(userLeave.getLeaveType() == cal3.get(Calendar.DAY_OF_WEEK)-1) {
													schedularTodayVM.startTime = "00:00";
													schedularTodayVM.endTime = "24:00";
													schedularTodayVM.type = "L";
													schedularTodayVM.color = "#d3d3d3";
													schedularTodayVM.visitType = "Weekly Leave!";
													vmList.add(schedularTodayVM);
												} 
											}
										}
									}	
								}
							
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
			
	
		} else {
			cal.set(Calendar.WEEK_OF_YEAR, weekOfYear);
			cal.set(Calendar.YEAR, year);
			for(int i = 0;i<= 6; i++) {
				List<SchedularTodayVM> vmList = new ArrayList<>();
				if(i == 0) {
					cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				}
				if(i == 1) {
					cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
				}
				if(i == 2) {
					cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
				}
				if(i == 3) {
					cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
				}
				if(i == 4) {
					cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
				}
				if(i == 5) {
					cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
				}
				if(i == 6) {
					cal.set(Calendar.WEEK_OF_YEAR, weekOfYear+1);
					cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				}
				
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String dy = sdf.format(cal.getTime());
				
				UserLeave leave = null;
				try {
					leave = UserLeave.getLeave(sdf.parse(dy));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				SchedularTodayVM schedularTodayVM = new SchedularTodayVM();
				
				if(leave != null) {
					if(vmList.size() == 0) {
						List<Organization> orgList = leave.getOrganizations();
						for(Organization org : orgList) {
							if(user.getOrganization().getId() == org.getId()) {
								isHoliday = true;
								schedularTodayVM.startTime = "00:00";
								schedularTodayVM.endTime = "24:00";
								schedularTodayVM.type = "L";
								schedularTodayVM.color = "#d3d3d3";
								schedularTodayVM.visitType = "Holiday!"+leave.getReason();
							}
						}
						vmList.add(schedularTodayVM);
					}
				} else {
					if(userLeaveList.size() != 0) {
						
						for(UserLeave userLeave: userLeaveList) {
							if(userLeave.getLeaveType() == cal.get(Calendar.DAY_OF_WEEK)-1) {
								isHoliday = true;
								if(vmList.size() == 0) {
									schedularTodayVM.startTime = "00:00";
									schedularTodayVM.endTime = "24:00";
									schedularTodayVM.type = "L";
									schedularTodayVM.color = "#d3d3d3";
									schedularTodayVM.visitType = "Weekly Leave!";
									vmList.add(schedularTodayVM);
								}
							} 
						}
					}	
				}
				
				int dateInt = cal.get(Calendar.DATE);
				int month = cal.get(Calendar.MONTH)+1;
				String d = dateInt<10?"0"+dateInt:dateInt+"";
				String m = month<10?"0"+month:month+"";
				if(vmList.size()>0) {
					map.put(d+"/"+m+"/"+cal.get(Calendar.YEAR),vmList);
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
		
		List<UserLeave> userLeaveList = UserLeave.getUserWeeklyLeaveList();
		
		for(int i=0;i<monthStart;i++) {
			DayVM dayVM = new DayVM();
			dayVM.setAppoinmentCount(0);
			dayVM.setAssigned(false);
			dayVM.isHoliday = false;
			dayVM.setDay(" ");
			dayVMList.add(dayVM);
		}	
		
		List<DayVM> monthDays = new ArrayList<DayVM>();
		Calendar calObj = Calendar.getInstance();
		calObj.set(Calendar.YEAR, year);
		calObj.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		for(int day = 1; day <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); day++) {
			DayVM dayVM = new DayVM();
			dayVM.setAppoinmentCount(0);
			dayVM.setAssigned(true);
			calObj.set(Calendar.DAY_OF_MONTH, day);
			calObj.set(Calendar.HOUR, 0);
			calObj.set(Calendar.MINUTE, 0);
			calObj.set(Calendar.SECOND, 0);
			calObj.set(Calendar.HOUR_OF_DAY, 0);
			UserLeave leave = UserLeave.getLeave(calObj.getTime());
			dayVM.isHoliday = false;
			if(leave != null) {
				List<Organization> orgList = leave.getOrganizations();
				for(Organization org : orgList) {
					if(user.getOrganization().getId() == org.getId()) {
						dayVM.isHoliday = true;
					}
				}	
				
			} else {
				if(userLeaveList.size() != 0) {
					for(UserLeave userLeave: userLeaveList) {
						if(userLeave.getLeaveType() == (calObj.get(Calendar.DAY_OF_WEEK)-1)) {
							dayVM.isHoliday = true;
						}
					}
				}
			}
			
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
			dayVM.isHoliday = false;
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
		
		List<UserLeave> userLeaveList = UserLeave.getUserLeaves(c.getTime(),nextMonth.getTime());
		List<UserLeave> userLeaveFixedPreviousList = UserLeave.getUserFixedYearlyLeaves(c.getTime());
		List<UserLeave> userLeavePreviousList = UserLeave.getUserPreviousLeaves(c.getTime());
		List<UserLeave> userWeeklyLeaveList = UserLeave.getUserWeeklyLeaveList();
		List<UserLeave> userFixedLeaveList = UserLeave.getUserFixedLeaveList(c.getTime(),nextMonth.getTime());
		
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
					List<Organization> orgList = dl.getOrganizations();
					if(orgList.size()>1 || dl.getReason().equals("Weekly Leaves")) {
						ld.setOrgId(0L);
					}
					if(orgList.size() == 1) {
						ld.setOrgId(orgList.get(0).getId());
					}
					
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
						List<Organization> orgList = dl.getOrganizations();
						if(orgList.size()>1 || dl.getReason().equals("Weekly Leaves")) {
							ld.setOrgId(0L);
						} 
						if(orgList.size() == 1) {
							ld.setOrgId(orgList.get(0).getId());
						}
						leaveDayList.add(ld);
						flag = false;
						break;
					} 
				}
			}
			if(flag) {
				for(UserLeave dl: userLeavePreviousList) {
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
						List<Organization> orgList = dl.getOrganizations();
						if(orgList.size()>1 || dl.getReason().equals("Weekly Leaves")) {
							ld.setOrgId(0L);
						} 
						if(orgList.size() == 1) {
							ld.setOrgId(orgList.get(0).getId());
						}
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
						List<Organization> orgList = dl.getOrganizations();
						if(orgList.size()>1 || dl.getReason().equals("Weekly Leaves")) {
							ld.setOrgId(0L);
						} 
						if(orgList.size() == 1) {
							ld.setOrgId(orgList.get(0).getId());
						}
						leaveDayList.add(ld);
						flag= false;
						break;
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
						List<Organization> orgList = dfl.getOrganizations();
						if(orgList.size()>1 || dfl.getReason().equals("Weekly Leaves")) {
							ld.setOrgId(0L);
						} 
						if(orgList.size() == 1) {
							ld.setOrgId(orgList.get(0).getId());
						}
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
			List<UserLeave> userFixedLeaves = UserLeave.getUserWeeklyLeaveList();

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
		UserLeave userLeave = UserLeave.getUserWeeklyLeave(leaveType);
		if(userLeave == null) {
			UserLeave leave = new UserLeave();
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
			userLeave.setReason(leaveVM.getReason());
			userLeave.setLeaveType(leaveVM.getLeaveType());
			try {
				userLeave.setFromDate(format.parse(leaveVM.getFromDate()));
			} catch (ParseException e) {
				userLeave.setFromDate(null);
			}
			
			if(leaveVM.getOrganizationId() == 0) {
				userLeave.setOrganizations(Organization.getOrganizationsByCompanyId(user.getCompanyobject().getId()));
			} else {
				List<Organization> list = new ArrayList<>();
				list.add(Organization.findById(leaveVM.getOrganizationId()));
				userLeave.setOrganizations(list);
			}
			
			userLeave.save();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public Map getTodayAllTimesheet(Integer weekOfYear, Integer year, User user, Date date) {
		List<User> users = User.findByManager(user);
		
		Boolean flag = false;
		
		Map map = new HashMap<>();
		
		List<TodayAllVM> todayAllVMList = new ArrayList<>();
		for(User userObj: users) {
			TodayAllVM todayAllVM = new TodayAllVM();
			todayAllVM.id = userObj.getId();
			todayAllVM.name = userObj.getFirstName()+" "+userObj.getLastName();
			Timesheet timesheet = Timesheet.getByUserWeekAndYear(userObj, weekOfYear, year);
			Boolean isHoliday = false;
			List<SchedularTodayVM> vmList = new ArrayList<>();
			UserLeave leave = UserLeave.getLeave(date);
			List<UserLeave> userLeaveList = UserLeave.getUserWeeklyLeaveList();
			if(timesheet != null) {
			
			List<TimesheetRow> timesheetRows = TimesheetRow.getByTimesheet(timesheet);
			
			if(timesheetRows != null) {
				for(TimesheetRow row : timesheetRows) {
					TimesheetDays timesheetDay = TimesheetDays.findByDateAndTimesheet(date, row);
					
					if(timesheetDay != null) {
						if(timesheetDay.getTimeFrom() != null && timesheetDay.getTimeTo() != null) {
							SchedularTodayVM schedularTodayVM = new SchedularTodayVM();
							schedularTodayVM.id = timesheetDay.getId();
							schedularTodayVM.notes = timesheet.getStatus().getName();
							schedularTodayVM.staffId = userObj.getId().toString();
							
							if(leave != null) {
								List<Organization> orgList = leave.getOrganizations();
								for(Organization org : orgList) {
									if(user.getOrganization().getId() == org.getId()) {
										isHoliday = true;
										schedularTodayVM.startTime = "00:00";
										schedularTodayVM.endTime = "24:00";
										schedularTodayVM.type = "L";
										schedularTodayVM.color = "#d3d3d3";
										schedularTodayVM.visitType = "Holiday!"+leave.getReason();
									}
								}
								
							} else {
								isHoliday = false;
								if(userLeaveList.size() != 0) {
									Calendar cal = Calendar.getInstance();
									cal.setTime(date);
									for(UserLeave userLeave: userLeaveList) {
										if(userLeave.getLeaveType() == cal.get(Calendar.DAY_OF_WEEK)-1) {
											isHoliday = true;
											schedularTodayVM.startTime = "00:00";
											schedularTodayVM.endTime = "24:00";
											schedularTodayVM.type = "L";
											schedularTodayVM.color = "#d3d3d3";
											schedularTodayVM.visitType = "Weekly Leave!";
										} 
									}
								}	
							}
							
							Projectclassnode task = Projectclassnode.getProjectById(Long.parseLong(row.getTaskCode()));
							
							if(isHoliday == false) {
								schedularTodayVM.startTime = timesheetDay.getTimeFrom();
								schedularTodayVM.endTime = timesheetDay.getTimeTo();
								schedularTodayVM.type = "A";
								schedularTodayVM.visitType = Projectinstance.getById(Long.parseLong(row.getProjectCode())).getProjectName();
								if(task.getProjectColor() != null) {
									schedularTodayVM.color = task.getProjectColor();
								} else {
									schedularTodayVM.color = "#FF7519";
								}
								Projectinstancenode instance = Projectinstancenode.getByClassNodeAndInstance(task, Long.parseLong(row.getProjectCode()));
								schedularTodayVM.status = instance.getStatus();
								schedularTodayVM.taskCode = task.getProjectTypes();
								schedularTodayVM.projectId = Long.parseLong(row.getProjectCode());
								schedularTodayVM.taskId = task.getId();
								if(timesheetDay.getSupplierId() != null) {
									schedularTodayVM.supplier = Supplier.findById(Long.parseLong(timesheetDay.getSupplierId())).getSupplierName();
								}
								if(timesheetDay.getCustomerId() != null) {
									schedularTodayVM.customer = Client.findById(Long.parseLong(timesheetDay.getCustomerId())).getClientName();
								}
								if(timesheetDay.getNotes() != null) {
									schedularTodayVM.todayNotes = timesheetDay.getNotes();
								}
							}
							
							vmList.add(schedularTodayVM);
					
						} else {
							SchedularTodayVM schedularTodayVM = new SchedularTodayVM();
							schedularTodayVM.id = timesheetDay.getId();
							schedularTodayVM.notes = timesheet.getStatus().getName();
							schedularTodayVM.staffId = userObj.getId().toString();
							
							if(leave != null) {
								if(vmList.size() == 0) {
									List<Organization> orgList = leave.getOrganizations();
									for(Organization org : orgList) {
										if(user.getOrganization().getId() == org.getId()) {
											schedularTodayVM.startTime = "00:00";
											schedularTodayVM.endTime = "24:00";
											schedularTodayVM.type = "L";
											schedularTodayVM.color = "#d3d3d3";
											schedularTodayVM.visitType = "Holiday!"+leave.getReason();
										}
									}
									vmList.add(schedularTodayVM);
								}
							} else {
								if(userLeaveList.size() != 0) {
									Calendar cal = Calendar.getInstance();
									cal.setTime(date);
									if(vmList.size() == 0) {
										for(UserLeave userLeave: userLeaveList) {
											if(userLeave.getLeaveType() == cal.get(Calendar.DAY_OF_WEEK)-1) {
												schedularTodayVM.startTime = "00:00";
												schedularTodayVM.endTime = "24:00";
												schedularTodayVM.type = "L";
												schedularTodayVM.color = "#d3d3d3";
												schedularTodayVM.visitType = "Weekly Leave!";
												vmList.add(schedularTodayVM);
											} 
										}
									}
								}	
							}
							
						}
					}
				}
			}
			
			todayAllVM.data = vmList;
		  } else {
			  SchedularTodayVM schedularTodayVM = new SchedularTodayVM();
			  schedularTodayVM.staffId = userObj.getId().toString();
				
				if(leave != null) {
					List<Organization> orgList = leave.getOrganizations();
					for(Organization org : orgList) {
						if(user.getOrganization().getId() == org.getId()) {
							schedularTodayVM.startTime = "00:00";
							schedularTodayVM.endTime = "24:00";
							schedularTodayVM.type = "L";
							schedularTodayVM.color = "#d3d3d3";
							schedularTodayVM.visitType = "Holiday!"+leave.getReason();
						}
					}
					
				} else {
					if(userLeaveList.size() != 0) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						for(UserLeave userLeave: userLeaveList) {
							if(userLeave.getLeaveType() == cal.get(Calendar.DAY_OF_WEEK)-1) {
								schedularTodayVM.startTime = "00:00";
								schedularTodayVM.endTime = "24:00";
								schedularTodayVM.type = "L";
								schedularTodayVM.color = "#d3d3d3";
								schedularTodayVM.visitType = "Weekly Leave!";
							} 
						}
					}	
				}
			  vmList.add(schedularTodayVM);
			  todayAllVM.data = vmList;
		  }
			todayAllVMList.add(todayAllVM);
	   }		
		
		map.put("isHoliday", flag);
		map.put("todayAllData", todayAllVMList);
		return map;
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
							int hours = 0,totalPercent = 0,totalHrs = 0,percent,totalMins = 0;
							List<ReportEvent> reportEventList = new ArrayList<>();
							for(TimesheetRow row : timesheetRows) {
								TimesheetDays timesheetDay = TimesheetDays.findByDayAndTimesheet(day, row);
								DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
								weekReportVM.date = df.format(timesheetDay.getTimesheetDate());
								if(timesheetDay.getTimeFrom() != null && timesheetDay.getTimeTo() != null) {
									
									hours = timesheetDay.getWorkMinutes()/60;
									totalMins = totalMins + timesheetDay.getWorkMinutes();
									percent = (timesheetDay.getWorkMinutes()*100)/540;
									totalPercent = totalPercent + percent;
									ReportEvent reportEvent2 = new ReportEvent();
									Projectclassnode classNode = Projectclassnode.getProjectById(Long.parseLong(row.taskCode));
									Projectinstancenode instanceNode = Projectinstancenode.getByClassNodeAndInstance(classNode, Long.parseLong(row.getProjectCode()));
									reportEvent2.setTooltip(classNode.getProjectTypes());
									reportEvent2.setX("");
									List<Integer> y2 = new ArrayList<Integer>();
									y2.add(percent);
									reportEvent2.setY(y2);
									reportEvent2.setColor(classNode.getProjectColor());
									if(timesheetDay.getWorkMinutes() % 60 == 0) {
										reportEvent2.setLabel(hours+"Hrs");
									} else {
										int mins = timesheetDay.getWorkMinutes() % 60;
										if(hours == 0) {
											reportEvent2.setLabel(mins+" min");
										} else {
											reportEvent2.setLabel(hours+"."+mins+"Hrs");
										}
									}
									
									reportEventList.add(reportEvent2);
								}
							}
							
							totalHrs = (540-totalMins)/60;
							
							if(totalPercent == 0) {
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
							if(totalPercent>0 && totalPercent<=100) {
								ReportEvent reportEvent = new ReportEvent();
								reportEvent.setTooltip("Free Time!");
								reportEvent.setX("");
								List<Integer> y = new ArrayList<Integer>();
								y.add(100-totalPercent);
								reportEvent.setY(y);
								reportEvent.setColor("#FF0000");
								int min = (540-totalMins)%60;
								if(min == 0) {
									reportEvent.setLabel(totalHrs+"Hrs");
								} else {
									reportEvent.setLabel(totalHrs+"."+min+"Hrs");
								}
								
								reportEventList.add(reportEvent);
								
								
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
	
	@Override
	public StaffWeekReportVM getTimesheetTaskWeekReport(Integer weekOfYear, Integer year, User user, Date date) {
		
			StaffWeekReportVM reportVM = new StaffWeekReportVM();
			reportVM.staffId = user.getId();
			reportVM.name = user.getFirstName()+" "+user.getLastName();
			reportVM.week = weekOfYear;
			reportVM.year = year;
			
			TimesheetActual timesheet = TimesheetActual.getByUserWeekAndYear(user, weekOfYear, year);
			
			if(timesheet != null) {
				List<TimesheetRowActual> timesheetRows = TimesheetRowActual.getByTimesheet(timesheet);
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
							int hours = 0,totalPercent = 0,totalHrs = 0,percent,totalMins = 0;
							List<ReportEvent> reportEventList = new ArrayList<>();
							for(TimesheetRowActual row : timesheetRows) {
								TimesheetDaysActual timesheetDay = TimesheetDaysActual.findByDayAndTimesheet(day, row);
								DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
								weekReportVM.date = "("+df.format(timesheetDay.getTimesheetDate())+")";
								if(timesheetDay.getTimeFrom() != null && timesheetDay.getTimeTo() != null) {
									
									hours = timesheetDay.getWorkMinutes()/60;
									totalMins = totalMins + timesheetDay.getWorkMinutes();
									percent = (timesheetDay.getWorkMinutes()*100)/540;
									totalPercent = totalPercent + percent;
									ReportEvent reportEvent2 = new ReportEvent();
									Projectclassnode classNode = Projectclassnode.getProjectById(Long.parseLong(row.taskCode));
									Projectinstancenode instanceNode = Projectinstancenode.getByClassNodeAndInstance(classNode, Long.parseLong(row.getProjectCode()));
									reportEvent2.setTooltip(classNode.getProjectTypes());
									reportEvent2.setX("");
									List<Integer> y2 = new ArrayList<Integer>();
									y2.add(percent);
									reportEvent2.setY(y2);
									reportEvent2.setColor(classNode.getProjectColor());
									if(timesheetDay.getWorkMinutes() % 60 == 0) {
										reportEvent2.setLabel(hours+"Hrs");
									} else {
										int mins = timesheetDay.getWorkMinutes() % 60;
										if(hours == 0) {
											reportEvent2.setLabel(mins+" min");
										} else {
											reportEvent2.setLabel(hours+"."+mins+"Hrs");
										}
									}
									
									reportEventList.add(reportEvent2);
								}
							}
							
							totalHrs = (540-totalMins)/60;
							
							if(totalPercent == 0) {
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
							if(totalPercent>0 && totalPercent<=100) {
								ReportEvent reportEvent = new ReportEvent();
								reportEvent.setTooltip("Free Time!");
								reportEvent.setX("");
								List<Integer> y = new ArrayList<Integer>();
								y.add(100-totalPercent);
								reportEvent.setY(y);
								reportEvent.setColor("#FF0000");
								int min = (540-totalMins)%60;
								if(min == 0) {
									reportEvent.setLabel(totalHrs+"Hrs");
								} else {
									reportEvent.setLabel(totalHrs+"."+min+"Hrs");
								}
								
								reportEventList.add(reportEvent);
								
								
							}
							weekReportVM.data = reportEventList;
							weekReportList.add(weekReportVM);
							
					}
						reportVM.weekReport = weekReportList;
				}	
			}
		
		
		return reportVM;
	}
	
	@Override
	public StaffWeekReportVM getCalendarTaskWeekReport(Integer weekOfYear, Integer year, User user, Date date) {
		
			StaffWeekReportVM reportVM = new StaffWeekReportVM();
			reportVM.staffId = user.getId();
			reportVM.name = user.getFirstName()+" "+user.getLastName();
			reportVM.week = weekOfYear;
			reportVM.year = year;
			
			Timesheet timesheet = Timesheet.getByUserWeekAndYear(user, weekOfYear, year);
			
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
							int hours = 0,totalPercent = 0,totalHrs = 0,percent,totalMins = 0;
							List<ReportEvent> reportEventList = new ArrayList<>();
							for(TimesheetRow row : timesheetRows) {
								TimesheetDays timesheetDay = TimesheetDays.findByDayAndTimesheet(day, row);
								DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
								weekReportVM.date = "("+df.format(timesheetDay.getTimesheetDate())+")";
								if(timesheetDay.getTimeFrom() != null && timesheetDay.getTimeTo() != null) {
									
									hours = timesheetDay.getWorkMinutes()/60;
									totalMins = totalMins + timesheetDay.getWorkMinutes();
									percent = (timesheetDay.getWorkMinutes()*100)/540;
									totalPercent = totalPercent + percent;
									ReportEvent reportEvent2 = new ReportEvent();
									Projectclassnode classNode = Projectclassnode.getProjectById(Long.parseLong(row.taskCode));
									Projectinstancenode instanceNode = Projectinstancenode.getByClassNodeAndInstance(classNode, Long.parseLong(row.getProjectCode()));
									reportEvent2.setTooltip(classNode.getProjectTypes());
									reportEvent2.setX("");
									List<Integer> y2 = new ArrayList<Integer>();
									y2.add(percent);
									reportEvent2.setY(y2);
									reportEvent2.setColor(classNode.getProjectColor());
									if(timesheetDay.getWorkMinutes() % 60 == 0) {
										reportEvent2.setLabel(hours+"Hrs");
									} else {
										int mins = timesheetDay.getWorkMinutes() % 60;
										if(hours == 0) {
											reportEvent2.setLabel(mins+" min");
										} else {
											reportEvent2.setLabel(hours+"."+mins+"Hrs");
										}
									}
									
									reportEventList.add(reportEvent2);
								}
							}
							
							totalHrs = (540-totalMins)/60;
							
							if(totalPercent == 0) {
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
							if(totalPercent>0 && totalPercent<=100) {
								ReportEvent reportEvent = new ReportEvent();
								reportEvent.setTooltip("Free Time!");
								reportEvent.setX("");
								List<Integer> y = new ArrayList<Integer>();
								y.add(100-totalPercent);
								reportEvent.setY(y);
								reportEvent.setColor("#FF0000");
								int min = (540-totalMins)%60;
								if(min == 0) {
									reportEvent.setLabel(totalHrs+"Hrs");
								} else {
									reportEvent.setLabel(totalHrs+"."+min+"Hrs");
								}
								
								reportEventList.add(reportEvent);
								
								
							}
							weekReportVM.data = reportEventList;
							weekReportList.add(weekReportVM);
							
					}
						reportVM.weekReport = weekReportList;
				}	
			}
		
		
		return reportVM;
	}
	
	public GanttVM getProjectDataOriginal(Long id) {
		
		GanttVM ganttVM = new GanttVM();
		List<GanttTask> tasklist = new ArrayList<>();
		
		List<Projectinstancenode> projectInstanceNodeList = Projectinstancenode.getProjectInstanceById(id);
		long i = -1;
		for(Projectinstancenode node : projectInstanceNodeList) {
			GanttTask task1 = new GanttTask();
			task1.id = i;
			Projectclassnode classNode = node.getProjectclassnode();
			if(classNode.getParentId() == null) {
				task1.name = Projectinstance.getById(id).getProjectName();
			} else {
				task1.name = classNode.getProjectTypes();
			}
			task1.code = "";
			
			task1.level = classNode.getLevel();
			task1.status = node.getStatus();
			task1.canWrite = true;
			task1.start = node.getStartDate().getTime();
			task1.end = node.getEndDate().getTime();
			
			long diff = node.getEndDate().getTime() - node.getStartDate().getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);
			
			task1.duration = diffDays+1;
			task1.startIsMilestone = true;
			task1.endIsMilestone = false;
			task1.collapsed = false;
			
			List<Projectclassnode> childList = Projectclassnode.getparentByprojectId(classNode.getId());
			if(childList.isEmpty() || childList.size() == 0) {
				task1.hasChild = false;
			} else {
				task1.hasChild = true;
			}
			tasklist.add(task1);
			
			i = i - 1;
		}
		
		ganttVM.tasks = tasklist;
		ganttVM.selectedRow = 0;
		ganttVM.canWrite = false;
		ganttVM.canWriteOnParent = false;
		
		return ganttVM;
	}
	
	@Override
	public GanttVM getProjectData(Long id) {
		
		GanttVM ganttVM = new GanttVM();
		List<GanttTask> tasklist = new ArrayList<>();
		
		Projectinstancenode projectInstanceRootNode = Projectinstancenode.getInstanceRoot(id);
		
		createTask(tasklist,projectInstanceRootNode,id);
		
		ganttVM.tasks = tasklist;
		ganttVM.selectedRow = 0;
		ganttVM.canWrite = false;
		ganttVM.canWriteOnParent = false;
		
		return ganttVM;
	}


	private void createTask(List<GanttTask> tasklist , Projectinstancenode root, Long projectinstaceId) {
		
		if(root != null) {
		Projectclassnode classNode = root.getProjectclassnode();
		
		GanttTask task = new GanttTask();
		task.id = root.getId();
		task.name= classNode.getProjectTypes();
		task.level = classNode.getLevel();
		task.status = root.getStatus();
		task.canWrite = true;
		task.start = root.getStartDate().getTime();
		task.end = root.getEndDate().getTime();
		
		long diff = root.getEndDate().getTime() - root.getStartDate().getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		
		task.duration = diffDays+1;
		task.startIsMilestone = true;
		task.endIsMilestone = false;
		task.collapsed = false;
		
		List<Projectclassnode> childList = Projectclassnode.getparentByprojectId(classNode.getId());
		
		if(childList.isEmpty() || childList.size() == 0) {
			task.hasChild = false;
			tasklist.add(task);
			return;
		} else {
			
			task.hasChild = true;
			tasklist.add(task);
			
			for(Projectclassnode node : childList) {
				Projectinstancenode instancenode = Projectinstancenode.getByClassNodeAndInstance(node,projectinstaceId);
				createTask(tasklist , instancenode, projectinstaceId);
			}
		}
		
		}
		
	}
	
	@Override
	public List getStageReport(Integer weekOfYear, Integer year, User user) {
		List<User> userList = User.findByManager(user);
		List<StaffWeekReportVM> staffWeekReportList = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.WEEK_OF_YEAR,weekOfYear);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		for(User userObj: userList) {
			
			StaffWeekReportVM reportVM = new StaffWeekReportVM();
			reportVM.staffId = userObj.getId();
			reportVM.name = userObj.getFirstName()+" "+userObj.getLastName();
			reportVM.week = weekOfYear;
			reportVM.year = year;
			
			Timesheet timesheet = Timesheet.getByUserWeekAndYear(userObj, weekOfYear, year);
			if(timesheet != null) {
				List<TimesheetRow> timesheetRows = TimesheetRow.getByTimesheet(timesheet);
			if(timesheetRows != null) {
				
				List<WeekReportVM> weekReportList = new ArrayList<>();
				
				DateFormat dfm = new SimpleDateFormat("MM-dd-yyyy");
				
			for(int i=0;i<=6;i++) {
				if(i == 0) {
					cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
				}
				if(i == 1) {
					cal.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
				}
				if(i == 2) {
					cal.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
				}
				if(i == 3) {
					cal.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
				}
				if(i == 4) {
					cal.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
				}
				if(i == 5) {
					cal.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
				}
				if(i == 6) {
					cal.set(Calendar.WEEK_OF_YEAR,weekOfYear+1);
					cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
				}
		
				WeekReportVM weekReportVM = new WeekReportVM();
				weekReportVM.date = dfm.format(cal.getTime());
				List<SqlRow> dayRows = TimesheetDays.getMinutesTotalByDay(df.format(cal.getTime()), userObj.getId());
				int hours = 0,totalPercent = 0,totalHrs = 0,percent,totalMins = 0;
				List<ReportEvent> reportEventList = new ArrayList<>();
				
				for(SqlRow row: dayRows) {
					if(row.get("minutes") != null) {
					int totalMinutes = Integer.parseInt(row.get("minutes").toString());
					hours = totalMinutes/60;
					totalMins = totalMins + totalMinutes;
					percent = (totalMinutes*100)/540;
					totalPercent = totalPercent + percent;
					ReportEvent reportEvent2 = new ReportEvent();
					Projectclassnode classNode = Projectclassnode.getProjectById(Long.parseLong(row.get("stage").toString()));
					reportEvent2.setTooltip(classNode.getProjectTypes());
					reportEvent2.setX("");
					List<Integer> y2 = new ArrayList<Integer>();
					y2.add(percent);
					reportEvent2.setY(y2);
					reportEvent2.setColor(classNode.getProjectColor());
					if(totalMinutes % 60 == 0) {
						reportEvent2.setLabel(hours+"Hrs");
					} else {
						int mins = totalMinutes % 60;
						if(hours == 0) {
							reportEvent2.setLabel(mins+" min");
						} else {
							reportEvent2.setLabel(hours+"."+mins+"Hrs");
						}
					}
					
						reportEventList.add(reportEvent2);
					}
				}
			
				totalHrs = (540-totalMins)/60;
				if(totalPercent == 0) {
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
				if(totalPercent>0 && totalPercent<=100) {
					ReportEvent reportEvent = new ReportEvent();
					reportEvent.setTooltip("Free Time!");
					reportEvent.setX("");
					List<Integer> y = new ArrayList<Integer>();
					y.add(100-totalPercent);
					reportEvent.setY(y);
					reportEvent.setColor("#FF0000");
					int min = (540-totalMins)%60;
					if(min == 0) {
						reportEvent.setLabel(totalHrs+"Hrs");
					} else {
						reportEvent.setLabel(totalHrs+"."+min+"Hrs");
					}
					
					reportEventList.add(reportEvent);
					
					
				}
					weekReportVM.data = reportEventList;
					weekReportList.add(weekReportVM);
					reportVM.weekReport = weekReportList;
			}
			
			}
		  }	
			
			staffWeekReportList.add(reportVM);
				
				
		}
			
		return staffWeekReportList;
	}
	
	@Override
	public StaffWeekReportVM getCalendarStageWeekReport(Integer weekOfYear, Integer year, User user) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.WEEK_OF_YEAR,weekOfYear);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
			StaffWeekReportVM reportVM = new StaffWeekReportVM();
			reportVM.staffId = user.getId();
			reportVM.name = user.getFirstName()+" "+user.getLastName();
			reportVM.week = weekOfYear;
			reportVM.year = year;
			
			Timesheet timesheet = Timesheet.getByUserWeekAndYear(user, weekOfYear, year);
			if(timesheet != null) {
				List<TimesheetRow> timesheetRows = TimesheetRow.getByTimesheet(timesheet);
			if(timesheetRows != null) {
				
				List<WeekReportVM> weekReportList = new ArrayList<>();
				
				DateFormat dfm = new SimpleDateFormat("MM-dd-yyyy");
				
			for(int i=0;i<=6;i++) {
				if(i == 0) {
					cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
				}
				if(i == 1) {
					cal.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
				}
				if(i == 2) {
					cal.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
				}
				if(i == 3) {
					cal.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
				}
				if(i == 4) {
					cal.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
				}
				if(i == 5) {
					cal.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
				}
				if(i == 6) {
					cal.set(Calendar.WEEK_OF_YEAR,weekOfYear+1);
					cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
				}
		
				WeekReportVM weekReportVM = new WeekReportVM();
				weekReportVM.date = "("+dfm.format(cal.getTime())+")";
				List<SqlRow> dayRows = TimesheetDays.getMinutesTotalByDay(df.format(cal.getTime()), user.getId());
				int hours = 0,totalPercent = 0,totalHrs = 0,percent,totalMins = 0;
				List<ReportEvent> reportEventList = new ArrayList<>();
				
				for(SqlRow row: dayRows) {
					if(row.get("minutes") != null) {
					int totalMinutes = Integer.parseInt(row.get("minutes").toString());
					hours = totalMinutes/60;
					totalMins = totalMins + totalMinutes;
					percent = (totalMinutes*100)/540;
					totalPercent = totalPercent + percent;
					ReportEvent reportEvent2 = new ReportEvent();
					Projectclassnode classNode = Projectclassnode.getProjectById(Long.parseLong(row.get("stage").toString()));
					reportEvent2.setTooltip(classNode.getProjectTypes());
					reportEvent2.setX("");
					List<Integer> y2 = new ArrayList<Integer>();
					y2.add(percent);
					reportEvent2.setY(y2);
					reportEvent2.setColor(classNode.getProjectColor());
					if(totalMinutes % 60 == 0) {
						reportEvent2.setLabel(hours+"Hrs");
					} else {
						int mins = totalMinutes % 60;
						if(hours == 0) {
							reportEvent2.setLabel(mins+" min");
						} else {
							reportEvent2.setLabel(hours+"."+mins+"Hrs");
						}
					}
					
						reportEventList.add(reportEvent2);
					}
				}
			
				totalHrs = (540-totalMins)/60;
				if(totalPercent == 0) {
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
				if(totalPercent>0 && totalPercent<=100) {
					ReportEvent reportEvent = new ReportEvent();
					reportEvent.setTooltip("Free Time!");
					reportEvent.setX("");
					List<Integer> y = new ArrayList<Integer>();
					y.add(100-totalPercent);
					reportEvent.setY(y);
					reportEvent.setColor("#FF0000");
					int min = (540-totalMins)%60;
					if(min == 0) {
						reportEvent.setLabel(totalHrs+"Hrs");
					} else {
						reportEvent.setLabel(totalHrs+"."+min+"Hrs");
					}
					
					reportEventList.add(reportEvent);
					
					
				}
					weekReportVM.data = reportEventList;
					weekReportList.add(weekReportVM);
			}
				reportVM.weekReport = weekReportList;
			}
		  }	
			
			
		return reportVM;
	}
	
	@Override
	public StaffWeekReportVM getTimesheetStageWeekReport(Integer weekOfYear, Integer year, User user) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.WEEK_OF_YEAR,weekOfYear);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
			StaffWeekReportVM reportVM = new StaffWeekReportVM();
			reportVM.staffId = user.getId();
			reportVM.name = user.getFirstName()+" "+user.getLastName();
			reportVM.week = weekOfYear;
			reportVM.year = year;
			
			TimesheetActual timesheet = TimesheetActual.getByUserWeekAndYear(user, weekOfYear, year);
			if(timesheet != null) {
				List<TimesheetRowActual> timesheetRows = TimesheetRowActual.getByTimesheet(timesheet);
			if(timesheetRows != null) {
				
				List<WeekReportVM> weekReportList = new ArrayList<>();
				
				DateFormat dfm = new SimpleDateFormat("MM-dd-yyyy");
				
			for(int i=0;i<=6;i++) {
				if(i == 0) {
					cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
				}
				if(i == 1) {
					cal.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
				}
				if(i == 2) {
					cal.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
				}
				if(i == 3) {
					cal.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
				}
				if(i == 4) {
					cal.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
				}
				if(i == 5) {
					cal.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
				}
				if(i == 6) {
					cal.set(Calendar.WEEK_OF_YEAR,weekOfYear+1);
					cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
				}
		
				WeekReportVM weekReportVM = new WeekReportVM();
				weekReportVM.date = "("+dfm.format(cal.getTime())+")";
				List<SqlRow> dayRows = TimesheetDaysActual.getMinutesTotalByDay(df.format(cal.getTime()), user.getId());
				int hours = 0,totalPercent = 0,totalHrs = 0,percent,totalMins = 0;
				List<ReportEvent> reportEventList = new ArrayList<>();
				
				for(SqlRow row: dayRows) {
					if(row.get("minutes") != null) {
					int totalMinutes = Integer.parseInt(row.get("minutes").toString());
					hours = totalMinutes/60;
					totalMins = totalMins + totalMinutes;
					percent = (totalMinutes*100)/540;
					totalPercent = totalPercent + percent;
					ReportEvent reportEvent2 = new ReportEvent();
					Projectclassnode classNode = Projectclassnode.getProjectById(Long.parseLong(row.get("stage").toString()));
					reportEvent2.setTooltip(classNode.getProjectTypes());
					reportEvent2.setX("");
					List<Integer> y2 = new ArrayList<Integer>();
					y2.add(percent);
					reportEvent2.setY(y2);
					reportEvent2.setColor(classNode.getProjectColor());
					if(totalMinutes % 60 == 0) {
						reportEvent2.setLabel(hours+"Hrs");
					} else {
						int mins = totalMinutes % 60;
						if(hours == 0) {
							reportEvent2.setLabel(mins+" min");
						} else {
							reportEvent2.setLabel(hours+"."+mins+"Hrs");
						}
					}
					
						reportEventList.add(reportEvent2);
					}
				}
			
				totalHrs = (540-totalMins)/60;
				if(totalPercent == 0) {
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
				if(totalPercent>0 && totalPercent<=100) {
					ReportEvent reportEvent = new ReportEvent();
					reportEvent.setTooltip("Free Time!");
					reportEvent.setX("");
					List<Integer> y = new ArrayList<Integer>();
					y.add(100-totalPercent);
					reportEvent.setY(y);
					reportEvent.setColor("#FF0000");
					int min = (540-totalMins)%60;
					if(min == 0) {
						reportEvent.setLabel(totalHrs+"Hrs");
					} else {
						reportEvent.setLabel(totalHrs+"."+min+"Hrs");
					}
					
					reportEventList.add(reportEvent);
					
					
				}
					weekReportVM.data = reportEventList;
					weekReportList.add(weekReportVM);
			}
				reportVM.weekReport = weekReportList;
			}
		  }	
			
			
		return reportVM;
	}
	
	
	@Override
	public StaffWeekReportVM getTaskWeekTotal(Integer weekOfYear, Integer year, User user) {
		
			StaffWeekReportVM reportVM = new StaffWeekReportVM();
			reportVM.staffId = user.getId();
			reportVM.name = user.getFirstName()+" "+user.getLastName();
			reportVM.week = weekOfYear;
			reportVM.year = year;
			
			List<WeekReportVM> weekReportList = new ArrayList<>();
							WeekReportVM weekReportVM = new WeekReportVM();
							int hours = 0,totalPercent = 0,totalHrs = 0,percent,totalMins = 0;
							List<ReportEvent> reportEventList = new ArrayList<>();
							List<SqlRow> dayRows = TimesheetDays.getWeekReportOfTask(weekOfYear, user.getId());
								DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
								
								for(SqlRow row: dayRows) {
									if(row.get("minutes") != null) {
									int totalMinutes = Integer.parseInt(row.get("minutes").toString());
									hours = totalMinutes/60;
									totalMins = totalMins + totalMinutes;
									percent = (totalMinutes*100)/3780;
									totalPercent = totalPercent + percent;
									ReportEvent reportEvent2 = new ReportEvent();
									Projectclassnode classNode = Projectclassnode.getProjectById(Long.parseLong(row.get("task_code").toString()));
									reportEvent2.setTooltip(classNode.getProjectTypes());
									reportEvent2.setX("");
									List<Integer> y2 = new ArrayList<Integer>();
									y2.add(percent);
									reportEvent2.setY(y2);
									reportEvent2.setColor(classNode.getProjectColor());
									if(totalMinutes % 60 == 0) {
										reportEvent2.setLabel(hours+"Hrs");
									} else {
										int mins = totalMinutes % 60;
										if(hours == 0) {
											reportEvent2.setLabel(mins+" min");
										} else {
											reportEvent2.setLabel(hours+"."+mins+"Hrs");
										}
									}
									
										reportEventList.add(reportEvent2);
									}
								}
							
								totalHrs = (3780-totalMins)/60;
								if(totalPercent == 0) {
									ReportEvent reportEvent = new ReportEvent();
									reportEvent.setTooltip("Free Time!");
									reportEvent.setX("");
									List<Integer> y = new ArrayList<Integer>();
									y.add(100);
									reportEvent.setY(y);
									reportEvent.setColor("#FF0000");
									reportEvent.setLabel("63Hrs");
									reportEventList.add(reportEvent);
								} 
								if(totalPercent>0 && totalPercent<=100) {
									ReportEvent reportEvent = new ReportEvent();
									reportEvent.setTooltip("Free Time!");
									reportEvent.setX("");
									List<Integer> y = new ArrayList<Integer>();
									y.add(100-totalPercent);
									reportEvent.setY(y);
									reportEvent.setColor("#FF0000");
									int min = (3780-totalMins)%60;
									if(min == 0) {
										reportEvent.setLabel(totalHrs+"Hrs");
									} else {
										reportEvent.setLabel(totalHrs+"."+min+"Hrs");
									}
									
									reportEventList.add(reportEvent);
									
									
								}
									weekReportVM.data = reportEventList;
									weekReportList.add(weekReportVM);
							
									WeekReportVM weekReportVM2 = new WeekReportVM();
									int hours2 = 0,totalPercent2 = 0,totalHrs2 = 0,percent2,totalMins2 = 0;
									List<ReportEvent> reportEventList2 = new ArrayList<>();
									List<SqlRow> dayRows2 = TimesheetDaysActual.getWeekReportOfTask(weekOfYear, user.getId());
										
										for(SqlRow row: dayRows2) {
											if(row.get("minutes") != null) {
											int totalMinutes2 = Integer.parseInt(row.get("minutes").toString());
											hours2 = totalMinutes2/60;
											totalMins2 = totalMins2 + totalMinutes2;
											percent2 = (totalMinutes2*100)/3780;
											totalPercent2 = totalPercent2 + percent2;
											ReportEvent reportEvent2 = new ReportEvent();
											Projectclassnode classNode = Projectclassnode.getProjectById(Long.parseLong(row.get("task_code").toString()));
											reportEvent2.setTooltip(classNode.getProjectTypes());
											reportEvent2.setX("");
											List<Integer> y2 = new ArrayList<Integer>();
											y2.add(percent2);
											reportEvent2.setY(y2);
											reportEvent2.setColor(classNode.getProjectColor());
											if(totalMinutes2 % 60 == 0) {
												reportEvent2.setLabel(hours2+"Hrs");
											} else {
												int mins = totalMinutes2 % 60;
												if(hours2 == 0) {
													reportEvent2.setLabel(mins+" min");
												} else {
													reportEvent2.setLabel(hours2+"."+mins+"Hrs");
												}
											}
											
												reportEventList2.add(reportEvent2);
											}
										}
									
										totalHrs2 = (3780-totalMins2)/60;
										if(totalPercent2 == 0) {
											ReportEvent reportEvent = new ReportEvent();
											reportEvent.setTooltip("Free Time!");
											reportEvent.setX("");
											List<Integer> y = new ArrayList<Integer>();
											y.add(100);
											reportEvent.setY(y);
											reportEvent.setColor("#FF0000");
											reportEvent.setLabel("63Hrs");
											reportEventList2.add(reportEvent);
										} 
										if(totalPercent2>0 && totalPercent2<=100) {
											ReportEvent reportEvent = new ReportEvent();
											reportEvent.setTooltip("Free Time!");
											reportEvent.setX("");
											List<Integer> y = new ArrayList<Integer>();
											y.add(100-totalPercent2);
											reportEvent.setY(y);
											reportEvent.setColor("#FF0000");
											int min = (3780-totalMins2)%60;
											if(min == 0) {
												reportEvent.setLabel(totalHrs2+"Hrs");
											} else {
												reportEvent.setLabel(totalHrs2+"."+min+"Hrs");
											}
											
											reportEventList2.add(reportEvent);
											
											
										}
											weekReportVM2.data = reportEventList2;
											weekReportList.add(weekReportVM2);			
									
									
								reportVM.weekReport = weekReportList;
							
							
						return reportVM;
	}
	
	@Override
	public StaffWeekReportVM getStageWeekTotal(Integer weekOfYear, Integer year, User user) {
		
			StaffWeekReportVM reportVM = new StaffWeekReportVM();
			reportVM.staffId = user.getId();
			reportVM.name = user.getFirstName()+" "+user.getLastName();
			reportVM.week = weekOfYear;
			reportVM.year = year;
			
			List<WeekReportVM> weekReportList = new ArrayList<>();
							WeekReportVM weekReportVM = new WeekReportVM();
							int hours = 0,totalPercent = 0,totalHrs = 0,percent,totalMins = 0;
							List<ReportEvent> reportEventList = new ArrayList<>();
							List<SqlRow> dayRows = TimesheetDays.getWeekReportOfStage(weekOfYear, user.getId());
								DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
								
								for(SqlRow row: dayRows) {
									if(row.get("minutes") != null) {
									int totalMinutes = Integer.parseInt(row.get("minutes").toString());
									hours = totalMinutes/60;
									totalMins = totalMins + totalMinutes;
									percent = (totalMinutes*100)/3780;
									totalPercent = totalPercent + percent;
									ReportEvent reportEvent2 = new ReportEvent();
									Projectclassnode classNode = Projectclassnode.getProjectById(Long.parseLong(row.get("stage").toString()));
									reportEvent2.setTooltip(classNode.getProjectTypes());
									reportEvent2.setX("");
									List<Integer> y2 = new ArrayList<Integer>();
									y2.add(percent);
									reportEvent2.setY(y2);
									reportEvent2.setColor(classNode.getProjectColor());
									if(totalMinutes % 60 == 0) {
										reportEvent2.setLabel(hours+"Hrs");
									} else {
										int mins = totalMinutes % 60;
										if(hours == 0) {
											reportEvent2.setLabel(mins+" min");
										} else {
											reportEvent2.setLabel(hours+"."+mins+"Hrs");
										}
									}
									
										reportEventList.add(reportEvent2);
									}
								}
							
								totalHrs = (3780-totalMins)/60;
								if(totalPercent == 0) {
									ReportEvent reportEvent = new ReportEvent();
									reportEvent.setTooltip("Free Time!");
									reportEvent.setX("");
									List<Integer> y = new ArrayList<Integer>();
									y.add(100);
									reportEvent.setY(y);
									reportEvent.setColor("#FF0000");
									reportEvent.setLabel("63Hrs");
									reportEventList.add(reportEvent);
								} 
								if(totalPercent>0 && totalPercent<=100) {
									ReportEvent reportEvent = new ReportEvent();
									reportEvent.setTooltip("Free Time!");
									reportEvent.setX("");
									List<Integer> y = new ArrayList<Integer>();
									y.add(100-totalPercent);
									reportEvent.setY(y);
									reportEvent.setColor("#FF0000");
									int min = (3780-totalMins)%60;
									if(min == 0) {
										reportEvent.setLabel(totalHrs+"Hrs");
									} else {
										reportEvent.setLabel(totalHrs+"."+min+"Hrs");
									}
									
									reportEventList.add(reportEvent);
									
									
								}
									weekReportVM.data = reportEventList;
									weekReportList.add(weekReportVM);
							
									WeekReportVM weekReportVM2 = new WeekReportVM();
									int hours2 = 0,totalPercent2 = 0,totalHrs2 = 0,percent2,totalMins2 = 0;
									List<ReportEvent> reportEventList2 = new ArrayList<>();
									List<SqlRow> dayRows2 = TimesheetDaysActual.getWeekReportOfStage(weekOfYear, user.getId());
										
										for(SqlRow row: dayRows2) {
											if(row.get("minutes") != null) {
											int totalMinutes2 = Integer.parseInt(row.get("minutes").toString());
											hours2 = totalMinutes2/60;
											totalMins2 = totalMins2 + totalMinutes2;
											percent2 = (totalMinutes2*100)/3780;
											totalPercent2 = totalPercent2 + percent2;
											ReportEvent reportEvent2 = new ReportEvent();
											Projectclassnode classNode = Projectclassnode.getProjectById(Long.parseLong(row.get("stage").toString()));
											reportEvent2.setTooltip(classNode.getProjectTypes());
											reportEvent2.setX("");
											List<Integer> y2 = new ArrayList<Integer>();
											y2.add(percent2);
											reportEvent2.setY(y2);
											reportEvent2.setColor(classNode.getProjectColor());
											if(totalMinutes2 % 60 == 0) {
												reportEvent2.setLabel(hours2+"Hrs");
											} else {
												int mins = totalMinutes2 % 60;
												if(hours2 == 0) {
													reportEvent2.setLabel(mins+" min");
												} else {
													reportEvent2.setLabel(hours2+"."+mins+"Hrs");
												}
											}
											
												reportEventList2.add(reportEvent2);
											}
										}
									
										totalHrs2 = (3780-totalMins2)/60;
										if(totalPercent2 == 0) {
											ReportEvent reportEvent = new ReportEvent();
											reportEvent.setTooltip("Free Time!");
											reportEvent.setX("");
											List<Integer> y = new ArrayList<Integer>();
											y.add(100);
											reportEvent.setY(y);
											reportEvent.setColor("#FF0000");
											reportEvent.setLabel("63Hrs");
											reportEventList2.add(reportEvent);
										} 
										if(totalPercent2>0 && totalPercent2<=100) {
											ReportEvent reportEvent = new ReportEvent();
											reportEvent.setTooltip("Free Time!");
											reportEvent.setX("");
											List<Integer> y = new ArrayList<Integer>();
											y.add(100-totalPercent2);
											reportEvent.setY(y);
											reportEvent.setColor("#FF0000");
											int min = (3780-totalMins2)%60;
											if(min == 0) {
												reportEvent.setLabel(totalHrs2+"Hrs");
											} else {
												reportEvent.setLabel(totalHrs2+"."+min+"Hrs");
											}
											
											reportEventList2.add(reportEvent);
											
											
										}
											weekReportVM2.data = reportEventList2;
											weekReportList.add(weekReportVM2);			
									
									
								reportVM.weekReport = weekReportList;
							
							
						return reportVM;
	}
	
}
