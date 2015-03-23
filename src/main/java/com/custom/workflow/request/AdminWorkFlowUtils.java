package com.custom.workflow.request;

import java.util.HashMap;
import java.util.Map;

import com.mnt.core.workflow.ActivitiHelper;
import com.mnt.core.workflow.ActivitiHelper.WorkMap;

public final class AdminWorkFlowUtils {
	public final static String USER_REQUEST_ID = "USER_REQUEST_ID";
	public static String startUserRequestWF(String userReqId){
		Map<String,Object> inputMap = new HashMap<String,Object>();
		System.out.println(userReqId);
		inputMap.put(USER_REQUEST_ID, userReqId);
		return ActivitiHelper.me().startProcessInstanceByKey(WorkMap.USER_REQUEST_APPROVAL, inputMap);
		
	}
	
	public static void setVariableToTask(String processIntanceID,boolean isApproved,String reqId){
		Map<String,Object> inputMap = new HashMap<String,Object>();
		inputMap.put("isApproved", isApproved);
		inputMap.put(USER_REQUEST_ID, reqId);
		ActivitiHelper.me().setVariableToTask(processIntanceID,"LevelApproval", inputMap);
	}
}
