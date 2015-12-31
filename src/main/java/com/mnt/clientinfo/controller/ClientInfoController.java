package com.mnt.clientinfo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
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
		List<String>x=new ArrayList<String>();
		
		
		Date dNow = new Date( );
	      SimpleDateFormat ft = new SimpleDateFormat (" dd.MM.yyyy ");
	     String s1=ft.format(dNow);
        s1=s1.replace(".","-");
        String s2[]=s1.split("-");
        int i=(Integer.parseInt(s2[1]));
        i=i-1;
        s2[1]=i+"";
        String s3=s2[0]+"-"+s2[1]+"-"+s2[2];
		
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
			vm.setTodate(s1);
			vm.setFromdate(s3);
			finalinfo.add(vm);
		
			x.add(s.getClientName());
		}
		//return x;*/
		return finalinfo;
	}
	
	
	@RequestMapping(value="/selectedclientinfo",method=RequestMethod.GET)
	public @ResponseBody clientinfoVm showclientdatabase(@RequestParam("id")Long id) {
		Client s=Client.findById(id);
     
		
			clientinfoVm vm=new clientinfoVm();
			
			vm.setId(s.getId());
			
			vm.setClientname(s.getClientName());
			
			if(s.getPhoneNo().equals("")||s.getPhoneNo()==null)
			{
			  vm.setPhoneno("NA");
			}
			else
			{
			vm.setPhoneno(s.getPhoneNo());
			}
			
			if(s.getEmail().equals("")||s.getEmail()==null)
			{
			vm.setEmail("NA");
			}else{
				vm.setEmail(s.getEmail());
			}
			
			
			
			if(s.getStreet().equals("")||s.getStreet()==null)
			{
			vm.setStreet("NA");
			}else{
				vm.setStreet(s.getStreet());
			}
			
			if(s.getSubLocation().equals("")||s.getSubLocation()==null)
			{
			vm.setSublocation("NA");
			}else{
				vm.setSublocation(s.getSubLocation());
			}
			
			if(s.getPin().equals("")||s.getPin()==null)
			{
			vm.setPin("NA");
			}else{
				vm.setPin(s.getPin());
			}
			
			
			if(s.getSegment().equals("")||s.getSegment()==null)
			{
			vm.setSagment("NA");
			}else{
				vm.setSagment(s.getSegment());
			}
			
			if(s.getLocality().toString().equals(""))
			{
			vm.setLocality("NA");
			}else{
				vm.setLocality(s.getLocality().toString());
			}
			
			if(s.getWebsite().equals("")||s.getWebsite()==null)
			{
			vm.setWebsite("NA");
			}else{
				vm.setWebsite(s.getWebsite());
			}
			
			if(s.getCountry().toString().equals(""))
			{
			vm.setCountry("NA");
			}else{
				vm.setCountry(s.getCountry().toString());
			}
			
			if(s.getState().toString().equals(""))
			{
				vm.setState("NA");
			}else{
				vm.setState(s.getState().toString());
			}
			
			if(s.getCity().toString().equals(""))
			{
			vm.setCity("NA");
			}else{
				vm.setCity(s.getCity().toString());
			}
			
			if(s.getSubsagment().equals("")||s.getSubsagment()==null)
			{
			vm.setSubsagment("NA");
			}else{
				vm.setSubsagment(s.getSubsagment());
			}
			
			if(s.getNumberOfEmployees().equals("")||s.getNumberOfEmployees()==null)
			{
			vm.setNoofemp("NA");
			}else{
				vm.setNoofemp(s.getNumberOfEmployees());
			}
			
			if(s.getEstabilshmentYear().equals("")||s.getEstabilshmentYear()==null)
			{
			vm.setEstabilshmentyear("NA");
			}else{
				vm.setEstabilshmentyear(s.getEstabilshmentYear());
			}
			
			if(s.getCompanyRegistrationNos().equals("")||s.getCompanyRegistrationNos()==null)
			{
			vm.setCompanyregno("NA");
			}else{
				vm.setCompanyregno(s.getCompanyRegistrationNos());
			}
			
			if(s.getCapital().equals("")||s.getCapital()==null)
			{
			vm.setCapital("NA");
			}else{
				vm.setCapital(s.getCapital());
			}
			
			if(s.getTierLargeMediumSmall().equals("")||s.getTierLargeMediumSmall()==null)
			{
			vm.setTier("NA");
			}else{
				vm.setTier(s.getTierLargeMediumSmall());
			}
			
			if(s.getOfficeAddress().equals("")||s.getOfficeAddress()==null)
			{
			vm.setOfficeaddress("NA");
			}else
			{
				vm.setOfficeaddress(s.getOfficeAddress());
			}
			
			if(s.getFactoryAddress().equals("")||s.getFactoryAddress()==null)
			{
			vm.setFactoryaddress("NA");
			}else{
				vm.setFactoryaddress(s.getFactoryAddress());
			}
			
			if(s.getProductionHeadName().equals("")||s.getProductionHeadName()==null)
			{
			vm.setProductheadname("NA");
			}else{
				vm.setProductheadname(s.getProductionHeadName());
			}
			
			if(s.getProductionHeadCellnos().equals("")||s.getProductionHeadCellnos()==null)
			{
			vm.setProductheadcell("NA");
			}else{
				vm.setProductheadcell(s.getProductionHeadCellnos());
			}
			
			if(s.getRandDheadCellnos().equals("")||s.getRandDheadCellnos()==null)
			{
			vm.setRanddcell("NA");
			}else{
				vm.setRanddcell(s.getRandDheadCellnos());
			}
			
			if(s.getMailIdofProductionHead().equals("")||s.getMailIdofProductionHead()==null)
			{
			vm.setMailhead("NA");
			}else{
				vm.setMailhead(s.getMailIdofProductionHead());
			}
			
			if(s.getAveragestaywithCompanyRandDfirst().equals("")||s.getAveragestaywithCompanyRandDfirst()==null)
			{
			vm.setAvgranddfirst("NA");
			}else{
				vm.setAvgranddfirst(s.getAveragestaywithCompanyRandDfirst());
			}
			
			if(s.getRandDsecondName().equals("")||s.getRandDsecondName()==null)
			{
			vm.setRanddsecondname("NA");
			}else{
				vm.setRanddsecondname(s.getRandDsecondName());
			}
			
			if(s.getRandDsecondCellnos().equals("")||s.getRandDsecondCellnos()==null)
			{
			vm.setRanddsecondcell("NA");
			}else{
				vm.setRanddsecondcell(s.getRandDsecondCellnos());
			}
			
			if(s.getMailIdofRandDhead().equals("")||s.getMailIdofRandDhead()==null)
			{
				vm.setMailrandd("NA");
			}else{
				vm.setMailrandd(s.getMailIdofRandDhead());
			}
			
			if(s.getMailIdofRandDsecondHead().equals("")||s.getMailIdofRandDsecondHead()==null)
			{
			vm.setMailranddsecond("NA");
			}else{
				vm.setMailranddsecond(s.getMailIdofRandDsecondHead());
			}
			
			
			if(s.getExcsieDivision().equals("")||s.getExcsieDivision()==null)
			{
				vm.setExcsiediv("NA");
			}else{
				vm.setExcsiediv(s.getExcsieDivision());
			}
			
			
			
			if(s.getAveragestaywithcompanysecond().equals("")||s.getAveragestaywithcompanysecond()==null)
			{
			vm.setAvgranddsecond("NA");
			}else{
				vm.setAvgranddsecond(s.getAveragestaywithcompanysecond());
			}
			
			if(s.getAccountsHeadName().equals("")||s.getAccountsHeadName()==null)
			{
			vm.setAccountname("NA");
			}else{
				vm.setAccountname(s.getAccountsHeadName());
			}
			
			if(s.getAccountsHeadCellnos().equals("")||s.getAccountsHeadCellnos()==null)
			{
			vm.setAccountcell("NA");
			}else{
				vm.setAccountcell(s.getAccountsHeadCellnos());
			}
			
			if(s.getMailIdofAccountsHead().equals("")||s.getMailIdofAccountsHead()==null)
			{
			vm.setMailaccount("NA");
			}else{
				vm.setMailaccount(s.getMailIdofAccountsHead());
			}
			
			
			if(s.getAccountsSecondName().equals("")||s.getAccountsSecondName()==null)
			{
			vm.setAccountsecondname("NA");
			}else{
				vm.setAccountsecondname(s.getAccountsSecondName());
			}
			
			if(s.getAccountsSecondCellnos().equals("")||s.getAccountsSecondCellnos()==null)
			{
			vm.setAccountsecondcell("NA");
			}else{
				vm.setAccountsecondcell(s.getAccountsSecondCellnos());
			}
			
			if(s.getMailIdofAccountsSecondHead().equals("")||s.getMailIdofAccountsSecondHead()==null)
			{
			vm.setMailaccountsecond("NA");
			}else{
				vm.setMailaccountsecond(s.getMailIdofAccountsSecondHead());
			}
			
			if(s.getPurchaseHeadName().equals("")||s.getPurchaseHeadName()==null)
			{
				vm.setPurchasename("NA");
			}else{
				vm.setPurchasename(s.getPurchaseHeadName());
			}
			
			if(s.getPurchaseHeadCellnos().equals("")||s.getPurchaseHeadCellnos()==null)
			{
			vm.setPurchasecell("NA");
			}else{
				vm.setPurchasecell(s.getPurchaseHeadCellnos());
			}
			
			if(s.getMailIdofPurchaseHead().equals("")||s.getMailIdofPurchaseHead()==null)
			{
			vm.setMailpurchase("NA");
			}else{
				vm.setMailpurchase(s.getMailIdofPurchaseHead());
			}
			
			if(s.getAveragestaywithCompanyBuyerFirst().equals("")||s.getAveragestaywithCompanyBuyerFirst()==null)
			{
			vm.setAvgbuyer("NA");
			}else{
				vm.setAvgbuyer(s.getAveragestaywithCompanyBuyerFirst());
			}
			
			if(s.getPurchaseSecondHeadName().equals("")||s.getPurchaseSecondHeadName()==null)
			{
			vm.setPurchasesecondname("NA");
			}else{
				vm.setPurchasesecondname(s.getPurchaseSecondHeadName());
			}
			
			if(s.getPurchaseSecondHeadCellnos().equals("")||s.getPurchaseSecondHeadCellnos()==null)
			{
			vm.setPurchasesecondcell("NA");
			}else{
				vm.setPurchasesecondcell(s.getPurchaseSecondHeadCellnos());
			}
			
			if(s.getMailIdofSecondPurchaseHead().equals("")||s.getMailIdofSecondPurchaseHead()==null)
			{
			vm.setMailpurchasesecond("NA");
			}else{
				vm.setMailpurchasesecond(s.getMailIdofSecondPurchaseHead());
			}
			
			if(s.getAveragestaywithCompanyBuyerSecond().equals("")||s.getAveragestaywithCompanyBuyerSecond()==null)
			{
			vm.setAvgpurchasesecond("NA");
			}else{
				vm.setAvgpurchasesecond(s.getAveragestaywithCompanyBuyerSecond());
			}
			
			if(s.getOverseassupplier1().equals("")||s.getOverseassupplier1()==null)
			{
			vm.setOverseassupplier1("NA");
			}else
			{
				vm.setOverseassupplier1(s.getOverseassupplier1());
			}
			
			
			if(s.getOverseassupplier2().equals("")||s.getOverseassupplier2()==null)
			{
			vm.setOverseassupplier2("NA");
			}else{
				vm.setOverseassupplier2(s.getOverseassupplier2());
			}
			
			if(s.getOverseassupplier3().equals("")||s.getOverseassupplier3()==null)
			{
			vm.setOverseassupplier3("NA");
			}else{
				vm.setOverseassupplier3(s.getOverseassupplier3());
			}
			
			if(s.getOverseassupplier4().equals("")||s.getOverseassupplier4()==null)
			{
			vm.setOverseassupplier4("NA");
			}else{
				vm.setOverseassupplier4(s.getOverseassupplier4());
			}
			
			if(s.getOverseassupplier5().equals("")||s.getOverseassupplier5()==null)
			{
			vm.setOverseassupplier5("NA");
			}else{
				vm.setOverseassupplier5(s.getOverseassupplier5());
			}
			
			if(s.getLocalsupplier1().equals("")||s.getLocalsupplier1()==null)
			{
			vm.setLocalsupplier1("NA");
			}else{
				vm.setLocalsupplier1(s.getLocalsupplier1());
			}
			
			if(s.getLocalsupplier2().equals("")||s.getLocalsupplier2()==null)
			{
			vm.setLocalsupplier2("NA");
			}else{
				vm.setLocalsupplier2(s.getLocalsupplier2());
			}
			
			if(s.getLocalsupplier3().equals("")||s.getLocalsupplier3()==null)
			{
			vm.setLocalsupplier3("NA");
			}else{
				vm.setLocalsupplier3(s.getLocalsupplier3());
			}
			
			if(s.getLocalsupplier4().equals("")||s.getLocalsupplier4()==null)
			{
			vm.setLocalsupplier4("NA");
			}else{
				vm.setLocalsupplier4(s.getLocalsupplier4());
			}
			
			if(s.getLocalsupplier5().equals("")||s.getLocalsupplier5()==null)
			{
			vm.setLocalsupplier5("NA");
			}else{
				vm.setLocalsupplier5(s.getLocalsupplier5());
			}
			
			
			
			if(s.getDirector1name().equals("")||s.getDirector1name()==null)
			{
			vm.setDirector1name("NA");
			}else{
				vm.setDirector1name(s.getDirector1name());
			}
			
			if(s.getDirector1cellnos().equals("")||s.getDirector1cellnos()==null)
			{
			vm.setDirector1cell("NA");
			}else{
				vm.setDirector1cell(s.getDirector1cellnos());
			}
			
			if(s.getMailIdofDirector1head().equals("")||s.getMailIdofDirector1head()==null)
			{
			vm.setMaildirector1("NA");
			}else{
				vm.setMaildirector1(s.getMailIdofDirector1head());
			}
			
			if(s.getExpert1().equals("")||s.getExpert1()==null)
			{
			vm.setExpert1("NA");
			}else{
				vm.setExpert1(s.getExpert1());
			}
			
			if(s.getQualification1().equals("")||s.getQualification1()==null)
			{
			vm.setQualification1("NA");
			}else{
				vm.setQualification1(s.getQualification1());
			}
			
			if(s.getAgeofDirector1().equals("")||s.getAgeofDirector1()==null)
			{
			vm.setAgedirector1("NA");
			}else{
				vm.setAgedirector1(s.getAgeofDirector1());
			}
			
			if(s.getDirectorworkwithbeforecoestabilshment1().equals("")||s.getDirectorworkwithbeforecoestabilshment1()==null)
			{
			vm.setDirectorwork("NA");
			}else{
				vm.setDirectorwork(s.getDirectorworkwithbeforecoestabilshment1());
			}
			
			if(s.getDirector2name().equals("")||s.getDirector2name()==null)
			{
			vm.setDirector2name("NA");
			}else{
				vm.setDirector2name(s.getDirector2name());
			}
			
			if(s.getDirector2cellnos().equals("")||s.getDirector2cellnos()==null)
			{
			vm.setDirector2cell("NA");
			}else{
				vm.setDirector2cell(s.getDirector2cellnos());
			}
			
			if(s.getMailIdofDirector2head().equals("")||s.getMailIdofDirector2head()==null)
			{
			vm.setMaildirector2("NA");
			}else{
				vm.setMaildirector2(s.getMailIdofDirector2head());
			}
			
			if(s.getExpert2().equals("")||s.getExpert2()==null)
			{
			vm.setExpert2("NA");
			}else
			{
				vm.setExpert2(s.getExpert2());
			}
			
			if(s.getQualification2().equals("")||s.getQualification2()==null)
			{
			vm.setQualification2("NA");
			}else{
				vm.setQualification2(s.getQualification2());
			}
			
			if(s.getRegion().equals("")||s.getRegion()==null)
			{
			vm.setRegion("NA");
			}else{
				vm.setRegion(s.getRegion());
			}
			
			if(s.getLandlinenosofoffice().equals("")||s.getLandlinenosofoffice()==null)
			{
			vm.setLandlineno("NA");
			}else{
				vm.setLandlineno(s.getLandlinenosofoffice());
			}
			
			if(s.getAgeofDirector2().equals("")||s.getAgeofDirector2()==null)
			{
			vm.setAgedirector2("NA");
			}else{
				vm.setAgedirector2(s.getAgeofDirector2());
			}
			
			if(s.getDirector3name().equals("")||s.getDirector3name()==null)
			{
			vm.setDirector3name("NA");
			}else{
				vm.setDirector3name(s.getDirector3name());
			}
			
			if(s.getDirector3Cellnos().equals("")||s.getDirector3Cellnos()==null)
			{
			vm.setDirector3cell("NA");
			}else{
				vm.setDirector3cell(s.getDirector3Cellnos());
			}
			
			if(s.getMailIdofDirector3head().equals("")||s.getMailIdofDirector3head()==null)
			{
			vm.setMaildirector3("NA");
			}else{
				vm.setMaildirector3(s.getMailIdofDirector3head());
			}
			
			if(s.getAgeofDirector3().equals("")||s.getAgeofDirector3()==null)
			{
			vm.setAgedirector3("NA");
			}else{
				vm.setAgedirector3(s.getAgeofDirector3());
			}
			
			if(s.getProduct1().equals("")||s.getProduct1()==null)
			{
			vm.setProduct1("NA");
			}else{
				vm.setProduct1(s.getProduct1());
			}
			
			if(s.getProduct2().equals("")||s.getProduct2()==null)
			{
			vm.setProduct2("NA");
			}else{
				vm.setProduct2(s.getProduct2());
			}
			
			if(s.getProduct3().equals("")||s.getProduct3()==null)
			{
			vm.setProduct3("NA");
			}else{
				vm.setProduct3(s.getProduct3());
			}
			
			if(s.getProduct4().equals("")||s.getProduct4()==null)
			{
			vm.setProduct4("NA");
			}else{
				vm.setProduct4(s.getProduct4());
			}
			
			if(s.getProduct5().equals("")||s.getProduct5()==null)
			{
			vm.setProduct5("NA");
			}else{
				vm.setProduct5(s.getProduct5());
			}
			
			if(s.getProduct6().equals("")||s.getProduct6()==null)
			{
			vm.setProduct6("NA");
			}else{
				vm.setProduct6(s.getProduct6());
			}
			
			if(s.getProduct7().equals("")||s.getProduct7()==null)
			{
			vm.setProduct7("NA");
			}else{
				vm.setProduct7(s.getProduct7());
			}
			
			if(s.getProduct8().equals("")||s.getProduct8()==null)
			{
			vm.setProduct8("NA");
			}else{
				vm.setProduct8(s.getProduct8());
			}
			
			if(s.getProduct9().equals("")||s.getProduct9()==null)
			{
			vm.setProduct9("NA");
			}else{
				vm.setProduct9(s.getProduct9());
			}
			
			if(s.getProduct10().equals("")||s.getProduct10()==null)
			{
			vm.setProduct10("NA");
			}else{
				vm.setProduct10(s.getProduct10());
			}
			
			if(s.getExpert3().equals("")||s.getExpert3()==null)
			{
			vm.setExpert3("NA");
			}else{
				vm.setExpert3(s.getExpert3());
			}
			
			if(s.getQualification3().equals("")||s.getQualification3()==null)
			{
			vm.setQualification("NA");
			}else{
				vm.setQualification(s.getQualification3());
			}
			
			if(s.getTurnoverFy2012().equals("")||s.getTurnoverFy2012()==null)
			{
			vm.setTurnover2012("NA");
			}else{
				vm.setTurnover2012(s.getTurnoverFy2012());
			}
			
			if(s.getTurnoverFy2013().equals("")||s.getTurnoverFy2013()==null)
			{
			vm.setTurnover2013("NA");
			}else{
				vm.setTurnover2013(s.getTurnoverFy2013());
			}
			
			if(s.getTurnoverFy2014().equals("")||s.getTurnoverFy2014()==null)
			{
			vm.setTurnover2014("NA");
			}else
			{
				vm.setTurnover2014(s.getTurnoverFy2014());
			}
			
			if(s.getprojectedTurnoverforfy2015().equals("")||s.getprojectedTurnoverforfy2015()==null)
			{
			vm.setTurnover2015("NA");
			}else{
				vm.setTurnover2015(s.getprojectedTurnoverforfy2015());
			}
			
			if(s.getprojectedTurnoverforfy2016().equals("")||s.getprojectedTurnoverforfy2016()==null)
			{
			vm.setTurnover2016("NA");
			}else{
				vm.setTurnover2016(s.getprojectedTurnoverforfy2016());
			}
			
			if(s.getprojectedTurnoverforfy2017().equals("")||s.getprojectedTurnoverforfy2017()==null)
			{
			vm.setTurnover2017("NA");
			}else
			{
				vm.setTurnover2017(s.getprojectedTurnoverforfy2017());
			}
			
			if(s.getSalesHeadName().equals("")||s.getSalesHeadName()==null)
			{
			vm.setSalesname("NA");
			}else{
				vm.setSalesname(s.getSalesHeadName());
			}
			
			if(s.getAveragepriceofProduct().equals("")||s.getAveragepriceofProduct()==null)
			{
			vm.setAvgproduct("NA");
			}else{
				vm.setAvgproduct(s.getAveragepriceofProduct());
			}
			
			if(s.getPursuingApprovals().equals("")||s.getPursuingApprovals()==null)
			{
			vm.setPursuingapprovals("NA");
			}else
			{
				vm.setPursuingapprovals(s.getPursuingApprovals());
			}
			
			if(s.getBank1().equals("")||s.getBank1()==null)
			{
			vm.setBank1("NA");
			}else{
				vm.setBank1(s.getBank1());
			}
			
			if(s.getBank2().equals("")||s.getBank2()==null)
			{
			vm.setBank2("NA");
			}else{
				vm.setBank2(s.getBank2());
			}
			
			if(s.getBank3().equals("")||s.getBank3()==null)
			{
			vm.setBank3("NA");
			}else{
				vm.setBank3(s.getBank3());
			}
			
			if(s.getLimits().equals("")||s.getLimits()==null)
			{
			vm.setLimit("NA");
			}else{
				vm.setLimit(s.getLimits());
			}
			
			if(s.getVatNo().equals("")||s.getVatNo()==null)
			{
			vm.setVatno("NA");
			}else{
				vm.setVatno(s.getVatNo());
			}
			
			if(s.getCstNo().equals("")||s.getCstNo()==null)
			{
			vm.setCstno("NA");
			}else{
				vm.setCstno(s.getCstNo());
			}
			
			if(s.getExcsieNo().equals("")||s.getExcsieNo()==null)
			{
			vm.setExcsieno("NA");
			}else
			{
				vm.setExcsieno(s.getExcsieNo());
			}
			
			if(s.getExcsieRange().equals("")||s.getExcsieRange()==null)
			{
			vm.setExcsierange("NA");
			}else{
				vm.setExcsierange(s.getExcsieRange());
			}
			
			if(s.getExcsieCommisionrate().equals("")||s.getExcsieCommisionrate()==null)
			{
			vm.setExcsiecomm("NA");
			}else{
				vm.setExcsiecomm(s.getExcsieCommisionrate());
			}
			
			if(s.getPanNo().equals("")||s.getPanNo()==null)
			{
			vm.setPanno("NA");
			}else{
				vm.setPanno(s.getPanNo());
			}
			
			if(s.getFutureProduct().equals("")||s.getFutureProduct()==null)
			{
			vm.setFutureproduct("NA");
			}else
			{
				vm.setFutureproduct(s.getFutureProduct());
			}
			
			if(s.getBenchmark().equals("")||s.getBenchmark()==null)
			{
			vm.setBenchmark("NA");
			}else{
				vm.setBenchmark(s.getBenchmark());
			}
			
			if(s.getCompetitorNo1().equals("")||s.getCompetitorNo1()==null)
			{
			vm.setCompetitorno1("NA");
			}else{
				vm.setCompetitorno1(s.getCompetitorNo1());
			}
			
			
			if(s.getCompetitorNo2().equals("")||s.getCompetitorNo2()==null)
			{
			vm.setCompetitorno2("NA");
			}else{
				vm.setCompetitorno2(s.getCompetitorNo2());
			}
			
			if(s.getCompetitorNo3().equals("")||s.getCompetitorNo3()==null)
			{
			vm.setCompetitorno3("NA");
			}else{
				vm.setCompetitorno3(s.getCompetitorNo3());
			}
			
			if(s.getOrganazationChart().equals("")||s.getOrganazationChart()==null)
			{
			vm.setOrganazationchat("NA");
			}else{
				vm.setOrganazationchat(s.getOrganazationChart());
			}
			
			if(s.getAverageageofEmployee().equals("")||s.getAverageageofEmployee()==null)
			{
				vm.setAvgofemp("NA");
			}else{
				vm.setAvgofemp(s.getAverageageofEmployee());
			}
			
			if(s.getAveragestaywithCompanyFactory().equals("")||s.getAveragestaywithCompanyFactory()==null)
			{
				vm.setAvgfactory("NA");
			}else{
				vm.setAvgfactory(s.getAveragestaywithCompanyFactory());
			}
			
			if(s.getAveragestaywithCompanyArchiechture().equals("")||s.getAveragestaywithCompanyArchiechture()==null)
			{
		    vm.setAvgarchiechture("NA");
			}else{
				vm.setAvgarchiechture(s.getAveragestaywithCompanyArchiechture());
			}
			
			if(s.getContactPerson().equals("")||s.getContactPerson()==null)
			{
		    vm.setCantactname("NA");
			}else{
				vm.setCantactname(s.getContactPerson());
			}
			
			if(s.getDesignation().equals("")||s.getDesignation()==null)
			{
			vm.setDesignation("NA");
			}else{
				vm.setDesignation(s.getDesignation());
			}
			
			if(s.getEmailaddress().equals("")||s.getEmailaddress()==null)
			{
			vm.setEmailaddrerss("NA");
			}else{
				vm.setEmailaddrerss(s.getEmailaddress());
			}
			
			if(s.getTelNo().equals("")||s.getTelNo()==null)
			{
			vm.setTelno("NA");
			}else{
				vm.setTelno(s.getTelNo());
			}
			
			if(s.getHpNo().equals("")||s.getHpNo()==null)
			{
			vm.setHpno("NA");
			}else
			{
				vm.setHpno(s.getHpNo());
			}
			
			if(s.getNavisionID().equals("")||s.getNavisionID()==null)
			{
			vm.setNavisionid("NA");
			}else{
				vm.setNavisionid(s.getNavisionID());
			}
			
			if(s.getRandDheadName().equals("")||s.getRandDheadName()==null)
			{
			vm.setRanddname("NA");
			}else{
				vm.setRanddname(s.getRandDheadName());
			}
			
			if(s.getAveragestaywithcompanysecond().equals("")||s.getAveragestaywithcompanysecond()==null)
			{
			vm.setAvgcomsecond("NA");
			}else{
				vm.setAvgcomsecond(s.getAveragestaywithcompanysecond());
			}
			
			if(s.getCustomerType().equals("")||s.getCustomerType()==null)
			{
				vm.setCustomertype("NA");
			}else{
				vm.setCustomertype(s.getCustomerType());
			}
			
			if(s.getTypeofcompany()==null||s.getTypeofcompany().equals(""))
			{
			vm.setTypeofcompany("NA");
			}else{
				vm.setTypeofcompany(s.getTypeofcompany());
			}
			
			
		
              
             
              
		return vm;
	}
	
	
	
	
	
	
	
}


	