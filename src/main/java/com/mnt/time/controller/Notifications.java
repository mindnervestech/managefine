package com.mnt.time.controller;

import static play.data.Form.form;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import models.Notification;
import models.User;
import models.notification.NotificationDO;

import org.codehaus.jackson.JsonNode;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import play.data.Form;
import play.libs.Json;

import com.custom.ScheduleManager;

import dto.fixtures.MenuBarFixture;
@Controller
public class Notifications {
	@RequestMapping(value="/notificationIndex",method=RequestMethod.GET)
	public String index(ModelMap model,@CookieValue("username") String username) {
		User user = User.findByEmail(username);
		Notification notify = Notification.find.where().eq("company", user.companyobject).findUnique();
		Form<JsonNode> notifyForm;
		if(notify==null || notify.getSettingAsJson() == null){
			notifyForm = form(JsonNode.class).fill(Json.toJson(Arrays.asList(new NotificationDO(NotificationDO.Day.Sunday),
					new NotificationDO(NotificationDO.Day.Monday),new NotificationDO(NotificationDO.Day.Tuesday),new NotificationDO(NotificationDO.Day.Wednesday),
					new NotificationDO(NotificationDO.Day.Thursday),new NotificationDO(NotificationDO.Day.Friday),new NotificationDO(NotificationDO.Day.Saturday))));
		}else{
			notifyForm = form(JsonNode.class).fill(Json.parse(notify.getSettingAsJson()));
			
		}
	   	model.addAttribute("_menuContext", MenuBarFixture.build(username));
    	model.addAttribute("user", user);
    	model.addAttribute("notifyForm", notifyForm);
		return "notificationIndex";

	}
	
	@RequestMapping(value="/notificationSave",method=RequestMethod.POST)	
	public String save(ModelMap model,@CookieValue("username") String username,HttpServletRequest request) throws ParseException
	{
		
		User user = User.findByEmail(username);
		//find out this one.
		Map<java.lang.String,java.lang.String[]> _rmap = (Map<java.lang.String,java.lang.String[]>)request.getParameterMap();
		
		List<NotificationDO> list = new ArrayList<NotificationDO>();
		String[] mins = _rmap.get("m[]");
		String[] hrs = _rmap.get("h[]");
		
		for(int _day =0 ; _day < 7 ; _day++){
			list.add(new NotificationDO(NotificationDO.Day.values()[_day],hrs[_day],mins[_day]));
		}
		/**Database handling start*/
		String asJson = Json.toJson(list).toString();
		Notification notificationModel = Notification.find.where().eq("company", user.companyobject).findUnique();
		Notification notify= new Notification();
		notify.setCompany(user.getCompanyobject());
		notify.setSettingAsJson(asJson);
		if(notificationModel==null){
			notify.save();
		}else{
			notificationModel.setSettingAsJson(asJson);
			notificationModel.update();
		}
		/**Database handling end*/
		
		List<DateTime> plannedTimes = new ArrayList<DateTime>(); 
		for(NotificationDO notificationDO:list){
			if(notificationDO.dateTime!=null){
				plannedTimes.add(notificationDO.dateTime);
			}
		}
		ScheduleManager.reSchedule(plannedTimes, user.companyobject.id);

		model.addAttribute("_menuContext", MenuBarFixture.build(username));
    	model.addAttribute("user", user);
		return "index";

	}

}
