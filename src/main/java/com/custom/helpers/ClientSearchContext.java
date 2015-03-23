package com.custom.helpers;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.util.List;
import java.util.Map;

import models.Client;
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

public class ClientSearchContext extends ASearchContext<Client> {

private static ClientSearchContext searchContext = null;
	
	public static ClientSearchContext getInstance(){
		//if(searchContext == null){
			return new ClientSearchContext();
		//}
	}
	
	public String entityName(){
		return Client.ENTITY;
	}

	public ClientSearchContext() {
		super(Client.class,null);
	}
	
	public ClientSearchContext(Client c) {
		super(Client.class,c);
	}

	@Override
	public String searchUrl() {
		return routes.Clients.search.url;
	}
	
	@Override
	public String generateExcel() {
		return routes.Clients.excelReport.url;
	}

	@Override
	public String editUrl() {
		return routes.Clients.edit.url;
	}

	@Override
	public String createUrl() {
		return routes.Clients.create.url;
	}

	@Override
	public String deleteUrl() {
		return routes.Clients.delete.url;
	}
	
	@Override
	public HSSFWorkbook doExcel(DynamicForm form) {
		Expression exp =  super.doSearchExpression(form, SearchType.Like);

		String email = form.data().get("email");
		User user1 = User.findByEmail(email);
		
		Expression exp1 = Expr.eq("company.companyCode", user1.companyobject.getCompanyCode());
		
		List<Client> results =  exp == null ?Client.find.where().add(exp1).findList()
				:Client.find.where().add(exp).add(exp1).findList();
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
			count = Client.find.where().findRowCount();
		}else{
			count = Client.find.where().add(exp1).add(exp).findRowCount();
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
		List<Client> results =  exp == null ?Client.find.setFirstRow(start).setMaxRows(limit).where().add(exp1).findList()
				:Client.find.where().add(exp).add(exp1).setFirstRow(start).setMaxRows(limit).findList();
		List<GridViewModel.RowViewModel> rows = transform(results, toJqGridFormat()) ;
		GridViewModel gridViewModel = new GridViewModel(pageData, count, rows);
		return gridViewModel;
	}
	
	private  Function<Client,RowViewModel> toJqGridFormat() {
		return new Function<Client, RowViewModel>() {
			@Override
			public RowViewModel apply(Client client) {
				try {
					return new GridViewModel.RowViewModel((client.id).intValue(), newArrayList(getResultStr(client)));
				} catch (Exception e) {
					ExceptionHandler.onError(e);
				} 
				return null;
			}
		};
	}

	@Override
	public String showEditUrl() {
		return routes.Clients.showEdit.url;
	}
	
	@Override
	public Map<String, String> autoCompleteUrls() {
		return Client.autoCompleteAction;
	}

	@Override
	protected void buildButton() {
		//super.getButtonActions().add(new AddButton());
	}
	
	
}
