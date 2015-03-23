package com.custom.ui.search.helper;

import play.data.DynamicForm;

import com.mnt.core.helper.SearchUIHelper;
import com.mnt.core.ui.annotation.SearchColumnOnUI;
import com.mnt.core.ui.annotation.SearchFilterOnUI;
import com.mnt.core.utils.GridViewModel;
import com.mnt.time.controller.routes;

public class ProjectSearchUI extends SearchUIHelper {
	
	private String displayValue="Select Project";
	public ProjectSearchUI() {
		super(ProjectSearchContext.class);
	}
	
	public ProjectSearchUI(String displayValue) {
		super(ProjectSearchContext.class);
		this.displayValue = displayValue;
	}
	
	public static class ProjectSearchContext{
		
		@SearchFilterOnUI(label = "First Name")
		@SearchColumnOnUI(colName="First Name", rank=1)
		public String firstName;
		
		@SearchFilterOnUI(label = "Last Name")
		@SearchColumnOnUI(colName="Last Name", rank=2)
		public String lastName;
	}

	@Override
	public String searchUrl() {
		// TODO Auto-generated method stub
		return routes.ProjectSearch.search.url;
	}

	@Override
	public String triggerUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String entityName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GridViewModel doSearch(DynamicForm form) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void buildButton() {
		// TODO Auto-generated method stub

	}
}
