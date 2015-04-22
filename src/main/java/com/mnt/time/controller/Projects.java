package com.mnt.time.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Project;
import models.Task;
import models.User;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.JsonNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import play.data.DynamicForm;
import play.libs.Json;
import utils.ExceptionHandler;
import viewmodel.ProjectVM;
import viewmodel.ProjectWidgetVM;
import viewmodel.TaskVM;
import viewmodel.WidgetVM;

import com.avaje.ebean.Expr;
import com.avaje.ebean.SqlRow;
import com.custom.helpers.ProjectSave;
import com.custom.helpers.ProjectSearchContext;
import com.google.common.collect.Lists;
import com.mnt.createProject.model.Projectinstance;

import dto.fixtures.MenuBarFixture;

@Controller
public class Projects  {
	
	@RequestMapping(value="/projectSearch",method=RequestMethod.GET)
	public @ResponseBody String search(ModelMap model,@CookieValue("username") String username,HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("email", username);
		return Json.toJson(ProjectSearchContext.getInstance().build().doSearch(form)).toString();
    }
	
	@RequestMapping(value="/projectEdit",method=RequestMethod.POST)	
	public @ResponseBody String edit(HttpServletRequest request) {
		try {
			ProjectSave saveUtils = new ProjectSave();
			saveUtils.doSave(true,request);
		} catch (Exception e) {
			ExceptionHandler.onError(request.getRequestURI(),e);
		}
		return "Project Edited Successfully";
    }
	
	@RequestMapping(value="/projectIndex",method=RequestMethod.GET)	
	public String index(ModelMap model ,@CookieValue("username") String username) {
		User user = User.findByEmail(username);
		model.addAttribute("context", ProjectSearchContext.getInstance().build());
	   	model.addAttribute("_menuContext", MenuBarFixture.build(username));
    	model.addAttribute("user", user);
    	return "projectIndex";
	}
	
	@RequestMapping(value="/projectDelete",method=RequestMethod.GET)		
	public String delete() {
		return "";
    }
	
	@RequestMapping(value="/getValues", method = RequestMethod.GET)
	public @ResponseBody JsonNode getValues(ModelMap model,@RequestParam("id") String id) {
		List<SqlRow> sqlRows = Project.getProjectsOfUser(Long.parseLong(id));
		List<ProjectWidgetVM> vmList = new ArrayList<>();
		for(SqlRow row: sqlRows) {
			Project project = Project.findById(row.getLong("project_id"));
			ProjectWidgetVM vm = new ProjectWidgetVM();
			vm.name = project.getProjectName();
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			vm.startDate = df.format(project.getStartDate());
			vm.endDate = df.format(project.getEndDate());
			vmList.add(vm);
		}	
		
		return Json.toJson(vmList);
	}
	
	@RequestMapping(value="/getTaskValues", method = RequestMethod.GET)
	public @ResponseBody JsonNode getTaskValues(ModelMap model,@RequestParam("id") String id) {
		List<SqlRow> sqlRows = Project.getProjectsOfUser(Long.parseLong(id));
		List<ProjectWidgetVM> vmList = new ArrayList<>();
		for(SqlRow row: sqlRows) {
			Project project = Project.findById(row.getLong("project_id"));
			List<SqlRow> taskRows = Project.getTasksOfProject(project.id);
			
			for(SqlRow taskRow : taskRows) {
				Task task = Task.findById(taskRow.getLong("task_id"));
				ProjectWidgetVM vm = new ProjectWidgetVM();
				vm.name = project.getProjectName();
				vm.taskName = task.getTaskName();
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				vm.startDate = df.format(task.getStartDate());
				vm.endDate = df.format(task.getEndDate());
				vmList.add(vm);
			}
			
		}	
		
		return Json.toJson(vmList);
	}
	
	@RequestMapping(value="/projectExcelReport",method=RequestMethod.GET)		
	public String excelReport(ModelMap model, @CookieValue("username") String username, HttpServletRequest request,HttpServletResponse response) throws IOException {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=excelReport.xls");
		form.data().put("email", username);
		HSSFWorkbook hssfWorkbook =  ProjectSearchContext.getInstance().build().doExcel(form);
		File f = new File("excelReport.xls");
		FileOutputStream fileOutputStream = new FileOutputStream(f);
		hssfWorkbook.write(fileOutputStream);
		//find out this too
		//return ok(f).as("application/vnd.ms-excel");
		return "";
    }
	
	@RequestMapping(value="/projectCreate",method=RequestMethod.POST)			
	public @ResponseBody String create(HttpServletRequest request,ModelMap model, @CookieValue("username") String username) {
		try {
			Map<String, Object> extra = new HashMap<String, Object>();
			extra.put("companyObj", User.findByEmail(username).companyobject);
			ProjectSave saveUtils = new ProjectSave(extra);
			saveUtils.doSave(false,request);
		} catch (Exception e) {
			ExceptionHandler.onError(request.getRequestURI(),e);
		}
		return "Project Created Successfully";
    }

	@RequestMapping(value="/projectShowEdit",method=RequestMethod.GET)			
	public String showEdit(ModelMap model, HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		Long id = null;
		try{
			id = Long.valueOf(form.get("query"));
			model.addAttribute("_searchContext",new ProjectSearchContext(Projectinstance.getById(id)));
			return "editWizard";
		}catch(NumberFormatException nfe){
			ExceptionHandler.onError(request.getRequestURI(),nfe);
		}
		return "Not able to show Results, Check Logs";
		
	}
	
	@RequestMapping(value="/projectCodeAvailability",method=RequestMethod.POST)			
	public @ResponseBody String checkProjectCodeAvailability(HttpServletRequest request){
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		String q = form.get("q");
		Projectinstance p = Projectinstance.find.where().eq("projectid", q).findUnique();
		if(p==null){
			return Json.toJson(true).asText();
		}
		return Json.toJson(false).asText();
	}
	
	public static List<Project> findProjectByName(String query, String username) {
		User user = User.findByEmail(username);
		List<Project> projects =  Project.find.where().and(Expr.eq("companyObj.companyCode", user.companyobject.getCompanyCode()),Expr.or(Expr.ilike("projectName", query+"%"),Expr.ilike("projectCode", query+"%")))
	       		.findList();
		return projects;
	}
}
