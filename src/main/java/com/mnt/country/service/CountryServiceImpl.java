package com.mnt.country.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mnt.country.vm.CityVM;
import com.mnt.country.vm.CountryVM;
import com.mnt.country.vm.StateVM;
import com.mnt.projectHierarchy.vm.ProjectclassVM;
import com.mnt.projectHierarchy.vm.ProjectclassnodeVM;
import com.mnt.projectHierarchy.vm.ProjectsupportattributVM;
import com.mnt.roleHierarchy.vm.RoleVM;

@Service
public class CountryServiceImpl implements CountryService{

	
	@Autowired
	com.mnt.country.repository.CountryRepository countryRepository;
	
	@Override
	public Map<String,List> getCountry() {
		// TODO Auto-generated method stub
		return countryRepository.getCountry();
	}

	@Override
	public List<StateVM> getStateByCountry(Long id) {
		// TODO Auto-generated method stub
		return countryRepository.getStateByCountry(id);
	}

	@Override
	public List<CityVM> getCityByState(Long id) {
		// TODO Auto-generated method stub
		return countryRepository.getCityByState(id);
	}

	@Override
	public Long saveCountry(CountryVM countryVM) {
		// TODO Auto-generated method stub
		return countryRepository.saveCountry(countryVM);
	}

	@Override
	public Long saveState(StateVM stateVM) {
		// TODO Auto-generated method stub
		return countryRepository.saveState(stateVM);
	}

	@Override
	public Long saveCity(CityVM cityVM) {
		// TODO Auto-generated method stub
		return countryRepository.saveCity(cityVM);
	}
	
	
}
