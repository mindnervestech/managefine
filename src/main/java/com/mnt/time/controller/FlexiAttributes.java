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
	 
	 public static class FlexiattributeList extends ArrayList<FlexiattributeVM> {
		 
	 }
	 
	 @RequestMapping(value="/saveFlexiAttribute",method=RequestMethod.POST)
		public @ResponseBody FlexiattributeVM saveFlexiAttribute(@RequestBody FlexiattributeList flexiAttVMs, HttpServletRequest request) {
		 FlexiattributeVM flexiattributeVM = new FlexiattributeVM();	
		 for(FlexiattributeVM flexi : flexiAttVMs) {
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
				}else if(flexi.getModel().equals("Case")){
					flexiAttribute.setModel("models.CaseFlexi");
					flexiAttribute.setUniqueid(6L);
				}
				
				//flexiAttribute.save();
				if(flexi.getId() == null) {
					flexiAttribute.save();
				} else {
					flexiAttribute.setId(flexi.getId());
					flexiAttribute.update();
				}
				flexiattributeVM.setName(flexi.getName());
				flexiattributeVM.setType(flexi.getType());
			}
			
			return flexiattributeVM;
		}
	 
	 
	 @RequestMapping(value="/getUserFlexiAttribute",method=RequestMethod.POST)
		public @ResponseBody  List<FlexiattributeVM> getFlexiAttribute(@RequestParam("userId") String userid,HttpServletRequest request) {
		 	
		 System.out.println("user-----"+userid);
		 List<FlexiattributeVM> flist = new ArrayList<FlexiattributeVM>();
		 List<FlexiAttribute> fl = new ArrayList<FlexiAttribute>();;
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
		}else if(userid.equals("Case")){
			 fl = FlexiAttribute.getFieldsByUniqueId(6L);
		}
		 
		 
		 for(FlexiAttribute flexi : fl) {
			 FlexiattributeVM flexiattributeVM = new FlexiattributeVM();
			 flexiattributeVM.setId(flexi.getId());
			 flexiattributeVM.setModel(flexi.getModel());
			 flexiattributeVM.setName(flexi.getName());
			 flexiattributeVM.setType(flexi.getType());
			 flist.add(flexiattributeVM);
		  }
		 
		return flist;
		} 
}
