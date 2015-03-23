package com.mnt.time.controller;

import static com.google.common.collect.Lists.transform;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import models.Project;
import models.Task;
import models.User;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import play.data.DynamicForm;
import play.libs.Json;
import utils.ExceptionHandler;

import com.custom.helpers.TaskSave;
import com.custom.helpers.TaskSearchContext;
import com.google.common.base.Function;
import com.mnt.core.ui.component.AutoComplete;

import dto.fixtures.MenuBarFixture;

@Controller
public class Tasks{
	@RequestMapping(value="/taskSearch",method=RequestMethod.GET)
	public @ResponseBody String search(ModelMap model, @CookieValue("username") String username,HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("email", username);
		return Json.toJson(TaskSearchContext.getInstance().build().doSearch(form)).toString();
    }
	
	@RequestMapping(value="/taskEdit",method=RequestMethod.POST)
	public @ResponseBody String edit(HttpServletRequest request) {
		try {
			TaskSave saveUtils = new TaskSave();
			saveUtils.doSave(true,request);
		} catch (Exception e) {
			ExceptionHandler.onError(request.getRequestURI(),e);
		}
		return "Tasks Edited Successfully";
    }
	
	@RequestMapping(value="/taskExcelReport",method=RequestMethod.GET)	
	public String excelReport(ModelMap model, @CookieValue("username") String username, HttpServletRequest request) throws IOException {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("email",username);
		HSSFWorkbook hssfWorkbook =  TaskSearchContext.getInstance().build().doExcel(form);
		File f = new File("excelReport.xls");
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//hssfWorkbook.write(fileOutputStream);
		return "application/vnd.ms-excel";
    }
	
	@RequestMapping(value="/taskIndex",method=RequestMethod.GET)		
	public String index(ModelMap model, @CookieValue("username") String username) {
		User user = User.findByEmail(username);
		model.addAttribute("user",user);
		model.addAttribute("_menuContext",MenuBarFixture.build(username));
		model.addAttribute("context",TaskSearchContext.getInstance().build());
		return "taskIndex";
	}
	@RequestMapping(value="/taskDelete",method=RequestMethod.GET)
	public String delete() {
		return "";
    }
	
	@RequestMapping(value="/taskCreate",method=RequestMethod.POST)	
	public @ResponseBody String create(HttpServletRequest request,@CookieValue("username")String username) {
		try {
			Map<String, Object> extra = new HashMap<String, Object>();
			TaskSave saveUtils = new TaskSave(extra);
			saveUtils.doSave(false,request);
		} catch (Exception e) {
			//ExceptionHandler.onError(request.getRequestURI(),e);
		}
		return "Tasks Created Successfully";
    }
	
	@RequestMapping(value="/taskShowEdit",method=RequestMethod.GET)
	public String showEdit(ModelMap model,HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		Long id = null;
		try{
			id = Long.valueOf(form.get("query"));
			model.addAttribute("_searchContext", new TaskSearchContext(Task.findById(id)).build());
			return "editWizard";
		}catch(NumberFormatException nfe){
			ExceptionHandler.onError(request.getRequestURI(),nfe);
		}
		return "Not able to show Results, Check Logs";
		
	}
	
	@RequestMapping(value="/projectByName",method=RequestMethod.GET)
	public @ResponseBody String findProjectByName(@CookieValue("username")String username,HttpServletRequest request){
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		String query = form.get("query");
		System.out.println(query);
		ObjectNode result = Json.newObject();
		List<AutoComplete> results = transform(Projects.findProjectByName(query,username), toAutoCompleteFormatForProject());
		result.put("results", Json.toJson(results));
		return Json.toJson(result).toString();
	}
	
	private  Function<Project,AutoComplete> toAutoCompleteFormatForProject() {
		return new Function<Project, AutoComplete>() {
			@Override
			public AutoComplete apply(Project project) {
					return new AutoComplete(project.projectCode,project.projectName,project.clientName.clientName,project.id);
			}
		};
	}

}
