package com.mnt.time.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Company;
import models.LeaveBalance;
import models.LeaveLevel;
import models.RoleLeave;
import models.RoleLevel;
import models.RoleX;
import models.User;


/*import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avaje.ebean.Expr;

@Component
public class RunScheduler {

/*	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;*/

	public void run() {

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
			 System.out.println("status :"+user);
		 }

		 
	}
	
	
	public void run1() {
	
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
				 leaveBalance.update();
			 }
		 }
			 
	
		
		
	}
	
}