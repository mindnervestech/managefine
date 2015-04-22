package com.mnt.orghierarchy.repository;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.mnt.orghierarchy.model.Organization;

import models.User;
import net.coobird.thumbnailator.Thumbnails;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import scala.collection.script.Remove;

import com.mnt.orghierarchy.vm.OrganizationVM;

@Service
public class OrgHierarchyRepositoryImpl implements OrgHierarchyRepository {

	@Value("${imageRootDir}")
	String imageRootDir;
	@Override
	public Long saveOrgChild(MultipartFile file, OrganizationVM organizationVM,String username) {
		
		Organization organization1= Organization.getOrganizationByName(organizationVM.getOrganizationName());
		Organization organization = new Organization();
		if(organization1 == null){
			
			organization.setOrganizationLocation(organizationVM.getOrganizationLocation());
			organization.setOrganizationName(organizationVM.getOrganizationName());
			organization.setOrganizationType(organizationVM.getOrganizationType());
			if(organizationVM.getParent() == 0){
				organization.setParent(null);
			}else{
				organization.setParent(organizationVM.getParent());
			}
			
			User user = User.findByEmail(username);
			organization.setCompanyId(user.getCompanyobject().getId());
			organization.save();
			try {
				String[] filenames = file.getOriginalFilename().split("\\.");
				String filename = imageRootDir+File.separator+"org"+organization.getCompanyId()+"_"+organization.getId()+"."+filenames[filenames.length-1];
				BufferedImage originalImage = ImageIO.read(file.getInputStream());
				File f = new File(filename);
				if(originalImage.getWidth()>120) {
					Thumbnails.of(originalImage).size(124, 124).toFile(f);
				} else {
					Thumbnails.of(originalImage).scale(1.0).toFile(f);
				}
				organization.setOrganizationProfileUrl("org"+organization.getCompanyId()+"_"+organization.getId()+"."+filenames[filenames.length-1]);
				organization.update();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			return null;
		}
		
		
		return organization.getId();
	}
		
	
	@Override
	public Long editOrgNotImgChild(OrganizationVM organizationVM) {
	
	
		Organization organization = Organization.getOrganizationById(organizationVM.getParent());
		//Organization organization1= Organization.getOrganizationByName(organization.getOrganizationName());
		//if(organization1 == null){
		organization.setOrganizationLocation(organizationVM.getOrganizationLocation());
		organization.setOrganizationName(organizationVM.getOrganizationName());
		organization.setOrganizationType(organizationVM.getOrganizationType());
		organization.update();
		//}else{
		//	return  null;
		//}
		return organization.getId();
	}
	
	
	@Override
	public Long editOrgChild(MultipartFile file, OrganizationVM organizationVM,String username) {
		Organization organization = Organization.getOrganizationById(organizationVM.getParent());
		
		//Organization organization1= Organization.getOrganizationByName(organization.getOrganizationName());
		//if(organization1 == null){
		
		organization.setOrganizationLocation(organizationVM.getOrganizationLocation());
		organization.setOrganizationName(organizationVM.getOrganizationName());
		organization.setOrganizationType(organizationVM.getOrganizationType());
		//organization.setParent(organizationVM.getParent());
		User user = User.findByEmail(username);
		organization.setCompanyId(user.getCompanyobject().getId());
		organization.update();
		try {
			String[] filenames = file.getOriginalFilename().split("\\.");
			String filename = imageRootDir+File.separator+"org"+organization.getCompanyId()+"_"+organization.getId()+"."+filenames[filenames.length-1];
			BufferedImage originalImage = ImageIO.read(file.getInputStream());
			File f = new File(filename);
			if(originalImage.getWidth()>120) {
				Thumbnails.of(originalImage).size(124, 124).toFile(f);
			} else {
				Thumbnails.of(originalImage).scale(1.0).toFile(f);
			}
			organization.setOrganizationProfileUrl("org"+organization.getCompanyId()+"_"+organization.getId()+"."+filenames[filenames.length-1]);
			organization.update();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//}else{
	//		return null;
		//}
		return organization.getId();
	}

	@Override
	public File orgProfile(Long id) {
		if(id!=null) {
			Organization organization = Organization.getOrganizationById(id);
			if(organization!=null) {
				return new File(imageRootDir+File.separator+organization.getOrganizationProfileUrl());
			}
		}
		return new File(imageRootDir+File.separator+"default.jpg");
	}

	@Override
	public List<OrganizationVM> getOrgHierarchy(String username) {
		User user = User.findByEmail(username);
		List<OrganizationVM> result = new ArrayList<OrganizationVM>();
		if(user!=null) {
			List<Organization> organizations = Organization.getOrganizationsByCompanyId(user.getCompanyobject().getId());
			for(Organization organization:organizations) {
				OrganizationVM organizationVM = new OrganizationVM();
				organizationVM.setId(organization.getId());
				organizationVM.setOrganizationLocation(organization.getOrganizationLocation());
				organizationVM.setOrganizationName(organization.getOrganizationName());
				organizationVM.setOrganizationType(organization.getOrganizationType());
				organizationVM.setParent(organization.getParent());
				result.add(organizationVM);
			}
		}
		return result;
	}

	@Override
	public Boolean deleteOrgChild(Long id) {
		Organization organization = Organization.getOrganizationById(id);
		if(organization != null ) {
			
			List<Organization> childList = Organization.getOrganizationsByParentId(id);
			for(Organization child:childList) {
				//File f = new File(imageRootDir+File.separator+child.getOrganizationProfileUrl());
				//f.delete();
				child.setParent(organization.getParent());
				child.update();
				
			}
			File f1 = new File(imageRootDir+File.separator+organization.getOrganizationProfileUrl());
			f1.delete();
			organization.delete();
			
			return true;
		} else {
			return false;
		}
		
	}

}
