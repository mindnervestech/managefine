package com.mnt.employeeHierarchy.controller;

import java.util.List;

import models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mnt.roleHierarchy.vm.RoleVM;




import play.libs.Json;



import dto.fixtures.MenuBarFixture;
@Controller
public class employeeHierarchyController {

	@Autowired
	com.mnt.employeeHierarchy.service.EmployeeHierarchyService employeeHierarchyService;
	
	@RequestMapping(value="/employeeHierarchy",method=RequestMethod.GET)
	public String orgHierarchy(@CookieValue("username")String username,Model model,@RequestParam("id")Long id) {
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
    	model.addAttribute("user", User.findByEmail(username));
    	model.addAttribute("data",Json.toJson(employeeHierarchyService.getEmployeeHierarchy(username,id)));
		return "employeeHierarchy";
	}
	
	
}
