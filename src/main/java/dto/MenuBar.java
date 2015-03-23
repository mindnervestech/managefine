package dto;

import java.util.ArrayList;
import java.util.List;

public class MenuBar {
	
	public List<MenuItem> items;
	
	public List<MenuItem> getItems() {
		return items;
	}

	public MenuBar(List<MenuItem> item){
		this.items = item;
	}

	public static class MenuItem {
		
		public String menu;
		public String getMenu() {
			return menu;
		}

		public String getName() {
			return name;
		}

		public List<Role> getAccessRight() {
			return accessRight;
		}

		public String getUrl() {
			return url;
		}

		public List<MenuItem> getSubMenu() {
			return subMenu;
		}

		public List<ObjectBehaviour> getEntiryObject() {
			return entiryObject;
		}

		public String name;
		public List<Role> accessRight;
		public String url;
		public List<MenuItem> subMenu;
		public List<ObjectBehaviour> entiryObject;
		
		public MenuItem(String menu, String name, List<Role> roles, String url,List<MenuItem> subMenu,List<ObjectBehaviour> entiryObject){
			this.menu = menu;
			this.name = name;
			this.accessRight = roles;
			this.url = url;
			this.subMenu = subMenu;
			this.entiryObject = entiryObject;
		}
		
		public MenuItem(String menu, String name, String url){
			this.menu = menu;
			this.name = name;
			this.url = url;
		}
		
		public void addSubMenu(String menu, String name, String url){
			if(subMenu == null){
				subMenu = new ArrayList<MenuItem>();
			}
			subMenu.add(new MenuItem(menu,name,url));
		}
		
		public boolean isSubMenu(){
			return (subMenu == null || subMenu.size() == 0);
		}

		public void setMenu(String menu) {
			this.menu = menu;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setAccessRight(List<Role> accessRight) {
			this.accessRight = accessRight;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public void setSubMenu(List<MenuItem> subMenu) {
			this.subMenu = subMenu;
		}

		public void setEntiryObject(List<ObjectBehaviour> entiryObject) {
			this.entiryObject = entiryObject;
		}
		
		
	}
	
	public static class Role{
		String name;
		public Role(String name){
			this.name = name;
		}
	}
	
	public  static class ObjectBehaviour{
		public Class object;
		public List<String> behavoiur;
	}
}
