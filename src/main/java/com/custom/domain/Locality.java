package com.custom.domain;

import com.mnt.core.domain.DomainEnum;

public enum Locality implements DomainEnum{
	Local("Local"),
	International("International");
	
	private String forName;
	private boolean uiHidden = false;
	@Override
	public boolean uiHidden() {
		return uiHidden;
	}
	private Locality(String forName){
		this.forName=forName;
	}
	
	public String getName(){
		return forName;
	}
	
}
