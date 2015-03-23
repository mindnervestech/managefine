package com.mnt.time.controller;
import static play.data.Form.form;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Project;
import models.Task;
import models.Timesheet;
import models.TimesheetRow;
import models.User;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import utils.EmailExceptionHandler;
import utils.SelectUIMap;

import com.avaje.ebean.Expr;
import com.custom.domain.TimesheetStatus;
import com.custom.exception.NoTimeSheetFoudException;
import com.custom.helpers.TimesheetSearchContext;
import com.custom.workflow.timesheet.TimesheetWorkflowUtils;

import dto.fixtures.MenuBarFixture;
/*
@Security.Authenticated(Secured.class)
@BasicAuth*/

@Controller
public class Timesheets{
	
	private static HashMap<String,Integer> timesheetRowsMap = null;
	
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
				timesheetRowsMap.put(row.projectCode, timesheetRowsMap.get(row.projectCode)+row.totalHrs);
			}else{
				timesheetRowsMap.put(row.projectCode, row.totalHrs);
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