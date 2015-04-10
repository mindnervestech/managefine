package com.mnt.employeeHierarchy.vm;

public class EmployeeHierarchyVM {
	private Long id;
	private String employeeName;
	private String designation;
	private Long parent;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public Long getParent() {
		return parent;
	}
	public void setParent(Long parent) {
		this.parent = parent;
	}
	
}
