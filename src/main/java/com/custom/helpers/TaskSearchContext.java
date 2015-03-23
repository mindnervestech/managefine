package com.custom.helpers;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import models.Task;
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

public class TaskSearchContext extends ASearchContext<Task>{
	
	private static TaskSearchContext searchContext = null;
	
	public static TaskSearchContext getInstance(){
		//if(searchContext == null){
			return  new TaskSearchContext();
		//}
	}
	
	public String entityName(){
		return Task.ENTITY;
	}

	public TaskSearchContext() {
		super(Task.class,null);
	}
	
	public TaskSearchContext(Task t) {
		super(Task.class,t);
	}

	@Override
	public String searchUrl() {
		return routes.Tasks.Search.url;
	}
	@Override
	public String generateExcel() {
		return routes.Tasks.excelReport.url;
	}

	@Override
	public String editUrl() {
		return routes.Tasks.edit.url;
	}

	@Override
	public String createUrl() {
		return routes.Tasks.create.url;
	}

	@Override
	public String deleteUrl() {
		return routes.Tasks.delete.url;
	}
	
	@Override
	public HSSFWorkbook doExcel(DynamicForm form) {
		Expression exp =  super.doSearchExpression(form, SearchType.Like);
		
		String email = form.data().get("email");
		User user1 = User.findByEmail(email);
		Expression exp1 = Expr.eq("projects.companyObj.companyCode", user1.companyobject.getCompanyCode());
		
		List<Task> results =  exp == null ?Task.find.where().add(exp1).findList()
				:Task.find.where().add(exp).add(exp1).findList();
		 
		SimpleDateFormat OrignaldateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date startTo,endFrom;
		List<Task> taskResult = new ArrayList<Task>(results.size());
		for(int i=0; i<results.size();i++)
		{
			Task task = new Task();
			task.id = results.get(i).id;
			task.taskName = results.get(i).taskName;
			task.taskCode = results.get(i).taskCode;
			startTo = results.get(i).startDate;
			String Date= OrignaldateFormat.format(startTo);
			task.startDateGrid = Date;
			endFrom = results.get(i).endDate;
			String Date1 = OrignaldateFormat.format(endFrom);
			task.endDateGrid = Date1;
			taskResult.add(task);
		}
		try {
			 return super.getExcelExport(taskResult);
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
		Expression exp1 = Expr.eq("projects.companyObj.companyCode", user1.companyobject.getCompanyCode());
		
		int count = 0;
		if(exp == null ){
			count = Task.find.where().add(exp1).findRowCount();
		}else{
			count = Task.find.where().add(exp).add(exp1).findRowCount();
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
		List<Task> results =  exp == null ?Task.find.setFirstRow(start).setMaxRows(limit).where().add(exp1).findList()
				:Task.find.where().add(exp).add(exp1).setFirstRow(start).setMaxRows(limit).findList();
		 
		SimpleDateFormat OrignaldateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date startTo,endFrom;
		List<Task> taskResult = new ArrayList<Task>(results.size());
		for(int i=0; i<results.size();i++)
		{
			Task task = new Task();
			task.id = results.get(i).id;
			task.taskName = results.get(i).taskName;
			task.taskCode = results.get(i).taskCode;
			startTo = results.get(i).startDate;
			String Date= OrignaldateFormat.format(startTo);
			task.startDateGrid = Date;
			endFrom = results.get(i).endDate;
			String Date1 = OrignaldateFormat.format(endFrom);
			task.endDateGrid = Date1;
			taskResult.add(task);
		}
		
		List<GridViewModel.RowViewModel> rows = transform(taskResult, toJqGridFormat()) ;
		GridViewModel gridViewModel = new GridViewModel(pageData, count, rows);
		return gridViewModel;
	}
	
	private  Function<Task,RowViewModel> toJqGridFormat() {
		return new Function<Task, RowViewModel>() {
			@Override
			public RowViewModel apply(Task task1) {
				try {
					return new GridViewModel.RowViewModel((task1.id).intValue(), newArrayList(getResultStr(task1)));
				} catch (Exception e) {
					ExceptionHandler.onError(e);
				} 
				return null;
			}
		};
	}

	@Override
	public String showEditUrl() {
		return routes.Tasks.showEdit.url;
	}
	
	@Override
	public Map<String, String> autoCompleteUrls() {
		return Task.autoCompleteAction;
	}

	@Override
	protected void buildButton() {
		// TODO Auto-generated method stub
		
	}
	
}
