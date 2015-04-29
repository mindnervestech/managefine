package com.mnt.createProject.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import models.Supplier;
import models.User;

import com.avaje.ebean.Ebean;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import scala.Array;

import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.SqlUpdate;
import com.custom.helpers.ProjectSearchContext;
import com.mnt.core.helper.SearchContext;
import com.mnt.core.ui.annotation.SearchColumnOnUI;
import com.mnt.core.ui.annotation.SearchFilterOnUI;
import com.mnt.core.ui.annotation.UIFields;
import com.mnt.core.ui.annotation.WizardCardUI;
import com.mnt.projectHierarchy.model.Projectclass;
import com.mnt.time.controller.routes;
@Entity

public class ProjectDateUserLogs extends Model{
	
	@Id
	private Long id;
	private Date oldStartDate;
	private Date oldEndDate;
	private Date newStartDate;
	private Date newEndDate;
	private Date changeDate;
	@OneToOne
	private Projectinstancenode projectinstancenode;
	@OneToOne
	private User user;
	
	public static Finder<Long,ProjectDateUserLogs> find = new Finder<Long,ProjectDateUserLogs>(Long.class,ProjectDateUserLogs.class);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getOldStartDate() {
		return oldStartDate;
	}

	public void setOldStartDate(Date oldStartDate) {
		this.oldStartDate = oldStartDate;
	}

	public Date getOldEndDate() {
		return oldEndDate;
	}

	public void setOldEndDate(Date oldEndDate) {
		this.oldEndDate = oldEndDate;
	}

	public Date getNewStartDate() {
		return newStartDate;
	}

	public void setNewStartDate(Date newStartDate) {
		this.newStartDate = newStartDate;
	}

	public Date getNewEndDate() {
		return newEndDate;
	}

	public void setNewEndDate(Date newEndDate) {
		this.newEndDate = newEndDate;
	}

	public Projectinstancenode getProjectinstancenode() {
		return projectinstancenode;
	}

	public void setProjectinstancenode(Projectinstancenode projectinstancenode) {
		this.projectinstancenode = projectinstancenode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
	
	
	
	
	
}
