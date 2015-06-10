package com.mnt.time.controller;

import static com.google.common.collect.Lists.transform;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import models.Client;
import models.RoleLevel;
import models.Supplier;
import models.User;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import play.data.DynamicForm;
import play.libs.Json;

import com.avaje.ebean.Expr;
import com.custom.helpers.ClientSave;
import com.custom.helpers.ClientSearchContext;
import com.google.common.base.Function;
import com.mnt.core.ui.component.AutoComplete;

import dto.fixtures.MenuBarFixture;

@Controller
public class Clients {

	@RequestMapping(value = "/clientSearch", method = RequestMethod.GET)
	public @ResponseBody
	String search(ModelMap model, @CookieValue("username") String username,
			HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("email", username);
		return Json.toJson(
				ClientSearchContext.getInstance().build().doSearch(form))
				.toString();
	}

	@RequestMapping(value = "/clientEdit", method = RequestMethod.POST)
	public @ResponseBody String edit(HttpServletRequest request) {
		try {
			ClientSave saveUtils = new ClientSave();
			saveUtils.doSave(true, request);
		} catch (Exception e) {
			// ExceptionHandler.onError(request().uri(),e);
		}
		return "Clients Edited Successfully";
	}

	@RequestMapping(value = "/clientExcelReport", method = RequestMethod.GET)
	public @ResponseBody
	String excelReport(ModelMap model,
			@CookieValue("username") String username, HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("email", username);
		model.addAttribute("context", ClientSearchContext.getInstance().build());
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", MenuBarFixture.build(username));

		// HSSFWorkbook hssfWorkbook =
		// ClientSearchContext.getInstance().build().doExcel(form);
		File f = new File("excelReport.xls");
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// hssfWorkbook.write(fileOutputStream);
		return "application/vnd.ms-excel";
	}

	@RequestMapping(value = "/clientIndex", method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest request,@CookieValue("username") String username) {
		User user = User.findByEmail(username);
		model.addAttribute("context", ClientSearchContext.getInstance().build());
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
		model.addAttribute("user", user);

		return "clientIndex";
	}

	@RequestMapping(value = "/clientDelete", method = RequestMethod.GET)
	public String delete() {
		return "";
	}

	@RequestMapping(value = "/findClient", method = RequestMethod.GET)
	public @ResponseBody
	String findClients(HttpServletRequest request, @CookieValue("username") String username) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		String query = form.get("query");
		ObjectNode result = Json.newObject();
		List<AutoComplete> results = transform(Clients.findClients(query, username), toAutoCompleteFormatForClient());
		result.put("results", Json.toJson(results));
		return Json.toJson(result).toString();
	}

	private static Function<Client, AutoComplete> toAutoCompleteFormatForClient() {
		return new Function<Client, AutoComplete>() {
			@Override
			public AutoComplete apply(Client client) {
				return new AutoComplete(client.clientName, client.email,
						client.email, client.id);
			}
		};
	}

	@RequestMapping(value = "/clientCreate", method = RequestMethod.POST)
	public @ResponseBody String create(HttpServletRequest request,
			@CookieValue("username") String username) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		String userName = form.get("email");
		
		RoleLevel r = RoleLevel.getRoleByName("Customer");
		String password = Application.generatePassword();
		User u = new User();
		u.setEmail(userName);
		u.setCompanyobject(User.findByEmail(username).companyobject);
		u.setRole(r);
		u.setTempPassword(1);
		u.setUserStatus(com.custom.domain.Status.Approved);
		u.setDesignation("Customer");
		RoleLevel rolLevel  = RoleLevel.getRoleByName("Customer");
		u.setRole(rolLevel);
		u.setPermissions("Case|CaseTo|");
		u.setUsertype("Customer User");
		u.setPassword(password);
		u.save();
		try {
			Map<String, Object> extra = new HashMap<String, Object>();
			extra.put("company", User.findByEmail(username).companyobject);
			ClientSave saveUtils = new ClientSave(extra);
			extra.put("user", u);
			saveUtils.doSave(false, request);
		} catch (Exception e) {
			// ExceptionHandler.onError(request().uri(),e);
		}
		return "Clients Created Successfully";
	}

	@RequestMapping(value = "/clientShowEdit", method = RequestMethod.GET)
	public String showEdit(ModelMap model,
			@CookieValue("username") String username, HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		Long id = null;
		try {
			id = Long.valueOf(form.get("query"));
			model.addAttribute("_searchContext",
					new ClientSearchContext(Client.findById(id)).build());
			return "editWizard";

		} catch (NumberFormatException e) {
			e.printStackTrace();
			//ExceptionHandler.onError(request().uri(),e);
		}
		return "Not able to show Results, Check Logs";

	}
	
	@RequestMapping(value = "/clientdatatoxml", method = RequestMethod.GET)
	public @ResponseBody String showEdit1() {
		List<Client> result=Client.getClientList();
		XSSFWorkbook workbook = new XSSFWorkbook();
	     //Create a blank sheet
	     XSSFSheet sheet = workbook.createSheet("client Data");
      int cellnum = 0;
      int rowid = 0;
      // This code is to create header Start
      Row rowHeader = sheet.createRow(0);
		//for (Supplier s : result) {
			 Cell cell1 = rowHeader.createCell(cellnum++);
			 cell1.setCellValue("Client Name");
			 
			 Cell cell2 = rowHeader.createCell(cellnum++);
			 cell2.setCellValue("email");
			 
			 Cell cell3 = rowHeader.createCell(cellnum++);
			 cell3.setCellValue("Phone");
			 
			 Cell cell4 = rowHeader.createCell(cellnum++);
			 cell4.setCellValue("Country");
			 
			 Cell cell5 = rowHeader.createCell(cellnum++);
			 cell5.setCellValue("City");
			 
			 Cell cell6 = rowHeader.createCell(cellnum++);
			 cell6.setCellValue("Region");
			 
			 Cell cell7 = rowHeader.createCell(cellnum++);
			 cell7.setCellValue("State");
			 
			 Cell cell8 = rowHeader.createCell(cellnum++);
			 cell8.setCellValue("Pin");
			 
			 Cell cell9 = rowHeader.createCell(cellnum++);
			 cell9.setCellValue("sub Location");
			 
			 Cell cell10 = rowHeader.createCell(cellnum++);
			 cell10.setCellValue("Segment");
			 
			 Cell cell11 = rowHeader.createCell(cellnum++);
			 cell11.setCellValue("Sub Segment");
			 
			
			 
			 Cell cell13 = rowHeader.createCell(cellnum++);
			 cell13.setCellValue("Number Of Employees");
			 
			 Cell cell14 = rowHeader.createCell(cellnum++);
			 cell14.setCellValue("Website");
			 
			 Cell cell15 = rowHeader.createCell(cellnum++);
			 cell15.setCellValue("Estabilshment year");
			 
			 Cell cell16 = rowHeader.createCell(cellnum++);
			 cell16.setCellValue("Company Registration No");
			 
			 Cell cell17 = rowHeader.createCell(cellnum++);
			 cell17.setCellValue("Customer type");
			 
			 Cell cell18 = rowHeader.createCell(cellnum++);
			 cell18.setCellValue("Tier");
			 
			 Cell cell19 = rowHeader.createCell(cellnum++);
			 cell19.setCellValue("Office Address");
			 
			 Cell cell20 = rowHeader.createCell(cellnum++);
			 cell20.setCellValue("Factory Address");
			 
			 Cell cell21 = rowHeader.createCell(cellnum++);
			 cell21.setCellValue("Production Head Name");
			 
			 Cell cell22 = rowHeader.createCell(cellnum++);
			 cell22.setCellValue("Production Head Cell nos");
			 
			 Cell cell23 = rowHeader.createCell(cellnum++);
			 cell23.setCellValue("Mail Id of Production  Head");
			 
			 Cell cell24 = rowHeader.createCell(cellnum++);
			 cell24.setCellValue("Average stay with Company");
			 
			 Cell cell25 = rowHeader.createCell(cellnum++);
			 cell25.setCellValue("R&D Head Name");
			 
			 Cell cell26 = rowHeader.createCell(cellnum++);
			 cell26.setCellValue("R&D Head Cell nos");
			 
			 Cell cell27 = rowHeader.createCell(cellnum++);
			 cell27.setCellValue("Mail Id of R&D Head");
			 
			 Cell cell28 = rowHeader.createCell(cellnum++);
			 cell28.setCellValue("Average stay with Company");
			 
			 Cell cell29 = rowHeader.createCell(cellnum++);
			 cell29.setCellValue("R&D Second Name");
			 
			 Cell cell30 = rowHeader.createCell(cellnum++);
			 cell30.setCellValue("R&D Second Cell nos");
			 
			 Cell cell31 = rowHeader.createCell(cellnum++);
			 cell31.setCellValue("Mail Id of R&D Second Head");
			 
			 Cell cell32 = rowHeader.createCell(cellnum++);
			 cell32.setCellValue("Average stay with Company");
			 
			 Cell cell33 = rowHeader.createCell(cellnum++);
			 cell33.setCellValue("Accounts Head Name");
			 
			 Cell cell34 = rowHeader.createCell(cellnum++);
			 cell34.setCellValue("Accounts Head  Cell nos");
			 
			 Cell cell35 = rowHeader.createCell(cellnum++);
			 cell35.setCellValue("Mail Id of Accounts Head");
			 
			 Cell cell36 = rowHeader.createCell(cellnum++);
			 cell36.setCellValue("Accounts Second Name");
			 
			 Cell cell37 = rowHeader.createCell(cellnum++);
			 cell37.setCellValue("Accounts Second Cell nos");
			 
			 Cell cell38 = rowHeader.createCell(cellnum++);
			 cell38.setCellValue("Mail Id of Accounts Second Head");
			 
			 Cell cell39 = rowHeader.createCell(cellnum++);
			 cell39.setCellValue("Purchase Head Name");
			 
			 Cell cell40 = rowHeader.createCell(cellnum++);
			 cell40.setCellValue("Purchase Head Cell nos");
			 
			 Cell cell41 = rowHeader.createCell(cellnum++);
			 cell41.setCellValue("Mail Id of Purchase Head");
			 
			 Cell cell42 = rowHeader.createCell(cellnum++);
			 cell42.setCellValue("Average stay with Company");
			 
			 Cell cell43 = rowHeader.createCell(cellnum++);
			 cell43.setCellValue("Purchase Second Head Name");
			 
			 Cell cell44 = rowHeader.createCell(cellnum++);
			 cell44.setCellValue("Purchase Second Head Cell nos");
			 
			 Cell cell45 = rowHeader.createCell(cellnum++);
			 cell45.setCellValue("Mail Id of Second Purchase Head");
			 
			 Cell cell46 = rowHeader.createCell(cellnum++);
			 cell46.setCellValue("Average stay with Company");
			 
			 Cell cell47 = rowHeader.createCell(cellnum++);
			 cell47.setCellValue("overseas supplier 1");
			 
			 Cell cell48 = rowHeader.createCell(cellnum++);
			 cell48.setCellValue("overseas supplier 2");
			 
			 Cell cell49 = rowHeader.createCell(cellnum++);
			 cell49.setCellValue("overseas supplier 3");
			 
			 Cell cell50 = rowHeader.createCell(cellnum++);
			 cell50.setCellValue("overseas supplier 4");
			 
			 Cell cell51 = rowHeader.createCell(cellnum++);
			 cell51.setCellValue("overseas supplier 5");
			 
			 Cell cell52 = rowHeader.createCell(cellnum++);
			 cell52.setCellValue("Local  supplier 1");
			 
			 Cell cell53 = rowHeader.createCell(cellnum++);
			 cell53.setCellValue("Local  supplier 2");
			 
			 Cell cell54 = rowHeader.createCell(cellnum++);
			 cell54.setCellValue("Local  supplier 3");
			 
			 Cell cell55 = rowHeader.createCell(cellnum++);
			 cell55.setCellValue("Local  supplier 4");
			 
			 Cell cell56 = rowHeader.createCell(cellnum++);
			 cell56.setCellValue("Local  supplier 5");
			 
			 Cell cell57 = rowHeader.createCell(cellnum++);
			 cell57.setCellValue("Director 1Name");
			 
			 Cell cell58 = rowHeader.createCell(cellnum++);
			 cell58.setCellValue("Director 1 Cell nos");
			 
			 Cell cell59 = rowHeader.createCell(cellnum++);
			 cell59.setCellValue("Mail Id of Director 1 Head");
			 
			 Cell cell60 = rowHeader.createCell(cellnum++);
			 cell60.setCellValue("Expert");
			 
			 Cell cell61 = rowHeader.createCell(cellnum++);
			 cell61.setCellValue("Qualification");
			 
			 Cell cell62 = rowHeader.createCell(cellnum++);
			 cell62.setCellValue("Age of Director 1");
			 
			 Cell cell63 = rowHeader.createCell(cellnum++);
			 cell63.setCellValue("Director work ");
			 
			 Cell cell64 = rowHeader.createCell(cellnum++);
			 cell64.setCellValue(" Director 2 Name");
			 
			 Cell cell65 = rowHeader.createCell(cellnum++);
			 cell65.setCellValue("Director 2 Cell nos");
			 
			 Cell cell66 = rowHeader.createCell(cellnum++);
			 cell66.setCellValue("Mail Id of Director 2 Head");
			 
			 Cell cell67 = rowHeader.createCell(cellnum++);
			 cell67.setCellValue("Expert");
			 
			 Cell cell68 = rowHeader.createCell(cellnum++);
			 cell68.setCellValue("Qualification");
			 
			 Cell cell69 = rowHeader.createCell(cellnum++);
			 cell69.setCellValue("Age of Director 2");
			 
			 Cell cell70 = rowHeader.createCell(cellnum++);
			 cell70.setCellValue("Director 3Name");
			 
			 Cell cell71 = rowHeader.createCell(cellnum++);
			 cell71.setCellValue("Director 3 Cell nos");
			 
			 Cell cell72 = rowHeader.createCell(cellnum++);
			 cell72.setCellValue("Mail Id of Director 3 Head");
			 
			 Cell cell73 = rowHeader.createCell(cellnum++);
			 cell73.setCellValue("Expert");
			 
			 Cell cell74 = rowHeader.createCell(cellnum++);
			 cell74.setCellValue("Qualification");
			 
			 Cell cell75 = rowHeader.createCell(cellnum++);
			 cell75.setCellValue("Age of Director 3");
			 
			 Cell cell76 = rowHeader.createCell(cellnum++);
			 cell76.setCellValue("Product 1");
			 
			 Cell cell77 = rowHeader.createCell(cellnum++);
			 cell77.setCellValue("Product 2");
			 
			 Cell cell78 = rowHeader.createCell(cellnum++);
			 cell78.setCellValue("Product 3");
			 
			 Cell cell79 = rowHeader.createCell(cellnum++);
			 cell79.setCellValue("Product 4");
			 
			 Cell cell80 = rowHeader.createCell(cellnum++);
			 cell80.setCellValue("Product 5");
			 
			 Cell cell81 = rowHeader.createCell(cellnum++);
			 cell81.setCellValue("Product 6");
			 
			 Cell cell82 = rowHeader.createCell(cellnum++);
			 cell82.setCellValue("Product 7");
			 
			 Cell cell83 = rowHeader.createCell(cellnum++);
			 cell83.setCellValue("Product 8");
			 
			 Cell cell84 = rowHeader.createCell(cellnum++);
			 cell84.setCellValue("Product 9");
			 
			 Cell cell85 = rowHeader.createCell(cellnum++);
			 cell85.setCellValue("Product 10");
			 
			 Cell cell86 = rowHeader.createCell(cellnum++);
			 cell86.setCellValue("Turnover FY 2012");
			 
			 Cell cell87 = rowHeader.createCell(cellnum++);
			 cell87.setCellValue("Turnover FY 2013");
			 
			 Cell cell88 = rowHeader.createCell(cellnum++);
			 cell88.setCellValue("Turnover FY 2014");
			 
			 Cell cell89 = rowHeader.createCell(cellnum++);
			 cell89.setCellValue("Projected Turnover for FY 2015");
			 
			 Cell cell90 = rowHeader.createCell(cellnum++);
			 cell90.setCellValue("Projected Turnover for FY 2016");
			 
			 Cell cell91 = rowHeader.createCell(cellnum++);
			 cell91.setCellValue("Projected Turnover for FY 2017");
			 
			 Cell cell92 = rowHeader.createCell(cellnum++);
			 cell92.setCellValue("Sales Head Name");
			 
			 Cell cell93 = rowHeader.createCell(cellnum++);
			 cell93.setCellValue("Average price of Product");
			 
			 Cell cell94 = rowHeader.createCell(cellnum++);
			 cell94.setCellValue("Pursuing Approval");
			 
			 Cell cell95 = rowHeader.createCell(cellnum++);
			 cell95.setCellValue("Bank 1");
			 
			 Cell cell96 = rowHeader.createCell(cellnum++);
			 cell96.setCellValue("Bank 2");
			 
			 Cell cell97 = rowHeader.createCell(cellnum++);
			 cell97.setCellValue("Bank 3");
			 
			 Cell cell98 = rowHeader.createCell(cellnum++);
			 cell98.setCellValue("Limits");
			 
			 Cell cell99 = rowHeader.createCell(cellnum++);
			 cell99.setCellValue("VAT No");
			 
			 Cell cell100 = rowHeader.createCell(cellnum++);
			 cell100.setCellValue("CST NO");
			 
			 Cell cell101 = rowHeader.createCell(cellnum++);
			 cell101.setCellValue("Excsie No");
			 
			 Cell cell102 = rowHeader.createCell(cellnum++);
			 cell102.setCellValue("Excsie Range");
			 
			 Cell cell103 = rowHeader.createCell(cellnum++);
			 cell103.setCellValue("Excsie Division");
			 
			 Cell cell104 = rowHeader.createCell(cellnum++);
			 cell104.setCellValue("Excsie Commisionrate");
			 
			 Cell cell105 = rowHeader.createCell(cellnum++);
			 cell105.setCellValue("Pan No");
			 
			 Cell cell106 = rowHeader.createCell(cellnum++);
			 cell106.setCellValue("Future Product");
			 
			 Cell cell107 = rowHeader.createCell(cellnum++);
			 cell107.setCellValue("Benchmark");
			 
			 Cell cell108 = rowHeader.createCell(cellnum++);
			 cell108.setCellValue("No 1");
			 
			 Cell cell109 = rowHeader.createCell(cellnum++);
			 cell109.setCellValue("No 2");
			 
			 Cell cell110 = rowHeader.createCell(cellnum++);
			 cell110.setCellValue("No 3");
			 
			 
			 Cell cell112 = rowHeader.createCell(cellnum++);
			 cell112.setCellValue("Organazation Chart");
			 
			 Cell cell113 = rowHeader.createCell(cellnum++);
			 cell113.setCellValue("Average age of Employee");
			 
			 Cell cell114 = rowHeader.createCell(cellnum++);
			 cell114.setCellValue("Average stay with Company ");
			 
			 Cell cell115 = rowHeader.createCell(cellnum++);
			 cell115.setCellValue("Contact Person");
			 
			 Cell cell116 = rowHeader.createCell(cellnum++);
			 cell116.setCellValue("Designation");
			 
			 Cell cell117 = rowHeader.createCell(cellnum++);
			 cell117.setCellValue("E-mail address");
			 
			 Cell cell118 = rowHeader.createCell(cellnum++);
			 cell118.setCellValue("Tel.No.");
			 
			 Cell cell119 = rowHeader.createCell(cellnum++);
			 cell119.setCellValue("Hp.No.");
			 
			 Cell cell120 = rowHeader.createCell(cellnum++);
			 cell120.setCellValue("Navision ID");
			 
			 
		
		//}
		 
		//create header End
		        
		        rowid = 1;
		        //This code create columns values
		        for(Client s :result){
			 		 cellnum = 0;
			 		 rowHeader = sheet.createRow(rowid++);
	                 
			 		 if(s.getClientName() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getClientName());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
			 		 
			 		 
			 		 
	            	    if(s.getEmail() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getEmail());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getPhoneNo() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getPhoneNo());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getCountry() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getCountry().toString());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getCity() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getCity().toString());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    
	            	    if(s.getRegion() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getRegion());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    
	            	    if(s.getState() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getState().toString());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getPin() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getPin());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getSubLocation() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getSubLocation());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getSegment() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getSegment());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getSubsagment()!= null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getSubsagment());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    
	            	    if(s.getNumberOfEmployees() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getNumberOfEmployees());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    
	            	    if(s.getWebsite() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getWebsite());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    
	            	    if(s.getEstabilshmentYear() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getEstabilshmentYear());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	
	            	    
	            	    if(s.getCompanyRegistrationNos()!= null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getCompanyRegistrationNos());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getCustomerType() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getCustomerType());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getTierLargeMediumSmall() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getTierLargeMediumSmall());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getOfficeAddress() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getOfficeAddress());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getFactoryAddress() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getFactoryAddress());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    
	            	    if(s.getProductionHeadName() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getProductionHeadName());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getProductionHeadCellnos() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getProductionHeadCellnos());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getMailIdofProductionHead() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getMailIdofProductionHead());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	               
	            	    if(s.getAveragestaywithCompanyFactory() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getAveragestaywithCompanyFactory());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getRandDheadName() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getRandDheadName());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getRandDheadCellnos() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getRandDheadCellnos());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getMailIdofRandDhead() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getMailIdofRandDhead());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getAveragestaywithCompanyRandDfirst() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getAveragestaywithCompanyRandDfirst());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getRandDsecondName() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getRandDsecondName());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getRandDsecondCellnos() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getRandDsecondCellnos());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getMailIdofRandDsecondHead()!= null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getMailIdofRandDsecondHead());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getAveragestaywithcompanysecond()!= null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getAveragestaywithcompanysecond());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getAccountsHeadName() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getAccountsHeadName());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getAccountsHeadCellnos() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getAccountsHeadCellnos());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getMailIdofAccountsHead() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getMailIdofAccountsHead());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getAccountsSecondName() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getAccountsSecondName());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getAccountsSecondCellnos() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getAccountsSecondCellnos());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getMailIdofAccountsSecondHead() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getMailIdofAccountsSecondHead());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getPurchaseHeadName() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getPurchaseHeadName());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getPurchaseHeadCellnos() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getPurchaseHeadCellnos());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getMailIdofPurchaseHead() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getMailIdofPurchaseHead());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getAveragestaywithCompanyBuyerFirst() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getAveragestaywithCompanyBuyerFirst());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getPurchaseSecondHeadName() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getPurchaseSecondHeadName());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getPurchaseSecondHeadCellnos() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getPurchaseSecondHeadCellnos());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getMailIdofSecondPurchaseHead() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getMailIdofSecondPurchaseHead());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getAveragestaywithCompanyBuyerSecond() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getAveragestaywithCompanyBuyerSecond());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getOverseassupplier1() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getOverseassupplier1());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getOverseassupplier2() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getOverseassupplier2());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getOverseassupplier3() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getOverseassupplier3());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getOverseassupplier4() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getOverseassupplier4());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getOverseassupplier5() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getOverseassupplier5());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getLocalsupplier1() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getLocalsupplier1());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getLocalsupplier2() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getLocalsupplier2());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getLocalsupplier3() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getLocalsupplier3());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getLocalsupplier4() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getLocalsupplier4());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getLocalsupplier5() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getLocalsupplier5());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getDirector1name() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getDirector1name());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getDirector1cellnos() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getDirector1cellnos());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getMailIdofDirector1head() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getMailIdofDirector1head());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getExpert1() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getExpert1());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getQualification1() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getQualification1());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getAgeofDirector1() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getAgeofDirector1());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getDirectorworkwithbeforecoestabilshment1() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getDirectorworkwithbeforecoestabilshment1());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getDirector2name() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getDirector2name());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getDirector2cellnos()!= null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getDirector2cellnos());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getMailIdofDirector2head() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getMailIdofDirector2head());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getExpert2() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getExpert2());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getQualification2() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getQualification2());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getAgeofDirector2() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getAgeofDirector2());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getDirector3name() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getDirector3name());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getDirector3Cellnos() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getDirector3Cellnos());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getMailIdofDirector3head() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getMailIdofDirector3head());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getExpert3() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getExpert3());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getQualification3()!= null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getQualification3());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getAgeofDirector3() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getAgeofDirector3());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getProduct1() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getProduct1());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getProduct2() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getProduct2());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getProduct3() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getProduct3());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getProduct4() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getProduct4());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getProduct5() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getProduct5());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getProduct6() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getProduct6());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getProduct7() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getProduct7());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getProduct8() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getProduct8());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getProduct9() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getProduct9());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getProduct10() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getProduct10());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getturnoverFy2012() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getturnoverFy2012());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getturnoverFy2013() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getturnoverFy2013());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getturnoverFy2014() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getturnoverFy2014());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getprojectedTurnoverforfy2015() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getprojectedTurnoverforfy2015());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getprojectedTurnoverforfy2016() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getprojectedTurnoverforfy2016());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getprojectedTurnoverforfy2017() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getprojectedTurnoverforfy2017());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getSalesHeadName() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getSalesHeadName());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getAveragepriceofProduct() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getAveragepriceofProduct());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getPursuingApprovals() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getPursuingApprovals());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getBank1() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getBank1());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getBank2() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getBank2());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getBank3() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getBank3());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getLimits() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getLimits());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    
	            	    if(s.getVatNo() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getVatNo());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getCstNo() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getCstNo());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    
	            	    if(s.getExcsieNo() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getExcsieNo());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getExcsieRange() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getExcsieRange());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getExcsieDivision() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getExcsieDivision());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getExcsieCommisionrate() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getExcsieCommisionrate());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getPanNo() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getPanNo());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getFutureProduct() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getFutureProduct());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    
	            	    if(s.getBenchmark() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getBenchmark());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getCompetitorNo1() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getCompetitorNo1());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getCompetitorNo2() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getCompetitorNo2());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getCompetitorNo3() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getCompetitorNo3());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getOrganazationChart() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getOrganazationChart());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getAverageageofEmployee() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getAverageageofEmployee());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getAveragestaywithCompanyArchiechture() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getAveragestaywithCompanyArchiechture());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getContactPerson() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getContactPerson());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getDesignation() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getDesignation());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getEmailaddress() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getEmailaddress());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getTelNo() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getTelNo());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getHpNo() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getHpNo());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
	            	    
	            	    if(s.getNavisionID() != null){
	            	    	Cell cell = rowHeader.createCell(cellnum++);
	            	    	cell.setCellValue(s.getNavisionID());
	            	     }else{
	            	    	 Cell cell = rowHeader.createCell(cellnum++);
	            	    	 cell.setCellValue("");
	            	    }
			 	}
		      //Code  columns values End
		try{
         //Write the workbook in file system
         FileOutputStream out = new FileOutputStream(new File("client.xlsx"));
         workbook.write(out);
         out.close();
         System.out.println("data.xlsx written successfully on disk.");
     }
     catch (Exception e)
     {
         e.printStackTrace();
     }
		
		return null;

	}
	
	public static List<Client> findClients(String query,String username) {
		User user = User.findByEmail(username);
		List<Client> clients =  Client.find.where().and(Expr.eq("company.companyCode", user.companyobject.getCompanyCode()),Expr.ilike("clientName", query+"%")).findList();
		return clients;
	}
	
	
	public static List<Client> getdatatoxml1()
	{
		return Client.find.all();
	}
}
