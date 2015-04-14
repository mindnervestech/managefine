package com.mnt.roleHierarchy.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import models.Company;
import models.User;

import com.mnt.orghierarchy.model.Organization;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
@Entity
public class Role extends Model{
	@Id
	private Long id;
	@Column(length=255)
	private String roleName;
	@Column(length=255)
	private String roleDescription;
	private Long parentId;
	@OneToOne
	private Company company;

	public static Finder<Long,Role> find = new Finder<Long,Role>(Long.class,Role.class);
	
	public static List<Role> getRoleList() {
		return find.all();

	}
	
	
	public static Role getRoleByName(String name) {
		return find.where().eq("roleName", name).findUnique();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	

	public Company getCompany() {
		return company;
	}


	public void setCompany(Company company) {
		this.company = company;
	}


	public static Role getRoleById(Long id) {
		// TODO Auto-generated method stub
		return find.byId(id);
	}
	

	public List<Role> getRoleByParentId(Long id) {
		// TODO Auto-generated method stub
		return find.where().eq("parentId", id).findList();
	}

	
	
}
