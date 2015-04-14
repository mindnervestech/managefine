package com.mnt.createProject.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import models.User;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

import com.avaje.ebean.Expr;
import com.custom.helpers.ProjectSearchContext;
import com.mnt.core.helper.SearchContext;
import com.mnt.core.ui.annotation.SearchColumnOnUI;
import com.mnt.core.ui.annotation.SearchFilterOnUI;
import com.mnt.core.ui.annotation.UIFields;
import com.mnt.core.ui.annotation.WizardCardUI;
import com.mnt.projectHierarchy.model.Projectclassnode;
import com.mnt.time.controller.routes;
@Entity

public class ProjectComment extends Model{
	
	@Id
	private Long id;
	private String projectComment;
	@OneToOne
	private User user;
	private Date commetDate;
	private Long projectinstanceid;
	
	public static Finder<Long,ProjectComment> find = new Finder<Long,ProjectComment>(Long.class,ProjectComment.class);

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public static List<ProjectComment> getprojectinstanceById(Long id) {
		return find.where().eq("projectinstanceid", id).findList();
	}
	
	
}
