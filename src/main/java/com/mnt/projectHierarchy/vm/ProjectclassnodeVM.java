package com.mnt.projectHierarchy.vm;


public class ProjectclassnodeVM {
	public Long id;
	public String projectTypes;
	public String projectDescription;
	public String projectColor;
	public Long parentId;
	public Long projectId;
	public Long completed;
	public int level;
	public String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public Long getCompleted() {
		return completed;
	}
	public void setCompleted(Long completed) {
		this.completed = completed;
	}
	
	
	
}
