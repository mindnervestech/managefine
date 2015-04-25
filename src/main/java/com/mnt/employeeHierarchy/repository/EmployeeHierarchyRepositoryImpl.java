package com.mnt.employeeHierarchy.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.User;
import models.UserFlexi;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mnt.core.domain.FileAttachmentMeta;
import com.mnt.employeeHierarchy.vm.EmployeeHierarchyVM;
import com.mnt.orghierarchy.model.Organization;

@Service
public class EmployeeHierarchyRepositoryImpl implements EmployeeHierarchyRepository {

	@Value("${imageRootDir}")
	String imageRootDir;
	
	@Override
	public List<EmployeeHierarchyVM> getEmployeeHierarchy(String username, Long id) {
		
		List<EmployeeHierarchyVM> result = new ArrayList<EmployeeHierarchyVM>();
	
			User user = User.findByEmail(username);
					
			List<User> userList = User.findByOrganizationId(id);
			
			for(User user2 :userList) {
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

	@Override
	public File employeeProfile(Long id) {
		if(id!=null) {
			//Organization organization = Organization.getOrganizationById(id);
			UserFlexi uFlexi = UserFlexi.getProjectUser(id);
			
			if(uFlexi !=null) {
				
				List<FileAttachmentMeta> list = null;
				try {
					
					list = new ObjectMapper().readValue(uFlexi.getValue(),
							TypeFactory.defaultInstance().constructCollectionType(List.class,FileAttachmentMeta.class));
					
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				return new File(imageRootDir+File.separator+"User"+File.separator+id+File.separator+list.get(0).n);
			}
		}
		return new File(imageRootDir+File.separator+"User"+File.separator+"default.jpg");
	}
	
}
