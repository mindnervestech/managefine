package com.mnt.time.controller;

import static play.data.Form.form;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.ApplyLeave;
import models.Company;
import models.LeaveBalance;
import models.LeaveLevel;
import models.LeaveX;
import models.RoleLeave;
import models.RoleLevel;
import models.RoleX;
import models.User;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import utils.ExceptionHandler;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.custom.RoleLeaveBindFromRequest;
import com.custom.domain.LeaveStatus;
import com.custom.domain.RoleDomain;
import com.custom.domain.TypeOfLeave;
import com.custom.helpers.LeaveApplyContext;
import com.custom.helpers.LeaveBucketSearchContext;
import com.custom.helpers.LeaveSave;
import com.custom.workflow.vacation.VacationWorkflowUtils;
import com.google.common.collect.Sets;
import com.mnt.core.domain.DomainEnum;



//import controllers.routes.Status.userSearch;
import dto.fixtures.MenuBarFixture;
//import play.mvc.Controller;

/*@Security.Authenticated(Secured.class)
@BasicAuth*/

@Controller
public class Leaves {
	//@Autowired private TaskScheduler taskScheduler;
	
	 @RequestMapping(value="/leaveIndex" , method = RequestMethod.GET)
	public String applyIndex(ModelMap model, @CookieValue("username") String username) {
		 User user = User.findByEmail(username);
		 
		List<LeaveBalance> leavebal=LeaveBalance.find.where().eq("employee_id",user.getId()).findList();
		List<DomainEnum> leaves=new ArrayList<DomainEnum>();
		for(int i=0;i<leavebal.size();i++)
		{
			leaves.add(new RoleDomain(leavebal.get(i).getLeaveLevel().getId()+"", leavebal.get(i).getLeaveLevel().getLeave_type(), false));
		}
		ApplyLeave.leave_domain = leaves;
		
		//leavebal.get(0).getId();
		List<LeaveBalance> leavebal1 = LeaveBalance.find.where().eq("leaveLevel", user.getLevel()).findList();
	//	System.out.println("hhhh"+leavebal.get(0).getId());
		//leavebal1.getEmployee().getId();
		//LeaveBalance lv=LeaveBalance.findById();
		//User user1=User.findById(leavebal1.getEmployee().getId());
		//System.out.println("User:"+user1.getFirstName()+" "+user1.getLastName());
		model.addAttribute("context",LeaveApplyContext.getInstance().build());
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
    	model.addAttribute("user", user);
    	
    	model.addAttribute("leaves",leavebal);
    	model.addAttribute("leaves1",leavebal1);
    			return "leaveIndex";
		
    }
	 
	 @RequestMapping(value="/leaveEdit" , method = RequestMethod.POST)
	public @ResponseBody String edit(HttpServletRequest request) {
		try {
			LeaveSave saveUtils = new LeaveSave();
			saveUtils.doSave(true,request);
		} catch (Exception e) {
			ExceptionHandler.onError(request.getRequestURI(),e);
		}
		return "Leave Edited Successfully";
    }
	 
	 @RequestMapping(value="/leaveExcelReport" , method = RequestMethod.GET)
	public String excelReport(@CookieValue("username") String username,HttpServletResponse response,HttpServletRequest request ) throws IOException {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("email",username);
		response.setContentType("application/vnd.ms-excel");
		HSSFWorkbook hssfWorkbook = LeaveBucketSearchContext.getInstance().build().doExcel(form);
		File f = new File("excelReport.xls");
		FileOutputStream fileOutputStream = new FileOutputStream(f);
		hssfWorkbook.write(fileOutputStream);
		response.setHeader("Content-Disposition", "attachment; filename=excelReport.xls");
		return "";
    }
	 
	 @RequestMapping(value="/leaveApplyExcelReport" , method = RequestMethod.GET)
	public String excelApplyReport(@CookieValue("username") String username, HttpServletRequest request,HttpServletResponse response) throws IOException  {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("email", username);
		HSSFWorkbook hssfWorkbook =  LeaveApplyContext.getInstance().build().doExcel(form);
		File f = new File("excelReport.xls");
		FileOutputStream fileOutputStream = new FileOutputStream(f);
		hssfWorkbook.write(fileOutputStream);
		response.setHeader("Content-Disposition", "attachment; filename=excelReport.xls");
		return username;
    }
	 
	 @RequestMapping(value="/leavesShowEdit" , method = RequestMethod.GET)
	public String showEdit(ModelMap model, @CookieValue("username") String username,HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		Long id = null;
		try{
			id = Long.valueOf(form.get("query"));
			ApplyLeave status = ApplyLeave.findById(id);
			if(status.getStatus() == LeaveStatus.Approved)
			{
				return "Approved Leave cannot be Edited";
			}
			else
			model.addAttribute("_searchContext",new LeaveApplyContext(ApplyLeave.findById(id)).build());	
			return "editWizard";
		}catch(NumberFormatException nfe){
			ExceptionHandler.onError(request.getRequestURI(),nfe);
		}
		return "Not able to show Results, Check Logs";
	}
    
	 @RequestMapping(value="/leaveCreate" , method = RequestMethod.POST)
	public @ResponseBody String create(@CookieValue("username") String username,HttpServletRequest request) {
		DynamicForm form = form().bindFromRequest(request);
		System.out.println("gg"+form.get("leave_domain").toString());
		 Form<ApplyLeave> leaveForm = form(ApplyLeave.class).bindFromRequest(request);
		LeaveLevel l1 = LeaveLevel.find.where().eq("id", form.get("leave_domain")).findUnique();
		 User user = User.findByEmail(username);
		leaveForm.get().setUser(user);
		leaveForm.get().setPendingWith(user);
		leaveForm.get().setLeaveGuid(UUID.randomUUID().toString());
		leaveForm.get().setStatus (LeaveStatus.Submitted);
		//l1.getLeave_type();
		leaveForm.get().setTypeOfLeave(l1.getLeave_type());
	
		LeaveBalance ll = LeaveBalance.find.where().eq("employee", user).eq("leaveLevel.id",Long.parseLong(form.get("leave_domain"))).findUnique();
		
		{
			 Float nl = Float.parseFloat(leaveForm.get().getNoOfDays()); 
			
			if( nl < ll.getBalance()){
				leaveForm.get().save();
			}else{
				System.out.println("hello");
			}
			
		}
		
		
		if(leaveForm.get().getStatus() == LeaveStatus.Submitted){
			leaveForm.get().setPendingWith(user.getManager());
			leaveForm.get().update();
		}
		
		try{
			VacationWorkflowUtils.startVacationWF(leaveForm.get().leaveGuid);
		}
		catch (Exception e) {
			//ExceptionHandler.onError(request().uri(),e);
		}
		return "Leave Created Successfully";
    }
	 
	 @RequestMapping(value="/leaveDelete" , method = RequestMethod.GET)
	public String delete() {
		return "";
    }
	 
	 @RequestMapping(value="/leaveSearch" , method = RequestMethod.GET)
	public @ResponseBody String search(@CookieValue("username") String username,HttpServletRequest request) {
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("email",username);
		return Json.toJson(LeaveApplyContext.getInstance().build().doSearch(form)).toString();
    }
	 
	 @RequestMapping(value="/leaveApprovalViaMail" , method = RequestMethod.GET)
	public String leaveApprovalViaMail(HttpServletRequest request){
		DynamicForm dynamicForm =DynamicForm .form().bindFromRequest(request);
		String query = dynamicForm.get("q");
		String leaveGUID = dynamicForm.get("id");
		
		ApplyLeave applyLeave = ApplyLeave.find.where().eq("leaveGuid", leaveGUID).findUnique();
		
		boolean isApproved;
		if(query.equals("yes")){
			isApproved = true;
		}
		else{
			isApproved = false;
		}
		
		
		if(applyLeave != null){
			String pid = applyLeave.getProcessInstanceId();
			VacationWorkflowUtils.setVariableToTask(pid, isApproved,applyLeave.getLeaveGuid());
		}else{
		}
		
		return "index";
	}
	
	
	 @RequestMapping(value="/leave/bucketIndex" , method = RequestMethod.GET)
	public String bucketIndex(ModelMap model,@CookieValue("username") String username){
		User user = User.findByEmail(username);
		model.addAttribute("context",LeaveBucketSearchContext.getInstance().build());
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
    	model.addAttribute("user", user);
		return "leaveBucket";
	}
	
	 @RequestMapping(value="/leave/bucketSearch" , method = RequestMethod.GET)
	public @ResponseBody String leaveSearch(@CookieValue("username") String username,HttpServletRequest request) {
		User user = User.findByEmail(username);
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		form.data().put("userEmail", user.getEmail());
		return Json.toJson(LeaveBucketSearchContext.getInstance().build().doSearch(form)).toString();
    }
	 
	 @RequestMapping(value="/leave/approveLeave" , method = RequestMethod.GET)
	public @ResponseBody String approveLeave(@CookieValue("username") String username,HttpServletRequest request){
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		String query = form.data().get("query");
		if(query == null){
			query = form.data().get("leaveID");
		}
		for(String ids : query.split(",")){
			ApplyLeave leave = ApplyLeave.find.byId(Long.parseLong(ids));
			leave.setStatus(LeaveStatus.Approved);
			leave.update(Long.parseLong(ids));
			String pid = leave.getProcessInstanceId();
			VacationWorkflowUtils.setVariableToTask(pid, true,leave.getLeaveGuid());
		}
		Integer count = Application.count(username);
		String notification = count.toString(); 
		String message = "Selected Leave have been Approved";
		Map<String,String> jsonMap = new HashMap<String,String>();
		jsonMap.put("count", notification);
		jsonMap.put("message", message);
		return Json.toJson(jsonMap).toString();
	}
	 
	 @RequestMapping(value="/leave/rejectLeave" , method = RequestMethod.GET)
	public @ResponseBody String rejectLeave(@CookieValue ("username") String username,HttpServletRequest request){
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		String query = form.data().get("query");
		if(query == null){
			query = form.data().get("leaveID");
		}
		for(String ids : query.split(",")){
			ApplyLeave leave = ApplyLeave.find.byId(Long.parseLong(ids));
			leave.setStatus(LeaveStatus.Rejected);
			leave.update(Long.parseLong(ids));
			String pid = leave.getProcessInstanceId();
			VacationWorkflowUtils.setVariableToTask(pid, false,leave.getLeaveGuid());
		}
		Integer count = Application.count(username);
		String notification = count.toString(); 
		String message = "Selected Leave have been Rejected";
		Map<String,String> jsonMap = new HashMap<String,String>();
		jsonMap.put("count", notification);
		jsonMap.put("message", message);
		return Json.toJson(jsonMap).toString();
	}
	 
	 @RequestMapping(value="/leave/retractLeave" , method = RequestMethod.GET)
	public @ResponseBody String retractLeave(HttpServletRequest request){
		DynamicForm form = DynamicForm.form().bindFromRequest(request);
		String query = form.data().get("query");
		ApplyLeave leave = ApplyLeave.find.byId(Long.parseLong(query));
		DateTime today = new DateTime();
		String message = "";
		Map<String,String> jsonMap = new HashMap<String,String>();
		if(today.isBefore(leave.getStartDate().getTime()))
		{
			if(leave.getStatus() == LeaveStatus.Submitted || leave.getStatus() == LeaveStatus.Approved)
			{
				leave.setStatus(LeaveStatus.Withdrawn);
				leave.update();
				message = "Leave has been WithDrawn Successfully";
				jsonMap.put("message", message);
					return Json.toJson(jsonMap).toString();
			}
			else if(leave.status == LeaveStatus.Rejected)
			{
				message = "Leave is Rejected it cannot be Retracted";
				jsonMap.put("message", message);
				return Json.toJson(jsonMap).toString();
			}
			else{
				message = "Leave is Already WithDrawn";
				jsonMap.put("message", message);
				return Json.toJson(jsonMap).toString();
			}
		}
		else{
				message = "Leave Period has Started it cannot be Withdrawn now";
				jsonMap.put("message", message);
				return Json.toJson(jsonMap).toString();
		}
	}
	 @RequestMapping(value="/defineLeaves" , method = RequestMethod.GET)
	 public String defineLeaves(ModelMap model, @CookieValue("username") String username)
	 {	
			User user = User.findByEmail(username);
			LeaveX leaveX = LeaveX.find.where(Expr.eq("company", user.companyobject)).findUnique();
			Form<LeaveX> leaveXForm;
			if(leaveX != null){
				leaveXForm = form(LeaveX.class).fill(leaveX);
			}else{
				LeaveX object = new LeaveX();
				List<LeaveLevel> leaveLevels = new ArrayList<LeaveLevel>();
				leaveLevels.add(new LeaveLevel());
				object.setLeaveLevels(leaveLevels);
				leaveXForm = form(LeaveX.class).fill(object);
					
			}
			List<LeaveLevel>leaveLevels=LeaveLevel.findListByCompany(user.getCompanyobject().getId()); 
			model.addAttribute("user",user);
			model.addAttribute("_menuContext",MenuBarFixture.build(username));
			model.addAttribute("leavexForm",leaveXForm);
			model.addAttribute("leaveLevels",leaveLevels);
			return "defineLeaves";
	 }
	 
	 @RequestMapping(value="/leaveSettings",method = RequestMethod.GET)
	 public String showLeaves(ModelMap model,@CookieValue("username") String username){
		 User user=User.findByEmail(username);
		 List<RoleLeave>leaves= RoleLeave.find.all();
		 List<Long>allTotalValue=new ArrayList<Long>();
		 for(int i=0;i<leaves.size();i++)
			allTotalValue.add(leaves.get(i).getTotal_leave()); 
		 model.addAttribute("user",user);
		 model.addAttribute("leave2RoleMap", RoleTypeByLeaveTypeMap.build(user));
		 model.addAttribute("_menuContext", MenuBarFixture.build(username));
		 return "leaveSettings";
	 } 

	 @RequestMapping(value="/saveLeaves " ,method=RequestMethod.POST)		
		public @ResponseBody String saveLeaves(@CookieValue("username")String username,HttpServletRequest request){
				
			final User user = User.findByEmail(username);
			
			List<RoleLevel> rl2 = RoleLevel.find.all();
			Form<LeaveX> leaveXForm = form(LeaveX.class).bindFromRequest(request);
			LeaveX leaveX = LeaveX.find.where(Expr.eq("company", user.getCompanyobject())).findUnique();
			List<RoleLevel> rolelevel=RoleLevel.findListByCompany(user.getCompanyobject().getId());
			
			if(leaveX == null){
				leaveXForm.get().setCompany(user.getCompanyobject());
				leaveXForm.get().save();
				
				for(LeaveLevel _rl : leaveXForm.get().getLeaveLevels()){
					if(_rl.getId()!=null)
					{
						_rl.update();
					} else {
						_rl.setLeaveX(leaveX);
						_rl.save();
					}
					updateLeaveBalance(_rl, user);
					 updateRoleLeave(_rl,rolelevel);
					
				}
			}else{
				/*leaveXForm.get().setCompany(user.getCompanyobject());
				leaveXForm.get().save();*/
			}
			
			List<LeaveLevel> ll=LeaveLevel.findListByCompany(user.getCompanyobject().getId());
			
			for(RoleLevel r:rolelevel){
				for(LeaveLevel l:ll) {
					
					if(RoleLeave.find.where()
					.eq("roleLevel", r)
					.eq("leaveLevel", l).findUnique() == null) {;
						final RoleLeave rl=new RoleLeave();
						rl.setRoleLevel(r);
						rl.setCompany(user.getCompanyobject());
						rl.setLeaveLevel(l);
						rl.save();
						//taskScheduler.schedule( new Runnable() {
						//	public void run() {
								;
						//	}
						//}, new Date());
						
					}
				}
			}
			return "Defined Leaves has been saved";	 
	 }
	 
	@RequestMapping(value="/saveLeaveValue " ,method=RequestMethod.POST)		
	public @ResponseBody String saveLeaveValue(@CookieValue("username")String username,HttpServletRequest request)
	{
		Form<RoleLeaveBindFromRequest>leaveForm=form(RoleLeaveBindFromRequest.class).bindFromRequest(request);	
		RoleLeave rl=new RoleLeave();
		for(int i=0;i<leaveForm.get().getRoleLeaves().size();i++)
		{
			rl.setId(leaveForm.get().getRoleLeaves().get(i).getId());
			rl.setTotal_leave(leaveForm.get().getRoleLeaves().get(i).getTotal_leave());
			rl.update();
		}
		return "leave value saved";
	}			

	private void updateLeaveBalance(LeaveLevel rl, User user){
		List<User> users = User.find.where().eq("companyobject",user.getCompanyobject()).findList();
		for(User u:users) {
			if(LeaveBalance.find.where().eq("employee", u).eq("leaveLevel", rl).findUnique() == null){
				LeaveBalance lbalance = new LeaveBalance();
				lbalance.employee = u;
				lbalance.leaveLevel = rl;
				lbalance.balance = 0f;
				lbalance.save();
			}
		}
		 
	}
	
	
	private void updateRoleLeave(LeaveLevel rl, List<RoleLevel> rolelevel ){
		List<RoleLevel> rl1 = RoleLevel.find.all();
		//List<User> users = User.find.where().eq("companyobject",user.getCompanyobject()).findList();
		for(RoleLevel r:rl1) {
			if(RoleLeave.find.where().eq("company", rl.getLeaveX().getCompany()).eq("roleLevel", r).eq("leaveLevel", rl).findUnique() == null){
				RoleLeave Rleave = new RoleLeave();
				Rleave.roleLevel = r;
				Rleave.company = rl.leaveX.getCompany();
				Rleave.leaveLevel = rl;
				Rleave.total_leave = 0l;
				Rleave.save();
			}
		}
		 
	}
	
	
	 
	 static class RoleTypeByLeaveTypeMap {
		 public static Map<String,List<LeaveCell>> build(User user ) {
			 List<RoleLeave>roleLeaves=RoleLeave.find.where()
					 .add(Expr.eq("company_id",user.getCompanyobject().getId())).setOrderBy("roleLevel.id, leaveLevel.id").
					 fetch("roleLevel","*").fetch("leaveLevel","*").findList();
			
			 Map<String,List<LeaveCell>> map = new HashMap<String,List<LeaveCell>>();
			 for(RoleLeave rleave : roleLeaves) {
				 String role_name = rleave.getRoleLevel().getRole_name();
				 List<LeaveCell> leaveCells;
				 if(map.containsKey(role_name)){
					 leaveCells = map.get(role_name);
				 } else {
					 leaveCells = new ArrayList<LeaveCell>();
				 }
				 leaveCells.add(new LeaveCell(rleave.getId(), rleave.leaveLevel.getLeave_type(), rleave.getTotal_leave()));
				 map.put(role_name, leaveCells);
			 }
			 return map;
		 }
		 
		 public static class LeaveCell {
			public Long id;
			public String leaveType;
			public Long totalLeave;
			public LeaveCell(Long id, String leave_type, Long totalLeave) {
				this.id = id;
				this.leaveType = leave_type;
				this.totalLeave = totalLeave == null ? 0 : totalLeave;
			}
			public Long getId() {
				return id;
			}
			public String getLeaveType() {
				return leaveType;
			}
			public Long getTotalLeave() {
				return totalLeave;
			}
			
		 }
	 }
	 
	 
	 @RequestMapping(value="/testupdate " ,method=RequestMethod.GET)		
	 public void testupdate() {
		 // Datastructure to hold mapping 
		 //<CompanyID,<RoleID,<LeaveType,Float>>>
		 Map<Long,Map<Long,Map<Long,Float>>> map = new HashMap<Long, Map<Long,Map<Long,Float>>>();
			
		 List<LeaveBalance> leaveBalances ;
		 List<Company> companies = Company.find.all();
		 Map<Long,  Float> value1=null;
		 Map<Long, Map<Long,  Float>> value = null;
		 for(Company company : companies) {
			 RoleX role = RoleX.find.where(Expr.eq("company", company)).findUnique();
			 value = new HashMap<Long, Map<Long,Float>>();
			 for(RoleLevel rl: role.roleLevels) {
				 List<RoleLeave> leaves =  RoleLeave.find.where().eq("company", company).eq("roleLevel", rl).findList();
				 
				 
				 value1 = new HashMap<Long, Float>();
				 
				 for(RoleLeave roleLeave : leaves) {
					 value1.put(roleLeave.leaveLevel.getId(), new Float(roleLeave.total_leave/12.0 ));
				 }
				 
				 value.put(rl.getId(), value1 );
			 }
			 map.put(company.getId(), value );
		 }
		 
		 List<User> users = User.find.all();
		 
		 for(User user :users) {
			 leaveBalances = LeaveBalance.find.where().eq("employee", user).findList();
			 for(LeaveBalance leaveBalance : leaveBalances) {
				 if(user.companyobject == null || user.role == null) break;
				 Float toBeAccrued ;
				 toBeAccrued =map.get(user.companyobject.getId()).get(user.role.getId()).get(leaveBalance.getLeaveLevel().getId());
				 System.out.println("chk val "+toBeAccrued);
				 leaveBalance.setBalance(leaveBalance.balance + toBeAccrued);
				 Ebean.update(leaveBalance,Sets.newHashSet("balance"));//leaveBalance.update();
			 }
		 }
			 
	}
			 
	
	
	 
	 @RequestMapping(value="/testupdate1 " ,method=RequestMethod.GET)		
	 public void testupdate1() {	 
	 
		 Map<Long,Map<Long,Map<Long,Float>>> map = new HashMap<Long, Map<Long,Map<Long,Float>>>();
			
		 List<LeaveBalance> leaveBalances ;
		 List<Company> companies = Company.find.all();
		 Map<Long,  Float> value1=null;
		 Map<Long, Map<Long,  Float>> value = null;
		 for(Company company : companies) {
			 RoleX role = RoleX.find.where(Expr.eq("company", company)).findUnique();
			 value = new HashMap<Long, Map<Long,Float>>();
			 for(RoleLevel rl: role.roleLevels) {
				 List<RoleLeave> leaves =  RoleLeave.find.where().eq("company", company).eq("roleLevel", rl).findList();
				 
				 
				 value1 = new HashMap<Long, Float>();
				 
				 for(RoleLeave roleLeave : leaves) {
					 value1.put(roleLeave.leaveLevel.getId(), new Float(roleLeave.total_leave/12.0 ));
				 }
				 
				 value.put(rl.getId(), value1 );
			 }
			 map.put(company.getId(), value );
		 }
		// List<LeaveLevel>ll = LeaveLevel.find.all();
		 
		 List<User> users = User.find.all();
		 
		 for(User user :users) {
			 leaveBalances = LeaveBalance.find.where().eq("employee", user).findList();
			 for(LeaveBalance leaveBalance : leaveBalances) {
				 //float b=0;
				// if(user.companyobject == null || user.role == null) break;
				  LeaveLevel lv = LeaveLevel.find.where().eq("id", leaveBalance.getLeaveLevel().getId()).findUnique() ;
                  
				  System.out.println("chk :"+lv.getCarry_forward(). equals("NO"));
				  
				 if(lv.getCarry_forward(). equals("NO"))
                  {
                	  leaveBalance.setBalance(0.0f);
                	  leaveBalance.update();
                  }else{
				  /*Float toBeAccrued ;
				 toBeAccrued =map.get(user.companyobject.getId()).get(user.role.getId()).get(leaveBalance.getLeaveLevel().getId());
				 System.out.println("chk val "+toBeAccrued);
				 leaveBalance.setBalance(leaveBalance.balance + toBeAccrued);
				 leaveBalance.update();*/
                  }
			 }
		 }
		 
		 
		 
	 }
	 
	 
	 
	 
	 
	 
}
