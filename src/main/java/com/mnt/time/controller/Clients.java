package com.mnt.time.controller;

import static com.google.common.collect.Lists.transform;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import models.Client;
import models.User;

import org.codehaus.jackson.node.ObjectNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import play.data.DynamicForm;
import play.libs.Json;

import com.avaje.ebean.Expr;
import com.custom.helpers.ClientSave;
import com.custom.helpers.ClientSearchContext;
import com.google.common.base.Function;
import com.mnt.core.ui.component.AutoComplete;

import dto.fixtures.MenuBarFixture;

@Controller
public class Clients {

	@RequestMapping(value = "/clientSearch", method = RequestMethod.GET)
	public @ResponseBody
	String search(ModelMap model, @CookieValue("username") String username,
			HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("email", username);
		return Json.toJson(
				ClientSearchContext.getInstance().build().doSearch(form))
				.toString();
	}

	@RequestMapping(value = "/clientEdit", method = RequestMethod.POST)
	public @ResponseBody String edit(HttpServletRequest request) {
		try {
			ClientSave saveUtils = new ClientSave();
			saveUtils.doSave(true, request);
		} catch (Exception e) {
			// ExceptionHandler.onError(request().uri(),e);
		}
		return "Clients Edited Successfully";
	}

	@RequestMapping(value = "/clientExcelReport", method = RequestMethod.GET)
	public @ResponseBody
	String excelReport(ModelMap model,
			@CookieValue("username") String username, HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("email", username);
		model.addAttribute("context", ClientSearchContext.getInstance().build());
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", MenuBarFixture.build(username));

		// HSSFWorkbook hssfWorkbook =
		// ClientSearchContext.getInstance().build().doExcel(form);
		File f = new File("excelReport.xls");
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// hssfWorkbook.write(fileOutputStream);
		return "application/vnd.ms-excel";
	}

	@RequestMapping(value = "/clientIndex", method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest request,@CookieValue("username") String username) {
		User user = User.findByEmail(username);
		model.addAttribute("context", ClientSearchContext.getInstance().build());
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);

		return "clientIndex";
	}

	@RequestMapping(value = "/clientDelete", method = RequestMethod.GET)
	public String delete() {
		return "";
	}

	@RequestMapping(value = "/findClient", method = RequestMethod.GET)
	public @ResponseBody
	String findClients(HttpServletRequest request, @CookieValue("username") String username) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		String query = form.get("query");
		ObjectNode result = Json.newObject();
		List<AutoComplete> results = transform(Clients.findClients(query, username), toAutoCompleteFormatForClient());
		result.put("results", Json.toJson(results));
		return Json.toJson(result).toString();
	}

	private static Function<Client, AutoComplete> toAutoCompleteFormatForClient() {
		return new Function<Client, AutoComplete>() {
			@Override
			public AutoComplete apply(Client client) {
				return new AutoComplete(client.clientName, client.email,
						client.contactEmail, client.id);
			}
		};
	}

	@RequestMapping(value = "/clientCreate", method = RequestMethod.POST)
	public @ResponseBody String create(HttpServletRequest request,
			@CookieValue("username") String username) {
		try {
			Map<String, Object> extra = new HashMap<String, Object>();
			extra.put("company", User.findByEmail(username).companyobject);
			ClientSave saveUtils = new ClientSave(extra);
			saveUtils.doSave(false, request);
		} catch (Exception e) {
			// ExceptionHandler.onError(request().uri(),e);
		}
		return "Clients Created Successfully";
	}

	@RequestMapping(value = "/clientShowEdit", method = RequestMethod.GET)
	public String showEdit(ModelMap model,
			@CookieValue("username") String username, HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		Long id = null;
		try {
			id = Long.valueOf(form.get("query"));
			model.addAttribute("_searchContext",
					new ClientSearchContext(Client.findById(id)).build());
			return "editWizard";

		} catch (NumberFormatException e) {
			e.printStackTrace();
			//ExceptionHandler.onError(request().uri(),e);
		}
		return "Not able to show Results, Check Logs";

	}
	public static List<Client> findClients(String query,String username) {
		User user = User.findByEmail(username);
		List<Client> clients =  Client.find.where().and(Expr.eq("company.companyCode", user.companyobject.getCompanyCode()),Expr.ilike("clientName", query+"%")).findList();
		return clients;
	}
}
