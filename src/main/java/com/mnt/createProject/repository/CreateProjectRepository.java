package com.mnt.createProject.repository;

import java.util.List;

import javax.servlet.http.HttpServletRequest;






import com.mnt.createProject.model.Projectinstance;
import com.mnt.createProject.vm.ClientVM;
import com.mnt.createProject.vm.ProjectinstanceVM;
import com.mnt.projectHierarchy.vm.ProjectclassVM;
import com.mnt.projectHierarchy.vm.ProjectclassnodeVM;
import com.mnt.projectHierarchy.vm.ProjectsupportattributVM;
import com.mnt.roleHierarchy.vm.RoleVM;

public interface CreateProjectRepository {
	ProjectsupportattributVM getAddJspPage(Long id,Long mainInstance);
	//ProjectsupportattributVM getEditJspPage(Long id,Long mainInstance);
	Projectinstance saveprojectTypeandName(HttpServletRequest request);
	Projectinstance editprojectTypeandName(Long projectId);
	List<ClientVM> getfindCliect();

}
