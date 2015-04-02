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

	/*public List<Saveattributes> getSaveAttributes() {
		return saveattributes;
	}

	public void setSaveAttributes(List<Saveattributes> saveAttributes) {
		this.saveattributes = saveAttributes;
	}*/
	

	public static Projectinstancenode getById(Long id) {
		return find.byId(id);
	}

	public static List<Projectinstancenode> getprojectNodeById(Long id) {
		return find.where().eq("Projectclassnode.id", id).findList();
	}
	
	public static Projectinstancenode getProjectParentId(Long pnodeid,Long ptypeid) {
		// TODO Auto-generated method stub
		return find.where().add(Expr.and(Expr.eq("Projectclassnode.id", pnodeid), Expr.eq("projecttypeid", ptypeid))).findUnique();
		//return find.where().add(Expr.or(Expr.eq("id", id), Expr.eq("parentId", id))).findList();
	}
	
	
}
