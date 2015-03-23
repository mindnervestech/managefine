package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class RoleLeave extends Model {

	@Id
	public Long id;
	
	@OneToOne
	public Company company;
	
	@OneToOne
	public RoleLevel roleLevel;
		
	/*@OneToMany
	@Column(name="leave_level_id")
	public List<LeaveLevel> leaveLevels;*/
	
	
	@OneToOne
	@Column(name="leave_level_id")
	public LeaveLevel leaveLevel;
	
	public LeaveX leaveX;
	
	@Column(nullable=false)
	public Long total_leave;

	public static Model.Finder<Long, RoleLeave> find = new Model.Finder<Long,RoleLeave>

(Long.class, RoleLeave.class);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public RoleLevel getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(RoleLevel roleLevel) {
		this.roleLevel = roleLevel;
	}

	/*public LeaveLevel getLeaveLevel() {
		return leaveLevel;
	}

	public void setLeaveLevel(LeaveLevel leaveLevel) {
		this.leaveLevel = leaveLevel;
	}*/

	public LeaveX getLeaveX() {
		return leaveX;
	}

	public void setLeaveX(LeaveX leaveX) {
		this.leaveX = leaveX;
	}

	public Long getTotal_leave() {
		return total_leave;
	}

	public void setTotal_leave(Long total_leave) {
		this.total_leave = total_leave;
	}

	public LeaveLevel getLeaveLevel() {
		return leaveLevel;
	}

	public void setLeaveLevel(LeaveLevel leaveLevel) {
		this.leaveLevel = leaveLevel;
	}

	/*public List<LeaveLevel> getLeaveLevels() {
		return leaveLevels;
	}

	public void setLeaveLevels(List<LeaveLevel> leaveLevels) {
		this.leaveLevels = leaveLevels;
	}*/
}
