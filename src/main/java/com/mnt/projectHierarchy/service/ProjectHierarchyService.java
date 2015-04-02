package com.mnt.projectHierarchy.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mnt.projectHierarchy.vm.ProjectclassVM;
import com.mnt.projectHierarchy.vm.ProjectclassnodeVM;
import com.mnt.projectHierarchy.vm.ProjectsupportattributVM;
import com.mnt.roleHierarchy.vm.RoleVM;

public interface ProjectHierarchyService {
	
	List<ProjectclassVM> getAllProjectType();
	Long saveproject(ProjectclassVM projectclassVM);
	List<ProjectclassnodeVM> selectProjectType(Long id);
	List<ProjectsupportattributVM> editProjectTypeInfo(Long id);
	Long saveProjectChild(ProjectsupportattributVM projectsupportattributVM);
	Long editProjectChild(ProjectsupportattributVM projectsupportattributVM);
	Boolean deleteProjectChild(Long id);
	
}
