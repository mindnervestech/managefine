package com.custom.domain;

import com.mnt.core.domain.DomainEnum;

public enum Status implements DomainEnum {
		Submitted("Submitted"),
		PendingApproval("Pending Approval"),
		Approved("Approved"),
		Disapproved("Disapproved");
		private boolean uiHidden = false;
		@Override
		public boolean uiHidden() {
			return uiHidden;
		}
		private String forName;
		
		private Status(String forName){
			this.forName=forName;
		}
		
		public String getName(){
			return forName;
		}
}
