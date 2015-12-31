package com.mnt.country.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingWorker.StateValue;

import models.City;
import models.Country;
import models.State;

import org.springframework.stereotype.Service;


import com.mnt.country.vm.CityVM;
import com.mnt.country.vm.CountryVM;
import com.mnt.country.vm.StateVM;
import com.mnt.projectHierarchy.model.Projectclass;
import com.mnt.projectHierarchy.model.Projectclassnode;
import com.mnt.projectHierarchy.model.Projectclassnodeattribut;
import com.mnt.projectHierarchy.vm.ProjectclassVM;
import com.mnt.projectHierarchy.vm.ProjectclassnodeVM;
import com.mnt.projectHierarchy.vm.ProjectclassnodeattributVM;
import com.mnt.projectHierarchy.vm.ProjectsupportattributVM;

@Service
public class CountryRepositoryImpl implements CountryRepository {

	@Override
	public Map<String,List> getCountry() {
		// TODO Auto-generated method stub
		List<CountryVM> countryResult = new ArrayList<CountryVM>();
		List<StateVM> stateResult = new ArrayList<StateVM>();
		List<CityVM> cityResult = new ArrayList<CityVM>();
		
		List<Country> countryList = Country.getCountryList();
		for(Country country :countryList) {
			CountryVM countryVM = new CountryVM();
			
			countryVM.setId(country.getId());
			countryVM.setName(country.getCountryName());
			
			
			countryResult.add(countryVM);
		}
		
		List<State> stateList = State.getStateList();
		for(State state :stateList) {
			 StateVM stateVM = new StateVM();
			
			 stateVM.setId(state.getId());
			 stateVM.setName(state.getStateName());
			 stateVM.setCountry(state.getCountry().getCountryName());
			
			
			 stateResult.add(stateVM);
		}
		
		List<City> cityList = City.getCityList();
		for(City city :cityList) {
			 CityVM cityVM = new CityVM();
			
			 cityVM.setId(city.getId());
			 cityVM.setName(city.getCityName());
			 cityVM.setState(city.getState().getStateName());
			
			
			 cityResult.add(cityVM);
		}
		
		
		Map<String,List> dataList = new HashMap<String, List>();
		dataList.put("countryList", countryResult);
		dataList.put("stateList", stateResult);
		dataList.put("cityList", cityResult);
		return dataList;
	}

	@Override
	public List<StateVM> getStateByCountry(Long id) {
		// TODO Auto-generated method stub
		
		List<StateVM> stateResult = new ArrayList<StateVM>();
		List<State> stateList = State.getStateListByCountry(id);
		for(State state :stateList) {
			 StateVM stateVM = new StateVM();
			
			 stateVM.setId(state.getId());
			 stateVM.setName(state.getStateName());
			 stateVM.setCountry(state.getCountry().getCountryName());
			
			
			 stateResult.add(stateVM);
		}
		return stateResult;
	}

	@Override
	public List<CityVM> getCityByState(Long id) {
		// TODO Auto-generated method stub
		List<CityVM> cityResult = new ArrayList<CityVM>();
		List<City> cityList = City.getCityListByState(id);
		for(City city :cityList) {
			 CityVM cityVM = new CityVM();
			
			 cityVM.setId(city.getId());
			 cityVM.setName(city.getCityName());
			 cityVM.setState(city.getState().getStateName());
			
			
			 cityResult.add(cityVM);
		}
		return cityResult;
	}

	@Override
	public Long saveCountry(CountryVM countryVM) {
		// TODO Auto-generated method stub
		Country country = new Country();
		country.setCountryName(countryVM.getName());
		country.save();
		return 0L;
		
	}

	@Override
	public Long saveState(StateVM stateVM) {
		// TODO Auto-generated method stub
		
		State state = new State();
		state.setStateName(stateVM.getName());
		state.setCountry(Country.findById(Long.parseLong(stateVM.getCountry())));
		state.save();
		return 0L;
	}

	@Override
	public Long saveCity(CityVM cityVM) {
		// TODO Auto-generated method stub
		
		City city = new City();
		city.setCityName(cityVM.getName());
		city.setState(State.findById(Long.parseLong(cityVM.getState())));
		city.save();
		return null;
	}

	
}
