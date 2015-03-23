package utils;

public class AvailablePermission {
	public String permissionName;
	public String permissionKey;
	public boolean state=false;
	public boolean parentPresent = false;
	public boolean urlPresent = false;
	
	public AvailablePermission(String permissionKey, String permission,boolean state,boolean parentPresent,boolean urlPresent){
		this.permissionName = permission;
		this.parentPresent = parentPresent;
		this.state = state;
		this.permissionKey = permissionKey;
		this.urlPresent = urlPresent;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionKey() {
		return permissionKey;
	}

	public void setPermissionKey(String permissionKey) {
		this.permissionKey = permissionKey;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public boolean isParentPresent() {
		return parentPresent;
	}

	public void setParentPresent(boolean parentPresent) {
		this.parentPresent = parentPresent;
	}

	public boolean isUrlPresent() {
		return urlPresent;
	}

	public void setUrlPresent(boolean urlPresent) {
		this.urlPresent = urlPresent;
	}
}
