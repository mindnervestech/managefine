package com.custom.domain;

import com.mnt.time.controller.routes;

public enum Permissions {
	Home(routes.Application.index.url,null,"Home"),
	
	Manage("#",null,"Manage"),
	ManageUser(routes.Users.index.url,Manage,"Users"),
	ManageClient(routes.Clients.index.url,Manage,"Customers"),
	ManageSupplier(routes.Suppliers.index.url,Manage,"Suppliers"),
	ManageProject(routes.Projects.index.url,Manage,"Manage Projects"),
	ManageTask(routes.Tasks.index.url,Manage,"Tasks"),
	EmployeeHierarchy(routes.Projects.employeeHierarchy.url,Manage,"Employee Hierarchy"),
	
	
	Leaves("#",null,"Leaves"),
	ApplyLeave(routes.Leaves.applyIndex.url,Leaves,"My Leaves"),
	
	Cases("#",null,"Case"),
	Case(routes.Cases.index.url,Cases,"Case"),
	
	Calendar("#",null,"Calendar"),
	CreateTimesheet(routes.Calendar.index.url,Calendar,"Create"),
	SearchTimesheet(routes.Calendar.searchIndex.url,Calendar,"History"),
	
	Timesheet("#",null,"Timesheet"),
	CreateTimesheets(routes.Timesheet.timesheetCreate.url,Timesheet,"Create"),
	
	Schedule("#",null,"Schedule"),
	Today(routes.Schedule.schedularToday.url,Schedule,"Today View"),
	Week(routes.Schedule.schedularWeek.url,Schedule,"Week View"),
	Month(routes.Schedule.schedularMonth.url,Schedule,"Month View"),
	Holiday(routes.Schedule.setupHoliday.url,Schedule,"Setup Holiday"),
	TodayAll(routes.Schedule.todayAll.url,Schedule,"Today All"),
	WeekReport(routes.Schedule.weekReport.url,Schedule,"Week Report"),
	
	Delegate(routes.Delegate.index.url,null,"Delegates"),
	
	Feedback("#",null,"Feedback"),
	FeedBackCreate(routes.Feedbacks.customIndex.url,Feedback,"Create"),
	FeedBackView(routes.Feedbacks.index.url,Feedback,"View"),
	
	PermissionsManagement("#",null,"Permission"),
	RolePermissions(routes.RolePermissions.index.url,PermissionsManagement,"By Roles"),
	UserPermissions(routes.UserPermissions.index.url,PermissionsManagement,"By Users"),
	
	ActionBox("#",null,"Actions"),
	UserRequest(routes.Status.userIndex.url,ActionBox,"On Users"),
	CompanyRequest(routes.Status.companyIndex.url,ActionBox,"On Company"),
	MyBucket(routes.TimesheetBuckets.index.url,ActionBox,"TimeSheet Requests"),
	LeaveBucket(routes.Leaves.bucketIndex.url,ActionBox,"Leaves Requests"),
	
	Report("#",null,"Report"),
	TeamRate(routes.Reports.teamRateIndex.url,Report,"Team Rate"),
	ProjectReport(routes.ProjectReports.projectReportIndex.url,Report,"ProjectReport"),

	Setting("#",null,"Settings"),
	Mail(routes.Mail.index.url,Setting,"Mail Setting"),
	Notification(routes.Notifications.index.url,Setting,"TimeSheet Notification"),
	DefineRoles(routes.Roles.defineRoles.url,Setting,"Define Roles"),
	DefineProjects(routes.Roles.defineProjects.url,Setting,"Define Projects"),
	DefineDepartments(routes.Roles.defineDepartment.url,Setting,"Define Department"),
	DefineLeaves(routes.Leaves.defineLeaves.url,Setting,"Define Leaves"),
	DefineFlexiAttribute(routes.FlexiAttribute.defineFlexiAttribute.url,Setting,"Define Flexi Attribute"),
	LeaveSettings(routes.Leaves.leaveSettings.url,Setting,"Leave Settings"),
	OrgHierarchy(routes.Roles.OrgHierarchy.url,Setting,"Org Hierarchy"),
	RoleHierarchy(routes.Roles.showRoles.url,Setting,"Role Hierarchy");
	
	private String url;
	private int level;
	private Permissions parent;
	private String uiDisplay;
	
	private Permissions(String _url,Permissions parent,String uiDisplay){
		this.url=_url;
		this.parent = parent;
		this.uiDisplay = uiDisplay;
	}
	
	public String url(){
		return url;
	}
	
	public int level(){
		return level;
	}
	
	public Permissions parent(){
		return parent;
	}
	
	public String display(){
		return uiDisplay;
	}
}