package com.mnt.orghierarchy.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mnt.orghierarchy.repository.OrgHierarchyRepository;
import com.mnt.orghierarchy.vm.OrganizationVM;

@Service
public class OrgHierarchyServiceImpl implements OrgHierarchyService {

	@Autowired
	OrgHierarchyRepository orgHierarchyRepository;
	@Override
	public Long saveOrgChild(MultipartFile file, OrganizationVM organizationVM,String username) {
		return orgHierarchyRepository.saveOrgChild(file, organizationVM,username);
	}
	@Override
	public Long editOrgChild(MultipartFile file, OrganizationVM organizationVM,String username) {
		return orgHierarchyRepository.editOrgChild(file, organizationVM,username);
	}
	@Override
	public File orgProfile(Long id) {
		return orgHierarchyRepository.orgProfile(id);
	}
	@Override
	public List<OrganizationVM> getOrgHierarchy(String username) {
		return orgHierarchyRepository.getOrgHierarchy(username);
	}
	@Override
	public Boolean deleteOrgChild(Long id) {
		return orgHierarchyRepository.deleteOrgChild(id);
	}

}
