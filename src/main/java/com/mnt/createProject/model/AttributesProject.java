package com.mnt.createProject.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.db.ebean.Model;
@Entity

public class AttributesProject extends Model{
	
	@Id
	private Long id;
	private String keyValue;
	private Boolean q1;
	private Boolean q2;
	private Boolean q3;
	private Boolean q4;
	@OneToOne
	private Projectinstance Projectinstance;
	
	public static Finder<Long,AttributesProject> find = new Finder<Long,AttributesProject>(Long.class,AttributesProject.class);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public Boolean getQ1() {
		return q1;
	}

	public void setQ1(Boolean q1) {
		this.q1 = q1;
	}

	public Boolean getQ2() {
		return q2;
	}

	public void setQ2(Boolean q2) {
		this.q2 = q2;
	}

	public Boolean getQ3() {
		return q3;
	}

	public void setQ3(Boolean q3) {
		this.q3 = q3;
	}

	public Boolean getQ4() {
		return q4;
	}

	public void setQ4(Boolean q4) {
		this.q4 = q4;
	}

	public Projectinstance getProjectinstance() {
		return Projectinstance;
	}

	public void setProjectinstance(Projectinstance projectinstance) {
		Projectinstance = projectinstance;
	}
	
	
	public static List<AttributesProject> getAttributByProject(Long projectId) {
		return find.where().eq("Projectinstance.id", projectId).findList();
	}
	
	
	
}
