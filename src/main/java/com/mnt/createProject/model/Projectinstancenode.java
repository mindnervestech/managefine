package com.mnt.createProject.model;

import java.util.Date;
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
import com.mnt.projectHierarchy.model.Projectclassnode;
import com.mnt.projectHierarchy.model.Projectclassnodeattribut;
import com.mnt.roleHierarchy.model.Role;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
@Entity
public class Projectinstancenode extends Model{
	@Id
	private Long id;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Projectclassnode Projectclassnode;
	private Long projecttypeid;
	private Long projectinstanceid;
	private Date startDate;
	private Date endDate;
	private long taskCompilation;
	private int weightage;
	
	/*@OneToMany(cascade=CascadeType.ALL)
	private List<Saveattributes> saveattributes;*/
	

	public static Finder<Long,Projectinstancenode> find = new Finder<Long,Projectinstancenode>(Long.class,Projectinstancenode.class);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Projectclassnode getProjectclassnode() {
		return Projectclassnode;
	}

	public void setProjectclassnode(Projectclassnode projectclassnode) {
		Projectclassnode = projectclassnode;
	}

	
	public Long getProjecttypeid() {
		return projecttypeid;
	}

	public void setProjecttypeid(Long projecttypeid) {
		this.projecttypeid = projecttypeid;
	}

	public long getTaskCompilation() {
		return taskCompilation;
	}

	public void setTaskCompilation(long taskCompilation) {
		this.taskCompilation = taskCompilation;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getProjectinstanceid() {
		return projectinstanceid;
	}

	public void setProjectinstanceid(Long projectinstanceid) {
		this.projectinstanceid = projectinstanceid;
	}
	
	public int getWeightage() {
		return weightage;
	}

	public void setWeightage(int weightage) {
		this.weightage = weightage;
	}

	public static Projectinstancenode getById(Long id) {
		return find.byId(id);
	}

	public static List<Projectinstancenode> getprojectNodeById(Long id) {
		return find.where().eq("Projectclassnode.id", id).findList();
	}
	
	public static Projectinstancenode getProjectParentId(Long pnodeid,Long ptypeid) {
		// TODO Auto-generated method stub
		return find.where().add(Expr.and(Expr.eq("Projectclassnode.id", pnodeid), Expr.eq("projectinstanceid", ptypeid))).findUnique();
		//return find.where().add(Expr.or(Expr.eq("id", id), Expr.eq("parentId", id))).findList();
	}
	
	public static List<Projectinstancenode> getprojectinstanceById(Long id) {
		return find.where().eq("projectinstanceid", id).findList();
	}
	
	
}
