package com.custom.ui.search.helper;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.User;
import play.data.DynamicForm;
import utils.ExceptionHandler;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import com.custom.ui.search.proxy.TeamRateReportProxyUISearch;
import com.google.common.base.Function;
import com.mnt.core.helper.ASearchContext;
import com.mnt.core.ui.component.BuildUIButton;
import com.mnt.core.ui.component.UIButton;
import com.mnt.core.utils.GridViewModel;
import com.mnt.core.utils.GridViewModel.PageData;
import com.mnt.core.utils.GridViewModel.RowViewModel;
import com.mnt.time.controller.routes;

public class TeamRateReportSearchUI extends ASearchContext<TeamRateReportProxyUISearch>{
	
	public static TeamRateReportSearchUI getInstance(){
		return new TeamRateReportSearchUI();
	}
	
	public String entityName(){
		return "Report";
	}

	public TeamRateReportSearchUI() {
		super(TeamRateReportProxyUISearch.class,null);
	}
	public TeamRateReportSearchUI(TeamRateReportProxyUISearch u) {
		super(TeamRateReportProxyUISearch.class,u);
	}
	
	
	@Override
    public void buildButton() {
           
		super.getButtonActions().add(new UIButton() {
			
              @Override
              public boolean visibility() {
                    return false;
              }
             
              @Override
              public String url() {
                    return "";
              }
             
              @Override
              public ButtonActionType target() {
                    return ButtonActionType.ACTION;
              }
             
              @Override
              public String label() {
                    return "";
              }
             
              @Override
              public String id() {
                    return "";
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
		return routes.Reports.teamRateSearch.url;
	}

	@Override
	public String editUrl() {
		return "#";
	}
	
	@Override
	public String showEditUrl() {
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
	
	public GridViewModel doSearch(DynamicForm form) {
		
		String query;
		
		if(form.data().get("projectCode") != null){
			
			query = "select u.id user_id , sum(ts_r.total_hrs) total_hrs, ts_r.project_code from timesheet ts, " +
					"timesheet_row ts_r , user u where "+
					"ts.id = ts_r.timesheet_id and "+ 
					"ts_r.project_code LIKE '"+form.data().get("projectCode")+"%' and "+
					"u.id = ts.user_id "+
					"group by ts.user_id,ts_r.project_code";
			
		}else{
			query = "select u.id user_id , sum(ts_r.total_hrs) total_hrs, ts_r.project_code from timesheet ts, " +
					"timesheet_row ts_r , user u where "+
					"ts.id = ts_r.timesheet_id and "+
					"u.id = ts.user_id "+
					"group by ts.user_id,ts_r.project_code";
		}
		
		List<SqlRow> userList = Ebean.createSqlQuery(query).findList();
		
		List<TeamRateReportProxyUISearch> results= new ArrayList<TeamRateReportProxyUISearch>(userList.size());
		
		for(int j=0; j<userList.size(); j++){
			User user = User.findById(Long.parseLong(userList.get(j).getString("user_id")));
			TeamRateReportProxyUISearch report = new TeamRateReportProxyUISearch();
			report.id = user.id;
			report.firstName = user.firstName;
			report.lastName = user.lastName;
			report.projectCode = userList.get(j).getString("project_code");
			report.perHrRate = user.hourlyrate;
			report.totalHrs = Integer.parseInt(userList.get(j).getString("total_hrs"));
			results.add(report);
		}
		
		int page = Integer.parseInt(form.get("page"));
		int limit = Integer.parseInt(form.get("rows"));
		GridViewModel.PageData pageData = new PageData(limit,
				page);
		int count=0;
		//TODO
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
		
		List<GridViewModel.RowViewModel> rows = transform(results, toJqGridFormat()) ;
		GridViewModel gridViewModel = new GridViewModel(pageData, count, rows);
		return gridViewModel;	
	}
	
	private  Function<TeamRateReportProxyUISearch,RowViewModel> toJqGridFormat() {
		return new Function<TeamRateReportProxyUISearch, RowViewModel>() {
			@Override
			public RowViewModel apply(TeamRateReportProxyUISearch user) {
				try {
					return new GridViewModel.RowViewModel((user.id).intValue(), newArrayList(getResultStr(user)));
				} catch (Exception e) {
					ExceptionHandler.onError(e);
				} 
				return null;
			}
		};
	}
	
	@Override
	public Map<String, String> autoCompleteUrls() {
		return User.autoCompleteAction;
	}
	
}



