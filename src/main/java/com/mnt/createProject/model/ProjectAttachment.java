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

public class ProjectAttachment extends Model{
	
	@Id
	private Long id;
	private String docPath;
	private String docName;
	private Date docDate;
	private Long projectinstanceid;
	
	public static Finder<Long,ProjectAttachment> find = new Finder<Long,ProjectAttachment>(Long.class,ProjectAttachment.class);
	
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
	
	public static List<ProjectAttachment> getprojectinstanceById(Long id) {
		return find.where().eq("projectinstanceid", id).findList();
	}
	
	public static ProjectAttachment getProjectIds(Long id,Long pid) {
		// TODO Auto-generated method stub
		return find.where().add(Expr.and(Expr.eq("id", id), Expr.eq("projectinstanceid", pid))).findUnique();
		//return find.where().add(Expr.or(Expr.eq("id", id), Expr.eq("parentId", id))).findList();
	}
	public static ProjectAttachment getById(Long id) {
		return find.byId(id);
	}
}
