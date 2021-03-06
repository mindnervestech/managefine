package com.mnt.roleHierarchy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mnt.roleHierarchy.vm.DepartmentDataVM;
import com.mnt.roleHierarchy.vm.RoleVM;

@Service
public class RoleHierarchyServiceImpl implements RoleHierarchyService{

	@Autowired
	com.mnt.roleHierarchy.repository.RoleHierarchyRepository roleHierarchyRepository;
	@Override
	public List<RoleVM> getRoleHierarchy() {
		// TODO Auto-generated method stub
		return roleHierarchyRepository.getRoleHierarchy();
	}

	@Override
	public Boolean deleteRoleChild(Long id) {
		// TODO Auto-generated method stub
		return roleHierarchyRepository.deleteRoleChild(id);
	}
	@Override
	public Long saveRoleChild(RoleVM roleVM, String username) {
		// TODO Auto-generated method stub
		return roleHierarchyRepository.saveRoleChild(roleVM, username);
	}
	
	@Override
	public Long editRoleChild(RoleVM roleVM, String username) {
		// TODO Auto-generated method stub
		return roleHierarchyRepository.editRoleChild(roleVM,username);
	}

	@Override
	public List<DepartmentDataVM> findDepartment() {
		// TODO Auto-generated method stub
		return roleHierarchyRepository.findDepartment();
	}
	
	//List<RoleVM> getRoleHierarchy(Long id);
	@Override
	public 	RoleVM findSelectedDepartment(Long id){
		// TODO Auto-generated method stub
		return roleHierarchyRepository.findSelectedDepartment(id);
	}
	
	
}
