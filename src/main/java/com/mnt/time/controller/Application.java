package com.mnt.time.controller;

import static com.google.common.collect.Lists.transform;
import static play.data.Form.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.ApplyLeave;
import models.Company;
import models.LeaveBalance;
import models.MailSetting;
import models.RoleLeave;
import models.RoleLevel;
import models.Timesheet;
import models.User;

import org.codehaus.jackson.node.ObjectNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import com.custom.domain.LeaveStatus;
import com.custom.domain.RoleLevels;
import com.custom.domain.Status;
import com.custom.domain.TimesheetStatus;
import com.custom.emails.Email;
import com.google.common.base.Function;
import com.mnt.core.ui.component.AutoComplete;
import com.mnt.core.workflow.ActivitiHelper;

import dto.fixtures.MenuBarFixture;



@Controller
public class Application  {
  
	@RequestMapping(value="/index" , method = RequestMethod.GET)
    public String index(ModelMap model, @CookieValue("username") String username) {
		User user = User.findByEmail(username);
		List<LeaveBalance> leavebal=LeaveBalance.find.where().eq("employee_id",user.getId()).findList();
		
		model.addAttribute("_menuContext", MenuBarFixture.build(username));
    	model.addAttribute("user", User.findByEmail(username));
    	model.addAttribute("leaves",leavebal);
    	//return "views/home";
    	return "home";
    }
    @RequestMapping(value="/register" , method = RequestMethod.GET)
	public  String companyregistration()
	{
    	//return ok(views.html.company.companyRegister.render());
		return "company/companyRegister";
	}
	
	@RequestMapping(value="/set" , method = RequestMethod.GET)
	public  String setPassword()
	{
		//return ok(views.html.login.setPassword.render());
	    	return "login/setPassword";
	}
	
	@RequestMapping(value="/complete" , method = RequestMethod.POST)
	public String companycreateAccount(ModelMap model,HttpServletRequest request, final RedirectAttributes redirectAttributes)
	{
		String email,code;
		int a,b;
		
		Form<Company> companyForm = form(Company.class).bindFromRequest(request);
		Company company = companyForm.get();
		company.setCompanyStatus(com.custom.domain.Status.PendingApproval);
		
		email = companyForm.get().companyEmail;
		b = email.length();
		a = email.lastIndexOf("@");
		code  = email.substring(a+1, b);
		
		Company companyCheck = Company.find.where().eq("companyCode", code).findUnique();
		if(companyCheck == null)
			{
				company.setCompanyCode(code);
				company.save();
				
				
				//Send Email to company email
				String recipients = "";
				String subject = "";
				String body = "";
				
				recipients = company.companyEmail;
				subject = "Company Registration Success";
				body = "Company "+company.companyName + " is Registered Successfully. \nPlease Be Patient while SUPER ADMIN Approves It!";
				
				User superAdmin = User.find.where().eq("designation", "SuperAdmin").findUnique();
				MailSetting smtpSetting = MailSetting.find.where().eq("companyObject",superAdmin.companyobject).findUnique();
				Email.sendOnlyMail(smtpSetting,recipients, subject, body);
				//Send Email to super Admin
				
				recipients = superAdmin.email;
				subject = "New Company Registered";
				body = "A New Company "+company.companyEmail +" is Registered.\n Please Take Neccesary Action For It!";
				Email.sendOnlyMail(smtpSetting,recipients, subject, body);
				return "redirect:"+routes.Application.login.url;
			}
		else
			{
			   //flash to transport error message
				redirectAttributes.addFlashAttribute("error", "Please Register Through Other Domain");
				//model.addAttribute("error", "Please Register Through Other Domain");
				return "redirect:"+routes.Application.companyregistration.url;
			}
			
	}
	
	public String menuContext(ModelMap model, @CookieValue("username") String username) {
    	model.addAttribute("_menuContext", MenuBarFixture.build(username));
    	model.addAttribute("user", MenuBarFixture.build(username));
    	
    	return "menuContext";
    }
	
	@RequestMapping(value="/" , method = RequestMethod.GET)
    public String login(ModelMap model) {
        return "login/login";
    }
	
	@RequestMapping(value="/logout" , method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response, final RedirectAttributes redirectAttributes) {
		Cookie[] cookies = request.getCookies();
		
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("username")) {
				cookie.setMaxAge(0);
				cookie.setValue("");
				response.addCookie(cookie);
			}
		}
		
		redirectAttributes.addFlashAttribute("success", "You've been logged out");
        return "redirect:" + routes.Application.login.url;
    }

	public static class Login {

        public String email;
        public String password;
        
        public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String validate() {
        	int a,b;
        	String code;
        	
        	User user= User.find.where().eq("email", email).eq("password", password).findUnique();
    		if (user!=null)
    		{
    			b = email.length();
    			a = email.lastIndexOf("@");
    			code  = email.substring(a+1, b);
    			
    			if("SuperAdmin".equals(user.designation))
    			{
    				return null; // Happy Scenario
    			}
    			
    			if(user.companyobject.getCompanyCode() != null  && user.companyobject.getCompanyCode().equalsIgnoreCase(code))
    			{
    				if(user.companyobject.getCompanyStatus() == com.custom.domain.Status.Disapproved)
    				{
    					return "Company Domain is not approve";
    				}
    				else
    				{
    					if(user.userStatus == com.custom.domain.Status.Disapproved){
    						return "You are Not Yet Approved";
    					}
    					else if(user.userStatus == com.custom.domain.Status.PendingApproval){
    						return "You are Not Yet Approved";
    					}
    					return null; // Happy Scenario
    				}
    			}
    			else
    			{
    				return "Invalid Company Domain";
    			}
    		}
    		else
    		{
    			return "Invalid Password";
    		}
        }
    }
    
	@RequestMapping(value="/login" , method = RequestMethod.POST)
    public String authenticate(ModelMap model, HttpServletRequest request, final RedirectAttributes redirectAttributes,
    		HttpServletResponse response) {
    	Form<Login> loginForm = form(Login.class).bindFromRequest(request);
        String error = null;
        if (User.findByEmail(loginForm.data().get("email"))==null)
        {
        	redirectAttributes.addFlashAttribute("error","You have Entered Username that does not exists");
			redirectAttributes.addFlashAttribute("loginForm", loginForm);
			return "redirect:"+routes.Application.login.url;
        } 
        else
        {
            if(loginForm.globalErrors().size()>0)
            {
            	error = loginForm.globalErrors().get(0).message();
            	redirectAttributes.addFlashAttribute("error",error);
            	redirectAttributes.addFlashAttribute("loginForm", loginForm);
	            return "redirect:"+ routes.Application.login.url;
            } 
            
            response.addCookie(new Cookie("username", loginForm.get().email));
            User user = User.findByEmail(loginForm.get().email);
            if(user.tempPassword == 0)
            	return "redirect:"+routes.Application.index.url;
            else
            	 return "redirect:"+routes.Application.setPassword.url;
            
        }
    }
    
	@RequestMapping(value="/registration" , method = RequestMethod.GET)
    public String registration() {
    	
    	return "login/registration";
    }

    //create a new user in DB
	@RequestMapping(value="/registration" , method = RequestMethod.POST)
    public String createAccount(HttpServletRequest request, final RedirectAttributes redirectAttributes){
    	DynamicForm dynamicForm = DynamicForm.form().bindFromRequest(request);
    	String code,email;
    	email = dynamicForm.get("email");
		code  = email.substring(email.lastIndexOf("@")+1, email.length());
		
    	Form<User> userForm = form(User.class).bindFromRequest(request);
    	User userEmail = User.find.where().eq("email", userForm.get().email).findUnique();
    	if(userEmail == null)
    	{
    			User user = userForm.get();
		    	user.userStatus=com.custom.domain.Status.PendingApproval;
		    	Company companyObj = Company.find.where().eq("companyCode", code).findUnique();
		    	user.companyobject= companyObj;
		    	user.tempPassword = 0;
		    	user.save();
		    try{	
		    	String recipients = "";
		    	String subject = "";
		    	String body = "";
		    	
		    	//send email to user
		    	MailSetting smtpSettings = MailSetting.find.where().eq("companyObject", user.companyobject).findUnique();
		    	recipients = user.email;
		    	subject = "Account Created Successfully.";
		    	body = "Your Account is created Successfully.";
		    	body += "\nUsername :"+ user.email;
		    	body += "\nPassword :"+user.password;
		    	body += "\n...Please wait to be approved by your Company Admin!";
		    	Email.sendOnlyMail(smtpSettings,recipients, subject, body);
		    	
		    	
		    	//send email to company admin
		    	Company company = user.companyobject;
		    	User companyAdmin = User.find.where().
		    			and(Expr.eq("companyobject", company),Expr.eq("designation", "Admin")).findUnique();
		    	
		    	recipients = companyAdmin.email;
		    	subject = "New User Account Creation";
		    	body = "User "+ user.firstName +" is created Successfully.";
		    	body +="\nPlease take Necessary Action regarding Approval/Disapproval of User...";
		    	
		    	User superAdmin = User.find.where().eq("designation", "SuperAdmin").findUnique();
				MailSetting smtpSetting = MailSetting.find.where().eq("companyObject",superAdmin.getCompanyobject()).findUnique();
				Email.sendOnlyMail(smtpSetting,recipients, subject, body);
    		}
    		catch (Exception e) {
    			//ExceptionHandler.onError(request().uri(),e);
    		}
    		return "login/login";
    	}
    	else
    	{
    		//TODO flash("registered", "Email id already exists");
    		redirectAttributes.addFlashAttribute("registered", "Email id already exists");
    		return "redirect:"+routes.Application.registration.url;
    	}
    }
    
	//auto-complete for company name in user registration
	@RequestMapping(value="/companysearch" , method = RequestMethod.GET)
    public String companysearch(String q)
 	{	
    	ObjectNode result = Json.newObject();
		List<Company> companies = Company.find.where().eq("companyStatus", com.custom.domain.Status.Approved).ilike("companyName", q+"%").findList();
		List<AutoComplete> results = transform(companies, toAutoCompleteFormatForCompanyName());
		result.put("results", Json.toJson(results));
    	return "result";
     }
   
	//auto-complete format for company name in user registration
    private Function<models.Company,AutoComplete> toAutoCompleteFormatForCompanyName() {
		return new Function<models.Company, AutoComplete>() {
			@Override
			public AutoComplete apply(models.Company company) {
					return new AutoComplete(company.companyName,company.companyEmail,company.getCompanyCode(),company.id);
				
			}
		};
	}
    
    //to show forgot password page
    @RequestMapping(value="/forgot" , method = RequestMethod.GET)
    public String forgotpassword()
	{
    	//return ok(views.html.forgotpassword.emailvalidate.render());
		return "forgotpassword/emailvalidate";
	}
    
    @RequestMapping(value="/finduserid" , method = RequestMethod.GET)
    public String finduser(HttpServletRequest request, final RedirectAttributes redirectAttributes)
	{
		
			DynamicForm formObj = form().bindFromRequest(request);
			String emailid= formObj.get("inputemail");
			User userobject	=User.find.where().ilike("email",emailid).findUnique();
			if(userobject==null)
			{	
				//TODO flash("success", "WRONG USERNAME.... PLEASE TRY AGAIN");
				redirectAttributes.addFlashAttribute("success", "WRONG USERNAME.... PLEASE TRY AGAIN");
				return "forgotpassword/emailvalidate";
			}
			else
			{		
					MailSetting smtpSettings = MailSetting.find.where().eq("companyObject", userobject.getCompanyobject()).findUnique();
					String recipients = "";
			    	String subject = "";
			    	String body = "";
			    	String passWord = generatePassword();
			    	recipients = userobject.getEmail();
			    	subject = "Password recovery email";
			    	body = "Your Login Details :";
			    	body += "\nUser Name :" + userobject.getEmail();
			    	body += "\nPassword  :" + passWord;
			    	userobject.setTempPassword(1);
			    	userobject.setPassword(passWord);
			    	userobject.update();
			    	Email.sendOnlyMail(smtpSettings,recipients, subject, body);
					return "forgotpassword/displaypassword";
			}
	}
	
    @RequestMapping(value="/accessDeny" , method = RequestMethod.GET)
    public String restrictedPage()
   	{
           return "restrictedPage";
 	}
    @RequestMapping(value="/deploy" , method = RequestMethod.GET)
    public String deployment(){
    	ActivitiHelper.me().doDeploy();
    	//ActivitiHelper.me().doRequestDeploy();
    	//ActivitiHelper.me().doVacationDeploy();
    	return "redirect:"+routes.Application.index.url;
    }
    
    //Code to check whether company code is available or not
    @RequestMapping(value="/checkCompanyCode" , method = RequestMethod.POST)
    @ResponseBody
    public String checkCompanyCodeAvailability(HttpServletRequest request){
    	DynamicForm form = DynamicForm.form().bindFromRequest(request);
		String q = form.get("q");
		Company company = Company.find.where().eq("companyCode", q).findUnique();
		
		if(company == null){
			return Json.toJson (true).toString();
		}
    	return Json.toJson(false).toString();
    }
    
    //Code to check whether company Name is available or not
    @RequestMapping(value="/checkCompanyName" , method = RequestMethod.POST)
    @ResponseBody
    public  String checkCompanyNameAvailability(HttpServletRequest request){
    	DynamicForm form = DynamicForm.form().bindFromRequest(request);
		String q = form.get("q");
		Company company = Company.find.where().eq("companyName", q).findUnique();
		
		if(company == null){
			return Json.toJson(true).toString();
		}
    	return Json.toJson ("Company Name is not available").toString();
    }
    
    //Code to check whether User Name is available or not
    @RequestMapping(value="/checkUserEmail" , method = RequestMethod.POST)
    @ResponseBody
    public String checkUserEmailAvailability(HttpServletRequest request){
    	DynamicForm form = DynamicForm.form().bindFromRequest(request);
		String email = form.get("q");
		int a,b;
    	String code;
    	
    	b = email.length();
		a = email.lastIndexOf("@");
		code  = email.substring(a+1, b);
		
		Company company = Company.find.where().eq("companyCode", code).findUnique();
		if(company != null)
		{
			if(company.companyStatus == com.custom.domain.Status.Approved)
			{
				User user = User.find.where().eq("email", email).findUnique();
				if(user == null){
					return Json.toJson(true).toString();
				}
				else
					return Json.toJson("Email ID is in use").toString();
			}
			else
			{
				return Json.toJson("Company Domain not yet approved").toString();
			}
		} 
		else
		{
			return Json.toJson("Company domain is not yet registered with us,Only employees of registered user can register with us.If you are comapny authorized person please register your company by clicking below link").toString();
		}
}
    @RequestMapping(value="/checkCompanyEmail" , method = RequestMethod.POST)
    @ResponseBody
    public String checkCompanyEmailAvailability(HttpServletRequest request){
    	String email,code;
		int a,b;
    	DynamicForm form = DynamicForm.form().bindFromRequest(request);
		String q = form.get("q");
		b = q.length();
		a = q.lastIndexOf("@");
		code  = q.substring(a+1, b);
		Company companyCheck = Company.find.where().eq("companyCode", code).findUnique();
		
		if(companyCheck == null){
			return Json.toJson(true).toString();
		}
    	return Json.toJson("Please Register Through Other Domain ").toString();
    }
   // @Security.Authenticated(Secured.class)
    @RequestMapping(value="/checkOldPassword" , method = RequestMethod.POST)
    @ResponseBody
    public static String checkOldPassword(HttpServletRequest request,@CookieValue("username") String username){
    	DynamicForm form = DynamicForm.form().bindFromRequest(request);
		String q = form.get("q");
		User user = User.findByEmail(username);
		if(q.equals(user.password))
		{
			return Json.toJson(true).toString();
		}
		else
			return Json.toJson("Wrong Password").toString();
    }
   
    //@Security.Authenticated(Secured.class)
    @RequestMapping(value="/changePassword" , method = RequestMethod.POST)
    public String changePassword(HttpServletRequest request,@CookieValue("username") String username){
    	DynamicForm form = DynamicForm.form().bindFromRequest(request);
    	User user = User.findByEmail(username);
    	String password = form.get("password");
    	user.setPassword(password);
    	user.setTempPassword(0);
    	user.update();
    	return "redirect:"+routes.Application.index.url;
    }
    
    public static String generatePassword()
    {
	    String alphaNumerics = "qwertyuiopasdfghjklzxcvbnm1234567890";
		String t = "";
		for (int i = 0; i < 6; i++) {
		    t += alphaNumerics.charAt((int) (Math.random() * alphaNumerics.length()));
		}
		return t;
    }
    
   @RequestMapping(value="/checkPassword" , method = RequestMethod.POST) 
   public String checkPassword(HttpServletRequest request)
    {
	   	boolean letter = false;
	   	boolean number = false;
    	DynamicForm form = DynamicForm.form().bindFromRequest(request);
		String q = form.get("q");

		if(q.length()>=6)
		{
			if (q.matches("[0-9][0-9]*[A-Za-z][A-Za-z]*")) {
			    System.out.println("Alphanumeric");
			    for (int n=0 ; n< q.length() ; n++)
			    {
			    Character c = q.charAt(n);
			    if (Character.isLetter(c))
			    letter=true;
			    else if (Character.isDigit(c))
			    number=true;
			    }
			    return Json.toJson(number&&letter).toString(); 
			}
			else {
			    return "Remove Special Characters";
			}
		 }
		 else
				return "Minimum 6 characters required";
    } 
   
   public static int count( String username)
   {
	   int count = 0;
	   
	   User user = User.findByEmail(username);
	   
	   if(user==null) return 0;

	   if("Admin".equals(user.getDesignation()))
		   	{
			   Expression exp1 = Expr.eq("companyobject.companyCode", user.getCompanyobject().getCompanyCode());
			   Expression exp2 = Expr.ne("email", user.getEmail());
			   count = User.find.where().ilike("userStatus","PendingApproval").add(exp1).add(exp2).findRowCount();
		   	}
	   
	   
		else if("SuperAdmin".equals(user.getDesignation()))
		   	{
			   count = Company.find.where().ilike("companyStatus","PendingApproval").findRowCount();
		   	}
		else if(RoleLevel.checkUserLevel(user.getId(), user.getRole().getRole_level()))
			{
			
			 count = User.find.where().eq("status", Status.Submitted).findRowCount();
			
			 int count2 = Timesheet.find.where().and(Expr.eq("status", TimesheetStatus.Submitted),Expr.eq("timesheetWith", user)).findRowCount();
			 int count3 = ApplyLeave.find.where().eq("status",LeaveStatus.Submitted).findRowCount();
			count = count2 + count3;
			}
	   
	  
	   return count;
   }
   
   public static int count0( String username)
   {
	   int count0 = 0;
	   
	   User user = User.findByEmail(username);
	   
	   if(user==null) return 0;

	   if("Admin".equals(user.getDesignation()))
		   	{
			   Expression exp1 = Expr.eq("companyobject.companyCode", user.getCompanyobject().getCompanyCode());
			   Expression exp2 = Expr.ne("email", user.getEmail());
			   count0 = User.find.where().ilike("userStatus","PendingApproval").add(exp1).add(exp2).findRowCount();
		   	}
	   return count0;
   }    
   
   
   public static int count1( String username)
   {
	   int count1 = 0;
	   
	   User user = User.findByEmail(username);
	   
	   if(user==null) return 0;
	   
	   if("Admin".equals(user.getDesignation()))
	   	{
		   Expression exp1 = Expr.eq("companyobject.companyCode", user.getCompanyobject().getCompanyCode());
		   Expression exp2 = Expr.ne("email", user.getEmail());
		   count1 = 0;
	   	}
	else if("SuperAdmin".equals(user.getDesignation()))
	   	{
		   count1 = Company.find.where().ilike("companyStatus","PendingApproval").findRowCount();
	   	}
	else if(RoleLevel.checkUserLevel(user.getId(), user.getRole().getRole_level()))
		{
		 count1 = Company.find.where().ilike("companyStatus","PendingApproval").findRowCount();
		// count1 = count0 + count2 + count3;
		}
 
	   
	return count1;  
   } 
   
   public static int count2( String username)
   {
	   int count2 = 0;
	   
	   User user = User.findByEmail(username);
	   
	   if(user==null) return 0;
   
	   if("Admin".equals(user.getDesignation()))
	   	{
		   Expression exp1 = Expr.eq("companyobject.companyCode", user.getCompanyobject().getCompanyCode());
		   Expression exp2 = Expr.ne("email", user.getEmail());
		   count2 = 0;
	   	}
	else if("SuperAdmin".equals(user.getDesignation()))
	   	{
		   count2 = 0;
	   	}
	else if(RoleLevel.checkUserLevel(user.getId(), user.getRole().getRole_level()))
		{
		  count2 = Timesheet.find.where().and(Expr.eq("status", TimesheetStatus.Submitted),Expr.eq("timesheetWith", user)).findRowCount();
		  
		} 
		 return count2;
   }
   
   public static int count3( String username)
   {
	   int count3 = 0;
	   
	   User user = User.findByEmail(username);
	   
	   if(user==null) return 0;
   
	   if("Admin".equals(user.getDesignation()))
	   	{
		   Expression exp1 = Expr.eq("companyobject.companyCode", user.getCompanyobject().getCompanyCode());
		   Expression exp2 = Expr.ne("email", user.getEmail());
		   count3 = 0;
	   	}
	   else if("SuperAdmin".equals(user.getDesignation()))
	   	{
		   count3 = 0;
	   	}
	   else if(RoleLevel.checkUserLevel(user.getId(), user.getRole().getRole_level()))
		{
		 
			count3 = ApplyLeave.find.where().eq("status",LeaveStatus.Submitted).findRowCount();
		
		}
		 return count3;
   }
   
   
   
}
