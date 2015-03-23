package com.custom.domain;

import com.mnt.core.domain.DomainEnum;

public enum Salutation implements DomainEnum{
	Mr("Mr"),
	Miss("Miss"),
	Mrs("Mrs");
	
	private String forName;
	private boolean uiHidden = false;
	@Override
	public boolean uiHidden() {
		return uiHidden;
	}
	private Salutation(String forName){
		this.forName=forName;
	}
	
	public String getName(){
		return forName;
	}
	
}
