package com.custom.domain;

import com.mnt.core.domain.DomainEnum;

public class RoleDomain implements DomainEnum{

	public String mapName;
	public String displayName;
	public boolean isHidden;
	
	public RoleDomain(String mapName,String displayName,boolean isHidden) {
		this.mapName = mapName;
		this.displayName = displayName;
		this.isHidden = isHidden;
	}
	
	@Override
	public String getName() {
		return displayName;
	}

	@Override
	public String name() {
		return mapName;
	}

	@Override
	public boolean uiHidden() {
		return isHidden;
	}
	
	@Override
    public String toString() {
            return mapName;
    }

}
