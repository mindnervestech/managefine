package com.mnt.createProject.vm;

import java.util.List;


public class AttributDataVM {
	
	public List<AttributVM> projectAtt;
	public Long currentParentId;;
 	public Long MainInstance;
	
 	
	public List<AttributVM> getProjectAtt() {
		return projectAtt;
	}
	public void setProjectAtt(List<AttributVM> projectAtt) {
		this.projectAtt = projectAtt;
	}
	public Long getCurrentParentId() {
		return currentParentId;
	}
	public void setCurrentParentId(Long currentParentId) {
		this.currentParentId = currentParentId;
	}
	public Long getMainInstance() {
		return MainInstance;
	}
	public void setMainInstance(Long mainInstance) {
		MainInstance = mainInstance;
	}
 	
	
	
	
}
