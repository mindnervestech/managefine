package com.mnt.projectHierarchy.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.avaje.ebean.Expr;
import com.mnt.orghierarchy.model.Organization;
import com.mnt.roleHierarchy.model.Role;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
@Entity
public class Projectclassnode extends Model{
	@Id
	private Long id;
	@Column(length=255)
	private String projectTypes;
	@Column(length=255)
	private String projectDescription;
	private String projectColor;
	private Long parentId;
	private int level;
	/*@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Projectclassnodeattribut> attribut;*/
	@OneToOne
	private Projectclass projectId;
	

	public static Finder<Long,Projectclassnode> find = new Finder<Long,Projectclassnode>(Long.class,Projectclassnode.class);

	public Projectclass getProjectId() {
		return projectId;
	}

	public void setProjectId(Projectclass projectId) {
		this.projectId = projectId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getProjectTypes() {
		return projectTypes;
	}

	public void setProjectTypes(String projectTypes) {
		this.projectTypes = projectTypes;
	}
	
	
	/*public List<Projectclassnodeattribut> getAttribut() {
		return attribut;
	}

	public void setAttribut(List<Projectclassnodeattribut> attribut) {
		this.attribut = attribut;
	}*/

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	
	public String getProjectColor() {
		return projectColor;
	}

	public void setProjectColor(String projectColor) {
		this.projectColor = projectColor;
	}

	public static List<Projectclassnode> getProjectList() {
		return find.all();

	}
	
	public static Projectclassnode getProjectById(Long id) {
		return find.byId(id);
	}
	
	public static List<Projectclassnode> getprojectByprojectId(Long id) {
		return find.where().eq("projectId.id", id).findList();
	}
	
	public static List<Projectclassnode> getparentByprojectId(Long id) {
		return find.where().eq("parentId", id).findList();
	}
	
	public static List<Projectclassnode> getProjectByProId(Long id) {
		// TODO Auto-generated method stub
		return find.where().add(Expr.or(Expr.eq("id", id), Expr.eq("parentId", id))).findList();
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	
	/*public static Projectclass getRoleById(Long id) {
		// TODO Auto-generated method stub
		return find.byId(id);
	}*/

	/*public List<Projectclass> getRoleByParentId(Long id) {
		// TODO Auto-generated method stub
		return find.where().eq("parentId", id).findList();
	}*/

	
	
}
