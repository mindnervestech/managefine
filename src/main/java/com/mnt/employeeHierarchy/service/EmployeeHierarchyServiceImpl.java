package com.mnt.employeeHierarchy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mnt.employeeHierarchy.vm.EmployeeHierarchyVM;
import com.mnt.roleHierarchy.vm.RoleVM;

@Service
public class EmployeeHierarchyServiceImpl implements EmployeeHierarchyService{

	@Autowired
	com.mnt.employeeHierarchy.repository.EmployeeHierarchyRepository employeeHierarchyRepository;
	@Override
	public List<EmployeeHierarchyVM> getEmployeeHierarchy(String username) {
		// TODO Auto-generated method stub
		return employeeHierarchyRepository.getEmployeeHierarchy(username);
	}


}
