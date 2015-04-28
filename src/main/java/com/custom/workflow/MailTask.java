package com.custom.workflow;

import models.Company;
import models.MailSetting;
import models.Timesheet;
import models.User;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import com.custom.emails.Email;
import com.custom.workflow.timesheet.TimesheetWorkflowUtils;
import com.mnt.workflow.timesheet.dto.MailWfObject;

public class MailTask  implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		MailWfObject mailWfObject = (MailWfObject) execution.getVariable("mailWfObject");
		
		String timesheet_id = (String) execution.getVariable(TimesheetWorkflowUtils.TIMESHEET_ID);
		Timesheet ts = Timesheet.find.where().eq("tid", timesheet_id).findUnique();
		User user = ts.getTimesheetWith();
		Company company = user.getCompanyobject();
		
		MailSetting mailSetting = MailSetting.find();
		
		Email.sendOnlyMail(mailSetting, mailWfObject.getTo(), mailWfObject.getSubject(), mailWfObject.getBody());
	}

}
