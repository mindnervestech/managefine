package com.mnt.createProject.repository;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import models.User;
import net.coobird.thumbnailator.Thumbnails;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.custom.helpers.UserSearchContext;
import com.mnt.createProject.model.Projectinstancenode;
import com.mnt.createProject.model.Saveattributes;
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

	
	
	
	public ProjectsupportattributVM getAddJspPage(Long id) {
		
		ProjectsupportattributVM  pList= new ProjectsupportattributVM();
		
		Projectclassnode projectclassnode=Projectclassnode.getProjectById(id);
		ProjectsupportattributVM pVm=new ProjectsupportattributVM();
		pVm.setProjectTypes(projectclassnode.getProjectTypes());
		pVm.setProjectDescription(projectclassnode.getProjectDescription());
		pVm.setProjectColor(projectclassnode.getProjectColor());
		pVm.setProjectId(projectclassnode.getProjectId().getId());
		pVm.setParentId(projectclassnode.getParentId());
		pVm.setLevel(projectclassnode.getLevel());
		
		List<Projectclassnodeattribut> projectclassnodeattribut= Projectclassnodeattribut.getattributByprojectId(id);
		
		List<ProjectclassnodeattributVM> pList2 = new ArrayList<ProjectclassnodeattributVM>();
		for(Projectclassnodeattribut proAtt:projectclassnodeattribut){
			ProjectclassnodeattributVM projectclassnodeattributVM=new ProjectclassnodeattributVM();
			projectclassnodeattributVM.setId(proAtt.getId());
			projectclassnodeattributVM.setName(proAtt.getName());
			projectclassnodeattributVM.setType(proAtt.getType());
			projectclassnodeattributVM.setValue(proAtt.getValue());
			
			Saveattributes saveattributes = Saveattributes.getprojectattriById(proAtt.getId());
			
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
			 projectclassnodeattributVM.setProjectnode(proAtt.getProjectnode().getId());
			pList2.add(projectclassnodeattributVM);
		}
		pVm.setProjectValue(pList2);
		
		
		
		return pVm;
		
	}
	
	
	public ProjectsupportattributVM getEditJspPage(Long id) {
		
		ProjectsupportattributVM  pList= new ProjectsupportattributVM();
		
		Projectclassnode projectclassnode=Projectclassnode.getProjectById(id);
		ProjectsupportattributVM pVm=new ProjectsupportattributVM();
		pVm.setProjectTypes(projectclassnode.getProjectTypes());
		pVm.setProjectDescription(projectclassnode.getProjectDescription());
		pVm.setProjectColor(projectclassnode.getProjectColor());
		pVm.setProjectId(projectclassnode.getProjectId().getId());
		pVm.setParentId(projectclassnode.getParentId());
		pVm.setLevel(projectclassnode.getLevel());
		
		List<Projectinstancenode> projectinstancenodes= Projectinstancenode.getprojectNodeById(id);
		
		List<Saveattributes> saveattributes = Saveattributes.getprojectNodeById(id);
		
		List<ProjectclassnodeattributVM> pList2 = new ArrayList<ProjectclassnodeattributVM>();
		for(Saveattributes sAtt:saveattributes){
			ProjectclassnodeattributVM projectclassnodeattributVM=new ProjectclassnodeattributVM();
			projectclassnodeattributVM.setId(sAtt.getId());
			projectclassnodeattributVM.setName(sAtt.getAttributName());
			projectclassnodeattributVM.setType(sAtt.getType());
			if(sAtt.getType().equals("Checkbox")){
				String[] gvalue = sAtt.getAttributValue().split(",");
				List<String> lines = new ArrayList<String>();
				for(int i=0; i < gvalue.length; i++){
					lines.add(gvalue[i]);
					System.out.println("%^%^%^%%^%");
				 	System.out.println(gvalue[i]);
					}
				projectclassnodeattributVM.setCheckBoxValue(lines);
			}else{
				projectclassnodeattributVM.setAttriValue(sAtt.getAttributValue());
			}
            projectclassnodeattributVM.setProjectnode(sAtt.getProjectinstancenode_id());
			pList2.add(projectclassnodeattributVM);
		}
		pVm.setProjectValue(pList2);
		
		
		
		return pVm;
		
	}
	
}
