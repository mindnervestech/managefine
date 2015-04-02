package com.mnt.projectHierarchy.vm;

import java.util.List;

import javax.persistence.Column;


public class ProjectclassnodeattributVM {

	public Long id;
	public String name;
	public String type;
	public String value;
	public String attriValue;
	public List<ProjectattributSelect> valueSlice;
	public List<String> checkBoxValue;
	public Long projectnode;
	
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
	public Long getProjectnode() {
		return projectnode;
	}
	public void setProjectnode(Long projectnode) {
		this.projectnode = projectnode;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
	public List<ProjectattributSelect> getValueSlice() {
		return valueSlice;
	}
	public void setValueSlice(List<ProjectattributSelect> valueSlice) {
		this.valueSlice = valueSlice;
	}
	public String getAttriValue() {
		return attriValue;
	}
	public void setAttriValue(String attriValue) {
		this.attriValue = attriValue;
	}
	public List<String> getCheckBoxValue() {
		return checkBoxValue;
	}
	public void setCheckBoxValue(List<String> checkBoxValue) {
		this.checkBoxValue = checkBoxValue;
	}
	
	
		
	
	
}
