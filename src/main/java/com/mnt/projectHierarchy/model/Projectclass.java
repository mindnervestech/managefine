package com.mnt.projectHierarchy.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.mnt.orghierarchy.model.Organization;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
@Entity
public class Projectclass extends Model{
	@Id
	private Long id;
	@Column(length=255)
	private String projectTypes;
	@Column(length=255)
	private String projectDescription;

	public static Finder<Long,Projectclass> find = new Finder<Long,Projectclass>(Long.class,Projectclass.class);
	
	

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

	public static List<Projectclass> getProjectList() {
		return find.all();
	}
	
	public static Projectclass getProjectById(Long id) {
		return find.byId(id);
	}
	
	public static Projectclass getProjectByName(String name) {
		return find.where().eq("projectTypes", name).findUnique();
	}
	
	public static Projectclass getRoleById(Long id) {
		// TODO Auto-generated method stub
		return find.byId(id);
	}
/*
	public List<Projectclass> getRoleByParentId(Long id) {
		// TODO Auto-generated method stub
		return find.where().eq("parentId", id).findList();
	}
*/
	
	
}
