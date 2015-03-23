package com.custom.ui.page.proxy;

import com.mnt.core.helper.ProxyUI;
import com.mnt.core.ui.annotation.UIFields;

public class DisplayTimesheetUIProxyModel implements ProxyUI{

	@UIFields(order=1,label="Employee ID",row=1,col=1,htmlAttrib="readonly")
	public String employeeId;
	
	@UIFields(order=2,label="Employee Name",row=1,col=2,htmlAttrib="readonly")
	public String employeeName;
	
	@UIFields(order=3,label="Week Number",row=1,col=3,htmlAttrib="readonly")
	public Integer weekOfYear;
	
	@UIFields(order=12,label="Timesheet ID",row=1,col=4,htmlAttrib="readonly",hidden=true)
	public Long timesheetID;	
		
	@UIFields(order=4,label="SUN",row=2,col=1,htmlAttrib="readonly")
	public Integer SUN;
	
	@UIFields(order=5,label="MON",row=2,col=2,htmlAttrib="readonly")
	public Integer MON;
	
	@UIFields(order=6,label="TUE",row=2,col=3,htmlAttrib="readonly")
	public Integer TUE;
	
	@UIFields(order=7,label="WED",row=2,col=4,htmlAttrib="readonly")
	public Integer WED;
	
	@UIFields(order=8,label="THU",row=2,col=5,htmlAttrib="readonly")
	public Integer THU;
	
	@UIFields(order=9,label="FRI",row=2,col=6,htmlAttrib="readonly")
	public Integer FRI;
	
	@UIFields(order=10,label="SAT",row=2,col=7,htmlAttrib="readonly")
	public Integer SAT;
	
	@UIFields(order=11,label="Total HRS",row=2,col=8,htmlAttrib="readonly")
	public Integer totalHrs;
	
	@UIFields(order=12,label="Overtime",row=2,col=9,htmlAttrib="readonly")
	public Boolean isOvertime;
	
	@Override
	public String pageTitle() {
		return "Timesheet of "+employeeName;
	}

	@Override
	 public String pageSubTitle() {
		return "";
	}

	
}
