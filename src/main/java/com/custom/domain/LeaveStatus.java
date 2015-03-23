package com.custom.domain;

import com.mnt.core.domain.DomainEnum;

public enum LeaveStatus implements DomainEnum{
	Submitted("Submitted"),
	Rejected("Rejected"),
	Approved("Approved"),
	Withdrawn("Withdrawn");
	
	private boolean uiHidden = false;
	@Override
	public boolean uiHidden() {
		return uiHidden;
	}
	private String forName;
	
	private LeaveStatus(String forName){
		this.forName=forName;
	}
	
	public String getName(){
		return forName;
	}
}
