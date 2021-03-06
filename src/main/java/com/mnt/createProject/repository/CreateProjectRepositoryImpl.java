package com.mnt.createProject.repository;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import models.Client;
import models.Supplier;
import models.User;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONException;
import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import play.data.DynamicForm;

import com.avaje.ebean.SqlRow;
import com.custom.domain.ProjectStatus;
import com.mnt.createProject.model.AduitLog;
import com.mnt.createProject.model.AttributesProject;
import com.mnt.createProject.model.DefinePart;
import com.mnt.createProject.model.Pits;
import com.mnt.createProject.model.ProjectAttachment;
import com.mnt.createProject.model.ProjectComment;
import com.mnt.createProject.model.ProjectPart;
import com.mnt.createProject.model.Projectinstance;
import com.mnt.createProject.model.Projectinstancenode;
import com.mnt.createProject.model.Saveattributes;
import com.mnt.createProject.vm.AttributDataVM;
import com.mnt.createProject.vm.AttributVM;
import com.mnt.createProject.vm.ClientVM;
import com.mnt.createProject.vm.DateWiseHistoryVM;
import com.mnt.createProject.vm.DefinePartVM;
import com.mnt.createProject.vm.HistoryAllLogVM;
import com.mnt.createProject.vm.PartVM;
import com.mnt.createProject.vm.ProjectAttachmentVM;
import com.mnt.createProject.vm.ProjectCommentVM;
import com.mnt.createProject.vm.ProjectPartVM;
import com.mnt.createProject.vm.SupplierDataVM;
import com.mnt.createProject.vm.UserVM;
import com.mnt.projectHierarchy.model.Projectclass;
import com.mnt.projectHierarchy.model.Projectclassnode;
import com.mnt.projectHierarchy.model.Projectclassnodeattribut;
import com.mnt.projectHierarchy.vm.ProjectattributSelect;
import com.mnt.projectHierarchy.vm.ProjectclassnodeVM;
import com.mnt.projectHierarchy.vm.ProjectclassnodeattributVM;
import com.mnt.projectHierarchy.vm.ProjectsupportattributVM;

@Service
public class CreateProjectRepositoryImpl implements CreateProjectRepository {

	@Value("${imageRootDir}")
	String imageRootDir;
	
	
	public ProjectsupportattributVM getAddJspPage(Long id,Long mainInstance) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = new Date();
		
		ProjectsupportattributVM  pList= new ProjectsupportattributVM();
		
		Projectclassnode projectclassnode=Projectclassnode.getProjectById(id);
		
		ProjectsupportattributVM pVm=new ProjectsupportattributVM();
		if(projectclassnode.getParentId() == null){
			Projectinstance projectinstance = Projectinstance.findById(mainInstance);
			pVm.setProjectTypes(projectinstance.getProjectName());
			pVm.setProjectDescription(projectinstance.getProjectDescription());
		}else{
			pVm.setProjectTypes(projectclassnode.getProjectTypes());
			pVm.setProjectDescription(projectclassnode.getProjectDescription());
		}
		
		
		pVm.setProjectColor(projectclassnode.getProjectColor());
		pVm.setProjectId(projectclassnode.getProjectId().getId());
		pVm.setParentId(projectclassnode.getParentId());
		pVm.setLevel(projectclassnode.getLevel());
		pVm.setThisNodeId(id);
		
		Projectinstance projectinstance= Projectinstance.getById(mainInstance);
		pVm.setCustomer(projectinstance.getClientId());
		if(projectinstance.getEndCustomer() != null){
			pVm.setEndCustomer(projectinstance.getEndCustomer().getId());
		}
		pVm.setOpportunityNo(projectinstance.getOpportunityNo());
		pVm.setCreatedDate(projectinstance.getCreatedDate());
		pVm.setRegion(projectinstance.getRegion());
		pVm.setEndCustomerLocation(projectinstance.getEndCustomerLocation());
		pVm.setProjectNameApplication(projectinstance.getProjectNameApplication());
		pVm.setProductLifeTime(projectinstance.getProductLifeTime());
		pVm.setSupplierRegistion(projectinstance.getSupplierRegistion());
		pVm.setProjectLastUpdate(projectinstance.getProjectLastUpdate());
		pVm.setSerialNo(projectinstance.getSerialNo());
		pVm.setSupplierFae(projectinstance.getSupplierFae());
		
	//	if(projectinstance.getProductionDate() != null){
	//		String[] gvalue = projectinstance.getProductionDate().split("-");
		//	pVm.setProductionDate(gvalue[2]+"-"+gvalue[1]+"-"+gvalue[0]);
	//	}
		
		pVm.setProductionDate(projectinstance.getProductionDate());
		pVm.setSupplierSaleperson(projectinstance.getSupplierSaleperson());
		pVm.setProjectWin(projectinstance.getProjectWin());
		pVm.setPurchaseCustContactNo(projectinstance.getPurchaseCustContactNo());
		pVm.setPurchaseCustEmail(projectinstance.getPurchaseCustEmail());
		pVm.setRemark(projectinstance.getRemark());
		if(projectinstance.getProjectManager() != null){
			pVm.setProjectManager(projectinstance.getProjectManager().getId().toString());
		}
		Projectinstancenode projectinstancenodeDate= Projectinstancenode.getProjectParentId(projectclassnode.getParentId(),mainInstance);

		if(projectinstancenodeDate != null){

			if(projectinstancenodeDate.getStartDate() != null){
				pVm.setStartDateLimit(format1.format(projectinstancenodeDate.getStartDate()));
			}
			if(projectinstancenodeDate.getEndDate() != null){
				Calendar c = Calendar.getInstance(); 
				c.setTime(projectinstancenodeDate.getEndDate()); 
				c.add(Calendar.DATE, 1);
				Date EndDateLimit = c.getTime();
				pVm.setEndDateLimit(format1.format(EndDateLimit));
			}
			pVm.setWeightage(projectinstancenodeDate.getWeightage());
		}else{
			pVm.setStartDateLimit("01-01-2005");
			pVm.setEndDateLimit("01-01-2035");
			
		}

		List<Projectclassnodeattribut> projectclassnodeattribut= Projectclassnodeattribut.getattributByprojectId(id);
		Projectinstancenode projectinstancenode= Projectinstancenode.getProjectParentId(id,mainInstance);
		if(projectinstancenode != null){
			if(projectinstancenode.getStartDate() != null){
				pVm.setStartDate(format.format(projectinstancenode.getStartDate()));
			}
			if(projectinstancenode.getEndDate() != null){
				pVm.setEndDate(format.format(projectinstancenode.getEndDate()));
			}
			pVm.setWeightage(projectinstancenode.getWeightage());

		}else{
			pVm.setStartDate(sdf.format(dt));
			pVm.setEndDate(sdf.format(dt));
			pVm.setProductionDate(format1.format(dt));
			pVm.setCreatedDate(format1.format(dt));
			pVm.setProjectLastUpdate(format1.format(dt));
		}
		List<ProjectclassnodeattributVM> pList2 = new ArrayList<ProjectclassnodeattributVM>();
		for(Projectclassnodeattribut proAtt:projectclassnodeattribut){
			ProjectclassnodeattributVM projectclassnodeattributVM=new ProjectclassnodeattributVM();
			projectclassnodeattributVM.setId(proAtt.getId());
			projectclassnodeattributVM.setName(proAtt.getName());
			projectclassnodeattributVM.setType(proAtt.getType());
			projectclassnodeattributVM.setValue(proAtt.getValue());

			Saveattributes saveattributes = null;


			if(projectinstancenode != null){
				saveattributes = Saveattributes.getProjectAttriId(projectinstancenode.getId(),proAtt.getId());

				if(saveattributes!=null){

					if(!proAtt.getType().equals("Checkbox")){
						projectclassnodeattributVM.setAttriValue(saveattributes.getAttributValue());
					}else{

						/*	String[] gvalue = saveattributes.getAttributValue().split(",");
					List<String> lines = new ArrayList<String>();
					for(int i=0; i < gvalue.length; i++){
						lines.add(gvalue[i]);
						}
					projectclassnodeattributVM.setCheckBoxValue(lines);*/

					}

				}
			}


			if(proAtt.getValue() != null){
				String[] gvalue = proAtt.getValue().split("\n");
				List<ProjectattributSelect> lines = new ArrayList<ProjectattributSelect>();
				for(int i=0; i < gvalue.length; i++){
					ProjectattributSelect pSelect = new ProjectattributSelect();
					if(saveattributes!=null){
						if(proAtt.getType().equals("Checkbox")){
							String[] gvalue1 = saveattributes.getAttributValue().split(",");
							for(int j=0; j < gvalue1.length; j++){
								if(gvalue[i].equals(gvalue1[j])){
									pSelect.setSelect("checked");
								}
							}
							pSelect.setValue(gvalue[i]);

						}else{
							pSelect.setValue(gvalue[i]);
							pSelect.setSelect("");

						}
					}else{
						pSelect.setValue(gvalue[i]);
						pSelect.setSelect("");
					}

					lines.add(pSelect);
				}
				projectclassnodeattributVM.setValueSlice(lines);
			}
			// projectclassnodeattributVM.setProjectnode(proAtt.getProjectnode().getId());
			pList2.add(projectclassnodeattributVM);
		}
		pVm.setProjectValue(pList2);



		return pVm;

	}
	
	@Override
	public List<ProjectclassnodeVM> selectAllProjectType(Long id, Long rootId) {
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		List<ProjectclassnodeVM> result = new ArrayList<ProjectclassnodeVM>();
		
		List<Projectclassnode> projList = Projectclassnode.getprojectByprojectId(id);
		
		List<Projectinstancenode> pList = Projectinstancenode.getProjectInstanceById(rootId);
		
		for(Projectclassnode projectclassnode :projList) {
			//long diff = 0;
			
				ProjectclassnodeVM pVm = new ProjectclassnodeVM();
			    for(Projectinstancenode projectList: pList){
				
					if(projectclassnode.getId().equals(projectList.getProjectclassnode().getId())){
						pVm.setCompleted(projectList.getTaskCompilation());
					
						
						long diff = projectList.getEndDate().getTime() - projectList.getStartDate().getTime();
						long dayDiff1 = diff / (1000 * 60 * 60 * 24);
						
						diff = dt.getTime() - projectList.getStartDate().getTime();
						long dayDiff2 = diff / (1000 * 60 * 60 * 24);
						
						if(dayDiff2 > dayDiff1){
							dayDiff2 = dayDiff1;
						}
						long expected = 0;
						if(dayDiff1 != 0){
							expected = (100*dayDiff2)/dayDiff1;
						}
						if(projectList.getTaskCompilation() < (expected-10)){
							pVm.setStatus("danger");
						} else if(projectList.getTaskCompilation() < expected){
							pVm.setStatus("warning");
						} else{
							pVm.setStatus("success");
						} 
					}
				
		    	}
				
				pVm.setId(projectclassnode.getId());
				pVm.setProjectTypes(projectclassnode.getProjectTypes());
				pVm.setProjectDescription(projectclassnode.getProjectDescription());
				pVm.setProjectId(projectclassnode.getProjectId().getId());
				pVm.setLevel(projectclassnode.getLevel());
				pVm.setParentId(projectclassnode.getParentId());
				result.add(pVm);
			
		}
		
		
		return result;
			
		
	}
	
		
	@Override
	public Projectinstance saveprojectTypeandName(HttpServletRequest request, String username) {

		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		
		String supplierValues[] = request.getParameterValues("supplier");
		String memberValues[] = request.getParameterValues("member");
		
		/*Projectinstance projectName =Projectinstance.getProjectByName(form.data().get("projectName"));
		
		if(projectName == null){*/
		
			Projectinstance projectinstance= new Projectinstance();
			projectinstance.setProjectName(form.data().get("projectName"));
			projectinstance.setClientId(Long.parseLong(form.data().get("client")));
			projectinstance.setEndCustomer(Client.findById(Long.parseLong(form.data().get("endCustomer"))));
			//
			projectinstance.setProjectid(Long.parseLong(form.data().get("projectTypeId")));
			Projectclass projectclass = Projectclass.getProjectById(Long.parseLong(form.data().get("projectTypeId")));
			projectinstance.setProjectTypeName(projectclass.getProjectTypes());
			projectinstance.setStatus(ProjectStatus.NotStarted);
			projectinstance.setProjectDescription(form.data().get("projectDescription"));
			if(form.data().get("client") != null){
				Client client = Client.findById(Long.parseLong(form.data().get("client")));
				projectinstance.setClientName(client.getClientName());
			}
		
			User userid = User.findByEmail(username);
			projectinstance.setUserid(User.findById(userid.getId()));
			projectinstance.setProjectManager(User.findById(Long.parseLong(form.data().get("projectManager"))));
		
			List<User> uList = new ArrayList<User>();
			for(String s:memberValues){
				User user = User.findById(Long.parseLong(s));
				uList.add(user);
			}
		
			projectinstance.setUser(uList);
		
			List<Supplier> sList = new ArrayList<Supplier>();
			for(String sid:supplierValues){
				Supplier supplier = Supplier.findById(Long.parseLong(sid));
				sList.add(supplier);
			}
		
			projectinstance.setSupplier(sList);
		
			projectinstance.save();
			
			return projectinstance;
	//	}
			/*else{
			return projectName;
		}*/
		
	}
	
	@Override
	public Projectinstance editprojectTypeandName(Long projectId) {

		Projectinstance projectinstance= Projectinstance.getById(projectId);
		
		return projectinstance;
		
	}
	
	@Override
	public List<ClientVM> getfindCliect() {
		
		List<ClientVM> result = new ArrayList<ClientVM>();
	
		//List<Client> pList = Projectclass.getProjectList();
		
		List<Client> cList = Client.getClientList();
			for(Client clientclass :cList) {
				ClientVM clientVM = new ClientVM();
				
				clientVM.setId(String.valueOf(clientclass.getId()));
				clientVM.setClientName(clientclass.getClientName());
				
				result.add(clientVM);
			}
		
		return result;
		
	}
	
	
	@Override
	public List<UserVM> getfindUser() {
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy hh:mm:ss a");
		Date dt = new Date();
		 Date d=null;
         Date d1=null;
         String dd = sdf.format(dt);
         try {
			d = sdf.parse(dd);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		List<UserVM> result = new ArrayList<UserVM>();
	  
		List<User> uList = User.getUserList();
		for (User lUser : uList) {
			if (lUser.getReleaseDate() != null) {
				String dd1 = sdf.format(lUser.getReleaseDate());
				try {
					d1 = sdf.parse(dd1);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (d1.getTime() > d.getTime()) {

					if (lUser.getUsertype() != null) {
						if (lUser.getUsertype().equals("User")) {

							UserVM userVM = new UserVM();

							userVM.setId(String.valueOf(lUser.getId()));
							userVM.setEmail(lUser.getEmail());
							userVM.setFirstName(lUser.getFirstName());
							userVM.setLastName(lUser.getLastName());
							userVM.setMiddleName(lUser.getMiddleName());

							result.add(userVM);
						}
					}
				}
			} else {
				if (lUser.getUsertype() != null) {
					if (lUser.getUsertype().equals("User")) {

						UserVM userVM = new UserVM();

						userVM.setId(String.valueOf(lUser.getId()));
						userVM.setEmail(lUser.getEmail());
						userVM.setFirstName(lUser.getFirstName());
						userVM.setLastName(lUser.getLastName());
						userVM.setMiddleName(lUser.getMiddleName());

						result.add(userVM);
					}
				}
			}
		}
		
		return result;
		
	}
	
	
	@Override
	public List<SupplierDataVM> getfindSupplier() {
		
		List<SupplierDataVM> result = new ArrayList<SupplierDataVM>();
	
		//List<Client> pList = Projectclass.getProjectList();
		
		List<Supplier> sList = Supplier.getSupplierList();
			for(Supplier slist :sList) {
				SupplierDataVM supplierVM = new SupplierDataVM();
				
				supplierVM.setId(String.valueOf(slist.getId()));
				supplierVM.setEmail(slist.getEmail());
				supplierVM.setSupplierName(slist.getSupplierName());
				supplierVM.setAddress(slist.getWebsite());
				
				result.add(supplierVM);
			}
		
		return result;
		
	}
	
	

	@Override
	public List<UserVM> getfindselectedAllUser(Long mainInstance, Long projectId) {
		
		List<UserVM> result = new ArrayList<UserVM>();
		
		Projectclassnode projectclassnode = Projectclassnode.getProjectById(projectId);
		
		if(projectclassnode.getParentId() == null){
			List<User> uList = User.getUserList();
			for(User lUser :uList) {
				
				if(lUser.getUsertype() != null){
				     if(lUser.getUsertype().equals("User")){
				   UserVM userVM = new UserVM();
				   userVM.setId(String.valueOf(lUser.getId()));
				   userVM.setEmail(lUser.getEmail());
				   userVM.setFirstName(lUser.getFirstName());
				   userVM.setLastName(lUser.getLastName());
				   userVM.setMiddleName(lUser.getMiddleName());
				
			
				   result.add(userVM);
				}
				}	
			}
			
		}else{
			
			Projectinstancenode projectnode1 = Projectinstancenode.getProjectParentId(projectclassnode.getParentId(), mainInstance);
			
			//Projectinstance projectinstance= Projectinstance.getById(mainInstance);
			for(User lUser :projectnode1.getUser()) {
				if(lUser.getUsertype() != null){
				     if(lUser.getUsertype().equals("User")){
					UserVM userVM = new UserVM();
					userVM.setId(String.valueOf(lUser.getId()));
					userVM.setEmail(lUser.getEmail());
					userVM.setFirstName(lUser.getFirstName());
					userVM.setLastName(lUser.getLastName());
					userVM.setMiddleName(lUser.getMiddleName());
				
			
					result.add(userVM);
				}
			  }
			}
		}
		return result;
		
	}
	
	@Override
	public List<String> getselectedUser(Long mainInstance, Long projectId) {
		
		/*List<UserVM> result = new ArrayList<UserVM>();
		
		Projectinstance projectinstance= Projectinstance.getById(mainInstance);
		
			for(User lUser :projectinstance.getUser()) {
				UserVM userVM = new UserVM();
				userVM.setId(String.valueOf(lUser.getId()));
				userVM.setEmail(lUser.getEmail());
				userVM.setFirstName(lUser.getFirstName());
				
				result.add(userVM);
			}
		
		return result;
		
		*/
		
	
		List<String> users = new ArrayList<>();
		Projectinstancenode projectnode = Projectinstancenode.getProjectParentId(projectId, mainInstance);
		if(projectnode == null){
			Projectinstance projectinstance= Projectinstance.getById(mainInstance);
	
			for(User lUser :projectinstance.getUser()) {
				users.add(lUser.getId().toString());
			}
			return users;
		} else {
			for(User lUser :projectnode.getUser()) {
				users.add(lUser.getId().toString());
			}
			return users;
		}
		
	}
	
	@Override
	public List<ProjectPartVM> getAllPartNo(String username){
		List<ProjectPartVM> pList = new ArrayList<>();
		
		List<ProjectPart> projectPart = ProjectPart.getProjectPartNo();
		for(ProjectPart part:projectPart){
			ProjectPartVM pVm = new ProjectPartVM();
			pVm.setId(part.getId());
			pVm.setPartNo(part.getPartNo());
			pList.add(pVm);
		}
		return pList;
	}
	
	@Override
	public List<String> getselectedSupplier(Long mainInstance) {
		
		/*List<SupplierDataVM> result = new ArrayList<SupplierDataVM>();
		
		Projectinstance projectinstance= Projectinstance.getById(mainInstance);
		
			for(Supplier lSupplier :projectinstance.getSupplier()) {
				SupplierDataVM supplierVM = new SupplierDataVM();
				supplierVM.setId(String.valueOf(lSupplier.getId()));
				supplierVM.setEmail(lSupplier.getEmail());
				supplierVM.setSupplierName(lSupplier.getSupplierName());
				
				result.add(supplierVM);
			}
		
		return result;*/
		
		List<String> supps = new ArrayList<>();
		
		Projectinstance projectinstance= Projectinstance.getById(mainInstance);
		
			for(Supplier lSupplier :projectinstance.getSupplier()) {
				supps.add(lSupplier.getId().toString());
			}
		return supps;
		
	}
	
	
	@Override
	public Long saveFiles(MultipartFile file, ProjectsupportattributVM pVm, String username) {
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	
		Projectinstancenode projectinstancenode= Projectinstancenode.getProjectParentId(pVm.getProjectId(), pVm.getThisNodeId());
		
			String[] filenames = file.getOriginalFilename().split("\\.");
			String filename = imageRootDir+File.separator+ "attachment" + File.separator +filenames[0]+ "_" + projectinstancenode.getId() +"."+filenames[filenames.length-1];
			
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
			
			
			ProjectAttachment projectattachment= new ProjectAttachment();
			projectattachment.setDocName(file.getOriginalFilename());
			projectattachment.setDocPath(filename);
			try {
				projectattachment.setDocDate(format.parse(sdf.format(dt)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//if(projectinstancenode != null){
			projectattachment.setProjectinstanceid(projectinstancenode.getId());
			//}
			projectattachment.save();

		return null;
	}

	public ProjectsupportattributVM findAttachFile(Long id,Long mainInstance) {
		
		ProjectsupportattributVM pVm = new ProjectsupportattributVM();
		
		List<Projectclassnode> projectclassnode = Projectclassnode.getparentByprojectId(id);
		
		if(projectclassnode.size() == 0){
		Projectinstancenode projectinstancenode = Projectinstancenode.getProjectParentId(id, mainInstance);
		
		if(projectinstancenode != null){
			pVm.setThisNodeId(projectinstancenode.getId());
			pVm.setProjectId(projectinstancenode.getProjectclassnode().getId());
			pVm.setTaskCompilation(projectinstancenode.getTaskCompilation());
			if(projectinstancenode.getTaskCompilation() < 100){
				pVm.setComment("Inprogress");
			}else{
				pVm.setComment("Completed");
			}
			List<ProjectAttachment> pAttachment = ProjectAttachment.getprojectinstanceById(projectinstancenode.getId());
		
			List<ProjectAttachmentVM> pAList = new ArrayList<ProjectAttachmentVM>();
			List<ProjectCommentVM> pCList = new ArrayList<ProjectCommentVM>();
		
			for(ProjectAttachment pAtt:pAttachment){
				ProjectAttachmentVM pAttachmentVM = new ProjectAttachmentVM();
				pAttachmentVM.setId(pAtt.getId());
				pAttachmentVM.setDocDate(pAtt.getDocDate());
				pAttachmentVM.setDocName(pAtt.getDocName());
				pAttachmentVM.setDocPath(pAtt.getDocPath());
				pAttachmentVM.setProjectinstanceid(pAtt.getProjectinstanceid());
				pAList.add(pAttachmentVM);
			
			}
			pVm.setProjectAttachment(pAList);
		
			List<ProjectComment> pComment = ProjectComment.getprojectinstanceById(projectinstancenode.getId());
			
			for(ProjectComment pComm:pComment){
				ProjectCommentVM pCommentVM = new ProjectCommentVM();
				pCommentVM.setCommetDate(pComm.getCommetDate());
				pCommentVM.setId(pComm.getId());
				pCommentVM.setProjectComment(pComm.getProjectComment());
				pCommentVM.setUserName(pComm.getUser().getFirstName());
				pCList.add(pCommentVM);
		  }
		  pVm.setProjectcomments(pCList);
		
		  }else{
			pVm.setThisNodeId(null);
		  }
		}else{
			pVm.setComment("notAllow");
		}
		return pVm;
		
	}
	
	@Override
	public Long saveDefineParts(DefinePartVM dpVm,String username) {
		
	
		List<DefinePart> definePart = DefinePart.getPartByProject(Long.parseLong(dpVm.projectId));
		if(definePart != null){
			for(DefinePart  dPart:definePart){
				dPart.delete();
			}
		}
		
		for(PartVM pvm:dpVm.getPartsValue()){
			DefinePart dPart = new DefinePart();
			dPart.setAnnualQty(pvm.getAnnualQty());
			dPart.setCostPrice(pvm.getCostPrice());
			dPart.setEstimatedRevenue(pvm.getEstimatedRevenue());
			dPart.setSuggestedResale(pvm.getSuggestedResale());
			dPart.setPartNo(ProjectPart.findById(pvm.getPartNo()));
			dPart.setClaimStatus(pvm.getClaimStatus());
			dPart.setLeadTime(pvm.getLeadTime());
			dPart.setSupplier(Supplier.findById(pvm.getSupplier()));
			if(pvm.getPits() != null){
				dPart.setPits(Pits.getById(pvm.getPits()));
			}
			dPart.setProjectinstance(Projectinstance.findById(Long.parseLong(dpVm.projectId)));
			
			dPart.save();
			
		}
		
		Projectinstance projectinstance = Projectinstance.findById(Long.parseLong(dpVm.projectId));
		projectinstance.setTotalEstimatedRevenue(Double.parseDouble(dpVm.getTotalEstimatedRevenue()));
		projectinstance.update();
		
		return projectinstance.getId();
		
	}
	
	@Override
	public DefinePartVM getAllDefinePartData(Long projectId,String username) {
		
		DefinePartVM dVm = new DefinePartVM();
		Projectinstance projectinstance = Projectinstance.getById(projectId);
		if(projectinstance.getTotalEstimatedRevenue() != null){
		dVm.setTotalEstimatedRevenue(projectinstance.getTotalEstimatedRevenue().toString());
		}
		List<DefinePart> dPart = DefinePart.getPartByProject(projectId);
		List<PartVM> partVMs = new ArrayList<>();
		for(DefinePart dp:dPart){
			PartVM pVm = new PartVM();
			pVm.setAnnualQty(dp.getAnnualQty());
			pVm.setClaimStatus(dp.getClaimStatus());
			pVm.setCostPrice(dp.getCostPrice());
			pVm.setEstimatedRevenue(dp.getEstimatedRevenue());
			pVm.setPartNo(dp.getPartNo().getId());
			pVm.setId(dp.getPartNo().getId());
			pVm.setSuggestedResale(dp.getSuggestedResale());
			pVm.setLeadTime(dp.getLeadTime());
			if(dp.getSupplier() != null){
				pVm.setSupplier(dp.getSupplier().getId());
			}
			if(dp.getPits() != null){
				pVm.setPits(dp.getPits().getId());
			}
			partVMs.add(pVm);
		}
		dVm.setPartsValue(partVMs);
		
		return dVm;
		
	}
	
	@Override
	public Long saveAttribues(AttributDataVM aDataVm,String username) {
		
		List<AttributesProject> aProject = AttributesProject.getAttributByProject(aDataVm.getMainInstance());
		if(aProject.size() == 0){
			for(AttributVM aVm:aDataVm.projectAtt){
				AttributesProject apProject = new AttributesProject();
				
					apProject.setKeyValue(aVm.getKeyValue());
					apProject.setQ1(aVm.q1);
					apProject.setQ2(aVm.q2);
					apProject.setQ3(aVm.q3);
					apProject.setQ4(aVm.q4);
					apProject.setProjectinstance(Projectinstance.findById(aDataVm.MainInstance));
					apProject.save();
				
			}
		}else{
			for(AttributesProject attributesProject:aProject){
				for(AttributVM aVm:aDataVm.projectAtt){
					if(attributesProject.getKeyValue().equals(aVm.getKeyValue())){
						attributesProject.setQ1(aVm.q1);
						attributesProject.setQ2(aVm.q2);
						attributesProject.setQ3(aVm.q3);
						attributesProject.setQ4(aVm.q4);
						attributesProject.update();
					}/*else{
						AttributesProject apProject = new AttributesProject();
						apProject.setKeyValue(aVm.getKeyValue());
						apProject.setQ1(aVm.q1);
						apProject.setQ2(aVm.q2);
						apProject.setQ3(aVm.q3);
						apProject.setQ4(aVm.q4);
						apProject.setProjectinstance(Projectinstance.findById(aDataVm.MainInstance));
						apProject.save();
					}*/
					
				}
			}
		}
		
		
		return aDataVm.MainInstance;
		
	}
	
	@Override
	public AttributDataVM findattributes(Long mainInstance) {
		AttributDataVM aDataVM = new AttributDataVM();
		List<AttributVM> aList = new ArrayList<>();
		List<AttributesProject> aProject = AttributesProject.getAttributByProject(mainInstance);
		for(AttributesProject aProject2:aProject){
			AttributVM aVm = new AttributVM();
			aVm.keyValue = aProject2.getKeyValue();
			aVm.q1 = aProject2.getQ1();
			aVm.q2 = aProject2.getQ2();
			aVm.q3 = aProject2.getQ3();
			aVm.q4 = aProject2.getQ4();
			aList.add(aVm);
		}
		aDataVM.setProjectAtt(aList);
		return aDataVM;
	}
	
	@Override
	public Long saveComment(ProjectsupportattributVM pVm,String username) {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		
		User user= User.findByEmail(username);
		ProjectComment pComment = new ProjectComment();
		
		try {
			pComment.setCommetDate(format.parse(sdf.format(dt)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pComment.setUser(User.findById(user.getId()));
		pComment.setProjectComment(pVm.getComment());
		
		Projectinstancenode projectinstancenode= Projectinstancenode.getProjectParentId(pVm.getProjectId(), pVm.getThisNodeId());
		pComment.setProjectinstanceid(projectinstancenode.getId());
		pComment.save();
		
		return pComment.getId();
	}
	
	public List<ProjectclassnodeVM> saveTask(Long id,Long mainInstance, Long task){
		
				
		double total =0;
		double numerator = 0;
		double denominator = 0;
		
		Projectinstancenode projectinstancenode= Projectinstancenode.getProjectParentId(id,mainInstance);
		projectinstancenode.setTaskCompilation(task.intValue());
		
		if(task.intValue() < 100){
			projectinstancenode.setStatus("Inprogress");
		}else{
			projectinstancenode.setStatus("Completed");
		}
		projectinstancenode.update();
		
		Long pId = id;
		while(pId != null){
			total =0;
			numerator = 0;
			denominator = 0;
			Projectclassnode projectclassnode= Projectclassnode.getProjectById(pId);
			if(projectclassnode.getParentId() != null){
				List<Projectclassnode> projecList = Projectclassnode.getparentByprojectId(projectclassnode.getParentId());
				for(Projectclassnode projectclassnode2:projecList){
					Projectinstancenode projectinstancenode2 = Projectinstancenode.getProjectParentId(projectclassnode2.getId(), mainInstance);
					if(projectinstancenode2 != null){
						numerator = numerator + (projectinstancenode2.getTaskCompilation() * projectinstancenode2.getWeightage());		
						denominator	= denominator + projectinstancenode2.getWeightage();
					}
				}
				total = numerator / denominator;
				
				Projectinstancenode parent = Projectinstancenode.getProjectParentId(projectclassnode.getParentId(), mainInstance);
				parent.setTaskCompilation((long)total);
				
				if((long)total < 100){
					parent.setStatus("Inprogress");
				}else{
					parent.setStatus("Completed");
				}
				
				parent.update();
			}else{
				Projectinstancenode projectinstancenode2=Projectinstancenode.getProjectParentId(projectclassnode.getId(), mainInstance);
				Projectinstance projectinstance = Projectinstance.getById(mainInstance);
				projectinstance.setStatus(ProjectStatus.valueOf(projectinstancenode2.getStatus()));
				projectinstance.setPercentage(projectinstancenode2.getTaskCompilation());
				projectinstance.update();
			}
			pId = projectclassnode.getParentId();
		}
		
		
		
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		List<ProjectclassnodeVM> result = new ArrayList<ProjectclassnodeVM>();
		Projectinstance projectinstance = Projectinstance.getById(mainInstance);
		List<Projectclassnode> projList = Projectclassnode.getprojectByprojectId(projectinstance.getProjectid());
		List<Projectinstancenode> pList = Projectinstancenode.getProjectInstanceById(mainInstance);
		for(Projectclassnode projectclassnode :projList) {
			//long diff = 0;
			
				ProjectclassnodeVM pVm = new ProjectclassnodeVM();
			    for(Projectinstancenode projectList: pList){
			
					if(projectclassnode.getId().equals(projectList.getProjectclassnode().getId())){ //projectList.getProjectclassnode().getId()){
						pVm.setCompleted(projectList.getTaskCompilation());
					
						
						long diff = projectList.getEndDate().getTime() - projectList.getStartDate().getTime();
						long dayDiff1 = diff / (1000 * 60 * 60 * 24);
							
						
						diff = dt.getTime() - projectList.getStartDate().getTime();
						long dayDiff2 = diff / (1000 * 60 * 60 * 24);
						
						if(dayDiff2 > dayDiff1){
							dayDiff2 = dayDiff1;
						}
						
						Projectinstancenode childColor = Projectinstancenode.getById(projectList.getId());
						long expected;
						if(dayDiff2 == 0 && dayDiff1 == 0){
							expected = 0;
						}else{
							expected = (100*dayDiff2)/dayDiff1;
						}	
						if(projectList.getTaskCompilation() < (expected-10)){
							childColor.setColor("#ff0000");
							pVm.setStatus("danger");
							
						} else if(projectList.getTaskCompilation() < expected){
							childColor.setColor("#FFC200");
							pVm.setStatus("warning");
						} else{
							childColor.setColor("#00ff00");
							pVm.setStatus("success");
						}
						childColor.update();
					}
				
		    	}
				
				pVm.setId(projectclassnode.getId());
				pVm.setProjectTypes(projectclassnode.getProjectTypes());
				pVm.setProjectDescription(projectclassnode.getProjectDescription());
				pVm.setProjectId(projectclassnode.getProjectId().getId());
				pVm.setLevel(projectclassnode.getLevel());
				pVm.setParentId(projectclassnode.getParentId());
				result.add(pVm);
			
		}
		
		
		return result;
	}

	public List<DateWiseHistoryVM> getAllHistory(Long mainInstance){
		//DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			List<SqlRow> sqlRows = AduitLog.getDateWiseHistory(mainInstance);
			List<DateWiseHistoryVM> vmList = new ArrayList<>();
			for(SqlRow row: sqlRows){
				DateWiseHistoryVM dHistoryVM=new DateWiseHistoryVM();
				
				List<SqlRow> aLog = null;
						aLog = AduitLog.getDateHistory(row.getString("date"), mainInstance);
					
				dHistoryVM.setChangeDate(row.getString("date"));
				List<HistoryAllLogVM> hAllList = new ArrayList<>();
				for(SqlRow sRow: aLog){
					AduitLog aduitLog = AduitLog.getById(sRow.getLong("id"));
					
					 JSONArray array = new JSONArray(aduitLog.getJsonData());
					 for(int i=0; i<array.length(); i++){
						 HistoryAllLogVM hVm = new HistoryAllLogVM(); 
					        JSONObject jsonObj  = array.getJSONObject(i);
					        hVm.setProperty(jsonObj.getString("property"));
					        if(jsonObj.getString("property").equals("startDate") || jsonObj.getString("property").equals("endDate")){
					        	
					        	SimpleDateFormat parseFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
					        	Date oldval = null;
					        	Date newval = null;
								try {
									oldval = parseFormat.parse(jsonObj.getString("oldVal"));
									newval = parseFormat.parse(jsonObj.getString("newVal"));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
					        	
					        	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
					        	 hVm.setOldVal(format.format(oldval)); 
					        	 hVm.setNewVal(format.format(newval));
					        	// hVm.setOldVal(format.format(jsonObj.getString("oldVal")));
							    // hVm.setNewVal(format.format(jsonObj.getString("newVal")));
					        }else{
					       if(jsonObj.getString("oldVal")!=null)
					        	hVm.setOldVal(jsonObj.getString("oldVal"));
					       if(jsonObj.getString("newVal")!=null)
							     hVm.setNewVal(jsonObj.getString("newVal"));
					        }
					       
					        hVm.setUserId(aduitLog.getUser().getId());
					        hVm.setUserName(aduitLog.getUser().getFirstName());
					        hAllList.add(hVm);
					    }
				}
				if(hAllList.size() != 0){
					dHistoryVM.setHistoryAllLogVM(hAllList);
				    vmList.add(dHistoryVM);
				}
			}
			
			return vmList;
		
	}
	
		
	
}

/*
for(SqlRow row: sqlRows){
	DateWiseHistoryVM dHistoryVM=new DateWiseHistoryVM();
	
	List<SqlRow> aLog = null;
			aLog = AduitLog.getDateHistory(row.getString("date"));
		
	dHistoryVM.setChangeDate(row.getString("date"));
	
	List<ValueListVM> vms = new ArrayList<>();
	for(SqlRow sRow: aLog){
		AduitLog aduitLog = AduitLog.getById(sRow.getLong("id"));
		List<HistoryAllLogVM> hAllList = new ArrayList<>();
		ValueListVM vListVM = new ValueListVM();
		 JSONArray array = new JSONArray(aduitLog.getJsonData());
		 for(int i=0; i<array.length(); i++){
			 HistoryAllLogVM hVm = new HistoryAllLogVM(); 
		        JSONObject jsonObj  = array.getJSONObject(i);
		        hVm.setProperty(jsonObj.getString("property"));
		        hVm.setOldVal(jsonObj.getString("oldVal"));
		        hVm.setNewVal(jsonObj.getString("newVal"));
		        hAllList.add(hVm);
		    }
		 vListVM.setHistoryAllLogVM(hAllList);
		 vms.add(vListVM);
	}
	dHistoryVM.setValueListVM(vms);
	vmList.add(dHistoryVM);
}*/