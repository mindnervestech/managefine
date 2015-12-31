package com.mnt.projectHierarchy.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import models.User;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import au.com.bytecode.opencsv.CSVWriter;

import com.avaje.ebean.SqlRow;
import com.mnt.createProject.model.ProjectPart;
import com.mnt.createProject.model.Projectinstance;
import com.mnt.projectHierarchy.vm.ProjectclassVM;
import com.mnt.projectHierarchy.vm.ProjectsupportattributVM;

import dto.fixtures.MenuBarFixture;
@Controller
public class ProjectHierarchyController {

	@Autowired
	com.mnt.projectHierarchy.service.ProjectHierarchyService projectHierarchyService;
	
	@RequestMapping(value="/defineProjects",method=RequestMethod.GET)
	public String orgHierarchy(@CookieValue("username")String username,Model model) {
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
    	model.addAttribute("user", User.findByEmail(username));
    	//model.addAttribute("data",Json.toJson(roleHierarchyService.getRoleHierarchy()));
		return "defineProjects";
	}
	
	@RequestMapping(value="/AllProjectType",method=RequestMethod.GET)
	public @ResponseBody List AllProjectType() {
		return projectHierarchyService.getAllProjectType();
	}
	
	@RequestMapping(value="/addProjectType",method=RequestMethod.GET)
	public String addProjectType(Model model) {
		return "addProjectType";
	}
	
	@RequestMapping(value="/addProjectTypeValue",method=RequestMethod.GET)
	public String addProjectTypeValue(Model model) {
		return "addProjectTypeValue";
	}
	
	@RequestMapping(value="/editProjectTypeValue",method=RequestMethod.GET)
	public String editProjectTypeValue(Model model) {
		return "editProjectTypeValue";
	}
	
	@RequestMapping(value="/saveproject",method=RequestMethod.POST) 
	public @ResponseBody Long saveproject(@RequestBody ProjectclassVM projectclassVM) {
		return projectHierarchyService.saveproject(projectclassVM);
	}
	
	@RequestMapping(value="/saveProjectChild",method=RequestMethod.POST) 
	public @ResponseBody Long saveProjectChild(@RequestBody ProjectsupportattributVM projectsupportattributVM) {
		return projectHierarchyService.saveProjectChild(projectsupportattributVM);
	}
	
	@RequestMapping(value="/editProjectChild",method=RequestMethod.POST) 
	public @ResponseBody Long editProjectChild(@RequestBody ProjectsupportattributVM projectsupportattributVM) {
		return projectHierarchyService.editProjectChild(projectsupportattributVM);
	}
	
	@RequestMapping(value="/selectProjectType",method=RequestMethod.GET) 
	public @ResponseBody List selectProjectType(@RequestParam("id")Long id) {
		return projectHierarchyService.selectProjectType(id);
	}
	
	
	@RequestMapping(value="edit/project/selectProjectType",method=RequestMethod.GET) 
	public @ResponseBody List selectProjectType1(@RequestParam("id")Long id) {
		return projectHierarchyService.selectProjectType(id);
	}
	
	
	@RequestMapping(value="/editProjectTypeInfo",method=RequestMethod.GET)
	public @ResponseBody List editProjectTypeInfo(@RequestParam("id")Long id) {
		return projectHierarchyService.editProjectTypeInfo(id);
	}
	
	@RequestMapping(value="/deleteProjectChild",method=RequestMethod.GET)
	public @ResponseBody Boolean deleteOrgChild(@RequestParam("id")Long id) {
		return projectHierarchyService.deleteProjectChild(id);
	}
	
	@RequestMapping(value="/savePartNo",method=RequestMethod.POST) 
	public @ResponseBody Long savePartNo(@RequestParam("file")MultipartFile file1,@CookieValue("username")String username) {
		
		File excelfile = new File(file1.getOriginalFilename());
		
			try {
				file1.transferTo(excelfile);
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		
		String filename = file1.getOriginalFilename();
		
		Workbook wb_xssf; 
	    Workbook wb_hssf;
		
		int newRows = 0;
		int updatedRows = 0;
		Sheet sheet = null;
		String  jobNum = ""; 
		String userPosition = "";
		
		try {
			
			 FileInputStream file = new FileInputStream(excelfile);
			 String fileExtn = FilenameUtils.getExtension(filename);
			 
			 if (fileExtn.equalsIgnoreCase("xlsx")){
			       wb_xssf = new XSSFWorkbook(file);
			       
			       sheet = wb_xssf.getSheetAt(0);
		      }
			 
			 if (fileExtn.equalsIgnoreCase("xls")){
			      POIFSFileSystem fs = new POIFSFileSystem(file);
		    	  wb_hssf = new HSSFWorkbook(fs);
		    	  sheet = wb_hssf.getSheetAt(0);
		      }
			 
			 /*if (fileExtn.equalsIgnoreCase("csv")){
			      POIFSFileSystem fs = new POIFSFileSystem(file);
		    	  wb_hssf = new HSSFWorkbook(fs);
		    	  sheet = wb_hssf.getSheetAt(0);
		      }*/
			 
			 ProjectPart projectpart = null;
				Row row;
				String reqNo = null;
				String posName = "";
				String level = "" ;
	 			
				Iterator<Row> rowIterator = sheet.iterator();
				rowIterator.next();
				
			//	List<ProjectPart> pList = ProjectPart.getProjectPartNo();
					
					while (rowIterator.hasNext()) {
						reqNo = null;
						row = rowIterator.next();
						if (!row.getZeroHeight()) {

							
							ProjectPart pp = new ProjectPart();
							Cell c = row.getCell(0);
							
							/*for(ProjectPart part:pList){
								
							}*/
							ProjectPart part = ProjectPart.getPartNo(c.getStringCellValue());
							
							if(part == null){
								switch (c.getCellType()) {
								
								case Cell.CELL_TYPE_STRING:
									
									pp.setPartNo(c.getStringCellValue());
									break;
								}
								
									pp.save();
							}
							
						}	
					}
					
				
				
			 
		} catch (Exception e) {
			
		}
		
		
		return null;
	}
	
	
	
	
}
