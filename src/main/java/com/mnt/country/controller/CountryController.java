package com.mnt.country.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.country.vm.CityVM;
import com.mnt.country.vm.CountryVM;
import com.mnt.country.vm.StateVM;
import com.mnt.projectHierarchy.vm.ProjectclassVM;



@Controller
public class CountryController {

	
	@Autowired
	com.mnt.country.service.CountryService countryService;
	
		
	@RequestMapping(value="/GetCountry",method=RequestMethod.GET)
	public @ResponseBody Map<String,List> GetCountry() {
		return countryService.getCountry();
	}
	
	@RequestMapping(value="/getStateByCountry",method=RequestMethod.GET)
	public @ResponseBody List<StateVM> getStateByCountry(@RequestParam("id")Long id) {
		//return projectHierarchyService.deleteProjectChild(id);
		return countryService.getStateByCountry(id);
	}
	
	@RequestMapping(value="/getCityByState",method=RequestMethod.GET)
	public @ResponseBody List<CityVM> getCityByState(@RequestParam("id")Long id) {
		//return projectHierarchyService.deleteProjectChild(id);
		return countryService.getCityByState(id);
	}
	
	@RequestMapping(value="/saveCountry",method=RequestMethod.POST) 
	public @ResponseBody Long saveCountry(@RequestBody CountryVM countryVM) {
		return countryService.saveCountry(countryVM);
	}
	
	@RequestMapping(value="/saveState",method=RequestMethod.POST) 
	public @ResponseBody Long saveState(@RequestBody StateVM stateVM) {
		return countryService.saveState(stateVM);
	}
	
	@RequestMapping(value="/saveCity",method=RequestMethod.POST) 
	public @ResponseBody Long saveCity(@RequestBody CityVM cityVM) {
		return countryService.saveCity(cityVM);
	}
}
