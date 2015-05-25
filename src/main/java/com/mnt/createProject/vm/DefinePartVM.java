package com.mnt.createProject.vm;

import java.util.List;


public class DefinePartVM {
	
	public Long id;
	public String projectId;
	public String totalEstimatedRevenue;;
 	public List<PartVM> partsValue;
 	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public String getTotalEstimatedRevenue() {
		return totalEstimatedRevenue;
	}
	public void setTotalEstimatedRevenue(String totalEstimatedRevenue) {
		this.totalEstimatedRevenue = totalEstimatedRevenue;
	}
	public List<PartVM> getPartsValue() {
		return partsValue;
	}
	public void setPartsValue(List<PartVM> partsValue) {
		this.partsValue = partsValue;
	}
	
	
	
	
	
}
