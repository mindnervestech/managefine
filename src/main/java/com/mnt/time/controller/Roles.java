package com.mnt.time.controller;

import static play.data.Form.form;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import models.LeaveLevel;
import models.RoleLeave;
import models.RoleLevel;
import models.RoleX;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import utils.ExceptionHandler;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.custom.domain.RoleLevels;
import com.custom.helpers.CustomRoleSearchContext;
import com.custom.helpers.RoleSave;
import com.custom.helpers.TaskSearchContext;
import com.google.common.collect.Sets;

import dto.fixtures.MenuBarFixture;

@Controller
public class Roles {
	@RequestMapping(value="/roleSearch" ,method=RequestMethod.GET)
	public @ResponseBody String search(HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		return Json.toJson(CustomRoleSearchContext.getInstance().build().doSearch(form)).toString();
    }
	
	@RequestMapping(value="/roleEdit" ,method=RequestMethod.POST)	
	public String edit(HttpServletRequest request) {
		try {
			RoleSave saveUtils = new RoleSave();
			saveUtils.doSave(true,request);
		} catch (Exception e) {
			ExceptionHandler.onError(request.getRequestURI(),e);
		}
		return "Edit Successfully";
    }
	@RequestMapping(value="/roleIndex" ,method=RequestMethod.GET)
	public String index(ModelMap model,@CookieValue("username")String username) {
		User user = User.findByEmail(username);
		model.addAttribute("user",user);
		model.addAttribute("_menuContext",MenuBarFixture.build(username));
		model.addAttribute("context",CustomRoleSearchContext.getInstance().build());
		return "roleIndex";
    }
	@RequestMapping(value="/roleDelete" ,method=RequestMethod.GET)
	public String delete() {
		return "";
    }
	
	@RequestMapping(value="/roleSave" ,method=RequestMethod.POST)		
	public String create(ModelMap model,@CookieValue("username")String username, HttpServletRequest request) {
		try {
			RoleSave saveUtils = new RoleSave();
			saveUtils.doSave(false,request);
			
			
		} catch (Exception e) {
			ExceptionHandler.onError(request.getRequestURI(),e);
		}
		return "";
    }
	
	@RequestMapping(value="/roleShowEdit " ,method=RequestMethod.GET)	
	public String showEdit(ModelMap model,HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		Long id = null;
		try{
			id = Long.valueOf(form.get("query"));
			model.addAttribute("_searchContext", new CustomRoleSearchContext(RoleX.findById(id)).build());
			return "views/editWizard";
		}catch(NumberFormatException nfe){
			ExceptionHandler.onError(request.getRequestURI(),nfe);
		}
		return "Not able to show Results, Check Logs";
	}
	
	@RequestMapping(value="/defineRoles " ,method=RequestMethod.GET)		
	public  String defineRoles(ModelMap model,@CookieValue("username") String username){
		User user = User.findByEmail(username);
		RoleX roleX = RoleX.find.where(Expr.eq("company", user.companyobject)).findUnique();
		Form<RoleX> roleXForm;
		if(roleX != null){
			roleXForm = form(RoleX.class).fill(roleX);
		}else{
			RoleX object = new RoleX();
			List<RoleLevel> roleLevels = new ArrayList<RoleLevel>();
			roleLevels.add(new RoleLevel());
			object.setRoleLevels(roleLevels);
			roleXForm = form(RoleX.class).fill(object);
				
		}
		model.addAttribute("user",user);
		model.addAttribute("_menuContext",MenuBarFixture.build(username));
		model.addAttribute("rolexForm",roleXForm);
		model.addAttribute("roleLevels",getRoleLevels());
		model.addAttribute("levels",getAllRoles(username));
		return "defineRoles";
	}
	
	@RequestMapping(value="/saveRole " ,method=RequestMethod.POST)		
	public @ResponseBody String saveRole(@CookieValue("username")String username,HttpServletRequest request){
		User user = User.findByEmail(username);
		Form<RoleX> roleXForm = form(RoleX.class).bindFromRequest(request);
		RoleX roleX = RoleX.find.where(Expr.eq("company", user.companyobject)).findUnique();
		
		if(roleX != null){
			List<LeaveLevel> ll = LeaveLevel.findListByCompany(user.getCompanyobject().getId());
			
			for(RoleLevel _rl : roleXForm.get().roleLevels){
				if( _rl.id != null ){
				Ebean.update(_rl,Sets.newHashSet("role_level","role_name"));	
				//_rl.update();
				} else {
					if(RoleLevel.find.where()
							.eq("role_name",_rl.role_name)
							.eq("roleX", roleX).findUnique() != null){
						return "Seems to be duplicate Submit or trying to insert duplicate roles name";
					}
					
					_rl.roleX = roleX;
					_rl.save();
					for(LeaveLevel l : ll) {
						RoleLeave rl=new RoleLeave();
						rl.setRoleLevel(_rl);
						rl.setCompany(user.getCompanyobject());
						rl.setLeaveLevel(l);
						rl.save();
					}
				}
			}
			
		}else{
			roleXForm.get().company = user.companyobject;
			roleXForm.get().save();
		}
		
		return "Defined Roles has been saved";
	}
	@RequestMapping(value="/saveOrg " ,method=RequestMethod.POST)		
	public @ResponseBody String saveOrg(@CookieValue("username")String username,HttpServletRequest request){
	//	String username="akg8990@gmail.com";
		User user = User.findByEmail(username);
		Form<RoleX> roleXForm = form(RoleX.class).bindFromRequest(request);
		RoleX roleX = RoleX.find.where(Expr.eq("company", user.companyobject)).findUnique();
		
		if(roleX != null){
			for(RoleLevel _rl : roleXForm.get().roleLevels){
				if( _rl.id != null ){
				_rl.update();
				} else {
					_rl.roleX = roleX;
					_rl.save();
				}
			}
			
		}else{
			roleXForm.get().company = user.companyobject;
			roleXForm.get().save();
		}

		return "Defined Hierarchy has been saved";
		
	}
	
	
	@RequestMapping(value="/showRoles " ,method=RequestMethod.GET)			
	public String showRoles(@CookieValue("username")String username,ModelMap model){
	//	String username="akg8990@gmail.com";
		User user = User.findByEmail(username);
		RoleX roleX = RoleX.find.where().add(Expr.eq("company", user.companyobject)).findUnique();
		if(roleX != null){
			Form<RoleX> roleXForm = form(RoleX.class).fill(roleX);
			model.addAttribute("user",user);
			model.addAttribute("_menuContext",MenuBarFixture.build(username));
			model.addAttribute("rolexForm",roleXForm);
			model.addAttribute("roleLevels",getAllRoles(username));
			model.addAttribute("levels",getRoleLevels());
			return "defineRoleHierarchy";
		}else{
			Form<RoleX> roleXForm = null;
			model.addAttribute("user",user);
			model.addAttribute("_menuContext",MenuBarFixture.build(username));
			model.addAttribute("rolexForm",roleXForm);
			return "defineRoleHierarchy";
		}
	}
	
	public static List<String> getRoleLevels(){
		RoleLevels[] rolelevel = RoleLevels.values();
		List<String> roleLevels = new ArrayList<String>();
		for(int i=0;i<rolelevel.length;i++){
			roleLevels.add(rolelevel[i].name());
		}
		return roleLevels;
	}
	
	public static List<String> getAllRoles(String username){
		//String username="akg8990@gmail.com";
		User user = User.findByEmail(username);
		List<String> roleLevels = new ArrayList<String>();
		
		List<RoleLevel> roleLevelList = RoleLevel.findListByCompany(user.getCompanyobject().getId());
		for(int i=0;i<roleLevelList.size();i++){
			roleLevels.add(roleLevelList.get(i).role_name);
		}
		return roleLevels;
	}
	
}
