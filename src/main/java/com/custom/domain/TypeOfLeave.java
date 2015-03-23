package com.custom.domain;

import com.mnt.core.domain.DomainEnum;

public enum TypeOfLeave implements DomainEnum{
	All("All"),
	AnnualLeave("Casual Leave"),
	SickLeave("Sick Leave"),
	MaternityLeave("Maternity Leave");
	private boolean uiHidden = false;
	@Override
	public boolean uiHidden() {
		return uiHidden;
	}
	private String forName;
	
	private TypeOfLeave(String forName){
		this.forName=forName;
	}
	
	public String getName(){
		return forName;
	}
	
	
	
	
}
