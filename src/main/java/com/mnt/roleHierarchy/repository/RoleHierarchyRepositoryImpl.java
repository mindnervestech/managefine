package com.mnt.roleHierarchy.repository;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import models.Company;
import models.RoleX;
import models.User;
import net.coobird.thumbnailator.Thumbnails;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mnt.orghierarchy.model.Organization;
import com.mnt.orghierarchy.vm.OrganizationVM;
import com.mnt.roleHierarchy.model.Role;
import com.mnt.roleHierarchy.vm.RoleVM;

@Service
public class RoleHierarchyRepositoryImpl implements RoleHierarchyRepository {

	@Override
	public List<RoleVM> getRoleHierarchy() {
		
		List<RoleVM> result = new ArrayList<RoleVM>();
	
			List<Role> roles = Role.getRoleList();
			System.out.println(roles);
			System.out.println("Tushar");
			for(Role role :roles) {
				RoleVM roleVM = new RoleVM();
				roleVM.setId(role.getId());
				roleVM.setParent(role.getParentId());
				roleVM.setRoleName(role.getRoleName());
				roleVM.setRoleDescription(role.getRoleDescription());
			
				result.add(roleVM);
			}
		
		return result;
		
	}

	@Override
	public Boolean deleteRoleChild(Long id) {
		// TODO Auto-generated method stub
		Role role = Role.getRoleById(id);
	if(role != null ) {
			
			List<Role> childList = role.getRoleByParentId(id);
			for(Role child:childList) {
				//File f = new File(imageRootDir+File.separator+child.getOrganizationProfileUrl());
				//f.delete();
				child.setParentId(role.getParentId());
				child.update();
				
			}
			
			role.delete();
			
			return true;
		} else {
			return false;
		}
	
	}

	@Override
	public Long saveRoleChild(RoleVM roleVM, String username) {
		
		Role role = new Role();
		Role role1 = Role.getRoleByName(roleVM.getRoleName());
		
		if(role1 == null){
		User user = User.findByEmail(username);
		
		role.setRoleX(RoleX.findByCompany(user.getCompanyobject().getId()));	
		role.setRoleName(roleVM.getRoleName());
		role.setRoleDescription(roleVM.getRoleDescription());
		role.setParentId(roleVM.getParent());
		role.save();
		}else{
			return null;
		}
    
		
		return role.getId();
	}

	@Override
	public Long editRoleChild(RoleVM roleVM, String username) {
		
		Role role = Role.getRoleById(roleVM.getParent());
		role.setRoleName(roleVM.getRoleName());
		role.setRoleDescription(roleVM.getRoleDescription());
		role.update();
		return role.getId();
		
	}

}
