package com.mnt.createProject.repository;

import java.util.List;






import com.mnt.projectHierarchy.vm.ProjectclassVM;
import com.mnt.projectHierarchy.vm.ProjectclassnodeVM;
import com.mnt.projectHierarchy.vm.ProjectsupportattributVM;
import com.mnt.roleHierarchy.vm.RoleVM;

public interface CreateProjectRepository {
	ProjectsupportattributVM getAddJspPage(Long id);
	ProjectsupportattributVM getEditJspPage(Long id);
	/*List<ProjectclassVM> getAllProjectType();
	Long saveproject(ProjectclassVM projectclassVM);
	List<ProjectclassnodeVM> selectProjectType(Long id);
	Long saveProjectChild(ProjectsupportattributVM projectsupportattributVM);
	Long editProjectChild(ProjectsupportattributVM projectsupportattributVM);
	List<ProjectsupportattributVM> editProjectTypeInfo(Long id);
	Boolean deleteProjectChild(Long id);*/

}
