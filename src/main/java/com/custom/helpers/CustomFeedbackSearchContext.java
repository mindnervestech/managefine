package com.custom.helpers;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.util.ArrayList;
import java.util.List;

import models.User;
import play.data.DynamicForm;
import utils.ExceptionHandler;

import com.custom.ui.search.proxy.FeedbackProxyUISearch;
import com.google.common.base.Function;
import com.mnt.core.helper.ASearchContext;
import com.mnt.core.ui.component.BuildUIButton;
import com.mnt.core.ui.component.UIButton;
import com.mnt.core.utils.GridViewModel;
import com.mnt.core.utils.GridViewModel.PageData;
import com.mnt.core.utils.GridViewModel.RowViewModel;
import com.mnt.time.controller.routes;

public class CustomFeedbackSearchContext extends ASearchContext<FeedbackProxyUISearch>{
	
	public static CustomFeedbackSearchContext getInstance(){
		return new CustomFeedbackSearchContext();
	}
	
	public String entityName(){
		return FeedbackProxyUISearch.ENTITY;
	}

	public CustomFeedbackSearchContext() {
		super(FeedbackProxyUISearch.class,null);
	}
	
	
	public CustomFeedbackSearchContext(FeedbackProxyUISearch p) {
		super(FeedbackProxyUISearch.class,p);
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
	protected void buildButton() {
		super.getButtonActions().add(BuildUIButton.me().withVisibilityTrue().withTargetModal().withLabel("Feedback").withID("feedbackButton").withUrl("/feedbackCreate"));
	}

	@Override
	public String searchUrl() {
		return routes.Feedbacks.customSearchIndex.url;
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
	public String deleteUrl() {
		return "#";
	}

	@Override
	public String showEditUrl() {
		return "/feedbackCreate";
	}
	
	@Override
	public String onDoubleRowClickCallID(){
		return "feedbackButton";
	}

	public GridViewModel doSearch(DynamicForm form) {
		List<User> users = User.find.where().eq("manager.email", form.data().get("userEmail")).findList();
		List<FeedbackProxyUISearch> results = new ArrayList<FeedbackProxyUISearch>();
		for(int i=0 ; i<= users.size()-1; i++){
			FeedbackProxyUISearch myBucket = new FeedbackProxyUISearch();
			myBucket.id = users.get(i).id;
			myBucket.employeeID = users.get(i).id;
			myBucket.firstName = users.get(i).firstName;
			myBucket.lastName = users.get(i).lastName;
			results.add(i, myBucket);
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
		
		int start = limit*page - limit;//orderBy(sidx+" "+sord)
		List<GridViewModel.RowViewModel> rows = transform(results, toJqGridFormat()) ;
		GridViewModel gridViewModel = new GridViewModel(pageData, count, rows);
		return gridViewModel;
	}
	
	private  Function<FeedbackProxyUISearch,RowViewModel> toJqGridFormat() {
		return new Function<FeedbackProxyUISearch, RowViewModel>() {
			@Override
			public RowViewModel apply(FeedbackProxyUISearch employeeFeedbacks) {
				try {
					return new GridViewModel.RowViewModel((employeeFeedbacks.id).intValue(), newArrayList(getResultStr(employeeFeedbacks)));
				} catch (Exception e) {
					ExceptionHandler.onError(e);
				} 
				return null;
			}
		};
	}

	
}