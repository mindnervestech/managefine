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
	public static class DepartmentList extends ArrayList<DepartmentVM> { }
	
	@RequestMapping(value="/saveDepartments",method=RequestMethod.POST)
	public @ResponseBody void saveDepartments(@CookieValue("username")String username,@RequestBody DepartmentList departments) {
		for(DepartmentVM department : departments) {
			models.Department d = new models.Department();
			d.setName(department.getName());
			if(department.getId() == null) {
				d.save();
			} else {
				d.setId(department.getId());
				d.update();
			}
			
		}
	}
	
	@RequestMapping(value="/deleteDepartments",method=RequestMethod.POST)
	public @ResponseBody void deleteDepartments(@RequestParam("dId") Long deptId) {
		models.Department dept = models.Department.departmentById(deptId);
		dept.delete();
	}
	
	 @RequestMapping(value="/getDeparment",method=RequestMethod.POST)
		public @ResponseBody  List<DepartmentVM> getDeparment() {
		 	
		 List<DepartmentVM> flist = new ArrayList<DepartmentVM>();
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
