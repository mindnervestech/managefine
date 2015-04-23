package com.mnt.employeeHierarchy.repository;

import java.util.ArrayList;
import java.util.List;

import models.User;

import org.springframework.stereotype.Service;

import com.mnt.employeeHierarchy.vm.EmployeeHierarchyVM;

@Service
public class EmployeeHierarchyRepositoryImpl implements EmployeeHierarchyRepository {

	@Override
	public List<EmployeeHierarchyVM> getEmployeeHierarchy(String username) {
		
		List<EmployeeHierarchyVM> result = new ArrayList<EmployeeHierarchyVM>();
	
			User user = User.findByEmail(username);
			List<User> uList = User.findByCompanyId(user.getCompanyobject().getId());
			
			
			for(User user2 :uList) {
				EmployeeHierarchyVM eVm = new EmployeeHierarchyVM();
				eVm.setId(user2.getId());
				if(user2.getManager() != null){
				eVm.setParent(user2.getManager().getId());
				}else{
					eVm.setParent(null);
				}
				eVm.setEmployeeName(user2.getFirstName());
				eVm.setDesignation(user2.getDesignation());
			
				result.add(eVm);
			}
		
		return result;
		
	}

	
}
