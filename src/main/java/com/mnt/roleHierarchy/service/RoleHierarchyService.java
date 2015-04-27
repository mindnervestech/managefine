package com.mnt.roleHierarchy.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mnt.createProject.vm.UserVM;
import com.mnt.roleHierarchy.vm.DepartmentDataVM;
import com.mnt.roleHierarchy.vm.RoleVM;

public interface RoleHierarchyService {
	
	List<RoleVM> getRoleHierarchy();
	Boolean deleteRoleChild(Long id);
    Long editRoleChild(RoleVM roleVM, String username);
	Long saveRoleChild(RoleVM roleVM, String username);
	List<DepartmentDataVM> findDepartment();
	RoleVM findSelectedDepartment(Long id);

//	Long saveRoleChild(MultipartFile file, RoleVM roleVM, String username);
}
