package com.custom.helpers;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.Timesheet;
import models.User;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.DateTime;

import play.data.DynamicForm;
import utils.ExceptionHandler;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import com.custom.domain.TimesheetStatus;
import com.google.common.base.Function;
import com.mnt.core.helper.ASearchContext;
import com.mnt.core.ui.component.BuildUIButton;
import com.mnt.core.ui.component.UIButton;
import com.mnt.core.utils.GridViewModel;
import com.mnt.core.utils.GridViewModel.PageData;
import com.mnt.core.utils.GridViewModel.RowViewModel;
import com.mnt.time.controller.routes;

public class TimesheetSearchContext extends ASearchContext<Timesheet>{
	
	public static TimesheetSearchContext getInstance(){
		return new TimesheetSearchContext();
	}
	
	public String entityName(){
		return Timesheet.ENTITY;
	}

	public TimesheetSearchContext() {
		super(Timesheet.class,null);
	}
	
	public TimesheetSearchContext(Timesheet p) {
		super(Timesheet.class,p);
	}
	
	@Override
	public UIButton showAddButton(){
		return BuildUIButton.me();
	}
	
	@Override
	public UIButton showEditButton(){
		return new UIButton() {
			
			@Override
			public boolean visibility() {
				return true;
			}
			
			@Override
			public String url() {
				return "/timesheetEdit";
			}
			
			@Override
			public ButtonActionType target() {
				return ButtonActionType.NEW;
			}

			@Override
			public String label() {
				// TODO Auto-generated method stub
				return "Edit Selected";
			}

			@Override
			public String id() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

	@Override
	public String searchUrl() {
		return routes.Timesheets.search.url;
	}

	@Override
	public String editUrl() {
		return "";
	}

	@Override
	public String createUrl() {
		return "#";
	}
	@Override
	public String generateExcel() {
		return routes.Timesheets.excelReport.url;
	}
	
	@Override
	public String deleteUrl() {
		return "#";
	}

	@Override
	public String showEditUrl() {
		return "";
	}

	
	
	@Override
	public HSSFWorkbook doExcel(DynamicForm form) {
		Expression exp = null;
		if(form.get("status") != null){
			exp = Expr.eq("status", TimesheetStatus.valueOf(form.get("status")).ordinal());
		}else{
			exp = Expr.eq("status", TimesheetStatus.Draft);
		}
		try {
			if(form.data().get("fromWeekWindow") != null  && form.data().get("toWeekWindow") != null
					&& !"".equals(form.data().get("fromWeekWindow"))  && !"".equals(form.data().get("toWeekWindow"))){
				int fromWeekFromUI = getWeekNumber(form.data().get("fromWeekWindow"));
				int toWeekFromUI = getWeekNumber(form.data().get("toWeekWindow"));
				if(fromWeekFromUI != 0 && toWeekFromUI != 0){
					exp = Expr.and(exp, Expr.between("weekOfYear",fromWeekFromUI, toWeekFromUI));
	        	}
			}
		} catch (ParseException e) {
			ExceptionHandler.onError(e);
		}
		List<Timesheet> results =  exp == null ?Timesheet.find.findList()
				:Timesheet.find.where().add(exp).findList();
		try {
			 return super.getExcelExport(results);
		} catch (Exception e) {
			ExceptionHandler.onError(e);
		}
		return null;
	}
	
	
	public GridViewModel doSearch(DynamicForm form) {
		Expression exp = null;
		if(form.get("status") != null){
			exp = Expr.eq("status", TimesheetStatus.valueOf(form.get("status")).ordinal());
		}else{
			exp = Expr.eq("status", TimesheetStatus.Draft);
		}
		
		exp = Expr.and(exp,Expr.eq("user", User.findByEmail(form.data().get("email"))));
		
		try {
			if(form.data().get("fromWeekWindow") != null  && form.data().get("toWeekWindow") != null
					&& !"".equals(form.data().get("fromWeekWindow"))  && !"".equals(form.data().get("toWeekWindow"))){
				int fromWeekFromUI = getWeekNumber(form.data().get("fromWeekWindow"));
				int toWeekFromUI = getWeekNumber(form.data().get("toWeekWindow"));
				if(fromWeekFromUI != 0 && toWeekFromUI != 0){
					exp = Expr.and(exp, Expr.between("weekOfYear",fromWeekFromUI, toWeekFromUI));
	        	}
			}
		} catch (ParseException e) {
			ExceptionHandler.onError(e);
		}
        
		int page = Integer.parseInt(form.get("page"));
		int limit = Integer.parseInt(form.get("rows"));
		GridViewModel.PageData pageData = new PageData(limit,page);
		int count = 0;
		
		if(exp == null ){
			count = Timesheet.find.where().findRowCount();
		}else{
			count = Timesheet.find.where().add(exp).findRowCount();
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
		List<Timesheet> results =  exp == null ?Timesheet.find.setFirstRow(start).setMaxRows(limit).findList()
				:Timesheet.find.where().add(exp).setFirstRow(start).setMaxRows(limit).findList();
		List<GridViewModel.RowViewModel> rows = transform(results, toJqGridFormat()) ;
		GridViewModel gridViewModel = new GridViewModel(pageData, count, rows);
		return gridViewModel;
	}
	
	private  Function<Timesheet,RowViewModel> toJqGridFormat() {
		return new Function<Timesheet, RowViewModel>() {
			@Override
			public RowViewModel apply(Timesheet timesheet) {
				try {
					return new GridViewModel.RowViewModel((timesheet.id).intValue(), newArrayList(getResultStr(timesheet)));
				} catch (Exception e) {
					ExceptionHandler.onError(e);
				} 
				return null;
			}
		};
	}

	public static int getWeekNumber(String input) throws ParseException{
		String format = "dd-MM-yyyy";
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date date = df.parse(input);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		DateTime date2 = new DateTime().withWeekOfWeekyear(week);
		return week;
	}

	@Override
	protected void buildButton() {
		// TODO Auto-generated method stub
		
	}
}
