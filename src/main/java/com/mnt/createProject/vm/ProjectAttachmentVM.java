package com.mnt.createProject.vm;

import java.util.Date;


public class ProjectAttachmentVM {
	
	public Long id;
	public String docPath;
	public String docName;
	public Date docDate;
	public Long projectinstanceid;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDocPath() {
		return docPath;
	}
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public Date getDocDate() {
		return docDate;
	}
	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}
	public Long getProjectinstanceid() {
		return projectinstanceid;
	}
	public void setProjectinstanceid(Long projectinstanceid) {
		this.projectinstanceid = projectinstanceid;
	}
	
	
	
}
