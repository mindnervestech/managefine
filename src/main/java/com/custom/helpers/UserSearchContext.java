package com.custom.helpers;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.User;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import play.data.DynamicForm;
import utils.ExceptionHandler;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import com.custom.domain.Status;
import com.google.common.base.Function;
import com.mnt.core.helper.ASearchContext;
import com.mnt.core.utils.GridViewModel;
import com.mnt.core.utils.GridViewModel.PageData;
import com.mnt.core.utils.GridViewModel.RowViewModel;
import com.mnt.time.controller.routes;


public class UserSearchContext extends ASearchContext<User>{
	
	private static UserSearchContext searchContext = null;
	
	public static UserSearchContext getInstance(){
		//if(searchContext == null){
		return new UserSearchContext();
		//}
		//return searchContext;
	}
	
	public String entityName(){
		return "User";
	}

	public UserSearchContext() {
		super(models.User.class,null);
	}
	
	public UserSearchContext(User u) {
		super(models.User.class,u);
	}

	@Override
	public String searchUrl() {
		return routes.Users.search.url;
	}
	
	@Override
	public String generateExcel() {
		return routes.Users.excelReport.url;
	}

	@Override
	public String editUrl() {
		return routes.Users.edit.url;
	}
	
	@Override
	public String showEditUrl() {
		return routes.Users.showEdit.url;
	}

	@Override
	public String createUrl() {
		return routes.Users.create.url;
	}

	@Override
	public String deleteUrl() {
		return routes.Users.delete.url;
	}
	
	@Override
	public HSSFWorkbook doExcel(DynamicForm form) {
		String email = form.data().get("email");
		Expression exp =  super.doSearchExpression(form, SearchType.Like);
		User user1 = User.findByEmail(email);
		Expression exp1 = Expr.eq("companyobject.companyCode", user1.companyobject.getCompanyCode());
		Expression exp2 = Expr.ne("email", user1.email);
		
		List<models.User> result =  exp == null ?models.User.find.where().add(exp1).add(exp2).findList()
				:models.User.find.where().add(exp).add(exp1).add(exp2).findList();
		List<models.User> showResult = new ArrayList<models.User>(result.size());
		
		for(int i=0; i<result.size();i++)
		{
			User user = new User();
			if(result.get(i).designation != "Admin" && result.get(i).userStatus != Status.Disapproved && result.get(i).userStatus != Status.PendingApproval)
			{
				user.id = result.get(i).id;
				user.firstName = result.get(i).firstName;
				user.lastName = result.get(i).lastName;
				user.email = result.get(i).email;
				user.employeeId = result.get(i).employeeId;
				user.designation = result.get(i).designation;
				showResult.add(user);
			}
		}
		
		try {
			 return super.getExcelExport(showResult);
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
		Expression exp1 = Expr.eq("companyobject.companyCode", user1.companyobject.getCompanyCode());
		Expression exp2 = Expr.and(Expr.ne("designation", "Admin"),
									Expr.and(Expr.eq("userStatus", Status.Approved),Expr.ne("email", user1.email)));
		int count=0;
		if(exp == null ){
			count = models.User.find.where().add(exp1).add(exp2).findRowCount();
		}else{
			count = models.User.find.where().add(exp).add(exp1).add(exp2).findRowCount();
		}
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
		
		int start = limit*page - limit;
		List<models.User> result =  exp == null ?models.User.find.setFirstRow(start).setMaxRows(limit).where().add(exp1).add(exp2).findList()
				:models.User.find.where().add(exp).add(exp1).add(exp2).setFirstRow(start).setMaxRows(limit).findList();
		
		List<GridViewModel.RowViewModel> rows = transform(result, toJqGridFormat()) ;
		GridViewModel gridViewModel = new GridViewModel(pageData, count, rows);
		return gridViewModel;
	}
	
	private  Function<models.User,RowViewModel> toJqGridFormat() {
		return new Function<models.User, RowViewModel>() {
			@Override
			public RowViewModel apply(models.User user) {
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

	@Override
	protected void buildButton() {
		// TODO Auto-generated method stub
		
	}
	
//	@Override
//	public UIButton showAddButton(){
//		return BuildUIButton.me().withVisibility(false);
//	}
	
}
