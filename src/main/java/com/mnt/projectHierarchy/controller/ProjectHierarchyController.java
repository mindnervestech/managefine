package com.mnt.projectHierarchy.controller;

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

import com.mnt.projectHierarchy.vm.ProjectclassVM;
import com.mnt.projectHierarchy.vm.ProjectsupportattributVM;
import com.mnt.roleHierarchy.vm.RoleVM;




import play.libs.Json;



import dto.fixtures.MenuBarFixture;
@Controller
public class ProjectHierarchyController {

	@Autowired
	com.mnt.projectHierarchy.service.ProjectHierarchyService projectHierarchyService;
	
	@RequestMapping(value="/defineProjects",method=RequestMethod.GET)
	public String orgHierarchy(@CookieValue("username")String username,Model model) {
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
    	model.addAttribute("user", User.findByEmail(username));
    	//model.addAttribute("data",Json.toJson(roleHierarchyService.getRoleHierarchy()));
		return "defineProjects";
	}
	
		
	@RequestMapping(value="/AllProjectType",method=RequestMethod.GET)
	public @ResponseBody List AllProjectType() {
		return projectHierarchyService.getAllProjectType();
	}
	
	@RequestMapping(value="/addProjectType",method=RequestMethod.GET)
	public String addProjectType(Model model) {
		return "addProjectType";
	}
	
	@RequestMapping(value="/addProjectTypeValue",method=RequestMethod.GET)
	public String addProjectTypeValue(Model model) {
		return "addProjectTypeValue";
	}
	
	@RequestMapping(value="/editProjectTypeValue",method=RequestMethod.GET)
	public String editProjectTypeValue(Model model) {
		return "editProjectTypeValue";
	}
	
	@RequestMapping(value="/saveproject",method=RequestMethod.POST) 
	public @ResponseBody Long saveproject(@RequestBody ProjectclassVM projectclassVM) {
		return projectHierarchyService.saveproject(projectclassVM);
	}
	
	@RequestMapping(value="/saveProjectChild",method=RequestMethod.POST) 
	public @ResponseBody Long saveProjectChild(@RequestBody ProjectsupportattributVM projectsupportattributVM) {
		return projectHierarchyService.saveProjectChild(projectsupportattributVM);
	}
	
	@RequestMapping(value="/editProjectChild",method=RequestMethod.POST) 
	public @ResponseBody Long editProjectChild(@RequestBody ProjectsupportattributVM projectsupportattributVM) {
		return projectHierarchyService.editProjectChild(projectsupportattributVM);
	}
	
	@RequestMapping(value="/selectProjectType",method=RequestMethod.GET) 
	public @ResponseBody List selectProjectType(@RequestParam("id")Long id) {
		return projectHierarchyService.selectProjectType(id);
	}
	
	
	@RequestMapping(value="edit/project/selectProjectType",method=RequestMethod.GET) 
	public @ResponseBody List selectProjectType1(@RequestParam("id")Long id) {
		return projectHierarchyService.selectProjectType(id);
	}
	
	
	@RequestMapping(value="/editProjectTypeInfo",method=RequestMethod.GET)
	public @ResponseBody List editProjectTypeInfo(@RequestParam("id")Long id) {
		return projectHierarchyService.editProjectTypeInfo(id);
	}
	
	@RequestMapping(value="/deleteProjectChild",method=RequestMethod.GET)
	public @ResponseBody Boolean deleteOrgChild(@RequestParam("id")Long id) {
		return projectHierarchyService.deleteProjectChild(id);
	}
	
	
}
