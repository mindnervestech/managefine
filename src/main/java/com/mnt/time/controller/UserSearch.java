package com.mnt.time.controller;

import static play.data.Form.form;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import play.data.DynamicForm;
import play.libs.Json;

import com.custom.ui.search.helper.UserSearchUI;
import com.mnt.core.helper.SearchUI;

@Controller
public class UserSearch{
	private static SearchUI searchUI = new UserSearchUI().build();
	
	@RequestMapping( value="/userGenericSearchIndex", method= RequestMethod.GET )
	public String index(ModelMap model){
		model.addAttribute("_searchContext", searchUI);
		return "views/searchPopup";//ok(views.html.searchPopup.render(searchUI));
	}
	
	@RequestMapping( value="/userGenericDoSearch", method= RequestMethod.GET )
	public @ResponseBody String search(ModelMap model, HttpServletRequest request, @CookieValue("username") String username) {
		//needs to ask
		DynamicForm form = form().bindFromRequest(request);
		form.data().put("email",username);
		return Json.toJson(searchUI.doSearch(form)).asText();
    }
}
