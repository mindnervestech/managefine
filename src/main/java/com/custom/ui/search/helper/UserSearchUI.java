package com.custom.ui.search.helper;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.util.List;

import models.User;
import play.data.DynamicForm;
import utils.ExceptionHandler;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import com.google.common.base.Function;
import com.mnt.core.helper.SearchUIHelper;
import com.mnt.core.ui.annotation.SearchColumnOnUI;
import com.mnt.core.ui.annotation.SearchFilterOnUI;
import com.mnt.core.ui.component.UIButton;
import com.mnt.core.utils.GridViewModel;
import com.mnt.core.utils.GridViewModel.PageData;
import com.mnt.core.utils.GridViewModel.RowViewModel;
import com.mnt.time.controller.routes;

public class UserSearchUI extends SearchUIHelper {
	private String displayValue="Select User";
	public UserSearchUI(){
		super(UserSearchContext.class);
	}
	
	public UserSearchUI(String displayValue){
		super(UserSearchContext.class);
		this.displayValue = displayValue;
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
				return "#";
			}
			
			@Override
			public ButtonActionType target() {
				return ButtonActionType.POPUP;
			}
			
			@Override
			public String label() {
				return "Select";
			}
			
			@Override
			public String id() {
				return "UserSelectButton";
			}
		});
	}

	public static class UserSearchContext{
		
		@SearchFilterOnUI(label = "First Name")
		@SearchColumnOnUI(colName="First Name", rank=1)
		public String firstName;
		
		@SearchFilterOnUI(label = "Last Name")
		@SearchColumnOnUI(colName="Last Name", rank=2)
		public String lastName;
	}

	@Override
	public String searchUrl() {
		return routes.userSearch.search.url;
	}

	@Override
	public String entityName() {
		return "USER_SEARCH";
	}

	@Override
	public GridViewModel doSearch(DynamicForm form) {
		Expression exp =  super.doSearchExpression(form, SearchType.Like);
		int page = Integer.parseInt(form.get("page"));
		int limit = Integer.parseInt(form.get("rows"));
		GridViewModel.PageData pageData = new PageData(limit,
				page);
		
		User user = User.findByEmail(form.data().get("email"));
		int count=0;
		if(exp == null ){
			count = models.User.find.where().and(Expr.and(Expr.ne("designation", "Admin"),Expr.eq("companyobject", user.companyobject)),Expr.ne("email", user.email)).findRowCount();
		}else{
			count = models.User.find.where().and(Expr.and(Expr.ne("designation", "Admin"),Expr.eq("companyobject", user.companyobject)),Expr.ne("email", user.email)).add(exp).findRowCount();
		}
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
		List<models.User> results =  exp == null ?models.User.find.where().and(Expr.and(Expr.ne("designation", "Admin"),Expr.eq("companyobject", user.companyobject)),Expr.ne("email", user.email)).setFirstRow(start).setMaxRows(limit).findList()
				:models.User.find.where().and(Expr.and(Expr.ne("designation", "Admin"),Expr.eq("companyobject", user.companyobject)),Expr.ne("email", user.email)).add(exp).setFirstRow(start).setMaxRows(limit).findList();
		List<GridViewModel.RowViewModel> rows = transform(results, toJqGridFormat()) ;
		GridViewModel gridViewModel = new GridViewModel(pageData, count, rows);
		return gridViewModel;
	}

	@Override
	public String triggerUrl() {
		return routes.userSearch.index.url;
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
	public String toString() {
		return displayValue;
	}
	
	

}
