package com.mnt.createProject.vm;

import java.util.List;


public class CaseDateVM {
	
	public List<Case_flexiVM> caseFlexi;
	public List<CasesNotsAndAttVM> comment;
	
	public List<Case_flexiVM> getCaseFlexi() {
		return caseFlexi;
	}
	public void setCaseFlexi(List<Case_flexiVM> caseFlexi) {
		this.caseFlexi = caseFlexi;
	}
	public List<CasesNotsAndAttVM> getComment() {
		return comment;
	}
	public void setComment(List<CasesNotsAndAttVM> comment) {
		this.comment = comment;
	}
	
	
	
}
