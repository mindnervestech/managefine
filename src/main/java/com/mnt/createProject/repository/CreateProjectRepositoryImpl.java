package com.mnt.createProject.repository;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import models.Client;
import models.Supplier;
import models.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import play.data.DynamicForm;

import com.mnt.createProject.model.ProjectAttachment;
import com.mnt.createProject.model.ProjectComment;
import com.mnt.createProject.model.Projectinstance;
import com.mnt.createProject.model.Projectinstancenode;
import com.mnt.createProject.model.Saveattributes;
import com.mnt.createProject.vm.ClientVM;
import com.mnt.createProject.vm.ProjectAttachmentVM;
import com.mnt.createProject.vm.ProjectCommentVM;
import com.mnt.createProject.vm.SupplierDataVM;
import com.mnt.createProject.vm.UserVM;
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
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		
		ProjectsupportattributVM  pList= new ProjectsupportattributVM();
		
		Projectclassnode projectclassnode=Projectclassnode.getProjectById(id);
		ProjectsupportattributVM pVm=new ProjectsupportattributVM();
		pVm.setProjectTypes(projectclassnode.getProjectTypes());
		pVm.setProjectDescription(projectclassnode.getProjectDescription());
		pVm.setProjectColor(projectclassnode.getProjectColor());
		pVm.setProjectId(projectclassnode.getProjectId().getId());
		pVm.setParentId(projectclassnode.getParentId());
		pVm.setLevel(projectclassnode.getLevel());
		pVm.setThisNodeId(id);
		
		Projectinstance projectinstance= Projectinstance.getById(mainInstance);
		if(projectinstance.getUserid() != null){
		pVm.setProjectManager(projectinstance.getUserid().getFirstName());
		}
		Projectinstancenode projectinstancenodeDate= Projectinstancenode.getProjectParentId(projectclassnode.getParentId(),mainInstance);
		
		if(projectinstancenodeDate != null){
		
			if(projectinstancenodeDate.getStartDate() != null){
				pVm.setStartDateLimit(format.format(projectinstancenodeDate.getStartDate()));
			}
			if(projectinstancenodeDate.getEndDate() != null){
				pVm.setEndDateLimit(format.format(projectinstancenodeDate.getEndDate()));
			}
			pVm.setWeightage(projectinstancenodeDate.getWeightage());
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
		
		if(projectinstancenode.getSupplier() != null){
			pVm.setSupplier(String.valueOf(projectinstancenode.getSupplier().getId()));
		}
		if(projectinstancenode.getUser() != null){
			pVm.setUser(String.valueOf(projectinstancenode.getUser().getId()));
		}
		
		pVm.setWeightage(projectinstancenode.getWeightage());
		
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
						System.out.println("%^%^%^%%^%");
					 	System.out.println(gvalue[i]);
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
			 	System.out.println(gvalue[i]);
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
		//System.out.println(format.parse(sdf.format(dt)));
		
		List<ProjectclassnodeVM> result = new ArrayList<ProjectclassnodeVM>();
		List<Projectclassnode> projList = Projectclassnode.getprojectByprojectId(id);
		List<Projectinstancenode> pList = Projectinstancenode.getprojectinstanceById(rootId);
		for(Projectclassnode projectclassnode :projList) {
			//long diff = 0;
			
				ProjectclassnodeVM pVm = new ProjectclassnodeVM();
			    for(Projectinstancenode projectList: pList){
				
					if(projectclassnode.getId() == projectList.getProjectclassnode().getId()){
						pVm.setCompleted(projectList.getTaskCompilation());
					
						
						long diff = projectList.getEndDate().getTime() - projectList.getStartDate().getTime();
						long dayDiff1 = diff / (1000 * 60 * 60 * 24);
						
						diff = dt.getTime() - projectList.getStartDate().getTime();
						long dayDiff2 = diff / (1000 * 60 * 60 * 24);
						
						if(dayDiff2 > dayDiff1){
							dayDiff2 = dayDiff1;
						}
						
						long expected = (100*dayDiff2)/dayDiff1;
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
	public Projectinstance saveprojectTypeandName(HttpServletRequest request) {

		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		
		String supplierValues[] = request.getParameterValues("supplier");
		String memberValues[] = request.getParameterValues("member");
		
		
		
		Projectinstance projectinstance= new Projectinstance();
		projectinstance.setProjectName(form.data().get("projectName"));
		projectinstance.setClientId(Long.parseLong(form.data().get("client")));
		projectinstance.setProjectid(Long.parseLong(form.data().get("projectTypeId")));
		projectinstance.setProjectDescription(form.data().get("projectDescription"));
		if(form.data().get("client") != null){
		Client client = Client.findById(Long.parseLong(form.data().get("client")));
		projectinstance.setClientName(client.getClientName());
		}
		
		projectinstance.setUserid(User.findById(Long.parseLong(form.data().get("projectManager"))));
		
		List<User> uList = new ArrayList<User>();
		for(String s:supplierValues){
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
		
		List<UserVM> result = new ArrayList<UserVM>();
	
		List<User> uList = User.getUserList();
			for(User lUser :uList) {
				UserVM userVM = new UserVM();
				
				userVM.setId(String.valueOf(lUser.getId()));
				userVM.setEmail(lUser.getEmail());
				userVM.setFirstName(lUser.getFirstName());
				
				result.add(userVM);
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
				supplierVM.setAddress(slist.getAddress());
				
				result.add(supplierVM);
			}
		
		return result;
		
	}
	
	
	@Override
	public List<UserVM> getselectedUser(Long mainInstance) {
		
		List<UserVM> result = new ArrayList<UserVM>();
		
		Projectinstance projectinstance= Projectinstance.getById(mainInstance);
		
			for(User lUser :projectinstance.getUser()) {
				UserVM userVM = new UserVM();
				userVM.setId(String.valueOf(lUser.getId()));
				userVM.setEmail(lUser.getEmail());
				userVM.setFirstName(lUser.getFirstName());
				
				result.add(userVM);
			}
		
		return result;
		
	}
	
	
	@Override
	public List<SupplierDataVM> getselectedSupplier(Long mainInstance) {
		
		List<SupplierDataVM> result = new ArrayList<SupplierDataVM>();
		
		Projectinstance projectinstance= Projectinstance.getById(mainInstance);
		
			for(Supplier lSupplier :projectinstance.getSupplier()) {
				SupplierDataVM supplierVM = new SupplierDataVM();
				supplierVM.setId(String.valueOf(lSupplier.getId()));
				supplierVM.setEmail(lSupplier.getEmail());
				supplierVM.setSupplierName(lSupplier.getSupplierName());
				
				result.add(supplierVM);
			}
		
		return result;
		
	}
	
	
	@Override
	public Long saveFiles(MultipartFile file, ProjectsupportattributVM pVm, String username) {
		//ProjectsupportattributVM pVm=new ProjectsupportattributVM();
		
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
				projectinstance.setStatus(projectinstancenode2.getStatus());
				projectinstance.update();
			}
			pId = projectclassnode.getParentId();
		}
		
		
		
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		//System.out.println(format.parse(sdf.format(dt)));
		
		List<ProjectclassnodeVM> result = new ArrayList<ProjectclassnodeVM>();
		Projectinstance projectinstance = Projectinstance.getById(mainInstance);
		List<Projectclassnode> projList = Projectclassnode.getprojectByprojectId(projectinstance.getProjectid());
		List<Projectinstancenode> pList = Projectinstancenode.getprojectinstanceById(mainInstance);
		for(Projectclassnode projectclassnode :projList) {
			//long diff = 0;
			
				ProjectclassnodeVM pVm = new ProjectclassnodeVM();
			    for(Projectinstancenode projectList: pList){
				
					if(projectclassnode.getId() == projectList.getProjectclassnode().getId()){
						pVm.setCompleted(projectList.getTaskCompilation());
					
						
						long diff = projectList.getEndDate().getTime() - projectList.getStartDate().getTime();
						long dayDiff1 = diff / (1000 * 60 * 60 * 24);
						
						diff = dt.getTime() - projectList.getStartDate().getTime();
						long dayDiff2 = diff / (1000 * 60 * 60 * 24);
						
						if(dayDiff2 > dayDiff1){
							dayDiff2 = dayDiff1;
						}
						
						Projectinstancenode childColor = Projectinstancenode.getById(projectList.getId());
						long expected = (100*dayDiff2)/dayDiff1;
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

	
}
