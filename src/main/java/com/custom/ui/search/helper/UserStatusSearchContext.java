package com.custom.ui.search.helper;

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
import com.custom.ui.search.proxy.UserProxyUISearch;
import com.google.common.base.Function;
import com.mnt.core.helper.ASearchContext;
import com.mnt.core.ui.component.BuildGridActionButton;
import com.mnt.core.ui.component.BuildUIButton;
import com.mnt.core.ui.component.GridActionButton;
import com.mnt.core.ui.component.UIButton;
import com.mnt.core.ui.component.UIButton.ButtonActionType;
import com.mnt.core.utils.GridViewModel;
import com.mnt.core.utils.GridViewModel.PageData;
import com.mnt.core.utils.GridViewModel.RowViewModel;
import com.mnt.time.controller.routes;


public class UserStatusSearchContext extends ASearchContext<UserProxyUISearch>{
	
	private static UserStatusSearchContext searchContext = null;
	
	public static UserStatusSearchContext getInstance(){
		return new UserStatusSearchContext();
	}
	
	@Override
	public boolean isMultiSelectSearch() {
		return false;
	}
	
	public String entityName(){
		return "User";
	}

	public UserStatusSearchContext() {
		super(UserProxyUISearch.class,null);
	}
	public UserStatusSearchContext(UserProxyUISearch u) {
		super(UserProxyUISearch.class,u);
	}
	
	@Override
	public void buildGridButton() {
		getGridActions().add(BuildGridActionButton.me().withVisibilityTrue().withIcon(GridActionButton.IconType.Tick)
				.withUrl(routes.Users.showEdit.url).
				withTooltip("Approve User").withTargetModal(ButtonActionType.EDIT_TYPE));
		getGridActions().add(BuildGridActionButton.me().withVisibilityTrue()
				.withIcon(GridActionButton.IconType.Cross)
				.withUrl(routes.Status.disapproveUserStatus.url).withTooltip("Disapprove User"));
		
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
		return routes.Users.edit.url;
	}
	
	@Override
	public String showEditUrl() {
		return routes.Users.showEdit.url;
	}
	@Override
	public String generateExcel() {
		return routes.Status.excelReportUser.url;
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
	public HSSFWorkbook doExcel(DynamicForm form) {
		String email = form.data().get("email");
		
		User user1 = User.findByEmail(email);
		Expression exp1 = Expr.eq("companyobject.companyCode", user1.companyobject.getCompanyCode());
		Expression exp2 = Expr.ne("email", user1.email);
		List<User> result = null;
		if(form.get("status")==null){
			result=User.find.where().eq("userStatus", Status.PendingApproval).add(exp1).add(exp2).findList();
		}else{
			result=User.find.where().ilike("userStatus",form.get("status")).add(exp1).add(exp2).findList();
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
		try {
			 return super.getExcelExport(userResult);
		} catch (Exception e) {
			ExceptionHandler.onError(e);
		}
		return null;
	}
		
	
	public GridViewModel doSearch(DynamicForm form) {
		String email = form.data().get("email");
		
		User user1 = User.findByEmail(email);
		Expression exp1 = Expr.eq("companyobject.companyCode", user1.companyobject.getCompanyCode());
		Expression exp2 = Expr.ne("email", user1.email);
				
			
		int page = Integer.parseInt(form.get("page"));
		int limit = Integer.parseInt(form.get("rows"));
		GridViewModel.PageData pageData = new PageData(limit,
				page);
		int count=0;
		
		double min = Double.parseDouble(form.get("rows"));
		int total_pages=0;
		if(form.get("status") == null ){
			count = User.find.where().eq("userStatus", Status.PendingApproval).add(exp1).add(exp2).findRowCount();
		}else{
			count = User.find.where().ilike("userStatus", form.get("status")).add(exp1).add(exp2).findRowCount();
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
			result=User.find.where().eq("userStatus", Status.PendingApproval).add(exp1).add(exp2).setFirstRow(start).setMaxRows(limit).findList();
		}else{
			result=User.find.where().ilike("userStatus",form.get("status")).add(exp1).add(exp2).setFirstRow(start).setMaxRows(limit).findList();
		}
		
		
		List<GridViewModel.RowViewModel> rows = transform(result, toJqGridFormat()) ;
		GridViewModel gridViewModel = new GridViewModel(pageData, count, rows);
		return gridViewModel;	
	}
	
	private  Function<User,RowViewModel> toJqGridFormat() {
		return new Function<User, RowViewModel>() {
			@Override
			public RowViewModel apply(User user) {
				try {
					return new GridViewModel.RowViewModel((user.id).intValue(), newArrayList(getResultStr(User.class,user)));
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
