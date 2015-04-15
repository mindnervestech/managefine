package com.mnt.time.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import models.FlexiAttribute;
import models.User;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;








import com.mnt.orghierarchy.vm.FlexiattributeVM;

import dto.fixtures.MenuBarFixture;


@Controller
public class FlexiAttributes {


	 @RequestMapping(value="/defineFlexiAttribute" , method = RequestMethod.GET)
	 public String defineFlexiAttribute(ModelMap model, @CookieValue("username") String username)
	 {	
			User user = User.findByEmail(username);
			System.out.println("user ----"+user);
			model.addAttribute("user",user);
			model.addAttribute("_menuContext",MenuBarFixture.build(username));
			return "defineFlexiAttribute";
	 }
	 
	 
	 @RequestMapping(value="/saveFlexiAttribute",method=RequestMethod.POST)
		public @ResponseBody FlexiattributeVM saveFlexiAttribute(@RequestBody List<FlexiattributeVM> flexiAttVMs,HttpServletRequest request) {
		 FlexiattributeVM flexiattributeVM = new FlexiattributeVM();	
		 for(FlexiattributeVM flexi : flexiAttVMs) {
				System.out.println("department:"+flexi.name);
				System.out.println("department:"+flexi.model);
				FlexiAttribute flexiAttribute = new FlexiAttribute();
				flexiAttribute.setName(flexi.getName());
				flexiAttribute.setType(flexi.getType());
				if(flexi.getModel().equals("Project")){
					flexiAttribute.setModel("models.ProjectFlexi");
					flexiAttribute.setUniqueid(2L);
				}else if(flexi.getModel().equals("User")){
					flexiAttribute.setModel("models.UserFlexi");
					flexiAttribute.setUniqueid(1L);
				}else if(flexi.getModel().equals("Client")){
					flexiAttribute.setModel("models.ClientFlexi");
					flexiAttribute.setUniqueid(3L);
				}else if(flexi.getModel().equals("Supplier")){
					flexiAttribute.setModel("models.SupplierFlexi");
					flexiAttribute.setUniqueid(5L);
				}else if(flexi.getModel().equals("Task")){
					flexiAttribute.setModel("models.TaskFlexi");
					flexiAttribute.setUniqueid(4L);
				}
				flexiAttribute.save();
			
				flexiattributeVM.setName(flexi.getName());
				flexiattributeVM.setType(flexi.getType());
			}
			
			return flexiattributeVM;
		}
	 
	 
	 @RequestMapping(value="/getUserFlexiAttribute",method=RequestMethod.POST)
		public @ResponseBody  List<FlexiattributeVM> getFlexiAttribute(@RequestParam("userId") String userid,HttpServletRequest request) {
		 	
		 System.out.println("user-----"+userid);
		 List<FlexiattributeVM> flist = new ArrayList<>();
		 List<FlexiAttribute> fl = new ArrayList<>();;
		 if(userid.equals("User")){
			  fl = FlexiAttribute.getFieldsByUniqueId(1L);
		}else if(userid.equals("Project")){
			 fl = FlexiAttribute.getFieldsByUniqueId(2L);
		}else if(userid.equals("Client")){
			 fl = FlexiAttribute.getFieldsByUniqueId(3L);
		}else if(userid.equals("Task")){
			 fl = FlexiAttribute.getFieldsByUniqueId(4L);
		}else if(userid.equals("Supplier")){
			 fl = FlexiAttribute.getFieldsByUniqueId(5L);
		}
		 
		 for(FlexiAttribute flexi : fl) {
			 FlexiattributeVM flexiattributeVM = new FlexiattributeVM();
			 flexiattributeVM.setModel(flexi.getModel());
			 flexiattributeVM.setName(flexi.getName());
			 flexiattributeVM.setType(flexi.getType());
			 flist.add(flexiattributeVM);
		  }
		 
		return flist;
		} 
}
