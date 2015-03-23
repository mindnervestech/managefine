package com.custom.domain;

import com.mnt.core.domain.DomainEnum;

public enum Currency implements DomainEnum{
	INR("INR"),
	Euro("Euro"),
	Pound("Pound"),
	Dollar("Dollar");
	
	private String forName;
	private boolean uiHidden = false;
	@Override
	public boolean uiHidden() {
		return uiHidden;
	}
	private Currency(String forName){
		this.forName=forName;
	}
	
	public String getName(){
		return forName;
	}
}
