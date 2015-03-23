package com.custom.domain;

import com.mnt.core.domain.DomainEnum;

public enum Gender implements DomainEnum{
	Male("Male"),
	Female("Female");
	
	private String forName;
	private boolean uiHidden = false;
	@Override
	public boolean uiHidden() {
		return uiHidden;
	}
	private Gender(String forName){
		this.forName=forName;
	}
	
	public String getName(){
		return forName;
	}
}
