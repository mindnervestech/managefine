package com.custom.helpers;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Feedback;
import models.User;
import play.data.DynamicForm;
import utils.ExceptionHandler;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import com.google.common.base.Function;
import com.mnt.core.helper.ASearchContext;
import com.mnt.core.ui.component.BuildUIButton;
import com.mnt.core.ui.component.UIButton;
import com.mnt.core.utils.GridViewModel;
import com.mnt.core.utils.GridViewModel.PageData;
import com.mnt.core.utils.GridViewModel.RowViewModel;
import com.mnt.time.controller.routes;

public class FeedbackSearchContext extends ASearchContext<Feedback>{
	
	public static FeedbackSearchContext getInstance(){
		return new FeedbackSearchContext();
	}
	
	public String entityName(){
		return Feedback.ENTITY;
	}

	public FeedbackSearchContext() {
		super(Feedback.class,null);
	}
	
	
	public FeedbackSearchContext(Feedback p) {
		super(Feedback.class,p);
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
		super.getButtonActions().add(new UIButton() {
			
			@Override
			public boolean visibility() {
				return true;
			}
			
			@Override
			public String url() {
				return "/feedbackDisplay";
			}
			
			@Override
			public ButtonActionType target() {
				return ButtonActionType.MODAL;
			}
			
			@Override
			public String label() {
				return "View Feedback";
			}
			
			@Override
			public String id() {
				return "viewFeedbackBtn";
			}
		});
	}
	@Override
	public String onDoubleRowClickCallID(){
		return "viewFeedbackBtn";
	}

	@Override
	public String searchUrl() {
		return routes.Feedbacks.searchIndex.url;
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
		return routes.Feedbacks.display.url;
	}

	public GridViewModel doSearch(DynamicForm form) {
		User user = User.find.where().add(Expr.eq("email", form.data().get("userEmail"))).findUnique();
		Expression exp = Expr.eq("user_id", user.id);
		
		String fromDateFromUI = form.get("fromDateWindow");
        String toDateFromUI = form.get("toDateWindow");
        SimpleDateFormat TargerdateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat OrignaldateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat customDateFormat = new SimpleDateFormat("dd MMM yyyy");
        
        try {
        	if(fromDateFromUI != null && toDateFromUI != null){
				Date todate = OrignaldateFormat.parse(toDateFromUI);
				Date fromdate = OrignaldateFormat.parse(fromDateFromUI);
				fromDateFromUI= TargerdateFormat.format(fromdate);  
				toDateFromUI= TargerdateFormat.format(todate);  
		        exp = Expr.and(exp, Expr.between("feedbackDate",fromDateFromUI, toDateFromUI));
        	}	
		} catch (ParseException e) {
			ExceptionHandler.onError(e);
		}
		List<Feedback> results = Feedback.find.where().add(exp).findList();
		
		List<Feedback> feedbackResult= new ArrayList<Feedback>(results.size());
		for(int i=0; i<results.size();i++)
		{
			Feedback feedback = new Feedback();
			feedback.id = results.get(i).id;
			feedback.rating = results.get(i).rating;
			feedback.feedbackDateDisplay = customDateFormat.format(results.get(i).feedbackDate);
			feedback.feedbackFromManager = results.get(i).manager.getFirstName() +" "+results.get(i).manager.getLastName(); 
			feedbackResult.add(feedback);
		}
		
		int page = Integer.parseInt(form.get("page"));
		int limit = Integer.parseInt(form.get("rows"));
		GridViewModel.PageData pageData = new PageData(limit,
				page);
		
		int count = 0;
		if(exp == null ){
			count = Feedback.find.where().findRowCount();
		}else{
			count = Feedback.find.where().add(exp).findRowCount();
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
		List<GridViewModel.RowViewModel> rows = transform(feedbackResult, toJqGridFormat()) ;
		GridViewModel gridViewModel = new GridViewModel(pageData, count, rows);
		return gridViewModel;
	}
	
	private  Function<Feedback,RowViewModel> toJqGridFormat() {
		return new Function<Feedback, RowViewModel>() {
			@Override
			public RowViewModel apply(Feedback feedback) {
				try {
					return new GridViewModel.RowViewModel((feedback.id).intValue(), newArrayList(getResultStr(feedback)));
				} catch (Exception e) {
					ExceptionHandler.onError(e);
				} 
				return null;
			}
		};
	}
}