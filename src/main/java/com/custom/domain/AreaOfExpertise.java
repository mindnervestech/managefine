package com.custom.domain;

import com.mnt.core.domain.DomainEnum;

public enum AreaOfExpertise implements DomainEnum{
	Yes("Yes"),
	No("No");
	
	private String forName;
	private boolean uiHidden = false;
	@Override
	public boolean uiHidden() {
		return uiHidden;
	}
	private AreaOfExpertise(String forName){
		this.forName=forName;
	}
	
	public String getName(){
		return forName;
	}
}
