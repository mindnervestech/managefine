package com.mnt.time.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import models.User;
import play.data.DynamicForm;
import play.libs.Json;


//import play.mvc.Controller;
import com.custom.ui.search.helper.TeamRateReportSearchUI;

import dto.fixtures.MenuBarFixture;

/*@Security.Authenticated(Secured.class)
@BasicAuth*/

@Controller
public class Reports {

	 @RequestMapping(value="/reportIndex" , method = RequestMethod.GET)
	public  String teamRateIndex(ModelMap model,@CookieValue("username") String username) {
		User user = User.findByEmail(username);
		model.addAttribute("context",TeamRateReportSearchUI.getInstance().build());
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
    	model.addAttribute("user",user);
    	
		return "reportIndex";
    }
	 
	 
	 @RequestMapping(value="/reportSearch" , method = RequestMethod.GET)
	public @ResponseBody String teamRateSearch(@CookieValue("username") String username,HttpServletRequest request) {
		User user = User.findByEmail(username);
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("userEmail", user.email);
		return Json.toJson(TeamRateReportSearchUI.getInstance().build().doSearch(form)).toString();
    }
	
}
