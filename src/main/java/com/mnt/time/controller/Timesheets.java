package com.mnt.time.controller;
import static play.data.Form.form;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.MailSetting;
import models.Project;
import models.Task;
import models.Timesheet;
import models.TimesheetActual;
import models.TimesheetDays;
import models.TimesheetDaysActual;
import models.TimesheetRow;
import models.TimesheetRowActual;
import models.User;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import utils.EmailExceptionHandler;
import utils.SelectUIMap;
import viewmodel.MonthVM;
import viewmodel.ProjectVM;
import viewmodel.StaffLeaveVM;
import viewmodel.TaskVM;
import viewmodel.TimesheetRowVM;
import viewmodel.TimesheetVM;
import viewmodel.WeekDayVM;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.SqlRow;
import com.custom.domain.TimesheetStatus;
import com.custom.emails.Email;
import com.custom.exception.NoTimeSheetFoudException;
import com.custom.helpers.TimesheetSearchContext;
import com.custom.workflow.timesheet.TimesheetWorkflowUtils;
import com.mnt.time.service.TimesheetService;

import dto.fixtures.MenuBarFixture;
/*
@Security.Authenticated(Secured.class)
@BasicAuth*/

@Controller
public class Timesheets{
	
	private static HashMap<String,Integer> timesheetRowsMap = null;
	
	@Autowired
	TimesheetService timesheetService;
	
	@RequestMapping(value="/timesheetIndex", method = RequestMethod.GET)
	public String index(ModelMap model, @CookieValue("username") String username) {
		User user = User.findByEmail(username);
		Form<Timesheet> timesheetForm = form(Timesheet.class);
		
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);
		model.addAttribute("timesheetForm", timesheetForm);
		
		return "timesheetIndex";
//		return ok(timesheetIndex.render(MenuBarFixture.build(request().username()),user,timesheetForm));
    }
    
	@RequestMapping(value="/timesheetNew", method = RequestMethod.GET)
	public String timesheetNew(ModelMap model, @CookieValue("username") String username) {
		User user = User.findByEmail(username);
		Form<Timesheet> timesheetForm = form(Timesheet.class);
		
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);
		model.addAttribute("timesheetForm", timesheetForm);
		
		return "timesheetNew";
//		return ok(timesheetIndex.render(MenuBarFixture.build(request().username()),user,timesheetForm));
    }
	
	@RequestMapping(value="/schedularToday", method = RequestMethod.GET)
	public String schedularView(ModelMap model, @CookieValue("username") String username) {
		User user = User.findByEmail(username);
		
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);
		
		return "schedularToday";
    }
	
	@RequestMapping(value="/schedularTodayAll", method = RequestMethod.GET)
	public String schedularTodayAll(ModelMap model, @CookieValue("username") String username) {
		User user = User.findByEmail(username);
		
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dt = df.format(date);
		try {
			date = df.parse(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		model.addAttribute("list", Json.toJson(timesheetService.getTodayAllTimesheet(cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), user, date)));
		
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);
		
		return "schedularTodayAll";
    }
	
	@RequestMapping(value="/schedularWeekReport", method = RequestMethod.GET)
	public String schedularWeekReport(ModelMap model, @CookieValue("username") String username) {
		User user = User.findByEmail(username);
		
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dt = df.format(date);
		try {
			date = df.parse(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("weekReport", Json.toJson(timesheetService.getWeekReport(cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), user, date)));
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);
		
		return "schedularWeekReport";
    }
	
	@RequestMapping(value="/getStaffWeekReport", method = RequestMethod.GET)
	public @ResponseBody JsonNode getStaffWeekReport(ModelMap model,@RequestParam("userId") String userId,@RequestParam("date") String date) {
		User user = User.findById(Long.parseLong(userId));
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date dt = null;
		try {
			dt = df.parse(date);
			cal.setTime(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return Json.toJson(timesheetService.getWeekReport(cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), user, dt));
		
	}
	
	@RequestMapping(value="/getTodayAllByDate", method = RequestMethod.GET)
	public @ResponseBody JsonNode getTodayAllByDate(ModelMap model,@RequestParam("userId") String userId,@RequestParam("date") String date) {
		User user = User.findById(Long.parseLong(userId));
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date dt = null;
		try {
			dt = df.parse(date);
			cal.setTime(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return Json.toJson(timesheetService.getTodayAllTimesheet(cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), user, dt));
		
	}
	
	
	@RequestMapping(value="/setupHoliday", method = RequestMethod.GET)
	public String setupHoliday(ModelMap model, @CookieValue("username") String username) {
		User user = User.findByEmail(username);
		
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);
		
		return "setupHoliday";
    }
	
	@RequestMapping(value="/{dateStr}/{userId}", method = RequestMethod.GET)
	public String getTodayData(ModelMap model, @PathVariable("userId") String userId,@PathVariable("dateStr") String date) {
		
		User user = User.findById(Long.parseLong(userId));
		model.addAttribute("_menuContext", MenuBarFixture.build(user.getEmail()));
		String arr[] = date.split("-");
		String dateStr = arr[0]+"/"+arr[1]+"/"+arr[2];
		model.addAttribute("user", user);
		model.addAttribute("dateStr", dateStr);
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date dt;
		try {
			dt = df.parse(dateStr);
			cal.setTime(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("userJson",timesheetService.getScheduleByDate(Long.parseLong(userId), cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), cal.getTime()));
		
		return "schedularToday";
    }
	
	@RequestMapping(value="/getUserTodayData/{dateStr}/{userId}/{uId}", method = RequestMethod.GET)
	public String getTodayData(ModelMap model,@PathVariable("dateStr") String date,@PathVariable("userId") String userId,@PathVariable("uId") String uId) {
		
		User user = User.findById(Long.parseLong(uId));
		model.addAttribute("_menuContext", MenuBarFixture.build(user.getEmail()));
		String arr[] = date.split("-");
		String dateStr = arr[0]+"/"+arr[1]+"/"+arr[2];
		model.addAttribute("user", user);
		model.addAttribute("dateStr", dateStr);
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date dt;
		try {
			dt = df.parse(dateStr);
			cal.setTime(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("userJson",timesheetService.getScheduleByDate(Long.parseLong(userId), cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), cal.getTime()));
		
		return "schedularToday";
    }
	
	@RequestMapping(value="/schedularWeek", method = RequestMethod.GET)
	public String schedularWeek(ModelMap model, @CookieValue("username") String username) {
		User user = User.findByEmail(username);
		
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);
		
		return "schedularWeek";
    }
	
	@RequestMapping(value="/schedularMonth", method = RequestMethod.GET)
	public String schedularMonth(ModelMap model, @CookieValue("username") String username) {
		User user = User.findByEmail(username);
		
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);
		
		return "schedularMonth";
    }
	
	@RequestMapping(value="/getProjectCodes", method = RequestMethod.GET)
	public @ResponseBody JsonNode getProjectCodes(ModelMap model,@RequestParam("userId") String userId) {
		User user = User.findById(Long.parseLong(userId));
		List<SqlRow> sqlRows = Project.getProjectsOfUser(user.id);
		List<ProjectVM> vmList = new ArrayList<>();
		for(SqlRow row: sqlRows) {
			Project project = Project.findById(row.getLong("project_id"));
			ProjectVM vm = new ProjectVM();
			List<TaskVM> taskVMList = new ArrayList<>();
			List<SqlRow> taskRows = Project.getTasksOfProject(project.id);
			
			for(SqlRow taskRow : taskRows) {
				TaskVM taskVM = new TaskVM();
				Task task = Task.findById(taskRow.getLong("task_id"));
				taskVM.id = task.id;
				taskVM.taskCode = task.taskCode;
				taskVMList.add(taskVM);
			}
			vm.id = project.id;
			vm.projectCode = project.projectCode;
			vm.tasklist = taskVMList;
			vmList.add(vm);
		}
		
		return Json.toJson(vmList);
    }
	
	@RequestMapping(value="/getLeaveDetails", method = RequestMethod.GET)
	public @ResponseBody JsonNode getLeaveDetails(ModelMap model,@RequestParam("userId") String userId) {
		User user = User.findById(Long.parseLong(userId));
		Map map = new HashMap();
		map.put("leaveList", timesheetService.getUserMonthLeaves(user));
		map.put("weekList", timesheetService.getUserWeek(user));
		
		return Json.toJson(map);
	}	
	
	@RequestMapping(value = "/setWeeklyLeave", method = RequestMethod.GET)
	public @ResponseBody Map markWeeklyLeaves(HttpServletRequest request,@RequestParam("leaveType") int leaveType,@RequestParam("userId") String userId) {
		User user = User.findById(Long.parseLong(userId));
		Map map = new HashMap();
		timesheetService.markWeeklyLeave(leaveType, user);
		
		map.put("weekList", timesheetService.getUserWeek(user));
		map.put("leaveList", timesheetService.getUserMonthLeaves(user));
		return map;
	}
	
	@RequestMapping(value="/markleaves" ,method=RequestMethod.POST)
	public @ResponseBody Map markLeaves(HttpServletRequest request,@RequestBody StaffLeaveVM leaveVM) {
		User user = User.findById(leaveVM.userId);
		timesheetService.markLeave(leaveVM, user);
		Map map = new HashMap();
		map.put("leaveList", timesheetService.getUserMonthLeaves(user));
		map.put("weekList", timesheetService.getUserWeek(user));
		return map;
	}
	
	@RequestMapping(value="/deleteTimesheetRow", method = RequestMethod.GET)
	public @ResponseBody String deleteTimesheetRow(ModelMap model,@RequestParam("rowId") String rowId) {
		TimesheetRow timesheetRow = TimesheetRow.findById(Long.parseLong(rowId));
		List<TimesheetDays> daysList = TimesheetDays.getByTimesheetRow(timesheetRow);
		
		for(TimesheetDays day : daysList) {
			day.delete();
		}
		
		timesheetRow.delete();
		
		return "";
	}
	
	
	@RequestMapping(value="/deleteActualTimesheetRow", method = RequestMethod.GET)
	public @ResponseBody String deleteActualTimesheetRow(ModelMap model,@RequestParam("rowId") String rowId) {
		TimesheetRowActual timesheetRow = TimesheetRowActual.findById(Long.parseLong(rowId));
		List<TimesheetDaysActual> daysList = TimesheetDaysActual.getByTimesheetRow(timesheetRow);
		
		for(TimesheetDaysActual day : daysList) {
			day.delete();
		}
		
		timesheetRow.delete();
		
		return "";
	}
	
	
	@RequestMapping(value="/deleteTimesheet", method = RequestMethod.GET)
	public @ResponseBody String deleteTimesheet(ModelMap model,@RequestParam("timesheetId") String timesheetId) {
		Timesheet timesheet = Timesheet.findById(Long.parseLong(timesheetId));
		List<TimesheetRow> timesheetRowList = TimesheetRow.getByTimesheet(timesheet);
		for(TimesheetRow row: timesheetRowList) {
			List<TimesheetDays> daysList = TimesheetDays.getByTimesheetRow(row);
			for(TimesheetDays day : daysList) {
				day.delete();
			}
			row.delete();
		}
		timesheet.delete();
		
		return "";
	}
	
	
	@RequestMapping(value="/deleteActualTimesheet", method = RequestMethod.GET)
	public @ResponseBody String deleteActualTimesheet(ModelMap model,@RequestParam("timesheetId") String timesheetId) {
		TimesheetActual timesheet = TimesheetActual.findById(Long.parseLong(timesheetId));
		List<TimesheetRowActual> timesheetRowList = TimesheetRowActual.getByTimesheet(timesheet);
		for(TimesheetRowActual row: timesheetRowList) {
			List<TimesheetDaysActual> daysList = TimesheetDaysActual.getByTimesheetRow(row);
			for(TimesheetDaysActual day : daysList) {
				day.delete();
			}
			row.delete();
		}
		timesheet.delete();
		
		return "";
	}
	
	
	@RequestMapping(value="/getSchedularDay", method = RequestMethod.GET)
	public @ResponseBody JsonNode getSchedularDay(ModelMap model,@RequestParam("date") String date,@RequestParam("userId") String userId) {
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date dt;
		try {
			dt = df.parse(date);
			cal.setTime(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		return timesheetService.getScheduleByDate(Long.parseLong(userId), cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), cal.getTime());
	}
	
	@RequestMapping(value="/getDayDetails", method = RequestMethod.GET)
	public @ResponseBody JsonNode getDayDetails(ModelMap model,@RequestParam("date") String date,@RequestParam("userId") String userId) {
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date dt;
		try {
			dt = df.parse(date);
			cal.setTime(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		User user = User.findById(Long.parseLong(userId));
		int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
		int yearVal = cal.get(Calendar.YEAR);
		String week[] = {"sunday","monday","tuesday","wednesday","thursday","friday","saturday"};
		String day = week[cal.get(Calendar.DAY_OF_WEEK)-1];
		
		Timesheet timesheet = Timesheet.getByUserWeekAndYear(user, weekOfYear, yearVal);
		List<WeekDayVM> weekDayList = new ArrayList<>();
		if(timesheet != null) {
			List<TimesheetRow> timesheetRowList = TimesheetRow.getByTimesheet(timesheet);
			
			for(TimesheetRow row: timesheetRowList) {
				WeekDayVM vm = new WeekDayVM();
				vm.projectCode = row.getProjectCode();
				vm.taskCode = row.getTaskCode();
				TimesheetDays timesheetDay = TimesheetDays.findByDayAndTimesheet(day, row);
				vm.from = timesheetDay.getTimeFrom();
				vm.to = timesheetDay.getTimeTo();
				vm.day = day;
				if(timesheetDay.getTimeFrom() != null && timesheetDay.getTimeTo() != null) {
					weekDayList.add(vm);
				}
			}
		}
		
		return Json.toJson(weekDayList);
	}
	
	@RequestMapping(value="/getSchedularWeek", method = RequestMethod.GET)
	public @ResponseBody Map getSchedularWeek(ModelMap model,@RequestParam("date") String date,@RequestParam("userId") String userId) {
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date dt;
		try {
			dt = df.parse(date);
			cal.setTime(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		return timesheetService.getScheduleByWeek(Long.parseLong(userId), cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), cal.getTime());
	}
	
	@RequestMapping(value="/getMonthSchedule", method = RequestMethod.GET)
	public @ResponseBody Map getMonthSchedule(ModelMap model,@RequestParam("date") String date,@RequestParam("userId") String userId) {
		System.out.println(".....Date..........."+date+".........user id ........"+userId);
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("MMM yyyy");
		Date dt;
		try {
			dt = df.parse(date);
			cal.setTime(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		MonthVM monthVM = timesheetService.getScheduleByMonth(Long.parseLong(userId), cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), cal.getTime());
		
		Map map = new HashMap();
		map.put("monthVM", monthVM);
		
		return map;
	}
	
	@RequestMapping(value="/getActualTimesheetBySelectedWeek", method = RequestMethod.GET)
	public @ResponseBody JsonNode getActualTimesheetBySelectedWeek(ModelMap model,@RequestParam("userId") String userId,@RequestParam("week") String week,@RequestParam("year") String year) {
		User user = User.findById(Long.parseLong(userId));
		
		TimesheetActual timesheet = TimesheetActual.getByUserWeekAndYear(user, Integer.parseInt(week), Integer.parseInt(year));
		TimesheetVM timesheetVM = new TimesheetVM();
		
		if(timesheet != null) {
		
			List<TimesheetRowActual> timesheetRowList = TimesheetRowActual.getByTimesheet(timesheet);
			List<TimesheetRowVM> timesheetRowVMList = new ArrayList<>();
			
			timesheetVM.id = timesheet.getId();
			timesheetVM.status = timesheet.getStatus().getName();
			timesheetVM.weekOfYear = timesheet.getWeekOfYear();
			timesheetVM.year = timesheet.getYear();
			for(TimesheetRowActual timesheetRow : timesheetRowList) {
				List<TimesheetDaysActual> timesheetDaysList = TimesheetDaysActual.getByTimesheetRow(timesheetRow);
				
				TimesheetRowVM timesheetRowVM = new TimesheetRowVM();
				timesheetRowVM.rowId = timesheetRow.getId();
				timesheetRowVM.projectCode = Project.findByProjectCode(timesheetRow.getProjectCode()).getId();
				timesheetRowVM.taskCode = Task.findByTaskCode(timesheetRow.getTaskCode()).getId();
				timesheetRowVM.isOverTime = timesheetRow.isOverTime();
				
				for(TimesheetDaysActual day : timesheetDaysList) {
					if(day.getDay().equals("monday")) {
						timesheetRowVM.monFrom = day.getTimeFrom();
						timesheetRowVM.monTo = day.getTimeTo();
						timesheetRowVM.mondayId = day.getId();
					}
					if(day.getDay().equals("tuesday")) {
						timesheetRowVM.tueFrom = day.getTimeFrom();
						timesheetRowVM.tueTo = day.getTimeTo();
						timesheetRowVM.tuesdayId = day.getId();
					}
					
					if(day.getDay().equals("wednesday")) {
						timesheetRowVM.wedFrom = day.getTimeFrom();
						timesheetRowVM.wedTo = day.getTimeTo();
						timesheetRowVM.wednesdayId = day.getId();
					}
					
					if(day.getDay().equals("thursday")) {
						timesheetRowVM.thuFrom = day.getTimeFrom();
						timesheetRowVM.thuTo = day.getTimeTo();
						timesheetRowVM.thursdayId = day.getId();
					}
					if(day.getDay().equals("friday")) {
						timesheetRowVM.friFrom = day.getTimeFrom();
						timesheetRowVM.friTo = day.getTimeTo();
						timesheetRowVM.fridayId = day.getId();
					}
					if(day.getDay().equals("saturday")) {
						timesheetRowVM.satFrom = day.getTimeFrom();
						timesheetRowVM.satTo = day.getTimeTo();
						timesheetRowVM.saturdayId = day.getId();
					}
					if(day.getDay().equals("sunday")) {
						timesheetRowVM.sunFrom = day.getTimeFrom();
						timesheetRowVM.sunTo = day.getTimeTo();
						timesheetRowVM.sundayId = day.getId();
					}
					timesheetRowVM.totalmins = day.getWorkMinutes();
				}
				timesheetRowVMList.add(timesheetRowVM);
			}
			timesheetVM.timesheetRows = timesheetRowVMList;
			
			return Json.toJson(timesheetVM);
		} else {
			return Json.toJson("");
		}
	}
	
	@RequestMapping(value="/getTimesheetBySelectedWeek", method = RequestMethod.GET)
	public @ResponseBody JsonNode getTimesheetBySelectedWeek(ModelMap model,@RequestParam("userId") String userId,@RequestParam("week") String week,@RequestParam("year") String year) {
		
		User user = User.findById(Long.parseLong(userId));
		
		Timesheet timesheet = Timesheet.getByUserWeekAndYear(user, Integer.parseInt(week), Integer.parseInt(year));
		TimesheetVM timesheetVM = new TimesheetVM();
		
		if(timesheet != null) {
		
			List<TimesheetRow> timesheetRowList = TimesheetRow.getByTimesheet(timesheet);
			List<TimesheetRowVM> timesheetRowVMList = new ArrayList<>();
			
			timesheetVM.id = timesheet.getId();
			timesheetVM.status = timesheet.getStatus().getName();
			timesheetVM.weekOfYear = timesheet.getWeekOfYear();
			timesheetVM.year = timesheet.getYear();
			for(TimesheetRow timesheetRow : timesheetRowList) {
				List<TimesheetDays> timesheetDaysList = TimesheetDays.getByTimesheetRow(timesheetRow);
				
				TimesheetRowVM timesheetRowVM = new TimesheetRowVM();
				timesheetRowVM.rowId = timesheetRow.getId();
				timesheetRowVM.projectCode = Project.findByProjectCode(timesheetRow.getProjectCode()).getId();
				timesheetRowVM.taskCode = Task.findByTaskCode(timesheetRow.getTaskCode()).getId();
				timesheetRowVM.isOverTime = timesheetRow.isOverTime();
				
				for(TimesheetDays day : timesheetDaysList) {
					if(day.getDay().equals("monday")) {
						timesheetRowVM.monFrom = day.getTimeFrom();
						timesheetRowVM.monTo = day.getTimeTo();
						timesheetRowVM.mondayId = day.getId();
					}
					if(day.getDay().equals("tuesday")) {
						timesheetRowVM.tueFrom = day.getTimeFrom();
						timesheetRowVM.tueTo = day.getTimeTo();
						timesheetRowVM.tuesdayId = day.getId();
					}
					
					if(day.getDay().equals("wednesday")) {
						timesheetRowVM.wedFrom = day.getTimeFrom();
						timesheetRowVM.wedTo = day.getTimeTo();
						timesheetRowVM.wednesdayId = day.getId();
					}
					
					if(day.getDay().equals("thursday")) {
						timesheetRowVM.thuFrom = day.getTimeFrom();
						timesheetRowVM.thuTo = day.getTimeTo();
						timesheetRowVM.thursdayId = day.getId();
					}
					if(day.getDay().equals("friday")) {
						timesheetRowVM.friFrom = day.getTimeFrom();
						timesheetRowVM.friTo = day.getTimeTo();
						timesheetRowVM.fridayId = day.getId();
					}
					if(day.getDay().equals("saturday")) {
						timesheetRowVM.satFrom = day.getTimeFrom();
						timesheetRowVM.satTo = day.getTimeTo();
						timesheetRowVM.saturdayId = day.getId();
					}
					if(day.getDay().equals("sunday")) {
						timesheetRowVM.sunFrom = day.getTimeFrom();
						timesheetRowVM.sunTo = day.getTimeTo();
						timesheetRowVM.sundayId = day.getId();
					}
					timesheetRowVM.totalmins = day.getWorkMinutes();
				}
				timesheetRowVMList.add(timesheetRowVM);
			}
			timesheetVM.timesheetRows = timesheetRowVMList;
			
			return Json.toJson(timesheetVM);
		} else {
			return Json.toJson("");
		}
		
	}
	
	@RequestMapping(value="/getActualTimesheetByCurrentWeek", method = RequestMethod.GET)
	public @ResponseBody JsonNode getActualTimesheetByCurrentWeek(ModelMap model,@RequestParam("userId") String userId) {
		
		User user = User.findById(Long.parseLong(userId));
		Calendar cal = Calendar.getInstance();
		TimesheetActual timesheet = TimesheetActual.getByUserWeekAndYear(user, cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR));
		
		TimesheetVM timesheetVM = new TimesheetVM();
		
		if(timesheet != null) {
		
			List<TimesheetRowActual> timesheetRowList = TimesheetRowActual.getByTimesheet(timesheet);
			List<TimesheetRowVM> timesheetRowVMList = new ArrayList<>();
			
			timesheetVM.id = timesheet.getId();
			timesheetVM.status = timesheet.getStatus().getName();
			timesheetVM.weekOfYear = timesheet.getWeekOfYear();
			timesheetVM.year = timesheet.getYear();
			for(TimesheetRowActual timesheetRow : timesheetRowList) {
				List<TimesheetDaysActual> timesheetDaysList = TimesheetDaysActual.getByTimesheetRow(timesheetRow);
				
				TimesheetRowVM timesheetRowVM = new TimesheetRowVM();
				timesheetRowVM.rowId = timesheetRow.getId();
				timesheetRowVM.projectCode = Project.findByProjectCode(timesheetRow.getProjectCode()).getId();
				timesheetRowVM.taskCode = Task.findByTaskCode(timesheetRow.getTaskCode()).getId();
				timesheetRowVM.isOverTime = timesheetRow.isOverTime();
				
				for(TimesheetDaysActual day : timesheetDaysList) {
					if(day.getDay().equals("monday")) {
						timesheetRowVM.monFrom = day.getTimeFrom();
						timesheetRowVM.monTo = day.getTimeTo();
						timesheetRowVM.mondayId = day.getId();
					}
					if(day.getDay().equals("tuesday")) {
						timesheetRowVM.tueFrom = day.getTimeFrom();
						timesheetRowVM.tueTo = day.getTimeTo();
						timesheetRowVM.tuesdayId = day.getId();
					}
					
					if(day.getDay().equals("wednesday")) {
						timesheetRowVM.wedFrom = day.getTimeFrom();
						timesheetRowVM.wedTo = day.getTimeTo();
						timesheetRowVM.wednesdayId = day.getId();
					}
					
					if(day.getDay().equals("thursday")) {
						timesheetRowVM.thuFrom = day.getTimeFrom();
						timesheetRowVM.thuTo = day.getTimeTo();
						timesheetRowVM.thursdayId = day.getId();
					}
					if(day.getDay().equals("friday")) {
						timesheetRowVM.friFrom = day.getTimeFrom();
						timesheetRowVM.friTo = day.getTimeTo();
						timesheetRowVM.fridayId = day.getId();
					}
					if(day.getDay().equals("saturday")) {
						timesheetRowVM.satFrom = day.getTimeFrom();
						timesheetRowVM.satTo = day.getTimeTo();
						timesheetRowVM.saturdayId = day.getId();
					}
					if(day.getDay().equals("sunday")) {
						timesheetRowVM.sunFrom = day.getTimeFrom();
						timesheetRowVM.sunTo = day.getTimeTo();
						timesheetRowVM.sundayId = day.getId();
					}
					timesheetRowVM.totalmins = day.getWorkMinutes();
				}
				timesheetRowVMList.add(timesheetRowVM);
			}
			timesheetVM.timesheetRows = timesheetRowVMList;
			
			return Json.toJson(timesheetVM);
			
		} else {
			return Json.toJson("");
		}
		
		
	}
	
	
	@RequestMapping(value="/getTimesheetByCurrentWeek", method = RequestMethod.GET)
	public @ResponseBody JsonNode getTimesheetByCurrentWeek(ModelMap model,@RequestParam("userId") String userId) {
		
		User user = User.findById(Long.parseLong(userId));
		Calendar cal = Calendar.getInstance();
		Timesheet timesheet = Timesheet.getByUserWeekAndYear(user, cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR));
		
		TimesheetVM timesheetVM = new TimesheetVM();
		
		if(timesheet != null) {
		
			List<TimesheetRow> timesheetRowList = TimesheetRow.getByTimesheet(timesheet);
			List<TimesheetRowVM> timesheetRowVMList = new ArrayList<>();
			
			timesheetVM.id = timesheet.getId();
			timesheetVM.status = timesheet.getStatus().getName();
			timesheetVM.weekOfYear = timesheet.getWeekOfYear();
			timesheetVM.year = timesheet.getYear();
			for(TimesheetRow timesheetRow : timesheetRowList) {
				List<TimesheetDays> timesheetDaysList = TimesheetDays.getByTimesheetRow(timesheetRow);
				
				TimesheetRowVM timesheetRowVM = new TimesheetRowVM();
				timesheetRowVM.rowId = timesheetRow.getId();
				timesheetRowVM.projectCode = Project.findByProjectCode(timesheetRow.getProjectCode()).getId();
				timesheetRowVM.taskCode = Task.findByTaskCode(timesheetRow.getTaskCode()).getId();
				timesheetRowVM.isOverTime = timesheetRow.isOverTime();
				
				for(TimesheetDays day : timesheetDaysList) {
					if(day.getDay().equals("monday")) {
						timesheetRowVM.monFrom = day.getTimeFrom();
						timesheetRowVM.monTo = day.getTimeTo();
						timesheetRowVM.mondayId = day.getId();
					}
					if(day.getDay().equals("tuesday")) {
						timesheetRowVM.tueFrom = day.getTimeFrom();
						timesheetRowVM.tueTo = day.getTimeTo();
						timesheetRowVM.tuesdayId = day.getId();
					}
					
					if(day.getDay().equals("wednesday")) {
						timesheetRowVM.wedFrom = day.getTimeFrom();
						timesheetRowVM.wedTo = day.getTimeTo();
						timesheetRowVM.wednesdayId = day.getId();
					}
					
					if(day.getDay().equals("thursday")) {
						timesheetRowVM.thuFrom = day.getTimeFrom();
						timesheetRowVM.thuTo = day.getTimeTo();
						timesheetRowVM.thursdayId = day.getId();
					}
					if(day.getDay().equals("friday")) {
						timesheetRowVM.friFrom = day.getTimeFrom();
						timesheetRowVM.friTo = day.getTimeTo();
						timesheetRowVM.fridayId = day.getId();
					}
					if(day.getDay().equals("saturday")) {
						timesheetRowVM.satFrom = day.getTimeFrom();
						timesheetRowVM.satTo = day.getTimeTo();
						timesheetRowVM.saturdayId = day.getId();
					}
					if(day.getDay().equals("sunday")) {
						timesheetRowVM.sunFrom = day.getTimeFrom();
						timesheetRowVM.sunTo = day.getTimeTo();
						timesheetRowVM.sundayId = day.getId();
					}
					timesheetRowVM.totalmins = day.getWorkMinutes();
				}
				timesheetRowVMList.add(timesheetRowVM);
			}
			timesheetVM.timesheetRows = timesheetRowVMList;
			
			return Json.toJson(timesheetVM);
			
		} else {
			return Json.toJson("");
		}
		
		
	}
	
	@RequestMapping(value="/timesheetRetract", method = RequestMethod.GET)
	public @ResponseBody String timesheetRetract(ModelMap model,@RequestParam("userId") String userId,@RequestParam("week") String week,@RequestParam("year") String year) {
		User user = User.findById(Long.parseLong(userId));
		Timesheet timesheet = Timesheet.getByUserWeekAndYear(user, Integer.parseInt(week), Integer.parseInt(year));
		timesheet.setStatus(TimesheetStatus.Draft);
		timesheet.update();
		return "";
	}
	
	
	@RequestMapping(value="/actualTimesheetRetract", method = RequestMethod.GET)
	public @ResponseBody String actualTimesheetRetract(ModelMap model,@RequestParam("userId") String userId,@RequestParam("week") String week,@RequestParam("year") String year) {
		User user = User.findById(Long.parseLong(userId));
		TimesheetActual timesheet = TimesheetActual.getByUserWeekAndYear(user, Integer.parseInt(week), Integer.parseInt(year));
		timesheet.setStatus(TimesheetStatus.Draft);
		timesheet.update();
		return "";
	}
	
	
	
	@RequestMapping(value="/getActualTimesheetByLastWeek", method = RequestMethod.GET)
	public @ResponseBody JsonNode getActualTimesheetByLastWeek(ModelMap model,@RequestParam("userId") String userId,@RequestParam("week") String week,@RequestParam("year") String year) {
		
		User user = User.findById(Long.parseLong(userId));
		Calendar cal = Calendar.getInstance();
		int weekOfYear = Integer.parseInt(week);
		int yearVal = Integer.parseInt(year);
		if(weekOfYear == 1) {
			yearVal = yearVal - 1;
			cal.set(Calendar.YEAR,yearVal);
			cal.set(Calendar.MONTH, Calendar.DECEMBER);
		    cal.set(Calendar.DAY_OF_MONTH, 31);
		    weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
			
		} else {
			weekOfYear = weekOfYear - 1;
		}
		TimesheetActual timesheet = TimesheetActual.getByUserWeekAndYear(user, weekOfYear, yearVal);
		
		
		
		TimesheetVM timesheetVM = new TimesheetVM();
		
		if(timesheet != null) {
		
			List<TimesheetRowActual> timesheetRowList = TimesheetRowActual.getByTimesheet(timesheet);
			List<TimesheetRowVM> timesheetRowVMList = new ArrayList<>();
			Date dt = new Date();
			cal.setTime(dt);
			timesheetVM.id = timesheet.getId();
			timesheetVM.status = TimesheetStatus.Draft.getName();
			timesheetVM.weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
			timesheetVM.year = cal.get(Calendar.YEAR);
			for(TimesheetRowActual timesheetRow : timesheetRowList) {
				List<TimesheetDaysActual> timesheetDaysList = TimesheetDaysActual.getByTimesheetRow(timesheetRow);
				
				TimesheetRowVM timesheetRowVM = new TimesheetRowVM();
				timesheetRowVM.rowId = timesheetRow.getId();
				timesheetRowVM.projectCode = Project.findByProjectCode(timesheetRow.getProjectCode()).getId();
				timesheetRowVM.taskCode = Task.findByTaskCode(timesheetRow.getTaskCode()).getId();
				timesheetRowVM.isOverTime = timesheetRow.isOverTime();
				
				for(TimesheetDaysActual day : timesheetDaysList) {
					if(day.getDay().equals("monday")) {
						timesheetRowVM.monFrom = day.getTimeFrom();
						timesheetRowVM.monTo = day.getTimeTo();
						timesheetRowVM.mondayId = day.getId();
					}
					if(day.getDay().equals("tuesday")) {
						timesheetRowVM.tueFrom = day.getTimeFrom();
						timesheetRowVM.tueTo = day.getTimeTo();
						timesheetRowVM.tuesdayId = day.getId();
					}
					
					if(day.getDay().equals("wednesday")) {
						timesheetRowVM.wedFrom = day.getTimeFrom();
						timesheetRowVM.wedTo = day.getTimeTo();
						timesheetRowVM.wednesdayId = day.getId();
					}
					
					if(day.getDay().equals("thursday")) {
						timesheetRowVM.thuFrom = day.getTimeFrom();
						timesheetRowVM.thuTo = day.getTimeTo();
						timesheetRowVM.thursdayId = day.getId();
					}
					if(day.getDay().equals("friday")) {
						timesheetRowVM.friFrom = day.getTimeFrom();
						timesheetRowVM.friTo = day.getTimeTo();
						timesheetRowVM.fridayId = day.getId();
					}
					if(day.getDay().equals("saturday")) {
						timesheetRowVM.satFrom = day.getTimeFrom();
						timesheetRowVM.satTo = day.getTimeTo();
						timesheetRowVM.saturdayId = day.getId();
					}
					if(day.getDay().equals("sunday")) {
						timesheetRowVM.sunFrom = day.getTimeFrom();
						timesheetRowVM.sunTo = day.getTimeTo();
						timesheetRowVM.sundayId = day.getId();
					}
					timesheetRowVM.totalmins = day.getWorkMinutes();
				}
				timesheetRowVMList.add(timesheetRowVM);
			}
			timesheetVM.timesheetRows = timesheetRowVMList;
			
			return Json.toJson(timesheetVM);
			
		} else {
			return Json.toJson("");
		}
		
	}
	

	@RequestMapping(value="/getWeekDayData", method = RequestMethod.GET)
	public @ResponseBody JsonNode getWeekDayData(ModelMap model,@RequestParam("userId") String userId,@RequestParam("week") String week,@RequestParam("year") String year,@RequestParam("day") String day) {
		
		User user = User.findById(Long.parseLong(userId));
		Calendar cal = Calendar.getInstance();
		int weekOfYear = Integer.parseInt(week);
		int yearVal = Integer.parseInt(year);
		
		Timesheet timesheet = Timesheet.getByUserWeekAndYear(user, weekOfYear, yearVal);
		List<WeekDayVM> weekDayList = new ArrayList<>();
		if(timesheet != null) {
			List<TimesheetRow> timesheetRowList = TimesheetRow.getByTimesheet(timesheet);
			
			for(TimesheetRow row: timesheetRowList) {
				WeekDayVM vm = new WeekDayVM();
				vm.projectCode = row.getProjectCode();
				vm.taskCode = row.getTaskCode();
				TimesheetDays timesheetDay = TimesheetDays.findByDayAndTimesheet(day, row);
				vm.from = timesheetDay.getTimeFrom();
				vm.to = timesheetDay.getTimeTo();
				if(timesheetDay.getTimeFrom() != null && timesheetDay.getTimeTo() != null) {
					weekDayList.add(vm);
				}
			}
		}
		
		
		return Json.toJson(weekDayList);
	}	
	
	
	@RequestMapping(value="/getTimesheetByLastWeek", method = RequestMethod.GET)
	public @ResponseBody JsonNode getTimesheetByLastWeek(ModelMap model,@RequestParam("userId") String userId,@RequestParam("week") String week,@RequestParam("year") String year) {
		
		User user = User.findById(Long.parseLong(userId));
		Calendar cal = Calendar.getInstance();
		int weekOfYear = Integer.parseInt(week);
		int yearVal = Integer.parseInt(year);
		if(weekOfYear == 1) {
			yearVal = yearVal - 1;
			cal.set(Calendar.YEAR,yearVal);
			cal.set(Calendar.MONTH, Calendar.DECEMBER);
		    cal.set(Calendar.DAY_OF_MONTH, 31);
		    weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
			
		} else {
			weekOfYear = weekOfYear - 1;
		}
		Timesheet timesheet = Timesheet.getByUserWeekAndYear(user, weekOfYear, yearVal);
		
		
		
		TimesheetVM timesheetVM = new TimesheetVM();
		
		if(timesheet != null) {
		
			List<TimesheetRow> timesheetRowList = TimesheetRow.getByTimesheet(timesheet);
			List<TimesheetRowVM> timesheetRowVMList = new ArrayList<>();
			Date dt = new Date();
			cal.setTime(dt);
			timesheetVM.id = timesheet.getId();
			timesheetVM.status = TimesheetStatus.Draft.getName();
			timesheetVM.weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
			timesheetVM.year = cal.get(Calendar.YEAR);
			for(TimesheetRow timesheetRow : timesheetRowList) {
				List<TimesheetDays> timesheetDaysList = TimesheetDays.getByTimesheetRow(timesheetRow);
				
				TimesheetRowVM timesheetRowVM = new TimesheetRowVM();
				timesheetRowVM.rowId = timesheetRow.getId();
				timesheetRowVM.projectCode = Project.findByProjectCode(timesheetRow.getProjectCode()).getId();
				timesheetRowVM.taskCode = Task.findByTaskCode(timesheetRow.getTaskCode()).getId();
				timesheetRowVM.isOverTime = timesheetRow.isOverTime();
				
				for(TimesheetDays day : timesheetDaysList) {
					if(day.getDay().equals("monday")) {
						timesheetRowVM.monFrom = day.getTimeFrom();
						timesheetRowVM.monTo = day.getTimeTo();
						timesheetRowVM.mondayId = day.getId();
					}
					if(day.getDay().equals("tuesday")) {
						timesheetRowVM.tueFrom = day.getTimeFrom();
						timesheetRowVM.tueTo = day.getTimeTo();
						timesheetRowVM.tuesdayId = day.getId();
					}
					
					if(day.getDay().equals("wednesday")) {
						timesheetRowVM.wedFrom = day.getTimeFrom();
						timesheetRowVM.wedTo = day.getTimeTo();
						timesheetRowVM.wednesdayId = day.getId();
					}
					
					if(day.getDay().equals("thursday")) {
						timesheetRowVM.thuFrom = day.getTimeFrom();
						timesheetRowVM.thuTo = day.getTimeTo();
						timesheetRowVM.thursdayId = day.getId();
					}
					if(day.getDay().equals("friday")) {
						timesheetRowVM.friFrom = day.getTimeFrom();
						timesheetRowVM.friTo = day.getTimeTo();
						timesheetRowVM.fridayId = day.getId();
					}
					if(day.getDay().equals("saturday")) {
						timesheetRowVM.satFrom = day.getTimeFrom();
						timesheetRowVM.satTo = day.getTimeTo();
						timesheetRowVM.saturdayId = day.getId();
					}
					if(day.getDay().equals("sunday")) {
						timesheetRowVM.sunFrom = day.getTimeFrom();
						timesheetRowVM.sunTo = day.getTimeTo();
						timesheetRowVM.sundayId = day.getId();
					}
					timesheetRowVM.totalmins = day.getWorkMinutes();
				}
				timesheetRowVMList.add(timesheetRowVM);
			}
			timesheetVM.timesheetRows = timesheetRowVMList;
			
			return Json.toJson(timesheetVM);
			
		} else {
			return Json.toJson("");
		}
		
	}
	
	
	@RequestMapping(value="/saveActualTimesheet", method = RequestMethod.POST)
	public @ResponseBody String saveActualTimesheet(ModelMap model,@RequestBody TimesheetVM timesheet,HttpServletRequest request){
		
		try {
		
		User user = User.findById(timesheet.userId);
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.YEAR,timesheet.year);
		cal.set(Calendar.WEEK_OF_YEAR,timesheet.weekOfYear);
		
		TimesheetActual timesheetSavedObj = TimesheetActual.getByUserWeekAndYear(user, timesheet.weekOfYear, timesheet.year);
		
		if(timesheetSavedObj == null) {
			
		TimesheetActual timesheetObj = new TimesheetActual();
		
		timesheetObj.setUser(user);
		timesheetObj.setStatus(TimesheetStatus.valueOf(timesheet.status));
		timesheetObj.setWeekOfYear(timesheet.weekOfYear);
		timesheetObj.setYear(timesheet.year);
		if(timesheet.status.equals("Submitted")) {
			timesheetObj.setTimesheetWith(user.getManager());
		} else {
			timesheetObj.setTimesheetWith(user);
		}
		timesheetObj.save();
		
		for(TimesheetRowVM rowVM : timesheet.timesheetRows) {
			TimesheetRowActual timesheetRow = new TimesheetRowActual();
			Project project = Project.findById(rowVM.projectCode);
			timesheetRow.setProjectCode(project.projectCode);
			Task task = Task.findById(rowVM.taskCode);
			timesheetRow.setTaskCode(task.taskCode);
			timesheetRow.setTimesheetActual(timesheetObj);
			timesheetRow.setOverTime(rowVM.isOverTime);
			timesheetRow.save();
			
			
			TimesheetDaysActual monday = new TimesheetDaysActual();
			cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
			monday.setTimesheetDate(cal.getTime());
			monday.setDay("monday");
			if(!rowVM.monFrom.equals("") && !rowVM.monTo.equals("")) {
				monday.setTimeFrom(rowVM.monFrom);
				monday.setTimeTo(rowVM.monTo);
				String monFrom[] = rowVM.monFrom.split(":");
				int from = ((Integer.parseInt(monFrom[0]) * 60) + Integer.parseInt(monFrom[1]));
				String monTo[] = rowVM.monTo.split(":");
				int to = ((Integer.parseInt(monTo[0]) * 60) + Integer.parseInt(monTo[1]));
				monday.setWorkMinutes(to - from);
			}
			monday.setTimesheetRowActual(timesheetRow);
			monday.save();
			
			TimesheetDaysActual tuesday = new TimesheetDaysActual();
			cal.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
			tuesday.setTimesheetDate(cal.getTime());
			tuesday.setDay("tuesday");
			if(!rowVM.tueFrom.equals("") || !rowVM.tueTo.equals("")) {
				tuesday.setTimeFrom(rowVM.tueFrom);
				tuesday.setTimeTo(rowVM.tueTo);
				String tueFrom[] = rowVM.tueFrom.split(":");
				int from = ((Integer.parseInt(tueFrom[0]) * 60) + Integer.parseInt(tueFrom[1]));
				String tueTo[] = rowVM.tueTo.split(":");
				int to = ((Integer.parseInt(tueTo[0]) * 60) + Integer.parseInt(tueTo[1]));
				tuesday.setWorkMinutes(to - from);
			}	
			tuesday.setTimesheetRowActual(timesheetRow);
			tuesday.save();
			
			TimesheetDaysActual wednesday = new TimesheetDaysActual();
			cal.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
			wednesday.setTimesheetDate(cal.getTime());
			wednesday.setDay("wednesday");
			if(!rowVM.wedFrom.equals("") || !rowVM.wedTo.equals("")) {
				wednesday.setTimeFrom(rowVM.wedFrom);
				wednesday.setTimeTo(rowVM.wedTo);
				String wedFrom[] = rowVM.wedFrom.split(":");
				int from = ((Integer.parseInt(wedFrom[0]) * 60) + Integer.parseInt(wedFrom[1]));
				String wedTo[] = rowVM.wedTo.split(":");
				int to = ((Integer.parseInt(wedTo[0]) * 60) + Integer.parseInt(wedTo[1]));
				wednesday.setWorkMinutes(to - from);
			}
			wednesday.setTimesheetRowActual(timesheetRow);
			wednesday.save();
			
			TimesheetDaysActual thursday = new TimesheetDaysActual();
			cal.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
			thursday.setTimesheetDate(cal.getTime());
			thursday.setDay("thursday");
			if(!rowVM.thuFrom.equals("") || !rowVM.thuTo.equals("")) {
				thursday.setTimeFrom(rowVM.thuFrom);
				thursday.setTimeTo(rowVM.thuTo);
				String thuFrom[] = rowVM.thuFrom.split(":");
				int from = ((Integer.parseInt(thuFrom[0]) * 60) + Integer.parseInt(thuFrom[1]));
				String thuTo[] = rowVM.thuTo.split(":");
				int to = ((Integer.parseInt(thuTo[0]) * 60) + Integer.parseInt(thuTo[1]));
				thursday.setWorkMinutes(to - from);
			}
			thursday.setTimesheetRowActual(timesheetRow);
			thursday.save();
			
			TimesheetDaysActual friday = new TimesheetDaysActual();
			cal.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
			friday.setTimesheetDate(cal.getTime());
			friday.setDay("friday");
			if(!rowVM.friFrom.equals("") || !rowVM.friTo.equals("")) {
				friday.setTimeFrom(rowVM.friFrom);
				friday.setTimeTo(rowVM.friTo);
				String friFrom[] = rowVM.friFrom.split(":");
				int from = ((Integer.parseInt(friFrom[0]) * 60) + Integer.parseInt(friFrom[1]));
				String friTo[] = rowVM.friTo.split(":");
				int to = ((Integer.parseInt(friTo[0]) * 60) + Integer.parseInt(friTo[1]));
				friday.setWorkMinutes(to - from);
			}
			friday.setTimesheetRowActual(timesheetRow);
			friday.save();
			
			TimesheetDaysActual saturday = new TimesheetDaysActual();
			cal.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
			saturday.setTimesheetDate(cal.getTime());
			saturday.setDay("saturday");
			if(!rowVM.satFrom.equals("") || !rowVM.satTo.equals("")) {
				saturday.setTimeFrom(rowVM.satFrom);
				saturday.setTimeTo(rowVM.satTo);
				String satFrom[] = rowVM.satFrom.split(":");
				int from = ((Integer.parseInt(satFrom[0]) * 60) + Integer.parseInt(satFrom[1]));
				String satTo[] = rowVM.satTo.split(":");
				int to = ((Integer.parseInt(satTo[0]) * 60) + Integer.parseInt(satTo[1]));
				saturday.setWorkMinutes(to - from);
			}
			saturday.setTimesheetRowActual(timesheetRow);
			saturday.save();
			
			TimesheetDaysActual sunday = new TimesheetDaysActual();
			cal.set(Calendar.WEEK_OF_YEAR,timesheet.weekOfYear+1);
			cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
			sunday.setTimesheetDate(cal.getTime());
			sunday.setDay("sunday");
			if(!rowVM.sunFrom.equals("") || !rowVM.sunTo.equals("")) {
				sunday.setTimeFrom(rowVM.sunFrom);
				sunday.setTimeTo(rowVM.sunTo);
				String sunFrom[] = rowVM.sunFrom.split(":");
				int from = ((Integer.parseInt(sunFrom[0]) * 60) + Integer.parseInt(sunFrom[1]));
				String sunTo[] = rowVM.sunTo.split(":");
				int to = ((Integer.parseInt(sunTo[0]) * 60) + Integer.parseInt(sunTo[1]));
				sunday.setWorkMinutes(to - from);
			}
			sunday.setTimesheetRowActual(timesheetRow);
			sunday.save();
			
		}
		
		} else {
			timesheetSavedObj.setStatus(TimesheetStatus.valueOf(timesheet.status));
			timesheetSavedObj.update();
			
			for(TimesheetRowVM row: timesheet.timesheetRows) {
				if(row.rowId != 0L) {
				TimesheetRowActual timesheetRow =  TimesheetRowActual.findById(row.rowId);
				Project project = Project.findById(row.projectCode);
				timesheetRow.setProjectCode(project.projectCode);
				Task task = Task.findById(row.taskCode);
				timesheetRow.setTaskCode(task.taskCode);
				timesheetRow.setOverTime(row.isOverTime);
				timesheetRow.update();
				
				
				TimesheetDaysActual monday = TimesheetDaysActual.findById(row.mondayId);
				if(!row.monFrom.equals("") && !row.monTo.equals("")) {
					monday.setTimeFrom(row.monFrom);
					monday.setTimeTo(row.monTo);
					String monFrom[] = row.monFrom.split(":");
					int from = ((Integer.parseInt(monFrom[0]) * 60) + Integer.parseInt(monFrom[1]));
					String monTo[] = row.monTo.split(":");
					int to = ((Integer.parseInt(monTo[0]) * 60) + Integer.parseInt(monTo[1]));
					monday.setWorkMinutes(to - from);
				}
				monday.update();
				
				TimesheetDaysActual tuesday = TimesheetDaysActual.findById(row.tuesdayId);
				if(!row.tueFrom.equals("") || !row.tueTo.equals("")) {
					tuesday.setTimeFrom(row.tueFrom);
					tuesday.setTimeTo(row.tueTo);
					String tueFrom[] = row.tueFrom.split(":");
					int from = ((Integer.parseInt(tueFrom[0]) * 60) + Integer.parseInt(tueFrom[1]));
					String tueTo[] = row.tueTo.split(":");
					int to = ((Integer.parseInt(tueTo[0]) * 60) + Integer.parseInt(tueTo[1]));
					tuesday.setWorkMinutes(to - from);
				}	
				tuesday.update();
				
				TimesheetDaysActual wednesday = TimesheetDaysActual.findById(row.wednesdayId);
				if(!row.wedFrom.equals("") || !row.wedTo.equals("")) {
					wednesday.setTimeFrom(row.wedFrom);
					wednesday.setTimeTo(row.wedTo);
					String wedFrom[] = row.wedFrom.split(":");
					int from = ((Integer.parseInt(wedFrom[0]) * 60) + Integer.parseInt(wedFrom[1]));
					String wedTo[] = row.wedTo.split(":");
					int to = ((Integer.parseInt(wedTo[0]) * 60) + Integer.parseInt(wedTo[1]));
					wednesday.setWorkMinutes(to - from);
				}
				wednesday.update();
				
				TimesheetDaysActual thursday = TimesheetDaysActual.findById(row.thursdayId);
				if(!row.thuFrom.equals("") || !row.thuTo.equals("")) {
					thursday.setTimeFrom(row.thuFrom);
					thursday.setTimeTo(row.thuTo);
					String thuFrom[] = row.thuFrom.split(":");
					int from = ((Integer.parseInt(thuFrom[0]) * 60) + Integer.parseInt(thuFrom[1]));
					String thuTo[] = row.thuTo.split(":");
					int to = ((Integer.parseInt(thuTo[0]) * 60) + Integer.parseInt(thuTo[1]));
					thursday.setWorkMinutes(to - from);
				}
				thursday.update();
				
				TimesheetDaysActual friday = TimesheetDaysActual.findById(row.fridayId);
				if(!row.friFrom.equals("") || !row.friTo.equals("")) {
					friday.setTimeFrom(row.friFrom);
					friday.setTimeTo(row.friTo);
					String friFrom[] = row.friFrom.split(":");
					int from = ((Integer.parseInt(friFrom[0]) * 60) + Integer.parseInt(friFrom[1]));
					String friTo[] = row.friTo.split(":");
					int to = ((Integer.parseInt(friTo[0]) * 60) + Integer.parseInt(friTo[1]));
					friday.setWorkMinutes(to - from);
				}
				friday.update();
				
				TimesheetDaysActual saturday = TimesheetDaysActual.findById(row.saturdayId);
				if(!row.satFrom.equals("") || !row.satTo.equals("")) {
					saturday.setTimeFrom(row.satFrom);
					saturday.setTimeTo(row.satTo);
					String satFrom[] = row.satFrom.split(":");
					int from = ((Integer.parseInt(satFrom[0]) * 60) + Integer.parseInt(satFrom[1]));
					String satTo[] = row.satTo.split(":");
					int to = ((Integer.parseInt(satTo[0]) * 60) + Integer.parseInt(satTo[1]));
					saturday.setWorkMinutes(to - from);
				}
				saturday.update();
				
				TimesheetDaysActual sunday = TimesheetDaysActual.findById(row.sundayId);
				if(!row.sunFrom.equals("") || !row.sunTo.equals("")) {
					sunday.setTimeFrom(row.sunFrom);
					sunday.setTimeTo(row.sunTo);
					String sunFrom[] = row.sunFrom.split(":");
					int from = ((Integer.parseInt(sunFrom[0]) * 60) + Integer.parseInt(sunFrom[1]));
					String sunTo[] = row.sunTo.split(":");
					int to = ((Integer.parseInt(sunTo[0]) * 60) + Integer.parseInt(sunTo[1]));
					sunday.setWorkMinutes(to - from);
				}
				sunday.update();
				
				} else {
					
					TimesheetRowActual timesheetRow = new TimesheetRowActual();
					Project project = Project.findById(row.projectCode);
					timesheetRow.setProjectCode(project.projectCode);
					Task task = Task.findById(row.taskCode);
					timesheetRow.setTaskCode(task.taskCode);
					timesheetRow.setTimesheetActual(timesheetSavedObj);
					timesheetRow.setOverTime(row.isOverTime);
					timesheetRow.save();
					
					
					TimesheetDaysActual monday = new TimesheetDaysActual();
					cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
					monday.setTimesheetDate(cal.getTime());
					monday.setDay("monday");
					if(!row.monFrom.equals("") && !row.monTo.equals("")) {
						monday.setTimeFrom(row.monFrom);
						monday.setTimeTo(row.monTo);
						String monFrom[] = row.monFrom.split(":");
						int from = ((Integer.parseInt(monFrom[0]) * 60) + Integer.parseInt(monFrom[1]));
						String monTo[] = row.monTo.split(":");
						int to = ((Integer.parseInt(monTo[0]) * 60) + Integer.parseInt(monTo[1]));
						monday.setWorkMinutes(to - from);
					}
					monday.setTimesheetRowActual(timesheetRow);
					monday.save();
					
					TimesheetDaysActual tuesday = new TimesheetDaysActual();
					cal.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
					tuesday.setTimesheetDate(cal.getTime());
					tuesday.setDay("tuesday");
					if(!row.tueFrom.equals("") || !row.tueTo.equals("")) {
						tuesday.setTimeFrom(row.tueFrom);
						tuesday.setTimeTo(row.tueTo);
						String tueFrom[] = row.tueFrom.split(":");
						int from = ((Integer.parseInt(tueFrom[0]) * 60) + Integer.parseInt(tueFrom[1]));
						String tueTo[] = row.tueTo.split(":");
						int to = ((Integer.parseInt(tueTo[0]) * 60) + Integer.parseInt(tueTo[1]));
						tuesday.setWorkMinutes(to - from);
					}	
					tuesday.setTimesheetRowActual(timesheetRow);
					tuesday.save();
					
					TimesheetDaysActual wednesday = new TimesheetDaysActual();
					cal.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
					wednesday.setTimesheetDate(cal.getTime());
					wednesday.setDay("wednesday");
					if(!row.wedFrom.equals("") || !row.wedTo.equals("")) {
						wednesday.setTimeFrom(row.wedFrom);
						wednesday.setTimeTo(row.wedTo);
						String wedFrom[] = row.wedFrom.split(":");
						int from = ((Integer.parseInt(wedFrom[0]) * 60) + Integer.parseInt(wedFrom[1]));
						String wedTo[] = row.wedTo.split(":");
						int to = ((Integer.parseInt(wedTo[0]) * 60) + Integer.parseInt(wedTo[1]));
						wednesday.setWorkMinutes(to - from);
					}
					wednesday.setTimesheetRowActual(timesheetRow);
					wednesday.save();
					
					TimesheetDaysActual thursday = new TimesheetDaysActual();
					cal.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
					thursday.setTimesheetDate(cal.getTime());
					thursday.setDay("thursday");
					if(!row.thuFrom.equals("") || !row.thuTo.equals("")) {
						thursday.setTimeFrom(row.thuFrom);
						thursday.setTimeTo(row.thuTo);
						String thuFrom[] = row.thuFrom.split(":");
						int from = ((Integer.parseInt(thuFrom[0]) * 60) + Integer.parseInt(thuFrom[1]));
						String thuTo[] = row.thuTo.split(":");
						int to = ((Integer.parseInt(thuTo[0]) * 60) + Integer.parseInt(thuTo[1]));
						thursday.setWorkMinutes(to - from);
					}
					thursday.setTimesheetRowActual(timesheetRow);
					thursday.save();
					
					TimesheetDaysActual friday = new TimesheetDaysActual();
					cal.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
					friday.setTimesheetDate(cal.getTime());
					friday.setDay("friday");
					if(!row.friFrom.equals("") || !row.friTo.equals("")) {
						friday.setTimeFrom(row.friFrom);
						friday.setTimeTo(row.friTo);
						String friFrom[] = row.friFrom.split(":");
						int from = ((Integer.parseInt(friFrom[0]) * 60) + Integer.parseInt(friFrom[1]));
						String friTo[] = row.friTo.split(":");
						int to = ((Integer.parseInt(friTo[0]) * 60) + Integer.parseInt(friTo[1]));
						friday.setWorkMinutes(to - from);
					}
					friday.setTimesheetRowActual(timesheetRow);
					friday.save();
					
					TimesheetDaysActual saturday = new TimesheetDaysActual();
					cal.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
					saturday.setTimesheetDate(cal.getTime());
					saturday.setDay("saturday");
					if(!row.satFrom.equals("") || !row.satTo.equals("")) {
						saturday.setTimeFrom(row.satFrom);
						saturday.setTimeTo(row.satTo);
						String satFrom[] = row.satFrom.split(":");
						int from = ((Integer.parseInt(satFrom[0]) * 60) + Integer.parseInt(satFrom[1]));
						String satTo[] = row.satTo.split(":");
						int to = ((Integer.parseInt(satTo[0]) * 60) + Integer.parseInt(satTo[1]));
						saturday.setWorkMinutes(to - from);
					}
					saturday.setTimesheetRowActual(timesheetRow);
					saturday.save();
					
					TimesheetDaysActual sunday = new TimesheetDaysActual();
					cal.set(Calendar.WEEK_OF_YEAR,timesheet.weekOfYear+1);
					cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
					sunday.setTimesheetDate(cal.getTime());
					sunday.setDay("sunday");
					if(!row.sunFrom.equals("") || !row.sunTo.equals("")) {
						sunday.setTimeFrom(row.sunFrom);
						sunday.setTimeTo(row.sunTo);
						String sunFrom[] = row.sunFrom.split(":");
						int from = ((Integer.parseInt(sunFrom[0]) * 60) + Integer.parseInt(sunFrom[1]));
						String sunTo[] = row.sunTo.split(":");
						int to = ((Integer.parseInt(sunTo[0]) * 60) + Integer.parseInt(sunTo[1]));
						sunday.setWorkMinutes(to - from);
					}
					sunday.setTimesheetRowActual(timesheetRow);
					sunday.save();
					
				}
				
			}
			
		}
			if(timesheet.status.equals("Submitted")) {
				MailSetting smtpSetting = new MailSetting();
				smtpSetting.hostName = "smtp.gmail.com";
				smtpSetting.portNumber = "587";
				smtpSetting.userName = "";
				smtpSetting.password = "";
				String recipients = "";
		    	String subject = "";
		    	String body = "";
		    	recipients = user.getManager().getEmail();
		    	subject = "Account Creation By";
		    	body = "Your Login Details :";
		    	body += "\nUser Name :";
		    	body += "\nPassword  :";
		    	Email.sendOnlyMail(smtpSetting,recipients, subject, body);
			}
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	
	
	@RequestMapping(value="/saveTimesheet", method = RequestMethod.POST)
	public @ResponseBody String saveTimesheet(ModelMap model,@RequestBody TimesheetVM timesheet,HttpServletRequest request){
		
		try {
		
		User user = User.findById(timesheet.userId);
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.YEAR,timesheet.year);
		cal.set(Calendar.WEEK_OF_YEAR,timesheet.weekOfYear);
		
		Timesheet timesheetSavedObj = Timesheet.getByUserWeekAndYear(user, timesheet.weekOfYear, timesheet.year);
		
		if(timesheetSavedObj == null) {
			
		Timesheet timesheetObj = new Timesheet();
		
		timesheetObj.setUser(user);
		timesheetObj.setStatus(TimesheetStatus.valueOf(timesheet.status));
		timesheetObj.setWeekOfYear(timesheet.weekOfYear);
		timesheetObj.setYear(timesheet.year);
		if(timesheet.status.equals("Submitted")) {
			timesheetObj.setTimesheetWith(user.getManager());
		} else {
			timesheetObj.setTimesheetWith(user);
		}
		timesheetObj.save();
		
		for(TimesheetRowVM rowVM : timesheet.timesheetRows) {
			TimesheetRow timesheetRow = new TimesheetRow();
			Project project = Project.findById(rowVM.projectCode);
			timesheetRow.setProjectCode(project.projectCode);
			Task task = Task.findById(rowVM.taskCode);
			timesheetRow.setTaskCode(task.taskCode);
			timesheetRow.setTimesheet(timesheetObj);
			timesheetRow.setOverTime(rowVM.isOverTime);
			timesheetRow.save();
			
			
			TimesheetDays monday = new TimesheetDays();
			cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
			monday.setTimesheetDate(cal.getTime());
			monday.setDay("monday");
			if(!rowVM.monFrom.equals("") && !rowVM.monTo.equals("")) {
				monday.setTimeFrom(rowVM.monFrom);
				monday.setTimeTo(rowVM.monTo);
				String monFrom[] = rowVM.monFrom.split(":");
				int from = ((Integer.parseInt(monFrom[0]) * 60) + Integer.parseInt(monFrom[1]));
				String monTo[] = rowVM.monTo.split(":");
				int to = ((Integer.parseInt(monTo[0]) * 60) + Integer.parseInt(monTo[1]));
				monday.setWorkMinutes(to - from);
			}
			monday.setTimesheetRow(timesheetRow);
			monday.save();
			
			TimesheetDays tuesday = new TimesheetDays();
			cal.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
			tuesday.setTimesheetDate(cal.getTime());
			tuesday.setDay("tuesday");
			if(!rowVM.tueFrom.equals("") || !rowVM.tueTo.equals("")) {
				tuesday.setTimeFrom(rowVM.tueFrom);
				tuesday.setTimeTo(rowVM.tueTo);
				String tueFrom[] = rowVM.tueFrom.split(":");
				int from = ((Integer.parseInt(tueFrom[0]) * 60) + Integer.parseInt(tueFrom[1]));
				String tueTo[] = rowVM.tueTo.split(":");
				int to = ((Integer.parseInt(tueTo[0]) * 60) + Integer.parseInt(tueTo[1]));
				tuesday.setWorkMinutes(to - from);
			}	
			tuesday.setTimesheetRow(timesheetRow);
			tuesday.save();
			
			TimesheetDays wednesday = new TimesheetDays();
			cal.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
			wednesday.setTimesheetDate(cal.getTime());
			wednesday.setDay("wednesday");
			if(!rowVM.wedFrom.equals("") || !rowVM.wedTo.equals("")) {
				wednesday.setTimeFrom(rowVM.wedFrom);
				wednesday.setTimeTo(rowVM.wedTo);
				String wedFrom[] = rowVM.wedFrom.split(":");
				int from = ((Integer.parseInt(wedFrom[0]) * 60) + Integer.parseInt(wedFrom[1]));
				String wedTo[] = rowVM.wedTo.split(":");
				int to = ((Integer.parseInt(wedTo[0]) * 60) + Integer.parseInt(wedTo[1]));
				wednesday.setWorkMinutes(to - from);
			}
			wednesday.setTimesheetRow(timesheetRow);
			wednesday.save();
			
			TimesheetDays thursday = new TimesheetDays();
			cal.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
			thursday.setTimesheetDate(cal.getTime());
			thursday.setDay("thursday");
			if(!rowVM.thuFrom.equals("") || !rowVM.thuTo.equals("")) {
				thursday.setTimeFrom(rowVM.thuFrom);
				thursday.setTimeTo(rowVM.thuTo);
				String thuFrom[] = rowVM.thuFrom.split(":");
				int from = ((Integer.parseInt(thuFrom[0]) * 60) + Integer.parseInt(thuFrom[1]));
				String thuTo[] = rowVM.thuTo.split(":");
				int to = ((Integer.parseInt(thuTo[0]) * 60) + Integer.parseInt(thuTo[1]));
				thursday.setWorkMinutes(to - from);
			}
			thursday.setTimesheetRow(timesheetRow);
			thursday.save();
			
			TimesheetDays friday = new TimesheetDays();
			cal.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
			friday.setTimesheetDate(cal.getTime());
			friday.setDay("friday");
			if(!rowVM.friFrom.equals("") || !rowVM.friTo.equals("")) {
				friday.setTimeFrom(rowVM.friFrom);
				friday.setTimeTo(rowVM.friTo);
				String friFrom[] = rowVM.friFrom.split(":");
				int from = ((Integer.parseInt(friFrom[0]) * 60) + Integer.parseInt(friFrom[1]));
				String friTo[] = rowVM.friTo.split(":");
				int to = ((Integer.parseInt(friTo[0]) * 60) + Integer.parseInt(friTo[1]));
				friday.setWorkMinutes(to - from);
			}
			friday.setTimesheetRow(timesheetRow);
			friday.save();
			
			TimesheetDays saturday = new TimesheetDays();
			cal.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
			saturday.setTimesheetDate(cal.getTime());
			saturday.setDay("saturday");
			if(!rowVM.satFrom.equals("") || !rowVM.satTo.equals("")) {
				saturday.setTimeFrom(rowVM.satFrom);
				saturday.setTimeTo(rowVM.satTo);
				String satFrom[] = rowVM.satFrom.split(":");
				int from = ((Integer.parseInt(satFrom[0]) * 60) + Integer.parseInt(satFrom[1]));
				String satTo[] = rowVM.satTo.split(":");
				int to = ((Integer.parseInt(satTo[0]) * 60) + Integer.parseInt(satTo[1]));
				saturday.setWorkMinutes(to - from);
			}
			saturday.setTimesheetRow(timesheetRow);
			saturday.save();
			
			TimesheetDays sunday = new TimesheetDays();
			cal.set(Calendar.WEEK_OF_YEAR,timesheet.weekOfYear+1);
			cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
			sunday.setTimesheetDate(cal.getTime());
			sunday.setDay("sunday");
			if(!rowVM.sunFrom.equals("") || !rowVM.sunTo.equals("")) {
				sunday.setTimeFrom(rowVM.sunFrom);
				sunday.setTimeTo(rowVM.sunTo);
				String sunFrom[] = rowVM.sunFrom.split(":");
				int from = ((Integer.parseInt(sunFrom[0]) * 60) + Integer.parseInt(sunFrom[1]));
				String sunTo[] = rowVM.sunTo.split(":");
				int to = ((Integer.parseInt(sunTo[0]) * 60) + Integer.parseInt(sunTo[1]));
				sunday.setWorkMinutes(to - from);
			}
			sunday.setTimesheetRow(timesheetRow);
			sunday.save();
			
		}
		
		} else {
			timesheetSavedObj.setStatus(TimesheetStatus.valueOf(timesheet.status));
			timesheetSavedObj.update();
			
			for(TimesheetRowVM row: timesheet.timesheetRows) {
				if(row.rowId != 0L) {
				TimesheetRow timesheetRow =  TimesheetRow.findById(row.rowId);
				Project project = Project.findById(row.projectCode);
				timesheetRow.setProjectCode(project.projectCode);
				Task task = Task.findById(row.taskCode);
				timesheetRow.setTaskCode(task.taskCode);
				timesheetRow.setOverTime(row.isOverTime);
				timesheetRow.update();
				
				
				TimesheetDays monday = TimesheetDays.findById(row.mondayId);
				if(!row.monFrom.equals("") && !row.monTo.equals("")) {
					monday.setTimeFrom(row.monFrom);
					monday.setTimeTo(row.monTo);
					String monFrom[] = row.monFrom.split(":");
					int from = ((Integer.parseInt(monFrom[0]) * 60) + Integer.parseInt(monFrom[1]));
					String monTo[] = row.monTo.split(":");
					int to = ((Integer.parseInt(monTo[0]) * 60) + Integer.parseInt(monTo[1]));
					monday.setWorkMinutes(to - from);
				}
				monday.update();
				
				TimesheetDays tuesday = TimesheetDays.findById(row.tuesdayId);
				if(!row.tueFrom.equals("") || !row.tueTo.equals("")) {
					tuesday.setTimeFrom(row.tueFrom);
					tuesday.setTimeTo(row.tueTo);
					String tueFrom[] = row.tueFrom.split(":");
					int from = ((Integer.parseInt(tueFrom[0]) * 60) + Integer.parseInt(tueFrom[1]));
					String tueTo[] = row.tueTo.split(":");
					int to = ((Integer.parseInt(tueTo[0]) * 60) + Integer.parseInt(tueTo[1]));
					tuesday.setWorkMinutes(to - from);
				}	
				tuesday.update();
				
				TimesheetDays wednesday = TimesheetDays.findById(row.wednesdayId);
				if(!row.wedFrom.equals("") || !row.wedTo.equals("")) {
					wednesday.setTimeFrom(row.wedFrom);
					wednesday.setTimeTo(row.wedTo);
					String wedFrom[] = row.wedFrom.split(":");
					int from = ((Integer.parseInt(wedFrom[0]) * 60) + Integer.parseInt(wedFrom[1]));
					String wedTo[] = row.wedTo.split(":");
					int to = ((Integer.parseInt(wedTo[0]) * 60) + Integer.parseInt(wedTo[1]));
					wednesday.setWorkMinutes(to - from);
				}
				wednesday.update();
				
				TimesheetDays thursday = TimesheetDays.findById(row.thursdayId);
				if(!row.thuFrom.equals("") || !row.thuTo.equals("")) {
					thursday.setTimeFrom(row.thuFrom);
					thursday.setTimeTo(row.thuTo);
					String thuFrom[] = row.thuFrom.split(":");
					int from = ((Integer.parseInt(thuFrom[0]) * 60) + Integer.parseInt(thuFrom[1]));
					String thuTo[] = row.thuTo.split(":");
					int to = ((Integer.parseInt(thuTo[0]) * 60) + Integer.parseInt(thuTo[1]));
					thursday.setWorkMinutes(to - from);
				}
				thursday.update();
				
				TimesheetDays friday = TimesheetDays.findById(row.fridayId);
				if(!row.friFrom.equals("") || !row.friTo.equals("")) {
					friday.setTimeFrom(row.friFrom);
					friday.setTimeTo(row.friTo);
					String friFrom[] = row.friFrom.split(":");
					int from = ((Integer.parseInt(friFrom[0]) * 60) + Integer.parseInt(friFrom[1]));
					String friTo[] = row.friTo.split(":");
					int to = ((Integer.parseInt(friTo[0]) * 60) + Integer.parseInt(friTo[1]));
					friday.setWorkMinutes(to - from);
				}
				friday.update();
				
				TimesheetDays saturday = TimesheetDays.findById(row.saturdayId);
				if(!row.satFrom.equals("") || !row.satTo.equals("")) {
					saturday.setTimeFrom(row.satFrom);
					saturday.setTimeTo(row.satTo);
					String satFrom[] = row.satFrom.split(":");
					int from = ((Integer.parseInt(satFrom[0]) * 60) + Integer.parseInt(satFrom[1]));
					String satTo[] = row.satTo.split(":");
					int to = ((Integer.parseInt(satTo[0]) * 60) + Integer.parseInt(satTo[1]));
					saturday.setWorkMinutes(to - from);
				}
				saturday.update();
				
				TimesheetDays sunday = TimesheetDays.findById(row.sundayId);
				if(!row.sunFrom.equals("") || !row.sunTo.equals("")) {
					sunday.setTimeFrom(row.sunFrom);
					sunday.setTimeTo(row.sunTo);
					String sunFrom[] = row.sunFrom.split(":");
					int from = ((Integer.parseInt(sunFrom[0]) * 60) + Integer.parseInt(sunFrom[1]));
					String sunTo[] = row.sunTo.split(":");
					int to = ((Integer.parseInt(sunTo[0]) * 60) + Integer.parseInt(sunTo[1]));
					sunday.setWorkMinutes(to - from);
				}
				sunday.update();
				
				} else {
					
					TimesheetRow timesheetRow = new TimesheetRow();
					Project project = Project.findById(row.projectCode);
					timesheetRow.setProjectCode(project.projectCode);
					Task task = Task.findById(row.taskCode);
					timesheetRow.setTaskCode(task.taskCode);
					timesheetRow.setTimesheet(timesheetSavedObj);
					timesheetRow.setOverTime(row.isOverTime);
					timesheetRow.save();
					
					
					TimesheetDays monday = new TimesheetDays();
					cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
					monday.setTimesheetDate(cal.getTime());
					monday.setDay("monday");
					if(!row.monFrom.equals("") && !row.monTo.equals("")) {
						monday.setTimeFrom(row.monFrom);
						monday.setTimeTo(row.monTo);
						String monFrom[] = row.monFrom.split(":");
						int from = ((Integer.parseInt(monFrom[0]) * 60) + Integer.parseInt(monFrom[1]));
						String monTo[] = row.monTo.split(":");
						int to = ((Integer.parseInt(monTo[0]) * 60) + Integer.parseInt(monTo[1]));
						monday.setWorkMinutes(to - from);
					}
					monday.setTimesheetRow(timesheetRow);
					monday.save();
					
					TimesheetDays tuesday = new TimesheetDays();
					cal.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
					tuesday.setTimesheetDate(cal.getTime());
					tuesday.setDay("tuesday");
					if(!row.tueFrom.equals("") || !row.tueTo.equals("")) {
						tuesday.setTimeFrom(row.tueFrom);
						tuesday.setTimeTo(row.tueTo);
						String tueFrom[] = row.tueFrom.split(":");
						int from = ((Integer.parseInt(tueFrom[0]) * 60) + Integer.parseInt(tueFrom[1]));
						String tueTo[] = row.tueTo.split(":");
						int to = ((Integer.parseInt(tueTo[0]) * 60) + Integer.parseInt(tueTo[1]));
						tuesday.setWorkMinutes(to - from);
					}	
					tuesday.setTimesheetRow(timesheetRow);
					tuesday.save();
					
					TimesheetDays wednesday = new TimesheetDays();
					cal.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
					wednesday.setTimesheetDate(cal.getTime());
					wednesday.setDay("wednesday");
					if(!row.wedFrom.equals("") || !row.wedTo.equals("")) {
						wednesday.setTimeFrom(row.wedFrom);
						wednesday.setTimeTo(row.wedTo);
						String wedFrom[] = row.wedFrom.split(":");
						int from = ((Integer.parseInt(wedFrom[0]) * 60) + Integer.parseInt(wedFrom[1]));
						String wedTo[] = row.wedTo.split(":");
						int to = ((Integer.parseInt(wedTo[0]) * 60) + Integer.parseInt(wedTo[1]));
						wednesday.setWorkMinutes(to - from);
					}
					wednesday.setTimesheetRow(timesheetRow);
					wednesday.save();
					
					TimesheetDays thursday = new TimesheetDays();
					cal.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
					thursday.setTimesheetDate(cal.getTime());
					thursday.setDay("thursday");
					if(!row.thuFrom.equals("") || !row.thuTo.equals("")) {
						thursday.setTimeFrom(row.thuFrom);
						thursday.setTimeTo(row.thuTo);
						String thuFrom[] = row.thuFrom.split(":");
						int from = ((Integer.parseInt(thuFrom[0]) * 60) + Integer.parseInt(thuFrom[1]));
						String thuTo[] = row.thuTo.split(":");
						int to = ((Integer.parseInt(thuTo[0]) * 60) + Integer.parseInt(thuTo[1]));
						thursday.setWorkMinutes(to - from);
					}
					thursday.setTimesheetRow(timesheetRow);
					thursday.save();
					
					TimesheetDays friday = new TimesheetDays();
					cal.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
					friday.setTimesheetDate(cal.getTime());
					friday.setDay("friday");
					if(!row.friFrom.equals("") || !row.friTo.equals("")) {
						friday.setTimeFrom(row.friFrom);
						friday.setTimeTo(row.friTo);
						String friFrom[] = row.friFrom.split(":");
						int from = ((Integer.parseInt(friFrom[0]) * 60) + Integer.parseInt(friFrom[1]));
						String friTo[] = row.friTo.split(":");
						int to = ((Integer.parseInt(friTo[0]) * 60) + Integer.parseInt(friTo[1]));
						friday.setWorkMinutes(to - from);
					}
					friday.setTimesheetRow(timesheetRow);
					friday.save();
					
					TimesheetDays saturday = new TimesheetDays();
					cal.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
					saturday.setTimesheetDate(cal.getTime());
					saturday.setDay("saturday");
					if(!row.satFrom.equals("") || !row.satTo.equals("")) {
						saturday.setTimeFrom(row.satFrom);
						saturday.setTimeTo(row.satTo);
						String satFrom[] = row.satFrom.split(":");
						int from = ((Integer.parseInt(satFrom[0]) * 60) + Integer.parseInt(satFrom[1]));
						String satTo[] = row.satTo.split(":");
						int to = ((Integer.parseInt(satTo[0]) * 60) + Integer.parseInt(satTo[1]));
						saturday.setWorkMinutes(to - from);
					}
					saturday.setTimesheetRow(timesheetRow);
					saturday.save();
					
					TimesheetDays sunday = new TimesheetDays();
					cal.set(Calendar.WEEK_OF_YEAR,timesheet.weekOfYear+1);
					cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
					sunday.setTimesheetDate(cal.getTime());
					sunday.setDay("sunday");
					if(!row.sunFrom.equals("") || !row.sunTo.equals("")) {
						sunday.setTimeFrom(row.sunFrom);
						sunday.setTimeTo(row.sunTo);
						String sunFrom[] = row.sunFrom.split(":");
						int from = ((Integer.parseInt(sunFrom[0]) * 60) + Integer.parseInt(sunFrom[1]));
						String sunTo[] = row.sunTo.split(":");
						int to = ((Integer.parseInt(sunTo[0]) * 60) + Integer.parseInt(sunTo[1]));
						sunday.setWorkMinutes(to - from);
					}
					sunday.setTimesheetRow(timesheetRow);
					sunday.save();
					
				}
				
			}
			
		}
			if(timesheet.status.equals("Submitted")) {
				//MailSetting smtpSetting = MailSetting.find.where().eq("companyObject", user.companyobject).findUnique();
				MailSetting smtpSetting = new MailSetting();
				smtpSetting.hostName = "smtp.gmail.com";
				smtpSetting.portNumber = "587";
				smtpSetting.userName = "";
				smtpSetting.password = "";
				String recipients = "";
		    	String subject = "";
		    	String body = "";
		    	recipients = user.getManager().getEmail();
		    	subject = "Account Creation By";
		    	body = "Your Login Details :";
		    	body += "\nUser Name :";
		    	body += "\nPassword  :";
		    	Email.sendOnlyMail(smtpSetting,recipients, subject, body);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	//Submit
	@RequestMapping(value="/timesheetCreate", method = RequestMethod.POST)
	public String create(ModelMap model, @CookieValue("username") String username,HttpServletRequest request){
		Form<Timesheet> timesheetForm = form(Timesheet.class).bindFromRequest(request);
		User user = User.findByEmail(username);
		timesheetForm.get().user = user;
		Form<Timesheet> newTimesheetForm;
		
		createHashMapOfTimesheetRows(timesheetForm.get());
	
		List<String> userProjects = getProjects(user.id, Integer.valueOf(timesheetForm.get().weekOfYear));
		
		if(Timesheet.byUser_Week_Year(user.id,timesheetForm.get().weekOfYear,timesheetForm.get().year).size() != 0){
			
			if(timesheetForm.get().status == TimesheetStatus.Submitted){
				timesheetForm.get().timesheetWith = user.manager;			
			}else{
				timesheetForm.get().timesheetWith = user;
			}
		//	System.out.println("id"+timesheetForm.get().id);
		//	timesheetForm.get().update(timesheetForm.get().getId());
			timesheetForm.get().update();
			newTimesheetForm = form(Timesheet.class).fill(Timesheet.find.byId(timesheetForm.get().id));
			if(timesheetForm.get().status == TimesheetStatus.Submitted){
				//System.out.println(newTimesheetForm.get().tid);
				try{
					TimesheetWorkflowUtils.startTimeSheetWF(newTimesheetForm.get().getTid());
				}
				catch (Exception e) {
					EmailExceptionHandler.handleException(e);
				}
			}
			
			model.addAttribute("user", user);
			model.addAttribute("timesheetForm", newTimesheetForm);
			model.addAttribute("projectsList", userProjects);
			model.addAttribute("TimesheetStatus", getTimesheetStatus());
			
			return "timesheetTable";
			//return ok(timesheetTable.render(user,newTimesheetForm,userProjects));
		}else{
			timesheetForm.get().id = null;
			if(timesheetForm.get().status == TimesheetStatus.Submitted){
				timesheetForm.get().timesheetWith = user.manager;
			}
			else{
				timesheetForm.get().timesheetWith = user;
			}
			timesheetForm.get().tid = UUID.randomUUID().toString();
			timesheetForm.get().save();
			//System.out.println(timesheetForm.get().id);
			newTimesheetForm = timesheetForm.fill(Timesheet.find.byId(timesheetForm.get().id));
			
			if(timesheetForm.get().status == TimesheetStatus.Submitted){
				//System.out.println(newTimesheetForm.get().tid);
				try{
					TimesheetWorkflowUtils.startTimeSheetWF(newTimesheetForm.get().getTid());
				}
				catch (Exception e) {
					// TODO: handle exception
					EmailExceptionHandler.handleException(e);
				}
			}
			model.addAttribute("user", user);
			model.addAttribute("timesheetForm", newTimesheetForm);
			model.addAttribute("projectsList", userProjects);
			model.addAttribute("TimesheetStatus", getTimesheetStatus());
			
			return "timesheetTable";
			//return ok(timesheetTable.render(user,newTimesheetForm,userProjects));
		}
	}
	
	@RequestMapping(value="/retractTimesheet", method= RequestMethod.GET)
	public String retractTimesheet(ModelMap model, @CookieValue("username") String username,HttpServletRequest request){
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		String id = form.data().get("id");
		User user = User.findByEmail(username);
		Timesheet timesheet = Timesheet.findById(Long.parseLong(id));
		timesheet.setTimesheetWith(user);
		timesheet.setStatus(TimesheetStatus.Draft);
		timesheet.update();
		Form<Timesheet> timesheetForm = form(Timesheet.class).fill(timesheet);
		List<String> userProjects = getProjects(user.getId(), Integer.valueOf(timesheetForm.get().weekOfYear));
		
		model.addAttribute("user", user);
		model.addAttribute("timesheetForm", timesheetForm);
		model.addAttribute("projectsList", userProjects);
		model.addAttribute("TimesheetStatus", getTimesheetStatus());
		
		return "timesheetTable";
		//return ok(timesheetTable.render(user,timesheetForm,userProjects));
	}
	
	public static HashMap<String,Integer> createHashMapOfTimesheetRows(Timesheet timesheet){
		timesheetRowsMap = new HashMap<String,Integer>();
		for(TimesheetRow row : timesheet.timesheetRows){
			if(timesheetRowsMap.containsKey(row.projectCode)){
				timesheetRowsMap.put(row.projectCode, timesheetRowsMap.get(row.projectCode));//+row.totalHrs);
			}else{
				timesheetRowsMap.put(row.projectCode,123); //row.totalHrs);
			}
		}
		return timesheetRowsMap;
	}
	
	
	@RequestMapping(value="/timesheet/getLastWeekTimesheet", method = RequestMethod.GET)
	public String getLastWeekTimesheet(ModelMap model, @CookieValue("username") String username, @RequestParam("week") Integer week,@RequestParam("year") Integer year){
		User user = User.findByEmail(username);
		Form<Timesheet> timesheetForm;
		Timesheet timesheet;
		List<String> userProjects = getProjects(user.getId(), Integer.valueOf(week));
		
		if(userProjects.size() != 0){
			if(Timesheet.byUser_Week_Year(user.getId(),(week-1),year).size() != 0 ){
				timesheet = Timesheet.byUser_Week_Year(user.getId(),(week-1),year).get(0);
				timesheet.setStatus(TimesheetStatus.Draft);
				timesheetForm = form(Timesheet.class).fill(timesheet);
			}else{
				timesheetForm = form(Timesheet.class);
			}
			
			int timesheetRowSize = 0;
			if(timesheetForm != null){
				timesheetRowSize = timesheetForm.get().timesheetRows.size();
			}
			
			if(userProjects.size() == timesheetRowSize){
				model.addAttribute("user", user);
				model.addAttribute("timesheetForm", timesheetForm);
				model.addAttribute("projectsList", userProjects);
				
				return "timesheetTable";
				//return ok(timesheetTable.render(user, timesheetForm,userProjects));
			}else{
				throw new NoTimeSheetFoudException();
			}
		}else{
			throw new NoTimeSheetFoudException();

		}
	}
	
	@RequestMapping(value="/timesheetCancel", method = RequestMethod.POST)
	public String cancel(ModelMap model, @CookieValue("username") String username){
		User user = User.findByEmail(username);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Form<Timesheet> newTimesheetForm = form(Timesheet.class).fill(Timesheet.byUser_Week_Year
												(user.id, cal.get(Calendar.WEEK_OF_YEAR), 
														cal.get(Calendar.YEAR)).get(0));
		List<String> userProjects = getProjects(user.id, Integer.valueOf(newTimesheetForm.get().weekOfYear));
		
		model.addAttribute("user", user);
		model.addAttribute("timesheetForm", newTimesheetForm);
		model.addAttribute("projectsList", userProjects);
		
		return "timesheetTable";
		//return ok(timesheetTable.render(user,newTimesheetForm,userProjects));
	}
	
	public static List<String> getProjects(Long id, int week){
		User user = User.findById(id);
		List<String> projects = new ArrayList<String>(); 
		for(Project project : Project.find.where().add(Expr.eq("users", user)).findList()){
	//		if(checkProjectDateValidation(user,project.projectCode,week)){
				projects.add(project.projectCode);
		//	}
		}
		return projects;
	}
	
	public static boolean checkProjectDateValidation(User user, String projectCode, int week){
		Project project = Project.findByProjectCode(projectCode);
		if(user.hireDate != null){
			if(getWeekNumber(project.startDate) <= week && getWeekNumber(project.endDate) >= week 
					&& getWeekNumber(user.hireDate) <= week){
//				if(project.endDate != null){
//					if(getWeekNumber(project.endDate) >= week){
						return true;
//					}else{
//						return false;
//					}
//				}else{
//					return true;
//				}
			}else{
				return false;
			}
		}else{
			return false;
		}
//		Project project = Project.findByProjectCode(projectCode);
//		if(getWeekNumber(user.hireDate) <= week && getWeekNumber(user.hireDate) >= getWeekNumber(project.startDate)){
//			if(week >= getWeekNumber(project.startDate) && week <= getWeekNumber(project.endDate)){
//				return true;
//			}else{
//				return false;
//			}
//		}else{
//			return false;
//		}
	}
	
	public static int getWeekNumber(Date date){
		Calendar userCal = Calendar.getInstance();
		userCal.setFirstDayOfWeek(1);
		userCal.setTime(date);
		return userCal.get(Calendar.WEEK_OF_YEAR);
	}
	
	
	public static List<String> getTaskByProject(Long userId, String projectCode) {
		List<String> tasks = new ArrayList<String>();
		Project project = Project.find.where()
				.add(Expr.eq("projectCode", projectCode)).findUnique();
		for (Task task : Task.find.where().add(Expr.eq("projects", project)).findList()) {
			tasks.add(task.taskCode);
		}
		return tasks;
	}
	
	@RequestMapping(value="/timesheet/getTimesheetTable" ,method=RequestMethod.GET)
	public String getTimesheetTable(ModelMap model, @CookieValue("username") String username, 
				@RequestParam(value="id") String id, @RequestParam(value="week") String week,
				@RequestParam(value="year") String year){
		User user = User.findByEmail(username);
		Form<Timesheet> timesheetForm;
		Timesheet timesheet;
		if(Timesheet.byUser_Week_Year(Long.parseLong(id),Integer.valueOf(week),Integer.valueOf(year)).size() != 0){
			timesheet = Timesheet.byUser_Week_Year(Long.parseLong(id),Integer.valueOf(week),Integer.valueOf(year)).get(0);
			timesheetForm = form(Timesheet.class).fill(timesheet);
		}else{
			Timesheet ts = new Timesheet();
			List<TimesheetRow> tsr = new ArrayList<TimesheetRow>();
			tsr.add(new TimesheetRow());
			ts.setTimesheetRows(tsr);
			timesheetForm = form(Timesheet.class).fill(ts);
		}
		
		List<String> userProjects = getProjects(user.id, Integer.valueOf(week)); 
		
		model.addAttribute("user", user);
		model.addAttribute("timesheetForm", timesheetForm);
		model.addAttribute("projectsList", userProjects);
		model.addAttribute("TimesheetStatus", getTimesheetStatus());
		return "timesheetTable";
		//return ok(timesheetTable.render(user, timesheetForm, userProjects));
	}
	
	@RequestMapping(value = "/timesheet/getTaskCode", method= RequestMethod.GET)
	public @ResponseBody String getTaskCodes(@RequestParam(value="_value")String _value){
		Project projects = Project.find.where().add(Expr.eq("projectCode", _value)).findUnique();
		List<Task> listOfTasks = Task.find.where().add(Expr.eq("projects", projects)).findList(); 
		SelectUIMap[] maps=new SelectUIMap[listOfTasks.size()];
		for(int i=0;i<listOfTasks.size();i++){
			maps[i]=new SelectUIMap(listOfTasks.get(i).taskCode, listOfTasks.get(i).taskCode);
		}
		return Json.toJson(maps).toString();
	}
	
	//getting called from drop-down section of timesheet
	public static List<String> getTimesheetStatus(){
		TimesheetStatus[] status = TimesheetStatus.values();
		List<String> timesheetStatus = new ArrayList<String>();
		for(int i=0;i<status.length;i++){
			timesheetStatus.add(status[i].name());
		}
		return timesheetStatus;
	}
	
	@RequestMapping(value = "/timesheetSearchIndex", method= RequestMethod.GET)
	public String searchIndex(ModelMap model, @CookieValue("username") String username){
		User user = User.findByEmail(username);
		Form<Timesheet> timesheetForm = form(Timesheet.class);
		
		model.addAttribute("context", TimesheetSearchContext.getInstance().build());
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);
		model.addAttribute("timesheetForm", timesheetForm);
		
		return "searchTimesheet";
		//return ok(searchTimesheet.render(TimesheetSearchContext.getInstance().build(),MenuBarFixture.build(request().username()), user,timesheetForm));
	}
	
	@RequestMapping(value = "/TimesheetSearch", method= RequestMethod.GET)
	public @ResponseBody String search(@CookieValue("username") String username,HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("email", username);
		return Json.toJson(TimesheetSearchContext.getInstance().build().doSearch(form)).toString();
    }
	
	@RequestMapping(value = "/timesheetEdit/{id}", method= RequestMethod.GET)
	public String editTimesheet(ModelMap model, @CookieValue("username") String username, @PathVariable("id") String id){
		User user = User.findByEmail(username);
		Timesheet timesheet = Timesheet.findById(Long.parseLong(id));
		Form<Timesheet> timesheetForm = form(Timesheet.class).fill(timesheet);
		
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);
		model.addAttribute("timesheetForm", timesheetForm);
		
		return "timesheetIndex";
		//return ok(timesheetIndex.render(MenuBarFixture.build(username),user,timesheetForm));
	}
	
	@RequestMapping(value = "/timesheetApprovalViaMail", method= RequestMethod.GET)
	public String timeSheetApprovalViaMail(HttpServletRequest request){
		DynamicForm dynamicForm =DynamicForm .form().bindFromRequest(request);
		String query = dynamicForm.get("q");
		String tid = dynamicForm.get("id");
		
		Timesheet timesheet = Timesheet.find.where().eq("tid", tid).findUnique();
		
		boolean isApproved;
		if(query.equals("yes")){
			isApproved = true;
		}
		else{
			isApproved = false;
		}
		
		
		if(timesheet != null){
			String pid = timesheet.processInstanceId;
			TimesheetWorkflowUtils.setVariableToTask(pid, isApproved,timesheet.tid);
		}else{
		}
		
		return "";
		//return ok();
	}
	
	
	@RequestMapping(value = "/timesheetExcelReport", method= RequestMethod.GET)
	public String excelReport(@CookieValue("username") String username, HttpServletResponse response,HttpServletRequest request) throws IOException {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("email", username);
		
		response.setContentType("application/vnd.ms-excel");
		HSSFWorkbook hssfWorkbook =  TimesheetSearchContext.getInstance().build().doExcel(form);
		File f = new File("excelReport.xls");
		FileOutputStream fileOutputStream = new FileOutputStream(f);
		hssfWorkbook.write(fileOutputStream);
		response.setHeader("Content-Disposition", "attachment; filename=excelReport.xls");
		return "";
		//return ok(f).as("application/vnd.ms-excel");
    }
}