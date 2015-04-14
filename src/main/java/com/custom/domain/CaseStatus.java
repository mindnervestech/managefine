package com.custom.domain;

import com.mnt.core.domain.DomainEnum;

public enum CaseStatus implements DomainEnum {
		Assigned("Assigned"),
		Unassigned("Unassigned");
		private boolean uiHidden = false;
		@Override
		public boolean uiHidden() {
			return uiHidden;
		}
		private String forName;
		
		private CaseStatus(String forName){
			this.forName=forName;
		}
		
		public String getName(){
			return forName;
		}
}
