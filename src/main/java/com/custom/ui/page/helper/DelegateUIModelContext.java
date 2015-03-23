package com.custom.ui.page.helper;

import com.custom.ui.page.proxy.DelegateUIProxyModel;
import com.mnt.core.helper.ProxyUI;
import com.mnt.core.helper.UIModelHelper;
import com.mnt.core.ui.component.UIButton;
import com.mnt.time.controller.routes;


public class DelegateUIModelContext extends UIModelHelper<DelegateUIProxyModel>{

	public static DelegateUIModelContext getInstance(){
		return new DelegateUIModelContext();
	}
	
	public DelegateUIModelContext() {
		super(DelegateUIProxyModel.class,null);
	}
	
	
	public DelegateUIModelContext(DelegateUIProxyModel p) {
		super(DelegateUIProxyModel.class,p);
	}

	
	public DelegateUIModelContext(Class<DelegateUIProxyModel> model) {
		super(model);
	}

	public DelegateUIModelContext(Class<? extends ProxyUI> object,ProxyUI valueObject) {
		super(object,valueObject);
	}
	
	

	@Override
	protected void buildButtons() {
		 super.getButtonActions().add(new UIButton() {
			
			@Override
			public boolean visibility() {
				return true;
			}
			
			@Override
			public String url() {
				return routes.Delegate.save.url;
			}
			
			@Override
			public ButtonActionType target() {
				return ButtonActionType.SUBMIT;
			}
			
			@Override
			public String label() {
				return "Save";
			}
			
			@Override
			public String id() {
				return "delegateButton";
			}
		});
		 
	}

	@Override
	public String pageName() {
		return "delegate";
	}

	@Override
	public String pageTitle() {
		return "Delegate";
	}

	@Override
	public String pageSubTitle() {
		return null;
	}

		
}


