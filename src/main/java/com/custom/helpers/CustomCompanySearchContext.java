package com.custom.helpers;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.Company;
import models.User;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import play.data.DynamicForm;
import utils.ExceptionHandler;

import com.avaje.ebean.Expression;
import com.custom.domain.Status;
import com.custom.ui.search.proxy.CompanyProxyUISearch;
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

public class CustomCompanySearchContext extends ASearchContext<CompanyProxyUISearch>{
	
	public static CustomCompanySearchContext getInstance(){
		return new CustomCompanySearchContext();
	}
	
	@Override
	public boolean isMultiSelectSearch() {
		return true;
	}
	
	public String entityName(){
		return "Company";
	}

	public CustomCompanySearchContext() {
		super(CompanyProxyUISearch.class,null);
	}
	public CustomCompanySearchContext(CompanyProxyUISearch u) {
		super(CompanyProxyUISearch.class,u);
	}
	
	@Override
	public void buildGridButton() {
		getGridActions().add(BuildGridActionButton.me().withVisibilityTrue().withIcon(GridActionButton.IconType.Tick).withUrl("/companyStatusApprove").withTooltip("Approve Company"));
		getGridActions().add(BuildGridActionButton.me().withVisibilityTrue().withIcon(GridActionButton.IconType.Cross).withUrl("/companyStatusDisapprove").withTooltip("Disapprove Company"));
		
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
	                    return "/companyStatusApprove";
	              }
	             
	              @Override
	              public ButtonActionType target() {
	                    return ButtonActionType.ACTION;
	              }
	             
	              @Override
	              public String label() {
	                    return "Approve Company";
	              }
	             
	              @Override
	              public String id() {
	                    return "companyApproveButton";
	              }
	           });
			super.getButtonActions().add(new UIButton() {
				
	            @Override
	            public boolean visibility() {
	                  return true;
	            }
	            @Override
	            public String url() {
	                  return "/companyStatusDisapprove";
	            }
	           
	            @Override
	            public ButtonActionType target() {
	                  return ButtonActionType.ACTION;
	            }
	           
	            @Override
	            public String label() {
	                  return "Disapprove Company";
	            }
	           
	            @Override
	            public String id() {
	                  return "companyDisapproveButton";
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
		return routes.Status.companySearch.url;
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
	public String generateExcel() {
		return routes.Status.excelReportCompany.url;
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
		Expression exp =  super.doSearchExpression(form, SearchType.Like);
		List<Company> result = null;
		
		if(form.get("status")==null){
			result = Company.find.where().eq("companyStatus", Status.PendingApproval).findList();
		}else{
			result = Company.find.where().ilike("companyStatus",form.get("status")).findList();
		}
		List<CompanyProxyUISearch> companyResult= new ArrayList<CompanyProxyUISearch>(result.size());
		
		for(int i=0; i<result.size();i++)
		{
			CompanyProxyUISearch company = new CompanyProxyUISearch();
			company.id = result.get(i).id;
			company.companyName = result.get(i).companyName;
			company.companyCode=result.get(i).getCompanyCode();
			company.companyEmail= result.get(i).companyEmail;
			companyResult.add(company);
		}
		try {
			 return super.getExcelExport(companyResult);
		} catch (Exception e) {
			ExceptionHandler.onError(e);
		}
		return null;
	}
		
	
	public GridViewModel doSearch(DynamicForm form) {
		Expression exp =  super.doSearchExpression(form, SearchType.Like);
		List<Company> result = null;
		if(form.get("status")==null){
			result = Company.find.where().eq("companyStatus", Status.PendingApproval).findList();
		}else{
			result = Company.find.where().ilike("companyStatus",form.get("status")).findList();
		}
		List<CompanyProxyUISearch> companyResult= new ArrayList<CompanyProxyUISearch>(result.size());
		
		for(int i=0; i<result.size();i++)
		{
			CompanyProxyUISearch company = new CompanyProxyUISearch();
			company.id = result.get(i).id;
			company.companyName = result.get(i).companyName;
			company.companyCode=result.get(i).getCompanyCode();
			company.companyEmail= result.get(i).companyEmail;
			companyResult.add(company);
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
		
		List<GridViewModel.RowViewModel> rows = transform(companyResult, toJqGridFormat()) ;
		GridViewModel gridViewModel = new GridViewModel(pageData, count, rows);
		return gridViewModel;	
	}
	
	private  Function<CompanyProxyUISearch,RowViewModel> toJqGridFormat() {
		return new Function<CompanyProxyUISearch, RowViewModel>() {
			@Override
			public RowViewModel apply(CompanyProxyUISearch user) {
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



