package com.mnt.employeeHierarchy.repository;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import models.User;
import net.coobird.thumbnailator.Thumbnails;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mnt.employeeHierarchy.vm.EmployeeHierarchyVM;
import com.mnt.orghierarchy.model.Organization;
import com.mnt.orghierarchy.vm.OrganizationVM;
import com.mnt.roleHierarchy.model.Role;
import com.mnt.roleHierarchy.vm.RoleVM;

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
