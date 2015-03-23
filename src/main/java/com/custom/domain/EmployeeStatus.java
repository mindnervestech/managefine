package com.custom.domain;

import com.mnt.core.domain.DomainEnum;

public enum EmployeeStatus implements DomainEnum{
	OnRolls("Employee"),
	OffRolls("Non-Employee");
	
	private String forName;
	private boolean uiHidden = false;
	@Override
	public boolean uiHidden() {
		return uiHidden;
	}
	private EmployeeStatus(String forName){
		this.forName=forName;
	}
	
	public String getName(){
		return forName;
	}
}
