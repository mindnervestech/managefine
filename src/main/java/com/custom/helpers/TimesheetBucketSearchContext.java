package com.custom.helpers;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.Delegate;
import models.Project;
import models.Timesheet;
import models.User;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.DateTime;

import play.data.DynamicForm;
import utils.ExceptionHandler;

import com.avaje.ebean.Expr;
import com.custom.domain.TimesheetStatus;
import com.google.common.base.Function;
import com.mnt.core.helper.ASearchContext;
import com.mnt.core.ui.component.BuildGridActionButton;
import com.mnt.core.ui.component.BuildUIButton;
import com.mnt.core.ui.component.GridActionButton;
import com.mnt.core.ui.component.UIButton;
import com.mnt.core.utils.GridViewModel;
import com.mnt.core.utils.GridViewModel.PageData;
import com.mnt.core.utils.GridViewModel.RowViewModel;
import com.mnt.time.controller.routes;

import dto.TimeSheetBucket;

public class TimesheetBucketSearchContext extends ASearchContext<TimeSheetBucket>{
	
	public static TimesheetBucketSearchContext getInstance(){
		return new TimesheetBucketSearchContext();
	}
	
	public String entityName(){
		return TimeSheetBucket.ENTITY;
	}

	public TimesheetBucketSearchContext() {
		super(TimeSheetBucket.class,null);
	}
	
	@Override
	public void buildGridButton() {
		getGridActions().add(BuildGridActionButton.me().withVisibilityTrue()
				.withIcon(GridActionButton.IconType.Tick).withUrl("/timesheet/approveTimesheets")
				.withTooltip("Approve Timesheet"));
		
		getGridActions().add(BuildGridActionButton.me().withVisibilityTrue()
				.withIcon(GridActionButton.IconType.Cross).withUrl("/timesheet/rejectTimesheets")
				.withTooltip("Reject Timesheet"));
	}
	
	@Override
	public boolean isMultiSelectSearch() {
		return true;
	}
	
	public TimesheetBucketSearchContext(TimeSheetBucket p) {
		super(TimeSheetBucket.class,p);
	}
	
	@Override
	public String generateExcel() {
		return routes.TimesheetBuckets.excelReport.url;
	}
	
	@Override
    protected void buildButton() {
           
		super.getButtonActions().add(new UIButton() {
			
          @Override
          public boolean visibility() {
                return true;
          }
         
          @Override
          public String url() {
                return "/timesheet/approveTimesheets";
          }
         
          @Override
          public ButtonActionType target() {
                return ButtonActionType.ACTION;
          }
         
          @Override
          public String label() {
                return "Approve";
          }
         
          @Override
          public String id() {
                return "timesheetApproveButton";
          }
		});
		
		super.getButtonActions().add(new UIButton() {
			
			@Override
	        public boolean visibility() {
	              return true;
	        }
	       
	        @Override
	        public String url() {
	              return "/timesheet/rejectTimesheets";
	        }
	       
	        @Override
	        public ButtonActionType target() {
	              return ButtonActionType.ACTION;
	        }
	       
	        @Override
	        public String label() {
	              return "Reject";
	        }
	       
	        @Override
	        public String id() {
	              return "timesheetRejectButton";
	        }
		});
		
		super.getButtonActions().add(new UIButton() {
			
			@Override
	        public boolean visibility() {
	              return true;
	        }
	       
	        @Override
	        public String url() {
	              return "/timesheet/displayTimesheet";
	        }
	       
	        @Override
	        public ButtonActionType target() {
	              return ButtonActionType.MODAL;
	        }
	       
	        @Override
	        public String label() {
	              return "View";
	        }
	       
	        @Override
	        public String id() {
	              return "displayTimesheetButton";
	        }
		});
	}
	
	@Override
	public UIButton showAddButton(){
		return BuildUIButton.me();
	}
	
	@Override
	public UIButton showEditButton(){
		return BuildUIButton.me();
	}

	@Override
	public String searchUrl() {
		return routes.TimesheetBuckets.search.url;
	}

	@Override
	public String editUrl() {
		return "#";
	}

	@Override
	public String createUrl() {
		return "#";
	}
	
	@Override
	public String deleteUrl() {
		return "#";
	}

	@Override
	public String showEditUrl() {
		return "displayTimesheet";
	}
	
	@Override
	public HSSFWorkbook doExcel(DynamicForm form) {
		List<Timesheet> timesheets = null;
		List<Timesheet> timesheetsDelegate = null;
		
		User user = User.findByEmail(form.data().get("email"));
		Long id = null;
		if(form.data().get("status") != null ){
			timesheets = Timesheet.find.where()
					.and(Expr.eq("status", TimesheetStatus.valueOf(form.data().get("status")).ordinal()),
							Expr.eq("timesheetWith", User.findByEmail(form.data().get("userEmail"))))
								.findList();
		}else{
			timesheets = Timesheet.find.where()
					.and(Expr.eq("status", TimesheetStatus.Submitted),
							Expr.eq("timesheetWith", User.findByEmail(form.data().get("userEmail"))))
								.findList();
		}
		Delegate delegate = Delegate.find.where().eq("delegatedTo", user).findUnique();
		DateTime today = new DateTime();
		if(delegate != null && (today.isAfter(delegate.fromDate.getTime()) && today.isBefore(delegate.toDate.getTime())))
		{
			id = delegate.delegator.id;
			timesheetsDelegate = Timesheet.find.where().eq("timesheetWith",User.find.byId(id)).findList();
		}
		
		List<TimeSheetBucket> results = new ArrayList<TimeSheetBucket>();
		if(timesheets != null){
			for(int i=0 ; i<= timesheets.size()-1; i++){
				TimeSheetBucket myBucket = new TimeSheetBucket();
				myBucket.setFirstName(timesheets.get(i).getUser().getFirstName());
				myBucket.setLastName(timesheets.get(i).user.getLastName());
				myBucket.setProjectName(Project.findByProjectCode(timesheets.get(i).timesheetRows.get(0).getProjectCode()).getProjectName());
				myBucket.setId(timesheets.get(i).getId());
				myBucket.setWeekOfYear(timesheets.get(i).getWeekOfYear());
				myBucket.setYear(timesheets.get(i).getYear());
//				if(timesheets.get(i).status == TimesheetStatus.Approved){
//					myBucket.status = myBucket.status.Approved;
//				}
//				if(timesheets.get(i).status == TimesheetStatus.Rejected){
//					myBucket.status = myBucket.status.Rejected;
//				}
//				if(timesheets.get(i).status == TimesheetStatus.Submitted){
//					myBucket.status = myBucket.status.Submitted;
//				}
				results.add(i, myBucket);
			}
		if(timesheetsDelegate != null){
			for(int i =0; i<timesheetsDelegate.size(); i++){
				TimeSheetBucket myBucket = new TimeSheetBucket();
				myBucket.setFirstName(timesheetsDelegate.get(i).getUser().getFirstName());
				myBucket.setLastName(timesheetsDelegate.get(i).user.getLastName());
				myBucket.setProjectName(Project.findByProjectCode(timesheetsDelegate.get(i).timesheetRows.get(0).getProjectCode()).getProjectName());
				myBucket.setId(timesheetsDelegate.get(i).getId());
				myBucket.setWeekOfYear(timesheetsDelegate.get(i).getWeekOfYear());
				myBucket.setYear(timesheetsDelegate.get(i).getYear());
//				if(timesheetsDelegate.get(i).status == TimesheetStatus.Approved){
//					myBucket.status = myBucket.status.Approved;
//				}
//				if(timesheetsDelegate.get(i).status == TimesheetStatus.Rejected){
//					myBucket.status = myBucket.status.Rejected;
//				}
//				if(timesheetsDelegate.get(i).status == TimesheetStatus.Submitted){
//					myBucket.status = myBucket.status.Submitted;
//				}
				results.add(i, myBucket);
			}
		}
			
		}
		try {
			 return super.getExcelExport(results);
		} catch (Exception e) {
			ExceptionHandler.onError(e);
		}
		return null;
	}

	public GridViewModel doSearch(DynamicForm form) {
		List<Timesheet> timesheets = null;
		List<Timesheet> timesheetsDelegate = null;
		
		User user = User.findByEmail(form.data().get("userEmail"));
		Long id = null;
		if(form.data().get("status") != null ){
			timesheets = Timesheet.find.where()
					.and(Expr.eq("status", TimesheetStatus.valueOf(form.data().get("status")).ordinal()),
							Expr.eq("timesheetWith", User.findByEmail(form.data().get("userEmail"))))
								.findList();
		}else{
			timesheets = Timesheet.find.where()
					.and(Expr.eq("status", TimesheetStatus.Submitted),
							Expr.eq("timesheetWith", User.findByEmail(form.data().get("userEmail"))))
								.findList();
		}
		Delegate delegate = Delegate.find.where().eq("delegatedTo", user).findUnique();
		DateTime today = new DateTime();
		if(delegate != null && (today.isAfter(delegate.fromDate.getTime()) && today.isBefore(delegate.toDate.getTime())))
		{
			id = delegate.delegator.id;
			timesheetsDelegate = Timesheet.find.where().eq("timesheetWith",User.find.byId(id)).findList();
		}
		
		
		List<TimeSheetBucket> results = new ArrayList<TimeSheetBucket>();
		if(timesheets!= null){
			for(int i=0 ; i<= timesheets.size()-1; i++){
				TimeSheetBucket myBucket = new TimeSheetBucket();
				myBucket.setFirstName(timesheets.get(i).getUser().getFirstName());
				myBucket.setLastName(timesheets.get(i).user.getLastName());
				myBucket.setProjectName(Project.findByProjectCode(timesheets.get(i).timesheetRows.get(0).getProjectCode()).getProjectName());
				myBucket.setId(timesheets.get(i).getId());
				myBucket.setWeekOfYear(timesheets.get(i).getWeekOfYear());
				myBucket.setYear(timesheets.get(i).getYear());
				/*
				myBucket.firstName = timesheets.get(i).user.firstName;
				myBucket.lastName = timesheets.get(i).user.lastName;
				myBucket.projectName = Project.findByProjectCode(timesheets.get(i).timesheetRows.get(0).projectCode).projectName;
				myBucket.id = timesheets.get(i).id;
				myBucket.weekOfYear = timesheets.get(i).weekOfYear;
				myBucket.year = timesheets.get(i).year;*/
//				if(timesheets.get(i).status == TimesheetStatus.Approved){
//					myBucket.status = myBucket.status.Approved;
//				}
//				if(timesheets.get(i).status == TimesheetStatus.Rejected){
//					myBucket.status = myBucket.status.Rejected;
//				}
//				if(timesheets.get(i).status == TimesheetStatus.Submitted){
//					myBucket.status = myBucket.status.Submitted;
//				}
				results.add(i, myBucket);
			}
		}
		
		int page = Integer.parseInt(form.get("page"));
		int limit = Integer.parseInt(form.get("rows"));
		GridViewModel.PageData pageData = new PageData(limit,page);
		int count = 0;
		
		if(results != null ){
			count = results.size();
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
		
		if(timesheetsDelegate != null){
			count = count + timesheetsDelegate.size();
			for(int i =0; i<timesheetsDelegate.size(); i++){
				TimeSheetBucket myBucket = new TimeSheetBucket();
				myBucket.setFirstName(timesheetsDelegate.get(i).getUser().getFirstName());
				myBucket.setLastName(timesheetsDelegate.get(i).user.getLastName());
				myBucket.setProjectName(Project.findByProjectCode(timesheetsDelegate.get(i).timesheetRows.get(0).getProjectCode()).getProjectName());
				myBucket.setId(timesheetsDelegate.get(i).getId());
				myBucket.setWeekOfYear(timesheetsDelegate.get(i).getWeekOfYear());
				myBucket.setYear(timesheetsDelegate.get(i).getYear());
				
/*				myBucket.firstName = timesheets.get(i).user.getFirstName();
				myBucket.lastName = timesheets.get(i).user.getLastName();
				myBucket.projectName = Project.findByProjectCode(timesheets.get(i).timesheetRows.get(0).projectCode).projectName;
				myBucket.id = timesheets.get(i).id;
				myBucket.weekOfYear = timesheets.get(i).weekOfYear;
				myBucket.year = timesheets.get(i).year;
*///				if(timesheetsDelegate.get(i).status == TimesheetStatus.Approved){
//					myBucket.status = myBucket.status.Approved;
//				}
//				if(timesheetsDelegate.get(i).status == TimesheetStatus.Rejected){
//					myBucket.status = myBucket.status.Rejected;
//				}
//				if(timesheetsDelegate.get(i).status == TimesheetStatus.Submitted){
//					myBucket.status = myBucket.status.Submitted;
//				}
				results.add(i, myBucket);
			}
		}
		
		int start = limit*page - limit;//orderBy(sidx+" "+sord)
		List<GridViewModel.RowViewModel> rows = transform(results, toJqGridFormat()) ;
		GridViewModel gridViewModel = new GridViewModel(pageData, count, rows);
		return gridViewModel;
	}
	
	private  Function<TimeSheetBucket,RowViewModel> toJqGridFormat() {
		return new Function<TimeSheetBucket, RowViewModel>() {
			@Override
			public RowViewModel apply(TimeSheetBucket timesheet) {
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
		System.out.println("Date : "+date2);
		return week;
	}
}
