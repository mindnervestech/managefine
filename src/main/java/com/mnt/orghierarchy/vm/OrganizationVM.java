package com.mnt.orghierarchy.vm;

public class OrganizationVM {
	private Long id;
	private String organizationName;
	private String organizationType;
	private String organizationLocation;
	private Long parent;
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
	public Long getParent() {
		return parent;
	}
	public void setParent(Long parent) {
		this.parent = parent;
	}
	
}
