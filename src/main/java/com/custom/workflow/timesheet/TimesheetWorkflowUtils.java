package com.custom.workflow.timesheet;

import java.util.HashMap;
import java.util.Map;

import com.mnt.core.workflow.ActivitiHelper;
import com.mnt.core.workflow.ActivitiHelper.WorkMap;

public final class TimesheetWorkflowUtils {
	public final static String TIMESHEET_ID = "TIMESHEET_ID";
	public static String startTimeSheetWF(String timesheetId){
		Map<String,Object> inputMap = new HashMap<String,Object>();
		inputMap.put(TIMESHEET_ID, timesheetId);
	//	System.out.println("inside timesheetflowutils starttimesheet");
		return ActivitiHelper.me().startProcessInstanceByKey(WorkMap.TIMESHEET_APPROVAL, inputMap);
		
	}
	
	public static void setVariableToTask(String processIntanceID,boolean isApproved,String tid){
		Map<String,Object> inputMap = new HashMap<String,Object>();
		inputMap.put("isApproved", isApproved);
		inputMap.put(TIMESHEET_ID, tid);
		ActivitiHelper.me().setVariableToTask(processIntanceID,"LevelApproval", inputMap);
	}
}
