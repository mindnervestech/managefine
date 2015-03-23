package com.custom.helpers;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.User;
import play.data.DynamicForm;
import utils.ExceptionHandler;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import com.custom.ui.search.proxy.UserProxyUISearch;
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


public class CustomUserSearchContext extends ASearchContext<UserProxyUISearch>{
	
	private static CustomUserSearchContext searchContext = null;
	
	public static CustomUserSearchContext getInstance(){
		//if(searchContext == null){
		return new CustomUserSearchContext();
		//}
		//return searchContext;
	}
	
	@Override
	public boolean isMultiSelectSearch() {
		return true;
	}
	
	public String entityName(){
		return "User";
	}

	public CustomUserSearchContext() {
		super(UserProxyUISearch.class,null);
	}
	public CustomUserSearchContext(UserProxyUISearch u) {
		super(UserProxyUISearch.class,u);
	}
	
	@Override
	public void buildGridButton() {
		getGridActions().add(BuildGridActionButton.me().withVisibilityTrue().withIcon(GridActionButton.IconType.Tick).withUrl("/userStatusApprove").withTooltip("Approve User"));
		getGridActions().add(BuildGridActionButton.me().withVisibilityTrue().withIcon(GridActionButton.IconType.Cross).withUrl("/userStatusdisapprove").withTooltip("Disapprove User"));
		
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
                    return "/userStatusApprove";
              }
             
              @Override
              public ButtonActionType target() {
                    return ButtonActionType.ACTION;
              }
             
              @Override
              public String label() {
                    return "Approve User";
              }
             
              @Override
              public String id() {
                    return "userApproveButton";
              }
           });
		super.getButtonActions().add(new UIButton() {
			
            @Override
            public boolean visibility() {
                  return true;
            }
           
            @Override
            public String url() {
                  return "/userStatusdisapprove";
            }
           
            @Override
            public ButtonActionType target() {
                  return ButtonActionType.ACTION;
            }
           
            @Override
            public String label() {
                  return "Disapprove User";
            }
           
            @Override
            public String id() {
                  return "userdisapproveButton";
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
		return routes.Status.userSearch.url;
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
		Expression exp =  super.doSearchExpression(form, SearchType.Like);
		String email = form.data().get("email");
		
		User user1 = User.findByEmail(email);
		Expression exp1 = Expr.eq("companyobject.companyCode", user1.companyobject.getCompanyCode());
		Expression exp2 = Expr.ne("email", user1.email);
		
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
		if(exp == null ){
			count = User.find.where().ilike("userStatus","Approved").add(exp1).add(exp2).findRowCount();
		}else{
			count = User.find.where().ilike("userStatus","Approved").add(exp1).add(exp2).findRowCount();
		}
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
		
		
		List<User> result = null;
		if(form.get("status")==null){
			result=User.find.where().ilike("userStatus","Approved").add(exp1).add(exp2).setFirstRow(start).setMaxRows(limit).findList();
		}else{
			result=User.find.where().ilike("userStatus",form.get("status")).add(exp1).add(exp2).setFirstRow(start).setMaxRows(limit).findList();
		}
		
		List<UserProxyUISearch> userResult= new ArrayList<UserProxyUISearch>(result.size());
		
		for(int i=0; i<result.size();i++)
		{
			UserProxyUISearch user = new UserProxyUISearch();
			if(result.get(i).designation != "SuperAdmin" && result.get(i).designation != "Admin" && result.get(i).companyobject != null)
			{
				user.id = result.get(i).id;
				user.firstName = result.get(i).firstName;
				user.lastName=result.get(i).lastName;
				user.email= result.get(i).email;
				user.companyName= result.get(i).companyobject.companyName;
				userResult.add(user);
			}
		}
		
		
		List<GridViewModel.RowViewModel> rows = transform(userResult, toJqGridFormat()) ;
		GridViewModel gridViewModel = new GridViewModel(pageData, count, rows);
		return gridViewModel;	
	}
	
	private  Function<UserProxyUISearch,RowViewModel> toJqGridFormat() {
		return new Function<UserProxyUISearch, RowViewModel>() {
			@Override
			public RowViewModel apply(UserProxyUISearch user) {
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
