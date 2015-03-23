package com.mnt.time.controller;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static play.data.Form.form;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import models.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import play.data.DynamicForm;
import play.libs.Json;
import utils.AvailablePermission;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import com.custom.domain.Permissions;
import com.google.common.base.Function;
import com.mnt.core.utils.GridViewModel;
import com.mnt.core.utils.GridViewModel.PageData;
import com.mnt.core.utils.GridViewModel.RowViewModel;

import dto.fixtures.MenuBarFixture;
//
//@Security.Authenticated(Secured.class)
//@BasicAuth

@Controller
public class UserPermissions {
	
	public static final String DELIMITER = "|";
	
	@RequestMapping(value="/permissionIndex", method = RequestMethod.GET)
	public String index(ModelMap model, @CookieValue("username") String username) {
		User user = User.findByEmail(username);
		
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);
		
		return "mainPermission";
//		return ok(mainPermission.render(MenuBarFixture.build(request().cookie("email").value()), user));
	}
	
	@RequestMapping(value="/permission/save", method = RequestMethod.POST)
	public @ResponseBody String save(HttpServletRequest request){
		DynamicForm form = form().bindFromRequest(request);
		Long uid = Long.parseLong(form.get("id"));
		int len=0;
		
		Map<java.lang.String,java.lang.String[]> requestData = (Map<java.lang.String,java.lang.String[]>)request.getParameterMap();
    	
		if(requestData.containsKey("permissions")){
			len=requestData.get("permissions").length;
		}
		
		String result = "";
		for(int i=0;i<len;i++){
			result += requestData.get("permissions")[i]+ DELIMITER;
		}
		User user = User.find.byId(uid);
		user.setPermissions(result);
		user.update(user.getId());
		return "Permissions Saved Successfully";
	}
	
	@RequestMapping(value="/permission/update", method = RequestMethod.POST)
	public @ResponseBody String update(HttpServletRequest request){
		DynamicForm form = form().bindFromRequest(request);
		
		Long id = Long.parseLong(form.get("id"));
		User user = User.find.byId(id);
		List<AvailablePermission> list = new ArrayList<AvailablePermission>();
		Permissions[] permissions = Permissions.values();
		List<String> userPermission=new ArrayList<String>();
		if(user.getPermissions() != null){
			String permission = user.getPermissions();
			String[] permissionArray = permission.split("[|]");
			for(String pa : permissionArray){
				userPermission.add(pa);
			}
		}
		AvailablePermission available= null;
		for(Permissions p:permissions){
			if(!p.name().equals("CompanyRequest")){
				if(userPermission.size()!=0 && userPermission.contains(p.name())){
					if(p.parent() != null){
						if(p.url() != "#"){
							available = new AvailablePermission(p.name(),p.parent() == null ? p.display():  p.display(), true,true,true);
						}else{
							available = new AvailablePermission(p.name(),p.parent() == null ? p.display():  p.display(), true,true,false);
						}	
					}else{
						if(p.url() != "#"){
							available = new AvailablePermission(p.name(),p.parent() == null ? p.display():  p.display(), true,false,true);
						}else{
							available = new AvailablePermission(p.name(),p.parent() == null ? p.display():  p.display(), true,false,false);
						}	
					}
					list.add(available);
				}else{
					if(p.parent() != null){
						if(p.url() != "#"){
							available = new AvailablePermission(p.name(),p.parent() == null ? p.display():  p.display(), false,true,true);
						}else{
							available = new AvailablePermission(p.name(),p.parent() == null ? p.display():  p.display(), false,true,false);
						}	
					}else{
						if(p.url() != "#"){
							if("Home".equals(p.name())){
								available = new AvailablePermission(p.name(),p.parent() == null ? p.display():  p.display(), false,false,true);
							}else{
								available = new AvailablePermission(p.name(),p.parent() == null ? p.display():  p.display(), false,false,true);
							}
						}else{
							available = new AvailablePermission(p.name(),p.parent() == null ? p.display():  p.display(), false,false,false);
						}	
					}
					list.add(available);
				}
			}
		}
		return Json.toJson(list).toString();
	}
	
	@RequestMapping(value="/permissionsearch", method= RequestMethod.GET)
	public @ResponseBody String getUserList(@CookieValue("username") String username,HttpServletRequest request){
		
		User user = User.findByEmail(username);
		
		DynamicForm form = form().bindFromRequest(request);
		PageData pageData = new PageData(Integer.valueOf(form.get("rows")),
							Integer.valueOf(form.get("page")));
		int page = Integer.parseInt(form.get("page"));
		int limit = Integer.parseInt(form.get("rows"));
		double min = Double.parseDouble(form.get("rows"));
		String sidx = form.get("sidx");
		String sord = form.get("sord");
		
		List<Expression> expressions = new ArrayList<Expression>();
		if(form.get("firstName") != null && !form.get("firstName").isEmpty()){
			expressions.add( Expr.like("firstName", "%"+form.get("firstName")+"%"));
		}
		
		if(form.get("lastName") != null && !form.get("lastName").isEmpty()){
			expressions.add( Expr.like("lastName", "%"+form.get("lastName")+"%"));
		}
		int count =0;
		Expression exp = null;
		if(expressions.size()!=0)
		{
			exp = expressions.get(0);
			for(int i =1;i<expressions.size();i++)
			{
				exp = Expr.and(exp, expressions.get(i));
			}
			count = User.find.where().eq("userStatus", com.custom.domain.Status.Approved).eq("companyobject.companyCode", user.getCompanyobject().getCompanyCode()).add(exp)
						.ne("email", user.getEmail()).findRowCount();
		}
		else{
			count = User.find.where().eq("userStatus", com.custom.domain.Status.Approved).eq("companyobject.companyCode", user.getCompanyobject().getCompanyCode())
						.ne("email", user.getEmail()).findRowCount();
		}
			
		//	results = Agent.find.where().add(exp).findList();
			int total_pages=0;
			if(count > 0){
				total_pages = (int) Math.ceil(count/min);
			}
			else{
				total_pages = 0;
			}
			
			if(page > total_pages){
				page = total_pages;
			}
			
		int start = limit*page - limit;
		List<User> results =  exp == null ?models.User.find.where().eq("userStatus", com.custom.domain.Status.Approved).
				eq("companyobject.companyCode", user.getCompanyobject().getCompanyCode())
							.ne("email", user.getEmail()).setFirstRow(start).setMaxRows(limit).findList()
					:models.User.find.where().eq("userStatus", com.custom.domain.Status.Approved).eq("companyobject.companyCode", user.getCompanyobject().getCompanyCode()).add(exp)
							 .ne("email", user.getEmail()).setFirstRow(start).setMaxRows(limit).findList();
							
		List<GridViewModel.RowViewModel> rows = transform(results, toJqGridFormat()) ;
		GridViewModel gridViewModel = new GridViewModel(pageData, count, rows);
		return Json.toJson(gridViewModel).toString();
	}
	
	private static Function<User,RowViewModel> toJqGridFormat() {
		return new Function<User, RowViewModel>() {
		
			@Override
			public RowViewModel apply(User user) {
		
				List<String> allPerission = new ArrayList<String>();
				for(Permissions tempPerm : Arrays.asList(Permissions.values())){
					allPerission.add(tempPerm.name());
				}
				
				List<String> blackListedPermissions = new ArrayList<String>();
				String permissions="";
				if(user.getPermissions() != null && !"".equals(user.getPermissions())){
					blackListedPermissions = Arrays.asList(user.getPermissions().split("[|]"));
					for(String permission : allPerission){
						if(!"CompanyRequest".equals(permission)){
							if(!blackListedPermissions.contains(permission)){
								permissions = permissions.concat(permission).concat("|");
							}
						}
					}
				}
				
				return new GridViewModel.RowViewModel((user.getId()).intValue(), newArrayList(
						user.getEmployeeId(),
						user.getFirstName(),
						user.getLastName(),
						user.getDesignation().toString(),
						user.getPermissions(),
					Long.toString(user.getId())));
			}
		};
	}
}
