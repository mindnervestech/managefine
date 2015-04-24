package com.mnt.time.controller;

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
			return Lists.newArrayList(new TaskForWidgetVM("Scheduler","Time","01-04-2015","25-04-2015","InProgress","60%"),
					new TaskForWidgetVM("Timesheet","Time","01-04-2015","25-04-2015","InProgress","70%"),
					new TaskForWidgetVM("Leave","Time","01-04-2015","25-04-2015","InProgress","50%"),
					new TaskForWidgetVM("Dashboard","Time","01-04-2015","25-04-2015","InProgress","10%"));
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
			return Lists.newArrayList(new ProjectForWidgetVM("Time","01-04-2015","25-04-2015","InProgress","60%"),
					new ProjectForWidgetVM("Roque","01-03-2015","25-06-2015","InProgress","70%"),
					new ProjectForWidgetVM("Liabily","01-04-2015","25-04-2015","InProgress","50%"),
					new ProjectForWidgetVM("Mini Bean","01-04-2014","25-04-2015","InProgress","90%"));
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
			return Lists.newArrayList(new GaugeForWidgetVM("Time",60),
					new GaugeForWidgetVM("Roque",70),
					new GaugeForWidgetVM("Liabily",50),
					new GaugeForWidgetVM("Mini Bean",90));
		}
		
		
		
	}
	
	
	//========================================================================================
	
	@RequestMapping(value="/salesFunnelForWidget" , method = RequestMethod.GET)
    @ResponseBody
	public List<List> saleFunnelForWidget(@RequestParam int projectType) {
		// TODO: do query on projectType 
		List<List> funnelMap = new ArrayList<List>();
		List map0 = new ArrayList<>();
		map0.add("sales");
		map0.add(100);
		funnelMap.add(map0);
		
		List map1 = new ArrayList<>();
		map1.add("lead");map1.add(200L);
		funnelMap.add(map1);
		
		List map2 = new ArrayList<>();
		map2.add("development");map2.add(50L);
		funnelMap.add(map2);
		
		List map3 = new ArrayList<>();
		map3.add("testing");map3.add(2L);
		funnelMap.add(map3);
		
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
			return Lists.newArrayList(new FunnelForWidgetVM("Commercial Construction",60),
					new FunnelForWidgetVM("Retails",70),
					new FunnelForWidgetVM("Auto-Manufacturing",50),
					new FunnelForWidgetVM("Civil Construction",90));
		}
		
		
		
	}
}
