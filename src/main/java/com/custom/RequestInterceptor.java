package com.custom;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.RoleLevel;
import models.User;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.avaje.ebean.Expr;
import com.custom.domain.BlackListedPermissions;

public class RequestInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		//String userName = arg0.getSession().getAttribute("username").toString();
        //if(isInUserPermission(userName, arg0.getRequestURI())){
               return true;
        //}
		//return false;
	}
	
	/*public static boolean isInRolePermission(String userName, String menu) {
		User user = User.findByEmail(userName);
		List<String> blackListedPermissions = null;
		
		if("SuperAdmin".equals(user.designation)){
			blackListedPermissions = Arrays.asList(user.getPermissions().split("[|]"));
		}else if("Admin".equals(user.designation)){
			return isInUserPermission(user, menu);
		}else{
			RoleLevel roleLevel = RoleLevel.find.where().add(Expr.eq("role_level", user.designation)).findUnique();
			if(roleLevel.getPermissions() != null && !"".equals(roleLevel.getPermissions())){
				blackListedPermissions = Arrays.asList(roleLevel.getPermissions().split("[|]"));
			}else{
				blackListedPermissions = Arrays.asList(
						BlackListedPermissions.BLACKLISTED_PERMISSIONS_FOR_USERS.split("[|]"));
			}
		}
		if(!"Admin".equals(user.designation)){
        	if(blackListedPermissions.contains(menu)){ //|| "CompanyRequest".equals(menu)
        		return false;
        	}
        }
		return true;
	}*/

	public static boolean isInUserPermission(User user,String menu, List<String> listedPermissions){
        // Logic to check the uri in permission list for User, if yes return true else false
        
        if("CompanyRequest".equals(menu)){
        	if("SuperAdmin".equals(user.designation)){
        		return true;
    		}else{
    			return false;
    		}
		}
        
		if (user.getPermissions() != null) {
			
			if (listedPermissions.contains(menu)) {
				return true;
			} 
		} 
		else {
			return true;
		}
		return false;
	}

}
