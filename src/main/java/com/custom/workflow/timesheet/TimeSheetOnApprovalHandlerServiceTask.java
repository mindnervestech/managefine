package com.custom.workflow.timesheet;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import models.RoleLevel;
import models.Timesheet;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import com.custom.domain.TimesheetStatus;
import com.mnt.workflow.timesheet.dto.MailWfObject;

public class TimeSheetOnApprovalHandlerServiceTask implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

	Map<String, Object> map =new HashMap<String, Object>();
	String timesheet_id = (String) execution.getVariable(TimesheetWorkflowUtils.TIMESHEET_ID);
	Timesheet ts = Timesheet.find.where().eq("tid", timesheet_id).findUnique();
	
	RoleLevel roleLevel = ts.getUser().getRole();
	MailWfObject mailWfObject = new MailWfObject();
	if(!"Admin".equals(ts.getTimesheetWith().getDesignation())){
		if(ts.getTimesheetWith().getRole().getRole_name().equals(roleLevel.getFinal_approval())){
			//update uuid here on approval
			ts.setTid(UUID.randomUUID().toString());
			ts.setStatus(TimesheetStatus.Approved);
			ts.update();
			timesheet_id = ts.getTid();
		//	System.out.println("in if::"+timesheet_id);
			//Create mail subject and body
			mailWfObject.setTo(ts.getUser().getEmail());
			mailWfObject.setSubject(String.format("You got Approval For Time Sheet for Week %s from %s has been Approved from all levels",
						ts.getWeekOfYear(),ts.getUser().getFirstName())) ;
			mailWfObject.setBody(String.format("You got Approval For Time Sheet for Week %s from %s has been Approved from all levels",
					ts.getWeekOfYear(),ts.getUser().getFirstName())) ;
			map.put(TimesheetWorkflowUtils.TIMESHEET_ID, timesheet_id);
			map.put("isFinal", true);
			map.put("mailWfObject", mailWfObject);
		}else{
			//update uuid here on approval
			ts.setTid(UUID.randomUUID().toString());
			ts.setTimesheetWith(ts.getTimesheetWith().getManager());
			
			ts.update();
			timesheet_id = ts.getTid();
			//Create mail subject and body
			mailWfObject.setTo(ts.getUser().getEmail());
			mailWfObject.setSubject(String.format("You got Approval For Time Sheet for Week %s from %s",ts.getWeekOfYear(),ts.getUser().getFirstName()));
			mailWfObject.setBody(String.format("You got Approval For Time Sheet for Week %s from %s",ts.getWeekOfYear(),ts.getUser().getFirstName()));;
			map.put(TimesheetWorkflowUtils.TIMESHEET_ID, timesheet_id);
			map.put("isFinal", false);
			map.put("mailWfObject", mailWfObject);
			
		}
	}else{
		ts.setTid(UUID.randomUUID().toString());
		ts.setStatus(TimesheetStatus.Approved);
		ts.setTimesheetWith(ts.getUser());
		ts.update();
		timesheet_id = ts.getTid();
//		System.out.println("in if::"+timesheet_id);
		//Create mail subject and body
		mailWfObject.setTo(ts.getUser().getEmail());

		mailWfObject.setSubject(String.format("You got Approval For Time Sheet for Week %s from %s",ts.getWeekOfYear(),ts.getUser().getFirstName()));
		mailWfObject.setBody(String.format("You got Approval For Time Sheet for Week %s from %s",ts.getWeekOfYear(),ts.getUser().getFirstName()));;


		map.put(TimesheetWorkflowUtils.TIMESHEET_ID, timesheet_id);
		map.put("isFinal", true);
		map.put("mailWfObject", mailWfObject);
	}
	execution.setVariables(map);
	}
}
