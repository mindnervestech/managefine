package com.mnt.country.service;

import java.util.List;
import java.util.Map;

import models.State;

import org.springframework.web.multipart.MultipartFile;

import com.mnt.country.vm.CityVM;
import com.mnt.country.vm.CountryVM;
import com.mnt.country.vm.StateVM;
import com.mnt.projectHierarchy.vm.ProjectclassVM;
import com.mnt.projectHierarchy.vm.ProjectclassnodeVM;
import com.mnt.projectHierarchy.vm.ProjectsupportattributVM;
import com.mnt.roleHierarchy.vm.RoleVM;

public interface CountryService {
	
	
	Map<String,List> getCountry();
	List<StateVM> getStateByCountry(Long id);
	List<CityVM> getCityByState(Long id);
	Long saveCountry(CountryVM countryVM);
	Long saveState(StateVM stateVM);
	Long saveCity(CityVM cityVM);
	
	
}
