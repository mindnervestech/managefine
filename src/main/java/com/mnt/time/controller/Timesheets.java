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

import models.Client;
import models.MailSetting;
import models.Project;
import models.Supplier;
import models.Task;
import models.TaskComment;
import models.TaskDetails;
import models.Timesheet;
import models.TimesheetActual;
import models.TimesheetDays;
import models.TimesheetDaysActual;
import models.TimesheetRow;
import models.TimesheetRowActual;
import models.User;
import models.UserLeave;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
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
import org.springframework.web.multipart.MultipartFile;

import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import utils.EmailExceptionHandler;
import utils.SelectUIMap;
import viewmodel.GanttTask;
import viewmodel.GanttVM;
import viewmodel.MonthVM;
import viewmodel.ProjectVM;
import viewmodel.StaffLeaveVM;
import viewmodel.StaffVM;
import viewmodel.SupplierVM;
import viewmodel.TaskCommentVM;
import viewmodel.TaskDetailVM;
import viewmodel.TaskVM;
import viewmodel.TimesheetRowVM;
import viewmodel.TimesheetVM;
import viewmodel.WeekDayVM;
import viewmodel.WidgetVM;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.SqlRow;
import com.custom.domain.TimesheetStatus;
import com.custom.emails.Email;
import com.custom.exception.NoTimeSheetFoudException;
import com.custom.helpers.TimesheetActualSearchContext;
import com.custom.helpers.TimesheetSearchContext;
import com.custom.workflow.timesheet.TimesheetWorkflowUtils;
import com.google.common.collect.Lists;
import com.mnt.createProject.model.ProjectAttachment;
import com.mnt.createProject.model.Projectinstance;
import com.mnt.createProject.model.Projectinstancenode;
import com.mnt.orghierarchy.model.Organization;
import com.mnt.orghierarchy.vm.OrganizationVM;
import com.mnt.projectHierarchy.model.Projectclassnode;
import com.mnt.projectHierarchy.vm.ProjectsupportattributVM;
import com.mnt.time.service.TimesheetService;

import dto.fixtures.MenuBarFixture;
/*
@Security.Authenticated(Secured.class)
@BasicAuth*/

@Controller
public class Timesheets{
	
	private static HashMap<String,Integer> timesheetRowsMap = null;
	
	@Value("${imageRootDir}")
	String imageRootDir;
	
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
    }
    
	@RequestMapping(value="/editSchedule", method = RequestMethod.GET)
	public String editSchedule(ModelMap model, @CookieValue("username") String username) {
		User user = User.findByEmail(username);
		
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);
		
		return "editSchedule";
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
		
		if(cal.get(Calendar.DAY_OF_WEEK)==1) {
			cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR)-1);
		}
		
		model.addAttribute("weekReport", Json.toJson(timesheetService.getWeekReport(cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), user, date)));
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);
		
		return "schedularWeekReport";
    }
	
	@RequestMapping(value="/showUsageReport", method = RequestMethod.GET)
	public String showUsageReport(ModelMap model, @CookieValue("username") String username) {
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
		
		if(cal.get(Calendar.DAY_OF_WEEK)==1) {
			cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR)-1);
		}
		
		model.addAttribute("plannedTask", Json.toJson(timesheetService.getCalendarTaskWeekReport(cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), user, date)));
		model.addAttribute("actualTask", Json.toJson(timesheetService.getTimesheetTaskWeekReport(cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), user, date)));
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);
		
		return "showUsageReport";
    }
	
	@RequestMapping(value="/showGantt/{id}", method = RequestMethod.GET)
	public String showGantt(ModelMap model, @CookieValue("username") String username, @PathVariable("id") String id) {
		User user = User.findByEmail(username);
		
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);
		
		model.addAttribute("data",Json.toJson(timesheetService.getProjectData(Long.parseLong(id))));
		return "showGantt";
    }
	
	@RequestMapping(value="/getOrganizations", method = RequestMethod.GET)
	public @ResponseBody JsonNode getOrganizations(ModelMap model,@RequestParam("userId") String userId) {
		User user = User.findById(Long.parseLong(userId));
		List<Organization> orgList = Organization.getOrganizationsByCompanyId(user.getCompanyobject().getId());
		List<OrganizationVM> orgVMList = new ArrayList<>();
		
		for(Organization org : orgList) {
			OrganizationVM vm = new OrganizationVM();
			vm.setId(org.getId());
			vm.setOrganizationName(org.getOrganizationName());
			orgVMList.add(vm);
		}
		
		return Json.toJson(orgVMList);
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
		if(cal.get(Calendar.DAY_OF_WEEK)==1) {
			cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR)-1);
		}
		return Json.toJson(timesheetService.getWeekReport(cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), user, dt));
		
	}
	
	@RequestMapping(value="/getTaskLevelReport", method = RequestMethod.GET)
	public @ResponseBody JsonNode getTaskLevelReport(ModelMap model,@RequestParam("userId") String userId,@RequestParam("date") String date) {
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
		if(cal.get(Calendar.DAY_OF_WEEK)==1) {
			cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR)-1);
		}
		Map map = new HashMap<>();
		map.put("plannedTasks", timesheetService.getCalendarTaskWeekReport(cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), user, dt));
		map.put("actualTasks", timesheetService.getTimesheetTaskWeekReport(cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), user, dt));
		return Json.toJson(map);
		
	}
	
	@RequestMapping(value="/getStageLevelReport", method = RequestMethod.GET)
	public @ResponseBody JsonNode getStageLevelReport(ModelMap model,@RequestParam("userId") String userId,@RequestParam("date") String date) {
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
		if(cal.get(Calendar.DAY_OF_WEEK)==1) {
			cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR)-1);
		}
		Map map = new HashMap<>();
		map.put("plannedStages", timesheetService.getCalendarStageWeekReport(cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), user));
		map.put("actualStages", timesheetService.getTimesheetStageWeekReport(cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), user));
		return Json.toJson(map);
		
	}
	
	@RequestMapping(value="/getTaskWeekTotal", method = RequestMethod.GET)
	public @ResponseBody JsonNode getTaskWeekTotal(ModelMap model,@RequestParam("userId") String userId,@RequestParam("date") String date) {
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
		if(cal.get(Calendar.DAY_OF_WEEK)==1) {
			cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR)-1);
		}
		return Json.toJson(timesheetService.getTaskWeekTotal(cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), user));
		
	}
	
	@RequestMapping(value="/getStageWeekTotal", method = RequestMethod.GET)
	public @ResponseBody JsonNode getStageWeekTotal(ModelMap model,@RequestParam("userId") String userId,@RequestParam("date") String date) {
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
		if(cal.get(Calendar.DAY_OF_WEEK)==1) {
			cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR)-1);
		}
		return Json.toJson(timesheetService.getStageWeekTotal(cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), user));
		
	}
	
	@RequestMapping(value="/getStaffStageReport", method = RequestMethod.GET)
	public @ResponseBody JsonNode getStaffStageReport(ModelMap model,@RequestParam("userId") String userId,@RequestParam("date") String date) {
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
		if(cal.get(Calendar.DAY_OF_WEEK)==1) {
			cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR)-1);
		}
		return Json.toJson(timesheetService.getStageReport(cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), user));
		
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
		if(cal.get(Calendar.DAY_OF_WEEK)==1) {
			cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR)-1);
		} 
		return Json.toJson(timesheetService.getTodayAllTimesheet(cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), user, dt));
		
	}
	
	@RequestMapping(value="/getUserStaffData", method = RequestMethod.GET)
	public @ResponseBody JsonNode getUserStaffData(ModelMap model,@RequestParam("userId") String userId) {
		User user = User.findById(Long.parseLong(userId));
		List<User> userList = User.findByManager(user);
		List<StaffVM> staffList = new ArrayList<>();
		StaffVM staffVM = new StaffVM();
		staffVM.id = user.getId();
		staffVM.name = user.getFirstName()+" "+user.getLastName();
		staffList.add(staffVM);
		for(User userObj : userList) {
			StaffVM staffObj = new StaffVM();
			staffObj.id = userObj.getId();
			staffObj.name = userObj.getFirstName()+" "+userObj.getLastName();
			staffList.add(staffObj);
		}
		
		return Json.toJson(staffList);
	}	
	
	@RequestMapping(value="/getUsersForReport", method = RequestMethod.GET)
	public @ResponseBody JsonNode getUsersForReport(ModelMap model,@RequestParam("userId") String userId) {
		User user = User.findById(Long.parseLong(userId));
		List<StaffVM> staffList = new ArrayList<>();
		StaffVM staffVM = new StaffVM();
		staffVM.id = user.getId();
		staffVM.name = user.getFirstName()+" "+user.getLastName();
		staffList.add(staffVM);
		if(user.getUsertype() == null || user.getUsertype().equals("Admin")) {
			List<User> userList = User.findByUserType();
			for(User userObj : userList) {
				StaffVM staffObj = new StaffVM();
				staffObj.id = userObj.getId();
				staffObj.name = userObj.getFirstName()+" "+userObj.getLastName();
				staffList.add(staffObj);
			}
		} else {
			List<User> userList = User.findByManager(user);
			for(User userObj : userList) {
				StaffVM staffObj = new StaffVM();
				staffObj.id = userObj.getId();
				staffObj.name = userObj.getFirstName()+" "+userObj.getLastName();
				staffList.add(staffObj);
			}
		}
		
		return Json.toJson(staffList);
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
		if(cal.get(Calendar.DAY_OF_WEEK)==1) {
			cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR)-1);
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
		if(cal.get(Calendar.DAY_OF_WEEK)==1) {
			cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR)-1);
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
		Map map = new HashMap();
		User user = User.findById(Long.parseLong(userId));
		List<SqlRow> sqlRows = Projectinstance.getProjectsOfUser(user.id);
		List<ProjectVM> vmList = new ArrayList<>();
		for(SqlRow row: sqlRows) {
			Projectinstance projectInstance = Projectinstance.getById(row.getLong("projectinstance_id"));
			ProjectVM vm = new ProjectVM();
			vm.projectCode = projectInstance.getProjectName();
			vm.id = projectInstance.getId();
			
			List<Projectinstancenode> instanceNodeList = Projectinstancenode.getProjectInstanceByIdAndType(projectInstance.getId(), projectInstance.getProjectid());
			
			List<TaskVM> taskVMList = new ArrayList<>();
			for(Projectinstancenode node : instanceNodeList) {
				Projectclassnode classNode = node.getProjectclassnode();
				boolean flag = false;
				for(Projectinstancenode nodeData : instanceNodeList) {
					Projectclassnode classNodeData = nodeData.getProjectclassnode();
					if(classNodeData.getParentId() == classNode.getId()) {
						flag = true;
						break;
					} 
				}
				 if(flag == false) {
					 TaskVM taskVM = new TaskVM();
					 taskVM.id = classNode.getId();
					 taskVM.taskCode = classNode.getProjectTypes();
					 taskVMList.add(taskVM);
				 }
			}
			
			vm.tasklist = taskVMList;
			vmList.add(vm);
		}
		
			List<Projectinstance> projectList = Projectinstance.getProjectsOfManager(user);
			for(Projectinstance project : projectList) {
				ProjectVM vm = new ProjectVM();
				vm.projectCode = project.getProjectName();
				vm.id = project.getId();
				
				List<Projectinstancenode> instanceNodeList = Projectinstancenode.getProjectInstanceByIdAndType(project.getId(), project.getProjectid());
				
				List<TaskVM> taskVMList = new ArrayList<>();
				for(Projectinstancenode node : instanceNodeList) {
					Projectclassnode classNode = node.getProjectclassnode();
					boolean flag = false;
					for(Projectinstancenode nodeData : instanceNodeList) {
						Projectclassnode classNodeData = nodeData.getProjectclassnode();
						if(classNodeData.getParentId() == classNode.getId()) {
							flag = true;
							break;
						} 
					}
					 if(flag == false) {
						 TaskVM taskVM = new TaskVM();
						 taskVM.id = classNode.getId();
						 taskVM.taskCode = classNode.getProjectTypes();
						 taskVMList.add(taskVM);
					 }
				}
				
				vm.tasklist = taskVMList;
				vmList.add(vm);
			}
		
		map.put("projectList", vmList);
		
		List<Supplier> supplierList = Supplier.getSupplierList();
		List<SupplierVM> supplierVMList = new ArrayList<>();
		for(Supplier sup: supplierList) {
			SupplierVM vm = new SupplierVM();
			vm.id = sup.getId();
			vm.name = sup.getSupplierName();
			supplierVMList.add(vm);
		}
		
		map.put("supplierList", supplierVMList);
		
		List<Client> clientList = Client.getClientList();
		List<SupplierVM> customerVMList = new ArrayList<>();
		for(Client client: clientList) {
			SupplierVM vm = new SupplierVM();
			vm.id = client.getId();
			vm.name = client.getClientName();
			customerVMList.add(vm);
		}
		
		map.put("customerList", customerVMList);
		
		return Json.toJson(map);
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
		
		if(cal.get(Calendar.DAY_OF_WEEK)==1) {
			cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR)-1);
		}
		return timesheetService.getScheduleByDate(Long.parseLong(userId), cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), cal.getTime());
	}
	
	@RequestMapping(value="/saveFile",method=RequestMethod.POST) 
	public @ResponseBody JsonNode saveFile(@RequestParam("file")MultipartFile file,TaskDetailVM taskVM) {
		
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		
		User user = User.findById(taskVM.getUserId());
		
			String[] filenames = file.getOriginalFilename().split("\\.");
			String filename = imageRootDir+File.separator+ "taskAttachment" + File.separator +filenames[0]+ "_" + user.getId() +"."+filenames[filenames.length-1];
			
			File f = new File(filename);
			try {
				file.transferTo(f);
		
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			TaskDetails taskDetails = new TaskDetails();
			taskDetails.projectId = taskVM.getProjectId();
			taskDetails.taskId = taskVM.getTaskId();
			taskDetails.startTime = taskVM.getStartTime();
			taskDetails.endTime = taskVM.getEndTime();
			taskDetails.status = taskVM.getStatus();
			Calendar cal = Calendar.getInstance();
			try {
				taskDetails.date = format.parse(taskVM.getDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			taskDetails.fileName = filenames[0];
			taskDetails.filePath = filename;
			taskDetails.user = user;
			
			taskDetails.save();
			
			List<TaskDetailVM> vmList = new ArrayList<>();
			List<TaskDetails> taskDetailList = TaskDetails.getDetailsByUser(user);
			for(TaskDetails task : taskDetailList) {
				TaskDetailVM vm = new TaskDetailVM();
				vm.id = task.getId();
				vm.date = format.format(task.getDate());
				vm.fileName = task.getFileName();
				vmList.add(vm);
			}
			
			return Json.toJson(vmList);
	}
	
	@RequestMapping(value="/getTaskDetails",method=RequestMethod.GET) 
	public @ResponseBody JsonNode getTaskDetails(@RequestParam("userId") String userId,@RequestParam("projectId") String projectId,@RequestParam("taskId") String taskId) {
		User user = User.findById(Long.parseLong(userId));
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		List<TaskDetailVM> vmList = new ArrayList<>();
		List<TaskDetails> taskDetailList = TaskDetails.getDetailsByUser(user);
		for(TaskDetails task : taskDetailList) {
			TaskDetailVM vm = new TaskDetailVM();
			vm.id = task.getId();
			vm.date = format.format(task.getDate());
			vm.fileName = task.getFileName();
			vmList.add(vm);
		}
		
		List<TaskComment> commentList = TaskComment.getByUserAndTask(user, Long.parseLong(projectId), Long.parseLong(taskId));
		List<TaskCommentVM> commentVMList = new ArrayList<>();
		for(TaskComment comment : commentList) {
			TaskCommentVM commentVM = new TaskCommentVM();
			commentVM.userName = comment.getUser().getFirstName()+" "+comment.getUser().getLastName();
			commentVM.date = format.format(comment.getDate());
			commentVM.comment = comment.getComment();
			commentVMList.add(commentVM);
		}
		
		Map map = new HashMap();
		map.put("taskDetails", vmList);
		map.put("commentDetails", commentVMList);
		
		return Json.toJson(map);
	}
	
	@RequestMapping(value = "/downloadTaskFile", method = RequestMethod.POST)
	@ResponseBody
	public FileSystemResource downloadTaskFile(HttpServletResponse response, @RequestParam("attchId") String attchId)
	{
		TaskDetails taskdetails = TaskDetails.getById(Long.parseLong(attchId));
		
		 response.setContentType("application/x-download");
         response.setHeader("Content-Transfer-Encoding", "binary"); 
         response.setHeader("Content-disposition","attachment; filename=\""+taskdetails.getFileName());
         File file = new File(taskdetails.getFilePath());
         
         return new FileSystemResource(file);
		
	}
	
	@RequestMapping(value="/saveComment", method = RequestMethod.GET)
	public @ResponseBody JsonNode saveComment(ModelMap model,@RequestParam("userId") String userId,@RequestParam("comment") String comment,@RequestParam("projectId") String projectId,@RequestParam("taskId") String taskId) {
		User user = User.findById(Long.parseLong(userId));
		TaskComment taskComment = new TaskComment();
		taskComment.comment = comment;
		taskComment.date = new Date();
		taskComment.projectId = Long.parseLong(projectId);
		taskComment.taskId = Long.parseLong(taskId);
		taskComment.user = user;
		taskComment.save();
		
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		List<TaskComment> commentList = TaskComment.getByUserAndTask(user, Long.parseLong(projectId), Long.parseLong(taskId));
		List<TaskCommentVM> commentVMList = new ArrayList<>();
		for(TaskComment commentObj : commentList) {
			TaskCommentVM commentVM = new TaskCommentVM();
			commentVM.userName = commentObj.getUser().getFirstName()+" "+commentObj.getUser().getLastName();
			commentVM.date = format.format(commentObj.getDate());
			commentVM.comment = commentObj.getComment();
			commentVMList.add(commentVM);
		}
		
		return Json.toJson(commentVMList);
		
	}	
	
	@RequestMapping(value="/updateTaskStatus", method = RequestMethod.GET)
	public @ResponseBody void updateTaskStatus(ModelMap model,@RequestParam("projectId") String projectId,@RequestParam("taskId") String taskId,@RequestParam("status") String status) {
		Projectclassnode classNode = Projectclassnode.getProjectById(Long.parseLong(taskId)); 
		Projectinstancenode node = Projectinstancenode.getByClassNodeAndInstance(classNode, Long.parseLong(projectId));
		node.setStatus(status);
		node.update();
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
		
		if(day.equals("sunday")) {
			weekOfYear = weekOfYear - 1;
		}
		
		Timesheet timesheet = Timesheet.getByUserWeekAndYear(user, weekOfYear, yearVal);
		List<WeekDayVM> weekDayList = new ArrayList<>();
		if(timesheet != null) {
			List<TimesheetRow> timesheetRowList = TimesheetRow.getByTimesheet(timesheet);
			
			for(TimesheetRow row: timesheetRowList) {
				WeekDayVM vm = new WeekDayVM();
				vm.projectCode = Projectinstance.getById(Long.parseLong(row.getProjectCode())).getProjectName();
				vm.taskCode = Projectclassnode.getProjectById(Long.parseLong(row.getTaskCode())).getProjectTypes();
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
			if(cal.get(Calendar.DAY_OF_WEEK)==1) {
				cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR)-1);
			} 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		return timesheetService.getScheduleByWeek(Long.parseLong(userId), cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), cal.getTime());
	}
	
	@RequestMapping(value="/getMonthSchedule", method = RequestMethod.GET)
	public @ResponseBody Map getMonthSchedule(ModelMap model,@RequestParam("date") String date,@RequestParam("userId") String userId) {
		
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
		
		Calendar cal = Calendar.getInstance();
		Boolean[] holidayList = {false,false,false,false,false,false,false};
		
		List<UserLeave> userLeaveList = UserLeave.getUserWeeklyLeaveList();
		if(userLeaveList.size() != 0) {
			int i = 0;
			for(UserLeave userLeave: userLeaveList) {
				holidayList[userLeave.getLeaveType()] = true;
				i++;
			}
		}	
		
		int weekOfYear = Integer.parseInt(week);
		
		for(int i=0;i<=6;i++) {
			cal.set(Calendar.WEEK_OF_YEAR, weekOfYear);
			if(i == 0) {
				cal.set(Calendar.WEEK_OF_YEAR, weekOfYear+1);
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			}
			if(i == 1) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			}
			if(i == 2) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
			}
			if(i == 3) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
			}
			if(i == 4) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
			}
			if(i == 5) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			}
			if(i == 6) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dy = sdf.format(cal.getTime());
			
			UserLeave leave = null;
			try {
				leave = UserLeave.getLeave(sdf.parse(dy));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if(leave != null) {
				holidayList[i] = true;
			}
			
		}
		
		Map map = new HashMap<>();
		
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
				timesheetRowVM.projectCode = Long.parseLong(timesheetRow.getProjectCode());
				timesheetRowVM.taskCode = Long.parseLong(timesheetRow.getTaskCode());
				timesheetRowVM.isOverTime = timesheetRow.isOverTime();
				
				for(TimesheetDaysActual day : timesheetDaysList) {
					if(day.getDay().equals("monday")) {
						timesheetRowVM.monFrom = day.getTimeFrom();
						timesheetRowVM.monTo = day.getTimeTo();
						timesheetRowVM.mondayId = day.getId();
						timesheetRowVM.monSupplier = day.getSupplierId();
						timesheetRowVM.monCustomer = day.getCustomerId();
						timesheetRowVM.monNotes = day.getNotes();
					}
					if(day.getDay().equals("tuesday")) {
						timesheetRowVM.tueFrom = day.getTimeFrom();
						timesheetRowVM.tueTo = day.getTimeTo();
						timesheetRowVM.tuesdayId = day.getId();
						timesheetRowVM.tueSupplier = day.getSupplierId();
						timesheetRowVM.tueCustomer = day.getCustomerId();
						timesheetRowVM.tueNotes = day.getNotes();
					}
					
					if(day.getDay().equals("wednesday")) {
						timesheetRowVM.wedFrom = day.getTimeFrom();
						timesheetRowVM.wedTo = day.getTimeTo();
						timesheetRowVM.wednesdayId = day.getId();
						timesheetRowVM.wedSupplier = day.getSupplierId();
						timesheetRowVM.wedCustomer = day.getCustomerId();
						timesheetRowVM.wedNotes = day.getNotes();
					}
					
					if(day.getDay().equals("thursday")) {
						timesheetRowVM.thuFrom = day.getTimeFrom();
						timesheetRowVM.thuTo = day.getTimeTo();
						timesheetRowVM.thursdayId = day.getId();
						timesheetRowVM.thuSupplier = day.getSupplierId();
						timesheetRowVM.thuCustomer = day.getCustomerId();
						timesheetRowVM.thuNotes = day.getNotes();
					}
					if(day.getDay().equals("friday")) {
						timesheetRowVM.friFrom = day.getTimeFrom();
						timesheetRowVM.friTo = day.getTimeTo();
						timesheetRowVM.fridayId = day.getId();
						timesheetRowVM.friSupplier = day.getSupplierId();
						timesheetRowVM.friCustomer = day.getCustomerId();
						timesheetRowVM.friNotes = day.getNotes();
					}
					if(day.getDay().equals("saturday")) {
						timesheetRowVM.satFrom = day.getTimeFrom();
						timesheetRowVM.satTo = day.getTimeTo();
						timesheetRowVM.saturdayId = day.getId();
						timesheetRowVM.satSupplier = day.getSupplierId();
						timesheetRowVM.satCustomer = day.getCustomerId();
						timesheetRowVM.satNotes = day.getNotes();
					}
					if(day.getDay().equals("sunday")) {
						timesheetRowVM.sunFrom = day.getTimeFrom();
						timesheetRowVM.sunTo = day.getTimeTo();
						timesheetRowVM.sundayId = day.getId();
						timesheetRowVM.sunSupplier = day.getSupplierId();
						timesheetRowVM.sunCustomer = day.getCustomerId();
						timesheetRowVM.sunNotes = day.getNotes();
					}
					timesheetRowVM.totalmins = day.getWorkMinutes();
				}
				timesheetRowVMList.add(timesheetRowVM);
			}
			timesheetVM.timesheetRows = timesheetRowVMList;
			
			map.put("holidayList", holidayList);
			map.put("timesheetData", timesheetVM);
			return Json.toJson(map);
		} else {
			map.put("holidayList", holidayList);
			map.put("timesheetData", "");
			return Json.toJson(map);
		}
	}
	
	@RequestMapping(value="/getTimesheetBySelectedWeek", method = RequestMethod.GET)
	public @ResponseBody JsonNode getTimesheetBySelectedWeek(ModelMap model,@RequestParam("userId") String userId,@RequestParam("week") String week,@RequestParam("year") String year) {
		
		User user = User.findById(Long.parseLong(userId));
		Calendar cal = Calendar.getInstance();
		Boolean[] holidayList = {false,false,false,false,false,false,false};
		
		List<UserLeave> userLeaveList = UserLeave.getUserWeeklyLeaveList();
		if(userLeaveList.size() != 0) {
			int i = 0;
			for(UserLeave userLeave: userLeaveList) {
				holidayList[userLeave.getLeaveType()] = true;
				i++;
			}
		}	
		
		int weekOfYear = Integer.parseInt(week);
		
		for(int i=0;i<=6;i++) {
			cal.set(Calendar.WEEK_OF_YEAR, weekOfYear);
			if(i == 0) {
				cal.set(Calendar.WEEK_OF_YEAR, weekOfYear+1);
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			}
			if(i == 1) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			}
			if(i == 2) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
			}
			if(i == 3) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
			}
			if(i == 4) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
			}
			if(i == 5) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			}
			if(i == 6) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dy = sdf.format(cal.getTime());
			
			UserLeave leave = null;
			try {
				leave = UserLeave.getLeave(sdf.parse(dy));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if(leave != null) {
				holidayList[i] = true;
			}
			
		}
		
		Map map = new HashMap<>();
		
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
				timesheetRowVM.projectCode = Long.parseLong(timesheetRow.getProjectCode());
				timesheetRowVM.taskCode = Long.parseLong(timesheetRow.getTaskCode());
				timesheetRowVM.isOverTime = timesheetRow.isOverTime();
				
				for(TimesheetDays day : timesheetDaysList) {
					if(day.getDay().equals("monday")) {
						timesheetRowVM.monFrom = day.getTimeFrom();
						timesheetRowVM.monTo = day.getTimeTo();
						timesheetRowVM.mondayId = day.getId();
						timesheetRowVM.monSupplier = day.getSupplierId();
						timesheetRowVM.monCustomer = day.getCustomerId();
						timesheetRowVM.monNotes = day.getNotes();
					}
					if(day.getDay().equals("tuesday")) {
						timesheetRowVM.tueFrom = day.getTimeFrom();
						timesheetRowVM.tueTo = day.getTimeTo();
						timesheetRowVM.tuesdayId = day.getId();
						timesheetRowVM.tueSupplier = day.getSupplierId();
						timesheetRowVM.tueCustomer = day.getCustomerId();
						timesheetRowVM.tueNotes = day.getNotes();
					}
					
					if(day.getDay().equals("wednesday")) {
						timesheetRowVM.wedFrom = day.getTimeFrom();
						timesheetRowVM.wedTo = day.getTimeTo();
						timesheetRowVM.wednesdayId = day.getId();
						timesheetRowVM.wedSupplier = day.getSupplierId();
						timesheetRowVM.wedCustomer = day.getCustomerId();
						timesheetRowVM.wedNotes = day.getNotes();
					}
					
					if(day.getDay().equals("thursday")) {
						timesheetRowVM.thuFrom = day.getTimeFrom();
						timesheetRowVM.thuTo = day.getTimeTo();
						timesheetRowVM.thursdayId = day.getId();
						timesheetRowVM.thuSupplier = day.getSupplierId();
						timesheetRowVM.thuCustomer = day.getCustomerId();
						timesheetRowVM.thuNotes = day.getNotes();
					}
					if(day.getDay().equals("friday")) {
						timesheetRowVM.friFrom = day.getTimeFrom();
						timesheetRowVM.friTo = day.getTimeTo();
						timesheetRowVM.fridayId = day.getId();
						timesheetRowVM.friSupplier = day.getSupplierId();
						timesheetRowVM.friCustomer = day.getCustomerId();
						timesheetRowVM.friNotes = day.getNotes();
					}
					if(day.getDay().equals("saturday")) {
						timesheetRowVM.satFrom = day.getTimeFrom();
						timesheetRowVM.satTo = day.getTimeTo();
						timesheetRowVM.saturdayId = day.getId();
						timesheetRowVM.satSupplier = day.getSupplierId();
						timesheetRowVM.satCustomer = day.getCustomerId();
						timesheetRowVM.satNotes = day.getNotes();
					}
					if(day.getDay().equals("sunday")) {
						timesheetRowVM.sunFrom = day.getTimeFrom();
						timesheetRowVM.sunTo = day.getTimeTo();
						timesheetRowVM.sundayId = day.getId();
						timesheetRowVM.sunSupplier = day.getSupplierId();
						timesheetRowVM.sunCustomer = day.getCustomerId();
						timesheetRowVM.sunNotes = day.getNotes();
					}
					timesheetRowVM.totalmins = day.getWorkMinutes();
				}
				timesheetRowVMList.add(timesheetRowVM);
			}
			timesheetVM.timesheetRows = timesheetRowVMList;
			map.put("holidayList", holidayList);
			map.put("timesheetData", timesheetVM);
			return Json.toJson(map);
		} else {
			map.put("holidayList", holidayList);
			map.put("timesheetData", "");
			return Json.toJson(map);
		}
		
	}
	
	@RequestMapping(value="/getActualTimesheetByCurrentWeek", method = RequestMethod.GET)
	public @ResponseBody JsonNode getActualTimesheetByCurrentWeek(ModelMap model,@RequestParam("userId") String userId) {
		
		User user = User.findById(Long.parseLong(userId));
		Calendar cal = Calendar.getInstance();
		Boolean[] holidayList = {false,false,false,false,false,false,false};
		
		if(cal.get(Calendar.DAY_OF_WEEK)==1) {
			cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR)-1);
		} 
		
		List<UserLeave> userLeaveList = UserLeave.getUserWeeklyLeaveList();
		if(userLeaveList.size() != 0) {
			int i = 0;
			for(UserLeave userLeave: userLeaveList) {
				holidayList[userLeave.getLeaveType()] = true;
				i++;
			}
		}	
		
		int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
		
		for(int i=0;i<=6;i++) {
			cal.set(Calendar.WEEK_OF_YEAR, weekOfYear);
			if(i == 0) {
				cal.set(Calendar.WEEK_OF_YEAR, weekOfYear+1);
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			}
			if(i == 1) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			}
			if(i == 2) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
			}
			if(i == 3) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
			}
			if(i == 4) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
			}
			if(i == 5) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			}
			if(i == 6) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dy = sdf.format(cal.getTime());
			
			UserLeave leave = null;
			try {
				leave = UserLeave.getLeave(sdf.parse(dy));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if(leave != null) {
				holidayList[i] = true;
			}
			
		}
		
		Map map = new HashMap<>();
		
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
				timesheetRowVM.projectCode = Long.parseLong(timesheetRow.getProjectCode());
				timesheetRowVM.taskCode = Long.parseLong(timesheetRow.getTaskCode());
				timesheetRowVM.isOverTime = timesheetRow.isOverTime();
				
				for(TimesheetDaysActual day : timesheetDaysList) {
					if(day.getDay().equals("monday")) {
						timesheetRowVM.monFrom = day.getTimeFrom();
						timesheetRowVM.monTo = day.getTimeTo();
						timesheetRowVM.mondayId = day.getId();
						timesheetRowVM.monSupplier = day.getSupplierId();
						timesheetRowVM.monCustomer = day.getCustomerId();
						timesheetRowVM.monNotes = day.getNotes();
					}
					if(day.getDay().equals("tuesday")) {
						timesheetRowVM.tueFrom = day.getTimeFrom();
						timesheetRowVM.tueTo = day.getTimeTo();
						timesheetRowVM.tuesdayId = day.getId();
						timesheetRowVM.tueSupplier = day.getSupplierId();
						timesheetRowVM.tueCustomer = day.getCustomerId();
						timesheetRowVM.tueNotes = day.getNotes();
					}
					
					if(day.getDay().equals("wednesday")) {
						timesheetRowVM.wedFrom = day.getTimeFrom();
						timesheetRowVM.wedTo = day.getTimeTo();
						timesheetRowVM.wednesdayId = day.getId();
						timesheetRowVM.wedSupplier = day.getSupplierId();
						timesheetRowVM.wedCustomer = day.getCustomerId();
						timesheetRowVM.wedNotes = day.getNotes();
					}
					
					if(day.getDay().equals("thursday")) {
						timesheetRowVM.thuFrom = day.getTimeFrom();
						timesheetRowVM.thuTo = day.getTimeTo();
						timesheetRowVM.thursdayId = day.getId();
						timesheetRowVM.thuSupplier = day.getSupplierId();
						timesheetRowVM.thuCustomer = day.getCustomerId();
						timesheetRowVM.thuNotes = day.getNotes();
					}
					if(day.getDay().equals("friday")) {
						timesheetRowVM.friFrom = day.getTimeFrom();
						timesheetRowVM.friTo = day.getTimeTo();
						timesheetRowVM.fridayId = day.getId();
						timesheetRowVM.friSupplier = day.getSupplierId();
						timesheetRowVM.friCustomer = day.getCustomerId();
						timesheetRowVM.friNotes = day.getNotes();
					}
					if(day.getDay().equals("saturday")) {
						timesheetRowVM.satFrom = day.getTimeFrom();
						timesheetRowVM.satTo = day.getTimeTo();
						timesheetRowVM.saturdayId = day.getId();
						timesheetRowVM.satSupplier = day.getSupplierId();
						timesheetRowVM.satCustomer = day.getCustomerId();
						timesheetRowVM.satNotes = day.getNotes();
					}
					if(day.getDay().equals("sunday")) {
						timesheetRowVM.sunFrom = day.getTimeFrom();
						timesheetRowVM.sunTo = day.getTimeTo();
						timesheetRowVM.sundayId = day.getId();
						timesheetRowVM.sunSupplier = day.getSupplierId();
						timesheetRowVM.sunCustomer = day.getCustomerId();
						timesheetRowVM.sunNotes = day.getNotes();
					}
					timesheetRowVM.totalmins = day.getWorkMinutes();
				}
				timesheetRowVMList.add(timesheetRowVM);
			}
			timesheetVM.timesheetRows = timesheetRowVMList;
			
			map.put("holidayList", holidayList);
			map.put("timesheetData", timesheetVM);
			return Json.toJson(map);
			
		} else {
			map.put("holidayList", holidayList);
			map.put("timesheetData", "");
			return Json.toJson(map);
		}
		
		
	}
	
	@RequestMapping(value="/addSupplierCustomer", method = RequestMethod.GET)
	public @ResponseBody void addSupplierCustomer(ModelMap model,@RequestParam("dayId") String dayId,@RequestParam("supplierCode") String supplierCode,@RequestParam("customerCode") String customerCode,@RequestParam("notes") String notes) {
		TimesheetDays day = TimesheetDays.findById(Long.parseLong(dayId));
		day.setSupplierId(supplierCode);
		day.setCustomerId(customerCode);
		day.setNotes(notes);
		day.update();
	}
	
	@RequestMapping(value="/addSupplierCustomerToActual", method = RequestMethod.GET)
	public @ResponseBody void addSupplierCustomerToActual(ModelMap model,@RequestParam("dayId") String dayId,@RequestParam("supplierCode") String supplierCode,@RequestParam("customerCode") String customerCode,@RequestParam("notes") String notes) {
		TimesheetDaysActual day = TimesheetDaysActual.findById(Long.parseLong(dayId));
		day.setSupplierId(supplierCode);
		day.setCustomerId(customerCode);
		day.setNotes(notes);
		day.update();
	}
	
	@RequestMapping(value="/getTimesheetByCurrentWeek", method = RequestMethod.GET)
	public @ResponseBody JsonNode getTimesheetByCurrentWeek(ModelMap model,@RequestParam("userId") String userId) {
		
		User user = User.findById(Long.parseLong(userId));
		Calendar cal = Calendar.getInstance();
		Boolean[] holidayList = {false,false,false,false,false,false,false};
		
		if(cal.get(Calendar.DAY_OF_WEEK)==1) {
			cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR)-1);
		} 
		
		List<UserLeave> userLeaveList = UserLeave.getUserWeeklyLeaveList();
		if(userLeaveList.size() != 0) {
			int i = 0;
			for(UserLeave userLeave: userLeaveList) {
				holidayList[userLeave.getLeaveType()] = true;
				i++;
			}
		}	
		
		int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
		
		for(int i=0;i<=6;i++) {
			cal.set(Calendar.WEEK_OF_YEAR, weekOfYear);
			if(i == 0) {
				cal.set(Calendar.WEEK_OF_YEAR, weekOfYear+1);
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			}
			if(i == 1) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			}
			if(i == 2) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
			}
			if(i == 3) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
			}
			if(i == 4) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
			}
			if(i == 5) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			}
			if(i == 6) {
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dy = sdf.format(cal.getTime());
			
			UserLeave leave = null;
			try {
				leave = UserLeave.getLeave(sdf.parse(dy));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if(leave != null) {
				holidayList[i] = true;
			}
			
		}
		
		Map map = new HashMap<>();
		
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
				timesheetRowVM.projectCode =Long.parseLong(timesheetRow.getProjectCode());
				timesheetRowVM.taskCode = Long.parseLong(timesheetRow.getTaskCode());
				timesheetRowVM.isOverTime = timesheetRow.isOverTime();
				
				for(TimesheetDays day : timesheetDaysList) {
					if(day.getDay().equals("monday")) {
						timesheetRowVM.monFrom = day.getTimeFrom();
						timesheetRowVM.monTo = day.getTimeTo();
						timesheetRowVM.mondayId = day.getId();
						timesheetRowVM.monSupplier = day.getSupplierId();
						timesheetRowVM.monCustomer = day.getCustomerId();
						timesheetRowVM.monNotes = day.getNotes();
					}
					if(day.getDay().equals("tuesday")) {
						timesheetRowVM.tueFrom = day.getTimeFrom();
						timesheetRowVM.tueTo = day.getTimeTo();
						timesheetRowVM.tuesdayId = day.getId();
						timesheetRowVM.tueSupplier = day.getSupplierId();
						timesheetRowVM.tueCustomer = day.getCustomerId();
						timesheetRowVM.tueNotes = day.getNotes();
					}
					
					if(day.getDay().equals("wednesday")) {
						timesheetRowVM.wedFrom = day.getTimeFrom();
						timesheetRowVM.wedTo = day.getTimeTo();
						timesheetRowVM.wednesdayId = day.getId();
						timesheetRowVM.wedSupplier = day.getSupplierId();
						timesheetRowVM.wedCustomer = day.getCustomerId();
						timesheetRowVM.wedNotes = day.getNotes();
					}
					
					if(day.getDay().equals("thursday")) {
						timesheetRowVM.thuFrom = day.getTimeFrom();
						timesheetRowVM.thuTo = day.getTimeTo();
						timesheetRowVM.thursdayId = day.getId();
						timesheetRowVM.thuSupplier = day.getSupplierId();
						timesheetRowVM.thuCustomer = day.getCustomerId();
						timesheetRowVM.thuNotes = day.getNotes();
					}
					if(day.getDay().equals("friday")) {
						timesheetRowVM.friFrom = day.getTimeFrom();
						timesheetRowVM.friTo = day.getTimeTo();
						timesheetRowVM.fridayId = day.getId();
						timesheetRowVM.friSupplier = day.getSupplierId();
						timesheetRowVM.friCustomer = day.getCustomerId();
						timesheetRowVM.friNotes = day.getNotes();
					}
					if(day.getDay().equals("saturday")) {
						timesheetRowVM.satFrom = day.getTimeFrom();
						timesheetRowVM.satTo = day.getTimeTo();
						timesheetRowVM.saturdayId = day.getId();
						timesheetRowVM.satSupplier = day.getSupplierId();
						timesheetRowVM.satCustomer = day.getCustomerId();
						timesheetRowVM.satNotes = day.getNotes();
					}
					if(day.getDay().equals("sunday")) {
						timesheetRowVM.sunFrom = day.getTimeFrom();
						timesheetRowVM.sunTo = day.getTimeTo();
						timesheetRowVM.sundayId = day.getId();
						timesheetRowVM.sunSupplier = day.getSupplierId();
						timesheetRowVM.sunCustomer = day.getCustomerId();
						timesheetRowVM.sunNotes = day.getNotes();
					}
					timesheetRowVM.totalmins = day.getWorkMinutes();
				}
				timesheetRowVMList.add(timesheetRowVM);
			}
			timesheetVM.timesheetRows = timesheetRowVMList;
			map.put("holidayList", holidayList);
			map.put("timesheetData", timesheetVM);
			return Json.toJson(map);
			
		} else {
			map.put("holidayList", holidayList);
			map.put("timesheetData", "");
			return Json.toJson(map);
		}
		
		
	}
	
	@RequestMapping(value="/timesheetRetract", method = RequestMethod.GET)
	public @ResponseBody String timesheetRetract(ModelMap model,@RequestParam("userId") String userId,@RequestParam("week") String week,@RequestParam("year") String year) {
		User user = User.findById(Long.parseLong(userId));
		Timesheet timesheet = Timesheet.getByUserWeekAndYear(user, Integer.parseInt(week), Integer.parseInt(year));
		timesheet.setStatus(TimesheetStatus.Draft);
		timesheet.setTimesheetWith(user);
		timesheet.update();
		return "";
	}
	
	
	@RequestMapping(value="/actualTimesheetRetract", method = RequestMethod.GET)
	public @ResponseBody String actualTimesheetRetract(ModelMap model,@RequestParam("userId") String userId,@RequestParam("week") String week,@RequestParam("year") String year) {
		User user = User.findById(Long.parseLong(userId));
		TimesheetActual timesheet = TimesheetActual.getByUserWeekAndYear(user, Integer.parseInt(week), Integer.parseInt(year));
		timesheet.setStatus(TimesheetStatus.Draft);
		timesheet.setTimesheetWith(user);
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
				timesheetRowVM.projectCode = Long.parseLong(timesheetRow.getProjectCode());
				timesheetRowVM.taskCode = Long.parseLong(timesheetRow.getTaskCode());
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
				vm.projectCode = Projectinstance.getById(Long.parseLong(row.getProjectCode())).getProjectName();
				vm.taskCode = Projectclassnode.getProjectById(Long.parseLong(row.getTaskCode())).getProjectTypes();
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
				timesheetRowVM.projectCode = Long.parseLong(timesheetRow.getProjectCode());
				timesheetRowVM.taskCode = Long.parseLong(timesheetRow.getTaskCode());
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
	public @ResponseBody JsonNode saveActualTimesheet(ModelMap model,@RequestBody TimesheetVM timesheet,HttpServletRequest request){
		
		User user = User.findById(timesheet.userId);
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.YEAR,timesheet.year);
		cal.set(Calendar.WEEK_OF_YEAR,timesheet.weekOfYear);
		
		TimesheetActual timesheetSavedObj = TimesheetActual.getByUserWeekAndYear(user, timesheet.weekOfYear, timesheet.year);
		
		try {
		
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
			timesheetRow.setProjectCode(rowVM.projectCode.toString());
			timesheetRow.setTaskCode(rowVM.taskCode.toString());
			timesheetRow.setTimesheetActual(timesheetObj);
			timesheetRow.setOverTime(rowVM.isOverTime);
			Projectclassnode classNode = Projectclassnode.getProjectById(Long.parseLong(rowVM.taskCode.toString()));
			while(classNode.getLevel() != 1) {
				classNode = Projectclassnode.getProjectById(classNode.getParentId());
			}
			timesheetRow.save();
			
			cal.set(Calendar.WEEK_OF_YEAR,timesheet.weekOfYear);
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
			monday.setSupplierId(rowVM.monSupplier);
			monday.setCustomerId(rowVM.monCustomer);
			monday.setNotes(rowVM.monNotes);
			monday.setTimesheetRowActual(timesheetRow);
			monday.setTaskCode(rowVM.taskCode.toString());
			monday.setWeekOfYear(timesheet.weekOfYear);
			
			if(classNode.getLevel() == 1) {
				monday.setStage(classNode.getId().toString());
			}
			monday.setUser(user);
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
			tuesday.setSupplierId(rowVM.tueSupplier);
			tuesday.setCustomerId(rowVM.tueCustomer);
			tuesday.setNotes(rowVM.tueNotes);
			tuesday.setTimesheetRowActual(timesheetRow);
			tuesday.setTaskCode(rowVM.taskCode.toString());
			tuesday.setWeekOfYear(timesheet.weekOfYear);
			
			if(classNode.getLevel() == 1) {
				tuesday.setStage(classNode.getId().toString());
			}
			tuesday.setUser(user);
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
			wednesday.setSupplierId(rowVM.wedSupplier);
			wednesday.setCustomerId(rowVM.wedCustomer);
			wednesday.setNotes(rowVM.wedNotes);
			wednesday.setTimesheetRowActual(timesheetRow);
			wednesday.setTaskCode(rowVM.taskCode.toString());
			wednesday.setWeekOfYear(timesheet.weekOfYear);
			
			if(classNode.getLevel() == 1) {
				wednesday.setStage(classNode.getId().toString());
			}
			wednesday.setUser(user);
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
			thursday.setSupplierId(rowVM.thuSupplier);
			thursday.setCustomerId(rowVM.thuCustomer);
			thursday.setNotes(rowVM.thuNotes);
			thursday.setTimesheetRowActual(timesheetRow);
			thursday.setTaskCode(rowVM.taskCode.toString());
			thursday.setWeekOfYear(timesheet.weekOfYear);
			
			if(classNode.getLevel() == 1) {
				thursday.setStage(classNode.getId().toString());
			}
			thursday.setUser(user);
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
			friday.setSupplierId(rowVM.friSupplier);
			friday.setCustomerId(rowVM.friCustomer);
			friday.setNotes(rowVM.friNotes);
			friday.setTimesheetRowActual(timesheetRow);
			friday.setTaskCode(rowVM.taskCode.toString());
			friday.setWeekOfYear(timesheet.weekOfYear);
			
			if(classNode.getLevel() == 1) {
				friday.setStage(classNode.getId().toString());
			}
			friday.setUser(user);
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
			saturday.setSupplierId(rowVM.satSupplier);
			saturday.setCustomerId(rowVM.satCustomer);
			saturday.setNotes(rowVM.satNotes);
			saturday.setTimesheetRowActual(timesheetRow);
			saturday.setTaskCode(rowVM.taskCode.toString());
			saturday.setWeekOfYear(timesheet.weekOfYear);
			
			if(classNode.getLevel() == 1) {
				saturday.setStage(classNode.getId().toString());
			}
			saturday.setUser(user);
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
			sunday.setSupplierId(rowVM.sunSupplier);
			sunday.setCustomerId(rowVM.sunCustomer);
			sunday.setNotes(rowVM.sunNotes);
			sunday.setTimesheetRowActual(timesheetRow);
			sunday.setTaskCode(rowVM.taskCode.toString());
			sunday.setWeekOfYear(timesheet.weekOfYear);
			
			if(classNode.getLevel() == 1) {
				sunday.setStage(classNode.getId().toString());
			}
			sunday.setUser(user);
			sunday.save();
			
		}
		
		} else {
			
			if(timesheet.status.equals("Submitted")) {
				timesheetSavedObj.setTimesheetWith(user.getManager());
			} else {
				timesheetSavedObj.setTimesheetWith(user);
			}
			timesheetSavedObj.setStatus(TimesheetStatus.valueOf(timesheet.status));
			timesheetSavedObj.update();
			
			for(TimesheetRowVM row: timesheet.timesheetRows) {
				
				Projectclassnode classNode = Projectclassnode.getProjectById(Long.parseLong(row.taskCode.toString()));
				while(classNode.getLevel() != 1) {
					classNode = Projectclassnode.getProjectById(classNode.getParentId());
				}
				
				if(row.rowId != 0L) {
				TimesheetRowActual timesheetRow =  TimesheetRowActual.findById(row.rowId);
				timesheetRow.setProjectCode(row.projectCode.toString());
				timesheetRow.setTaskCode(row.taskCode.toString());
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
				monday.setSupplierId(row.monSupplier);
				monday.setCustomerId(row.monCustomer);
				monday.setNotes(row.monNotes);
				monday.setTaskCode(row.taskCode.toString());
				monday.setWeekOfYear(timesheet.weekOfYear);
				
				if(classNode.getLevel() == 1) {
					monday.setStage(classNode.getId().toString());
				}
				monday.setUser(user);
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
				tuesday.setSupplierId(row.tueSupplier);
				tuesday.setCustomerId(row.tueCustomer);
				tuesday.setNotes(row.tueNotes);
				tuesday.setTaskCode(row.taskCode.toString());
				tuesday.setWeekOfYear(timesheet.weekOfYear);
				
				if(classNode.getLevel() == 1) {
					tuesday.setStage(classNode.getId().toString());
				}
				tuesday.setUser(user);
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
				wednesday.setSupplierId(row.wedSupplier);
				wednesday.setCustomerId(row.wedCustomer);
				wednesday.setNotes(row.wedNotes);
				wednesday.setTaskCode(row.taskCode.toString());
				wednesday.setWeekOfYear(timesheet.weekOfYear);
				
				if(classNode.getLevel() == 1) {
					wednesday.setStage(classNode.getId().toString());
				}
				wednesday.setUser(user);
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
				thursday.setSupplierId(row.thuSupplier);
				thursday.setCustomerId(row.thuCustomer);
				thursday.setNotes(row.thuNotes);
				thursday.setTaskCode(row.taskCode.toString());
				thursday.setWeekOfYear(timesheet.weekOfYear);
				
				if(classNode.getLevel() == 1) {
					thursday.setStage(classNode.getId().toString());
				}
				thursday.setUser(user);
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
				friday.setSupplierId(row.friSupplier);
				friday.setCustomerId(row.friCustomer);
				friday.setNotes(row.friNotes);
				friday.setTaskCode(row.taskCode.toString());
				friday.setWeekOfYear(timesheet.weekOfYear);
				
				if(classNode.getLevel() == 1) {
					friday.setStage(classNode.getId().toString());
				}
				friday.setUser(user);
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
				saturday.setSupplierId(row.satSupplier);
				saturday.setCustomerId(row.satCustomer);
				saturday.setNotes(row.satNotes);
				saturday.setTaskCode(row.taskCode.toString());
				saturday.setWeekOfYear(timesheet.weekOfYear);
				
				if(classNode.getLevel() == 1) {
					saturday.setStage(classNode.getId().toString());
				}
				saturday.setUser(user);
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
				sunday.setSupplierId(row.sunSupplier);
				sunday.setCustomerId(row.sunCustomer);
				sunday.setNotes(row.sunNotes);
				sunday.setTaskCode(row.taskCode.toString());
				sunday.setWeekOfYear(timesheet.weekOfYear);
				
				if(classNode.getLevel() == 1) {
					sunday.setStage(classNode.getId().toString());
				}
				sunday.setUser(user);
				sunday.update();
				
				} else {
					
					TimesheetRowActual timesheetRow = new TimesheetRowActual();
					timesheetRow.setProjectCode(row.projectCode.toString());
					timesheetRow.setTaskCode(row.taskCode.toString());
					timesheetRow.setTimesheetActual(timesheetSavedObj);
					timesheetRow.setOverTime(row.isOverTime);
					timesheetRow.save();
					
					cal.set(Calendar.WEEK_OF_YEAR,timesheet.weekOfYear);
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
					monday.setSupplierId(row.monSupplier);
					monday.setCustomerId(row.monCustomer);
					monday.setNotes(row.monNotes);
					monday.setTimesheetRowActual(timesheetRow);
					monday.setTaskCode(row.taskCode.toString());
					monday.setWeekOfYear(timesheet.weekOfYear);
					
					if(classNode.getLevel() == 1) {
						monday.setStage(classNode.getId().toString());
					}
					monday.setUser(user);
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
					tuesday.setSupplierId(row.tueSupplier);
					tuesday.setCustomerId(row.tueCustomer);
					tuesday.setNotes(row.tueNotes);
					tuesday.setTimesheetRowActual(timesheetRow);
					tuesday.setTaskCode(row.taskCode.toString());
					tuesday.setWeekOfYear(timesheet.weekOfYear);
					
					if(classNode.getLevel() == 1) {
						tuesday.setStage(classNode.getId().toString());
					}
					tuesday.setUser(user);
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
					wednesday.setSupplierId(row.wedSupplier);
					wednesday.setCustomerId(row.wedCustomer);
					wednesday.setNotes(row.wedNotes);
					wednesday.setTimesheetRowActual(timesheetRow);
					wednesday.setTaskCode(row.taskCode.toString());
					wednesday.setWeekOfYear(timesheet.weekOfYear);
					
					if(classNode.getLevel() == 1) {
						wednesday.setStage(classNode.getId().toString());
					}
					wednesday.setUser(user);
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
					thursday.setSupplierId(row.thuSupplier);
					thursday.setCustomerId(row.thuCustomer);
					thursday.setNotes(row.thuNotes);
					thursday.setTimesheetRowActual(timesheetRow);
					thursday.setTaskCode(row.taskCode.toString());
					thursday.setWeekOfYear(timesheet.weekOfYear);
					
					if(classNode.getLevel() == 1) {
						thursday.setStage(classNode.getId().toString());
					}
					thursday.setUser(user);
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
					friday.setSupplierId(row.friSupplier);
					friday.setCustomerId(row.friCustomer);
					friday.setNotes(row.friNotes);
					friday.setTimesheetRowActual(timesheetRow);
					friday.setTaskCode(row.taskCode.toString());
					friday.setWeekOfYear(timesheet.weekOfYear);
					
					if(classNode.getLevel() == 1) {
						friday.setStage(classNode.getId().toString());
					}
					friday.setUser(user);
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
					saturday.setSupplierId(row.satSupplier);
					saturday.setCustomerId(row.satCustomer);
					saturday.setNotes(row.satNotes);
					saturday.setTimesheetRowActual(timesheetRow);
					saturday.setTaskCode(row.taskCode.toString());
					saturday.setWeekOfYear(timesheet.weekOfYear);
					
					if(classNode.getLevel() == 1) {
						saturday.setStage(classNode.getId().toString());
					}
					saturday.setUser(user);
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
					sunday.setSupplierId(row.sunSupplier);
					sunday.setCustomerId(row.sunCustomer);
					sunday.setNotes(row.sunNotes);
					sunday.setTimesheetRowActual(timesheetRow);
					sunday.setTaskCode(row.taskCode.toString());
					sunday.setWeekOfYear(timesheet.weekOfYear);
					
					if(classNode.getLevel() == 1) {
						sunday.setStage(classNode.getId().toString());
					}
					sunday.setUser(user);
					sunday.save();
					
				}
				
			}
			
		}
			if(timesheet.status.equals("Submitted")) {
				MailSetting smtpSetting = MailSetting.find();
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
		List<TimesheetRowVM> timesheetRowVMList = new ArrayList<>();
		
		if(timesheetSavedObj != null) {

			TimesheetVM timesheetVM = new TimesheetVM();
			timesheetVM.id = timesheetSavedObj.getId();
			timesheetVM.status = timesheetSavedObj.getStatus().getName();
			timesheetVM.weekOfYear = timesheetSavedObj.getWeekOfYear();
			timesheetVM.year = timesheetSavedObj.getYear();
			for(TimesheetRowActual timesheetRow : timesheetSavedObj.getTimesheetRowsActual()) {
				List<TimesheetDaysActual> timesheetDaysList = TimesheetDaysActual.getByTimesheetRow(timesheetRow);
				
				TimesheetRowVM timesheetRowVM = new TimesheetRowVM();
				timesheetRowVM.rowId = timesheetRow.getId();
				timesheetRowVM.projectCode =Long.parseLong(timesheetRow.getProjectCode());
				timesheetRowVM.taskCode = Long.parseLong(timesheetRow.getTaskCode());
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
			TimesheetActual timesheetobj = TimesheetActual.getByUserWeekAndYear(user, timesheet.weekOfYear, timesheet.year);
			
			TimesheetVM timesheetVM = new TimesheetVM();
			timesheetVM.id = timesheetobj.getId();
			timesheetVM.status = timesheetobj.getStatus().getName();
			timesheetVM.weekOfYear = timesheetobj.getWeekOfYear();
			timesheetVM.year = timesheetobj.getYear();
			for(TimesheetRowActual timesheetRow : timesheetobj.getTimesheetRowsActual()) {
				List<TimesheetDaysActual> timesheetDaysList = TimesheetDaysActual.getByTimesheetRow(timesheetRow);
				
				TimesheetRowVM timesheetRowVM = new TimesheetRowVM();
				timesheetRowVM.rowId = timesheetRow.getId();
				timesheetRowVM.projectCode =Long.parseLong(timesheetRow.getProjectCode());
				timesheetRowVM.taskCode = Long.parseLong(timesheetRow.getTaskCode());
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
		}
		
	}
	
	
	
	
	@RequestMapping(value="/saveTimesheet", method = RequestMethod.POST)
	public @ResponseBody JsonNode saveTimesheet(ModelMap model,@RequestBody TimesheetVM timesheet,HttpServletRequest request){
		
		User user = User.findById(timesheet.userId);
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.YEAR,timesheet.year);
		cal.set(Calendar.WEEK_OF_YEAR,timesheet.weekOfYear);
		
		Timesheet timesheetSavedObj = Timesheet.getByUserWeekAndYear(user, timesheet.weekOfYear, timesheet.year);
		
		try {
		
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
			timesheetRow.setProjectCode(rowVM.projectCode.toString());
			timesheetRow.setTaskCode(rowVM.taskCode.toString());
			timesheetRow.setTimesheet(timesheetObj);
			timesheetRow.setOverTime(rowVM.isOverTime);
			
			Projectclassnode classNode = Projectclassnode.getProjectById(Long.parseLong(rowVM.taskCode.toString()));
			while(classNode.getLevel() != 1) {
				classNode = Projectclassnode.getProjectById(classNode.getParentId());
			}
			
			
			timesheetRow.save();
			
			
			
			cal.set(Calendar.WEEK_OF_YEAR,timesheet.weekOfYear);
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
			monday.setSupplierId(rowVM.monSupplier);
			monday.setCustomerId(rowVM.monCustomer);
			monday.setNotes(rowVM.monNotes);
			monday.setTimesheetRow(timesheetRow);
			monday.setTaskCode(rowVM.taskCode.toString());
			monday.setWeekOfYear(timesheet.weekOfYear);
			
			if(classNode.getLevel() == 1) {
				monday.setStage(classNode.getId().toString());
			}
			monday.setUser(user);
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
			tuesday.setSupplierId(rowVM.tueSupplier);
			tuesday.setCustomerId(rowVM.tueCustomer);
			tuesday.setNotes(rowVM.tueNotes);
			tuesday.setTimesheetRow(timesheetRow);
			tuesday.setTaskCode(rowVM.taskCode.toString());
			tuesday.setWeekOfYear(timesheet.weekOfYear);
			
			if(classNode.getLevel() == 1) {
				tuesday.setStage(classNode.getId().toString());
			}
			tuesday.setUser(user);
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
			wednesday.setSupplierId(rowVM.wedSupplier);
			wednesday.setCustomerId(rowVM.wedCustomer);
			wednesday.setNotes(rowVM.wedNotes);
			wednesday.setTimesheetRow(timesheetRow);
			wednesday.setTaskCode(rowVM.taskCode.toString());
			wednesday.setWeekOfYear(timesheet.weekOfYear);
			
			if(classNode.getLevel() == 1) {
				wednesday.setStage(classNode.getId().toString());
			}
			wednesday.setUser(user);
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
			thursday.setSupplierId(rowVM.thuSupplier);
			thursday.setCustomerId(rowVM.thuCustomer);
			thursday.setNotes(rowVM.thuNotes);
			thursday.setTimesheetRow(timesheetRow);
			thursday.setTaskCode(rowVM.taskCode.toString());
			thursday.setWeekOfYear(timesheet.weekOfYear);
			
			if(classNode.getLevel() == 1) {
				thursday.setStage(classNode.getId().toString());
			}
			thursday.setUser(user);
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
			friday.setSupplierId(rowVM.friSupplier);
			friday.setCustomerId(rowVM.friCustomer);
			friday.setNotes(rowVM.friNotes);
			friday.setTimesheetRow(timesheetRow);
			friday.setTaskCode(rowVM.taskCode.toString());
			friday.setWeekOfYear(timesheet.weekOfYear);
			
			if(classNode.getLevel() == 1) {
				friday.setStage(classNode.getId().toString());
			}
			friday.setUser(user);
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
			saturday.setSupplierId(rowVM.satSupplier);
			saturday.setCustomerId(rowVM.satCustomer);
			saturday.setNotes(rowVM.satNotes);
			saturday.setTimesheetRow(timesheetRow);
			saturday.setTaskCode(rowVM.taskCode.toString());
			saturday.setWeekOfYear(timesheet.weekOfYear);
			
			if(classNode.getLevel() == 1) {
				saturday.setStage(classNode.getId().toString());
			}
			saturday.setUser(user);
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
			sunday.setSupplierId(rowVM.sunSupplier);
			sunday.setCustomerId(rowVM.sunCustomer);
			sunday.setNotes(rowVM.sunNotes);
			sunday.setTimesheetRow(timesheetRow);
			sunday.setTaskCode(rowVM.taskCode.toString());
			sunday.setWeekOfYear(timesheet.weekOfYear);
			
			if(classNode.getLevel() == 1) {
				sunday.setStage(classNode.getId().toString());
			}
			sunday.setUser(user);
			sunday.save();
			
		}
		
		} else {
			if(timesheet.status.equals("Submitted")) {
				timesheetSavedObj.setTimesheetWith(user.getManager());
			} else {
				timesheetSavedObj.setTimesheetWith(user);
			}
			timesheetSavedObj.setStatus(TimesheetStatus.valueOf(timesheet.status));
			timesheetSavedObj.update();
			
			for(TimesheetRowVM row: timesheet.timesheetRows) {
				Projectclassnode classNode = Projectclassnode.getProjectById(Long.parseLong(row.taskCode.toString()));
				while(classNode.getLevel() != 1) {
					classNode = Projectclassnode.getProjectById(classNode.getParentId());
				}
				if(row.rowId != 0L) {
				TimesheetRow timesheetRow =  TimesheetRow.findById(row.rowId);
				timesheetRow.setProjectCode(row.projectCode.toString());
				timesheetRow.setTaskCode(row.taskCode.toString());
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
				monday.setSupplierId(row.monSupplier);
				monday.setCustomerId(row.monCustomer);
				monday.setNotes(row.monNotes);
				monday.setTaskCode(row.taskCode.toString());
				monday.setWeekOfYear(timesheet.weekOfYear);
				
				if(classNode.getLevel() == 1) {
					monday.setStage(classNode.getId().toString());
				}
				monday.setUser(user);
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
				tuesday.setSupplierId(row.tueSupplier);
				tuesday.setCustomerId(row.tueCustomer);
				tuesday.setNotes(row.tueNotes);
				tuesday.setTaskCode(row.taskCode.toString());
				tuesday.setWeekOfYear(timesheet.weekOfYear);
				
				if(classNode.getLevel() == 1) {
					tuesday.setStage(classNode.getId().toString());
				}
				tuesday.setUser(user);
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
				wednesday.setSupplierId(row.wedSupplier);
				wednesday.setCustomerId(row.wedCustomer);
				wednesday.setNotes(row.wedNotes);
				wednesday.setTaskCode(row.taskCode.toString());
				wednesday.setWeekOfYear(timesheet.weekOfYear);
				
				if(classNode.getLevel() == 1) {
					wednesday.setStage(classNode.getId().toString());
				}
				wednesday.setUser(user);
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
				thursday.setSupplierId(row.thuSupplier);
				thursday.setCustomerId(row.thuCustomer);
				thursday.setNotes(row.thuNotes);
				thursday.setTaskCode(row.taskCode.toString());
				thursday.setWeekOfYear(timesheet.weekOfYear);
				
				if(classNode.getLevel() == 1) {
					thursday.setStage(classNode.getId().toString());
				}
				thursday.setUser(user);
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
				friday.setSupplierId(row.friSupplier);
				friday.setCustomerId(row.friCustomer);
				friday.setNotes(row.friNotes);
				friday.setTaskCode(row.taskCode.toString());
				friday.setWeekOfYear(timesheet.weekOfYear);
				
				if(classNode.getLevel() == 1) {
					friday.setStage(classNode.getId().toString());
				}
				friday.setUser(user);
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
				saturday.setSupplierId(row.satSupplier);
				saturday.setCustomerId(row.satCustomer);
				saturday.setNotes(row.satNotes);
				saturday.setTaskCode(row.taskCode.toString());
				saturday.setWeekOfYear(timesheet.weekOfYear);
				
				if(classNode.getLevel() == 1) {
					saturday.setStage(classNode.getId().toString());
				}
				saturday.setUser(user);
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
				sunday.setSupplierId(row.sunSupplier);
				sunday.setCustomerId(row.sunCustomer);
				sunday.setTaskCode(row.taskCode.toString());
				sunday.setWeekOfYear(timesheet.weekOfYear);
				
				sunday.setNotes(row.sunNotes);
				if(classNode.getLevel() == 1) {
					sunday.setStage(classNode.getId().toString());
				}
				sunday.setUser(user);
				sunday.update();
				
				} else {
					
					TimesheetRow timesheetRow = new TimesheetRow();
					timesheetRow.setProjectCode(row.projectCode.toString());
					timesheetRow.setTaskCode(row.taskCode.toString());
					timesheetRow.setTimesheet(timesheetSavedObj);
					timesheetRow.setOverTime(row.isOverTime);
					
					timesheetRow.save();
					
					cal.set(Calendar.WEEK_OF_YEAR,timesheet.weekOfYear);
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
					monday.setSupplierId(row.monSupplier);
					monday.setCustomerId(row.monCustomer);
					monday.setNotes(row.monNotes);
					monday.setTaskCode(row.taskCode.toString());
					monday.setWeekOfYear(timesheet.weekOfYear);
					
					monday.setTimesheetRow(timesheetRow);
					if(classNode.getLevel() == 1) {
						monday.setStage(classNode.getId().toString());
					}
					monday.setUser(user);
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
					tuesday.setSupplierId(row.tueSupplier);
					tuesday.setCustomerId(row.tueCustomer);
					tuesday.setNotes(row.tueNotes);
					tuesday.setTimesheetRow(timesheetRow);
					tuesday.setTaskCode(row.taskCode.toString());
					tuesday.setWeekOfYear(timesheet.weekOfYear);
					
					if(classNode.getLevel() == 1) {
						tuesday.setStage(classNode.getId().toString());
					}
					tuesday.setUser(user);
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
					wednesday.setSupplierId(row.wedSupplier);
					wednesday.setCustomerId(row.wedCustomer);
					wednesday.setNotes(row.wedNotes);
					wednesday.setTimesheetRow(timesheetRow);
					wednesday.setTaskCode(row.taskCode.toString());
					wednesday.setWeekOfYear(timesheet.weekOfYear);
					
					if(classNode.getLevel() == 1) {
						wednesday.setStage(classNode.getId().toString());
					}
					wednesday.setUser(user);
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
					thursday.setSupplierId(row.thuSupplier);
					thursday.setCustomerId(row.thuCustomer);
					thursday.setNotes(row.thuNotes);
					thursday.setTimesheetRow(timesheetRow);
					thursday.setTaskCode(row.taskCode.toString());
					thursday.setWeekOfYear(timesheet.weekOfYear);
					
					if(classNode.getLevel() == 1) {
						thursday.setStage(classNode.getId().toString());
					}
					thursday.setUser(user);
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
					friday.setSupplierId(row.friSupplier);
					friday.setCustomerId(row.friCustomer);
					friday.setNotes(row.friNotes);
					friday.setTimesheetRow(timesheetRow);
					friday.setTaskCode(row.taskCode.toString());
					friday.setWeekOfYear(timesheet.weekOfYear);
					
					if(classNode.getLevel() == 1) {
						friday.setStage(classNode.getId().toString());
					}
					friday.setUser(user);
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
					saturday.setSupplierId(row.satSupplier);
					saturday.setCustomerId(row.satCustomer);
					saturday.setNotes(row.satNotes);
					saturday.setTimesheetRow(timesheetRow);
					saturday.setTaskCode(row.taskCode.toString());
					saturday.setWeekOfYear(timesheet.weekOfYear);
					
					if(classNode.getLevel() == 1) {
						saturday.setStage(classNode.getId().toString());
					}
					saturday.setUser(user);
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
					sunday.setSupplierId(row.sunSupplier);
					sunday.setCustomerId(row.sunCustomer);
					sunday.setNotes(row.sunNotes);
					sunday.setTimesheetRow(timesheetRow);
					sunday.setTaskCode(row.taskCode.toString());
					sunday.setWeekOfYear(timesheet.weekOfYear);
					
					if(classNode.getLevel() == 1) {
						sunday.setStage(classNode.getId().toString());
					}
					sunday.setUser(user);
					sunday.save();
					
				}
				
			}
			
		}
			if(timesheet.status.equals("Submitted")) {
				//MailSetting smtpSetting = MailSetting.find.where().eq("companyObject", user.companyobject).findUnique();
				MailSetting smtpSetting = MailSetting.find();
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
		
		List<TimesheetRowVM> timesheetRowVMList = new ArrayList<>();
		
		if(timesheetSavedObj != null) {

			TimesheetVM timesheetVM = new TimesheetVM();
			timesheetVM.id = timesheetSavedObj.getId();
			timesheetVM.status = timesheetSavedObj.getStatus().getName();
			timesheetVM.weekOfYear = timesheetSavedObj.getWeekOfYear();
			timesheetVM.year = timesheetSavedObj.getYear();
			for(TimesheetRow timesheetRow : timesheetSavedObj.getTimesheetRows()) {
				List<TimesheetDays> timesheetDaysList = TimesheetDays.getByTimesheetRow(timesheetRow);
				
				TimesheetRowVM timesheetRowVM = new TimesheetRowVM();
				timesheetRowVM.rowId = timesheetRow.getId();
				timesheetRowVM.projectCode =Long.parseLong(timesheetRow.getProjectCode());
				timesheetRowVM.taskCode = Long.parseLong(timesheetRow.getTaskCode());
				timesheetRowVM.isOverTime = timesheetRow.isOverTime();
				
				for(TimesheetDays day : timesheetDaysList) {
					if(day.getDay().equals("monday")) {
						timesheetRowVM.monFrom = day.getTimeFrom();
						timesheetRowVM.monTo = day.getTimeTo();
						timesheetRowVM.mondayId = day.getId();
						timesheetRowVM.monSupplier = day.getSupplierId();
						timesheetRowVM.monCustomer = day.getCustomerId();
						timesheetRowVM.monNotes = day.getNotes();
					}
					if(day.getDay().equals("tuesday")) {
						timesheetRowVM.tueFrom = day.getTimeFrom();
						timesheetRowVM.tueTo = day.getTimeTo();
						timesheetRowVM.tuesdayId = day.getId();
						timesheetRowVM.tueSupplier = day.getSupplierId();
						timesheetRowVM.tueCustomer = day.getCustomerId();
						timesheetRowVM.tueNotes = day.getNotes();
					}
					
					if(day.getDay().equals("wednesday")) {
						timesheetRowVM.wedFrom = day.getTimeFrom();
						timesheetRowVM.wedTo = day.getTimeTo();
						timesheetRowVM.wednesdayId = day.getId();
						timesheetRowVM.wedSupplier = day.getSupplierId();
						timesheetRowVM.wedCustomer = day.getCustomerId();
						timesheetRowVM.wedNotes = day.getNotes();
					}
					
					if(day.getDay().equals("thursday")) {
						timesheetRowVM.thuFrom = day.getTimeFrom();
						timesheetRowVM.thuTo = day.getTimeTo();
						timesheetRowVM.thursdayId = day.getId();
						timesheetRowVM.thuSupplier = day.getSupplierId();
						timesheetRowVM.thuCustomer = day.getCustomerId();
						timesheetRowVM.thuNotes = day.getNotes();
					}
					if(day.getDay().equals("friday")) {
						timesheetRowVM.friFrom = day.getTimeFrom();
						timesheetRowVM.friTo = day.getTimeTo();
						timesheetRowVM.fridayId = day.getId();
						timesheetRowVM.friSupplier = day.getSupplierId();
						timesheetRowVM.friCustomer = day.getCustomerId();
						timesheetRowVM.friNotes = day.getNotes();
					}
					if(day.getDay().equals("saturday")) {
						timesheetRowVM.satFrom = day.getTimeFrom();
						timesheetRowVM.satTo = day.getTimeTo();
						timesheetRowVM.saturdayId = day.getId();
						timesheetRowVM.satSupplier = day.getSupplierId();
						timesheetRowVM.satCustomer = day.getCustomerId();
						timesheetRowVM.satNotes = day.getNotes();
					}
					if(day.getDay().equals("sunday")) {
						timesheetRowVM.sunFrom = day.getTimeFrom();
						timesheetRowVM.sunTo = day.getTimeTo();
						timesheetRowVM.sundayId = day.getId();
						timesheetRowVM.sunSupplier = day.getSupplierId();
						timesheetRowVM.sunCustomer = day.getCustomerId();
						timesheetRowVM.sunNotes = day.getNotes();
					}
					timesheetRowVM.totalmins = day.getWorkMinutes();
				}
				timesheetRowVMList.add(timesheetRowVM);
			}
			timesheetVM.timesheetRows = timesheetRowVMList;
			return Json.toJson(timesheetVM);
		} else {
			Timesheet timesheetobj = Timesheet.getByUserWeekAndYear(user, timesheet.weekOfYear, timesheet.year);
			
			TimesheetVM timesheetVM = new TimesheetVM();
			timesheetVM.id = timesheetobj.getId();
			timesheetVM.status = timesheetobj.getStatus().getName();
			timesheetVM.weekOfYear = timesheetobj.getWeekOfYear();
			timesheetVM.year = timesheetobj.getYear();
			for(TimesheetRow timesheetRow : timesheetobj.getTimesheetRows()) {
				List<TimesheetDays> timesheetDaysList = TimesheetDays.getByTimesheetRow(timesheetRow);
				
				TimesheetRowVM timesheetRowVM = new TimesheetRowVM();
				timesheetRowVM.rowId = timesheetRow.getId();
				timesheetRowVM.projectCode =Long.parseLong(timesheetRow.getProjectCode());
				timesheetRowVM.taskCode = Long.parseLong(timesheetRow.getTaskCode());
				timesheetRowVM.isOverTime = timesheetRow.isOverTime();
				
				for(TimesheetDays day : timesheetDaysList) {
					if(day.getDay().equals("monday")) {
						timesheetRowVM.monFrom = day.getTimeFrom();
						timesheetRowVM.monTo = day.getTimeTo();
						timesheetRowVM.mondayId = day.getId();
						timesheetRowVM.monSupplier = day.getSupplierId();
						timesheetRowVM.monCustomer = day.getCustomerId();
						timesheetRowVM.monNotes = day.getNotes();
						
					}
					if(day.getDay().equals("tuesday")) {
						timesheetRowVM.tueFrom = day.getTimeFrom();
						timesheetRowVM.tueTo = day.getTimeTo();
						timesheetRowVM.tuesdayId = day.getId();
						timesheetRowVM.tueSupplier = day.getSupplierId();
						timesheetRowVM.tueCustomer = day.getCustomerId();
						timesheetRowVM.tueNotes = day.getNotes();
						
					}
					
					if(day.getDay().equals("wednesday")) {
						timesheetRowVM.wedFrom = day.getTimeFrom();
						timesheetRowVM.wedTo = day.getTimeTo();
						timesheetRowVM.wednesdayId = day.getId();
						timesheetRowVM.wedSupplier = day.getSupplierId();
						timesheetRowVM.wedCustomer = day.getCustomerId();
						timesheetRowVM.wedNotes = day.getNotes();
					}
					
					if(day.getDay().equals("thursday")) {
						timesheetRowVM.thuFrom = day.getTimeFrom();
						timesheetRowVM.thuTo = day.getTimeTo();
						timesheetRowVM.thursdayId = day.getId();
						timesheetRowVM.thuSupplier = day.getSupplierId();
						timesheetRowVM.thuCustomer = day.getCustomerId();
						timesheetRowVM.thuNotes = day.getNotes();
					}
					if(day.getDay().equals("friday")) {
						timesheetRowVM.friFrom = day.getTimeFrom();
						timesheetRowVM.friTo = day.getTimeTo();
						timesheetRowVM.fridayId = day.getId();
						timesheetRowVM.friSupplier = day.getSupplierId();
						timesheetRowVM.friCustomer = day.getCustomerId();
						timesheetRowVM.friNotes = day.getNotes();
					}
					if(day.getDay().equals("saturday")) {
						timesheetRowVM.satFrom = day.getTimeFrom();
						timesheetRowVM.satTo = day.getTimeTo();
						timesheetRowVM.saturdayId = day.getId();
						timesheetRowVM.satSupplier = day.getSupplierId();
						timesheetRowVM.satCustomer = day.getCustomerId();
						timesheetRowVM.satNotes = day.getNotes();
					}
					if(day.getDay().equals("sunday")) {
						timesheetRowVM.sunFrom = day.getTimeFrom();
						timesheetRowVM.sunTo = day.getTimeTo();
						timesheetRowVM.sundayId = day.getId();
						timesheetRowVM.sunSupplier = day.getSupplierId();
						timesheetRowVM.sunCustomer = day.getCustomerId();
						timesheetRowVM.sunNotes = day.getNotes();
					}
					timesheetRowVM.totalmins = day.getWorkMinutes();
				}
				timesheetRowVMList.add(timesheetRowVM);
			}
			timesheetVM.timesheetRows = timesheetRowVMList;
			
			return Json.toJson(timesheetVM);
		}
		
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
	}
	
	@RequestMapping(value = "/timesheetActualSearchIndex", method= RequestMethod.GET)
	public String timesheetActualSearchIndex(ModelMap model, @CookieValue("username") String username){
		User user = User.findByEmail(username);
		Form<TimesheetActual> timesheetForm = form(TimesheetActual.class);
		
		model.addAttribute("context", TimesheetActualSearchContext.getInstance().build());
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);
		model.addAttribute("timesheetForm", timesheetForm);
		
		return "searchActualTimesheet";
	}
	
	@RequestMapping(value = "/TimesheetSearch", method= RequestMethod.GET)
	public @ResponseBody String search(@CookieValue("username") String username,HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("email", username);
		return Json.toJson(TimesheetSearchContext.getInstance().build().doSearch(form)).toString();
    }
	
	@RequestMapping(value = "/timesheetActualSearch", method= RequestMethod.GET)
	public @ResponseBody String TimesheetActualSearch(@CookieValue("username") String username,HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("email", username);
		return Json.toJson(TimesheetActualSearchContext.getInstance().build().doSearch(form)).toString();
    }
	
	@RequestMapping(value = "/timesheetEdit/{id}", method= RequestMethod.GET)
	public String editTimesheet(ModelMap model, @CookieValue("username") String username, @PathVariable("id") String id){
		User user = User.findByEmail(username);
		Timesheet timesheet = Timesheet.findById(Long.parseLong(id));
		TimesheetVM timesheetVM = new TimesheetVM();
		timesheetVM.id = timesheet.getId();
		timesheetVM.status = timesheet.getStatus().getName();
		timesheetVM.weekOfYear = timesheet.getWeekOfYear();
		timesheetVM.year = timesheet.getYear();
		timesheetVM.userId = user.getId();
		if(timesheet.getTimesheetRows().size()>0)
		{
		List<TimesheetDays> list = timesheet.getTimesheetRows().get(0).getTimesheetDays();
			for(TimesheetDays day : list) {
				if(day.getDay().equals("sunday")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(day.getTimesheetDate());
					timesheetVM.date = cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.YEAR);
				}
			}
		}
		
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);
		model.addAttribute("asJson", Json.toJson(timesheetVM));
		
		return "timesheetIndex";
	}
	
	@RequestMapping(value = "/timesheetActualEdit/{id}", method= RequestMethod.GET)
	public String timesheetActualEdit(ModelMap model, @CookieValue("username") String username, @PathVariable("id") String id){
		User user = User.findByEmail(username);
		TimesheetActual timesheet = TimesheetActual.findById(Long.parseLong(id));
		
		
		TimesheetVM timesheetVM = new TimesheetVM();
		timesheetVM.id = timesheet.getId();
		timesheetVM.status = timesheet.getStatus().getName();
		timesheetVM.weekOfYear = timesheet.getWeekOfYear();
		timesheetVM.year = timesheet.getYear();
		timesheetVM.userId = user.getId();
		
		if(timesheet.getTimesheetRowsActual().size()>0)
		{
			List<TimesheetDaysActual> list = timesheet.getTimesheetRowsActual().get(0).getTimesheetDaysActual();
			
				for(TimesheetDaysActual day : list) {
					if(day.getDay().equals("sunday")) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(day.getTimesheetDate());
						timesheetVM.date = cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.YEAR);
					}
				}
		}
		
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);
		model.addAttribute("asJson", Json.toJson(timesheetVM));
		
		return "timesheetNew";
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