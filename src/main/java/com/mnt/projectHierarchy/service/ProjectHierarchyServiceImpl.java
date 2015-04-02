package com.mnt.projectHierarchy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mnt.projectHierarchy.vm.ProjectclassVM;
import com.mnt.projectHierarchy.vm.ProjectclassnodeVM;
import com.mnt.projectHierarchy.vm.ProjectsupportattributVM;
import com.mnt.roleHierarchy.vm.RoleVM;

@Service
public class ProjectHierarchyServiceImpl implements ProjectHierarchyService{

	@Autowired
	com.mnt.projectHierarchy.repository.ProjectHierarchyRepository projectHierarchyRepository;
	@Override
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
	public Long saveProjectChild(ProjectsupportattributVM projectsupportattributVM) {
		// TODO Auto-generated method stub
		return projectHierarchyRepository.saveProjectChild(projectsupportattributVM);
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
	
	
}
