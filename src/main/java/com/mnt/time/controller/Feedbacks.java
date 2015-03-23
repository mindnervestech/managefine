package com.mnt.time.controller;

import static play.data.Form.form;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import models.Feedback;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
//import play.mvc.Controller;
import utils.ExceptionHandler;

import com.custom.domain.Rating;
import com.custom.helpers.CustomFeedbackSearchContext;
import com.custom.helpers.FeedbackSearchContext;

import dto.fixtures.MenuBarFixture;

/*Security.Authenticated(Secured.class)
@BasicAuth*/

@Controller
public class Feedbacks {
	 @RequestMapping(value="/feedback/create" , method = RequestMethod.GET)
	public String customIndex(ModelMap model,@CookieValue("username") String username) {
		User user = User.findByEmail(username);
		model.addAttribute("context",CustomFeedbackSearchContext.getInstance().build());
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
    	model.addAttribute("user",user );
    	
    	
		//session().get("email");
		//request().username();
		return "feedbackIndex";
    }
	 @RequestMapping(value="/feedback/view" , method = RequestMethod.GET)
	public static String index(ModelMap model,@CookieValue("username") String username) {
		User user = User.findByEmail(username);
		model.addAttribute("context",FeedbackSearchContext.getInstance().build());
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
    	model.addAttribute("user", user);
    	
    	
		//session().get("email");
		//request().username();
		return "feedbackIndex";
    }
	
	//FeedbackProxySearchModel
	 
	 @RequestMapping(value="/feedbackCreate" , method = RequestMethod.GET)
	public String create(ModelMap model,@CookieValue("username") String username,HttpServletRequest request) {
		
		 DynamicForm form = DynamicForm.form().bindFromRequest(request);
		Long id = null;
		
		try{
			id = Long.valueOf(form.get("query"));
			User us = User.findById(id);
			Form<Feedback> feedbackForm = form(Feedback.class);
			model.addAttribute("feedbackForm",feedbackForm);
			model.addAttribute("user",us);
			model.addAttribute("ratings",getRatingOptions());
			return "createFeedback";
		}catch(NumberFormatException nfe){
			ExceptionHandler.onError(request.getRequestURI(),nfe);
		}
		return "Not able to show Results, Please check Employee for feedback.";
	}
	 @RequestMapping(value="/submitFeedback" , method = RequestMethod.POST)
	public @ResponseBody String submit(@CookieValue("username") String username,HttpServletRequest request){
		Form<Feedback> feedbackForm = form(Feedback.class).bindFromRequest(request);
		User user = User.findById(Long.parseLong(feedbackForm.data().get("employeeID")));
		feedbackForm.get().user = user;
		feedbackForm.get().manager = User.findByEmail(username);
		feedbackForm.get().save();
		return "Feedback Submitted Successfully";
	}
	 
	 @RequestMapping(value="/feedback/customSearchIndex" , method = RequestMethod.GET)
	public @ResponseBody String customSearchIndex(@CookieValue("username") String username,HttpServletRequest request){
		User user = User.findByEmail(username);
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("userEmail", user.email);
		return Json.toJson(CustomFeedbackSearchContext.getInstance().build().doSearch(form)).toString();
	}
	 @RequestMapping(value="/feedback/searchIndex" , method = RequestMethod.GET)
	public @ResponseBody String searchIndex(@CookieValue("username") String username,HttpServletRequest request){
		User user = User.findByEmail(username);
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("userEmail", user.email);
		return Json.toJson(FeedbackSearchContext.getInstance().build().doSearch(form)).toString();
	}
	 
	public static List<String> getRatingOptions(){
		Rating[] rating = Rating.values();
		List<String> ratings = new ArrayList<String>();
		for(int i=0;i<rating.length;i++){
			ratings.add(rating[i].name());
		}
		return ratings;
	}
	 @RequestMapping(value="/feedbackDisplay" , method = RequestMethod.GET)
	public String display(ModelMap model,@CookieValue("username") String username,HttpServletRequest request){
		User user = User.findByEmail(username);
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		Long id = null;
		try{
			SimpleDateFormat customDateFormat = new SimpleDateFormat("dd MMM yyyy");
	        id = Long.valueOf(form.get("query"));
			Feedback feedback = Feedback.find.byId(id);
			feedback.feedbackDateDisplay = customDateFormat.format(feedback.feedbackDate);
			model.addAttribute("Feedback",feedback);
			model.addAttribute("user",user);
			return "viewFeedback";
		}catch(NumberFormatException nfe){
			ExceptionHandler.onError(request.getRequestURI(),nfe);
		}
		return "Not able to show Results, Please select feedback.";
	}
	
}
