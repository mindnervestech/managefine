package com.mnt.orghierarchy.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Organization extends Model {

	@Id
	public Long id;
	@Column(length=255)
	public String organizationName;
	@Column(length=255)
	public String organizationType;
	@Column(length=255)
	public String organizationLocation;
	@Column(length=255)
	public String organizationProfileUrl;
	public Long companyId;
	public Long parent;
	
	public static Finder<Long,Organization> find = new Finder<Long,Organization>(Long.class,Organization.class);
	
	public static Organization getOrganizationById(Long id) {
		return find.byId(id);
	}
	
	public static Organization findById(Long id) {
        return find.where().eq("id", id).findUnique();
    }

	public static List<Organization> getOrganizationByNameNId(Long companyId,String query) {
		return find.where().eq("companyId",companyId).ilike("organizationName", query+"%")
	       		.findList();
	}
	
	public static Organization getOrganizationByName(String name) {
		return find.where().eq("organizationName", name).findUnique();
	}
	
	public static List<Organization> getOrganizationsByParentId(Long parentId) {
		return find.where().eq("parent", parentId).findList();
	}

	public static List<Organization> getOrganizationsByCompanyId(Long companyId) {
		return find.where().eq("companyId", companyId).findList();
	}
	
	@Override
	public String toString() {
		return getOrganizationName() + " ("+getOrganizationType()+")";
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}

	public String getOrganizationLocation() {
		return organizationLocation;
	}

	public void setOrganizationLocation(String organizationLocation) {
		this.organizationLocation = organizationLocation;
	}

	public String getOrganizationProfileUrl() {
		return organizationProfileUrl;
	}

	public void setOrganizationProfileUrl(String organizationProfileUrl) {
		this.organizationProfileUrl = organizationProfileUrl;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}
	
	
}
