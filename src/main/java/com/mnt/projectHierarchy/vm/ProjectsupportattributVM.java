package com.mnt.projectHierarchy.vm;

import java.util.List;

import javax.persistence.Column;


public class ProjectsupportattributVM {

	public Long parentId;
	public Long projectId;
	public String projectTypes;
	public String projectDescription;
	public String startDate;
	public String endDate;
	public String startDateLimit;
	public String endDateLimit;
	public String projectColor;
	public int level;
	public Long thisNodeId;
	public List<ProjectclassnodeattributVM> projectValue;
	
	
	public String getProjectTypes() {
		return projectTypes;
	}
	public void setProjectTypes(String projectTypes) {
		this.projectTypes = projectTypes;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public List<ProjectclassnodeattributVM> getProjectValue() {
		return projectValue;
	}
	public void setProjectValue(List<ProjectclassnodeattributVM> projectValue) {
		this.projectValue = projectValue;
	}
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	public String getProjectColor() {
		return projectColor;
	}
	public void setProjectColor(String projectColor) {
		this.projectColor = projectColor;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Long getThisNodeId() {
		return thisNodeId;
	}
	public void setThisNodeId(Long thisNodeId) {
		this.thisNodeId = thisNodeId;
	}
	public String getStartDateLimit() {
		return startDateLimit;
	}
	public void setStartDateLimit(String startDateLimit) {
		this.startDateLimit = startDateLimit;
	}
	public String getEndDateLimit() {
		return endDateLimit;
	}
	public void setEndDateLimit(String endDateLimit) {
		this.endDateLimit = endDateLimit;
	}
	
	
}
