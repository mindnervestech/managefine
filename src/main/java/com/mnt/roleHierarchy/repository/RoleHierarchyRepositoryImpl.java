package com.mnt.roleHierarchy.repository;

import java.util.ArrayList;
import java.util.List;

import models.LeaveLevel;
import models.RoleLeave;
import models.RoleLevel;
import models.RoleX;
import models.User;

import org.springframework.stereotype.Service;

import com.mnt.roleHierarchy.vm.DepartmentDataVM;
import com.mnt.roleHierarchy.vm.RoleVM;

@Service
public class RoleHierarchyRepositoryImpl implements RoleHierarchyRepository {

	@Override
	public List<RoleVM> getRoleHierarchy() {
		
		List<RoleVM> result = new ArrayList<RoleVM>();
	
			List<RoleLevel> roles = RoleLevel.getRoleList();
			for(RoleLevel role :roles) {
				RoleVM roleVM = new RoleVM();
				roleVM.setId(role.getId());
				roleVM.setParent(role.getParentId());
				roleVM.setRoleName(role.getRole_name());
				roleVM.setRoleDescription(role.getRoleDescription());
				if(role.getDepartment() != null){
				roleVM.setDepartment(String.valueOf(role.getDepartment().getId()));
				roleVM.setDepartmentName(role.getDepartment().getName());
				}
			
				result.add(roleVM);
			}
		
		return result;
		
	}
	
	@Override
	public RoleVM findSelectedDepartment(Long id) {
		
			RoleLevel role = RoleLevel.findById(id);

			RoleVM roleVM = new RoleVM();
			if(role != null){

				roleVM.setId(role.getId());
				roleVM.setParent(role.getParentId());
				roleVM.setRoleName(role.getRole_name());
				roleVM.setRoleDescription(role.getRoleDescription());
				if(role.getDepartment() != null){
					roleVM.setDepartment(String.valueOf(role.getDepartment().getId()));
					roleVM.setDepartmentName(role.getDepartment().getName());
				}
			}
		
		return roleVM;
		
	}

	@Override
	public Boolean deleteRoleChild(Long id) {
		// TODO Auto-generated method stub

		RoleLevel role = RoleLevel.getRoleById(id);
		if (role != null) {
			RoleLeave roleLeave = RoleLeave.getDeleteRoleLevel(id);
			if(roleLeave != null){
				roleLeave.delete();
			}
			List<RoleLevel> childList = role.getRoleByParentId(id);
			for (RoleLevel child : childList) {
				// File f = new
				// File(imageRootDir+File.separator+child.getOrganizationProfileUrl());
				// f.delete();
				child.setParentId(role.getParentId());
				child.update();

			}

			role.delete();

			return true;
		} else {
			return false;
		}

	}

	public List<DepartmentDataVM> findDepartment(){
		List<DepartmentDataVM> dpDataVMs = new ArrayList<DepartmentDataVM>();
		List<models.Department> department = models.Department.findAll();
		
		for(models.Department department2:department){
			DepartmentDataVM pVm = new DepartmentDataVM();
			pVm.setId(department2.getId());
			pVm.setName(department2.getName());
			dpDataVMs.add(pVm);
		}
		
		
		return dpDataVMs;
		
	}
	
	
	@Override
	public Long saveRoleChild(RoleVM roleVM, String username) {
		
		RoleLevel role = new RoleLevel();
		RoleLevel role1 = RoleLevel.getRoleByName(roleVM.getRoleName());
		
		if(role1 == null){
		User user = User.findByEmail(username);
		
		//role.setRoleX(RoleX.findByCompany(user.getCompanyobject().getId()));	
		role.setRole_name(roleVM.getRoleName());
		role.setRoleDescription(roleVM.getRoleDescription());
		role.setDepartment(models.Department.departmentById(Long.parseLong(roleVM.getDepartment())));
		role.setParentId(roleVM.getParent());
		
		role.save();
		List<LeaveLevel> ll1 = LeaveLevel.findListByCompany(user.getCompanyobject().getId());
		if(ll1 != null){
		List<RoleLevel> rl1 = RoleLevel.find.all();
		for(RoleLevel r:rl1) {
			for(LeaveLevel _ll1 : ll1){
			if(RoleLeave.find.where().eq("company", user.getCompanyobject()).eq("roleLevel", r).eq("leaveLevel", _ll1).findUnique() == null){
				RoleLeave Rleave = new RoleLeave();
				Rleave.roleLevel = r;
				Rleave.company = user.getCompanyobject();
				Rleave.leaveLevel = _ll1;
				Rleave.total_leave = 0l;
				Rleave.save();
			}else{
				System.out.println("empty");
			}
			}
		}	
		}
		}else{
			return null;
		}
    
		
		return role.getId();
	}

	@Override
	public Long editRoleChild(RoleVM roleVM, String username) {
		
		RoleLevel role = RoleLevel.getRoleById(roleVM.getParent());
		role.setRole_name(roleVM.getRoleName());
		role.setRoleDescription(roleVM.getRoleDescription());
		role.setDepartment(models.Department.departmentById(Long.parseLong(roleVM.getDepartment())));
		role.update();
		return role.getId();
	}

}
