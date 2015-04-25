package com.mnt.createProject.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import models.Supplier;
import models.User;
import play.db.ebean.Model;

import com.avaje.ebean.Expr;
import com.mnt.projectHierarchy.model.Projectclassnode;
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
	private String projectManager;
	private String status;
	private String color;

	@ManyToMany(cascade=CascadeType.ALL)
	public List<User> user;
	
	@OneToOne
	private Supplier supplier;
	
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

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public static Projectinstancenode getById(Long id) {
		return find.byId(id);
	}

	public static List<Projectinstancenode> getprojectNodeById(Long id) {
		return find.where().eq("Projectclassnode.id", id).findList();
	}
	
	public static List<Projectinstancenode> getProjectTaskList() {
		return find.all();
	}
	
	public static Projectinstancenode getProjectParentId(Long pnodeid,Long ptypeid) {
		// TODO Auto-generated method stub
		return find.where().add(Expr.and(Expr.eq("Projectclassnode.id", pnodeid), Expr.eq("projectinstanceid", ptypeid))).findUnique();
		//return find.where().add(Expr.or(Expr.eq("id", id), Expr.eq("parentId", id))).findList();
	}
	
	public static List<Projectinstancenode> getprojectinstanceById(Long id) {
		return find.where().eq("projectinstanceid", id).findList();
	}
	
	public static List<Projectinstancenode> getProjectInstanceByIdAndType(Long id,Long typeId) {
		return find.where().eq("projectinstanceid", id).eq("projecttypeid", typeId).findList();
	}
	
	public static Projectinstancenode getByClassNodeAndInstance(Projectclassnode projectclassnode,Long instanceId) {
		return find.where().eq("Projectclassnode", projectclassnode).eq("projectinstanceid", instanceId).findUnique();
	}

	public void removeAllUser() {
		if(this.user == null)
			return;
		this.user.clear();
		this.deleteManyToManyAssociations("user");
	}
	
		public static Projectinstancenode getProjectInprogressStatus(Long pnodeid,Long ptypeid,String status) {
		return find.where().add(Expr.and(Expr.eq("Projectclassnode.id", pnodeid),Expr.and(Expr.eq("projectinstanceid", ptypeid), Expr.eq("status", status)))).findUnique();
	}

	
}
