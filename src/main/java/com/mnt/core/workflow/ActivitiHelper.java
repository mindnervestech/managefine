package com.mnt.core.workflow;

import java.io.InputStream;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;

public class ActivitiHelper {
	
	private static ActivitiHelper activitiHelper=null;
	private final ProcessEngine processEngine;
	
	public static enum WorkMap{
		TIMESHEET_APPROVAL("TimeSheetApproval"),
		VACATION_APPROVAL("VacationApproval"),
		FEEDBACK("feedback"),
		USER_REQUEST_APPROVAL("RequestApproval");
		
		private String key;
		private WorkMap(String key){
			this.key =key;
		}
		
		public String key(){
			return key;
		}
		
	}
	
	private ActivitiHelper(){
		processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();
	}
	
	
	public static ActivitiHelper me(){
		if(activitiHelper==null){
			activitiHelper = new ActivitiHelper();
		}
		return activitiHelper;
	}
	
	public String startProcessInstanceByKey(WorkMap wMap, Map<String,Object> inputMap){
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(wMap.key(), inputMap);
		return processInstance.getProcessInstanceId();
	}
	
	public String getTaskByKeyForInstance(String processInstanceId,String taskDefinitionKey){
		RuntimeService runtimeService = processEngine.getRuntimeService();
		TaskService taskService = processEngine.getTaskService();
		org.activiti.engine.task.Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskDefinitionKey(taskDefinitionKey).singleResult();
		return task.getId();
		
	}
	
	public void setVariableToTask(String processInstanceId,String taskDefinitionKey,Map<String,Object> inputMap){
		TaskService taskService = processEngine.getTaskService();
		taskService.complete(getTaskByKeyForInstance(processInstanceId,taskDefinitionKey),  inputMap);
	}
	
	public void doDeploy(){
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("TimeSheetApproval.bar");
		ZipInputStream inputStream = new ZipInputStream(is);
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment()
		.addZipInputStream(inputStream)
		//.addInputStream(WorkMap.TIMESHEET_APPROVAL.key(), is)
		.name(WorkMap.TIMESHEET_APPROVAL.key()).deploy();
	}
	
	public void doRequestDeploy(){
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("UserRequestApproval.bar");
		ZipInputStream inputStream = new ZipInputStream(is);
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment()
		.addZipInputStream(inputStream)
		//.addInputStream(WorkMap.TIMESHEET_APPROVAL.key(), is)
		.name(WorkMap.USER_REQUEST_APPROVAL.key()).deploy();
	}
	
	public void doVacationDeploy(){
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("VacationApproval.bar");
		ZipInputStream inputStream = new ZipInputStream(is);
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment()
		.addZipInputStream(inputStream)
		//.addInputStream(WorkMap.TIMESHEET_APPROVAL.key(), is)
		.name(WorkMap.VACATION_APPROVAL.key()).deploy();
	}
}
