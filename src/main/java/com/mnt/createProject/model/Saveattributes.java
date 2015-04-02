package com.mnt.createProject.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
@Entity
public class Saveattributes extends Model{
	@Id
	private Long id;
	//private Long projectclassnode;
	private String attributValue;
	private String attributName;
	private String type;
	private Long projectattrid;
	private Long projectinstancenode_id;

	public static Finder<Long,Saveattributes> find = new Finder<Long,Saveattributes>(Long.class,Saveattributes.class);

	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



/*

	public Long getProjectclassnode() {
		return projectclassnode;
	}



	public void setProjectclassnode(Long projectclassnode) {
		this.projectclassnode = projectclassnode;
	}
*/


	public String getAttributValue() {
		return attributValue;
	}



	public void setAttributValue(String attributValue) {
		this.attributValue = attributValue;
	}



	public Long getProjectinstancenode_id() {
		return projectinstancenode_id;
	}



	public void setProjectinstancenode_id(Long projectinstancenode_id) {
		this.projectinstancenode_id = projectinstancenode_id;
	}

	

	public String getAttributName() {
		return attributName;
	}



	public void setAttributName(String attributName) {
		this.attributName = attributName;
	}

	

	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public static Saveattributes getById(Long id) {
		return find.byId(id);
	}

	
	
	public Long getProjectattrid() {
		return projectattrid;
	}



	public void setProjectattrid(Long projectattrid) {
		this.projectattrid = projectattrid;
	}



	public static List<Saveattributes> getprojectNodeById(Long id) {
		return find.where().eq("projectinstancenode_id", id).findList();
	}
	
	public static Saveattributes getprojectattriById(Long id) {
		return find.where().eq("projectattrid", id).findUnique();
	}
	
}
