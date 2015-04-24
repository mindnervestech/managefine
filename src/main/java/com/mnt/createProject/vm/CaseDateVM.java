package com.mnt.createProject.vm;

import java.util.List;


public class CaseDateVM {
	
	public List<Case_flexiVM> caseFlexi;
	public List<ProjectCommentVM> comment;
	public List<Case_flexiVM> getCaseFlexi() {
		return caseFlexi;
	}
	public void setCaseFlexi(List<Case_flexiVM> caseFlexi) {
		this.caseFlexi = caseFlexi;
	}
	public List<ProjectCommentVM> getComment() {
		return comment;
	}
	public void setComment(List<ProjectCommentVM> comment) {
		this.comment = comment;
	}
	
	
}
