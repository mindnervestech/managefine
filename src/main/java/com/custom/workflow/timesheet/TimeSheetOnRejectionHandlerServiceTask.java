package com.custom.workflow.timesheet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import models.Timesheet;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import com.custom.domain.TimesheetStatus;
import com.mnt.workflow.timesheet.dto.MailWfObject;


public class TimeSheetOnRejectionHandlerServiceTask implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Map<String, Object> map =new HashMap<String, Object>();
		String timesheet_id = (String)execution.getVariable(TimesheetWorkflowUtils.TIMESHEET_ID);
		Timesheet ts = Timesheet.find.where().eq("tid", timesheet_id).findUnique();
		MailWfObject mailWfObject = new MailWfObject();
		
		//update uuid here on approval
		ts.setTid(UUID.randomUUID().toString());
		timesheet_id = ts.getTid();
		
		mailWfObject.setTo(ts.getUser().getEmail());
		mailWfObject.setSubject("Your Time Sheet for Week"+ts.getWeekOfYear()+ " has been rejected by  "+ts.getTimesheetWith().getFirstName());
		mailWfObject.setBody("Your Time Sheet for Week"+ts.getWeekOfYear()+ " has been rejected by  "+ts.getTimesheetWith().getFirstName());
//		mailWfObject.to=timeSheetWFObj.user;
//		mailWfObject.subject = "Your Time Sheet for Week {?} has been rejected by  " + timeSheetWFObj.statusWith;
		ts.setStatus(TimesheetStatus.Rejected);
		ts.level = 0;
		ts.setTimesheetWith(ts.getUser());
		ts.update();
		
		map.put(TimesheetWorkflowUtils.TIMESHEET_ID, timesheet_id);
		map.put("mailWfObject", mailWfObject);
		
		execution.setVariables(map);
	}
	
	

}
