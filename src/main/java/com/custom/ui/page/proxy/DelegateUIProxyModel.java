package com.custom.ui.page.proxy;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.OneToOne;

import models.User;
import play.data.format.Formats;

import com.mnt.core.helper.ProxyUI;
import com.mnt.core.ui.annotation.UIFields;
import com.mnt.core.ui.annotation.Validation;
import com.mnt.time.controller.routes;


public class DelegateUIProxyModel implements ProxyUI{
	
	
	@UIFields(order=1,label="From Date",row=1,col=1)
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date fromDate;
	
	
	@UIFields(order=2,label="To Date",row=1,col=2)
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date toDate;

	@Validation(required=true)
	@UIFields(order=3,label="Delegate To",row=1,col=3,autocomplete=true)
	@OneToOne
	public User delegateTo;
	
	

	@Override
	public String pageTitle() {
		return "Timesheet of ";
	}

	@Override
	public String pageSubTitle() {
		return "Last approved by";
	}

	public static Map<String,String> autoCompleteAction=new HashMap<String, String>();
	
	static {
		autoCompleteAction.put("Delegate To", routes.Delegate.index.url);
	}

}