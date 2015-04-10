package com.mnt.createProject.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mnt.createProject.model.Projectinstance;
import com.mnt.createProject.vm.ClientVM;
import com.mnt.createProject.vm.ProjectinstanceVM;
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
	
	
	/*@Override
	public ProjectsupportattributVM getEditJspPage(Long id,Long mainInstance) {
		// TODO Auto-generated method stub
		return createProjectRepository.getEditJspPage(id,mainInstance);
	}*/
	
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
	
	/*@Override
	public List<ProjectclassVM> getAllProjectType() {
		// TODO Auto-generated method stub
		return projectHierarchyRepository.getAllProjectType();
	}
	@Override
	public Long saveproject(ProjectclassVM projectclassVM) {
		// TODO Auto-generated method stub
		return projectHierarchyRepository.saveproject(projectclassVM);
	}
	
	
	
	@Override
	public Long editProjectChild(ProjectsupportattributVM projectsupportattributVM) {
		// TODO Auto-generated method stub
		return projectHierarchyRepository.editProjectChild(projectsupportattributVM);
	}
	
	@Override
	public List<ProjectclassnodeVM> selectProjectType(Long id) {
		// TODO Auto-generated method stub
		return projectHierarchyRepository.selectProjectType(id);
	}
	
	@Override
	public List<ProjectsupportattributVM> editProjectTypeInfo(Long id) {
		// TODO Auto-generated method stub
		return projectHierarchyRepository.editProjectTypeInfo(id);
	}
	
	@Override
	public Boolean deleteProjectChild(Long id) {
		// TODO Auto-generated method stub
		return projectHierarchyRepository.deleteProjectChild(id);
	}
	*/
	
}
