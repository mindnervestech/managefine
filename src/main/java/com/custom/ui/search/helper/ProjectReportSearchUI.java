package com.custom.ui.search.helper;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.util.ArrayList;
import java.util.List;

import models.Project;
import models.User;
import play.data.DynamicForm;
import utils.ExceptionHandler;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import com.custom.ui.search.proxy.ProjectReportProxyUISearch;
import com.google.common.base.Function;
import com.mnt.core.helper.ASearchContext;
import com.mnt.core.ui.component.BuildUIButton;
import com.mnt.core.ui.component.UIButton;
import com.mnt.core.utils.GridViewModel;
import com.mnt.core.utils.GridViewModel.PageData;
import com.mnt.core.utils.GridViewModel.RowViewModel;
import com.mnt.time.controller.routes;

public class ProjectReportSearchUI extends ASearchContext<ProjectReportProxyUISearch> {

	public static ProjectReportSearchUI getInstance(){
		return new ProjectReportSearchUI();
	}
	
	public ProjectReportSearchUI() {
		super(ProjectReportProxyUISearch.class,null);
	}

	public ProjectReportSearchUI(ProjectReportProxyUISearch u) {
		super(ProjectReportProxyUISearch.class,u);
	}
	
	@Override
	public String searchUrl() {
		// TODO Auto-generated method stub
		return routes.ProjectReports.projectReportSearch.url;
	}

	@Override
	public String editUrl() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String createUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String entityName() {
		// TODO Auto-generated method stub
		return "Projects";
	}

	@Override
	public GridViewModel doSearch(DynamicForm form) {
		String query = "";
		User manager = User.findByEmail(form.data().get("userEmail"));
		
		List<Project> projectList = new ArrayList<Project>();
		
		List<ProjectReportProxyUISearch> results= new ArrayList<ProjectReportProxyUISearch>(projectList.size());

		projectList = Project.find.where().eq("projectManager", manager).findList();
		
		for(Project project : projectList){
			query = "select sum(total_hrs) total_hrs , sum(total_hrs*t2.hourlyrate) total_billed  from timesheet_row JOIN(select t.id T_id,u.hourlyrate hourlyrate from timesheet t ,user u where t.status = '3' and t.user_id = u.id)" +
					"AS t2 ON t2.T_id = timesheet_id AND project_code = '"+project.projectCode+"';";
			
			List<SqlRow> resultList = Ebean.createSqlQuery(query).findList();
			
			for(int i=0;i<resultList.size();i++){
				ProjectReportProxyUISearch report = new ProjectReportProxyUISearch();
				report.setId(project.getId());
				report.setProjectName(project.getProjectName());
				report.setProjectStart(project.getStartDate().toString());
				report.setProjectEnd(project.getEndDate().toString());
				report.setEstimatedMoney(project.getBudget());
				if(resultList.get(i).getString("total_hrs") != null){
					report.hrsSpent = Integer.valueOf(resultList.get(i).getString("total_hrs"));
				}else{
					report.hrsSpent = 0;
				}
				
				if(resultList.get(i).getString("total_billed") != null){
					report.moneySpent = Double.valueOf(resultList.get(i).getString("total_billed")).doubleValue();
				}
				else{
					report.moneySpent = 0.0;
				}
				results.add(report);
			}
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
	
	private  Function<ProjectReportProxyUISearch,RowViewModel> toJqGridFormat() {
		return new Function<ProjectReportProxyUISearch, RowViewModel>() {
			@Override
			public RowViewModel apply(ProjectReportProxyUISearch report) {
				try {
					return new GridViewModel.RowViewModel((report.id).intValue(), newArrayList(getResultStr(report)));
				} catch (Exception e) {
					ExceptionHandler.onError(e);
				} 
				return null;
			}
		};
	}

	@Override
	public String showEditUrl() {
		// TODO Auto-generated method stub
		return "";
	}
	
	@Override
	public UIButton showAddButton(){
		return BuildUIButton.me().withVisibility(false);
	}
	
	
	@Override
	public UIButton showEditButton(){
		return BuildUIButton.me();
	}
	
	
	
	@Override
	protected void buildButton() {
		// TODO Auto-generated method stub
		super.getButtonActions().add(new UIButton() {
			
            @Override
            public boolean visibility() {
                  return true;
            }
           
            @Override
            public String url() {
                  return routes.ProjectReports.viewProjectDetails.url;
            }
           
            @Override
            public ButtonActionType target() {
                  return ButtonActionType.MODAL;
            }
           
            @Override
            public String label() {
                  return "View Details";
            }
           
            @Override
            public String id() {
                  return "showdetails";
            }
         });
		super.getButtonActions().add(new UIButton() {
			
			@Override
			public boolean visibility() {
				return true;
			}
			
			@Override
			public String url() {
				return "/viewGraph";
			}
			
			@Override
			public ButtonActionType target() {
				return ButtonActionType.MODAL;
			}

			@Override
			public String label() {
				// TODO Auto-generated method stub
				return "View Usage by Hrs";
			}

			@Override
			public String id() {
				// TODO Auto-generated method stub
				return "viewUsageByhrs";
			}
         });
		
	}
	
	

}
