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
import models.Project;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import play.data.DynamicForm;
import utils.ExceptionHandler;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import com.google.common.base.Function;
import com.mnt.core.helper.ASearchContext;
import com.mnt.core.ui.component.UIButton;
import com.mnt.core.utils.GridViewModel;
import com.mnt.core.utils.GridViewModel.PageData;
import com.mnt.core.utils.GridViewModel.RowViewModel;
import com.mnt.time.controller.routes;

public class LeaveApplyContext extends ASearchContext<ApplyLeave>{
private static LeaveApplyContext searchContext = null;
	
	public static LeaveApplyContext getInstance(){
		return new LeaveApplyContext();
	}
	
	public String entityName(){
		return ApplyLeave.ENTITY;
	}

	public LeaveApplyContext() {
		super(ApplyLeave.class,null);
	}
	
	public LeaveApplyContext(ApplyLeave p) {
		super(ApplyLeave.class,p);
	}

	@Override
	public String searchUrl() {
		return routes.Leaves.search.url;
	}

	@Override
	public String editUrl() {
		return routes.Leaves.edit.url;
	}

	@Override
	public String createUrl() {
		return routes.Leaves.create.url;
	}

	@Override
	public String deleteUrl() {
		return "#";
	}
	
	@Override
	public String showEditUrl() {
		return routes.Leaves.showEdit.url;
	}
	
	@Override
    public void buildButton() {
		
			super.getButtonActions().add(new UIButton() {
	              @Override
	              public boolean visibility() {
	                    return true;
	              }
	              @Override
	              public String url() {
	                    return "/leave/retractLeave";
	              }
	             
	              @Override
	              public ButtonActionType target() {
	                    return ButtonActionType.ACTION;
	              }
	             
	              @Override
	              public String label() {
	                    return "Retract Leave";
	              }
	             
	              @Override
	              public String id() {
	                    return "retractLeaveButton";
	              }
	           });
	}

	@Override
	public String generateExcel() {
		return routes.Leaves.excelApplyReport.url;
	}
	
	@Override
	public HSSFWorkbook doExcel(DynamicForm form) {
		Expression exp =  super.doSearchExpression(form, SearchType.Like);
		SimpleDateFormat OrignaldateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String email = form.data().get("email");
		List<ApplyLeave> results = new ArrayList<ApplyLeave>();
		if(form.get("status")=="ALL"){
			results = ApplyLeave.find.all();
		}else{
			results =  exp == null ?ApplyLeave.find.where().eq("user.email", email).findList()
				:ApplyLeave.find.where().add(exp).eq("user.email", email).findList();
		}
		Date startTo;
		List<ApplyLeave> leaveResult= new ArrayList<ApplyLeave>(results.size());
		for(int i=0; i<results.size();i++)
		{
			ApplyLeave leave = new ApplyLeave();
			leave.id = results.get(i).id;
			startTo = results.get(i).startDate;
			String startDate= OrignaldateFormat.format(startTo);
			leave.startDateGrid = startDate;
			leave.noOfDays = results.get(i).noOfDays;
			leave.typeOfLeave = results.get(i).typeOfLeave;
			leave.remarks = results.get(i).remarks;
			leave.status = results.get(i).status;
			leaveResult.add(leave);
			
		}
		try {
			 return super.getExcelExport(leaveResult);
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
			        exp = Expr.and(exp, Expr.between("startDate",fromDateFromUI, toDateFromUI));
        		}
        	}	
		} catch (ParseException e) {
			ExceptionHandler.onError(e);
		}
        int page = Integer.parseInt(form.get("page"));
		int limit = Integer.parseInt(form.get("rows"));
		GridViewModel.PageData pageData = new PageData(limit,
				page);
		
		int count = 0;
		String email = form.data().get("email");
		
		if(exp == null ){
			count = ApplyLeave.find.where().eq("user.email", email).findRowCount();
		}else{
			count = ApplyLeave.find.where().eq("user.email", email).add(exp).findRowCount();
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
		List<ApplyLeave> results = new ArrayList<ApplyLeave>();
		int start = limit*page - limit;//orderBy(sidx+" "+sord)
		if(form.get("status")=="ALL"){
			results = ApplyLeave.find.all();
		}else{
			results =  exp == null ?ApplyLeave.find.where().eq("user.email", email).setFirstRow(start).setMaxRows(limit).findList()
				:ApplyLeave.find.where().add(exp).eq("user.email", email).setFirstRow(start).setMaxRows(limit).findList();
		}
		Date startTo;
		List<ApplyLeave> leaveResult= new ArrayList<ApplyLeave>(results.size());
		for(int i=0; i<results.size();i++)
		{
			ApplyLeave leave = new ApplyLeave();
			leave.id = results.get(i).id;
			startTo = results.get(i).startDate;
			String startDate= OrignaldateFormat.format(startTo);
			leave.startDateGrid = startDate;
			leave.noOfDays = results.get(i).noOfDays;
			leave.typeOfLeave = results.get(i).typeOfLeave;
			leave.remarks = results.get(i).remarks;
			leave.status = results.get(i).status;
			leaveResult.add(leave);
			
		}
		List<GridViewModel.RowViewModel> rows = transform(leaveResult, toJqGridFormat()) ;
		GridViewModel gridViewModel = new GridViewModel(pageData, count, rows);
		return gridViewModel;
	}
	
	private  Function<ApplyLeave,RowViewModel> toJqGridFormat() {
		return new Function<ApplyLeave, RowViewModel>() {
			@Override
			public RowViewModel apply(ApplyLeave applyLeave) {
				try {
					return new GridViewModel.RowViewModel((applyLeave.id).intValue(), newArrayList(getResultStr(applyLeave)));
				} catch (Exception e) {
					ExceptionHandler.onError(e);
				} 
				return null;
			}
		};
	}
	
	@Override
	public Map<String, String> autoCompleteUrls() {
		return Project.autoCompleteAction;
	}

	
}