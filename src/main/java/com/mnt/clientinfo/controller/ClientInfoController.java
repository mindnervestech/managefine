package com.mnt.clientinfo.controller;

import java.util.ArrayList;
import java.util.List;

import models.Client;
import models.Supplier;
import models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnt.clientinfo.vm.clientinfoVm;

import dto.fixtures.MenuBarFixture;
@Controller
public class ClientInfoController {

	@Autowired
	com.mnt.clientinfo.service.ClientInfoService clientInfoService;
	
	
	@RequestMapping(value="/clientinfo",method=RequestMethod.GET)
	public String clientdata(@CookieValue("username")String username,Model model) {
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", User.findByEmail(username));
		return "clientInfo";
	}
	
	@RequestMapping(value="/clientinformation",method=RequestMethod.GET)
	public @ResponseBody List clientdatabase() {
		List<Client>result=Client.getClientList();
		List<clientinfoVm>finalinfo=new ArrayList<clientinfoVm>();
		System.out.println("list size="+result.size());
		List<String>x=new ArrayList<String>();
		for(Client s:result)
		{
			clientinfoVm vm=new clientinfoVm();
			vm.setId(s.getId());
			vm.setClientname(s.getClientName());
			vm.setPhoneno(s.getPhoneNo());
			vm.setEmail(s.getEmail());
			vm.setCountry(s.getCountry().toString());
			vm.setState(s.getState().toString());
			vm.setCity(s.getCity().toString());
			vm.setSagment(s.getSegment());
			vm.setSubsagment(s.getSubsagment());
			finalinfo.add(vm);
		
			System.out.println("x="+s.getClientName());
			x.add(s.getClientName());
		}
		//return x;*/
		return finalinfo;
	}
	
	
	
}


	