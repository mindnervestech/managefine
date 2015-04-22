package com.mnt.roleHierarchy.repository;

import java.util.List;






import com.mnt.roleHierarchy.vm.DepartmentDataVM;
import com.mnt.roleHierarchy.vm.RoleVM;

public interface RoleHierarchyRepository {
	List<RoleVM> getRoleHierarchy();
	Boolean deleteRoleChild(Long id);
	Long saveRoleChild(RoleVM roleVM, String username);
	Long editRoleChild(RoleVM roleVM, String username);
	List<DepartmentDataVM> findDepartment();

//	Long saveRoleChild(MultipartFile file, RoleVM roleVM, String username);
}
