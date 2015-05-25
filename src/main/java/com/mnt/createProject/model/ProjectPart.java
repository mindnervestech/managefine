package com.mnt.createProject.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import models.User;

import play.db.ebean.Model;
@Entity

public class ProjectPart extends Model{
	
	@Id
	private Long id;
	private String partNo;
	
	
	
	public static Finder<Long,ProjectPart> find = new Finder<Long,ProjectPart>(Long.class,ProjectPart.class);



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getPartNo() {
		return partNo;
	}



	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public static List<ProjectPart> getProjectPartNo() {
		return find.all();
	}
	public static ProjectPart findById(Long id) {
		return find.byId(id);
	}
	
}
