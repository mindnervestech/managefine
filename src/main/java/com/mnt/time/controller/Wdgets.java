package com.mnt.time.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.mnt.createProject.model.Projectinstance;
import com.mnt.createProject.model.Projectinstancenode;
import com.mnt.projectHierarchy.model.Projectclass;
import com.mnt.projectHierarchy.model.Projectclassnode;

@Controller
public class Wdgets {

	//========================================================================================
	@RequestMapping(value="/tasksForWidget" , method = RequestMethod.GET)
    @ResponseBody
	public List<TaskForWidgetVM> tasksForWidget(ModelMap model, @CookieValue("username") String username) {
		User user = User.findByEmail(username);
		System.out.println("=====tasksForWidget=======");
		List<TaskForWidgetVM> list = new ArrayList<>();
		//TODO Look into Projectinstancenode 
		
		list = TaskForWidgetVM.toDummy();
    	return list;
    }
	
	public static class TaskForWidgetVM {
		public String taskName;
		

		public String name;	
		public String startDate;
		public String endDate;
		public String status;
		public String percentage;
		
		
		public TaskForWidgetVM() {
			
		}
		public TaskForWidgetVM(String taskName, String name, String startDate,
				String endDate, String status, String percentage) {
			super();
			this.taskName = taskName;
			this.name = name;
			this.startDate = startDate;
			this.endDate = endDate;
			this.status = status;
			this.percentage = percentage;
			
		}
		
		public static List<TaskForWidgetVM> toDummy(){
			DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			
			List<Projectinstancenode> pList = Projectinstancenode.getProjectTaskList();
			List<TaskForWidgetVM> tsList = new ArrayList<Wdgets.TaskForWidgetVM>();
			
			for(Projectinstancenode pIntNode:pList){
				TaskForWidgetVM tVm = new TaskForWidgetVM();
				
				List<Projectclassnode> projectclassnode = Projectclassnode.getparentByprojectId(pIntNode.getProjectclassnode().getId());
				
				if(projectclassnode.size() == 0){
					
					Projectinstance projectinstance = Projectinstance.findById(pIntNode.getProjectinstanceid());
					tVm.name = projectinstance.getProjectName();
					tVm.taskName = pIntNode.getProjectclassnode().getProjectTypes();
					tVm.startDate = format.format(pIntNode.getStartDate());
					tVm.endDate = format.format(pIntNode.getEndDate());
					tVm.status = pIntNode.getStatus();
					tVm.percentage = String.valueOf(pIntNode.getTaskCompilation());
					tsList.add(tVm);
					
				}
				
			}
			
			return tsList;
			
		}
	}
	
	
	//========================================================================================
	
	@RequestMapping(value="/projectsForWidget" , method = RequestMethod.GET)
    @ResponseBody
	public List<ProjectForWidgetVM> projectsForWidget(ModelMap model, @CookieValue("username") String username) {
		List<ProjectForWidgetVM> list = new ArrayList<>();
		//TODO Look into Projectinstance
		list = ProjectForWidgetVM.toDummy(); 
    	return list;
    }
	
	public static class ProjectForWidgetVM {
		public String name;	
		public String startDate;
		public String endDate;
		public String status;
		public String percentage;
		
		ProjectForWidgetVM(){}
		
		public ProjectForWidgetVM(String name, String startDate,
				String endDate, String status, String percentage) {
			this.name = name;
			this.startDate = startDate;
			this.endDate = endDate;
			this.status = status;
			this.percentage = percentage;
			
		}
		
		public static List<ProjectForWidgetVM> toDummy(){
			
			List<Projectinstance> pList = Projectinstance.getProjectList();
			List<ProjectForWidgetVM> tsList = new ArrayList<Wdgets.ProjectForWidgetVM>();
			
			for(Projectinstance projectinstance:pList){
				ProjectForWidgetVM tVm = new ProjectForWidgetVM();
				tVm.name = projectinstance.getProjectName();
				tVm.startDate = projectinstance.getStartDate();
				tVm.endDate = projectinstance.getEndDate();
				tVm.status = projectinstance.getStatus();
				tVm.percentage = String.valueOf(projectinstance.getPercentage());
				tsList.add(tVm);
			}
			
			return tsList;
			
			
		}
		
	}
	
	
	//========================================================================================
	
	
	
	
	//========================================================================================
	
	
	public static List<GaugeForWidgetVM> getProjectForGauge(String username){
		User user = User.findByEmail(username);
		List<GaugeForWidgetVM> list = new ArrayList<>();
		list = GaugeForWidgetVM.toDummy();
    	return list;
	}
	
	public static class GaugeForWidgetVM {
		
		public String name;
		public int percent;
		
		public  GaugeForWidgetVM(){}
		public GaugeForWidgetVM(String name, int percent) {
			super();
			this.name = name;
			this.percent = percent;
		}
		
		
		public String getName() {
			return name;
		}
		public int getPercent() {
			return percent;
		}
		
		public static List<GaugeForWidgetVM> toDummy(){
			
			List<Projectinstance> pList = Projectinstance.getProjectList();
			List<GaugeForWidgetVM> gWList = new ArrayList<Wdgets.GaugeForWidgetVM>();
			
			for(Projectinstance projectinstance:pList){
				GaugeForWidgetVM gVm = new GaugeForWidgetVM();
				gVm.name = projectinstance.getProjectName();
				gVm.percent= projectinstance.getPercentage().intValue();
				gWList.add(gVm);
			}
			
			return gWList;
			
		}
		
	}
	
	//========================================================================================
	
	@RequestMapping(value="/salesFunnelForWidget" , method = RequestMethod.GET)
    @ResponseBody
	public List<List> saleFunnelForWidget(@RequestParam int projectType) {
		// TODO: do query on projectType 
		Long projectTypeId = Long.parseLong(String.valueOf(projectType));
		
		List<List> funnelMap = new ArrayList<List>();
		
		List<Projectclassnode> pList = Projectclassnode.getProjectAndLevel(projectTypeId,1);
		int a = 0;
		
		for(Projectclassnode projectclassnode:pList){
			 a = 0;
			 List map0 = new ArrayList<Object>();
			List<Projectinstance> projectinstance = Projectinstance.getProjectTypeById(projectTypeId);
			
			  map0.add(projectclassnode.getProjectTypes());
			   for(Projectinstance projectI:projectinstance){
				   Projectinstancenode projectinstancenode = Projectinstancenode.getProjectInprogressStatus(projectclassnode.getId(), projectI.getId(), "Inprogress");
				   if(projectinstancenode != null){
					   a = a + 1;		   		
				   }
				   
			    }
			   map0.add(a);
			funnelMap.add(map0);
		}
	
		
		
		
		return funnelMap;
		//[[],[]]
	}
	
	
	
	public static List<FunnelForWidgetVM> getProjectTypeForFunnel(String username){
		User user = User.findByEmail(username);
		List<FunnelForWidgetVM> list = new ArrayList<>();
		list = FunnelForWidgetVM.toDummy();
    	return list;
	}
	
	public static class FunnelForWidgetVM {
		
		public String name;
		public int id;
		
		public  FunnelForWidgetVM(){}
		public FunnelForWidgetVM(String name, int id) {
			super();
			this.name = name;
			this.id = id;
		}
		
		
		public String getName() {
			return name;
		}
		public int getId() {
			return id;
		}
		
		public static List<FunnelForWidgetVM> toDummy(){
			List<Projectclass> pList =  Projectclass.getProjectList();
			List<FunnelForWidgetVM> forWidgetVMs = new ArrayList<Wdgets.FunnelForWidgetVM>();
			
			for(Projectclass projectclass:pList){
				FunnelForWidgetVM fWidgetVM = new FunnelForWidgetVM();
				fWidgetVM.name = projectclass.getProjectTypes();
				fWidgetVM.id = projectclass.getId().intValue();
				forWidgetVMs.add(fWidgetVM);
			}
			
			return forWidgetVMs;
			
			/*return Lists.newArrayList(new FunnelForWidgetVM("Commercial Construction",60),
					new FunnelForWidgetVM("Retails",70), //project type name, Id
					new FunnelForWidgetVM("Auto-Manufacturing",50),
					new FunnelForWidgetVM("Civil Construction",90));*/
		}
		
		
		
	}
}
