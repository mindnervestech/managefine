package com.mnt.createProject.repository;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.mnt.createProject.model.Projectinstance;
import com.mnt.createProject.vm.ClientVM;
import com.mnt.createProject.vm.SupplierDataVM;
import com.mnt.createProject.vm.UserVM;
import com.mnt.projectHierarchy.vm.ProjectclassnodeVM;
import com.mnt.projectHierarchy.vm.ProjectsupportattributVM;

public interface CreateProjectRepository {
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
	List<UserVM> getfindUser();
	List<UserVM> getfindselectedAllUser(Long mainInstance, Long projectId);
	List<SupplierDataVM> getfindSupplier();
	List<String> getselectedUser(Long mainInstance, Long projectId);
	List<String> getselectedSupplier(Long mainInstance);

}
