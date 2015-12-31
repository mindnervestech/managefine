package com.custom.helpers;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import models.ApplyLeave;
import models.CaseData;
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

public class CaseSearchContext extends ASearchContext<CaseData> {

private static CaseSearchContext searchContext = null;
	
	public static CaseSearchContext getInstance(){
		//if(searchContext == null){
			return new CaseSearchContext();
		//}
	}
	
	public String entityName(){
		return CaseData.ENTITY;
	}

	public CaseSearchContext() {
		super(CaseData.class,null);
	}
	
	public CaseSearchContext(CaseData c) {
		super(CaseData.class,c);
	}

	@Override
	public String searchUrl() {
		return routes.Cases.search.url;
	}
	
	@Override
	public String generateExcel() {
		return routes.Cases.excelReport.url;
	}

	@Override
	public String editUrl() {
		return routes.Cases.edit.url;
	}

	@Override
	public String createUrl() {
		return routes.Cases.create.url;
	}

	@Override
	public String deleteUrl() {
		return routes.Cases.delete.url;
	}
	
	@Override
	public HSSFWorkbook doExcel(DynamicForm form) {
		Expression exp =  super.doSearchExpression(form, SearchType.Like);
		String email = form.data().get("email");
		User user1 = User.findByEmail(email);
		
		Expression exp1 = Expr.eq("company.companyCode", user1.companyobject.getCompanyCode());
		
		List<CaseData> results =  exp == null ?CaseData.find.where().add(exp1).findList()
				:CaseData.find.where().add(exp).add(exp1).findList();
		try {
			 return super.getExcelExport(results);
		} catch (Exception e) {
			ExceptionHandler.onError(e);
		}
		return null;
	}
		
	
	public GridViewModel doSearch(DynamicForm form) {
		
		Expression exp =  super.doSearchExpression(form, SearchType.Like);
		String fromDateFromUI = form.get("startDateWindow");
        String toDateFromUI = form.get("endDateWindow");
        SimpleDateFormat TargerdateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat OrignaldateFormat = new SimpleDateFormat("dd-MM-yyyy");
        
        try {
        	if(fromDateFromUI != null || toDateFromUI != null){
        		if(!(fromDateFromUI.equals("") && toDateFromUI.equals(""))){
					Date todate = OrignaldateFormat.parse(toDateFromUI);
					Date fromdate = OrignaldateFormat.parse(fromDateFromUI);
					fromDateFromUI= TargerdateFormat.format(fromdate);  
					toDateFromUI= TargerdateFormat.format(todate);  
			        exp =  Expr.between("dueDate",fromDateFromUI, toDateFromUI);
        		}
        	}	
		} catch (ParseException e) {
			ExceptionHandler.onError(e);
		}
		int page = Integer.parseInt(form.get("page"));
		int limit = Integer.parseInt(form.get("rows"));
		GridViewModel.PageData pageData = new PageData(limit,
				page);
		
		String email = form.data().get("email");
		
		User user1 = User.findByEmail(email);
		Expression exp1 = Expr.eq("company.companyCode", user1.companyobject.getCompanyCode());
		
		int count =0;
		if(exp == null ){
			count = CaseData.find.where().findRowCount();
		}else{
			count = CaseData.find.where().add(exp1).add(exp).findRowCount();
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
		List<CaseData> results =  exp == null ?CaseData.find.setFirstRow(start).setMaxRows(limit).where().add(exp1).setOrderBy("dueDate").findList()
				:CaseData.find.where().add(exp).add(exp1).setOrderBy("dueDate").setFirstRow(start).setMaxRows(limit).findList();
		
		
		Date startTo;
		List<CaseData> caseResult= new ArrayList<CaseData>(results.size());
		for(int i=0; i<results.size();i++)
		{
			CaseData case1 = new CaseData();
			case1.id = results.get(i).id;
			startTo = results.get(i).getDueDate();
			String startDate= OrignaldateFormat.format(startTo);
			case1.startDateGrid = startDate;
			case1.assignto = results.get(i).assignto;
			case1.title = results.get(i).title;
			case1.description = results.get(i).description;
			case1.status = results.get(i).status;
			caseResult.add(case1);
			
		}
		
		
		List<GridViewModel.RowViewModel> rows = transform(caseResult, toJqGridFormat()) ;
		GridViewModel gridViewModel = new GridViewModel(pageData, count, rows);
		return gridViewModel;
	}
	
	private  Function<CaseData,RowViewModel> toJqGridFormat() {
		return new Function<CaseData, RowViewModel>() {
			@Override
			public RowViewModel apply(CaseData cas) {
				try {
					return new GridViewModel.RowViewModel((cas.id).intValue(), newArrayList(getResultStr(cas)));
				} catch (Exception e) {
					ExceptionHandler.onError(e);
				} 
				return null;
			}
		};
	}

	@Override
	public String showEditUrl() {
		return routes.Cases.showEdit.url;
	}
	
	@Override
	public Map<String, String> autoCompleteUrls() {
		return CaseData.autoCompleteAction;
	}

	@Override
	protected void buildButton() {
		//super.getButtonActions().add(new AddButton());
	}
	
	
}
