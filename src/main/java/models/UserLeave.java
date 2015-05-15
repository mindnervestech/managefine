package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.mnt.orghierarchy.model.Organization;

import play.db.ebean.Model;

@Entity
public class UserLeave extends Model {

	@Id
	public Long id;
	
	@ManyToOne
	public User user;
	
	public String reason;
	
	public Integer leaveType;
	
	public Date fromDate;

	@ManyToMany(cascade=CascadeType.ALL)
	public List<Organization> organizations;
	
	public static Model.Finder<Long, UserLeave> find = new Model.Finder<Long,UserLeave>(Long.class, UserLeave.class);
	
	
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(Integer leaveType) {
		this.leaveType = leaveType;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public List<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}

	public static List<UserLeave> getUserLeaves(Date fromDate,Date endDate) {
		return find.where().eq("leaveType", 7).between("fromDate", fromDate, endDate).findList();
	}
	
	public static List<UserLeave> getUserFixedYearlyLeaves(Date fromDate) {
		return find.where().eq("leaveType", 8).eq("fromDate", fromDate).findList();
	}
	
	public static List<UserLeave> getUserPreviousLeaves(Date fromDate) {
		return find.where().eq("leaveType", 7).eq("fromDate", fromDate).findList();
	}
	
	public static List<UserLeave> getUserWeeklyLeaveList() {
		return find.where().eq("fromDate", null).findList();
	}
	
	public static List<UserLeave> getUserFixedLeaveList(Date fromDate,Date endDate) {
		return find.where().eq("leaveType", 7).between("fromDate", fromDate, endDate).findList();
	}
	
	public static UserLeave getUserWeeklyLeave(Integer leaveType) {
		return find.where().eq("leaveType", leaveType).findUnique();
	}
	
	public static UserLeave getLeave(Date fromDate) {
		return find.where().eq("fromDate", fromDate).eq("leaveType", "7").findUnique();
	}
	
}
