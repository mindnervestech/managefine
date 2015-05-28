package com.mnt.createProject.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mnt.createProject.model.Projectinstance;
import com.mnt.createProject.vm.ClientVM;
import com.mnt.createProject.vm.DateWiseHistoryVM;
import com.mnt.createProject.vm.DefinePartVM;
import com.mnt.createProject.vm.PartVM;
import com.mnt.createProject.vm.ProjectPartVM;
import com.mnt.createProject.vm.SupplierDataVM;
import com.mnt.createProject.vm.UserVM;
import com.mnt.projectHierarchy.vm.ProjectclassnodeVM;
import com.mnt.projectHierarchy.vm.ProjectsupportattributVM;

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
	public List<ProjectclassnodeVM> selectAllProjectType(Long id, Long rootId) {
		// TODO Auto-generated method stub
		return createProjectRepository.selectAllProjectType(id, rootId);
	}
	
	@Override
	public ProjectsupportattributVM findAttachFile(Long id,Long mainInstance) {
		// TODO Auto-generated method stub
		return createProjectRepository.findAttachFile(id,mainInstance);
	}
	
	
	@Override
	public List<ProjectclassnodeVM> saveTask(Long id,Long mainInstance, Long task) {
		// TODO Auto-generated method stub
		return createProjectRepository.saveTask(id,mainInstance, task);
	}
	
	@Override
	public Long saveComment(ProjectsupportattributVM pVm,String username) {
		// TODO Auto-generated method stub
		return createProjectRepository.saveComment(pVm,username);
	}
	
	@Override
	public Long saveDefineParts(DefinePartVM dpVm,String username) {
		// TODO Auto-generated method stub
		return createProjectRepository.saveDefineParts(dpVm,username);
	}
		
	@Override
	public Projectinstance saveprojectTypeandName(HttpServletRequest request, String username) {
		// TODO Auto-generated method stub
		return createProjectRepository.saveprojectTypeandName(request, username);
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
	public List<UserVM> getfindUser() {
		// TODO Auto-generated method stub
		return createProjectRepository.getfindUser();
	}
	
	@Override
	public List<ProjectPartVM> getAllPartNo(String username){
		return createProjectRepository.getAllPartNo(username);
	}
	
	@Override
	public List<UserVM> getfindselectedAllUser(Long mainInstance, Long projectId) {
		// TODO Auto-generated method stub
		return createProjectRepository.getfindselectedAllUser(mainInstance, projectId);
	}
	
	
	@Override
	public List<String> getselectedUser(Long mainInstance,Long projectId) {
		// TODO Auto-generated method stub
		return createProjectRepository.getselectedUser(mainInstance,projectId);
	}
	
	@Override
	public List<String> getselectedSupplier(Long mainInstance) {
		// TODO Auto-generated method stub
		return createProjectRepository.getselectedSupplier(mainInstance);
	}
	
	@Override
	public List<SupplierDataVM> getfindSupplier() {
		// TODO Auto-generated method stub
		return createProjectRepository.getfindSupplier();
	}
	
	@Override
	public DefinePartVM getAllDefinePartData(Long projectId,String username) {
		// TODO Auto-generated method stub
		return createProjectRepository.getAllDefinePartData(projectId,username);
	}
	
	@Override
	public Long saveFiles(MultipartFile file,ProjectsupportattributVM pVm,String username) {
		return  createProjectRepository.saveFiles(file, pVm, username);
	}
	
	@Override
	public List<DateWiseHistoryVM> getAllHistory(){
		return  createProjectRepository.getAllHistory();
	}
	
}
