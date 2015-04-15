package com.mnt.createProject.repository;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import models.Client;
import models.User;
import net.coobird.thumbnailator.Thumbnails;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import play.data.DynamicForm;

import com.custom.helpers.UserSearchContext;
import com.mnt.createProject.model.ProjectAttachment;

import com.mnt.createProject.model.ProjectComment;
import com.mnt.createProject.model.Projectinstance;
import com.mnt.createProject.model.Projectinstancenode;
import com.mnt.createProject.model.Saveattributes;
import com.mnt.createProject.vm.ClientVM;
import com.mnt.createProject.vm.ProjectAttachmentVM;
import com.mnt.createProject.vm.ProjectCommentVM;
import com.mnt.createProject.vm.ProjectinstanceVM;
import com.mnt.orghierarchy.model.Organization;
import com.mnt.orghierarchy.vm.OrganizationVM;
import com.mnt.projectHierarchy.model.Projectclass;
import com.mnt.projectHierarchy.model.Projectclassnode;
import com.mnt.projectHierarchy.model.Projectclassnodeattribut;
import com.mnt.projectHierarchy.vm.ProjectattributSelect;
import com.mnt.projectHierarchy.vm.ProjectclassVM;
import com.mnt.projectHierarchy.vm.ProjectclassnodeVM;
import com.mnt.projectHierarchy.vm.ProjectclassnodeattributVM;
import com.mnt.projectHierarchy.vm.ProjectsupportattributVM;
import com.mnt.roleHierarchy.model.Role;
import com.mnt.roleHierarchy.vm.RoleVM;

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
		
		Projectinstancenode projectinstancenodeDate= Projectinstancenode.getProjectParentId(projectclassnode.getParentId(),mainInstance);
		
		if(projectinstancenodeDate != null){
		
			if(projectinstancenodeDate.getStartDate() != null){
				pVm.setStartDateLimit(format1.format(projectinstancenodeDate.getStartDate()));
			}
			if(projectinstancenodeDate.getEndDate() != null){
				pVm.setEndDateLimit(format1.format(projectinstancenodeDate.getEndDate()));
			}
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
	public Projectinstance saveprojectTypeandName(HttpServletRequest request) {

		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		
		Projectinstance projectinstance= new Projectinstance();
		projectinstance.setProjectName(form.data().get("projectName"));
		projectinstance.setClientId(Long.parseLong(form.data().get("client")));
		projectinstance.setProjectid(Long.parseLong(form.data().get("projectTypeId")));
		projectinstance.setProjectDescription(form.data().get("projectDescription"));
		if(form.data().get("client") != null){
		Client client = Client.findById(Long.parseLong(form.data().get("client")));
		projectinstance.setClientName(client.getClientName());
		}
		projectinstance.save();
	
		return projectinstance;
		
	}
	
	@Override
	public Projectinstance editprojectTypeandName(Long projectId) {

		Projectinstance projectinstance= Projectinstance.getById(projectId);
		System.out.println(projectinstance.getProjectName());
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
		
		Projectinstancenode projectinstancenode = Projectinstancenode.getProjectParentId(id, mainInstance);
		
		if(projectinstancenode != null){
		pVm.setThisNodeId(projectinstancenode.getId());
		pVm.setProjectId(projectinstancenode.getProjectclassnode().getId());
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
	
	public Long saveTask(Long id,Long mainInstance, Long task){
		
		Projectinstancenode projectinstancenode= Projectinstancenode.getProjectParentId(id,mainInstance);
		
		projectinstancenode.setTaskCompilation(task.intValue());
		projectinstancenode.update();
		
		return projectinstancenode.getId();
		
	}

	
}