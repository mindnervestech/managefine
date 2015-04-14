package com.mnt.createProject.vm;

import java.util.Date;

import javax.persistence.OneToOne;

import models.User;


public class ProjectCommentVM {
	
	public Long id;
	public String projectComment;
	public Long userId;
	public String userName;
	public Date commetDate;
	public Long projectinstanceid;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProjectComment() {
		return projectComment;
	}
	public void setProjectComment(String projectComment) {
		this.projectComment = projectComment;
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
	public Long getProjectinstanceid() {
		return projectinstanceid;
	}
	public void setProjectinstanceid(Long projectinstanceid) {
		this.projectinstanceid = projectinstanceid;
	}
	
	
	
	
		
}
