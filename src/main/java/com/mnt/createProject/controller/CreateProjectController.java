package com.mnt.createProject.controller;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import models.LeaveLevel;
import models.RoleLeave;
import models.RoleLevel;
import models.RoleX;
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

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.google.common.collect.Sets;
import com.mnt.createProject.model.Projectinstance;
import com.mnt.createProject.model.Projectinstancenode;
import com.mnt.createProject.model.Saveattributes;
import com.mnt.projectHierarchy.model.Projectclassnode;
import com.mnt.projectHierarchy.model.Projectclassnodeattribut;
import com.mnt.projectHierarchy.vm.ProjectclassVM;
import com.mnt.projectHierarchy.vm.ProjectclassnodeattributVM;
import com.mnt.projectHierarchy.vm.ProjectsupportattributVM;
import com.mnt.roleHierarchy.vm.RoleVM;




import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;



import dto.fixtures.MenuBarFixture;
@Controller
public class CreateProjectController {

	@Autowired
	com.mnt.createProject.service.CreateProjectService createProjectService;
	
	@RequestMapping(value="/createProject",method=RequestMethod.GET)
	public String orgHierarchy(@CookieValue("username")String username,Model model) {
		System.out.println("hhhhhhhhh");
		System.out.println(username);
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
    	model.addAttribute("user", User.findByEmail(username));
    	//model.addAttribute("data",Json.toJson(roleHierarchyService.getRoleHierarchy()));
		return "createProject";
	}
	
	
	@RequestMapping(value="/AddJspPage",method=RequestMethod.GET)
	public String AddJspPage(@RequestParam("id")Long id,Model model) {
		model.addAttribute("nodeMetaData",createProjectService.getAddJspPage(id));
		return "nodeMetaData";
	}
	
	@RequestMapping(value="/EditJspPage",method=RequestMethod.GET)
	public String EditJspPage(@RequestParam("id")Long id,Model model) {
		model.addAttribute("editNodeMetaData",createProjectService.getAddJspPage(id));
		return "editNodeMetaData";
	}

		
	
	@RequestMapping(value="/saveCreateProjectAttributes" ,method=RequestMethod.POST)		
	public @ResponseBody String saveCreateProjectAttributes(@CookieValue("username")String username,
			HttpServletRequest request){
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		
		List<Projectclassnodeattribut> attributesArray = Projectclassnodeattribut.getattributByprojectId(Long.parseLong(form.data().get("projectId")));
		
		Projectinstancenode projectnode = Projectinstancenode.getProjectParentId(Long.parseLong(form.data().get("projectId")), Long.parseLong(form.data().get("projecttypeId")));
		if(projectnode == null){
			
			Projectinstance projectinstance= new Projectinstance();
			projectinstance.setProjectTypes(form.data().get("projectT"));
			projectinstance.setProjectDescription(form.data().get("projectD"));
			projectinstance.setProjectid(Long.parseLong(form.data().get("projecttypeId")));
			
			projectinstance.save();
			
			Projectinstancenode projectinstancenode= new Projectinstancenode();
			projectinstancenode.setProjecttypeid(Long.parseLong(form.data().get("projecttypeId")));
			projectinstancenode.setProjectclassnode(Projectclassnode.getProjectById(Long.parseLong(form.data().get("projectId"))));
		
			projectinstancenode.save();
		   
		    for(Projectclassnodeattribut attr : attributesArray){
		    	
				Saveattributes saveattri = new Saveattributes();
				String checkboxValue = null;
				checkboxValue = "";
				if(attr.getType().equalsIgnoreCase("Checkbox")) {
					
		    		String values[] = request.getParameterValues(attr.getName());
		    		System.out.println(values);
		    		for(String s:values){
		    			System.out.println(s);
		    			checkboxValue = checkboxValue + s +",";
		    			System.out.println(checkboxValue);
		    		}
		    		
		    		System.out.println(checkboxValue);
		    		saveattri.setAttributValue(checkboxValue);
		    	} else {
		    		saveattri.setAttributValue(form.data().get(attr.getName()));
		    	}
				saveattri.setProjectattrid(attr.getId());
				saveattri.setProjectinstancenode_id(projectinstancenode.getId());
				
				saveattri.save();
				
			}
			
		}else{
			for(Projectclassnodeattribut attr : attributesArray){
		    	
				Saveattributes saveattri = Saveattributes.getprojectattriById(attr.getId());
				String checkboxValue = null;
				checkboxValue = "";
				if(attr.getType().equalsIgnoreCase("Checkbox")) {
					
		    		String values[] = request.getParameterValues(attr.getName());
		    		for(String s:values){
		    			checkboxValue = checkboxValue + s +",";
		    		}
		    		
		    		System.out.println(checkboxValue);
		    		saveattri.setAttributValue(checkboxValue);
		    	} else {
		    		saveattri.setAttributValue(form.data().get(attr.getName()));
		    	}
				
				saveattri.update();
				
			}
		}
		
	
	
		return null;
	}
	
		
}

