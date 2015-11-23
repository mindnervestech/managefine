package com.custom.domain;

import com.mnt.core.domain.DomainEnum;

public enum ProjectStatus implements DomainEnum{
	Inprogress("Inprogress"),
	Completed("Completed"),
	NotStarted("Not Started"),
	Paused("Paused"),
	Cancelled("Cancelled"),
	won("won"),
	lost("lost"),
	closed("closed");
	
	
	private boolean uiHidden = false;
	@Override
	public boolean uiHidden() {
		return uiHidden;
	}
	private String forName;
	
	private ProjectStatus(String forName){
		this.forName=forName;
	}
	
	public String getName(){
		return forName;
	}
}
