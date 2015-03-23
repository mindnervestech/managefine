package com.mnt.orghierarchy.repository;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mnt.orghierarchy.vm.OrganizationVM;

public interface OrgHierarchyRepository {
	Long saveOrgChild(MultipartFile file,OrganizationVM organizationVM,String username);
	Long editOrgChild(MultipartFile file,OrganizationVM organizationVM,String username);
	File orgProfile(Long id);
	List<OrganizationVM> getOrgHierarchy(String username);
	Boolean deleteOrgChild(Long id);
}
