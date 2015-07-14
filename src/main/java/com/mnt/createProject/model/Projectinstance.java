package com.mnt.createProject.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import models.Client;
import models.Supplier;
import models.User;
import play.db.ebean.Model;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.SqlUpdate;
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
	@SearchFilterOnUI(label="Project Name")
	public String projectName;
	
	
	@SearchColumnOnUI(rank=2,colName="Description",width=40)
	@Column(length=255)
	public String projectDescription;
	
	@SearchColumnOnUI(rank=3,colName="Client Name",width=20)
	@SearchFilterOnUI(label="Client Name")
	public String clientName;
	
	@SearchColumnOnUI(rank=7,colName="ProjectType Name",width=20)
	@SearchFilterOnUI(label="ProjectType Name")
	public String projectTypeName;
	
	@SearchColumnOnUI(rank=4,colName="Start Date",width=15)
	@SearchFilterOnUI(label="Start Date")
	public String startDate;
	
	@SearchColumnOnUI(rank=5,colName="End Date",width=15)
	@SearchFilterOnUI(label="End Date")
	public String endDate;
	
	@SearchColumnOnUI(rank=6,colName="Status",width=20)
	@WizardCardUI(name="Basic Info",step=1)
	@SearchFilterOnUI(label="Status")
	@UIFields(order=6,label="Due Date")
	public String status;
	
	public Long clientId;
	@OneToOne
	public Client endCustomer;
	public Long projectid;
	public Long percentage;
	public Double totalEstimatedRevenue;
	public String opportunityNo;
	public String createdDate;
	public String region;
	public String endCustomerLocation;
	public String projectNameApplication;
	public String productionDate;
	public String productLifeTime;
	public String supplierRegistion;
	public String projectLastUpdate;
	public String serialNo;
	public String supplierFae;
	public String supplierSaleperson;
	public String projectWin;
	public String purchaseCustContactNo;
	public String purchaseCustEmail;
	public String remark;
	
	
	/*
	@Transient
	@SearchColumnOnUI(rank=4,colName="Project Type",width=20)
	@SearchFilterOnUI(label="Project Type")
	public String projectType;*/
	
	@ManyToMany(cascade=CascadeType.ALL)
	public List<User> user;
	
	@ManyToMany(cascade=CascadeType.ALL)
	public List<Supplier> supplier;
	
	@OneToOne
	public User userid;
	
	@OneToOne
	public User projectManager;

	public static Finder<Long,Projectinstance> find = new Finder<Long,Projectinstance>(Long.class,Projectinstance.class);
	
	
	public Long getId() {
		return id;
	}
	//@Transactional
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
	

	public Client getEndCustomer() {
		return endCustomer;
	}

	public void setEndCustomer(Client endCustomer) {
		this.endCustomer = endCustomer;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}
	
	public void addUser(List<User> user) {
		this.user.addAll(user);
	}
	
	public Double getTotalEstimatedRevenue() {
		return totalEstimatedRevenue;
	}

	public void setTotalEstimatedRevenue(Double totalEstimatedRevenue) {
		this.totalEstimatedRevenue = totalEstimatedRevenue;
	}

	public void removeAllUser() {
		String sql = "DELETE FROM projectinstancenode_user";
		String pinIds = "(";
		for(int i = 0 ;i<Projectinstancenode.getProjectInstanceById(this.id).size(); i++){
			if(i == Projectinstancenode.getProjectInstanceById(this.id).size()-1){
				pinIds = pinIds + Projectinstancenode.getProjectInstanceById(this.id).get(i).getId() + ")";
				sql = sql+" WHERE projectinstancenode_id IN "+pinIds;
				break;
			} 
			pinIds = pinIds + Projectinstancenode.getProjectInstanceById(this.id).get(i).getId() + ",";
			
		}
		String userIds ="(";
		for(int i = 0 ;i<this.user.size(); i++){
			if(i == this.user.size()-1){
				userIds = userIds + this.user.get(i).getId() + ")";
				sql = sql + " and user_id IN " + userIds; 
				break; 
			}
			userIds = userIds + this.user.get(i).getId() + ",";
		}
		if(!(Projectinstancenode.getProjectInstanceById(this.id).isEmpty() || this.user.isEmpty())){
			SqlUpdate sqlQuery = Ebean.createSqlUpdate(sql);
			sqlQuery.execute(); 
		}
		if(this.user == null)
			return;
		this.user.clear();
		this.deleteManyToManyAssociations("user");
	}

		public User getUserid() {
		return userid;
	}

		public Long getPercentage() {
		return percentage;
	}

	public void setPercentage(Long percentage) {
		this.percentage = percentage;
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
	
	public void addSupplier(List<Supplier> supplier) {
		this.supplier.addAll(supplier);
	}
	
	public User getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(User projectManager) {
		this.projectManager = projectManager;
	}

	
	
	public String getOpportunityNo() {
		return opportunityNo;
	}

	public void setOpportunityNo(String opportunityNo) {
		this.opportunityNo = opportunityNo;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getEndCustomerLocation() {
		return endCustomerLocation;
	}

	public void setEndCustomerLocation(String endCustomerLocation) {
		this.endCustomerLocation = endCustomerLocation;
	}
	
	public String getProjectNameApplication() {
		return projectNameApplication;
	}

	public void setProjectNameApplication(String projectNameApplication) {
		this.projectNameApplication = projectNameApplication;
	}

	public String getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}

	public String getProductLifeTime() {
		return productLifeTime;
	}

	public void setProductLifeTime(String productLifeTime) {
		this.productLifeTime = productLifeTime;
	}

	public String getSupplierRegistion() {
		return supplierRegistion;
	}

	public void setSupplierRegistion(String supplierRegistion) {
		this.supplierRegistion = supplierRegistion;
	}

	public String getProjectLastUpdate() {
		return projectLastUpdate;
	}

	public void setProjectLastUpdate(String projectLastUpdate) {
		this.projectLastUpdate = projectLastUpdate;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getSupplierFae() {
		return supplierFae;
	}

	public void setSupplierFae(String supplierFae) {
		this.supplierFae = supplierFae;
	}

	public String getSupplierSaleperson() {
		return supplierSaleperson;
	}

	public void setSupplierSaleperson(String supplierSaleperson) {
		this.supplierSaleperson = supplierSaleperson;
	}
	public String getPurchaseCustContactNo() {
		return purchaseCustContactNo;
	}
	public void setPurchaseCustContactNo(String purchaseCustContactNo) {
		this.purchaseCustContactNo = purchaseCustContactNo;
	}
	public String getPurchaseCustEmail() {
		return purchaseCustEmail;
	}
	public void setPurchaseCustEmail(String purchaseCustEmail) {
		this.purchaseCustEmail = purchaseCustEmail;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void removeAllSupplier() {
		if(this.supplier == null)
			return;
		this.supplier.clear();
		this.deleteManyToManyAssociations("supplier");
	}

	public static Projectinstance getById(Long id) {
		return find.byId(id);
	}
	
	public static Projectinstance findById(Long id) {
		return find.byId(id);
	}
	
	public static Projectinstance getProjecttackByUser(Long id,Long userId) {
		return find.where().eq("id", id).eq("userid.id", userId).findUnique();
	}
	
	public static Projectinstance getProjecttackByManager(Long id,Long userId) {
		return find.where().eq("id", id).eq("projectManager.id", userId).findUnique();
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getProjectTypeName() {
		return projectTypeName;
	}
	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}
	public String getProjectWin() {
		return projectWin;
	}

	public void setProjectWin(String projectWin) {
		this.projectWin = projectWin;
	}

	public static List<Projectinstance> getProjectUser(String username) {
		User user = User.findByEmail(username);
		return find.where().eq("userid.id", user.getId()).findList();
	}
	
	public static List<Projectinstance> getProjectUserAndquery(String query, String username) {
		User user = User.findByEmail(username);
		List<Projectinstance> pIList = new ArrayList<>();
		
		List<SqlRow> sqlRows = Projectinstance.getProjectsOfUser(user.getId());
		for(SqlRow row: sqlRows) {
			 Projectinstance projectInstance = Projectinstance.getById(row.getLong("projectinstance_id"));
			 pIList.add(projectInstance);
		}
			List<Projectinstance> projectList = Projectinstance.getProjectsOfManager(user);
		if (projectList != null) {
			for (Projectinstance project : projectList) {
				Projectinstance projectInstance = Projectinstance
						.getById(project.getId());
				pIList.add(projectInstance);
			}
		}
		
		
		return pIList;
		//return find.where().eq("userid.id", user.getId()).like("projectName", query+"%").findList();
	}
	
	public static List<Projectinstance> getProjectByUser(Long id) {
		return find.where().eq("userid.id", id).findList();
	}

		
	public static List<Projectinstance> getProjectByClient(Long id) {
		return find.where().eq("clientId", id).findList();
	}
	
	public static List<Projectinstance> getProjectBySupplier(Long id) {
		return find.where().eq("supplier.id", id).findList();
	}
	
	public static Projectinstance getProjecttackByClient(Long clientId,Long userId) {
		return find.where().eq("clientId", clientId).eq("userid.id", userId).findUnique();
	}
	
	public static Projectinstance getProjecttackBySupplier(Long supplierId,Long userId) {
		return find.where().eq("supplier.id", supplierId).eq("userid.id", userId).findUnique();
	}
	
	

	public static List<Projectinstance> getProjectList() {
		return find.all();
	}
	
	public static Projectinstance getProjectByName(String  projectName) {
		return find.where().eq("projectName", projectName).findUnique();
	}
	
	public static List<Projectinstance> getProjectTypeById(Long projectTypeId) {
		return find.where().eq("projectid", projectTypeId).findList();
	}
	
	public static List<Projectinstance> getProjectTypeByUserId(Long projectTypeId,Long userId) {
		return find.where().eq("projectid", projectTypeId).eq("userid.id", userId).findList();
	}
	
	public static List<Projectinstance> getProjectTypeByManagerid(Long projectTypeId,Long userId) {
		return find.where().eq("projectid", projectTypeId).eq("projectManager.id", userId).findList();
	}
	
	public static List<Projectinstance> getProjectTypeByClientId(Long projectTypeId,Long clientId) {
		return find.where().eq("projectid", projectTypeId).eq("clientId", clientId).findList();
	}
	
	public static List<Projectinstance> getProjectTypeBySupplierId(Long projectTypeId,Long supplierId) {
		return find.where().eq("projectid", projectTypeId).eq("supplier.id", supplierId).findList();
	}
	
	public static List<Projectinstance> getProjectsOfManager(User manager) {
        return find.where().eq("projectManager", manager).findList();
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
		return getProjectName() ;
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
	
	public static SqlRow getProjectsOfAllUser(Long pId, Long id) {
		String sql = "select projectinstance_user.projectinstance_id from projectinstance_user where projectinstance_id =: pId AND projectinstance_user.user_id = :id";
		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		sqlQuery.setParameter("id", id);
		SqlRow list = sqlQuery.findUnique();
		 return list;
	}

	public static void getProjectsOfUserDelerte(Long id) {
		String sql = "DELETE FROM projectinstance_user WHERE projectinstance_id="+id;
		SqlUpdate update = Ebean.createSqlUpdate(sql);
		update.execute();
		
	}
	
	public static void getProjectsOfSupplierDelerte(Long id) {
		String sql = "DELETE FROM projectinstance_supplier WHERE projectinstance_id="+id;
		SqlUpdate update = Ebean.createSqlUpdate(sql);
		update.execute();
		
	}
	
}
