package com.mnt.time.controller;

import static play.data.Form.form;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import models.User;
import play.data.DynamicForm;
import play.data.Form;
//import play.mvc.Controller;


import com.custom.ui.page.helper.DelegateUIModelContext;
import com.custom.ui.page.proxy.DelegateUIProxyModel;
import com.custom.ui.search.helper.UserSearchUI;

import dto.fixtures.MenuBarFixture;


/*@Security.Authenticated(Secured.class)
@BasicAuth*/

@Controller
public class Delegate  {
	@RequestMapping(value="/delegateIndex" , method = RequestMethod.GET)
	public String index(ModelMap model, @CookieValue("username") String username){
		User user = User.findByEmail(username);
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user",user);
		model.addAttribute("_pageContext",new DelegateUIModelContext(DelegateUIProxyModel.class).build());
		
		models.Delegate delegate = new models.Delegate();
		//model.addAttribute(attributeValue)
		delegate = models.Delegate.find.where().eq("delegator",user).findUnique();
		
		if(delegate == null)
		{
			model.addAttribute("_pageContext",new DelegateUIModelContext(DelegateUIProxyModel.class).build());
			
			return "delegateIndex";
		}
		else
		{
			DelegateUIProxyModel proxyModel = new DelegateUIProxyModel();
			proxyModel.fromDate = delegate.fromDate;
			proxyModel.toDate = delegate.toDate;
			proxyModel.delegateTo = delegate.delegatedTo;
			model.addAttribute("_pageContext",new DelegateUIModelContext(proxyModel).build());
			
			return "delegateIndex";
		}	
	}
	@RequestMapping(value="/delagationSave" , method = RequestMethod.GET)
	public String save(HttpServletRequest request,@CookieValue("username") String username){
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		User user = User.findByEmail(username);
		Form<models.Delegate> delegateForm = form(models.Delegate.class).bindFromRequest(request);
		models.Delegate delegatep = new models.Delegate();
		delegatep = models.Delegate.find.where().eq("delegator",user).findUnique();
		
		if(delegatep == null)
		{
			models.Delegate delegate = new models.Delegate();
			delegate = delegateForm.get();
			delegate.delegatedTo = User.find.byId(Long.parseLong(form.get("delegateTo")));
			delegate.delegator = user;
			delegate.save();
			return "redirect:"+routes.Delegate.index.url;
		}
		else
		{
			Long id = delegatep.id;
			delegatep = delegateForm.get();
			delegatep.delegatedTo = User.find.byId(Long.parseLong(form.get("delegateTo")));
			delegatep.delegator = user;
			delegatep.update(id);
			return "redirect:"+routes.Delegate.index.url;
		}
	}
	
}
