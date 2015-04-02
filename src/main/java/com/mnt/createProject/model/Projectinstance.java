package com.mnt.createProject.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.avaje.ebean.Expr;
import com.mnt.orghierarchy.model.Organization;
import com.mnt.projectHierarchy.model.Projectclassnodeattribut;
import com.mnt.roleHierarchy.model.Role;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
@Entity
public class Projectinstance extends Model{
	@Id
	private Long id;
	private String projectTypes;
	@Column(length=255)
	private String projectDescription;
	private Long projectid;

	public static Finder<Long,Projectinstance> find = new Finder<Long,Projectinstance>(Long.class,Projectinstance.class);
	
	
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




	public String getProjectDescription() {
		return projectDescription;
	}




	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}




	public Long getProjectid() {
		return projectid;
	}




	public void setProjectid(Long projectid) {
		this.projectid = projectid;
	}




	public static Projectinstance getById(Long id) {
		return find.byId(id);
	}

	
}
