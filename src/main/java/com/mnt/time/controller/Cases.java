package com.mnt.time.controller;

import static com.google.common.collect.Lists.transform;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Attachment;
import models.CaseData;
import models.CaseFlexi;
import models.CaseNotes;
import models.Department;
import models.FlexiAttribute;
import models.RoleLevel;
import models.User;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import play.data.DynamicForm;
import play.libs.Json;

import com.custom.domain.RoleDomain;
import com.custom.helpers.CaseSave;
import com.custom.helpers.CaseSearchContext;
import com.custom.helpers.CaseToSearchContext;
import com.google.common.base.Function;
import com.mnt.core.domain.DomainEnum;
import com.mnt.core.domain.FileAttachmentMeta;
import com.mnt.core.ui.component.AutoComplete;
import com.mnt.createProject.model.Projectinstance;
import com.mnt.createProject.vm.CaseDateVM;
import com.mnt.createProject.vm.Case_flexiVM;
import com.mnt.createProject.vm.CasesNotsAndAttVM;
import com.mnt.createProject.vm.ProjectAttachmentVM;
import com.mnt.createProject.vm.caseSupportVM;

import dto.fixtures.MenuBarFixture;

/*@Security.Authenticated(Secured.class)
@BasicAuth*/

@Controller
public class Cases {
	
	
	@Value("${imageRootDir}")
	String rootDir;
	
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
		//List<CaseFlexi> casef = CaseFlexi.getCasesAttId(id);
		
		String modelName= "models.CaseFlexi";
		List<FlexiAttribute> fAttribute = FlexiAttribute.getIdByModel(modelName);

		for (FlexiAttribute fAttribute2 : fAttribute) {
			if (fAttribute2.getType().equals("FILE")) {
			//	if (casef.size() != 0) {
					//for (CaseFlexi cFlexi : casef) {

				CaseFlexi cFlexi= CaseFlexi.getIDByFlexi(id,fAttribute2.getId());
				if(cFlexi != null){
						Case_flexiVM caseFlexi1 = new Case_flexiVM();
						caseFlexi1.setCaseId(String.valueOf(cFlexi
								.getCaseData().getId()));

						List<FileAttachmentMeta> list = null;
						if (cFlexi.getValue() != null && cFlexi.getValue() != "") {

							try {

								
								
								
							System.out.println("OOOPOPOPO");
							System.out.println("OOOPOPOPO1");
							System.out.println("OOOPOPOPO2");
							
								
								
								
								
								
								
								list = new ObjectMapper()
										.readValue(
												cFlexi.getValue(),TypeFactory.defaultInstance().constructCollectionType(List.class,FileAttachmentMeta.class));

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
							if (list.get(0) != null) {
								caseFlexi1.setValue(list.get(0).n);
							}
						}
						caseFlexi1.setId(String.valueOf(cFlexi.getId()));
						caseFlexi.add(caseFlexi1);
						
					//}
				}
			}
		}
		
		List<CaseNotes> casenote = CaseNotes.getCasesNotesId(id);
		List<CasesNotsAndAttVM>  pList = new ArrayList<CasesNotsAndAttVM>();
		
		for(CaseNotes cNotes:casenote){
			CasesNotsAndAttVM pVm = new CasesNotsAndAttVM();
			pVm.setCaseComment(cNotes.getCasenote());
			pVm.setCommetDate(cNotes.getNoteDate());
			User user = User.findById(cNotes.getNoteUser());
			pVm.setUserName(user.getFirstName());
			pVm.setUserId(user.getId());
			
			List<Attachment> attachment1 = Attachment.getAttachmentByNotesId(cNotes.getId());
			List<ProjectAttachmentVM> attachment = new ArrayList<ProjectAttachmentVM>();
			for(Attachment att:attachment1){
				ProjectAttachmentVM projVm = new ProjectAttachmentVM();
				projVm.setDocName(att.getFileName());
				projVm.setDocDate(att.getFileDate());
				projVm.setId(att.getId());
				attachment.add(projVm);
			}
			pVm.setProjectAtt(attachment);
			pList.add(pVm);
		}
		
		casedata.setComment(pList);
		casedata.setCaseFlexi(caseFlexi);
		return casedata;
		
		
	}
	
	
	@RequestMapping(value="/saveFileAndNotes",method=RequestMethod.POST) 
	public @ResponseBody Long saveFileAndNotes(@RequestParam("file")MultipartFile file,caseSupportVM caseSVM,@CookieValue("username")String username) {
		
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		createDir(rootDir,caseSVM.getType(),caseSVM.getCaseId());
			String[] filenames = file.getOriginalFilename().split("\\.");
			String filename = rootDir+File.separator+ caseSVM.getType() + File.separator +caseSVM.getCaseId()+  File.separator + file.getOriginalFilename();
			
			File f = new File(filename);
			try {
				file.transferTo(f);
		
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			Date dt = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			
			User user = User.findByEmail(username);
			CaseNotes cNotes = new CaseNotes();
			cNotes.setCasenote(caseSVM.getComment());
			cNotes.setCasedata(CaseData.findById(Long.parseLong(caseSVM.caseId)));
			cNotes.setNoteUser(user.getId());
			try {
				cNotes.setNoteDate(format.parse(sdf.format(dt)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cNotes.save();
			
			Attachment attachment = new Attachment();
			attachment.setFileName(file.getOriginalFilename());
			try {
				attachment.setFileDate(format.parse(sdf.format(dt)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			attachment.setCaseNotes(CaseNotes.getById(cNotes.getId()));
			attachment.setType(caseSVM.getType());
			attachment.save();
			
		return null;
		
		//return null;
		
	}
	
	public static void createDir(String rootDir,String caseType, String casaId) {
        File file1 = new File(rootDir + File.separator+ caseType +File.separator+ casaId);
        if (!file1.exists()) {
                file1.mkdirs();
        }
	}
	
	@RequestMapping(value = "/downloadCaseNotesFile", method = RequestMethod.POST)
	@ResponseBody
	public FileSystemResource downloadCaseNotesFile(final HttpServletResponse response, @RequestParam(value = "attchId", required = true) final String attchId)
	{
	
		 Attachment attachment = Attachment.getById(Long.parseLong(attchId));
		 response.setContentType("application/x-download");
         response.setHeader("Content-Transfer-Encoding", "binary"); 
         response.setHeader("Content-disposition","attachment; filename=\""+attachment.getFileName());
         File file = new File(rootDir+File.separator+ attachment.getType() + File.separator + attachment.getCaseNotes().getCasedata().getId() +  File.separator + attachment.getFileName());
         
         return new FileSystemResource(file);
		//return null;
		
	}
	
	@RequestMapping(value = "/downloadCaseFile", method = RequestMethod.POST)
	@ResponseBody
	public FileSystemResource getattchfile(final HttpServletResponse response, @RequestParam(value = "attchId", required = true) final String attchId)
	{
	
		CaseFlexi cFlexi = CaseFlexi.getById(Long.parseLong(attchId));
		
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
				
		
		 response.setContentType("application/x-download");
         response.setHeader("Content-Transfer-Encoding", "binary"); 
         response.setHeader("Content-disposition","attachment; filename=\""+list.get(0).n);
         File file = new File(rootDir+File.separator+ "caseData" + File.separator + cFlexi.getCaseData().getId()  + File.separator + list.get(0).n);
         
         return new FileSystemResource(file);
		
		
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
