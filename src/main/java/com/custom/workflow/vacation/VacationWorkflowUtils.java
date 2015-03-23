package com.custom.workflow.vacation;

import java.util.HashMap;
import java.util.Map;

import com.mnt.core.workflow.ActivitiHelper;
import com.mnt.core.workflow.ActivitiHelper.WorkMap;

public final class VacationWorkflowUtils {
	
	public final static String VACATION_ID = "VACATION_ID";
	
	public static String startVacationWF(String vacationId){
		Map<String,Object> inputMap = new HashMap<String,Object>();
		inputMap.put(VACATION_ID, vacationId);
		return ActivitiHelper.me().startProcessInstanceByKey(WorkMap.VACATION_APPROVAL, inputMap);
	}
	
	public static void setVariableToTask(String processIntanceID,boolean isApproved,String vacationId){
		Map<String,Object> inputMap = new HashMap<String,Object>();
		inputMap.put("isApproved", isApproved);
		inputMap.put(VACATION_ID, vacationId);
		ActivitiHelper.me().setVariableToTask(processIntanceID,"VacationLevelApproval", inputMap);
	}
}
