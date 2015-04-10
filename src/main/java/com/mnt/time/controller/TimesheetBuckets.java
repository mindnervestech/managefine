package com.mnt.time.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Project;
import models.Task;
import models.Timesheet;
import models.TimesheetActual;
import models.TimesheetDaysActual;
import models.TimesheetRowActual;
import models.User;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import play.data.DynamicForm;
import play.libs.Json;
import utils.EmailExceptionHandler;
import utils.ExceptionHandler;
import viewmodel.TimesheetDaysVM;
import viewmodel.TimesheetRowVM;
import viewmodel.TimesheetVM;

import com.custom.domain.TimesheetStatus;
import com.custom.helpers.TimesheetBucketSearchContext;
import com.custom.workflow.timesheet.TimesheetWorkflowUtils;

import dto.fixtures.MenuBarFixture;

@Controller
public class TimesheetBuckets  {

	@RequestMapping(value="/timesheet/bucketIndex",method=RequestMethod.GET)
	public String index(@CookieValue ("username")String username,ModelMap model){
		User user = User.findByEmail(username);
	   	model.addAttribute("_menuContext", MenuBarFixture.build(username));
    	model.addAttribute("user", user);
    	model.addAttribute("context", TimesheetBucketSearchContext.getInstance().build());
    	return "timesheetsBucket";
	}
	
	@RequestMapping(value="/timesheet/bucketSearch",method=RequestMethod.GET)
	public @ResponseBody String search(@CookieValue("username")String username,HttpServletRequest request) {
		User user = User.findByEmail(username);
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("userEmail", user.getEmail());
		return Json.toJson(TimesheetBucketSearchContext.getInstance().build().doSearch(form)).toString();
    }
	
	@RequestMapping(value="/timesheetBucketExcelReport",method=RequestMethod.GET)	
	public String excelReport(@CookieValue("username")String username,HttpServletRequest request,HttpServletResponse response) throws IOException {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		response.setContentType("application/vnd.ms-excel");
		form.data().put("email", username);
		HSSFWorkbook hssfWorkbook =  TimesheetBucketSearchContext.getInstance().build().doExcel(form);
		File f = new File("excelReport.xls");
		FileOutputStream fileOutputStream = new FileOutputStream(f);
		hssfWorkbook.write(fileOutputStream);
		response.setHeader("Content-Disposition", "attachment; filename=excelReport.xls");
		//return ok(f).as("application/vnd.ms-excel");
        return"";
	}
	
	
	@RequestMapping(value="/timesheet/approveTimesheets",method=RequestMethod.GET)		
	public @ResponseBody String approveTimesheets(HttpServletRequest request,@CookieValue("username")String username){
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		String query = form.data().get("query");
		if(query == null){
			query = form.data().get("timesheetID_hidden");
		}
		for(String ids : query.split(",")){
			//Timesheet timesheet = Timesheet.find.byId(Long.parseLong(ids));
			TimesheetActual timesheet = TimesheetActual.find.byId(Long.parseLong(ids));
			timesheet.setStatus(TimesheetStatus.Approved);
			timesheet.update();
			/*try{
				TimesheetWorkflowUtils.setVariableToTask(timesheet.getProcessInstanceId(), true, timesheet.getTid());
			}
			catch (Exception e) {
				ExceptionHandler.onError(request.getRequestURI(),e);
			}*/
		}
		Integer count = Application.count(username);
		String notification = count.toString(); 
		String message = "TimeSheet has been Approved";
		Map<String,String> jsonMap = new HashMap<String,String>();
		jsonMap.put("count", notification);
		jsonMap.put("message", message);
		return Json.toJson(jsonMap).toString();
	}
	
	@RequestMapping(value="/timesheet/approveTimesheetsOk",method=RequestMethod.GET)		
	public @ResponseBody String approveTimesheetsOk(String id,@CookieValue("username")String username){
		//Timesheet timesheet = Timesheet.find.byId(Long.parseLong(id));
		TimesheetActual timesheet = TimesheetActual.find.byId(Long.parseLong(id));
		timesheet.setStatus(TimesheetStatus.Approved);
		timesheet.update();
		
		/*try{
			TimesheetWorkflowUtils.setVariableToTask(timesheet.processInstanceId, true, timesheet.tid);
		}
		catch (Exception e) {
			EmailExceptionHandler.handleException(e);
		}*/
		Integer count = Application.count(username);
		String notification = count.toString(); 
		String message = "TimeSheet has been Approved";
		Map<String,String> jsonMap = new HashMap<String,String>();
		jsonMap.put("count", notification);
		jsonMap.put("message", message);
		return Json.toJson(jsonMap).toString();
	}
	
	@RequestMapping(value="/timesheet/rejectTimesheetsOk",method=RequestMethod.GET)		
	public @ResponseBody String rejectTimesheetsOk(String id,@CookieValue("username")String username){
		
		//Timesheet timesheet = Timesheet.find.byId(Long.parseLong(id));
		TimesheetActual timesheet = TimesheetActual.find.byId(Long.parseLong(id));
		timesheet.setStatus(TimesheetStatus.Rejected);
		timesheet.update();
		
		/*try{
			TimesheetWorkflowUtils.setVariableToTask(timesheet.processInstanceId, false, timesheet.tid);
		}
		catch (Exception e) {
			EmailExceptionHandler.handleException(e);
		}*/
			Integer count = Application.count(username);
			String notification = count.toString(); 
			String message = "TimeSheet has been Rejected";
			Map<String,String> jsonMap = new HashMap<String,String>();
			jsonMap.put("count", notification);
			jsonMap.put("message", message);
			return Json.toJson(jsonMap).toString();
	}

	@RequestMapping(value="/timesheet/rejectTimesheets",method=RequestMethod.GET)		
	public @ResponseBody String rejectTimesheets(@CookieValue("username")String username,HttpServletRequest request){
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		String query = form.data().get("query");
		if(query == null){
			query = form.data().get("timesheetID_hidden");
		}
		for(String ids : query.split(",")){
			//Timesheet timesheet = Timesheet.find.byId(Long.parseLong(ids));
			TimesheetActual timesheet = TimesheetActual.find.byId(Long.parseLong(ids));
			timesheet.setStatus(TimesheetStatus.Rejected);
			timesheet.update();
			/*try{
				TimesheetWorkflowUtils.setVariableToTask(timesheet.processInstanceId, false, timesheet.tid);
			}
			catch (Exception e) {
				ExceptionHandler.onError(request.getRequestURI(),e);
			}*/
		}
		
			Integer count = Application.count(username);
			String notification = count.toString(); 
			String message = "TimeSheet has been Rejected";
			Map<String,String> jsonMap = new HashMap<String,String>();
			jsonMap.put("count", notification);
			jsonMap.put("message", message);
			return Json.toJson(jsonMap).toString();
	}
	
	@RequestMapping(value="/timesheet/displayTimesheet",method=RequestMethod.GET)			
	public  String displaySelectedTimesheet(ModelMap model,String query,@CookieValue("username")String username){
		User user = User.findByEmail(username);
		//Timesheet timesheet = Timesheet.find.byId(Long.parseLong(query));
		TimesheetActual timesheet = TimesheetActual.find.byId(Long.parseLong(query));
		TimesheetVM timesheetVM = new TimesheetVM();
		
		if(timesheet != null) {
			timesheetVM.id = timesheet.getId();
			timesheetVM.weekOfYear = timesheet.weekOfYear;
			timesheetVM.year = timesheet.year;
			timesheetVM.status = timesheet.getStatus().getName();
			timesheetVM.firstName = timesheet.getUser().getFirstName();
			timesheetVM.lastName = timesheet.getUser().getLastName();
			List<TimesheetRowActual> timesheetRowList = TimesheetRowActual.getByTimesheet(timesheet);
			List<TimesheetRowVM> timesheetRowVMList = new ArrayList<>();
			for(TimesheetRowActual row : timesheetRowList) {
				TimesheetRowVM rowVM = new TimesheetRowVM();
				rowVM.projectName = Project.findByProjectCode(row.getProjectCode()).getProjectName();
				rowVM.taskName = Task.findByTaskCode(row.getTaskCode()).getTaskName();
				List<TimesheetDaysActual> timesheetDaysList = TimesheetDaysActual.getByTimesheetRow(row);
				List<TimesheetDaysVM> daysList = new ArrayList<>();
				int totalmins = 0;
				for(TimesheetDaysActual day: timesheetDaysList) {
					TimesheetDaysVM daysVM = new TimesheetDaysVM();
					daysVM.timeFrom = day.getTimeFrom();
					daysVM.timeTo = day.getTimeTo();
					if(day.getWorkMinutes() != null) {
						totalmins = totalmins + day.getWorkMinutes();
					}
					daysList.add(daysVM);
				}
				rowVM.totalHrs = totalmins/60;
				rowVM.timesheetRowDays = daysList;
				timesheetRowVMList.add(rowVM);
			}
			
			timesheetVM.timesheetRowsList = timesheetRowVMList;
			
		}	
		
		model.addAttribute("timesheetVM", timesheetVM);
		model.addAttribute("user", user);
		return "viewTimesheet";
	}
	
}
