package com.custom.domain;

public class BlackListedPermissions {

	public static String BLACKLISTED_PERMISSIONS_FOR_SUPERADMIN = 
			"CompanyRequest|Mail";
	
	
	
	public static String BLACKLISTED_PERMISSIONS_FOR_ADMIN = 
			"Delegate|FeedBackCreate|FeedBackView|TeamRate|ProjectReport|Notification|" +
					"ApplyLeave|Leaves|Timesheet|CreateTimesheet|SearchTimesheet|Mail|"
					+ "UserRequest|DefineRoles|OrgHierarchy|UserPermissions|ManageUser|"
					+ "ManageProject|ManageTask|ManageClient|LeaveSettings|RolePermissions|DefineLeaves";
	
	

	public static String BLACKLISTED_PERMISSIONS_FOR_USERS = 
			"Delegate|FeedBackCreate|FeedBackView|" +
				"UserPermissions|UserRequest|MyBucket|LeaveBucket|TeamRate|ProjectReport|Notification|" +
					"DefineRoles|OrgHierarchy|ApplyLeave|Leaves|Timesheet|CreateTimesheet|SearchTimesheet|" +
						"RolePermissions";


}
