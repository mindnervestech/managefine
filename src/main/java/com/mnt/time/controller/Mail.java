package com.mnt.time.controller;

import static play.data.Form.form;

import javax.servlet.http.HttpServletRequest;

import models.MailSetting;
import models.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dto.fixtures.MenuBarFixture;
import play.data.Form;


@Controller
public class Mail{
	
	@RequestMapping(value="/mailIndex",method=RequestMethod.GET)
	public String index(@CookieValue("username") String username,ModelMap model) {
		User user = User.findByEmail(username);
		MailSetting mail = MailSetting.find.where().eq("companyObject", user.companyobject).findUnique();
		Form<MailSetting> mailForm;
		if(mail != null) {
			mailForm = form(MailSetting.class).fill(mail);
		}
		else {
			mailForm = form(MailSetting.class).fill(new MailSetting());
		}
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("mailForm", mailForm);
		model.addAttribute("user", user);
		return "mailIndex";

	}
	
	@RequestMapping(value="/mailSave",method=RequestMethod.POST)	
	public String settingSave(HttpServletRequest request,@CookieValue("username") String username,ModelMap model)
	{
		Form<MailSetting> mailForm = form(MailSetting.class).bindFromRequest(request);
		
		User user = User.findByEmail(username);
		MailSetting mail1 = MailSetting.find.where().eq("companyObject", user.getCompanyobject()).findUnique();
		
		MailSetting mail = mailForm.get();
		mail.id = mail1.id;
		mail.update();
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", User.findByEmail(username));
		return "home";
	}
	
	
}
