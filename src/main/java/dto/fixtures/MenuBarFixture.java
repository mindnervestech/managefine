package dto.fixtures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import models.User;

import com.custom.RequestInterceptor;
import com.custom.domain.Permissions;
import com.mnt.time.controller.routes;

import dto.MenuBar;
import dto.MenuBar.MenuItem;

public class MenuBarFixture {
	private static Map<String,MenuItem> map=null ;
	
	public static MenuBar build(String username){
		if(map==null){
			map = new LinkedHashMap<String,MenuItem>();
			for(Permissions p :Permissions.values()){
				if(p.parent() == null){
					if(!map.containsKey(p.name())){
						map.put(p.name(), new MenuItem(p.name(),p.display(),p.url()));
					}
				}else{
					if(map.containsKey(p.parent().name())){
						map.get(p.parent().name()).addSubMenu(p.name(),p.display(),p.url());
					}
				}
			}
			map.put("Logout",new MenuItem("Logout","Logout", null, routes.Application.logout.url, null,null));
		}
		
		List<MenuItem> resultMenu = new ArrayList<MenuItem>();
		User user = User.findByEmail(username);
		List<String> listedPermissions = new ArrayList<String>(Arrays.asList(user.getPermissions().split("[|]")));
		if(user.getRole() != null) {
			List<String> rolePermissions = 
					new ArrayList<String>(Arrays.asList(user.getRole().getPermissions().split("[|]")));
			for(String rolePerm: rolePermissions) {
				if(!listedPermissions.contains(rolePerm)) {
					listedPermissions.add(rolePerm);
				}
			}
		}
		
		for(MenuItem mi : map.values()){
			if(!mi.isSubMenu()){
				List<MenuItem> resultSubMenus = new ArrayList<MenuItem>();
				for(MenuItem sm : mi.subMenu){
					if(RequestInterceptor.isInUserPermission(user, sm.menu,listedPermissions) ){
						resultSubMenus.add(sm);
					}
				}
				
				if(resultSubMenus.size() != 0){
					MenuItem item = new MenuItem(mi.menu, mi.name, null, mi.url, resultSubMenus, null);
					resultMenu.add(item);
				}
			}
			else{
				if(RequestInterceptor.isInUserPermission(user, mi.menu,listedPermissions)){
					resultMenu.add(mi);
				}
			}
		}
		
		if(resultMenu.size() == 0){
			resultMenu.add(new MenuItem("Logout","Logout", null, routes.Application.logout.url, null,null));
//			resultMenu.add(new MenuItem("Home","Home", null, routes.Application.index.url, null,null));
		}
		else
		{
			resultMenu.add(new MenuItem("Logout","Logout", null, routes.Application.logout.url, null,null));
		}
		return new MenuBar(resultMenu);
	}

}
