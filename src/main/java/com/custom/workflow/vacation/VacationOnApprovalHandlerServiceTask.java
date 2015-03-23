package com.custom.workflow.vacation;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import models.ApplyLeave;
import models.RoleLevel;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import com.custom.domain.LeaveStatus;
import com.mnt.workflow.timesheet.dto.MailWfObject;

public class VacationOnApprovalHandlerServiceTask implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

	Map<String, Object> map =new HashMap<String, Object>();
	String vacation_id = (String) execution.getVariable(VacationWorkflowUtils.VACATION_ID);
	System.out.println(vacation_id);
	ApplyLeave leave = ApplyLeave.find.where().eq("leaveGuid", vacation_id).findUnique();
	
	RoleLevel roleLevel = leave.getUser().getRole();
	MailWfObject mailWfObject = new MailWfObject();
		if(leave.getPendingWith().getDesignation().equals(roleLevel.getFinal_approval())){
			//update uuid here on approval
			leave.setLeaveGuid(UUID.randomUUID().toString());
			leave.setStatus(LeaveStatus.Approved);
			leave.setPendingWith(leave.getUser());
			leave.update();
			vacation_id = leave.getLeaveGuid();
			//Create mail subject and body
			mailWfObject.setTo(leave.getUser().getEmail());
			mailWfObject.setSubject(String.format("Leave Request Approve."));
			mailWfObject.setBody(String.format("Your leave request from  %s for %s has been Approved from all levels",
					leave.getStartDate(),leave.getNoOfDays())) ;
			map.put(VacationWorkflowUtils.VACATION_ID, vacation_id);
			map.put("isFinal", true);
			map.put("mailWfObject", mailWfObject);
			
		}else{
			//update uuid here on approval
			leave.setLeaveGuid(UUID.randomUUID().toString());
			leave.setPendingWith(leave.getPendingWith().getManager());
			leave.update();
			vacation_id = leave.getLeaveGuid();
			//Create mail subject and body
			mailWfObject.setTo(leave.getUser().getEmail());
			mailWfObject.setSubject(String.format("Leave Request Approval from %s",leave.getUser().getFirstName())) ;
			mailWfObject.setBody("Your leave request has approved from "+ leave.getUser().getFirstName() + " from "+leave.getStartDate()) ;
			
			map.put(VacationWorkflowUtils.VACATION_ID, vacation_id);
			map.put("isFinal", false);
			map.put("mailWfObject", mailWfObject);
		}
	execution.setVariables(map);
	}

}
