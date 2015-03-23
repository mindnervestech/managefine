package com.custom.ui.page.helper;

import com.custom.ui.page.proxy.DisplayTimesheetUIProxyModel;
import com.mnt.core.helper.ProxyUI;
import com.mnt.core.helper.UIModelHelper;
import com.mnt.core.ui.component.BuildUIButton;
import com.mnt.core.ui.component.UIButton.ButtonActionType;


public class CustomTimesheetUIModel extends UIModelHelper<DisplayTimesheetUIProxyModel>{

	public CustomTimesheetUIModel(Class<? extends ProxyUI> model) {
		super(model);
	}

	public CustomTimesheetUIModel(Class<? extends ProxyUI> object,ProxyUI valueObject) {
		super(object,valueObject);
	}
	
	@Override
	protected void buildButtons() {
		 super.getButtonActions().add(BuildUIButton.me().withID("approveButton").withLabel("Approve")
				 	.withTarget(ButtonActionType.SUBMIT).withUrl("approveTimesheets").withVisibilityTrue());
		 
		 super.getButtonActions().add(BuildUIButton.me().withID("rejectButton").withLabel("Reject")
				 .withTarget(ButtonActionType.SUBMIT).withUrl("rejectTimesheets").withVisibilityTrue());
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
