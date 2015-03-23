package com.custom.workflow.vacation;
import models.ApplyLeave;
import models.Delegate;
import models.User;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.joda.time.DateTime;

import com.mnt.workflow.timesheet.dto.MailWfObject;


public class VacationHandlerServiceTask implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String vacation_id = (String) execution.getVariable(VacationWorkflowUtils.VACATION_ID);
		ApplyLeave leave = ApplyLeave.find.where().eq("leaveGuid", vacation_id).findUnique();
		User manager = leave.getPendingWith();
		MailWfObject mailWfObject = new MailWfObject();  
		
		if(manager !=null){
			leave.setPendingWith(manager);
			leave.level++;
			mailWfObject = createMailObject(leave, manager);
			leave.setProcessInstanceId(execution.getProcessInstanceId());
			leave.update();
		}
		
		execution.setVariable("mailWfObject", mailWfObject);
	}
	
	public MailWfObject createMailObject(ApplyLeave leave,User manager){
		
		Delegate delegate = Delegate.find.where().eq("delegator_id", manager.id).findUnique();
		
		DateTime today = new DateTime();
		
		MailWfObject mailWfObject = new MailWfObject();
		
		mailWfObject.setTo(manager.getEmail() + ",");
		
		if(delegate != null && (today.isAfter(delegate.getFromDate().getTime()) && today.isBefore(delegate.getToDate().getTime()))){
			mailWfObject.to += delegate.getDelegatedTo().getEmail() ;
		}
		
		mailWfObject.setSubject(String.format("You got Leave Request  from %s",leave.getUser().getFirstName())) ;
		//leave.startDate,leave.endDate,
		
		mailWfObject.setBody("You have received Leave Request from "+ leave.getUser().getFirstName() + " from "+leave.getStartDate());
		
		mailWfObject.body = mailWfObject.getBody() +" \r\n "+ "To Approve click on below link ";
		mailWfObject.body = mailWfObject.getBody() + "\r\n http://time.mnt.cloudbees.net/leaveApprovalViaMail?q=yes&id="+leave.getLeaveGuid();
		mailWfObject.body = mailWfObject.getBody() + " \r\n "+ "To Reject click on below link ";
		mailWfObject.body = mailWfObject.getBody() + "\r\n  http://time.mnt.cloudbees.net/leaveApprovalViaMail?q=no&id="+leave.getLeaveGuid();
		mailWfObject.setBody(String.format(mailWfObject.getBody()));
		return mailWfObject;
	}

}
