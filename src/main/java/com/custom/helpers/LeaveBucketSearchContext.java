package com.custom.helpers;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.ApplyLeave;
import models.Delegate;
import models.User;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.DateTime;

import play.data.DynamicForm;
import utils.ExceptionHandler;

import com.custom.domain.LeaveStatus;
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

import dto.LeaveBucket;


public class LeaveBucketSearchContext extends ASearchContext<LeaveBucket>{
	
	public static LeaveBucketSearchContext getInstance(){
		return new LeaveBucketSearchContext();
	}
	
	public String entityName(){
		return "My_Leave_Approval_Bucket";
	}

	public LeaveBucketSearchContext() {
		super(LeaveBucket.class,null);
	}
	
	
	public LeaveBucketSearchContext(LeaveBucket p) {
		super(LeaveBucket.class,p);
	}
	
	@Override
	public void buildGridButton() {
		getGridActions().add(BuildGridActionButton.me().withVisibilityTrue().withIcon(GridActionButton.IconType.Tick).withUrl("/leave/approveLeave").withTooltip("Approve Leave"));
		getGridActions().add(BuildGridActionButton.me().withVisibilityTrue().withIcon(GridActionButton.IconType.Cross).withUrl("/leave/rejectLeave").withTooltip("Disapprove Leave"));
		
	}
	
	@Override
	public boolean isMultiSelectSearch() {
		return true;
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
                return "/leave/approveLeave";
          }
         
          @Override
          public ButtonActionType target() {
                return ButtonActionType.ACTION;
          }
         
          @Override
          public String label() {
                return "Approve Leave";
          }
         
          @Override
          public String id() {
                return "leaveApproveButton";
          }
		});
		
		super.getButtonActions().add(new UIButton() {
			
			@Override
	        public boolean visibility() {
	              return true;
	        }
	       
	        @Override
	        public String url() {
	              return "/leave/rejectLeave";
	        }
	       
	        @Override
	        public ButtonActionType target() {
	              return ButtonActionType.ACTION;
	        }
	       
	        @Override
	        public String label() {
	              return "Reject Leave";
	        }
	       
	        @Override
	        public String id() {
	              return "leaveRejectButton";
	        }
		});
	}

	@Override
	public String searchUrl() {
		return routes.Leaves.leaveSearch.url;
	}

	@Override
	public String editUrl() {
		// TODO Auto-generated method stub
		return "#";
	}

	@Override
	public String createUrl() {
		// TODO Auto-generated method stub
		return "#";
	}

	@Override
	public String deleteUrl() {
		// TODO Auto-generated method stub
		return "#";
	}
	@Override
	public UIButton showAddButton(){
		return BuildUIButton.me().withVisibility(false);
	}
	
	@Override
	public String generateExcel() {
		return routes.Leaves.excelReport.url;
	}
	
	@Override
	public UIButton showEditButton(){
		return BuildUIButton.me().withVisibility(false);
	}

	@Override
	public String showEditUrl() {
		// TODO Auto-generated method stub
		return "#";
	}
	
	@Override
	public HSSFWorkbook doExcel(DynamicForm form) {
		String email = form.data().get("email");
		User user1 = User.findByEmail(email);
		Long id = null;
		
		Date startTo;
        SimpleDateFormat TargerdateFormat = new SimpleDateFormat("dd-MM-yyyy");
        
		List<ApplyLeave> result = null;
		List<ApplyLeave> delegateResult = null;
		String status = form.get("status");
		if(form.get("status")==null){
			result = ApplyLeave.find.where().eq("status",LeaveStatus.Submitted).findList();
		}else{
			result = ApplyLeave.find.where().eq("status",form.get("status")).findList();
		}
		
		Delegate delegate = Delegate.find.where().eq("delegatedTo", user1).findUnique();
		DateTime today = new DateTime();
		if(delegate != null && (today.isAfter(delegate.getFromDate().getTime()) && today.isBefore(delegate.toDate.getTime())))
		{
			id = delegate.delegator.id;
			delegateResult = ApplyLeave.find.where().eq("pendingWith",User.find.byId(id)).findList(); 
		}
		
		List<LeaveBucket> leaveResult= new ArrayList<LeaveBucket>(result.size());
		
		for(int i=0; i<result.size();i++)
		{
			LeaveBucket leave = new LeaveBucket();
			if(result.get(i).getUser().getManager().getId() == user1.getId())
			{

				leave.setId(result.get(i).getId());
				leave.setFirstName(result.get(i).user.getFirstName());
				leave.setLastName(result.get(i).user.getLastName());
				leave.setStatus(result.get(i).getStatus());
				startTo = result.get(i).getStartDate();
				String startDate= TargerdateFormat.format(startTo);
				leave.setStartDate(startDate);
				leave.setNoOfDays(result.get(i).getNoOfDays());
				leave.setTypeOfLeave(result.get(i).getTypeOfLeave());
				leave.setRemarks(result.get(i).getRemarks());
				leaveResult.add(leave);
			}
		}
		if(delegateResult != null)
		{
			for(int i=0; i<result.size();i++)
			{
				LeaveBucket leave = new LeaveBucket();
				leave.setId(delegateResult.get(i).getId());
				leave.setFirstName(delegateResult.get(i).user.getFirstName());
				leave.setLastName(delegateResult.get(i).user.getLastName());
				leave.setStatus(delegateResult.get(i).getStatus());
				startTo = delegateResult.get(i).getStartDate();
				String startDate= TargerdateFormat.format(startTo);
				leave.setStartDate(startDate);
				leave.setNoOfDays(delegateResult.get(i).getNoOfDays());
				leave.setTypeOfLeave( delegateResult.get(i).getTypeOfLeave());
				leave.setRemarks(delegateResult.get(i).getRemarks());
				leaveResult.add(leave);
			}
		}
		
		
		try {
			 return super.getExcelExport(leaveResult);
		} catch (Exception e) {
			ExceptionHandler.onError(e);
		}
		return null;
	}
	
	public GridViewModel doSearch(DynamicForm form) {
		
		int page = Integer.parseInt(form.get("page"));
		int limit = Integer.parseInt(form.get("rows"));
		GridViewModel.PageData pageData = new PageData(limit,page);
		int count = 0;
		
		String email = form.data().get("userEmail");
		User user1 = User.findByEmail(email);
		Long id = null;
		
		if(form.get("status")==null){
			count = ApplyLeave.find.findRowCount();
		}else{
			count = ApplyLeave.find.where().eq("status",form.get("status")).findRowCount();
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
		
		Date startTo;
        SimpleDateFormat TargerdateFormat = new SimpleDateFormat("dd-MM-yyyy");
        
		List<ApplyLeave> result = null;
		List<ApplyLeave> delegateResult = null;
		
		if(form.get("status")==null){
			result = ApplyLeave.find.where().eq("status",LeaveStatus.Submitted).setFirstRow(start).setMaxRows(limit).findList();
		}else{
			result = ApplyLeave.find.where().eq("status",form.get("status")).setFirstRow(start).setMaxRows(limit).findList();
		}
		
		Delegate delegate = Delegate.find.where().eq("delegatedTo", user1).findUnique();
		DateTime today = new DateTime();
		if(delegate != null && (today.isAfter(delegate.fromDate.getTime()) && today.isBefore(delegate.toDate.getTime())))
		{
			id = delegate.delegator.id;
			delegateResult = ApplyLeave.find.where().eq("pendingWith",User.find.byId(id)).findList(); 
		}
		
		List<LeaveBucket> leaveResult= new ArrayList<LeaveBucket>(result.size());
		
		for(int i=0; i<result.size();i++)
		{
			LeaveBucket leave = new LeaveBucket();
			if(result.get(i).getUser().getManager().getId() == user1.getId())
			{
			leave.setId(result.get(i).getId());
			leave.setFirstName(result.get(i).user.getFirstName());
			leave.setLastName(result.get(i).user.getLastName());
			leave.setStatus(result.get(i).getStatus());
			startTo = result.get(i).getStartDate();
			String startDate= TargerdateFormat.format(startTo);
			leave.setStartDate(startDate);
			leave.setNoOfDays(result.get(i).getNoOfDays());
			leave.setTypeOfLeave(result.get(i).getTypeOfLeave());
			leave.setRemarks(result.get(i).getRemarks());
			leaveResult.add(leave);
			}
		}
		if(delegateResult != null)
		{
			count = count+ delegateResult.size();
			for(int i=0; i<result.size();i++)
			{
				LeaveBucket leave = new LeaveBucket();
				leave.setId(delegateResult.get(i).getId());
				leave.setFirstName(delegateResult.get(i).user.getFirstName());
				leave.setLastName(delegateResult.get(i).user.getLastName());
				leave.setStatus(delegateResult.get(i).getStatus());
				startTo = delegateResult.get(i).getStartDate();
				String startDate= TargerdateFormat.format(startTo);
				leave.setStartDate(startDate);
				leave.setNoOfDays(delegateResult.get(i).getNoOfDays());
				leave.setTypeOfLeave(delegateResult.get(i).getTypeOfLeave());
				leave.remarks = delegateResult.get(i).getRemarks();
				leaveResult.add(leave);
			}
		}
		
		List<GridViewModel.RowViewModel> rows = transform(leaveResult, toJqGridFormat()) ;
		GridViewModel gridViewModel = new GridViewModel(pageData, count, rows);
		return gridViewModel;
	}
	
	private  Function<LeaveBucket,RowViewModel> toJqGridFormat() {
		return new Function<LeaveBucket, RowViewModel>() {
			@Override
			public RowViewModel apply(LeaveBucket leave) {
				try {
					return new GridViewModel.RowViewModel((leave.id).intValue(), newArrayList(getResultStr(leave)));
				} catch (Exception e) {
					ExceptionHandler.onError(e);
				} 
				return null;
			}
		};
	}


}