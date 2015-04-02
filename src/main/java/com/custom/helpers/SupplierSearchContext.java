package com.custom.helpers;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.util.List;
import java.util.Map;

import models.Supplier;
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

public class SupplierSearchContext extends ASearchContext<Supplier> {

private static SupplierSearchContext searchContext = null;
	
	public static SupplierSearchContext getInstance(){
		//if(searchContext == null){
			return new SupplierSearchContext();
		//}
	}
	
	public String entityName(){
		return Supplier.ENTITY;
	}

	public SupplierSearchContext() {
		super(Supplier.class,null);
	}
	
	public SupplierSearchContext(Supplier c) {
		super(Supplier.class,c);
	}

	@Override
	public String searchUrl() {
		return routes.Suppliers.search.url;
	}
	
	@Override
	public String generateExcel() {
		return routes.Suppliers.excelReport.url;
	}

	@Override
	public String editUrl() {
		return routes.Suppliers.edit.url;
	}

	@Override
	public String createUrl() {
		return routes.Suppliers.create.url;
	}

	@Override
	public String deleteUrl() {
		return routes.Suppliers.delete.url;
	}
	
	@Override
	public HSSFWorkbook doExcel(DynamicForm form) {
		Expression exp =  super.doSearchExpression(form, SearchType.Like);

		String email = form.data().get("email");
		User user1 = User.findByEmail(email);
		
		Expression exp1 = Expr.eq("company.companyCode", user1.companyobject.getCompanyCode());
		
		List<Supplier> results =  exp == null ?Supplier.find.where().add(exp1).findList()
				:Supplier.find.where().add(exp).add(exp1).findList();
		try {
			 return super.getExcelExport(results);
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
		Expression exp1 = Expr.eq("company.companyCode", user1.companyobject.getCompanyCode());
		
		int count =0;
		if(exp == null ){
			count = Supplier.find.where().findRowCount();
		}else{
			count = Supplier.find.where().add(exp1).add(exp).findRowCount();
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
		List<Supplier> results =  exp == null ?Supplier.find.setFirstRow(start).setMaxRows(limit).where().add(exp1).findList()
				:Supplier.find.where().add(exp).add(exp1).setFirstRow(start).setMaxRows(limit).findList();
		List<GridViewModel.RowViewModel> rows = transform(results, toJqGridFormat()) ;
		GridViewModel gridViewModel = new GridViewModel(pageData, count, rows);
		return gridViewModel;
	}
	
	private  Function<Supplier,RowViewModel> toJqGridFormat() {
		return new Function<Supplier, RowViewModel>() {
			@Override
			public RowViewModel apply(Supplier supplier) {
				try {
					return new GridViewModel.RowViewModel((supplier.id).intValue(), newArrayList(getResultStr(supplier)));
				} catch (Exception e) {
					ExceptionHandler.onError(e);
				} 
				return null;
			}
		};
	}

	@Override
	public String showEditUrl() {
		return routes.Suppliers.showEdit.url;
	}
	
	@Override
	public Map<String, String> autoCompleteUrls() {
		return Supplier.autoCompleteAction;
	}

	@Override
	protected void buildButton() {
		//super.getButtonActions().add(new AddButton());
	}
	
	
}
