package com.mnt.time.controller;

import java.util.ArrayList;
import java.util.List;





import javax.servlet.http.HttpServletRequest;

import models.Project;
import models.User;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import play.data.DynamicForm;
import play.libs.Json;
import utils.UserDetails;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import com.custom.ui.search.helper.ProjectReportSearchUI;

import dto.fixtures.MenuBarFixture;

@Controller
public class ProjectReports {
	@RequestMapping(value="/projReportIndex" ,method=RequestMethod.GET)
	public String projectReportIndex(ModelMap model,@CookieValue("username") String username) {
		User user = User.findByEmail(username);
	 	model.addAttribute("_menuContext", MenuBarFixture.build(username));
    	model.addAttribute("user", user);
    	model.addAttribute("context", ProjectReportSearchUI.getInstance().build());
		return "reportIndex";
    }
	@RequestMapping(value="/projReportSearch" ,method=RequestMethod.GET)
	public @ResponseBody String projectReportSearch(@CookieValue("username") String username,HttpServletRequest request) {
		User user = User.findByEmail(username);
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("userEmail", user.email);
		return Json.toJson(ProjectReportSearchUI.getInstance().build().doSearch(form)).toString();
    }
	@RequestMapping(value="/projViewDetails" ,method=RequestMethod.GET)
	public String viewProjectDetails(ModelMap model, @CookieValue("username") String username,HttpServletRequest request){
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		String id = form.get("query");
		Project project = Project.find.byId(Long.parseLong(id));
		String query="";
		query = "Select u.first_name firstname , sum(ts_r.total_hrs) total_hrs, sum(ts_r.total_hrs * u.hourlyrate) total_billed from timesheet ts , " +
				"timesheet_row ts_r , user u " +
				"where "+
				"ts.id = ts_r.timesheet_id and "+ 
				"ts_r.project_code = '"+project.projectCode +"' and "+
				"u.id = ts.user_id "+
				"group by ts.user_id;";
		List<SqlRow> resultList = Ebean.createSqlQuery(query).findList();
		
		String empName="";
		Integer hrsSpent=0;
		Double moneyBilled=0.0;
		
		List<UserDetails> detailsList = new ArrayList<UserDetails>();
		for(int j=0;j<resultList.size();j++){
			empName = resultList.get(j).getString("firstname");
			if(resultList.get(j).getString("total_hrs") != null){
				hrsSpent = Integer.valueOf(resultList.get(j).getString("total_hrs"));
			}else{
				hrsSpent = 0;
			}
			
			if(resultList.get(j).getString("total_billed") != null){
				moneyBilled = Double.valueOf(resultList.get(j).getString("total_billed")).doubleValue();
			}
			else{
				moneyBilled = 0.0;
			}
			UserDetails userDetail = new UserDetails(empName, hrsSpent, moneyBilled);
			detailsList.add(userDetail);
		}
		model.addAttribute("userDetails", detailsList);
		return "viewUserDetails";
	}
	@RequestMapping(value="/viewGraph" ,method=RequestMethod.GET)
	public String viewGraph(ModelMap model, @CookieValue("username") String username,HttpServletRequest request){
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		String id = form.get("query");
		model.addAttribute("proID", id);	
		return "viewUsageByHrs";
	}	
	
	@RequestMapping(value="/projViewGraphHrs" ,method=RequestMethod.GET)
	public @ResponseBody String viewUsageByHrs(String id){
		Project project = Project.find.byId(Long.parseLong(id));
		System.out.println(id);
		String query = "";
		query = "Select u.first_name firstname , sum(ts_r.total_hrs) total_hrs, sum(ts_r.total_hrs * u.hourlyrate) total_billed from timesheet ts , " +
				"timesheet_row ts_r , user u " +
				"where "+
				"ts.id = ts_r.timesheet_id and "+ 
				"ts_r.project_code = '"+project.projectCode.toString()+"' and "+
				"u.id = ts.user_id "+
				"group by ts.user_id;";
		List<SqlRow> resultList = Ebean.createSqlQuery(query).findList();
		
		String empName="";
		String hrsSpent="";
		String total_billed="";
		
		JSONArray mainJsonArray = new JSONArray();
		JSONObject hrsJSON = new JSONObject();
		JSONArray hrsJSONArray = new JSONArray();
		JSONObject billJSON = new JSONObject();
		JSONArray billJSONArray = new JSONArray();
		
		for(int j=0;j<resultList.size();j++){
			empName = resultList.get(j).getString("firstname");
			if(resultList.get(j).getString("total_hrs") != null){
				hrsSpent = resultList.get(j).getString("total_hrs");
			}else{
				hrsSpent = "0";
			}
			
			if(resultList.get(j).getString("total_billed") != null){
				total_billed = resultList.get(j).getString("total_billed");
			}else{
				total_billed = "0";
			}
			JSONObject hrsTempJSON = new JSONObject();
			JSONArray hrsTempArray = new JSONArray();
			
			hrsTempArray.put(hrsSpent);
			hrsTempJSON.put("label", empName);
			hrsTempJSON.put("values", hrsTempArray);
			hrsJSONArray.put(hrsTempJSON);
			
			JSONObject billTempJSON = new JSONObject();
			JSONArray billempArray = new JSONArray();
			
			billempArray.put(total_billed);
			billTempJSON.put("label", empName);
			billTempJSON.put("values", billempArray);
			billJSONArray.put(billTempJSON);
			
		}
		hrsJSON.put("values", hrsJSONArray);
		billJSON.put("values", billJSONArray);
		mainJsonArray.put(hrsJSON);
		mainJsonArray.put(billJSON);
		System.out.println(mainJsonArray);
		return mainJsonArray.toString();
	}
}
