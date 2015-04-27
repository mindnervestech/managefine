package com.mnt.orghierarchy.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mnt.employeeHierarchy.vm.EmployeeHierarchyVM;
import com.mnt.orghierarchy.vm.OrganizationVM;

public interface OrgHierarchyService {	
	//Long saveOrgChild(MultipartFile file,OrganizationVM organizationVM,String username);
	Long saveOrgChild(OrganizationVM organizationVM, String username);
	Long editOrgChild(MultipartFile file,OrganizationVM organizationVM,String username);
	Long editOrgNotImgChild(OrganizationVM organizationVM);
	File orgProfile(Long id);
	List<OrganizationVM> getOrgHierarchy(String username);
	Boolean deleteOrgChild(Long id);
	List<EmployeeHierarchyVM> orgEmployee(String user,Long id);
}
