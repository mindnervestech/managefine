package com.mnt.time.controller;

import java.util.List;

import models.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dto.fixtures.MenuBarFixture;

@Controller
public class Department {

	@RequestMapping(value="/defineDepartments",method=RequestMethod.GET)
	public String defineDepartments(Model model,@CookieValue("username")String username) {
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
    	model.addAttribute("user", User.findByEmail(username));
		return "defineDepartments";
	}
	
	@RequestMapping(value="/saveDepartments",method=RequestMethod.POST)
	public @ResponseBody void saveDepartments(@CookieValue("username")String username,@RequestBody List<DepartmentVM> departments) {
		for(DepartmentVM department : departments) {
			System.out.println("department:"+department.name);
		}
	}
	
	public static class DepartmentVM {
		public String name;
	}
}
