package com.mnt.employeeHierarchy.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mnt.employeeHierarchy.vm.EmployeeHierarchyVM;
import com.mnt.roleHierarchy.vm.RoleVM;

public interface EmployeeHierarchyService {
	
	List<EmployeeHierarchyVM> getEmployeeHierarchy(String username, Long id);
	File employeeProfile(Long id);
	
}
