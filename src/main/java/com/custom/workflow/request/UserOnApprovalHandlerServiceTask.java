package com.custom.workflow.request;

import java.util.HashMap;
import java.util.Map;

import models.User;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import com.mnt.workflow.timesheet.dto.MailWfObject;

public class UserOnApprovalHandlerServiceTask implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub

		Map<String, Object> map =new HashMap<String, Object>();
		String request_id = (String) execution.getVariable(AdminWorkFlowUtils.USER_REQUEST_ID);
		User user = User.find.byId(Long.parseLong(request_id));
		User admin = User.find.where().eq("designation","SuperAdmin").findUnique();
		MailWfObject mailWfObject = new MailWfObject();
		
		mailWfObject.to = user.email;
		mailWfObject.subject = "You are approved by"+admin.firstName;
		mailWfObject.body = "You Approval Request Has Been Sanctioned By "+admin.firstName;
		mailWfObject.body = String.format(mailWfObject.body);
		execution.setVariable("mailWfObject", mailWfObject);
		System.out.println("After Approval");
	}

}
