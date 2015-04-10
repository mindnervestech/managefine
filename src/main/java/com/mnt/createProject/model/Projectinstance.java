package com.mnt.createProject.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.avaje.ebean.Expr;
import com.custom.helpers.ProjectSearchContext;
import com.mnt.core.helper.SearchContext;
import com.mnt.core.ui.annotation.SearchColumnOnUI;
import com.mnt.core.ui.annotation.SearchFilterOnUI;
import com.mnt.core.ui.annotation.UIFields;
import com.mnt.core.ui.annotation.Validation;
import com.mnt.core.ui.annotation.WizardCardUI;
import com.mnt.orghierarchy.model.Organization;
import com.mnt.projectHierarchy.model.Projectclassnodeattribut;
import com.mnt.roleHierarchy.model.Role;
import com.mnt.time.controller.routes;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
@Entity

public class Projectinstance extends Model{
	
	private static final String CHOOSE_MEMBERS = "Choose Members";
	public static final String ENTITY = "Projectinstance";  
	public static final String PROJECT_MANAGER = "Project Manager";  
	public static final String CLIENT = "Client Name";
	
	@WizardCardUI(name="Basic Info",step=0)
	@UIFields(order=0,label="id",hidden=true)
	@Id
	public Long id;
	
	
	@SearchColumnOnUI(rank=1,colName="Project Name",width=20)
	@SearchFilterOnUI(label="Project Name")
	public String projectName;
	
	
	@SearchColumnOnUI(rank=2,colName="Description",width=50)
	@SearchFilterOnUI(label="Description")
	@Column(length=255)
	public String projectDescription;
	
	@SearchColumnOnUI(rank=3,colName="Client Name",width=30)
	@SearchFilterOnUI(label="Client Name")
	public String clientName;
	
	@SearchColumnOnUI(rank=4,colName="Start Date",width=20)
	@SearchFilterOnUI(label="Start Date")
	public String startDate;
	
	@SearchColumnOnUI(rank=5,colName="End Date",width=20)
	@SearchFilterOnUI(label="End Date")
	public String endDate;
	
	public Long clientId;
	public Long projectid;

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

	public static Projectinstance getById(Long id) {
		return find.byId(id);
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
		return id + "," + projectName ;
	}
	
	public static SearchContext  getSearchContext(String onFieldNamePrefix){
		return  ProjectSearchContext.getInstance().withFieldNamePrefix(onFieldNamePrefix);
	}




	
}
