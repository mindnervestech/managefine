package models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.Version;

import play.data.format.Formats;
import play.db.ebean.Model;

import com.avaje.ebean.Expr;
import com.custom.domain.AreaOfExpertise;
import com.custom.domain.Currency;
import com.custom.domain.EmployeeStatus;
import com.custom.domain.Gender;
import com.custom.domain.Salutation;
import com.custom.domain.Status;
import com.custom.helpers.UserSearchContext;
import com.mnt.core.domain.DomainEnum;
import com.mnt.core.helper.SearchContext;
import com.mnt.core.ui.annotation.SearchColumnOnUI;
import com.mnt.core.ui.annotation.SearchFilterOnUI;
import com.mnt.core.ui.annotation.UIFields;
import com.mnt.core.ui.annotation.Validation;
import com.mnt.core.ui.annotation.WizardCardUI;
import com.mnt.createProject.model.Projectinstance;
import com.mnt.createProject.vm.UserVM;
import com.mnt.orghierarchy.model.Organization;
import com.mnt.time.controller.routes;

@Entity
public class User extends Model {
	
	public static final String ENTITY = "User";
	
	private static final String REPORTING_MANAGER = "Reporting Manager";
	
	private static final String ORGINIZATION = "Organization";

	private static final String HR_MANAGER = "HR Manager";
	@Version
    public Timestamp lastUpdate;
	
	@Id
	@WizardCardUI(name="Personal Info",step=0)
	@UIFields(order=0,label="id",hidden=true)
	public Long id;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=1,label="Salutation")
	@Enumerated(EnumType.STRING)
	public Salutation	salutation;
	
	
	@SearchColumnOnUI(rank=2,colName="First Name")
	@SearchFilterOnUI(label="First Name")
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=2,label="First Name", mandatory = true)
	@Validation(required=true)
	public String	firstName;
    
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=3,label="Middle Name", mandatory = true)
    public String	middleName;
    
	@SearchFilterOnUI(label="Last Name")
	@SearchColumnOnUI(rank=3,colName="Last Name")
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=4,label="Last Name",mandatory = true)
	@Validation(required=true)
	public String	lastName;
	
	/*@SearchColumnOnUI(rank=4,colName="Email")
	@SearchFilterOnUI(label="Email")
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=5,label="Username")
	@Validation(required=true,remote="emailAvailability", messages="Username is not available")
	@Column(unique = true)
    public String	email;*/
	@WizardCardUI(name="Personal Info",step=1)
	@UIFields(order=5,label="Username")
	@Validation(required=true,email=true,remote="emailAvailability")
	@Column(unique = true)
	public String email;
	
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=6,label="Gender")
	@Enumerated(EnumType.STRING)
	@Validation(required = true)
	public Gender gender;
	
	@SearchColumnOnUI(rank=1,colName="Employee Id")
	@SearchFilterOnUI(label="Employee Id")
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=7,label="Employee Id")
//	@Validation(required = true,remote="/idAvailability", messages ="Employee Id is already occupied")
	@Validation(required = true)
	public String	employeeId;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=8,label=ORGINIZATION, autocomplete=true)
	@Validation(required = true)
	@OneToOne
	public Organization organization;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=9,label="Currency")
	@Enumerated(EnumType.STRING)
	public Currency	currency;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=10,label="Montly sale/purchase amount ", mandatory = true)
	@Validation(required=true,digits=true)
    public String amount;
    
    
	@Enumerated(EnumType.STRING)
	@WizardCardUI(name="Professional Info",step=2)
	@UIFields(order=1,label="Employee Status")
	public EmployeeStatus status;
	
	@WizardCardUI(name="Professional Info",step=2)
	@UIFields(order=2,label="Date of Joining")
	@Validation(required = true)
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date	hireDate;
    
	@WizardCardUI(name="Professional Info",step=2)
	@UIFields(order=3,label="Annual Income")
	@Validation(number=true)
	public Double annualIncome;
	
	@WizardCardUI(name="Professional Info",step=2)
	@UIFields(order=4,label="Hourly Rate")
	@Validation(required = true)
	public Double hourlyrate;

	@Transient
	@WizardCardUI(name="Professional Info",step=2)
	@UIFields(order=5,label="Designation")
	@Validation(required = true)
	public static List<DomainEnum> rolex;
	
	@Transient
	@WizardCardUI(name="Professional Info",step=2)
	@UIFields(order=8,label="Department")
	@Validation(required = true)
	public static List<DomainEnum> dept;
	
		
	@WizardCardUI(name="Experience",step=3)
	@UIFields(order=1,label="Total No. of Years Experience")
	public String totalYearExp;
	
	@WizardCardUI(name="Experience",step=3)
	@UIFields(order=2,label="No. of Years Experience Previously")
	public String yearExpPrevious;
	
	@WizardCardUI(name="Experience",step=3)
	@UIFields(order=3,label="Team Handled Previously")
	public String teamHandledPreviously;
	
	@WizardCardUI(name="Experience",step=3)
	@UIFields(order=4,label="Team Handled today")
	public String teamHandledToday;
	
	@WizardCardUI(name="Experience",step=3)
	@UIFields(order=5,label="Industry Expertise 1")
	public String industryExpertise1;
	
	@WizardCardUI(name="Experience",step=3)
	@UIFields(order=6,label="Industry Expertise 1")
	public String industryExpertise2;
	
	@WizardCardUI(name="Experience",step=3)
	@UIFields(order=7,label="Industry Expertise 1")
	public String industryExpertise3;
	
	@WizardCardUI(name="Experience",step=3)
	@UIFields(order=8,label="Industry Expertise 1")
	public String industryExpertise4;
	
	@WizardCardUI(name="Area Of Expertise",step=4)
	@UIFields(order=1,label="Problem Solving")
	@Enumerated(EnumType.STRING)
	public AreaOfExpertise problemSolving;
	
	@WizardCardUI(name="Area Of Expertise",step=4)
	@UIFields(order=2,label="Initiative & Leadership")
	@Enumerated(EnumType.STRING)
	public AreaOfExpertise initiativeLeadership;
	
	@WizardCardUI(name="Area Of Expertise",step=4)
	@UIFields(order=3,label="Teamwork")
	@Enumerated(EnumType.STRING)
	public AreaOfExpertise teamwork;
	
	@WizardCardUI(name="Area Of Expertise",step=4)
	@UIFields(order=4,label="Planning & Organization")
	@Enumerated(EnumType.STRING)
	public AreaOfExpertise planningOrganization;
	
	@WizardCardUI(name="Area Of Expertise",step=4)
	@UIFields(order=5,label="Design & Development")
	@Enumerated(EnumType.STRING)
	public AreaOfExpertise designDevelopment;
	
	@WizardCardUI(name="Area Of Expertise",step=4)
	@UIFields(order=6,label="Project Management")
	@Enumerated(EnumType.STRING)
	public AreaOfExpertise projectManagement;
	
	@WizardCardUI(name="Area Of Expertise",step=4)
	@UIFields(order=7,label="Client Co-ordination")
	@Enumerated(EnumType.STRING)
	public AreaOfExpertise clientCoordination;
	
	@WizardCardUI(name="Area Of Expertise",step=4)
	@UIFields(order=8,label="Innovation")
	@Enumerated(EnumType.STRING)
	public AreaOfExpertise innovation;
	
	@WizardCardUI(name="Area Of Expertise",step=4)
	@UIFields(order=9,label="Sales")
	@Enumerated(EnumType.STRING)
	public AreaOfExpertise sales;
	
	@WizardCardUI(name="Experience Breakup",step=5)
	@UIFields(order=1,label="From")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date from1;
	
	@WizardCardUI(name="Experience Breakup",step=5)
	@UIFields(order=2,label="To")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date to1;
	
	@WizardCardUI(name="Experience Breakup",step=5)
	@UIFields(order=3,label="From")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date from2;
	
	@WizardCardUI(name="Experience Breakup",step=5)
	@UIFields(order=4,label="To")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date to2;
	
	@WizardCardUI(name="Experience Breakup",step=5)
	@UIFields(order=5,label="From")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date from3;
	
	@WizardCardUI(name="Experience Breakup",step=5)
	@UIFields(order=6,label="To")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date to3;
	
	@WizardCardUI(name="Experience Breakup",step=5)
	@UIFields(order=7,label="From")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date from4;
	
	@WizardCardUI(name="Experience Breakup",step=5)
	@UIFields(order=8,label="To")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date to4;
	
	@WizardCardUI(name="Experience Breakup",step=5)
	@UIFields(order=9,label="From")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date from5;
	
	@WizardCardUI(name="Experience Breakup",step=5)
	@UIFields(order=10,label="To")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date to5;
	
	@WizardCardUI(name="Education Breakup",step=5)
	@UIFields(order=1,label="From")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date fromEdu1;
	
	@WizardCardUI(name="Education Breakup",step=5)
	@UIFields(order=2,label="To")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date toEdu1;
	
	@WizardCardUI(name="Education Breakup",step=5)
	@UIFields(order=3,label="From")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date fromEdu2;
	
	@WizardCardUI(name="Education Breakup",step=5)
	@UIFields(order=4,label="To")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date toEdu2;
	
	@WizardCardUI(name="Education Breakup",step=5)
	@UIFields(order=5,label="From")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date fromEdu3;
	
	@WizardCardUI(name="Education Breakup",step=5)
	@UIFields(order=6,label="To")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date toEdu3;
	
	@WizardCardUI(name="Education Breakup",step=5)
	@UIFields(order=7,label="From")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date fromEdu4;
	
	@WizardCardUI(name="Education Breakup",step=5)
	@UIFields(order=8,label="To")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date toEdu4;
	
	@WizardCardUI(name="Education Breakup",step=5)
	@UIFields(order=9,label="From")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date fromEdu5;
	
	@WizardCardUI(name="Education Breakup",step=5)
	@UIFields(order=10,label="To")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date toEdu5;
	
	
	@WizardCardUI(name="Flexi Attribute",step=6)
	@UIFields(order=1,label="flexiAttributes")
	@OneToMany(cascade=CascadeType.PERSIST)
	public List<UserFlexi> flexiAttributes;
	
	@OneToOne(cascade=CascadeType.ALL)
	public Company companyobject;
	
	@SearchColumnOnUI(rank=5,colName="Designation")
	public String designation;
	
	@SearchColumnOnUI(rank=6,colName="Department")
	public String department;
	
	public String usertype;
	
	@OneToOne
	public RoleLevel role;
	
	@OneToOne
	public LeaveLevel level;
	
	
	@WizardCardUI(name="Professional Info",step=3)
	@UIFields(order=6,label=REPORTING_MANAGER, autocomplete=true)
	@Validation(required = true)
	@OneToOne
	public User manager;
	
	@WizardCardUI(name="Professional Info",step=2)
	@UIFields(order=7,label="Date of Release")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date releaseDate;
	
	//@WizardCardUI(name="Professional Info",step=2)
	//@UIFields(order=6,label=HR_MANAGER, autocomplete=true)
	@OneToOne
	public User hrmanager;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="user")
	public List<Timesheet> timesheets;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="user")
	public List<Feedback> feedbacks;
	
	@Column(length=700)
	public String permissions;
	
	public String getPermissions(){
		return permissions;
	}
	
	public void setPermissions(String permissions){
		this.permissions = permissions;
	}
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="user")
	public List<ApplyLeave> leaves;
	
	public Integer 	tempPassword;
	public String	password;
	public Boolean	resetFlag;
    public Integer	failedLoginAttempt;
    public Date		createDate;
    public Date		modifiedDate;
    public Boolean	passwordReset;
    @Enumerated(EnumType.STRING)
	public Status 	userStatus;
    
    public String processInstanceId;
    
    @ManyToMany
	public List<Project> project;
    
   /* @ManyToMany
   	public List<Projectinstance> projectinstance;*/
	
    public static Model.Finder<Long,User> find = new Model.Finder<Long,User>(Long.class, User.class);
	
	public static User findById(Long id) {
        return find.where().eq("id", id).findUnique();
    }
	
	public static List<User> findUserByRoleAndCompany(Long id,Long companyid) {
       // return find.where().eq("role.id", id).findList();   
    	return find.where().eq("role.id", id).eq("companyobject.id", companyid).findList();
    }
	
	public static List<User> findByCompanyId(Long id) {
        return find.where().eq("companyobject.id", id).findList();
    }
	
	public static List<User> findByOrganizationId(Long id) {
        return find.where().eq("organization.id", id).findList();
    }
	
	public static List<User> findByIds(List<Long> ids) {
        return find.where().idIn(ids).findList();
    }
	
	public static List<User> getUserList() {
		return find.all();
	}
	
	public static List<User> findByManager(User user) {
		return find.where().eq("manager", user).findList();
	}
	
	public static List<User> findByUserType() {
		return find.where().eq("usertype", "User").findList();
	}
	
	public static List<User> findByManagerBycompny(User user,String query, Long projectId) {
		List<User> uList = new ArrayList<User>();
		Projectinstance projectinstance = Projectinstance.findById(projectId);
		for(User us:projectinstance.getUser()){
			
			User user2  = User.findByMember(user, query, us.getId());
			if(user2 != null){
			User uVm = new User();
			uVm.setFirstName(user2.getFirstName());
			uVm.setLastName(user2.getLastName());
			uVm.setEmail(user2.getEmail());
			uVm.setId(user2.getId());
			uList.add(uVm);
			}
		}
		return uList;
		
	}
	
	public static User findByMember(User user,String query, Long userId) {
				
		return find.where().and(Expr.eq("id", userId),Expr.or(Expr.ilike("firstName", query+"%"),Expr.ilike("lastName", query+"%")))
	       		.findUnique();
	}
	
	
	public static User authenticate(String email, String password, String companyCode) {
		User user= find.where().eq("email", email).eq("password", password).findUnique();
				
		if (user!=null)
		{
			if("SuperAdmin".equals(user.designation))
			{
				return user;
			}
			
			return find.where().eq("email", email).eq("password", password).eq("companyobject.companyCode",companyCode)
							.findUnique();
		}	
		else
			return null;
	}
	
    public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	/**
     * Retrieve a User from email.
     */
    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }
	
	public static SearchContext  getSearchContext(String onFieldNamePrefix){
		return  UserSearchContext.getInstance().withFieldNamePrefix(onFieldNamePrefix);
	}
	
	// Note from Dev: I want this code to be in model, dont move it to some other place in name of refactoring.
	// This field is more coupled with field level, may be it not Best to put the code here.
	public static Map<String,String> autoCompleteAction=new HashMap<String, String>();
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	static {
		autoCompleteAction.put(HR_MANAGER, routes.Users.findHRUser.url);
		autoCompleteAction.put(ORGINIZATION, routes.Users.findOrganizations.url);
		autoCompleteAction.put(REPORTING_MANAGER, routes.Users.findProjectManagers.url);
	}
	
	@Override
	public String toString() {
		return getFirstName() + ","  + getLastName() + "("+getDesignation()+")";
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Salutation getSalutation() {
		return salutation;
	}

	public void setSalutation(Salutation salutation) {
		this.salutation = salutation;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public EmployeeStatus getStatus() {
		return status;
	}

	public void setStatus(EmployeeStatus status) {
		this.status = status;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Double getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(Double annualIncome) {
		this.annualIncome = annualIncome;
	}

	public Double getHourlyrate() {
		return hourlyrate;
	}

	public void setHourlyrate(Double hourlyrate) {
		this.hourlyrate = hourlyrate;
	}

	public Company getCompanyobject() {
		return companyobject;
	}

	public void setCompanyobject(Company companyobject) {
		this.companyobject = companyobject;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public RoleLevel getRole() {
		return role;
	}

	public void setRole(RoleLevel role) {
		this.role = role;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public User getHrmanager() {
		return hrmanager;
	}

	public void setHrmanager(User hrmanager) {
		this.hrmanager = hrmanager;
	}

	public List<Timesheet> getTimesheets() {
		return timesheets;
	}

	public void setTimesheets(List<Timesheet> timesheets) {
		this.timesheets = timesheets;
	}

	public List<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(List<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	public List<ApplyLeave> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<ApplyLeave> leaves) {
		this.leaves = leaves;
	}

	public Integer getTempPassword() {
		return tempPassword;
	}

	public void setTempPassword(Integer tempPassword) {
		this.tempPassword = tempPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getResetFlag() {
		return resetFlag;
	}

	public void setResetFlag(Boolean resetFlag) {
		this.resetFlag = resetFlag;
	}

	public Integer getFailedLoginAttempt() {
		return failedLoginAttempt;
	}

	public void setFailedLoginAttempt(Integer failedLoginAttempt) {
		this.failedLoginAttempt = failedLoginAttempt;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Boolean getPasswordReset() {
		return passwordReset;
	}

	public void setPasswordReset(Boolean passwordReset) {
		this.passwordReset = passwordReset;
	}

	public Status getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Status userStatus) {
		this.userStatus = userStatus;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public List<Project> getProject() {
		return project;
	}

	public void setProject(List<Project> project) {
		this.project = project;
	}

	public LeaveLevel getLevel() {
		return level;
	}

	public void setLevel(LeaveLevel level) {
		this.level = level;
	}

	public List<UserFlexi> getFlexiAttributes() {
		return flexiAttributes;
	}

	public void setFlexiAttributes(List<UserFlexi> flexiAttributes) {
		this.flexiAttributes = flexiAttributes;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTotalYearExp() {
		return totalYearExp;
	}

	public void setTotalYearExp(String totalYearExp) {
		this.totalYearExp = totalYearExp;
	}

	public String getYearExpPrevious() {
		return yearExpPrevious;
	}

	public void setYearExpPrevious(String yearExpPrevious) {
		this.yearExpPrevious = yearExpPrevious;
	}

	public String getTeamHandledPreviously() {
		return teamHandledPreviously;
	}

	public void setTeamHandledPreviously(String teamHandledPreviously) {
		this.teamHandledPreviously = teamHandledPreviously;
	}

	public String getTeamHandledToday() {
		return teamHandledToday;
	}

	public void setTeamHandledToday(String teamHandledToday) {
		this.teamHandledToday = teamHandledToday;
	}

	public String getIndustryExpertise1() {
		return industryExpertise1;
	}

	public void setIndustryExpertise1(String industryExpertise1) {
		this.industryExpertise1 = industryExpertise1;
	}

	public String getIndustryExpertise2() {
		return industryExpertise2;
	}

	public void setIndustryExpertise2(String industryExpertise2) {
		this.industryExpertise2 = industryExpertise2;
	}

	public String getIndustryExpertise3() {
		return industryExpertise3;
	}

	public void setIndustryExpertise3(String industryExpertise3) {
		this.industryExpertise3 = industryExpertise3;
	}

	public String getIndustryExpertise4() {
		return industryExpertise4;
	}

	public void setIndustryExpertise4(String industryExpertise4) {
		this.industryExpertise4 = industryExpertise4;
	}

	public AreaOfExpertise getProblemSolving() {
		return problemSolving;
	}

	public void setProblemSolving(AreaOfExpertise problemSolving) {
		this.problemSolving = problemSolving;
	}

	public AreaOfExpertise getInitiativeLeadership() {
		return initiativeLeadership;
	}

	public void setInitiativeLeadership(AreaOfExpertise initiativeLeadership) {
		this.initiativeLeadership = initiativeLeadership;
	}

	public AreaOfExpertise getTeamwork() {
		return teamwork;
	}

	public void setTeamwork(AreaOfExpertise teamwork) {
		this.teamwork = teamwork;
	}

	public AreaOfExpertise getPlanningOrganization() {
		return planningOrganization;
	}

	public void setPlanningOrganization(AreaOfExpertise planningOrganization) {
		this.planningOrganization = planningOrganization;
	}

	public AreaOfExpertise getDesignDevelopment() {
		return designDevelopment;
	}

	public void setDesignDevelopment(AreaOfExpertise designDevelopment) {
		this.designDevelopment = designDevelopment;
	}

	public AreaOfExpertise getProjectManagement() {
		return projectManagement;
	}

	public void setProjectManagement(AreaOfExpertise projectManagement) {
		this.projectManagement = projectManagement;
	}

	public AreaOfExpertise getClientCoordination() {
		return clientCoordination;
	}

	public void setClientCoordination(AreaOfExpertise clientCoordination) {
		this.clientCoordination = clientCoordination;
	}

	public AreaOfExpertise getInnovation() {
		return innovation;
	}

	public void setInnovation(AreaOfExpertise innovation) {
		this.innovation = innovation;
	}

	public AreaOfExpertise getSales() {
		return sales;
	}

	public void setSales(AreaOfExpertise sales) {
		this.sales = sales;
	}

	public Date getFrom1() {
		return from1;
	}

	public void setFrom1(Date from1) {
		this.from1 = from1;
	}

	public Date getTo1() {
		return to1;
	}

	public void setTo1(Date to1) {
		this.to1 = to1;
	}

	public Date getFromEdu1() {
		return fromEdu1;
	}

	public void setFromEdu1(Date fromEdu1) {
		this.fromEdu1 = fromEdu1;
	}

	public Date getToEdu1() {
		return toEdu1;
	}

	public void setToEdu1(Date toEdu1) {
		this.toEdu1 = toEdu1;
	}

	public Date getFrom2() {
		return from2;
	}

	public void setFrom2(Date from2) {
		this.from2 = from2;
	}

	public Date getTo2() {
		return to2;
	}

	public void setTo2(Date to2) {
		this.to2 = to2;
	}

	public Date getFrom3() {
		return from3;
	}

	public void setFrom3(Date from3) {
		this.from3 = from3;
	}

	public Date getTo3() {
		return to3;
	}

	public void setTo3(Date to3) {
		this.to3 = to3;
	}

	public Date getFrom4() {
		return from4;
	}

	public void setFrom4(Date from4) {
		this.from4 = from4;
	}

	public Date getTo4() {
		return to4;
	}

	public void setTo4(Date to4) {
		this.to4 = to4;
	}

	public Date getFrom5() {
		return from5;
	}

	public void setFrom5(Date from5) {
		this.from5 = from5;
	}

	public Date getTo5() {
		return to5;
	}

	public void setTo5(Date to5) {
		this.to5 = to5;
	}

	public Date getFromEdu2() {
		return fromEdu2;
	}

	public void setFromEdu2(Date fromEdu2) {
		this.fromEdu2 = fromEdu2;
	}

	public Date getToEdu2() {
		return toEdu2;
	}

	public void setToEdu2(Date toEdu2) {
		this.toEdu2 = toEdu2;
	}

	public Date getFromEdu3() {
		return fromEdu3;
	}

	public void setFromEdu3(Date fromEdu3) {
		this.fromEdu3 = fromEdu3;
	}

	public Date getToEdu3() {
		return toEdu3;
	}

	public void setToEdu3(Date toEdu3) {
		this.toEdu3 = toEdu3;
	}

	public Date getFromEdu4() {
		return fromEdu4;
	}

	public void setFromEdu4(Date fromEdu4) {
		this.fromEdu4 = fromEdu4;
	}

	public Date getToEdu4() {
		return toEdu4;
	}

	public void setToEdu4(Date toEdu4) {
		this.toEdu4 = toEdu4;
	}

	public Date getFromEdu5() {
		return fromEdu5;
	}

	public void setFromEdu5(Date fromEdu5) {
		this.fromEdu5 = fromEdu5;
	}

	public Date getToEdu5() {
		return toEdu5;
	}

	public void setToEdu5(Date toEdu5) {
		this.toEdu5 = toEdu5;
	}

	
	
	
}
