package com.custom.workflow.request;

import models.User;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import com.mnt.workflow.timesheet.dto.MailWfObject;

public class AdminHandlerServiceTask implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		String reqId = (String) execution.getVariable(AdminWorkFlowUtils.USER_REQUEST_ID);
		
		User admin = User.find.where().eq("designation", "SuperAdmin").findUnique();
		User user = User.find.byId(Long.parseLong(reqId));
		MailWfObject mailWfObject = new MailWfObject();
		System.out.println(admin.firstName);
		if(admin != null){
			System.out.println("In handler");
			mailWfObject = createMailObject(admin,user);
			user.processInstanceId = execution.getProcessInstanceId();
			user.update();
		}
		
		execution.setVariable("mailWfObject", mailWfObject);
		System.out.println("After mailWfObject");
	}
	
	public MailWfObject createMailObject(User admin,User user){
		
		MailWfObject mailWfObject = new MailWfObject();
		
		mailWfObject.to =  admin.email;
		
		mailWfObject.subject = String.format("Got Approval Request From "+user.firstName) ;
		
		mailWfObject.body = "You have received Approval Request From "+user.firstName;
		
		mailWfObject.body = String.format(mailWfObject.body);
		return mailWfObject;
	}

}
