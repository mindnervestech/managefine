package com.mnt.createProject.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Client;
import models.Supplier;
import models.User;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import play.data.DynamicForm;

import com.custom.domain.ProjectStatus;
import com.google.gson.Gson;
import com.mnt.createProject.model.AduitLog;
import com.mnt.createProject.model.Pits;
import com.mnt.createProject.model.ProjectAttachment;
import com.mnt.createProject.model.Projectinstance;
import com.mnt.createProject.model.Projectinstancenode;
import com.mnt.createProject.model.Saveattributes;
import com.mnt.createProject.vm.AttributDataVM;
import com.mnt.createProject.vm.DefinePartVM;
import com.mnt.projectHierarchy.model.Projectclassnode;
import com.mnt.projectHierarchy.model.Projectclassnodeattribut;
import com.mnt.projectHierarchy.vm.ProjectsupportattributVM;

import dto.fixtures.MenuBarFixture;
@Controller
public class CreateProjectController {

	@Autowired
	com.mnt.createProject.service.CreateProjectService createProjectService;
	
	@Value("${imageRootDir}")
	String imageRootDir;
	
	@RequestMapping(value="/importPartNo",method=RequestMethod.GET)
	public String importPartNo(@CookieValue("username")String username,Model model) {
		
			model.addAttribute("_menuContext", MenuBarFixture.build(username));
   	model.addAttribute("user", User.findByEmail(username));
		return "importPartNo";
	}
	
	@RequestMapping(value="/selectAllProjectType",method=RequestMethod.GET) 
	public @ResponseBody List selectAllProjectType(@RequestParam("id")Long id,@RequestParam("rootId")Long rootId) {
		return createProjectService.selectAllProjectType(id,rootId);
	}
	
	
	@RequestMapping(value="/getAllHistory",method=RequestMethod.GET) 
	public @ResponseBody List getAllHistory(@RequestParam("mainInstance")Long mainInstance) {
		return createProjectService.getAllHistory(mainInstance);
	}
	
	@RequestMapping(value="/AddJspPage",method=RequestMethod.GET)
	public String AddJspPage(@RequestParam("id")Long id,@RequestParam("mainInstance")Long mainInstance,Model model) {
		model.addAttribute("nodeMetaData",createProjectService.getAddJspPage(id,mainInstance));
		return "nodeMetaData";
	}
	
	@RequestMapping(value="/EditJspPage",method=RequestMethod.GET)
	public String EditJspPage(@RequestParam("id")Long id,@RequestParam("mainInstance")Long mainInstance,Model model) {
		model.addAttribute("editNodeMetaData",createProjectService.getAddJspPage(id,mainInstance));
		return "editNodeMetaData";
	}
	
	@RequestMapping(value="/edit/project/AddJspPage",method=RequestMethod.GET)
	public String AddJspPageProject(@RequestParam("id")Long id,@RequestParam("mainInstance")Long mainInstance,Model model) {
		 
		model.addAttribute("nodeMetaData",createProjectService.getAddJspPage(id,mainInstance));
		return "nodeMetaData";
	}

	@RequestMapping(value="/edit/project/EditJspPage",method=RequestMethod.GET)
	public String EditJspPageProject(@RequestParam("id")Long id,@RequestParam("mainInstance")Long mainInstance,Model model) {
		model.addAttribute("editNodeMetaData",createProjectService.getAddJspPage(id,mainInstance));
		return "editNodeMetaData";
	}
	
	@RequestMapping(value="/findAttachFile",method=RequestMethod.GET)
	public @ResponseBody ProjectsupportattributVM findAttachFile(@RequestParam("id")Long id,@RequestParam("mainInstance")Long mainInstance) {
		
		return createProjectService.findAttachFile(id,mainInstance);
	}
	
	@RequestMapping(value="/findattributes",method=RequestMethod.GET)
	public @ResponseBody AttributDataVM findattributes(@RequestParam("mainInstance")Long mainInstance) {
		
		return createProjectService.findattributes(mainInstance);
	}
	
	@RequestMapping(value="/saveComment",method=RequestMethod.POST) 
	public @ResponseBody Long saveComment(@RequestBody ProjectsupportattributVM pVm,@CookieValue("username")String username) {
		return createProjectService.saveComment(pVm,username);
	}
	
	
	@RequestMapping(value="/saveAttribues",method=RequestMethod.POST) 
	public @ResponseBody Long saveAttribues(@RequestBody AttributDataVM aDataVm,@CookieValue("username")String username) {
		return createProjectService.saveAttribues(aDataVm,username);
	}
	
	
	
	@RequestMapping(value="/saveDefineParts",method=RequestMethod.POST) 
	public @ResponseBody Long saveDefineParts(@RequestBody DefinePartVM dpVm,@CookieValue("username")String username) {
		return createProjectService.saveDefineParts(dpVm,username);
	}
		
	@RequestMapping(value="/saveprojectTypeandName",method=RequestMethod.POST) 
	public String saveprojectTypeandName(HttpServletRequest request,@CookieValue("username")String username,Model model) {
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
    	model.addAttribute("user", User.findByEmail(username));
		return "redirect:" + "/edit/project/"+createProjectService.saveprojectTypeandName(request,username).id;
	}
	
	
	@RequestMapping(value="/findCliect",method=RequestMethod.GET)
	public @ResponseBody List findCliect() {
		return createProjectService.getfindCliect();
	}
	
	@RequestMapping(value="/findUser",method=RequestMethod.GET)
	public @ResponseBody List findUser() {
		return createProjectService.getfindUser();
	}
	
	@RequestMapping(value="/getAllPartNo",method=RequestMethod.GET)
	public @ResponseBody List getAllPartNo(@CookieValue("username")String username){
		return createProjectService.getAllPartNo(username);		
	}
	
	@RequestMapping(value="/getAllDefinePartData",method=RequestMethod.GET)
	public @ResponseBody DefinePartVM getAllDefinePartData(@RequestParam("id")Long projectId,@CookieValue("username")String username){
		return createProjectService.getAllDefinePartData(projectId,username);		
	}
	
	
	@RequestMapping(value="/findselectedAllUser",method=RequestMethod.GET)
	public @ResponseBody List findselectedAllUser(@RequestParam("mainInstance")Long mainInstance, @RequestParam("projectId")Long projectId) {
		return createProjectService.getfindselectedAllUser(mainInstance,projectId);
	}
	
	
	@RequestMapping(value="/selectedUser",method=RequestMethod.GET)
	public @ResponseBody List selectedUser(@RequestParam("mainInstance")Long mainInstance, @RequestParam("projectId")Long projectId) {
		return createProjectService.getselectedUser(mainInstance,projectId);
	}
	
	@RequestMapping(value="/selectedSupplier",method=RequestMethod.GET)
	public @ResponseBody List selectedSupplier(@RequestParam("mainInstance")Long mainInstance) {
		return createProjectService.getselectedSupplier(mainInstance);
	}
		
	
	@RequestMapping(value="/findSupplierData",method=RequestMethod.GET)
	public @ResponseBody List findSupplier() {
		return createProjectService.getfindSupplier();
	}
	
	
	
	@RequestMapping(value="/findPits",method=RequestMethod.GET)
	public @ResponseBody List findPits() {
		List<Pits> pits= Pits.getAllPits();
		return pits;
	}
	
	@RequestMapping(value="/edit/project/{projectId}",method=RequestMethod.GET)
	public String showEditProject(@PathVariable("projectId")Long projectId,@CookieValue("username")String username,Model model){
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
    	model.addAttribute("user", User.findByEmail(username));
    //	model.addAttribute("usertype", User.findByUserType());
		model.addAttribute("createProject",createProjectService.editprojectTypeandName(projectId));
		return "createProject";
		
	}
	
	@RequestMapping(value="/saveCreateProjectAttributes" ,method=RequestMethod.POST)		
	public @ResponseBody Long saveCreateProjectAttributes(@CookieValue("username")String username,
			HttpServletRequest request){
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = new Date();*/
		
		String supplierValues[] = request.getParameterValues("supplier");
		String memberValues[] = request.getParameterValues("member");
		
		List<Projectclassnodeattribut> attributesArray = null;
		Projectinstancenode projectnode = null;
		if(form.data().get("projectId") != null){
			attributesArray = Projectclassnodeattribut.getattributByprojectId(Long.parseLong(form.data().get("projectId")));
			projectnode = Projectinstancenode.getProjectParentId(Long.parseLong(form.data().get("projectId")), Long.parseLong(form.data().get("projectInstance")));
		}
		
		if(projectnode == null){
			
			Projectclassnode projectclassnode = Projectclassnode.getProjectById(Long.parseLong(form.data().get("projectId")));
			if(projectclassnode.getParentId() == null){
				
				Projectinstance projectinstance= Projectinstance.getById(Long.parseLong(form.data().get("projectInstance")));
				projectinstance.setStartDate(form.data().get("startDate"));
				projectinstance.setEndDate(form.data().get("endDate"));
				projectinstance.setProjectName(form.data().get("projectName"));
				projectinstance.setClientId(Long.parseLong(form.data().get("customer")));
				projectinstance.setEndCustomer(Client.findById(Long.parseLong(form.data().get("endCustomer"))));
				projectinstance.setProjectManager(User.findById(Long.parseLong(form.data().get("projectManager"))));
				projectinstance.setOpportunityNo(form.data().get("opportunityNo"));
				projectinstance.setCreatedDate(form.data().get("createdDate"));
				projectinstance.setRegion(form.data().get("region"));
				projectinstance.setEndCustomerLocation(form.data().get("endCustomerLocation"));
				projectinstance.setProjectNameApplication(form.data().get("projectNameApplication"));
				projectinstance.setProductionDate(form.data().get("productionDate"));
				projectinstance.setProductLifeTime(form.data().get("productLifeTime"));
				projectinstance.setSupplierRegistion(form.data().get("supplierRegistion"));
				projectinstance.setProjectLastUpdate(form.data().get("projectLastUpdate"));
				projectinstance.setSerialNo(form.data().get("serialNo"));
				projectinstance.setSupplierFae(form.data().get("supplierFae"));
				projectinstance.setProjectWin(form.data().get("projectWin"));
				projectinstance.setSupplierSaleperson(form.data().get("supplierSaleperson"));
				projectinstance.setPurchaseCustContactNo(form.data().get("purchaseCustContactNo"));
				projectinstance.setPurchaseCustEmail(form.data().get("purchaseCustEmail"));
				projectinstance.setRemark(form.data().get("remark"));
				projectinstance.setStatus(ProjectStatus.valueOf(form.data().get("status")));
				
				projectinstance.update();
				
				Projectinstance.getProjectsOfUserDelerte(projectinstance.getId());
				//projectinstance.removeAllUser();
				
				
				List<User> uList = new ArrayList<User>();
				if(memberValues != null)
					for(String s:memberValues){
						User user = User.findById(Long.parseLong(s));
						uList.add(user);
					}
				projectinstance.setUser(uList);
				projectinstance.saveManyToManyAssociations("user");

				//projectinstance.removeAllSupplier();
				Projectinstance.getProjectsOfSupplierDelerte(projectinstance.getId());
				List<Supplier> sList = new ArrayList<Supplier>();
				if(supplierValues != null)
					for(String sid:supplierValues){
						Supplier supplier = Supplier.findById(Long.parseLong(sid));
						sList.add(supplier);
					}
				projectinstance.setSupplier(sList);
				projectinstance.saveManyToManyAssociations("supplier");

			}
			Projectinstancenode projectinstancenode= new Projectinstancenode();
			projectinstancenode.setProjecttypeid(Long.parseLong(form.data().get("projecttypeId")));
			projectinstancenode.setProjectclassnode(Projectclassnode.getProjectById(Long.parseLong(form.data().get("projectId"))));
			projectinstancenode.setProjectinstanceid(Long.parseLong(form.data().get("projectInstance")));
			projectinstancenode.setWeightage(Integer.parseInt(form.data().get("weightage")));

			//projectinstancenode.setSupplier(Supplier.findById(Long.parseLong(form.data().get("supplier"))));
			//projectinstancenode.setUser(User.findById(Long.parseLong(form.data().get("member"))));

			try {
				projectinstancenode.setStartDate(format.parse(form.data().get("startDate")));
				projectinstancenode.setEndDate(format.parse(form.data().get("endDate")));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			projectinstancenode.save();
			//projectinstancenode.removeAllUser();
			Projectinstancenode.getProjectsinstanceNodeDelete(projectinstancenode.getId());
			List<User> uList = new ArrayList<User>();
			if(memberValues != null)
				for(String s:memberValues){
					User user = User.findById(Long.parseLong(s));
					uList.add(user);
				}
			projectinstancenode.setUser(uList);
			projectinstancenode.saveManyToManyAssociations("user");



			for(Projectclassnodeattribut attr : attributesArray){

				Saveattributes saveattri = new Saveattributes();
				String checkboxValue = null;
				checkboxValue = "";
				if(attr.getType().equalsIgnoreCase("Checkbox")) {

					String values[] = request.getParameterValues(attr.getName());
					for(String s:values){
						checkboxValue = checkboxValue + s +",";
					}

					saveattri.setAttributValue(checkboxValue);
				} else {
					saveattri.setAttributValue(form.data().get(attr.getName()));
				}
				saveattri.setProjectattrid(attr.getId());
				saveattri.setProjectinstancenode_id(projectinstancenode.getId());

				saveattri.save();

			}
						//compare(projectinstancenode,projectinstancenode,  "Projectinstancenode" , projectinstancenode.getId(), username);

		}else{

			Projectinstancenode projectinstancenodeOld = Projectinstancenode.getProjectParentId(Long.parseLong(form.data().get("projectId")),Long.parseLong(form.data().get("projectInstance")));
			Projectclassnode projectclassnode = Projectclassnode.getProjectById(Long.parseLong(form.data().get("projectId")));
			if(projectclassnode.getParentId() == null){
				Projectinstance projectinstance= Projectinstance.getById(Long.parseLong(form.data().get("projectInstance")));

				projectinstance.setStartDate(form.data().get("startDate"));
				projectinstance.setEndDate(form.data().get("endDate"));
				projectinstance.setProjectName(form.data().get("projectName"));
				projectinstance.setClientId(Long.parseLong(form.data().get("customer")));
				projectinstance.setEndCustomer(Client.findById(Long.parseLong(form.data().get("endCustomer"))));
				projectinstance.setProjectManager(User.findById(Long.parseLong(form.data().get("projectManager"))));
				projectinstance.setOpportunityNo(form.data().get("opportunityNo"));
				projectinstance.setCreatedDate(form.data().get("createdDate"));
				projectinstance.setRegion(form.data().get("region"));
				projectinstance.setEndCustomerLocation(form.data().get("endCustomerLocation"));
				projectinstance.setProjectNameApplication(form.data().get("projectNameApplication"));
				projectinstance.setProductionDate(form.data().get("productionDate"));
				projectinstance.setProductLifeTime(form.data().get("productLifeTime"));
				projectinstance.setSupplierRegistion(form.data().get("supplierRegistion"));
				projectinstance.setProjectLastUpdate(form.data().get("projectLastUpdate"));
				projectinstance.setSerialNo(form.data().get("serialNo"));
				projectinstance.setSupplierFae(form.data().get("supplierFae"));
				projectinstance.setSupplierSaleperson(form.data().get("supplierSaleperson"));
				projectinstance.setProjectWin(form.data().get("projectWin"));
				projectinstance.setStatus(ProjectStatus.valueOf(form.data().get("status")));
				
				projectinstance.update();

			//	projectinstance.removeAllUser();
				Projectinstance.getProjectsOfUserDelerte(projectinstance.getId());
				List<User> uList = new ArrayList<User>();
				if(memberValues != null)
					for(String s:memberValues){
						User user = User.findById(Long.parseLong(s));
						uList.add(user);
					}
				projectinstance.setUser(uList);
				projectinstance.saveManyToManyAssociations("user");

				//projectinstance.removeAllSupplier();
				Projectinstance.getProjectsOfSupplierDelerte(projectinstance.getId());
				List<Supplier> sList = new ArrayList<Supplier>();
				if(supplierValues != null)
					for(String sid:supplierValues){
						Supplier supplier = Supplier.findById(Long.parseLong(sid));
						sList.add(supplier);
					}
				projectinstance.setSupplier(sList);
				projectinstance.saveManyToManyAssociations("supplier");
			}
			Projectinstancenode projectinstancenode= Projectinstancenode.getProjectParentId(Long.parseLong(form.data().get("projectId")),Long.parseLong(form.data().get("projectInstance")));
			
			projectinstancenode.setWeightage(Integer.parseInt(form.data().get("weightage")));

			
			try {
				projectinstancenode.setStartDate(format.parse(form.data().get("startDate")));
				projectinstancenode.setEndDate(format.parse(form.data().get("endDate")));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			projectinstancenode.update();
			//projectinstancenode.removeAllUser();
			projectinstancenode.getProjectsinstanceNodeDelete(projectinstancenode.getId());
			List<User> uList = new ArrayList<User>();
			if(memberValues != null)
				for(String s:memberValues){
					User user = User.findById(Long.parseLong(s));
					uList.add(user);
				}
			projectinstancenode.setUser(uList);
			projectinstancenode.saveManyToManyAssociations("user");


			for(Projectclassnodeattribut attr : attributesArray){
				
				Saveattributes saveattri = Saveattributes.getProjectAttriId(projectinstancenode.getId(),attr.getId());
				String checkboxValue = null;
				if(saveattri != null){
					
					checkboxValue = "";
					if(attr.getType().equalsIgnoreCase("Checkbox")) {
						
			    		String values[] = request.getParameterValues(attr.getName());
			    		for(String s:values){
			    			checkboxValue = checkboxValue + s +",";
			    		}
			    		
			    		saveattri.setAttributValue(checkboxValue);
			    	} else {
			    		saveattri.setAttributValue(form.data().get(attr.getName()));
			    	}
					
					saveattri.update();
		
				}else{
					Saveattributes saveattri1 = new Saveattributes();
					checkboxValue = "";
					if(attr.getType().equalsIgnoreCase("Checkbox")) {

						String values[] = request.getParameterValues(attr.getName());
						for(String s:values){
							checkboxValue = checkboxValue + s +",";
						}

						saveattri1.setAttributValue(checkboxValue);
					} else {
						saveattri1.setAttributValue(form.data().get(attr.getName()));
					}
					saveattri1.setProjectattrid(attr.getId());
					saveattri1.setProjectinstancenode_id(projectinstancenode.getId());

					saveattri1.save();
				}
				
			}
		//	Projectinstancenode projectinstancenode1= Projectinstancenode.getProjectParentId(Long.parseLong(form.data().get("projectId")),Long.parseLong(form.data().get("projectInstance")));
			compare(projectinstancenodeOld,projectinstancenode, "Projectinstancenode" , projectinstancenode.getId(), username, projectinstancenode.getProjectinstanceid());
		}

		return Long.parseLong(form.data().get("projectId"));
	}
	
	@RequestMapping(value="/edit/project/addProjectNotsAndAtt",method=RequestMethod.GET)
	public String addProjectNotsAndAtt(Model model) {
		return "addProjectNotsAndAtt";
	}
	
	@RequestMapping(value="/edit/project/saveFile",method=RequestMethod.POST) 
	public @ResponseBody Long saveFile(@RequestParam("file")MultipartFile file,ProjectsupportattributVM pVm,@CookieValue("username")String username) {
		return createProjectService.saveFiles(file, pVm, username);
	}
	
		
	@RequestMapping(value = "/downloadStatusFile", method = RequestMethod.POST)
	@ResponseBody
	public FileSystemResource getattchfile(final HttpServletResponse response, @RequestParam(value = "attchId", required = true) final String attchId, @RequestParam(value = "mainInstance", required = true) final String mainInstance, @RequestParam(value = "currentParentId", required = true) final String currentParentId)
	{
	
		Projectinstancenode projectinstancenode= Projectinstancenode.getProjectParentId(Long.parseLong(currentParentId),Long.parseLong(mainInstance));
		
		ProjectAttachment projectAttachment = ProjectAttachment.getById(Long.parseLong(attchId));
		String[] filenames = projectAttachment.getDocName().split("\\.");
		
		 response.setContentType("application/x-download");
         response.setHeader("Content-Transfer-Encoding", "binary"); 
         response.setHeader("Content-disposition","attachment; filename=\""+projectAttachment.getDocName());
         File file = new File(imageRootDir+File.separator+ "attachment" + File.separator +filenames[0]+ "_" + projectinstancenode.getId() +"."+filenames[filenames.length-1]);
         
         return new FileSystemResource(file);
		
	}
	
	//delete attachement
	@RequestMapping(value = "/deleteStatusFile", method=RequestMethod.GET)
	public @ResponseBody String deleteAttachFile(@RequestParam("id") String  id) {
		ProjectAttachment attachment= ProjectAttachment.getById(Long.parseLong(id));
		File file = new File(attachment.getDocPath());
		file.delete();
		attachment.delete();
		return "";
	}
	@RequestMapping(value="/saveTask",method=RequestMethod.GET)
	public @ResponseBody List saveTask(@RequestParam("id")Long id,@RequestParam("mainInstance")Long mainInstance, @RequestParam("task")Long task) {
		
		return createProjectService.saveTask(id, mainInstance,task);
	}	
	
	
	private static void compare(Object o, Object n, String entity, Long EntityId, String username,Long projectinstanceid) {
		Javers javers = JaversBuilder.javers().build();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = new Date();
		Diff diff = javers.compare(o, n);
		List<changeValueVM> list = new ArrayList<changeValueVM>();
		
		Map map = new java.util.HashMap<>();
		List<ValueChange> changes = diff.getChangesByType(ValueChange.class);
		 for(ValueChange c:changes){
			 changeValueVM chVm = new changeValueVM();
			 if(c.getProperty().getName() != "beanLoaderIndex"){
			 chVm.property = c.getProperty().getName();
			 if(c.getLeft() != null){
				 chVm.oldVal = c.getLeft().toString();
			 }
			 if(c.getRight() != null){
				 chVm.newVal = c.getRight().toString();
			 }
			 list.add(chVm);
			 }	
    	 }
		 Gson gson = new Gson(); 
		 String json = gson.toJson(list); 
		 
		 User user = User.findByEmail(username);
		 
		 AduitLog al = new AduitLog();
         al.setEntityId(EntityId);
         al.setEntity(entity);
         al.setJsonData(json);
         al.setUser(User.findById(user.getId()));
         al.setChangeDate(dt);
         al.setProjectinstance(projectinstanceid);
         al.save();

	}
	
	public static class changeValueVM {
		public String property;
		public String oldVal;
		public String newVal;
		
	}
	
}


	