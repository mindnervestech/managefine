package models;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import play.data.format.Formats.DateTime;
import play.db.ebean.Model;

import com.avaje.ebean.Expr;
import com.custom.domain.Currency;
import com.custom.helpers.ProjectSearchContext;
import com.mnt.core.helper.SearchContext;
import com.mnt.core.ui.annotation.SearchColumnOnUI;
import com.mnt.core.ui.annotation.SearchFilterOnUI;
import com.mnt.core.ui.annotation.UIFields;
import com.mnt.core.ui.annotation.Validation;
import com.mnt.core.ui.annotation.WizardCardUI;
import com.mnt.time.controller.routes;

@Entity
public class Project extends Model{

	private static final String CHOOSE_MEMBERS = "Choose Members";
	public static final String ENTITY = "Project";  
	public static final String PROJECT_MANAGER = "Project Manager";  
	public static final String CLIENT = "Client Name";
	
	@Id
	@WizardCardUI(name="Basic Info",step=0)
	@UIFields(order=0,label="id",hidden=true)
	public Long id;
	
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=1,label=CLIENT, autocomplete=true)
	@OneToOne(cascade = CascadeType.ALL)
	@Validation(required=true)
	public Client clientName;
	
	@SearchColumnOnUI(rank=1,colName="Project Name",width=40)
	@SearchFilterOnUI(label="Project Name")
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=2,label="Project Name")
	@Validation(required=true)
	public String	projectName;
    
	@SearchColumnOnUI(rank=2,colName="Project Code",width=20)
	@SearchFilterOnUI(label="Project Code")
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=3,label="Project Code")
	@Validation(required=true,remote="projectCodeAvailability", messages="Project Code is already taken")
	public String	projectCode;
	
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=4,label="Project Description")
	public String	projectDescription;
	
	//TODO
	@SearchColumnOnUI(rank=4,colName="Start Date",width=30)
	@WizardCardUI(name="Other Info",step=2)
	@UIFields(order=1,label="Start Date")
	@DateTime(pattern="dd-MM-yyyy")
	@Validation(required=true)
	public Date	startDate;
	
	//TODO
	@SearchColumnOnUI(rank=5,colName="End Date",width=30)
	@WizardCardUI(name="Other Info",step=2)
	@UIFields(order=2,label="End Date")
	@DateTime(pattern="dd-MM-yyyy")
	@Validation(required=true)
	public Date	endDate;
	
	@WizardCardUI(name="Other Info",step=2)
	@UIFields(order=3,label="Budget")
	@Validation(required=true,number=true)
	public Double	budget;
	
	@WizardCardUI(name="Other Info",step=2)
	@UIFields(order=4,label="Currency")
	@Enumerated(EnumType.STRING)
	public Currency currency;
	
	@WizardCardUI(name="Other Info",step=2)
	@UIFields(order=5,label="Effort")
	public Integer efforts;
	
	@SearchColumnOnUI(rank=3,colName="Project Manager",width=60)
	@WizardCardUI(name="Other Info",step=2)
	@UIFields(order=6,label=PROJECT_MANAGER, autocomplete=true)
	@OneToOne(cascade = CascadeType.ALL)
	@Validation(required=true)
	public User projectManager;
	
	
	@WizardCardUI(name="Assign Members",step=3)
	@UIFields(order=1,label=CHOOSE_MEMBERS,multiselect=true)
	@ManyToMany(cascade=CascadeType.ALL)
	public List<User> users;
	
	
	@OneToOne(cascade=CascadeType.ALL)
	public Company companyObj;
	
	@ManyToMany
	public List<Task> task;
	
	public static Model.Finder<Long,Project> find = new Model.Finder<Long,Project>(Long.class, Project.class);
	
	
	
	public static Project findByProjectCode(String query) {
		Project project =  find.where().add(Expr.ilike("projectCode", query)).findUnique();
		return project;
	}
	
	public static Project findById(Long id) {
        return find.where().eq("id", id).findUnique();
    }
	
	public static List<Project> findByIds(List<Long> ids) {
        return find.where().idIn(ids).findList();
    }
	
	
	public static Map<String,String> autoCompleteAction=new HashMap<String, String>();
	
	static {
		autoCompleteAction.put(CLIENT, routes.Clients.findClients.url);
		autoCompleteAction.put(PROJECT_MANAGER, routes.Users.findProjectManagers.url);
		//TODO: Need users of the company not the PMs
		autoCompleteAction.put(CHOOSE_MEMBERS, routes.Users.getCompanyUser.url);
	}
	
	@Override
	public String toString() {
		return projectCode + "," + projectName ;
	}
	
	public static SearchContext  getSearchContext(String onFieldNamePrefix){
		return  ProjectSearchContext.getInstance().withFieldNamePrefix(onFieldNamePrefix);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Client getClientName() {
		return clientName;
	}

	public void setClientName(Client clientName) {
		this.clientName = clientName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Integer getEfforts() {
		return efforts;
	}

	public void setEfforts(Integer efforts) {
		this.efforts = efforts;
	}

	public User getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(User projectManager) {
		this.projectManager = projectManager;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Company getCompanyObj() {
		return companyObj;
	}

	public void setCompanyObj(Company companyObj) {
		this.companyObj = companyObj;
	}

	public List<Task> getTask() {
		return task;
	}

	public void setTask(List<Task> task) {
		this.task = task;
	}


}
