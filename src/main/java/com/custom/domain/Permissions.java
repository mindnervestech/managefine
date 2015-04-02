package com.custom.domain;

import com.mnt.time.controller.routes;

public enum Permissions {
	Home(routes.Application.index.url,null,"Home"),
	
	Manage("#",null,"Manage"),
	ManageUser(routes.Users.index.url,Manage,"Users"),
	ManageClient(routes.Clients.index.url,Manage,"Customers"),
	ManageSupplier(routes.Suppliers.index.url,Manage,"Suppliers"),
	ManageProject(routes.Projects.index.url,Manage,"Projects"),
	ManageTask(routes.Tasks.index.url,Manage,"Tasks"),
	CreateProject(routes.Projects.createProject.url,Manage,"Create Project"),
	
	
	Leaves("#",null,"Leaves"),
	ApplyLeave(routes.Leaves.applyIndex.url,Leaves,"My Leaves"),
	
	Timesheet("#",null,"Timesheet"),
	CreateTimesheet(routes.Timesheets.index.url,Timesheet,"Create"),
	SearchTimesheet(routes.Timesheets.searchIndex.url,Timesheet,"History"),
	
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