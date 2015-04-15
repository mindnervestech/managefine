package com.mnt.createProject.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mnt.createProject.model.Projectinstance;
import com.mnt.createProject.vm.ClientVM;
import com.mnt.createProject.vm.ProjectinstanceVM;
import com.mnt.orghierarchy.vm.OrganizationVM;
import com.mnt.projectHierarchy.vm.ProjectclassVM;
import com.mnt.projectHierarchy.vm.ProjectclassnodeVM;
import com.mnt.projectHierarchy.vm.ProjectsupportattributVM;
import com.mnt.roleHierarchy.vm.RoleVM;

@Service
public class CreateProjectServiceImpl implements CreateProjectService{

	
	
	@Autowired
	com.mnt.createProject.repository.CreateProjectRepository createProjectRepository;
	
	
	@Override
	public ProjectsupportattributVM getAddJspPage(Long id,Long mainInstance) {
		// TODO Auto-generated method stub
		return createProjectRepository.getAddJspPage(id,mainInstance);
	}
	
	@Override
	public ProjectsupportattributVM findAttachFile(Long id,Long mainInstance) {
		// TODO Auto-generated method stub
		return createProjectRepository.findAttachFile(id,mainInstance);
	}
	
	
	@Override
	public Long saveTask(Long id,Long mainInstance, Long task) {
		// TODO Auto-generated method stub
		return createProjectRepository.saveTask(id,mainInstance, task);
	}
	
	@Override
	public Long saveComment(ProjectsupportattributVM pVm,String username) {
		// TODO Auto-generated method stub
		return createProjectRepository.saveComment(pVm,username);
	}
	
		
	@Override
	public Projectinstance saveprojectTypeandName(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return createProjectRepository.saveprojectTypeandName(request);
	}
	
	@Override
	public Projectinstance editprojectTypeandName(Long projectId) {
		// TODO Auto-generated method stub
		return createProjectRepository.editprojectTypeandName(projectId);
	}
	
	@Override
	public List<ClientVM> getfindCliect() {
		// TODO Auto-generated method stub
		return createProjectRepository.getfindCliect();
	}
	
	@Override
	public Long saveFiles(MultipartFile file,ProjectsupportattributVM pVm,String username) {
		return  createProjectRepository.saveFiles(file, pVm, username);
	}
	
}