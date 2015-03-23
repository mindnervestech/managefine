package com.custom.helpers;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.util.List;

import models.RoleX;
import play.data.DynamicForm;
import utils.ExceptionHandler;

import com.avaje.ebean.Expression;
import com.google.common.base.Function;
import com.mnt.core.helper.ASearchContext;
import com.mnt.core.utils.GridViewModel;
import com.mnt.core.utils.GridViewModel.PageData;
import com.mnt.core.utils.GridViewModel.RowViewModel;

public class CustomRoleSearchContext extends ASearchContext<RoleX> {
	
	
private static CustomRoleSearchContext searchContext = null;
	
	public static CustomRoleSearchContext getInstance(){
		//if(searchContext == null){
		return new CustomRoleSearchContext();
	//	}
	}
	
	public String entityName(){
		return "Role";
	}

	public CustomRoleSearchContext() {
		super(RoleX.class,null);
	}
	public CustomRoleSearchContext(RoleX u) {
		super(RoleX.class,u);
	}
	
	
	@Override
	public String searchUrl() {
		return "/roleSearch	";
	}

	@Override
	public String editUrl() {
		return "/roleEdit";
	}

	@Override
	public String createUrl() {
		return "/roleCreate";
	}

	@Override
	public String deleteUrl() {
		return "/roleDelete";
	}

	@Override
	public GridViewModel doSearch(DynamicForm form) {
		Expression exp =  super.doSearchExpression(form, SearchType.Like);
		
		int page = Integer.parseInt(form.get("page"));
		int limit = Integer.parseInt(form.get("rows"));
		GridViewModel.PageData pageData = new PageData(limit,
				page);
		int count=0;
		
		if(exp == null ){
			count = RoleX.find.where().findRowCount();
		}else{
			count = RoleX.find.where().add(exp).findRowCount();
		}

		String sidx = form.get("sidx");
		String sord = form.get("sord");
		double min = Double.parseDouble(form.get("rows"));
		int total_pages=0;
		
		if(count > 0){
			total_pages = (int) Math.ceil(count/min);
		}
		else{
			total_pages = 0;
		}
		
		if(page > total_pages){
			page = total_pages;
		}
		
		int start = limit*page - limit;//orderBy(sidx+" "+sord)
		List<RoleX> results =  exp == null ?RoleX.find.setFirstRow(start).setMaxRows(limit).findList()
				:RoleX.find.where().add(exp).setFirstRow(start).setMaxRows(limit).findList();
		
		List<GridViewModel.RowViewModel> rows = transform(results, toJqGridFormat()) ;
		GridViewModel gridViewModel = new GridViewModel(pageData, count, rows);
		return gridViewModel;	}

	@Override
	public String showEditUrl() {
		return "/roleShowEdit";
	}

	private  Function<RoleX,RowViewModel> toJqGridFormat() {
		return new Function<RoleX, RowViewModel>() {
			@Override
			public RowViewModel apply(RoleX user) {
				try {
					return new GridViewModel.RowViewModel((user.id).intValue(), newArrayList(getResultStr(user)));
				} catch (Exception e) {
					ExceptionHandler.onError(e);
				} 
				return null;
			}
		};
	}
	
	/*@Override
	public Map<String, String> autoCompleteUrls() {
		return models.role.Role.autoCompleteAction;
	}
*/
	@Override
	protected void buildButton() {
		
	}
}

