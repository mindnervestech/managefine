package com.custom.domain;

import com.mnt.core.domain.DomainEnum;

public enum RoleLevels implements DomainEnum{

	Level_1("Level 1"),
	Level_2("Level 2"),
	Level_3("Level 3"),
	Level_4("Level 4"),
	Level_5("Level 5"),
	Level_6("Level 6"),
	Level_7("Level 7"),
	Level_8("Level 8"),
	Level_9("Level 9"),
	Level_10("Level 10");

	private String forName;
	private boolean uiHidden = true;
	
	@Override
	public String getName() {
		return forName;
	}

	private RoleLevels(String forName){
		this.forName=forName;
	}
	
	@Override
	public boolean uiHidden() {
		return uiHidden;
	}
}
