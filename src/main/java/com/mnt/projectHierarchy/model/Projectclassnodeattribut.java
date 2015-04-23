package com.mnt.projectHierarchy.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

import com.avaje.ebean.Expr;
@Entity
public class Projectclassnodeattribut extends Model{
	@Id
	private Long id;
	@Column(length=255)
	private String name;
	private String type;
	private String value;
	@OneToOne
	private Projectclassnode projectnode;
	

	public static Finder<Long,Projectclassnodeattribut> find = new Finder<Long,Projectclassnodeattribut>(Long.class,Projectclassnodeattribut.class);

	


	public static List<Projectclassnodeattribut> getProjectList() {
		return find.all();

	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

	public Projectclassnode getProjectnode() {
		return projectnode;
	}

	public void setProjectnode(Projectclassnode projectnode) {
		this.projectnode = projectnode;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static Projectclassnodeattribut getProjectById(Long id) {
		return find.byId(id);
	}
	
	public static List<Projectclassnodeattribut> getattributByprojectId(Long id) {
		return find.where().eq("projectnode.id", id).findList();
	}
	
	public static List<Projectclassnodeattribut> getProjectByProId(Long id) {
		// TODO Auto-generated method stub
		return find.where().add(Expr.or(Expr.eq("id", id), Expr.eq("parentId", id))).findList();
	}
	
	
}
