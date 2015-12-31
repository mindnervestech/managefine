package com.mnt.createProject.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import models.User;
import play.db.ebean.Model;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
@Entity

public class AduitLog extends Model{
	
	@Id
	private Long id;
	private Date changeDate;
	@OneToOne
	private User user;
	
	private String entity;
	private String jsonData;
	private Long entityId;
	private Long projectinstance;
	
	
	public static Finder<Long,AduitLog> find = new Finder<Long,AduitLog>(Long.class,AduitLog.class);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
	
	

	public Long getProjectinstance() {
		return projectinstance;
	}

	public void setProjectinstance(Long projectinstance) {
		this.projectinstance = projectinstance;
	}

	public static AduitLog getById(Long id) {
		return find.byId(id);
	}
	
	public static List<AduitLog> getProjectUser(String username) {
		return find.where().eq("userid.id", username).findList();
	}
	
	public static List<SqlRow> getDateHistory(String date, Long mainInstance) {
		
		String[] gvalue = date.split("/");
		String mainDate = gvalue[2]+"-"+gvalue[1]+"-"+gvalue[0];
		
		String sql = "select id from aduit_log where change_date= :mainDate and projectinstance = '"+mainInstance+"'";
		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		sqlQuery.setParameter("mainDate", mainDate);
		List<SqlRow> list = sqlQuery.findList();
		 return list;
	}
	
	/*String sql = "select projectinstance_user.projectinstance_id from projectinstance_user where projectinstance_user.user_id = :id";
	SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
	sqlQuery.setParameter("id", id);*/
	
	public static List<SqlRow> getDateWiseHistory(Long mainInstance) {
		//select DISTINCT Date_Format(change_date,'%d/%m/%Y') as DateFound from aduit_log;
		String sql = "select DISTINCT Date_Format(change_date,'%d/%m/%Y') as date from aduit_log where entity = 'Projectinstancenode' and projectinstance = '"+mainInstance+"'";
		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		List<SqlRow> list = sqlQuery.findList();
		 return list;
	}
	
	
}
