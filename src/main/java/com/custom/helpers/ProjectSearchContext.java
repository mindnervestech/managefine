package com.custom.helpers;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import models.Project;
import models.User;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import play.data.DynamicForm;
import utils.ExceptionHandler;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import com.google.common.base.Function;
import com.mnt.core.helper.ASearchContext;
import com.mnt.core.utils.GridViewModel;
import com.mnt.core.utils.GridViewModel.PageData;
import com.mnt.core.utils.GridViewModel.RowViewModel;
import com.mnt.time.controller.routes;


public class ProjectSearchContext extends ASearchContext<Project>{
	
	private static ProjectSearchContext searchContext = null;
	
	public static ProjectSearchContext getInstance(){
		//if(searchContext == null){
			return new ProjectSearchContext();
		//}
	}
	
	public String entityName(){
		return Project.ENTITY;
	}

	public ProjectSearchContext() {
		super(Project.class,null);
	}
	
	public ProjectSearchContext(Project p) {
		super(Project.class,p);
	}

	@Override
	public String generateExcel() {
		return routes.Projects.excelReport.url;
	}
	
	@Override
	public String searchUrl() {
		return routes.Projects.Search.url;
	}

	@Override
	public String editUrl() {
		return routes.Projects.edit.url;
	}

	@Override
	public String createUrl() {
		return routes.Projects.create.url;
	}

	@Override
	public String deleteUrl() {
		return routes.Projects.delete.url;
	}
	
	@Override
	public HSSFWorkbook doExcel(DynamicForm form) {
		Expression exp =  super.doSearchExpression(form, SearchType.Like);
		String email = form.data().get("email");
		User user1 = User.findByEmail(email);
		Expression exp1 = Expr.eq("companyObj.companyCode", user1.getCompanyobject().getCompanyCode());
		List<Project> result;  
		
		if(exp == null ){
			result = Project.find.where().add(exp1).findList();
		}else{
			result = Project.find.where().add(exp1).add(exp).findList();
		}
		try {
			 return super.getExcelExport(result);
		} catch (Exception e) {
			ExceptionHandler.onError(e);
		}
		return null;
	}
	
	public GridViewModel doSearch(DynamicForm form) {
		
		Expression exp =  super.doSearchExpression(form, SearchType.Like);
		int page = Integer.parseInt(form.get("page"));
		int limit = Integer.parseInt(form.get("rows"));
		GridViewModel.PageData pageData = new PageData(limit,
				page);
		
		String email = form.data().get("email");
		
		User user1 = User.findByEmail(email);
		Expression exp1 = Expr.eq("companyObj.companyCode", user1.getCompanyobject().getCompanyCode());
		
		int count = 0;
		if(exp == null ){
			count = Project.find.where().add(exp1).findRowCount();
		}else{
			count = Project.find.where().add(exp1).add(exp).findRowCount();
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
		List<Project> results =  exp == null ?Project.find.setFirstRow(start).setMaxRows(limit).where().add(exp1).findList()
				:Project.find.where().add(exp).add(exp1).setFirstRow(start).setMaxRows(limit).findList();
		List<GridViewModel.RowViewModel> rows = transform(results, toJqGridFormat()) ;
		GridViewModel gridViewModel = new GridViewModel(pageData, count, rows);
		return gridViewModel;
	}
	
	private  Function<Project,RowViewModel> toJqGridFormat() {
		return new Function<Project, RowViewModel>() {
			@Override
			public RowViewModel apply(Project project) {
				try {
					return new GridViewModel.RowViewModel((project.getId()).intValue(),newArrayList(
									project.getProjectName(),
									project.getProjectCode(),
									project.getProjectManager().toString(),
									new SimpleDateFormat("dd MMM yyyy").format(project.getStartDate()).toString(),
									new SimpleDateFormat("dd MMM yyyy").format(project.getEndDate()).toString()
									));
				} catch (Exception e) {
					ExceptionHandler.onError(e);
				} 
				return null;
			}
		};
	}

	//@Override
	public String showEditUrl() {
		return routes.Projects.showEdit.url;
	}
	
	@Override
	public Map<String, String> autoCompleteUrls() {
		return Project.autoCompleteAction;
	}

	@Override
	protected void buildButton() {
		// TODO Auto-generated method stub
		
	}
	
	

}
