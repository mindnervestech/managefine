package com.mnt.orghierarchy.controller;

import java.util.List;

import models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import play.libs.Json;

import com.avaje.ebean.Expr;
import com.mnt.orghierarchy.model.Organization;
import com.mnt.orghierarchy.service.OrgHierarchyService;
import com.mnt.orghierarchy.vm.OrganizationVM;

import dto.fixtures.MenuBarFixture;

@Controller
public class OrgHierarchyController {

	@Autowired
	OrgHierarchyService orgHierarchyService;
	
	@RequestMapping(value="/orgHierarchy",method=RequestMethod.GET)
	public String orgHierarchy(@CookieValue("username")String username,Model model) {
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
    	model.addAttribute("user", User.findByEmail(username));
    	model.addAttribute("data",Json.toJson(orgHierarchyService.getOrgHierarchy(username)));
		return "orgHierarchy";
	}
	
	@RequestMapping(value="/deleteOrgChild",method=RequestMethod.GET)
	public @ResponseBody Boolean deleteOrgChild(@RequestParam("id")Long id) {
		return orgHierarchyService.deleteOrgChild(id);
	} 
	
	@RequestMapping(value="/loadAllData",method=RequestMethod.GET)
	public @ResponseBody List loadAllData(@CookieValue("username")String username) {
		return orgHierarchyService.getOrgHierarchy(username);
	}
	
	@RequestMapping(value="/orgEmployee",method=RequestMethod.GET)
	public @ResponseBody List orgEmployee(@CookieValue("username")String username, @RequestParam("id")Long id) {
		return orgHierarchyService.orgEmployee(username,id);
	}

	@RequestMapping(value="/addOrganization",method=RequestMethod.GET)
	public String addOrganization(Model model) {
		return "addOrganization";
	}
	
	@RequestMapping(value="/editOrganization",method=RequestMethod.GET)
	public String editOrganization(Model model) {
		return "editOrganization";
	}
	
	@RequestMapping(value="/saveOrgChild",method=RequestMethod.POST) 
	public @ResponseBody Long saveOrgChild(@RequestParam("file")MultipartFile file,OrganizationVM organizationVM,@CookieValue("username")String username) {
		return orgHierarchyService.saveOrgChild(file, organizationVM,username);
	}
	
	@RequestMapping(value="/editOrgChild",method=RequestMethod.POST) 
	public @ResponseBody Long editOrgChild(@RequestParam("file")MultipartFile file,OrganizationVM organizationVM,@CookieValue("username")String username) {
		return orgHierarchyService.editOrgChild(file, organizationVM,username);
	}
	
	
	@RequestMapping(value="/editOrgNotImgChild",method=RequestMethod.POST) 
	public @ResponseBody Long editOrgNotImgChild(@RequestBody OrganizationVM organizationVM) {
		return orgHierarchyService.editOrgNotImgChild(organizationVM);
	}
	
	@RequestMapping(value="/orgProfile/{id}",method=RequestMethod.GET)
	public @ResponseBody FileSystemResource orgProfile(@PathVariable("id")Long id) {
		return new FileSystemResource(orgHierarchyService.orgProfile(id));
	}
	
	public static List<Organization> findOrgByName(String query, String username) {
		User user = User.findByEmail(username);
		System.out.println("queary ---"+query);
		List<Organization> orgs =  Organization.find.where().and(Expr.eq("companyId", user.companyobject.getId()),Expr.or(Expr.ilike("organizationName", query+"%"),Expr.ilike("organizationType", query+"%")))
	       		.findList();
		return orgs;
	}
	
}
