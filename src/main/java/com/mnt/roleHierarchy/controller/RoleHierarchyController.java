package com.mnt.roleHierarchy.controller;

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
public class RoleHierarchyController {

	@Autowired
	com.mnt.roleHierarchy.service.RoleHierarchyService roleHierarchyService;
	
	@RequestMapping(value="/defineRoles",method=RequestMethod.GET)
	public String orgHierarchy(@CookieValue("username")String username,Model model) {
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
    	model.addAttribute("user", User.findByEmail(username));
    	model.addAttribute("data",Json.toJson(roleHierarchyService.getRoleHierarchy()));
		return "defineRoles";
	}
	
	@RequestMapping(value="/loadAllRoleData",method=RequestMethod.GET)
	public @ResponseBody List loadAllRoleData(@CookieValue("username")String username) {
		return roleHierarchyService.getRoleHierarchy();
	}
	
	@RequestMapping(value="/addRoleOrganization",method=RequestMethod.GET)
	public String addRoleOrganization(Model model) {
		return "addRoleOrganization";
	}
	
	@RequestMapping(value="/editRoleOrganization",method=RequestMethod.GET)
	public String editRoleOrganization(Model model) {
		return "editRoleOrganization";
	}
	@RequestMapping(value="/saveRoleChild",method=RequestMethod.POST) 
	public @ResponseBody Long saveRoleChild(@RequestBody RoleVM roleVM,@CookieValue("username")String username) {
		return roleHierarchyService.saveRoleChild(roleVM,username);
	}
	
	@RequestMapping(value="/deleteRoleChild",method=RequestMethod.GET)
	public @ResponseBody Boolean deleteOrgChild(@RequestParam("id")Long id) {
		return roleHierarchyService.deleteRoleChild(id);
	}
	
	@RequestMapping(value="/editRoleChild",method=RequestMethod.POST) 
	public @ResponseBody Long editRoleChild(@RequestBody RoleVM roleVM,@CookieValue("username")String username) {
		return roleHierarchyService.editRoleChild(roleVM,username);
	}
	
	@RequestMapping(value="/findDepartment",method=RequestMethod.GET)
	public @ResponseBody List findDepartment() {
		return roleHierarchyService.findDepartment();
	}
	
	
	@RequestMapping(value="/findSelectedDepartment",method=RequestMethod.GET) 
	public @ResponseBody RoleVM findSelectedDepartment(@RequestParam("id")Long id) {
		return roleHierarchyService.findSelectedDepartment(id);
	}
	
}
