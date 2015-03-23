package com.custom.workflow.vacation;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import models.ApplyLeave;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import com.custom.domain.LeaveStatus;
import com.mnt.workflow.timesheet.dto.MailWfObject;


public class VacationOnRejectionHandlerServiceTask implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Map<String, Object> map =new HashMap<String, Object>();
		String vacation_id = (String)execution.getVariable(VacationWorkflowUtils.VACATION_ID);
		ApplyLeave leave = ApplyLeave.find.where().eq("leaveGuid", vacation_id).findUnique();
		MailWfObject mailWfObject = new MailWfObject();
		
		//update uuid here on approval
		leave.setLeaveGuid(UUID.randomUUID().toString());
		vacation_id = leave.getLeaveGuid();
		
		mailWfObject.setTo(leave.getUser().getEmail());
		mailWfObject.setSubject("Your Leave Request as been rejected");
		mailWfObject.setBody("Your Leave Request as been rejected by has been rejected by  "+leave.getPendingWith().getFirstName());
		
		leave.setStatus(LeaveStatus.Rejected);
		leave.setLevel(0);
		leave.setPendingWith(leave.getUser());
		leave.update();
		
		map.put(VacationWorkflowUtils.VACATION_ID, vacation_id);
		map.put("mailWfObject", mailWfObject);
		
		execution.setVariables(map);
	}
	
	

}
