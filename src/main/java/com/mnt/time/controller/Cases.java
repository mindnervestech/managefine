package com.mnt.time.controller;

import static com.google.common.collect.Lists.transform;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.CaseData;
import models.CaseNotes;
import models.Department;
import models.Project;
import models.RoleLevel;
import models.User;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import play.data.DynamicForm;
import play.libs.Json;

import com.custom.domain.RoleDomain;
import com.custom.helpers.CaseSave;
import com.custom.helpers.CaseSearchContext;
import com.custom.helpers.CaseToSearchContext;
import com.google.common.base.Function;
import com.mnt.core.domain.DomainEnum;
import com.mnt.core.ui.component.AutoComplete;
<<<<<<< HEAD
import com.mnt.createProject.model.Projectinstance;
import com.mnt.roleHierarchy.model.Role;
import com.mnt.time.controller.Application.Login;
import com.mnt.time.controller.routes.Application.login;
import com.mnt.time.controller.routes.Status.companyIndex;
=======
>>>>>>> b7af703564f9abd555eeacd3a8b6fb6f32c67546

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
		List<AutoComplete> results = transform(Projectinstance.getProjectUser(username), toAutoCompleteFormatForProject());
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
	
	
	private  Function<Projectinstance,AutoComplete> toAutoCompleteFormatForProject() {
		return new Function<Projectinstance, AutoComplete>() {
			@Override
			public AutoComplete apply(Projectinstance project) {
					return new AutoComplete(project.projectName,project.projectName,project.clientName,project.id);
			}
		};
	}
	
	
	@RequestMapping(value="/caseIndex", method = RequestMethod.GET)
	public String index(ModelMap model,@CookieValue("username") String username) {
		User user = User.findByEmail(username);
		List<RoleLevel> role = RoleLevel.getRoleList();
		List<Department> deptr = Department.findAll();
		//RoleX role = RoleX.find.where(Expr.eq("company", user.companyobject)).findUnique();
		List<DomainEnum> roleX = new ArrayList<DomainEnum>();
		List<DomainEnum> dept = new ArrayList<DomainEnum>();
		if(role != null){
			for(int i=0; i<role.size(); i++){
				roleX.add(new RoleDomain(role.get(i).getId()+"",role.get(i).getRole_name(),false));
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
		extra1.put("status", "Assigned");
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
	
	
	
	@RequestMapping(value="/findCaseFile",method=RequestMethod.GET)
	public @ResponseBody CaseDateVM findCaseFile(@RequestParam("id")Long id) {
		
		CaseDateVM casedata = new CaseDateVM();
		List<Case_flexiVM> caseFlexi = new ArrayList<Case_flexiVM>();
		List<CaseFlexi> casef = CaseFlexi.getCasesAttId(id);
		
		
		for(CaseFlexi cFlexi:casef){
			Case_flexiVM caseFlexi1 = new Case_flexiVM();
			caseFlexi1.setCaseId(String.valueOf(cFlexi.getCaseData().getId()));
			
			List<FileAttachmentMeta> list = null;
			try {
				
				list = new ObjectMapper().readValue(cFlexi.getValue(),
						TypeFactory.defaultInstance().constructCollectionType(List.class,FileAttachmentMeta.class));
				
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			caseFlexi1.setValue(list.get(0).n);
			caseFlexi1.setId(String.valueOf(cFlexi.getId()));
			caseFlexi.add(caseFlexi1);
		}
		
		List<CaseNotes> casenote = CaseNotes.getCasesNotesId(id);
		List<ProjectCommentVM>  pList = new ArrayList<ProjectCommentVM>();
		
		for(CaseNotes cNotes:casenote){
			ProjectCommentVM pVm = new ProjectCommentVM();
			pVm.setProjectComment(cNotes.getCasenote());
			pVm.setCommetDate(cNotes.getNoteDate());
			User user = User.findById(cNotes.getNoteUser());
			pVm.setUserName(user.getFirstName());
			pVm.setUserId(user.getId());
			pList.add(pVm);
		}
		
		casedata.setComment(pList);
		casedata.setCaseFlexi(caseFlexi);
		return casedata;
		
		
	}
	
	

	@RequestMapping(value="/caseToIndex", method = RequestMethod.GET)
	public String toIndex(ModelMap model,@CookieValue("username") String username) {
		User user = User.findByEmail(username);

		model.addAttribute("context", CaseToSearchContext.getInstance().build());
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);
		
		return "caseToIndex";
		/*return ok(userIndex.render(UserSearchContext.getInstance().build(),MenuBarFixture.build(request().username()),user));*/
    }
	
	@RequestMapping( value="/caseToSearch", method= RequestMethod.GET )
	public @ResponseBody String searchTo(HttpServletRequest request,@CookieValue("username") String username) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("email", username);
		return Json.toJson(CaseToSearchContext.getInstance().build().doSearch(form)).toString();
    }
	
	@RequestMapping(value="/caseToCreate", method = RequestMethod.POST)
	public @ResponseBody String createTo(HttpServletRequest request,@CookieValue("username") String username) throws Exception{
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		System.out.println("case too");
		User currentuser = User.findByEmail(username);
		Date notedate = new Date(); 
		String status = form.get("caseStatus");
		String note = form.get("caseNote");
		CaseData cas = CaseData.findById(Long.parseLong(form.get("caseform")));
		cas.setStatus(status);
		cas.setNotes(note);
		cas.update();
		CaseNotes cnote = new CaseNotes();
		cnote.setCasedata(cas);
		cnote.setNoteDate(notedate);
		cnote.setNoteUser(currentuser.getId());
		cnote.setCasenote(cas.getNotes());
		cnote.save();
		return "Case Updated Successfully";
    }
	
	@RequestMapping( value="/caseToEdit", method = RequestMethod.POST)
	public @ResponseBody String editTo(@CookieValue("username")String username,HttpServletRequest request) throws Exception {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		User currentuser = User.findByEmail(username);
		Date notedate = new Date(); 
		
		return "Cases Edited Successfully";
    }

	@RequestMapping( value="/caseToShowEdit ", method= RequestMethod.GET )
	public String showEditTo(ModelMap model, HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		Long id = null;
		try {
			id = Long.valueOf(form.get("query"));
			model.addAttribute("_searchContext",
					new CaseToSearchContext(CaseData.findById(id)).build());
			return "editWizard";

		} catch (NumberFormatException e) {
			e.printStackTrace();
			//ExceptionHandler.onError(request().uri(),e);
		}
		return "Not able to show Results, Check Logs";
		
	}
	
}
