package com.mnt.time.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Company;
import models.MailSetting;
import models.Notification;
import models.User;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import play.data.DynamicForm;
import play.libs.Json;

import com.avaje.ebean.Expr;
import com.custom.domain.BlackListedPermissions;
import com.custom.domain.Permissions;
import com.custom.emails.Email;
import com.custom.helpers.CustomCompanySearchContext;
import com.custom.ui.search.helper.UserStatusSearchContext;

import dto.fixtures.MenuBarFixture;

@Controller
public class Status {

	private static final String DELIMITER = "[|]";
	@RequestMapping(value="/userStatusIndex", method=RequestMethod.GET)
	public String userIndex(ModelMap model,@CookieValue("username")String username) {
		User user = User.findByEmail(username);
		model.addAttribute("context", UserStatusSearchContext.getInstance().build());
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);
		return "userStatus";
	}
	
	@RequestMapping(value="/userStatusSearch",method=RequestMethod.GET)
	public @ResponseBody String userSearch(@CookieValue ("username") String username,HttpServletRequest request)
	{
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("email",username);
		return Json.toJson(UserStatusSearchContext.getInstance().build().doSearch(form)).toString();
    }
	
	@RequestMapping(value="/userStatusApprove", method=RequestMethod.GET)
	public @ResponseBody String approveUserStatus(@CookieValue("username")String username,String query){
		String result = "";
		
		Permissions[] permissions = Permissions.values();
		for(Permissions p :permissions)
		{
			if(!(p == Permissions.Home))
			{
				result += p+ DELIMITER;
			}
		}
		
		for(String ids : query.split(",")){
			User user = User.find.byId(Long.parseLong(ids));
			user.setUserStatus(com.custom.domain.Status.Approved);
			//user.userStatus = com.custom.domain.Status.Approved;
			user.manager =  User.findByEmail(username);
			
			user.update();
			
			//Send Email to User After Approval
			MailSetting smtpSetting = MailSetting.find.where().eq("companyObject", user.companyobject).findUnique();
			String recipients = "";
			String subject = "";
			String body = "";
			
			recipients = user.email;
			subject = "Approval By Admin";
			body = "Congratulation !!! You are Approved By Admin..." +
					"\nNow You can Login the TimeTrotter System!!";
			//Email.sendOnlyMail(smtpSetting,recipients, subject, body);
		}
		Integer count = Application.count(username);
		String notification = count.toString(); 
		String message = "The Selected User have been Approved Successfully. So Please Fill the User Detail in Manage User Section";
		Map<String,String> jsonMap = new HashMap<String,String>();
		jsonMap.put("count", notification);
		jsonMap.put("message", message);
		return Json.toJson(jsonMap).toString();
	}
	@RequestMapping(value="/userStatusExcelReport", method=RequestMethod.GET)	
	public String excelReportUser(@CookieValue("username") String username, HttpServletResponse response,HttpServletRequest request) throws IOException {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("email",username);
		HSSFWorkbook hssfWorkbook =  UserStatusSearchContext.getInstance().build().doExcel(form);
		File f = new File("excelReport.xls");
		FileOutputStream fileOutputStream = new FileOutputStream(f);
		hssfWorkbook.write(fileOutputStream);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=excelReport.xls");
		//return ok(f).as("application/vnd.ms-excel");
		return "";
    }

	@RequestMapping(value="/userStatusdisapprove", method=RequestMethod.GET)
	public @ResponseBody String disapproveUserStatus(String query,@CookieValue("username") String username){
		for(String ids : query.split(",")){
			User user = User.find.byId(Long.parseLong(ids));
			user.setUserStatus(com.custom.domain.Status.Disapproved);
			//user.userStatus = com.custom.domain.Status.Disapproved;
			if(user.getPermissions() != null){
				user.setPermissions(null);
			}
			user.update();
			
			//Send Email to User After Approval
			MailSetting smtpSetting = MailSetting.find.where().eq("companyObject", user.companyobject).findUnique();
			String recipients = "";
			String subject = "";
			String body = "";
			
			recipients = user.email;
			subject = "Disapproved By Admin";
			body = "Sorry !!! You are Disapproved By Admin..." +
					"\nPlease Contact Your Company Admin Regarding This!!";
			//Email.sendOnlyMail(smtpSetting,recipients, subject, body);
		}
		Integer count = Application.count(username);
		String notification = count.toString(); 
		String message = "The Selected User Has Been Disapproved Successfully";
		Map<String,String> jsonMap = new HashMap<String,String>();
		jsonMap.put("count", notification);
		jsonMap.put("message", message);
		return Json.toJson(jsonMap).toString();
	}
	
	@RequestMapping(value="/companyStatusIndex", method=RequestMethod.GET)
	public String companyIndex(ModelMap model,@CookieValue("username")String username) {
		User user = User.findByEmail(username);
		//session().get("email");
		//request().username();
		model.addAttribute("context", CustomCompanySearchContext.getInstance().build());
		model.addAttribute("_menuContext",MenuBarFixture.build(username));
		model.addAttribute("user", user);
		return "companyStatus";
	}
	@RequestMapping(value="/companyStatusSearch", method=RequestMethod.GET)	
	public @ResponseBody String companySearch(HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		return Json.toJson(CustomCompanySearchContext.getInstance().build().doSearch(form)).toString();
    }
	@RequestMapping(value="/companyStatusApprove", method=RequestMethod.GET)		
	public @ResponseBody String approveCompanyStatus(String query,@CookieValue("username")String username){
		for(String ids : query.split(",")){
			Company company = Company.find.byId(Long.parseLong(ids));
			company.setCompanyStatus(com.custom.domain.Status.Approved);
			company.update();
			
			User companyAdmin = new User();
			MailSetting mailNew = new MailSetting();
			Notification notification = new Notification();
			String password = Application.generatePassword();
			companyAdmin.firstName = company.getCompanyCode()+" Admin";
			companyAdmin.designation = "Admin";
			companyAdmin.companyobject = company;
			companyAdmin.email = company.companyEmail;
			companyAdmin.userStatus = com.custom.domain.Status.Approved;
			companyAdmin.password = password;
			companyAdmin.tempPassword = 1;
			mailNew.companyObject = company;
			mailNew.userName = company.companyEmail;
			notification.company = company;
			notification.save();
			mailNew.save();
			companyAdmin.setPermissions(BlackListedPermissions.BLACKLISTED_PERMISSIONS_FOR_ADMIN);
			companyAdmin.save();
			
			//Send Email to company Admin After Approval
			String recipients = "";
			String subject = "";
			String body = "";
			
			recipients = companyAdmin.email;
			subject = "Company Approved by Super Admin";
			body = "Company "+company.companyName + " is Approved by Super Admin." +
					"\nYour Login Details are:" +
					"\nUsername :"+ companyAdmin.email+
					"\nPassword :"+ companyAdmin.password+
					"\n\nNow You Can Login Timesheet Trotter!";
			
			User superAdmin = User.find.where().eq("designation", "SuperAdmin").findUnique();
			MailSetting smtpSetting = MailSetting.find.where().eq("companyObject",superAdmin.companyobject).findUnique();
			try {
			Email.sendOnlyMail(smtpSetting,recipients, subject, body);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		Integer count = Application.count(username);
		String notification = count.toString(); 
		String message = "The Selected Company have been Approved Successfully.";
		Map<String,String> jsonMap = new HashMap<String,String>();
		jsonMap.put("count", notification);
		jsonMap.put("message", message);
		return Json.toJson(jsonMap).toString();
	}
	
	@RequestMapping(value="/companyStatusExcelReport", method=RequestMethod.GET)			
	public String excelReportCompany(@CookieValue("username") String username,HttpServletRequest request) throws IOException {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("email",username);
		HSSFWorkbook hssfWorkbook =  CustomCompanySearchContext.getInstance().build().doExcel(form);
		File f = new File("excelReport.xls");
		FileOutputStream fileOutputStream = new FileOutputStream(f);
		hssfWorkbook.write(fileOutputStream);
		return "";
		//return ok(f).as("application/vnd.ms-excel");
    }
	
	@RequestMapping(value="/companyStatusDisapprove", method=RequestMethod.GET)			
	public @ResponseBody String disapproveCompanyStatus(String query,@CookieValue("username") String username){
		for(String ids : query.split(",")){
			Company company = Company.find.byId(Long.parseLong(ids));
			company.setCompanyStatus( com.custom.domain.Status.Disapproved);
			company.update();
			
			User companyAdmin = User.find.where().and(Expr.eq("companyobject.companyCode",company.getCompanyCode()), Expr.eq("designation", "Admin")).findUnique();
			if (companyAdmin != null) {
				MailSetting mailDel = MailSetting.find.where().eq("companyObject", companyAdmin.companyobject).findUnique();
				if(mailDel != null){
					MailSetting.find.ref(mailDel.id).delete();
				}
				Notification notify = Notification.find.where().eq("company", companyAdmin.companyobject).findUnique();
				if(notify != null){
					Notification.find.ref(notify.id).delete();
				}
				User.find.ref(companyAdmin.id).delete();
				
				if(companyAdmin.getPermissions() != null){
					companyAdmin.setPermissions(null);
				}
			}
			
			
			String recipients = "";
			String subject = "";
			String body = "";
			
			recipients = company.companyEmail;
			subject = "Company Disapproved by Super Admin";
			body = "Company "+company.companyName + " is Disapproved by Super Admin. \nPlease Contact Super Admin Regarding This!";
			
			User superAdmin = User.find.where().eq("designation", "SuperAdmin").findUnique();
			MailSetting smtpSetting = MailSetting.find.where().eq("companyObject",superAdmin.companyobject).findUnique();
			Email.sendOnlyMail(smtpSetting,recipients, subject, body);
		}
		Integer count = Application.count(username);
		String notification = count.toString(); 
		String message = "The Selected Company Has Been Disapproved Successfully";
		Map<String,String> jsonMap = new HashMap<String,String>();
		jsonMap.put("count", notification);
		jsonMap.put("message", message);
		return Json.toJson(jsonMap).toString();
	}
	
}
