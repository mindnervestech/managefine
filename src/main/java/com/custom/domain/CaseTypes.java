package com.custom.domain;

import com.mnt.core.domain.DomainEnum;

public enum CaseTypes implements DomainEnum {
		Tasks("Tasks"),
		Issues("Issues"),
		Risk("Risk");
		private boolean uiHidden = false;
		@Override
		public boolean uiHidden() {
			return uiHidden;
		}
		private String forName;
		
		private CaseTypes(String forName){
			this.forName=forName;
		}
		
		public String getName(){
			return forName;
		}
}
