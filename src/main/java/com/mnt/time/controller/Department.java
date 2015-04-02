package com.mnt.time.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import models.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.orghierarchy.vm.DepartmentVM;

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
			models.Department d = new models.Department();
			d.setName(department.getName());
			d.save();
		}
	}
	
	@RequestMapping(value="/deleteDepartments",method=RequestMethod.POST)
	public @ResponseBody void deleteDepartments(@RequestParam("dId") Long deptId) {
		System.out.println("depet id = "+deptId);
		models.Department dept = models.Department.departmentById(deptId);
		dept.delete();
	}
	
	 @RequestMapping(value="/getDeparment",method=RequestMethod.POST)
		public @ResponseBody  List<DepartmentVM> getDeparment() {
		 	
		 List<DepartmentVM> flist = new ArrayList<>();
		 List<models.Department> fl = models.Department.findAll();
		
		 for(models.Department de : fl) {
			 DepartmentVM departmentVM = new DepartmentVM();
			 departmentVM.setName(de.getName());
			 departmentVM.setId(de.getId());
			 flist.add(departmentVM);
		  }
		 
		return flist;
		} 
	
	
	
}
