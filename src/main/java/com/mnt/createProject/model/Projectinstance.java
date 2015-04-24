package com.mnt.createProject.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import models.Supplier;
import models.User;

import com.avaje.ebean.Ebean;
import play.db.ebean.Model;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.custom.helpers.ProjectSearchContext;
import com.mnt.core.helper.SearchContext;
import com.mnt.core.ui.annotation.SearchColumnOnUI;
import com.mnt.core.ui.annotation.SearchFilterOnUI;
import com.mnt.core.ui.annotation.UIFields;
import com.mnt.core.ui.annotation.WizardCardUI;
import com.mnt.time.controller.routes;
@Entity

public class Projectinstance extends Model{
	
	private static final String CHOOSE_MEMBERS = "Choose Members";
	public static final String ENTITY = "Projectinstance";  
	public static final String PROJECTS = "Projects";  
	public static final String CLIENT = "Client Name";
	
	@WizardCardUI(name="Basic Info",step=0)
	@UIFields(order=0,label="id",hidden=true)
	@Id
	public Long id;
	
	
	@SearchColumnOnUI(rank=1,colName="Project Name",width=20)
	@UIFields(order=6,label=PROJECTS, autocomplete=true)
	@SearchFilterOnUI(label="Project Name")
	public String projectName;
	
	
	@SearchColumnOnUI(rank=2,colName="Description",width=40)
	@SearchFilterOnUI(label="Description")
	@Column(length=255)
	public String projectDescription;
	
	@SearchColumnOnUI(rank=3,colName="Client Name",width=20)
	@SearchFilterOnUI(label="Client Name")
	public String clientName;
	
	@SearchColumnOnUI(rank=4,colName="Start Date",width=15)
	@SearchFilterOnUI(label="Start Date")
	public String startDate;
	
	@SearchColumnOnUI(rank=5,colName="End Date",width=15)
	@SearchFilterOnUI(label="End Date")
	public String endDate;
	
	@SearchColumnOnUI(rank=6,colName="Status")
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=6,label="Due Date")
	private String status;
	
	public Long clientId;
	public Long projectid;
	
	@ManyToMany(cascade=CascadeType.ALL)
	public List<User> user;
	
	@ManyToMany(cascade=CascadeType.ALL)
	public List<Supplier> supplier;
	
	@OneToOne
	public User userid;

	public static Finder<Long,Projectinstance> find = new Finder<Long,Projectinstance>(Long.class,Projectinstance.class);
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public Long getProjectid() {
		return projectid;
	}

	public void setProjectid(Long projectid) {
		this.projectid = projectid;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}
	
	public User getUserid() {
		return userid;
	}

	public void setUserid(User userid) {
		this.userid = userid;
	}

	public List<Supplier> getSupplier() {
		return supplier;
	}

	public void setSupplier(List<Supplier> supplier) {
		this.supplier = supplier;
	}

	public static Projectinstance getById(Long id) {
		return find.byId(id);
	}
	
	public static Projectinstance findById(Long id) {
		return find.byId(id);
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static List<Projectinstance> getProjectUser(String username) {
		User user = User.findByEmail(username);
		return find.where().eq("userid.id", user.getId()).findList();
	}



	public static Map<String,String> autoCompleteAction=new HashMap<String, String>();
	
	static {
		autoCompleteAction.put(PROJECTS, routes.Cases.findProject.url);
		//autoCompleteAction.put(CLIENT, routes.Clients.findClients.url);
		//autoCompleteAction.put(PROJECT_MANAGER, routes.Users.findProjectManagers.url);
		//TODO: Need users of the company not the PMs
		//autoCompleteAction.put(CHOOSE_MEMBERS, routes.Users.getCompanyUser.url);
	}
	
	@Override
	public String toString() {
		return id + "," + projectName ;
	}
	
	public static SearchContext  getSearchContext(String onFieldNamePrefix){
		return  ProjectSearchContext.getInstance().withFieldNamePrefix(onFieldNamePrefix);
	}

	public static List<SqlRow> getProjectsOfUser(Long id) {
		String sql = "select projectinstance_user.projectinstance_id from projectinstance_user where projectinstance_user.user_id = :id";
		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		sqlQuery.setParameter("id", id);
		List<SqlRow> list = sqlQuery.findList();
		 return list;
	}


	
}
