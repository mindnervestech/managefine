package com.mnt.projectHierarchy.repository;

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

import com.mnt.orghierarchy.model.Organization;
import com.mnt.orghierarchy.vm.OrganizationVM;
import com.mnt.projectHierarchy.model.Projectclass;
import com.mnt.projectHierarchy.model.Projectclassnode;
import com.mnt.projectHierarchy.model.Projectclassnodeattribut;
import com.mnt.projectHierarchy.vm.ProjectclassVM;
import com.mnt.projectHierarchy.vm.ProjectclassnodeVM;
import com.mnt.projectHierarchy.vm.ProjectclassnodeattributVM;
import com.mnt.projectHierarchy.vm.ProjectsupportattributVM;
import com.mnt.roleHierarchy.model.Role;
import com.mnt.roleHierarchy.vm.RoleVM;

@Service
public class ProjectHierarchyRepositoryImpl implements ProjectHierarchyRepository {

	@Override
	public List<ProjectclassVM> getAllProjectType() {
		
		List<ProjectclassVM> result = new ArrayList<ProjectclassVM>();
	
		List<Projectclass> pList = Projectclass.getProjectList();
		
			for(Projectclass projectclass :pList) {
				ProjectclassVM projectVM = new ProjectclassVM();
				
				projectVM.setId(projectclass.getId());
				projectVM.setProjectTypes(projectclass.getProjectTypes());
				projectVM.setProjectDescription(projectclass.getProjectDescription());
				
				result.add(projectVM);
			}
		
		return result;
		
	}
	
	
	@Override
	public List<ProjectclassnodeVM> selectProjectType(Long id) {
		
		System.out.println("%%%%%%%%%%");
		System.out.println(id);
		System.out.println("%%%%%%%%%%");
		
		List<ProjectclassnodeVM> result = new ArrayList<ProjectclassnodeVM>();
		List<Projectclassnode> projList = Projectclassnode.getprojectByprojectId(id);
		for(Projectclassnode projectclassnode :projList) {
			ProjectclassnodeVM pVm = new ProjectclassnodeVM();
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
	public Long editProjectChild(ProjectsupportattributVM projectsupportattributVM) {
	
		Projectclassnode projectclassnode=Projectclassnode.getProjectById(projectsupportattributVM.getParentId());
		projectclassnode.setProjectTypes(projectsupportattributVM.getProjectTypes());
		projectclassnode.setProjectDescription(projectsupportattributVM.getProjectDescription());
		projectclassnode.setProjectColor(projectsupportattributVM.getProjectColor());
		//projectclassnode.setProjectId(Projectclass.getProjectById(projectsupportattributVM.getProjectId()));
		
		projectclassnode.update();
		
		List<Projectclassnodeattribut> projectclassnodeattribut= Projectclassnodeattribut.getattributByprojectId(projectsupportattributVM.getParentId());
		for(Projectclassnodeattribut projectclassnodeattribut2:projectclassnodeattribut){
			projectclassnodeattribut2.delete();
		}
		
		for(ProjectclassnodeattributVM pVm:projectsupportattributVM.getProjectValue()){
			Projectclassnodeattribut projectA= new Projectclassnodeattribut();
			projectA.setName(pVm.getName());
			projectA.setType(pVm.getType());
			projectA.setValue(pVm.getValue());
			projectA.setProjectnode(Projectclassnode.getProjectById(projectclassnode.getId()));
			projectA.save();
		}
		return projectclassnode.getId();
		
	}
	
	@Override
	public Long saveProjectChild(ProjectsupportattributVM projectsupportattributVM) {

		System.out.println(projectsupportattributVM.getProjectColor());
		Projectclassnode projectnode=Projectclassnode.getProjectById(projectsupportattributVM.getParentId());
		Projectclassnode projectclassnode=new Projectclassnode();
		projectclassnode.setProjectTypes(projectsupportattributVM.getProjectTypes());
		projectclassnode.setProjectDescription(projectsupportattributVM.getProjectDescription());
		projectclassnode.setProjectColor(projectsupportattributVM.getProjectColor());
		projectclassnode.setParentId(projectsupportattributVM.getParentId());
		projectclassnode.setProjectId(Projectclass.getProjectById(projectsupportattributVM.getProjectId()));
		projectclassnode.setLevel(projectnode.getLevel() + 1);
		
		projectclassnode.save();
		
		for(ProjectclassnodeattributVM pVm:projectsupportattributVM.getProjectValue()){
			Projectclassnodeattribut projectclassnodeattribut= new Projectclassnodeattribut();
			projectclassnodeattribut.setName(pVm.getName());
			projectclassnodeattribut.setType(pVm.getType());
			projectclassnodeattribut.setValue(pVm.getValue());
			projectclassnodeattribut.setProjectnode(Projectclassnode.getProjectById(projectclassnode.getId()));
			projectclassnodeattribut.save();
		}
		return projectclassnode.getId();
		
	}
	
	@Override
	public Long saveproject(ProjectclassVM projectclassVM) {
		
		Projectclass already = Projectclass.getProjectByName(projectclassVM.getProjectTypes());
		Projectclass pclass = new Projectclass();
		Projectclassnode projectclassnode= new Projectclassnode();
		if(already == null){
		
		pclass.setProjectDescription(projectclassVM.getProjectDescription());
		pclass.setProjectTypes(projectclassVM.getProjectTypes());
		pclass.save();
		
		Projectclass pclass1 = Projectclass.getProjectByName(projectclassVM.getProjectTypes());
		
		projectclassnode.setProjectId(Projectclass.getProjectById(pclass1.getId()));
		projectclassnode.setProjectTypes(pclass1.getProjectTypes());
		projectclassnode.setProjectDescription(pclass1.getProjectDescription());
		projectclassnode.setLevel(0);
		projectclassnode.save();
		}else{
			return null;
		}
		
		return projectclassnode.getId();
	}
	
	@Override
	public List<ProjectsupportattributVM> editProjectTypeInfo(Long id) {
		
		List<ProjectsupportattributVM>  pList=new ArrayList<ProjectsupportattributVM>();
		
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
			projectclassnodeattributVM.setProjectnode(proAtt.getProjectnode().getId());
			pList2.add(projectclassnodeattributVM);
		}
		pVm.setProjectValue(pList2);
		pList.add(pVm);
		
		return pList;
		
	}
	
	@Override
	public Boolean deleteProjectChild(Long id) {
		// TODO Auto-generated method stub
				
		Projectclassnode projectclassnode=Projectclassnode.getProjectById(id);
		
		if(projectclassnode != null){
		List<Projectclassnodeattribut> projectclassnodeattribut= Projectclassnodeattribut.getattributByprojectId(id);
		for(Projectclassnodeattribut projectclassnodeattribut2:projectclassnodeattribut){
			projectclassnodeattribut2.delete();
		}
		
		List<Projectclassnode> childList = Projectclassnode.getparentByprojectId(id);
		for(Projectclassnode child:childList) {
			child.setParentId(projectclassnode.getParentId());
			child.update();
		}
		
		projectclassnode.delete();
		
		return true;
		}else{
			return false;
		}
	}
	
}
