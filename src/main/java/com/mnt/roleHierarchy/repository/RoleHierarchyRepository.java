package com.mnt.roleHierarchy.repository;

import java.util.List;






import com.mnt.roleHierarchy.vm.RoleVM;

public interface RoleHierarchyRepository {
	List<RoleVM> getRoleHierarchy();
	Boolean deleteRoleChild(Long id);
	Long saveRoleChild(RoleVM roleVM);
	Long editRoleChild(RoleVM roleVM);

//	Long saveRoleChild(MultipartFile file, RoleVM roleVM, String username);
}
