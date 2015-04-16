package com.mnt.createProject.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.mnt.createProject.model.Projectinstance;
import com.mnt.createProject.vm.ClientVM;
import com.mnt.createProject.vm.ProjectinstanceVM;
import com.mnt.orghierarchy.vm.OrganizationVM;
import com.mnt.projectHierarchy.vm.ProjectclassVM;
import com.mnt.projectHierarchy.vm.ProjectclassnodeVM;
import com.mnt.projectHierarchy.vm.ProjectsupportattributVM;
import com.mnt.roleHierarchy.vm.RoleVM;

public interface CreateProjectService {
	
	ProjectsupportattributVM getAddJspPage(Long id,Long mainInstance);
	//ProjectsupportattributVM getEditJspPage(Long id,Long mainInstance);
	Long saveFiles(MultipartFile file,ProjectsupportattributVM pVm,String username);
	Long saveComment(ProjectsupportattributVM pVm,String username);
	Projectinstance saveprojectTypeandName(HttpServletRequest request);
	Projectinstance editprojectTypeandName(Long projectId);
	ProjectsupportattributVM findAttachFile(Long id,Long mainInstance);
	List<ProjectclassnodeVM> selectAllProjectType(Long id,Long rootId);
	List<ProjectclassnodeVM> saveTask(Long id,Long mainInstance, Long task);
	List<ClientVM> getfindCliect();
	
	
}
