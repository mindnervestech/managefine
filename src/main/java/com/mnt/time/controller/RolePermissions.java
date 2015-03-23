package com.mnt.time.controller;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static play.data.Form.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import models.RoleLevel;
import models.RoleX;
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

/*@Security.Authenticated(Secured.class)
@BasicAuth*/

@Controller
public class RolePermissions {
	private static final String DELIMITER = "|";
	
	 @RequestMapping(value="/rolepermissionIndex" , method = RequestMethod.GET)
	public String index(ModelMap model,@CookieValue("username") String username) {
		User user = User.findByEmail(username);
		model.addAttribute("_menuContext",MenuBarFixture.build(username));
		model.addAttribute("user", user);
		
		return "roleMainPermission";
				
		//return ok(roleMainPermission.render(MenuBarFixture.build(request().cookie("email").value()), user));
	}
	
	 @RequestMapping(value="/rolepermission/save" , method = RequestMethod.POST)
	public @ResponseBody String save(@CookieValue("username") String username,HttpServletRequest request){
		
		DynamicForm form = form().bindFromRequest(request);
		Long uid = Long.parseLong(form.get("id"));
		int len=0;
		
		Map<java.lang.String,java.lang.String[]> requestData = (Map<java.lang.String,java.lang.String[]>)request.getParameterMap();
		if(requestData.containsKey("permissions")){
		//	len=request().body().asFormUrlEncoded().get("permissions").length;
			len=requestData.get("permissions").length;
		}
		
		String result = "";
		
		for(int i=0;i<len;i++){
			result += requestData.get("permissions")[i]+ DELIMITER;
		}
		
		RoleLevel roleLevel = RoleLevel.find.byId(uid);

			roleLevel.setPermissions(result);
			roleLevel.update();
			
//			User.findByEmail(request().username()).companyobject.id
			/*User user1=User.findByEmail(username);
			long companyId=user1.getCompanyobject().getId();
			List<User> users = User.find.where(Expr.and(Expr.eq("role", roleLevel),
					Expr.eq("role.roleX", RoleX.findByCompany(companyId))))
					.findList();
			
			for(User user : users){
				user.setPermissions(roleLevel.getPermissions());
				user.update();
			}*/
			return "Permissions Saved Successfully";
	}
	
	
	 @RequestMapping(value="/rolepermission/update" , method = RequestMethod.POST)
	public @ResponseBody String update(HttpServletRequest request){
		
		DynamicForm form = form().bindFromRequest(request);
		
		Long id = Long.parseLong(form.get("id"));
		RoleLevel roleLevel = RoleLevel.find.byId(id);
		
		Permissions[] permissions = Permissions.values();
		List<AvailablePermission> list = new ArrayList<AvailablePermission>();
		List<String> userPermission=new ArrayList<String>();
		if(roleLevel.getPermissions() != null){
			String permissions1 = roleLevel.getPermissions();
			String[] permissionArray = permissions1.split("\\"+DELIMITER);
			for(String pa : permissionArray){
				userPermission.add(pa);
			}
		}
		AvailablePermission available= null;
		for(Permissions p:permissions){
			if(!p.name().equals("CompanyRequest"))
			{
				if(userPermission.size()!=0 && userPermission.contains(p.name())){
					if(p.parent() != null){
						if(p.url() != "#")
							available = new AvailablePermission(p.name(),p.parent() == null ? p.display():  p.display(), true,true,true);
						else
							available = new AvailablePermission(p.name(),p.parent() == null ? p.display():  p.display(), true,true,false);
					}
					else{
						if(p.url() != "#")
							available = new AvailablePermission(p.name(),p.parent() == null ? p.display():  p.display(), true,false,true);
						else
							available = new AvailablePermission(p.name(),p.parent() == null ? p.display():  p.display(), true,false,false);
					}
						list.add(available);
				}else{
					if(p.parent() != null){
						if(p.url() != "#")
							available = new AvailablePermission(p.name(),p.parent() == null ? p.display():  p.display(), false,true,true);
						else
							available = new AvailablePermission(p.name(),p.parent() == null ? p.display():  p.display(), false,true,false);
					}else{
						if(p.url() != "#")
							available = new AvailablePermission(p.name(),p.parent() == null ? p.display():  p.display(), false,false,true);
						else
							available = new AvailablePermission(p.name(),p.parent() == null ? p.display():  p.display(), false,false,false);
					}
					list.add(available);
				}
			}
		}
		return Json.toJson(list).toString();
	}
	
	 @RequestMapping(value="/rolepermissionsearch" , method = RequestMethod.GET)
	public @ResponseBody String getRoleList(@CookieValue("username") String username,HttpServletRequest request){
		
		 DynamicForm form = form().bindFromRequest(request);
		PageData pageData = new PageData(Integer.valueOf(form.get("rows")),
							Integer.valueOf(form.get("page")));
		int page = Integer.parseInt(form.get("page"));
		int limit = Integer.parseInt(form.get("rows"));
		double min = Double.parseDouble(form.get("rows"));
		String sidx = form.get("sidx");
		String sord = form.get("sord");
		List<RoleX> results = null;
		
		List<Expression> expressions = new ArrayList<Expression>();
		
		expressions.add(Expr.eq("company", User.findByEmail(username).getCompanyobject()));
		
		if(form.get("role_name") != null){
			expressions.add( Expr.like("role_name", "%"+form.get("role_name")+"%"));
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
			count = RoleX.find.where().add(exp).findRowCount();
		}
		else{
			count = RoleX.find.where().findRowCount();
		}
			//results = Agent.find.where().add(exp).findList();
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
		RoleX role =  exp == null ?RoleX.find.setFirstRow(start).setMaxRows(limit).findUnique()
					:RoleX.find.where().add(exp).setFirstRow(start).setMaxRows(limit).findUnique();
			
		List<GridViewModel.RowViewModel> rows = transform(role.getRoleLevels(), toJqGridFormat());
		GridViewModel gridViewModel = new GridViewModel(pageData, count, rows);
		return Json.toJson(gridViewModel).toString();
	}
	
	private static Function<RoleLevel,RowViewModel> toJqGridFormat() {
		return new Function<RoleLevel, RowViewModel>() {
		
			@Override
			public RowViewModel apply(RoleLevel roleLevel) {
				
				List<String> allPerission = new ArrayList<String>();
				for(Permissions tempPerm : Arrays.asList(Permissions.values())){
					allPerission.add(tempPerm.name());
				}
				
				List<String> blackListedPermissions = new ArrayList<String>();
				String permissions="";
				if(roleLevel.getPermissions() != null && !"".equals(roleLevel.getPermissions())){
					blackListedPermissions = Arrays.asList(roleLevel.getPermissions().split("[|]"));
					//permissions = roleLevel.getPermissions();
					for(String permission : allPerission){
						if(blackListedPermissions.contains(permission)){
							permissions = permissions.concat(permission).concat("|");
						}
					}
				}
				
				return new GridViewModel.RowViewModel((roleLevel.getId()).intValue(), newArrayList(
						roleLevel.getRole_name(),
						permissions,
						Long.toString(roleLevel.getId())));
			}
		};
	}
}
