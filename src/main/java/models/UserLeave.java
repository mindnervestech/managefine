package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
	
	public Date toDate;
	
	public String status;

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

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public static List<UserLeave> getUserLeaves(User user, Date fromDate,Date endDate) {
		return find.where().eq("user", user).eq("leaveType", 7).between("fromDate", fromDate, endDate).findList();
	}
	
	public static List<UserLeave> getUserFixedYearlyLeaves(User user, Date fromDate) {
		return find.where().eq("user", user).eq("leaveType", 8).betweenProperties("fromDate", "toDate", fromDate).findList();
	}
	
	public static List<UserLeave> getUserPreviousLeaves(User user, Date fromDate) {
		return find.where().eq("user", user).eq("leaveType", 7).betweenProperties("fromDate", "toDate", fromDate).findList();
	}
	
	public static List<UserLeave> getUserWeeklyLeaveList(User user) {
		return find.where().eq("user", user).eq("fromDate", null).findList();
	}
	
	public static List<UserLeave> getUserFixedLeaveList(User user, Date fromDate,Date endDate) {
		return find.where().eq("user", user).eq("leaveType", 7).between("fromDate", fromDate, endDate).findList();
	}
	
	public static UserLeave getUserWeeklyLeave(Integer leaveType,User user) {
		return find.where().eq("user", user).eq("leaveType", leaveType).findUnique();
	}
	
}
