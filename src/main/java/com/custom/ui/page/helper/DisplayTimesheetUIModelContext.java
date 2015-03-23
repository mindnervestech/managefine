package com.custom.ui.page.helper;

import com.custom.ui.page.proxy.DisplayTimesheetUIProxyModel;
import com.mnt.core.helper.ProxyUI;
import com.mnt.core.helper.UIModelHelper;
import com.mnt.core.ui.component.AddButton;

public class DisplayTimesheetUIModelContext extends UIModelHelper<DisplayTimesheetUIProxyModel>{

	public DisplayTimesheetUIModelContext(Class<? extends ProxyUI> model) {
		super(model);
	}

	public DisplayTimesheetUIModelContext(Class<? extends ProxyUI> object,ProxyUI valueObject) {
		super(object,valueObject);
	}
	
	@Override
	protected void buildButtons() {
		 super.getButtonActions().add(new AddButton());
	}

	@Override
	public String pageName() {
		return "timesheet";
	}

	@Override
	public String pageTitle() {
		return "TimeSheet";
	}

	@Override
	public String pageSubTitle() {
		return null;
	}

	
}
