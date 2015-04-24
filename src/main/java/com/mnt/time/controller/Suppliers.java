package com.mnt.time.controller;

import static com.google.common.collect.Lists.transform;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import models.RoleLevel;
import models.Supplier;
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
import com.custom.helpers.SupplierSave;
import com.custom.helpers.SupplierSearchContext;
import com.google.common.base.Function;
import com.mnt.core.ui.component.AutoComplete;

import dto.fixtures.MenuBarFixture;

@Controller
public class Suppliers {

	@RequestMapping(value = "/supplierSearch", method = RequestMethod.GET)
	public @ResponseBody
	String search(ModelMap model, @CookieValue("username") String username,
			HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("email", username);
		return Json.toJson(
				SupplierSearchContext.getInstance().build().doSearch(form))
				.toString();
	}

	@RequestMapping(value = "/supplierEdit", method = RequestMethod.POST)
	public @ResponseBody String edit(HttpServletRequest request) {
		try {
			SupplierSave saveUtils = new SupplierSave();
			saveUtils.doSave(true, request);
		} catch (Exception e) {
			// ExceptionHandler.onError(request().uri(),e);
		}
		return "Suppliers Edited Successfully";
	}

	@RequestMapping(value = "/supplierExcelReport", method = RequestMethod.GET)
	public @ResponseBody
	String excelReport(ModelMap model,
			@CookieValue("username") String username, HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("email", username);
		model.addAttribute("context", SupplierSearchContext.getInstance().build());
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

	@RequestMapping(value = "/supplierIndex", method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest request,@CookieValue("username") String username) {
		User user = User.findByEmail(username);
		model.addAttribute("context", SupplierSearchContext.getInstance().build());
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);

		return "supplierIndex";
	}

	@RequestMapping(value = "/supplierDelete", method = RequestMethod.GET)
	public String delete() {
		return "";
	}

	@RequestMapping(value = "/findSupplier", method = RequestMethod.GET)
	public @ResponseBody
	String findSuppliers(HttpServletRequest request, @CookieValue("username") String username) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		String query = form.get("query");
		ObjectNode result = Json.newObject();
		List<AutoComplete> results = transform(Suppliers.findSuppliers(query, username), toAutoCompleteFormatForSupplier());
		result.put("results", Json.toJson(results));
		return Json.toJson(result).toString();
	}

	private static Function<Supplier, AutoComplete> toAutoCompleteFormatForSupplier() {
		return new Function<Supplier, AutoComplete>() {
			@Override
			public AutoComplete apply(Supplier supplier) {
				return new AutoComplete(supplier.supplierName, supplier.email,
						supplier.contactEmail, supplier.id);
			}
		};
	}

	@RequestMapping(value = "/supplierCreate", method = RequestMethod.POST)
	public @ResponseBody String create(HttpServletRequest request,
			@CookieValue("username") String username) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		String userName = form.get("email");
		
		RoleLevel r = RoleLevel.getRoleByName("Supplier");
		String password = Application.generatePassword();
		User u = new User();
		u.setEmail(userName);
		u.setCompanyobject(User.findByEmail(username).companyobject);
		u.setRole(r);
		u.setTempPassword(1);
		u.setUsertype("Supplier User");
		u.setPassword(password);
		u.setUserStatus(com.custom.domain.Status.Approved);
		u.save();
		try {
			Map<String, Object> extra = new HashMap<String, Object>();
			extra.put("company", User.findByEmail(username).companyobject);
			extra.put("user", u);
			SupplierSave saveUtils = new SupplierSave(extra);
			saveUtils.doSave(false, request);
		} catch (Exception e) {
			// ExceptionHandler.onError(request().uri(),e);
		}
		return "Suppliers Created Successfully";
	}

	@RequestMapping(value = "/supplierShowEdit", method = RequestMethod.GET)
	public String showEdit(ModelMap model,
			@CookieValue("username") String username, HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		Long id = null;
		try {
			id = Long.valueOf(form.get("query"));
			model.addAttribute("_searchContext",
					new SupplierSearchContext(Supplier.findById(id)).build());
			return "editWizard";

		} catch (NumberFormatException e) {
			e.printStackTrace();
			//ExceptionHandler.onError(request().uri(),e);
		}
		return "Not able to show Results, Check Logs";

	}
	public static List<Supplier> findSuppliers(String query,String username) {
		User user = User.findByEmail(username);
		List<Supplier> supplier =  Supplier.find.where().and(Expr.eq("company.companyCode", user.companyobject.getCompanyCode()),Expr.ilike("supplierName", query+"%")).findList();
		return supplier;
	}
}
