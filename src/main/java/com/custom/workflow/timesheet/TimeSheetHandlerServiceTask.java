package com.custom.workflow.timesheet;
import models.Delegate;
import models.Timesheet;
import models.TimesheetRow;
import models.User;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.joda.time.DateTime;

import com.mnt.workflow.timesheet.dto.MailWfObject;


public class TimeSheetHandlerServiceTask implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String timesheet_id = (String) execution.getVariable(TimesheetWorkflowUtils.TIMESHEET_ID);
		Timesheet ts = Timesheet.find.where().eq("tid", timesheet_id).findUnique();
		User manager = ts.getTimesheetWith();
		MailWfObject mailWfObject = new MailWfObject();  
		if(manager !=null){
			ts.level++;
			mailWfObject = createMailObject(ts, manager);
			ts.setProcessInstanceId(execution.getProcessInstanceId());
			ts.update();
		}
		
		execution.setVariable("mailWfObject", mailWfObject);
	}
	
	public MailWfObject createMailObject(Timesheet ts,User manager){
		
		Delegate delegate = Delegate.find.where().eq("delegator_id", manager.id).findUnique();
		
		DateTime today = new DateTime();
		
		MailWfObject mailWfObject = new MailWfObject();
		
		mailWfObject.setTo(manager.getEmail() + ",");
		
		if(delegate != null && (today.isAfter(delegate.getFromDate().getTime()) && today.isBefore(delegate.getToDate().getTime()))){
			mailWfObject.to += delegate.getDelegatedTo().getEmail() ;
		}
		
		mailWfObject.setSubject(String.format("You got Time Sheet for Week %s from %s",
				ts.getWeekOfYear(),ts.getUser().getFirstName()));
		
		mailWfObject.setBody("You have received Timesheet for Week "+ ts.getWeekOfYear() + " of Year "+ts.getYear() + " From "+ts.getUser().getFirstName());
		
		int totalHrs = 0;
		mailWfObject.body = mailWfObject.body + "\r\n " +
				"Proj. Code\t\tTask Code\t\tSun\t\tMon\t\tTue\t\tWed\t\tThur\t\tFri\t\tSat";
		for(TimesheetRow tsr : ts.timesheetRows){
			mailWfObject.body = mailWfObject.body + " \r\n " +
					tsr.projectCode+"\t\t\t"+tsr.taskCode+"\t\t\t"+tsr.sun+"\t\t\t"+tsr.mon+"\t\t\t"+tsr.tue+"\t\t\t"+tsr.wed+"\t\t\t"+tsr.thu+"\t\t\t"+tsr.fri+"\t\t\t"+tsr.sat;
			
			totalHrs += tsr.totalHrs;
		}
		mailWfObject.body = mailWfObject.body + "" +
				" \r\n "+"Total		"+totalHrs;
		
		mailWfObject.body = mailWfObject.body +" \r\n "+ "To Approve click on below link ";
		mailWfObject.body = mailWfObject.body + "\r\n http://time.mnt.cloudbees.net/timesheetApprovalViaMail?q=yes&id="+ts.tid;
		mailWfObject.body = mailWfObject.body + " \r\n "+ "To Reject click on below link ";
		mailWfObject.body = mailWfObject.body + "\r\n  http://time.mnt.cloudbees.net/timesheetApprovalViaMail?q=no&id="+ts.tid;
		mailWfObject.body = String.format(mailWfObject.body);
		return mailWfObject;
	}

}
