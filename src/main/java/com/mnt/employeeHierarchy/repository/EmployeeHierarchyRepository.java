package com.mnt.employeeHierarchy.repository;

import java.io.File;
import java.util.List;






import com.mnt.employeeHierarchy.vm.EmployeeHierarchyVM;
import com.mnt.roleHierarchy.vm.RoleVM;

public interface EmployeeHierarchyRepository {
	List<EmployeeHierarchyVM> getEmployeeHierarchy(String username,Long id);
	File employeeProfile(Long id);
}
