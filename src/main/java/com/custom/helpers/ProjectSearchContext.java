package com.custom.helpers;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.Client;
import models.Supplier;
import models.User;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import play.data.DynamicForm;
import utils.ExceptionHandler;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import com.avaje.ebean.SqlRow;
import com.google.common.base.Function;
import com.mnt.core.helper.ASearchContext;
import com.mnt.core.ui.component.BuildGridActionButton;
import com.mnt.core.ui.component.BuildUIButton;
import com.mnt.core.ui.component.EditButton;
import com.mnt.core.ui.component.GridActionButton;
import com.mnt.core.ui.component.UIButton;
import com.mnt.core.ui.component.UIButton.ButtonActionType;
import com.mnt.core.utils.GridViewModel;
import com.mnt.core.utils.GridViewModel.PageData;
import com.mnt.core.utils.GridViewModel.RowViewModel;
import com.mnt.createProject.model.Projectinstance;
import com.mnt.time.controller.routes;


public class ProjectSearchContext extends ASearchContext<Projectinstance>{ //ProjectInstance 
	
	private static ProjectSearchContext searchContext = null;
	
	public static ProjectSearchContext getInstance(){
		//if(searchContext == null){
			return new ProjectSearchContext();
		//}
	}
	
	public String entityName(){
		return Projectinstance.ENTITY;
	}
	
	public ProjectSearchContext() {
		super(Projectinstance.class,null);//ProjectInstance 
	}
	
	public ProjectSearchContext(Projectinstance p) {//ProjectInstance 
		super(Projectinstance.class,p);//ProjectInstance 
	}

	@Override
	public String generateExcel() {
		return routes.Projects.excelReport.url;
	}
	
	@Override
	public String searchUrl() {
		return routes.Projects.Search.url;
	}

	@Override
	public String editUrl() {
		return routes.Projects.edit.url;
	}

	@Override
	public String createUrl() {
		return routes.Projects.create.url;
	}

	@Override
	public String deleteUrl() {
		return routes.Projects.delete.url;
	}
	
	
	
	@Override
	public HSSFWorkbook doExcel(DynamicForm form) {
		Expression exp =  super.doSearchExpression(form, SearchType.Like);
		String email = form.data().get("email");
	//	User user1 = User.findByEmail(email);
	//	Expression exp1 = Expr.eq("companyObj.companyCode", user1.getCompanyobject().getCompanyCode());
		List<Projectinstance> result;  
		
		if(exp == null ){
			result = Projectinstance.find.where().findList();
		}else{
			result = Projectinstance.find.where().add(exp).findList();
		}
		try {
			 return super.getExcelExport(result);
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
		
		User user = User.findByEmail(email);
		//Expression exp1 = Expr.eq("projectManager.id", user.getId());
		//Expression exp1 = Expr.eq("companyObj.companyCode", user1.getCompanyobject().getCompanyCode());

		int count = 0;
		/*if(exp == null ){
			count = Project.find.where().add(exp1).findRowCount();
		}else{
			count = Project.find.where().add(exp1).add(exp).findRowCount();
		}*/
		
		List<Projectinstance> pList = new ArrayList<>();
		
		if(user.getUsertype() == null){
			pList = Projectinstance.getProjectList();
		}else if(user.getUsertype().equals("User")){
			//pList = Projectinstance.getProjectByUser(user.getId());
			List<SqlRow> sqlRows = Projectinstance.getProjectsOfUser(user.getId());
			for(SqlRow row: sqlRows) {
				Projectinstance projectinstance =Projectinstance.findById(row.getLong("projectinstance_id"));
				pList.add(projectinstance);
				
			}
				List<Projectinstance> projectList = Projectinstance.getProjectsOfManager(user);
			if (projectList != null) {
				for (Projectinstance project : projectList) {
					Projectinstance projectInstance = Projectinstance
							.getById(project.getId());
					pList.add(projectInstance);
				}
			}
	   }else if(user.getUsertype().equals("Customer User")){
			Client client = Client.findByUserId(user.getId());
			 if(client != null){
			    pList = Projectinstance.getProjectByClient(client.getId());
			 }
		}else if(user.getUsertype().equals("Supplier User")){
			Supplier supplier = Supplier.findByUserId(user.getId());
			 if(supplier != null){
			    pList = Projectinstance.getProjectBySupplier(supplier.getId());
			 }
		}
		
		
		List<Projectinstance> pList1 = new ArrayList<>();
		
		
		count = pList.size(); //Projectinstance.find.where().findRowCount();
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
		limit = limit + start;
		
		if(pList.size() > start && pList.size() < limit){
			limit = pList.size(); 
		}
		int j = 0;
		for(int i = start;i < limit;i++){
			pList1.add(pList.get(i));
			j++;
		}
		
		List<Projectinstance> results = null;
		List<Projectinstance> pList2 = new ArrayList<>();
		if(form.data().get("clientName") != null && form.data().get("clientName") != ""){
			
			for(Projectinstance projList:pList){
				
				if(projList.clientName.toUpperCase().startsWith(form.data().get("clientName").toUpperCase())){
					pList2.add(projList);
				}
			}
			results = pList2;
			
			count = pList2.size(); 
					
			 start = limit*page - limit;
			limit = limit + start;
			
			
		}else if(form.data().get("projectName") != null && form.data().get("projectName") != ""){
			for(Projectinstance projList:pList){
				if(projList.projectName.toUpperCase().startsWith(form.data().get("projectName").toUpperCase())){
					pList2.add(projList);
				}
			}
			results = pList2;
			
			count = pList2.size(); 
					
			 start = limit*page - limit;
			limit = limit + start;
			
			
		}else if(form.data().get("projectTypeName") != null && form.data().get("projectTypeName") != ""){
			for(Projectinstance projList:pList){
				if(projList.projectTypeName != null){
				if(projList.projectTypeName.toUpperCase().startsWith(form.data().get("projectTypeName").toUpperCase())){
					pList2.add(projList);
				}
				}
			}
			results = pList2;
			
			count = pList2.size(); 
					
			 start = limit*page - limit;
			limit = limit + start;
			
			
		}else if(form.data().get("startDate") != null && form.data().get("startDate") != ""){
			for(Projectinstance projList:pList){
				if(projList.startDate.toUpperCase().equals(form.data().get("startDate").toUpperCase())){
					pList2.add(projList);
				}
			}
			results = pList2;
			
			count = pList2.size(); 
					
			 start = limit*page - limit;
			limit = limit + start;
			
			
		}else if(form.data().get("endDate") != null && form.data().get("endDate") != ""){
			for(Projectinstance projList:pList){
				if(projList.endDate.equals(form.data().get("endDate"))){
					pList2.add(projList);
				}
			}
			results = pList2;
			
			count = pList2.size(); 
					
			 start = limit*page - limit;
			limit = limit + start;
			
			
		}else if(form.data().get("status") != null && form.data().get("status") != ""){
			for(Projectinstance projList:pList){
				if(projList.status != null){
					if(projList.status.getName().toUpperCase().startsWith(form.data().get("status").toUpperCase())){
						pList2.add(projList);
					}
				}
			}
			results = pList2;
			
			count = pList2.size(); 
					
			 start = limit*page - limit;
			limit = limit + start;
			
			
		}else{
				results = pList1;
		}
		
		/*List<Projectinstance> results = exp == null ?Projectinstance.find.setFirstRow(start).setMaxRows(limit).where().findList()
				:Projectinstance.find.where().add(exp).setFirstRow(start).setMaxRows(limit).findList();*/
		List<GridViewModel.RowViewModel> rows = transform(results, toJqGridFormat());
		GridViewModel gridViewModel = new GridViewModel(pageData, count, rows);
		return gridViewModel;
	}
	
	

	private  Function<Projectinstance,RowViewModel> toJqGridFormat() {
		return new Function<Projectinstance, RowViewModel>() {
			@Override
			public RowViewModel apply(Projectinstance project) {
				try {
					return new GridViewModel.RowViewModel((project.getId()).intValue(),newArrayList(
									project.getProjectName(),
									project.getProjectDescription(),
									project.getClientName(),
									project.getStartDate(),
									project.getEndDate(),
									project.getStatus().getName(),
									project.getProjectTypeName()
									));
				} catch (Exception e) {
					ExceptionHandler.onError(e);
				} 
				return null;
			}
		};
	}

	//@Override
	public String showEditUrl() {
		return routes.Projects.showEdit.url;
	}
	
	@Override
	public Map<String, String> autoCompleteUrls() {
		return Projectinstance.autoCompleteAction;
	}

	
	@Override
	public UIButton showEditButton(){
		
		return BuildUIButton.me().withID("_editButton").
				withTarget(ButtonActionType.NEW).
				withLabel("Edit Selected").
				withUrl("/edit/project").
				withVisibility(true);
	}
	
	@Override
    protected void buildButton() {
           
		super.getButtonActions().add(BuildUIButton.me().withID("_grantButton").
				withTarget(ButtonActionType.NEW).
				withLabel("Show As Gantt").
				withUrl("/showGantt").
				
				withVisibility(true));
	}
	

}
