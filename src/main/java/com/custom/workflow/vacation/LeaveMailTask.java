package com.custom.workflow.vacation;

import models.ApplyLeave;
import models.Company;
import models.MailSetting;
import models.User;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import com.custom.emails.Email;
import com.mnt.workflow.timesheet.dto.MailWfObject;

public class LeaveMailTask implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		MailWfObject mailWfObject = (MailWfObject) execution.getVariable("mailWfObject");
		
		String leaves_id = (String) execution.getVariable(VacationWorkflowUtils.VACATION_ID);
		
		ApplyLeave leave = ApplyLeave.find.where().eq("leaveGuid", leaves_id).findUnique();
		User user = leave.getPendingWith();
		Company company = user.getCompanyobject();
		
		MailSetting mailSetting = MailSetting.find.where().eq("companyObject", company).findUnique();
	//	System.out.println(mailSetting.hostName);
	//	System.out.println(mailWfObject.to);
	//	System.out.println(mailWfObject.body);
		
		Email.sendOnlyMail(mailSetting, mailWfObject.getTo(), mailWfObject.getSubject(), mailWfObject.getBody());
//		String timesheet_id = (String) execution.getVariable(TimesheetWorkflowUtils.TIMESHEET_ID);
	}

}
