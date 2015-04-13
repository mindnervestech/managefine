package com.mnt.time.controller;

import static com.google.common.collect.Lists.transform;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.CaseData;
import models.CaseFlexi;
import models.CaseNotes;
import models.Department;
import models.LeaveBalance;
import models.LeaveLevel;
import models.LeavesCredit;
import models.MailSetting;
import models.Project;
import models.RoleLevel;
import models.RoleX;
import models.Supplier;
import models.Task;
import models.User;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import play.data.DynamicForm;
import play.libs.Json;
import utils.ExceptionHandler;
import viewmodel.ProjectVM;
import viewmodel.TaskVM;

import com.avaje.ebean.Expr;
import com.avaje.ebean.SqlRow;
import com.custom.domain.RoleDomain;
import com.custom.domain.Status;
import com.custom.emails.Email;
import com.custom.helpers.CaseSave;
import com.custom.helpers.CaseSearchContext;
import com.custom.helpers.ClientSave;
import com.custom.helpers.ProjectSave;
import com.custom.helpers.SupplierSave;
import com.custom.helpers.SupplierSearchContext;
import com.custom.helpers.UserSave;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mnt.core.domain.DomainEnum;
import com.mnt.core.ui.component.AutoComplete;
import com.mnt.roleHierarchy.model.Role;
import com.mnt.time.controller.Application.Login;
import com.mnt.time.controller.routes.Application.login;
import com.mnt.time.controller.routes.Status.companyIndex;

import dto.fixtures.MenuBarFixture;

/*@Security.Authenticated(Secured.class)
@BasicAuth*/

@Controller
public class Cases {
	
	@RequestMapping( value="/caseSearch", method= RequestMethod.GET )
	public @ResponseBody String search(HttpServletRequest request,@CookieValue("username") String username) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("email", username);
		return Json.toJson(CaseSearchContext.getInstance().build().doSearch(form)).toString();
    }
	
	@RequestMapping( value="/caseShowEdit ", method= RequestMethod.GET )
	public String showEdit(ModelMap model, HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		Long id = null;
		try {
			id = Long.valueOf(form.get("query"));
			model.addAttribute("_searchContext",
					new CaseSearchContext(CaseData.findById(id)).build());
			return "editWizard";

		} catch (NumberFormatException e) {
			e.printStackTrace();
			//ExceptionHandler.onError(request().uri(),e);
		}
		return "Not able to show Results, Check Logs";
		
	}
	
	@RequestMapping( value="/caseExcelReport", method = RequestMethod.GET)
	public String excelReport(@CookieValue("username") String username, HttpServletRequest request,HttpServletResponse response ) throws IOException {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		
		response.setContentType("application/vnd.ms-excel");
		form.data().put("email", username);
		HSSFWorkbook hssfWorkbook =  CaseSearchContext.getInstance().build().doExcel(form);
		File f = new File("excelReport.xls");
		FileOutputStream fileOutputStream = new FileOutputStream(f);
		hssfWorkbook.write(fileOutputStream);
		
		response.setHeader("Content-Disposition", "attachment; filename=excelReport.xls");
//		return ok(f).as();
		return "application/vnd.ms-excel";
    }
	
	@RequestMapping( value="/caseEdit", method = RequestMethod.POST)
	public @ResponseBody String edit(@CookieValue("username")String username,HttpServletRequest request) throws Exception {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		User currentuser = User.findByEmail(username);
		Date notedate = new Date(); 
		Map<String, String> extra = new HashMap<String, String>();
		extra.put("note", form.get("notes"));
		extra.put("notedate", notedate.toString());
		extra.put("currentuser", currentuser.getId().toString());
		Map<String, Object> extra1 = new HashMap<String, Object>();
		extra1.put("company", User.findByEmail(username).companyobject);
		CaseSave saveUtils = new CaseSave(extra1);
		Long id  = (Long)saveUtils.doSave(true, request);
		CaseData cd = CaseData.findById(id);
		CaseNotes cnote = new CaseNotes();
		cnote.setCasedata(cd);
		cnote.setNoteDate(notedate);
		cnote.setNoteUser(currentuser.getId());
		cnote.setCasenote(cd.getNotes());
		cnote.save();
		
		return "Cases Edited Successfully";
    }
	@RequestMapping(value="/findPro", method=RequestMethod.GET)
	public @ResponseBody String findProject(@CookieValue("username") String username,HttpServletRequest requset){
		DynamicForm form = DynamicForm.form().bindFromRequest(requset);
		String query = form.get("query");
		System.out.println(query);
		ObjectNode result = Json.newObject();
		List<AutoComplete> results = transform(Projects.findProjectByName(query,username), toAutoCompleteFormatForProject());
		result.put("results", Json.toJson(results));
		return Json.toJson(result).toString();
	}
	
	
	private static Function<models.User,AutoComplete> toAutoCompleteFormatForPM() {
		return new Function<models.User, AutoComplete>() {
			@Override
			public AutoComplete apply(models.User user) {
					return new AutoComplete(user.firstName,user.lastName,user.email,user.id);
			}
		};
	}
	
	
	private  Function<Project,AutoComplete> toAutoCompleteFormatForProject() {
		return new Function<Project, AutoComplete>() {
			@Override
			public AutoComplete apply(Project project) {
					return new AutoComplete(project.projectCode,project.projectName,project.clientName.clientName,project.id);
			}
		};
	}
	
	
	@RequestMapping(value="/caseIndex", method = RequestMethod.GET)
	public String index(ModelMap model,@CookieValue("username") String username) {
		User user = User.findByEmail(username);
		List<Role> role = Role.getRoleList();
		List<Department> deptr = Department.findAll();
		//RoleX role = RoleX.find.where(Expr.eq("company", user.companyobject)).findUnique();
		List<DomainEnum> roleX = new ArrayList<DomainEnum>();
		List<DomainEnum> dept = new ArrayList<DomainEnum>();
		if(role != null){
			for(int i=0; i<role.size(); i++){
				roleX.add(new RoleDomain(role.get(i).getId()+"",role.get(i).getRoleName(),false));
			}
		}
		if(deptr != null){
			for(int i=0; i<deptr.size(); i++){
				dept.add(new RoleDomain(deptr.get(i).getId()+"",deptr.get(i).getName(),false));
			}
		}
		user.rolex = roleX;
		user.dept = dept;
		
		model.addAttribute("context", CaseSearchContext.getInstance().build());
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);
		
		return "caseIndex";
		/*return ok(userIndex.render(UserSearchContext.getInstance().build(),MenuBarFixture.build(request().username()),user));*/
    }
	
	@RequestMapping(value="/caseDelete", method=RequestMethod.GET)
	public String delete() {
		return "";
    }

	@RequestMapping(value="/caseCreate", method = RequestMethod.POST)
	public @ResponseBody String create(HttpServletRequest request,@CookieValue("username") String username) throws Exception{
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		User currentuser = User.findByEmail(username);
		Date notedate = new Date(); 
		Map<String, String> extra = new HashMap<String, String>();
		extra.put("note", form.get("notes"));
		extra.put("notedate", notedate.toString());
		extra.put("currentuser", currentuser.getId().toString());
		Map<String, Object> extra1 = new HashMap<String, Object>();
		extra1.put("company", User.findByEmail(username).companyobject);
		CaseSave saveUtils = new CaseSave(extra1);
		Long id  = (Long)saveUtils.doSave(false, request);
		CaseData cd = CaseData.findById(id);
		CaseNotes cnote = new CaseNotes();
		cnote.setCasedata(cd);
		cnote.setNoteDate(notedate);
		cnote.setNoteUser(currentuser.getId());
		cnote.setCasenote(cd.getNotes());
		cnote.save();
		
		return "Case Created Successfully";
    }
	

}
