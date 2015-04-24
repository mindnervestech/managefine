package com.mnt.createProject.vm;

import java.util.Date;
import java.util.List;

import javax.persistence.OneToOne;

import models.User;


public class CasesNotsAndAttVM {
	
	public Long id;
	public String caseComment;
	public String caseId;
	public Long userId;
	public String userName;
	public Date commetDate;
	public List<ProjectAttachmentVM> projectAtt;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCaseComment() {
		return caseComment;
	}
	public void setCaseComment(String caseComment) {
		this.caseComment = caseComment;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCommetDate() {
		return commetDate;
	}
	public void setCommetDate(Date commetDate) {
		this.commetDate = commetDate;
	}
	public List<ProjectAttachmentVM> getProjectAtt() {
		return projectAtt;
	}
	public void setProjectAtt(List<ProjectAttachmentVM> projectAtt) {
		this.projectAtt = projectAtt;
	}
	
	
		
}
